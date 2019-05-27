package cn.pingyougou.manager.controller;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import PingYouGouPojo.PingYouGouList;
import cn.pingyougou.sellergoodsServiceInterface.typeTemplateService;
import cn.pinyougou.pojo.TbTypeTemplate;
import entryPingYouGou.ErrorPingYouGou;
import entryPingYouGou.PageToel;

@RestController
@RequestMapping("/typeTemplate")
public class typeTemplateController {
	@Reference
	private typeTemplateService typeTemplateService;
//查询所有模
	@RequestMapping("/selectList.do")
	public PageToel<TbTypeTemplate> selectList(int pageTole,int pageSize,@RequestBody TbTypeTemplate tbTypeTemplate){
		
		PageToel<TbTypeTemplate> page=typeTemplateService.selectLists(pageTole,pageSize,tbTypeTemplate);
		
		return page;
	}
	
	@RequestMapping("/SelectBranSpType.do")
	public PingYouGouList SelectBranSpType() {
		return typeTemplateService.selectBranSpecification();
	}
	
	@RequestMapping("/insertBranSpType.do")
	public ErrorPingYouGou insertBranSpType(@RequestBody TbTypeTemplate tbTypetemalte) {
		try {
			typeTemplateService.insertBranSpType(tbTypetemalte);
			
			return new ErrorPingYouGou(true,"添加成功");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new ErrorPingYouGou(true,"添加失败");
	}
	
	@RequestMapping("/updateTypeTemplate.do")
	public TbTypeTemplate updatetypeTemplate(long id) {
		
		return typeTemplateService.updateTypeTemp(id);
	}
	
	@RequestMapping("/deleteTypeTemplate.do")
	public ErrorPingYouGou deleteTypeTemplate(Long[] ids) {
		try {
			typeTemplateService.deleteTypeTemplate(ids);
			return new ErrorPingYouGou(true, "删除成功");
		}catch (Exception e) {
		}
		return new ErrorPingYouGou(true, "删除失败");
	}
	
}
