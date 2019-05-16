package cn.pingyougou.shop.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.pingyougou.sellergoodsServiceInterface.GoodsService;
import cn.pinyougou.pojo.TbGoods;
import cn.pinyougou.pojo.TbItemCat;
import cn.pinyougou.pojo.TbSpecificationOption;
import cn.pinyougou.pojo.TbTypeTemplate;
import entryPingYouGou.ErrorPingYouGou;
import entryPingYouGou.GoodsShopInsert;
import entryPingYouGou.PageToel;

@RestController
@RequestMapping("/goodsEditAndDesc")
public class goodsController{
	@Reference
	private GoodsService goodsService;
	
	@RequestMapping("/insertShop.do")
	public ErrorPingYouGou inserShop(@RequestBody GoodsShopInsert goodsShopInsert) {
		goodsShopInsert.getGoodsEdit().setSellerId(SecurityContextHolder.getContext().getAuthentication().getName());
		try {
			goodsService.insertShop(goodsShopInsert);
			return new ErrorPingYouGou(true, "添加成功");
		}catch (Exception e) {
			e.printStackTrace();
			return new ErrorPingYouGou(false, "添加失败");
		}
	}
	//查询分类
	@RequestMapping("/ItemCat.do")
	public List<TbItemCat> selectItemCat(long id){
		return goodsService.selectItemcat(id);
	}
	//查询模板
	@RequestMapping("/TypeTemplate.do")
	public TbTypeTemplate selectTypeTemplate(long typeId) {
		return goodsService.selectTypeTemplate(typeId);
	}
	//根据 模板id查询规格id和 在通过规格id查询 和规格多对多的表
	@RequestMapping("/spectificatonOption.do")
	public List<Map> ItemList(Long typeStr){
		return goodsService.selectItemList(typeStr);
	}
	//查询所有goods数据
	@RequestMapping("/selectAllList.do")
	public PageToel<TbGoods>  selectGoodsList(int pageTole,int pageSize,@RequestBody TbGoods goods){
		return goodsService.selectGoodsList(pageTole,pageSize,goods);
	}
	
	//删除Goods表内容
	@RequestMapping("/deleteGoodsAll.do")
	public ErrorPingYouGou delGoods(Long[] longs) {
		try {
			goodsService.delgoodsList(longs);
			return new ErrorPingYouGou(true, "删除成功");
		}catch (Exception e) {
			return new ErrorPingYouGou(false, "删除失败");
		}
	}
	
	//查询所有分类
	@RequestMapping("/selectSpecificationOption.do")
	public List<TbSpecificationOption> selectSpecificationOption(){
		return goodsService.selectSpecificationOption();
	}
}
