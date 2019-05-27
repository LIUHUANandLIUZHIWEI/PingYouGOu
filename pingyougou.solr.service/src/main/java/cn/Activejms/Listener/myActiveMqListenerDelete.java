package cn.Activejms.Listener;

import java.util.Enumeration;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.pingyougou.sorl.service.secachService;

@Component("myMessageListenerDelete")
public class myActiveMqListenerDelete implements MessageListener {
	@Autowired
	private secachService secachService;
	//删除索引库
	@Override
	public void onMessage(Message message) {
	ObjectMessage objectMessage=(ObjectMessage) message;
	System.out.println("solr ");
	try {
		Long[] longs=(Long[]) objectMessage.getObject();
		secachService.searchDelete(longs);
		System.out.println("删除成功");
	} catch (JMSException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("删除失败");
	}
	}


}
