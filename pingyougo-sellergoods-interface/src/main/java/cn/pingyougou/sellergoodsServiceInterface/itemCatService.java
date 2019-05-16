package cn.pingyougou.sellergoodsServiceInterface;

import cn.pinyougou.pojo.TbItemCat;
import entryPingYouGou.PageToel;

public interface itemCatService {
	PageToel<TbItemCat> selectList(long id,int pageTole,int pageSize);
	TbItemCat selectOne(long id);
	void inserItemCat(TbItemCat tbItemCat);
	int deleteItemCat(long[] logs);
}
