package cn.pingyougou.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import cn.pingyougou.sellergoodsServiceInterface.SellerService;
import cn.pinyougou.pojo.TbSeller;
import entryPingYouGou.ErrorPingYouGou;


@RestController
@RequestMapping("/loginNameSeller")
public class userNameController {
	@Reference
	private SellerService sellerService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoding;
	
	@RequestMapping("/user.do")
	public String userName() {
		//获取用户名
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	
	@RequestMapping("/inserSeller.do")
	public ErrorPingYouGou insertSeller(@RequestBody TbSeller tbseller) {
		try{
			tbseller.setPassword(passwordEncoding.encode(tbseller.getPassword()));
			sellerService.insertSeller(tbseller);
			return new ErrorPingYouGou(true,"添加成功");
		}catch (Exception e) {
			
		}
		return new ErrorPingYouGou(false,"添加失败");
	}
}
