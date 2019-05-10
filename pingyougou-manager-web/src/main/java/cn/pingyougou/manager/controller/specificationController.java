package cn.pingyougou.manager.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import PingYouGouPojo.SpecificationPojo;
import cn.pingyougou.sellergoodsServiceInterface.SpecificationService;
import cn.pinyougou.pojo.TbSpecification;
import entryPingYouGou.ErrorPingYouGou;
import entryPingYouGou.PageToel;

@RestController
@RequestMapping("/specification")
public class specificationController {
	@Reference
	private SpecificationService specificationService;
	
	//查询所有规格
	@RequestMapping("/selectList.do")
	public PageToel<TbSpecification> selectList(int pageTole,int pageSize,@RequestBody TbSpecification tbSpecification) {
		return specificationService.selectList(pageTole,pageSize,tbSpecification.getSpecName());
	}
	
	@RequestMapping("/insertList.do")
	public ErrorPingYouGou insertList(@RequestBody SpecificationPojo specificationPojo) {
		try {
			int i=specificationService.insertList(specificationPojo);
				return new ErrorPingYouGou(true, "添加成功");
		}catch (Exception e) {
			e.printStackTrace();
			return new ErrorPingYouGou(true, "添加失败");
		}
		
		
	}
	
	@RequestMapping("/updateInsert.do")
	public SpecificationPojo updateInser(@RequestParam(name = "id")long id) {
	return specificationService.updateInsert(id);	
	}
	
	@RequestMapping("/updateSp.do")
	public ErrorPingYouGou updateSp(@RequestBody SpecificationPojo specificationPojo) {
		try {
			specificationService.updateSp(specificationPojo);
			return new ErrorPingYouGou(true, "修改成功");
		}catch (Exception e) {
			return new ErrorPingYouGou(true, "修改失败");
		}
	}
	
	@RequestMapping("/deleteSp.do")
	public ErrorPingYouGou deleteSp(long[] ids) {
		try {
			int i=specificationService.deleteSp(ids);
			if(i>0) {
				return new ErrorPingYouGou(true, "删除成功");
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return new ErrorPingYouGou(true, "删除失败");
	}
	
}
