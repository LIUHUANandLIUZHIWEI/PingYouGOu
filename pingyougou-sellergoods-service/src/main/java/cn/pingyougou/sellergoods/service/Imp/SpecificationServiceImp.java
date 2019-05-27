package cn.pingyougou.sellergoods.service.Imp;

import java.util.Iterator;
import java.util.List;

import javax.management.loading.PrivateClassLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import BeanPingYouGou.PingYouGouPoJo;
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
			redisList();
			return new PageToel<TbSpecification>(page.getTotal(),page.getResult());
		}
		
		//缓存
		@Autowired
		private  RedisTemplate redisTemplate;
		private  void  redisList() {
			//获取 缓存中 模板表所有 key
			Iterator iterator = redisTemplate.boundHashOps("specIds").keys().iterator();
			while(iterator.hasNext()) {
				Object next = iterator.next();
				//遍历书数据 
				if(next!=null) {
					//转换成一个pojo集合
					List<PingYouGouPoJo> object = (List<PingYouGouPoJo>) redisTemplate.boundHashOps("specIds").get(next);
						//遍历集合得到 以specificationOption 表中数据  以 specid 进行缓存
					for(PingYouGouPoJo pojo:object) {
							redisTemplate.boundHashOps("specOption").put(pojo.getId(), selectSpecList(pojo.getId()));
					
					}
				}
				
			}
			
		
			System.out.println("缓存了规格");
		}
		
		//根据模板中的 specIds 表中的ID 查询数据
		public List<TbSpecificationOption> selectSpecList(Long lo){
			TbSpecificationOptionExample example=new TbSpecificationOptionExample();
			example.createCriteria().andSpecIdEqualTo(lo);
			return tbspecificationOptionMapper.selectByExample(example);
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
