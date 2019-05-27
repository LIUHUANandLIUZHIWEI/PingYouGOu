package cn.pingyougou.solr.controller;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import cn.pingyougou.sorl.service.secachService;

@RestController
@RequestMapping("/itemsearch")
public class searchController{
	@Reference
	private secachService secachservice;
	@RequestMapping("/search")
	public Map search(@RequestBody Map searchMap){
		return  secachservice.searchSerlect(searchMap);
	}
}
