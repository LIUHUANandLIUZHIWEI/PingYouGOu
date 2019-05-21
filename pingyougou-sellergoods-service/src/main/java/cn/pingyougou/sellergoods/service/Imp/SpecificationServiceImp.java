package cn.pingyougou.sellergoods.service.Imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import PingYouGouPojo.SpecificationPojo;
import cn.pingyougou.sellergoodsServiceInterface.SpecificationService;
import cn.pinyougou.mapper.TbSpecificationMapper;
import cn.pinyougou.mapper.TbSpecificationOptionMapper;
import cn.pinyougou.pojo.TbSpecification;
import cn.pinyougou.pojo.TbSpecificationExample;
import cn.pinyougou.pojo.TbSpecificationExample.Criteria;
import entryPingYouGou.PageToel;
import cn.pinyougou.pojo.TbSpecificationOption;
import cn.pinyougou.pojo.TbSpecificationOptionExample;
@Service
@Transactional
public class SpecificationServiceImp implements SpecificationService {

		@Autowired
		private TbSpecificationMapper tbSpecificationMapper;
		@Autowired
		private TbSpecificationOptionMapper tbspecificationOptionMapper;
	
		@Override
		public PageToel<TbSpecification> selectList(int pageTole,int pageSize,String pageStr) {
			PageHelper.startPage(pageTole, pageSize);
			TbSpecificationExample tb=null;
			if(pageStr!=null&&pageStr.length()>0) {
				tb=new TbSpecificationExample();
				Criteria crit=tb.createCriteria();
				crit.andSpecNameLike("%"+pageStr+"%");
			}
			Page<TbSpecification> page=(Page<TbSpecification>) tbSpecificationMapper.selectByExample(tb);
			
			return new PageToel<TbSpecification>(page.getTotal(),page.getResult());
		}

		@Override
		public int insertList(SpecificationPojo specificationPojo) {
			Long lo=null;
			if(specificationPojo.getSpification().getId()!=null) {
				lo=specificationPojo.getSpification().getId();
			}
			int i=tbSpecificationMapper.insert(specificationPojo.getSpification());
			if(lo==null) {
				lo=specificationPojo.getSpification().getId();
			}
			for(TbSpecificationOption tbsp:specificationPojo.getOption()) {
				tbsp.setSpecId(lo);
				tbspecificationOptionMapper.insert(tbsp);
			}
			return i;
		}

		@Override
		public SpecificationPojo updateInsert(long id) {
			SpecificationPojo sp=new SpecificationPojo();
			sp.setSpification(tbSpecificationMapper.selectByPrimaryKey(id));
			 TbSpecificationOptionExample example=new TbSpecificationOptionExample();
			example.createCriteria().andSpecIdEqualTo(id);
			sp.setOption(tbspecificationOptionMapper.selectByExample(example));
			return sp;
		}

		@Override
		public void updateSp(SpecificationPojo specificationPojo) {
			long lo=specificationPojo.getSpification().getId();
			TbSpecificationOptionExample example=new TbSpecificationOptionExample();
			tbSpecificationMapper.deleteByPrimaryKey(lo);
			example.createCriteria().andSpecIdEqualTo(lo);
			tbspecificationOptionMapper.deleteByExample(example);
			this.insertList(specificationPojo);
		}

		@Override
		public int deleteSp(long[] ids) {
			try {
				for(long lo:ids) {
					 TbSpecificationOptionExample example=new TbSpecificationOptionExample();
					tbSpecificationMapper.deleteByPrimaryKey(lo);
					example.createCriteria().andSpecIdEqualTo(lo);
					tbspecificationOptionMapper.deleteByExample(example);
				}
				return 1;
			}catch (Exception e) {
				// TODO: handle exception
			}
		
			return 0;
		}
		
		
}
