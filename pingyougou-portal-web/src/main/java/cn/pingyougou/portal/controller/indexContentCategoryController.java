package cn.pingyougou.portal.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.pingyougou.Interface.ContentCategoryService;
import cn.pingyougou.Interface.ContentService;
import cn.pinyougou.pojo.TbContent;

@RestController
@RequestMapping("/indexContent")
public class indexContentCategoryController {
	@Reference
	private ContentService contentService;
	//查询所有首页广告
	@RequestMapping("/ContentCategoryList.do")
	public List<TbContent> ContentCategList(Long id) {
		return contentService.contenCatAll(id);
	}
}
