package cn.pingyougou.manager.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import cn.pingyougou.sellergoodsServiceInterface.SellerService;
import cn.pinyougou.pojo.TbSeller;
import entryPingYouGou.ErrorPingYouGou;
import entryPingYouGou.PageToel;

@RestController
@RequestMapping("/sellerOne")
public class sellerOneController {
	@Reference
	private SellerService sellerService;
	//查询所有
	@RequestMapping("/selerOneList.do")
	public PageToel<TbSeller> selectOneList(int pageTole,int pageSize,@RequestBody TbSeller tbseller) {
		
		return sellerService.selectSellerList(pageTole,pageSize,tbseller);
	}
	//查询单个
	@RequestMapping("/sellerAll.do")
	public TbSeller selectAll(String sellerId) {
		return sellerService.selectAll(sellerId);
	}
	
	@RequestMapping("/updateStatus.do")
	public ErrorPingYouGou updateStatus(String status,@RequestBody TbSeller tbSeller) {
		
		try {
			tbSeller.setStatus(status);
			sellerService.updateStatus(tbSeller);
			return new ErrorPingYouGou(true, "审核成功");
		}catch (Exception e) {
			return new ErrorPingYouGou(false,"审核失败");
		}
	}
	
	@RequestMapping("/userName.do")
	public String userName() {
		return new SecurityContextHolder().getContext().getAuthentication().getName();
	}
	 
}
