package cn.pingyougou.sellergoods.service.Imp;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.alibaba.fastjson.JSON;

import cn.pingyougou.sellergoodsServiceInterface.GoodsService;
import cn.pinyougou.mapper.TbBrandMapper;
import cn.pinyougou.mapper.TbGoodsDescMapper;
import cn.pinyougou.mapper.TbGoodsMapper;
import cn.pinyougou.mapper.TbItemCatMapper;
import cn.pinyougou.mapper.TbItemMapper;
import cn.pinyougou.mapper.TbSellerMapper;
import cn.pinyougou.mapper.TbSpecificationOptionMapper;
import cn.pinyougou.mapper.TbTypeTemplateMapper;
import cn.pinyougou.pojo.TbGoods;
import cn.pinyougou.pojo.TbGoodsDesc;
import cn.pinyougou.pojo.TbGoodsExample;
import cn.pinyougou.pojo.TbGoodsExample.Criteria;
import cn.pinyougou.pojo.TbItem;
import cn.pinyougou.pojo.TbItemCat;
import cn.pinyougou.pojo.TbItemCatExample;
import cn.pinyougou.pojo.TbItemExample;
import cn.pinyougou.pojo.TbSpecificationOption;
import cn.pinyougou.pojo.TbSpecificationOptionExample;
import cn.pinyougou.pojo.TbTypeTemplate;
import entryPingYouGou.GoodsShopInsert;
import entryPingYouGou.PageToel;

@Service
@Transactional
public class GoodsServiceImp implements GoodsService{
	@Autowired
	private TbGoodsDescMapper tbGoodsDescMapper;
	@Autowired
	private TbGoodsMapper TbGoodsMapper;
	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbTypeTemplateMapper tbTypeTemplateMapper;
	@Autowired
	private TbSpecificationOptionMapper tbSpecificationOptionMapper;
	@Autowired
	private TbBrandMapper TbBrandMapper;
	@Autowired
	private TbSellerMapper tbSellerMapper;
	//修改
	@Override
	public void updateGoodsShop(GoodsShopInsert goodsShopInsert) throws Exception {
	TbGoodsMapper.updateByPrimaryKey(goodsShopInsert.getGoodsEdit());
	tbGoodsDescMapper.updateByPrimaryKey(goodsShopInsert.getGoodsDesc());
	for(TbItem item:goodsShopInsert.getItems()) {
		if(item.getId()!=null) {
			tbItemMapper.deleteByPrimaryKey(item.getId());
		}else {
			throw new Exception("删除错误");
		}
		
	}
		this.items(goodsShopInsert);
	}
	//添加
	@Override	
	public void insertShop(GoodsShopInsert goodsShopInsert) throws Exception {
		goodsShopInsert.getGoodsEdit().setAuditStatus("0");
		goodsShopInsert.getGoodsEdit().setIsMarketable("1");
		
		TbGoodsMapper.insert(goodsShopInsert.getGoodsEdit());
		
		goodsShopInsert.getGoodsDesc().setGoodsId(goodsShopInsert.getGoodsEdit().getId());
		tbGoodsDescMapper.insertSelective(goodsShopInsert.getGoodsDesc());
		
		this.items(goodsShopInsert);
	
	}
	
	private void items(GoodsShopInsert goodsShopInsert) {
		boolean boo="1".equals(goodsShopInsert.getGoodsEdit().getIsEnableSpec());
		if(boo) {
			for(TbItem itemcat:goodsShopInsert.getItems()) {
				String brans="";//品牌
				if(goodsShopInsert.getGoodsEdit().getBrandId()!=null) {
					brans=TbBrandMapper.selectByPrimaryKey(goodsShopInsert.getGoodsEdit().getBrandId()).getName();
				}
				itemcat.setBrand(brans);//品牌名称
				String title=goodsShopInsert.getGoodsEdit().getGoodsName();
				if(boo) {
					Map<String, Object> list=JSON.parseObject(itemcat.getSpec());
					for(Entry<String, Object> map:list.entrySet()) {
							title+=map.getValue()+" ";
					}
				}
				
				itemcat.setTitle(title);//SUP 编号
				String image="";//第一张图片
				if(goodsShopInsert.getGoodsDesc().getItemImages()!=null) {
					List<Map> listImage=JSON.parseArray(goodsShopInsert.getGoodsDesc().getItemImages(),Map.class);
					System.out.println(listImage.get(0).get("url")+"");
					image=listImage.get(0).get("url")+"";
				}
				itemcat.setImage(image);
				//分类它的子类名
				itemcat.setCategoryid(goodsShopInsert.getGoodsEdit().getCategory3Id());
				itemcat.setCreateTime(new Date());//当前时间
				itemcat.setUpdateTime(new Date());//更新时间
				//商家店名和商家ID
				String sellerName="";
				if(goodsShopInsert.getGoodsEdit().getSellerId()!=null) {
					sellerName=tbSellerMapper.selectByPrimaryKey(goodsShopInsert.getGoodsEdit().getSellerId()).getName();
					
				}
				//默认库存
				if(itemcat.getNum()==null&&boo) {
					itemcat.setNum(9999);
				}
				itemcat.setSellerId(goodsShopInsert.getGoodsEdit().getSellerId());
				itemcat.setSeller(sellerName);
				itemcat.setGoodsId(goodsShopInsert.getGoodsEdit().getId());
				tbItemMapper.insert(itemcat);
				}
			
		}
	}

	
	
