package cn.pingyougou.shop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import cn.pingyougou.sellergoodsServiceInterface.SellerService;
import cn.pinyougou.pojo.TbSeller;

public class SecurityService implements UserDetailsService{

	
		private SellerService sellerService;
			public void setSellerService(SellerService sellerService) {
				this.sellerService = sellerService;
			}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		TbSeller tb=sellerService.selectSellerOne(username);
		List<GrantedAuthority> grantedAuthorities=new ArrayList<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		User user=new User(tb.getSellerId(), tb.getPassword(), tb.getStatus().equals("1")?true:false, true, true, true, grantedAuthorities);
		return user;
	}

}
