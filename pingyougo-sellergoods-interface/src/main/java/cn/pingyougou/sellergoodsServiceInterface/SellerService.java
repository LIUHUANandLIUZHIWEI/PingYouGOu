package cn.pingyougou.sellergoodsServiceInterface;
import cn.pinyougou.pojo.TbSeller;
import entryPingYouGou.PageToel;

public interface SellerService{

	void insertSeller(TbSeller tbseller);
	TbSeller selectSellerOne(String username);
	PageToel<TbSeller> selectSellerList(int pageTole, int pageSize, TbSeller tbseller);
	TbSeller selectAll(String sellerId);
	void updateStatus(TbSeller tbSeller);
}
