package cn.pingyougou.manager.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.pingyougou.sellergoodsServiceInterface.GoodsService;
import cn.pinyougou.pojo.TbGoods;
import cn.pinyougou.pojo.TbItemCat;
import entryPingYouGou.ErrorPingYouGou;
import entryPingYouGou.PageToel;

@RestController
@RequestMapping("/goods")
public class goodsController {
	@Reference
	private GoodsService goodsService;
	
	@RequestMapping("/goodsSelectList.do")
	public PageToel<TbGoods> goodsSelectList(int pageNum,int pageSize,@RequestBody TbGoods goods){
		return goodsService.selectGoodsList(pageNum, pageSize, goods);
	}
	
	@RequestMapping("/goodsStatusList.do")
	public ErrorPingYouGou goodsStatusList(Long[] longs,long status){
		try {
			
			System.out.println(status);
			goodsService.updateStatusList(longs,status);
			return new ErrorPingYouGou(true,"cg");
		}catch (Exception e) {
			e.printStackTrace();
			return  new ErrorPingYouGou(false,"shibai");
		}
	
	}
	
	@RequestMapping("/goodsitemCatList.do")
	public List<TbItemCat> goodsitemCatList(){
		return goodsService.selectItemCat();
	}
	
	@RequestMapping("/goodsDel.do")
	public ErrorPingYouGou goodsDel(Long[] longs){
		for(Long lo:longs) {
			System.out.println(lo);
		}
		try {
			goodsService.delgoodsList(longs);
			return new ErrorPingYouGou(true, "cg");
		}catch (Exception e) {
			e.printStackTrace();
			return new ErrorPingYouGou(false, "shibai");
		}
		
	}
}
