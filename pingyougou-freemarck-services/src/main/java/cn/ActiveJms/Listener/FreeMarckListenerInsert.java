package cn.ActiveJms.Listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.pingyougou.freemarck.interfac.FreeMarckService;
@Component("myMessageListenerInsert")
public class FreeMarckListenerInsert implements MessageListener{
	
	@Autowired
	private FreeMarckService freeMarckService;
	@Override
	public void onMessage(Message message) {
		ObjectMessage object=(ObjectMessage) message;
		System.out.println("ddd");
		try {
			Long[] londs=(Long[]) object.getObject();
			for(Long lo:londs) {
				freeMarckService.FreeMarckTemplate(lo);
			}
			System.out.println("网页静态化成功");
		} catch (JMSException e) {
			e.printStackTrace();
			System.out.println("网页静态化失败");
		}
		
	}

}
