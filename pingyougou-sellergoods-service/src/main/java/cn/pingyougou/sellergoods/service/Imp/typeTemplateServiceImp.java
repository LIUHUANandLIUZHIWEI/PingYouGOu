package cn.pingyougou.sellergoods.service.Imp;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import BeanPingYouGou.PingYouGouPoJo;
import PingYouGouPojo.PingYouGouList;
import cn.pingyougou.sellergoodsServiceInterface.typeTemplateService;
import cn.pinyougou.mapper.TbBrandMapper;
import cn.pinyougou.mapper.TbSpecificationMapper;
import cn.pinyougou.mapper.TbTypeTemplateMapper;
import cn.pinyougou.pojo.TbAddress;
import cn.pinyougou.pojo.TbBrand;
import cn.pinyougou.pojo.TbTypeTemplate;
import cn.pinyougou.pojo.TbTypeTemplateExample;
import entryPingYouGou.PageToel;

@Service
@Transactional
public class typeTemplateServiceImp implements typeTemplateService{

	@Autowired
	private TbTypeTemplateMapper tbTypeTemplateMapper;
	
	@Autowired
	private TbBrandMapper tbBrandMapper;
	
	@Autowired
	private TbSpecificationMapper tbSpecificationMapper;
	@Override
	public PageToel<TbTypeTemplate> selectLists(int pageTole, int pageSize, TbTypeTemplate tbTypeTemplate) {
		
		PageHelper.startPage(pageSize, pageTole);
		TbTypeTemplateExample examle=null;
		if(tbTypeTemplate!=null) {
			examle=new TbTypeTemplateExample();
			if(tbTypeTemplate.getName()!=null) {
				System.out.println(tbTypeTemplate.getName());
				examle.createCriteria().andNameLike("%"+tbTypeTemplate.getName()+"%");
			}
		}
		Page<TbTypeTemplate> page=(Page<TbTypeTemplate>)tbTypeTemplateMapper.selectByExample(examle);
		selectList();
		return new PageToel<TbTypeTemplate>(page.getTotal(),page);
	}

	@Override
	public PingYouGouList selectBranSpecification() {
		List<PingYouGouPoJo> bran=tbBrandMapper.selectPingYouGouList();
		List<PingYouGouPoJo> splification=tbSpecificationMapper.selectSplificationList();
		return new PingYouGouList(bran,splification);
	}

	@Override
	public void insertBranSpType(TbTypeTemplate tbtype) {
		if(tbtype.getId()!=null&&tbtype.getId().toString().length()>0) {
			tbTypeTemplateMapper.deleteByPrimaryKey(tbtype.getId());
		}else {
			tbtype.setId(null);
		}
		tbTypeTemplateMapper.insert(tbtype);
	}
	

	@Override
	public TbTypeTemplate updateTypeTemp(Long id) {
		return tbTypeTemplateMapper.selectByPrimaryKey(id);
	}

	@Override
	public void deleteTypeTemplate(Long[] ids) {
		for(Long lo:ids) {
			tbTypeTemplateMapper.deleteByPrimaryKey(lo);
		}
		
	}
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	
	//查询所有并传入 缓存中
	@Override
	public List<TbTypeTemplate> selectList() {
		List<TbTypeTemplate> list = tbTypeTemplateMapper.selectByExample(null);
		for(TbTypeTemplate temp:list) {
			//缓存品牌
			List<PingYouGouPoJo> parseArray = JSON.parseArray(temp.getBrandIds(),PingYouGouPoJo.class);
			redisTemplate.boundHashOps("brand").put(temp.getId(), parseArray);
			//缓存 规格
			List<PingYouGouPoJo> SpecIds = JSON.parseArray(temp.getSpecIds(),PingYouGouPoJo.class);
			redisTemplate.boundHashOps("specIds").put(temp.getId(), SpecIds);
		}
		System.out.println("缓存了模板");
		return list;
	}

}
