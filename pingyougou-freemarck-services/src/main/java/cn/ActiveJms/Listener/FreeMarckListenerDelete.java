package cn.ActiveJms.Listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.pingyougou.freemarck.interfac.FreeMarckService;

@Component("myMessageListenerDelete")
public class FreeMarckListenerDelete implements MessageListener {
	@Autowired
	private FreeMarckService freeMarckService;
	
	//根据id删除 静态网页
	@Override
	public void onMessage(Message message) {
		ObjectMessage objectMessage=(ObjectMessage) message;
		System.out.println("dd");
		try {
			Long[] longs=(Long[]) objectMessage.getObject();
			freeMarckService.FreeMarckDelete(longs);
			System.out.println("静态网页删除成功");
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("静态网页删除失败");
		}

	}

}
