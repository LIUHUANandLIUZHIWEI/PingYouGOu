package cn.pingyougou.user.interfac;
import java.util.List;

import cn.pinyougou.pojo.TbUser;
import entryPingYouGou.PageToel;
/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface UserService {
	//验证手机号
	boolean selectPhone(String phone);
//用户注册
	void insertUser(TbUser user);


}
