package cn.pingyougou.sorl.service.Imp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.FilterQuery;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.HighlightQuery;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.GroupPage;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;

import BeanPingYouGou.PingYouGouPoJo;
import cn.pingyougou.sorl.service.secachService;
import cn.pinyougou.pojo.TbItem;
import cn.pinyougou.pojo.TbSpecificationOption;
import cn.pinyougou.pojo.TbTypeTemplate;

@Service(timeout = 5000)
public class searchServiceImp implements secachService  {
	@Autowired
	private SolrTemplate solrTemplate;
	@Override
	public Map searchSerlect(Map map) {
		Map<String, Object> maps=new HashMap<String, Object>();
		//搜索 
		
		maps.putAll(search(map));
		ArrayList list = new ArrayList() ;
		list.addAll((HashSet) maps.get("category"));
		if(map.get("category")!=null&&!map.get("category").equals("")) {
			maps.putAll(searchJie(map.get("category")+""));
		}else {
			if(list.size()>0) {
				maps.putAll(searchJie(list.get(0)+""));
			}else {
				maps.putAll(searchJie("手机"));
			}
		}
		//从缓存中获取规格  品牌
		return maps;
	}
	//搜索按钮
	public Map  search(Map map) {
		Map maps=new HashMap();
		
		//设置查询条件
		//设置查询域  这里设置的是 复制域
		Criteria criteria=new Criteria("item_keywords").contains(map.get("keywords")+"");
		HighlightQuery query = new SimpleHighlightQuery(criteria);
		//过滤 分类
		if(map.get("category")!=null&&!map.get("category").equals("")) {
			FilterQuery cat=new SimpleQuery();
			Criteria catCrit=new Criteria("item_category").contains(map.get("category")+"");
			cat.addCriteria(catCrit);
			query.addFilterQuery(cat);
		}
		//过滤品牌
		if(map.get("brand")!=null&&!map.get("brand").equals("")) {
			FilterQuery branC=new SimpleQuery();
			Criteria branCrit=new Criteria("item_brand").contains(map.get("brand")+"");
			branC.addCriteria(branCrit);
			query.addFilterQuery(branC);
		}
		//过滤规格
		if(map.get("specIds")!=null) {
			Map obj = (Map)map.get("specIds");
			Set entry = obj.keySet();
			Iterator iterator = entry.iterator();
			while(iterator.hasNext()) {
				FilterQuery specC=new SimpleQuery();
				Object next = iterator.next();
				Criteria specCtr=new Criteria("item_spec_"+next).contains(obj.get(next)+"");
				specC.addCriteria(specCtr);
				query.addFilterQuery(specC);
			}
		
		}
		
		//价格过滤
		if(map.get("price")!=null && !map.get("price").equals("")){
			String strs = (String)map.get("price");
			String[] split = strs.split("-");
			FilterQuery priceFil=new SimpleQuery();
			Criteria priceCri=new Criteria("item_price");
			if(split.length==1) {
				priceCri.greaterThan(split[0]);
				priceFil.addCriteria(priceCri);
				query.addFilterQuery(priceFil);
			}else {
				priceCri.greaterThan(split[0]);
				priceCri.lessThan(split[1]);
				priceFil.addCriteria(priceCri);
				query.addFilterQuery(priceFil);
			}
		}
		
		//按价格排序
		if(map.get("priceDesc")!=null && !map.get("priceDesc").equals("")) {
			Sort sortP=null;
			if(map.get("priceDesc").equals("DESC")) {
				sortP=new Sort(Sort.DEFAULT_DIRECTION.DESC, "item_price");
			}else {
				sortP=new Sort(Sort.DEFAULT_DIRECTION.ASC,"item_price");
			}
			query.addSort(sortP);
		}
		
		//按新品排序
		if(map.get("newproduct")!=null && !map.get("newproduct").equals("")) {
			Sort sortnew=new Sort(Sort.DEFAULT_DIRECTION.DESC, "item_updateTime");
			query.addSort(sortnew);
		}
		//分页
		//当前页
		Integer pageCurrent=1;
		//显示页数
		Integer pageSize=40;
		//当前页
		if( map.get("pageCurrent")!=null && !map.get("pageCurrent").equals("")) {
			pageCurrent=Integer.parseInt(map.get("pageCurrent")+"");
		}
		//显示页数
		if(map.get("pageSize")!=null && !map.get("pageSize").equals("")) {
			pageSize=Integer.parseInt(map.get("pageSize")+"");
		}
		
		FilterQuery queryPage=new SimpleQuery();
		query.addFilterQuery(queryPage);
		query.setOffset((pageCurrent-1)*pageSize);
		query.setRows(pageSize);
		
		//设置 前置和后置 和 需要高亮显示的域
		HighlightOptions setSimplePostfix = new HighlightOptions().addField("item_title");
		setSimplePostfix.setSimplePrefix("<em style='color:red'>");
		setSimplePostfix.setSimplePostfix("</em>");
		query.setHighlightOptions(setSimplePostfix);
		HighlightPage<TbItem> page = solrTemplate.queryForHighlightPage(query, TbItem.class);//设置查询
		//循环设置  高亮  设置到 原类型中
		for(HighlightEntry<TbItem> higlist: page.getHighlighted()) {
				if(higlist.getHighlights().size()>0) {
					if(higlist.getHighlights().get(0).getSnipplets().size()>0&&higlist.getHighlights().size()>0) {
						higlist.getEntity().setTitle(higlist.getHighlights().get(0).getSnipplets().get(0));
					}
				}
		}
		List<TbItem> content = page.getContent();
		Set set=new HashSet();
		for(TbItem item:content) {
			System.out.println(item.getCategory());
			set.add(item.getCategory());
		}
			//分页封装
				GroupPage<TbItem> pages = solrTemplate.queryForGroupPage(query, TbItem.class);
				//当前页
				maps.put("pageCurrent",pageCurrent);
				//总记录数
				maps.put("pageNum",page.getTotalElements());
				//总页数
				maps.put("pageTole", page.getTotalPages());
				
				//获取所有商品数据
				maps.put("rowsList",content);
				//获取所有分类
				maps.put("category",set);
				return maps;
			}
	@Autowired
	private RedisTemplate redisTemplate;
	//从缓存中 获取  品牌   规格 	
	
