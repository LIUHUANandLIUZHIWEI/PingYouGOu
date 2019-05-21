package cn.solr.Select;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import cn.pinyougou.mapper.TbItemMapper;
import cn.pinyougou.pojo.TbItem;
import cn.pinyougou.pojo.TbItemExample;

@Component("select")
public class Select  implements Serializable{
	@Autowired
	private TbItemMapper tbItemMapper;
	
	@Autowired
	private SolrTemplate solrTemplage;
	public void implutis() {
			TbItemExample example=new TbItemExample();
			example.createCriteria().andStatusEqualTo("1");
			List<TbItem> list = tbItemMapper.selectByExample(example);
			for(TbItem item:list){
				Map specMap= JSON.parseObject(item.getSpec());//将 spec 字段中的 json 字符串转换为 map
				item.setMap(specMap);//给带注解的字段赋值
				System.out.println(item.getTitle());
				}
			System.out.println("结束");
			solrTemplage.saveBeans(list);
			solrTemplage.commit();
	}
	
	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml");
		Select bean = context.getBean("select", Select.class);
		bean.implutis();
	}
	
}
