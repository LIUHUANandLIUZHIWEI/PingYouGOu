package cn.pingyougou.manager.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.pingyougou.sellergoodsServiceInterface.itemCatService;
import cn.pinyougou.pojo.TbItemCat;
import entryPingYouGou.ErrorPingYouGou;
import entryPingYouGou.PageToel;

@RestController
@RequestMapping("/itemCat")
public class itemCatController {
	@Reference
	private itemCatService itemCatService;
	//查询分类
	@RequestMapping("/selectItemCatList.do")
	public PageToel<TbItemCat> selectList(@RequestParam(name="id",defaultValue = "0") long id,
											@RequestParam(name="pageaTole",defaultValue = "1") int pageTole,
											@RequestParam(name="pageSize",defaultValue = "10") int pageSize){
		
		return itemCatService.selectList(id,pageTole,pageSize);
	}
	
	@RequestMapping("/updateItemCat.do")
	public TbItemCat ItemCatOne(long id) {
		return itemCatService.selectOne(id);
	}
	
	@RequestMapping("/insertItemCat.do")
	public ErrorPingYouGou insertItemCat(@RequestBody TbItemCat tbItemCat) {
		
		try {
			itemCatService.inserItemCat(tbItemCat);
			return new ErrorPingYouGou(true, "成功");
		}catch (Exception e) {
			return new ErrorPingYouGou(false, "失败");
		}
	}
	@RequestMapping("/deleteItemCat.do")
	public ErrorPingYouGou deleteItemCat(long[] lo) {
		try {
			int ids=itemCatService.deleteItemCat(lo);
			return new ErrorPingYouGou(true, "删除成功");
		}catch (Exception e) {
			return new ErrorPingYouGou(false, "删除失败");
		}
	}
}