		private  Map  searchJie(String str) {
			Map map=new  HashMap();
			List list=new ArrayList();
			TbTypeTemplate tbtype=null;
			//根据分类名称获取模板ID 
			if(str!=null&&!"".equals(str)) {
				Object object=redisTemplate.boundHashOps("item").get(str);
					if(object!=null) {
						//更具模板ID 查询模板中brandIDS 数据 并封装数据
						map.put("brand", redisTemplate.boundHashOps("brand").get(object));
							//根据模板ID 获取模板表中specID 数据 并封装 
						List<PingYouGouPoJo> pojos = (List<PingYouGouPoJo>) redisTemplate.boundHashOps("specIds").get(object);
								//specID数据 更具id查询所有规格数据
							Map m=new HashMap();
							for(PingYouGouPoJo gou:pojos) {
								List<TbSpecificationOption> tbObject = (List<TbSpecificationOption>)redisTemplate.boundHashOps("specOption").get(gou.getId());
								String[] strs=new String[tbObject.size()];
								for(int i=0;i<tbObject.size();i++) {
								
									strs[i]=tbObject.get(i).getOptionName();
								}
								m.put(gou.getText(),strs);
							}
							//封装数据
							map.put("specIds", m);
						}
			}
				
			return map;
		}
		/**
		 * 添加索引库
		 */
		@Override
		public void searchInsert(List<TbItem> list) {
			for(TbItem item:list) {
				Map map = JSON.parseObject(item.getSpec(),Map.class);
				item.setMap(map);
				System.out.println(item.getTitle());
			}
			solrTemplate.saveBeans(list);
			solrTemplate.commit();
		}
		
		/**
		 * 删除索引库
		 */
		@Override
		public void searchDelete(Long[] lo) {
			for(Long l:lo) {
				solrTemplate.deleteById(l+"");
			}
		}
}
