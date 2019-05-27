package cn.pingyougou.freemarck.service.Imp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import cn.pingyougou.freemarck.interfac.FreeMarckService;
import cn.pinyougou.mapper.TbGoodsDescMapper;
import cn.pinyougou.mapper.TbGoodsMapper;
import cn.pinyougou.mapper.TbItemCatMapper;
import cn.pinyougou.mapper.TbItemMapper;
import cn.pinyougou.pojo.TbGoods;
import cn.pinyougou.pojo.TbGoodsDesc;
import cn.pinyougou.pojo.TbItemExample;
import cn.pinyougou.pojo.TbItemExample.Criteria;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
@Service
public class FreeMarckServiceImp implements FreeMarckService{
	@Value("${FreeMarckURl}")
	private String URl;
	@Autowired
	private FreeMarkerConfig freeMarkerConfig;
	@Autowired
	private TbGoodsMapper tbGoodsMapper;
	@Autowired
	private TbGoodsDescMapper tbGoodsDescMapper;
	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	//生成静态网页
	@Override
	public void FreeMarckTemplate(Long lo) {
		System.out.println(lo);
			Configuration configuration = freeMarkerConfig.getConfiguration();
			try {
				//加载模板
				Template template = configuration.getTemplate("body.ftl");
				//设置参数
				Map map=new HashMap();
				//商品参数
				TbGoods goods = tbGoodsMapper.selectByPrimaryKey(lo);
				map.put("goods",goods);
				
				//设置 规格  保证等参数
				TbGoodsDesc goodsDesc = tbGoodsDescMapper.selectByPrimaryKey(goods.getId());
				map.put("goodsDesc",goodsDesc);
				
				// 设置 SUP  SKP 商品详情表
				TbItemExample example=new TbItemExample();
				example.setOrderByClause("is_default desc");
				Criteria createCriteria = example.createCriteria();
				createCriteria.andGoodsIdEqualTo(lo);
				map.put("items",tbItemMapper.selectByExample(example));
				
				//设置分类
				Writer out=new FileWriter(new File(URl+lo+".html"));
				map.put("category1",tbItemCatMapper.selectByPrimaryKey(goods.getCategory1Id()).getName());
				map.put("category2",tbItemCatMapper.selectByPrimaryKey(goods.getCategory2Id()).getName());
				map.put("category3",tbItemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName());
				//生成静态网页
				template.process(map, out);
				out.close();
			} catch (IOException | TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public void FreeMarckDelete(Long[] lo) {
		//根据id删除文件
		for(Long los:lo) {
			File file = new File(URl+los+".html");
			System.out.println(file.isFile());
			if(file.exists()) {
				file.delete();
			}
		}
		
		
	}
}
