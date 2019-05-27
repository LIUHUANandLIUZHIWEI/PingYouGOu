package cn.pingyougou.shop.controller;

import java.util.List;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.pingyougou.sellergoodsServiceInterface.GoodsService;
import cn.pinyougou.pojo.TbGoods;
import cn.pinyougou.pojo.TbItemCat;
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
			System.out.println(goodsShopInsert.getGoodsEdit().getId());
			System.out.println(goodsShopInsert.getGoodsEdit().getId()==null);
			if(goodsShopInsert.getGoodsEdit().getId()==null){
				//添加
				goodsService.insertShop(goodsShopInsert);
			}else{
				//修改
				goodsService.updateGoodsShop(goodsShopInsert);
			}
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
		goods.setSellerId(SecurityContextHolder.getContext().getAuthentication().getName());;
		return goodsService.selectGoodsList(pageTole,pageSize,goods);
	}
	
	@Autowired
	private JmsTemplate jmsTemplate;
	//删除 静态网页
	@Autowired
	private Destination topicFreemarkDestinationInsertDelete;
	//删除 索引库
	@Autowired
	private Destination queueSorlDestinationDelete;
	//删除Goods表内容
	@RequestMapping("/deleteGoodsAll.do")
	public ErrorPingYouGou delGoods(final Long[] longs) {
		try {
			goodsService.delgoodsList(longs);
			//通过activeMQ 删除 静态网页
			jmsTemplate.send(topicFreemarkDestinationInsertDelete,new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					return session.createObjectMessage(longs);
				}
			});
			
			//通过activeMQ 中间件 删除 索引库
			jmsTemplate.send(queueSorlDestinationDelete, new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					return session.createObjectMessage(longs);
				}
			});
			return new ErrorPingYouGou(true, "删除成功");
		}catch (Exception e) {
			return new ErrorPingYouGou(false, "删除失败");
		}
	}
	
	//查询所有分类
	@RequestMapping("/selectItemCat.do")
	public List<TbItemCat> selectItemCat(){
		return goodsService.selectItemCat();
	}
	
	//修改
	@RequestMapping("/updateSelectList.do")
	public GoodsShopInsert updateSelectList(Long id) {
		String strName=SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println(strName);
		String name=goodsService.SellerId(id);
		System.out.println(name);
		System.out.println(name.equals(strName));
		if(!name.equals(strName)) {
				return null;
		}
		return  goodsService.updateSelectList(id);
	}
}
