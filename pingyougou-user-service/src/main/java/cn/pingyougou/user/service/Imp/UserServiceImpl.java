package cn.pingyougou.user.service.Imp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.pingyougou.user.interfac.UserService;
import cn.pinyougou.mapper.TbUserMapper;
import cn.pinyougou.pojo.TbUser;
import cn.pinyougou.pojo.TbUserExample;
import cn.pinyougou.pojo.TbUserExample.Criteria;
import entryPingYouGou.PageToel;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;
		/**
		 * 验证手机号
		 */
	@Override
	public boolean selectPhone(String phone) {
		TbUserExample example=new TbUserExample();
		example.createCriteria().andPhoneEqualTo(phone);
		List<TbUser> list = userMapper.selectByExample(example);
		if(list.size()<1) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 用户注册
	 */
		@Override
		public void insertUser(TbUser user) {
			userMapper.insert(user);
			
		}
	
	
}
