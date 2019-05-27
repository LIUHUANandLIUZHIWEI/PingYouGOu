package cn.pingyougou.manager.controller;

import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;

import cn.pingyougou.sellergoodsServiceInterface.GoodsService;
import cn.pinyougou.pojo.TbGoods;
import cn.pinyougou.pojo.TbItem;
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
	//solr
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private Destination queueSolrDestination;
	//freemarck
	
	@Autowired
	private Destination topicFreemarkDestinationInsert;
	@RequestMapping("/goodsStatusList.do")
	public ErrorPingYouGou goodsStatusList(final Long[] longs,long status){
		try {
			goodsService.updateStatusList(longs,status);
			System.out.println(status==1);
			if(status==1) {
				//*****导入到索引库
				//得到需要导入的SKU列表
				List<TbItem> itemList = goodsService.solrluse(longs);
				//导入到solr
				//itemSearchService.importList(itemList);	
				final String jsonString = JSON.toJSONString(itemList);//转换为json传输
				System.out.println(jsonString);
				jmsTemplate.send(queueSolrDestination, new MessageCreator() {
					
					@Override
					public Message createMessage(Session session) throws JMSException {
						
						return session.createTextMessage(jsonString);
					}
				});
				
				System.out.println("索引库更新成功");
				//网页静态化
					jmsTemplate.send(topicFreemarkDestinationInsert, new MessageCreator() {
						@Override
						public Message createMessage(Session session) throws JMSException {
							return session.createObjectMessage(longs);
						}
					});
				System.out.println("网页静态化成功");
			}
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
