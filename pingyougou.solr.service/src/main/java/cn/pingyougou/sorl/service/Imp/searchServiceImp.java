package cn.pingyougou.sorl.service.Imp;
import java.util.HashMap;
import java.util.Map;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;

import com.alibaba.dubbo.config.annotation.Service;

import cn.pingyougou.sorl.service.secachService;
import cn.pinyougou.pojo.TbItem;

@Service(timeout = 5000)
public class searchServiceImp implements secachService  {
	@Autowired
	private SolrTemplate solrTemplate;

	@Override
	public Map searchSerlect(Map map) {
		Map<String, Object> maps=new HashMap<String, Object>();
		Query query=new SimpleQuery("*:*");
		Criteria criteria=new Criteria("item_keywords").is("手机");
		query.setOffset(1);
		query.setRows(20);
		query.addCriteria(criteria);
		try {
			ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);
			maps.put("rows",page);
		}catch (Exception e) {
			e.printStackTrace();
		}
	
		return maps;
	}
	
	

}
