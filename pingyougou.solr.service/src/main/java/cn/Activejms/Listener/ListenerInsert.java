package cn.Activejms.Listener;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import cn.pingyougou.sorl.service.secachService;
import cn.pinyougou.pojo.TbItem;

@Component("myMessageListenerInsert")
public class ListenerInsert implements MessageListener{
	@Autowired
	private secachService secachService;
	@Override
	public void onMessage(Message message) {
		TextMessage textMessage=(TextMessage) message;
		try {
			String text = textMessage.getText();
			List<TbItem> parseObject =JSON.parseArray(text, TbItem.class);
			secachService.searchInsert(parseObject);
			System.out.println("搜索库添加成功");
		} catch (JMSException e) {
			e.printStackTrace();
			System.out.println("搜索库添加失败");
		}
	}

}
