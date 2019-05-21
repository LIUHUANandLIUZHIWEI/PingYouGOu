package cn.pingyougou.manager.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.pingyougou.Interface.ContentCategoryService;
import cn.pinyougou.pojo.TbContentCategory;
import entryPingYouGou.ErrorPingYouGou;
import entryPingYouGou.PageToel;

@RestController
@RequestMapping("/contentCaregory")
public class ControllerCategoryController {
	@Reference
	private ContentCategoryService contentCategoryService;
	
		//删除
	@RequestMapping("/contentCatDel.do")
	public ErrorPingYouGou contentCatDel(Long[] longs) {
		try {
			contentCategoryService.contentCatDel(longs);
			return new ErrorPingYouGou(true, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorPingYouGou(false, "失败");
		}
	}
		//查找所有
		@RequestMapping("/contentCatList.do")
		public PageToel<TbContentCategory> contentCatList(int pageNum,int  pageSize){
			System.out.println("d");
			return contentCategoryService.contentCatList(pageNum,pageSize);
		}
		//根据id查询一个
		@RequestMapping("/contentCatOne.do")
		public TbContentCategory contentCatOne(long id) {
			return contentCategoryService.contentCatOne(id);
		}
	
		//修改和保存
		@RequestMapping("/contentCatSave.do")
		public ErrorPingYouGou contentCatSave(@RequestBody TbContentCategory tbContentCategory) {
			try {
				contentCategoryService.contentCatSave(tbContentCategory);
				return new ErrorPingYouGou(true, "成功");
			} catch (Exception e) {
				e.printStackTrace();
				return new ErrorPingYouGou(false, "失败");
			}
		}
	
}
