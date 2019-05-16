package cn.pingyougou.sellergoods.service.Imp;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
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

}
