package cn.pingyougou.manager.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.pingyougou.sellergoodsServiceInterface.BranService;
import cn.pinyougou.pojo.TbBrand;
import entryPingYouGou.ErrorPingYouGou;
import entryPingYouGou.PageToel;
//Controller和RequestBody  的结合体
@RestController
@RequestMapping("/bran")
public class branController {
		@Reference
		private BranService branService;
		//查询所有品牌并分页功能
	@RequestMapping("/selectBran.do")
	public PageToel<TbBrand> getbran(@RequestBody TbBrand tbBrand,int pageNum,int pageSize){
		return branService.selectBran(pageNum,pageSize,tbBrand);
	}
	
	
	//添加品牌产品
	@RequestMapping("/intsertBrean.do")
	public ErrorPingYouGou insertBran(@RequestBody TbBrand brand) {
		
		try {
			int branTrue=branService.insertBran(brand);
			if(branTrue>0) {
				return new ErrorPingYouGou(true, "添加成功");
			}
		}catch (Exception e) {
			// TODO: handle exception
			return new ErrorPingYouGou(true, "添加失败");
		}
		return new ErrorPingYouGou(true, "添加失败");
	} 
	//查询单个品牌
	@RequestMapping("/updataSelect.do")
	public TbBrand updataSelect(long id) {
		return branService.updataSelect(id);
	}
	
	//修改品牌
	@RequestMapping("/updateBran")
	public ErrorPingYouGou updateBran(@RequestBody TbBrand tbBrand) {
		
		try{
			int branId=branService.upDataBran(tbBrand);
			if(branId>0) {
				return new ErrorPingYouGou(true, "修改成功");
			}
		}catch (Exception e) {
		}
		return new ErrorPingYouGou(false, "修改失败");
	}
	//删除品牌功能
	@RequestMapping("/deleteBran.do")
	public ErrorPingYouGou deleteBran(long[] ids) {
	try {
		branService.deleteBran(ids);
		return new ErrorPingYouGou(true, "删除成功");
	}catch (Exception e) {
	}
	return new ErrorPingYouGou(true, "删除失败");
	}
	

}