	@Override
	public List<TbItemCat> selectItemcat(long id) {
		TbItemCatExample example=new TbItemCatExample();
		example.createCriteria().andParentIdEqualTo(id);
		return tbItemCatMapper.selectByExample(example);
	}
	
	@Override
	public TbTypeTemplate selectTypeTemplate(long id) {
		TbItemCat item=tbItemCatMapper.selectByPrimaryKey(id);
		return tbTypeTemplateMapper.selectByPrimaryKey(item.getTypeId());
	}
	
	@Override
	public List<Map> selectItemList(Long typeId) {
	TbTypeTemplate item=tbTypeTemplateMapper.selectByPrimaryKey(typeId);
		List<Map> list=JSON.parseArray(item.getSpecIds(), Map.class);
		if(list!=null) {
		for(Map map:list) {
			TbSpecificationOptionExample example=new TbSpecificationOptionExample();
			
			Long lo= new Long((Integer)map.get("id"));
			example.createCriteria().andSpecIdEqualTo(lo);
			List<TbSpecificationOption> option=tbSpecificationOptionMapper.selectByExample(example);
			map.put("option", option);
		}
		}
		return list;
	}
	
	@Override
	public PageToel<TbGoods> selectGoodsList(int pageTole, int pageSize, TbGoods goods) {
		PageHelper.startPage(pageTole, pageSize);
		TbGoodsExample example=null;
		example=new TbGoodsExample();
		 Criteria crit=example.createCriteria();
		 crit.andIsDeleteIsNull();
		if(goods!=null) {
			 if(goods.getGoodsName()!=null&&goods.getGoodsName().length()>0) {
				 crit.andGoodsNameLike("%"+goods.getGoodsName()+"%");
			 }
			 if(goods.getAuditStatus()!=null&&goods.getAuditStatus().length()>0) {
				 crit.andAuditStatusEqualTo(goods.getAuditStatus());
			 }
			 if(goods.getSellerId()!=null&&goods.getSellerId().length()>0) {
				 crit.andSellerIdEqualTo(goods.getSellerId());
			 }
		}
		Page<TbGoods> page=(Page<TbGoods>) TbGoodsMapper.selectByExample(example);
		return new PageToel<TbGoods>(page.getTotal(), page.getResult());
	}

	@Override
	public void delgoodsList(Long[] longs) {
		for(long lo:longs) {
		TbGoods goo=TbGoodsMapper.selectByPrimaryKey(lo);
		goo.setIsDelete("1");
		TbGoodsMapper.updateByPrimaryKey(goo);
		}
		
	}

	@Override
	public List<TbItemCat> selectItemCat() {
		return tbItemCatMapper.selectByExample(null);
	}
	//根据id查询用户名
	@Override
	public String SellerId(Long id) {
		TbGoods tb=TbGoodsMapper.selectByPrimaryKey(id);
		return tb.getSellerId();
	}

	@Override
	public GoodsShopInsert updateSelectList(Long id) {
		TbGoods tb=TbGoodsMapper.selectByPrimaryKey(id);
		TbGoodsDesc desc=tbGoodsDescMapper.selectByPrimaryKey(id);
		TbItemExample example=new TbItemExample();
		example.createCriteria().andGoodsIdEqualTo(id);
		List<TbItem> items=tbItemMapper.selectByExample(example);
		return new GoodsShopInsert(tb,desc, items);
	}
	
	@Override
	public void updateStatusList(Long[] longs, long status) {
		for(Long lo:longs) {
			TbGoods goo=TbGoodsMapper.selectByPrimaryKey(lo);
			goo.setAuditStatus(status+"");
			TbGoodsMapper.updateByPrimaryKeySelective(goo);
		}
		
	}

}
