package cn.pingyougou.sorl.service;

import java.util.List;
import java.util.Map;

import cn.pinyougou.pojo.TbItem;

public interface secachService{
	//页面搜索功能
	Map searchSerlect(Map map);
	//索引库添加功能
	void searchInsert(List<TbItem> list);
	//索引库 删除功能
	void searchDelete(Long[] lo);
}
