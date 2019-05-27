package cn.pingyougou.user.Controller;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;

import cn.pingyougou.user.interfac.UserService;
import cn.pingyougouUtiles.PhoneFormatCheckUtils;
import cn.pinyougou.pojo.TbUser;
import entryPingYouGou.ErrorPingYouGou;
import entryPingYouGou.PageToel;
/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Reference
	private UserService userService;
	@Autowired
	private JmsTemplate jmstemplate;
	@Autowired
	private Destination queueSmsDestination;
	@Value("${SMS}")
	private String sms;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	//发送验证码
	@RequestMapping("/gainSms.do")
	public ErrorPingYouGou gain(final String phone) {
		
		 if(PhoneFormatCheckUtils.isChinaPhoneLegal(phone)) {
			 if(userService.selectPhone(phone)) {
				 //随机生成验证码
				 String code = (long) (Math.random()*1000000)+"";
				
					jmstemplate.send(queueSmsDestination, new MessageCreator() {
						
						@Override
						public Message createMessage(Session session) throws JMSException {
							//随机生成验证码
							 String code = (long) (Math.random()*1000000)+"";
							 //签名
							 String signature="天禧";
								MapMessage mapMessage = session.createMapMessage();
								
								mapMessage.setString("phone", phone);
								mapMessage.setString("signature", signature);
								mapMessage.setString("sms", sms);
								Map<String, String> map=new HashMap<String, String>();
								map.put("code", code);
								mapMessage.setString("smsTemplate", JSON.toJSONString(map));
								mapMessage.setString("code", code);
							return mapMessage;
						}
					});
					return new ErrorPingYouGou(true,"短信发送成功");
			}else {
				return new ErrorPingYouGou(false,"手机号已存在");
			}
		 }else {
			return new ErrorPingYouGou(false,"手机号格式不正确");
		 }
		
	}
	
	//注册用户
	@RequestMapping("/login.do")
	public ErrorPingYouGou login(@RequestBody TbUser user,String smsCode) {
		System.out.println(smsCode);
		System.out.println(user.getPhone());
		String string = redisTemplate.boundValueOps(user.getPhone()).get();
		System.out.println(string);
		if(smsCode.equals(string)) {
				user.setStatus("Y");
				user.setCreated(new Date());
				user.setUpdated(new Date());
				user.setPassword(DigestUtils.md5Hex(user.getPassword()));
				try {
					userService.insertUser(user);
					return new ErrorPingYouGou(true,"注册成功");
				}catch (Exception e) {
					e.printStackTrace();
					return new ErrorPingYouGou(false,"注册失败");
				}
		}else {
			return new ErrorPingYouGou(false, "验证码输出错误");
		}
	}
	
}
