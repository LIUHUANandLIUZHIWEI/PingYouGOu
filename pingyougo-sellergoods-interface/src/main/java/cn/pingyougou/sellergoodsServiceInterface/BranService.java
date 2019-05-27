package cn.pingyougou.sellergoodsServiceInterface;
import cn.pinyougou.pojo.TbBrand;
import entryPingYouGou.PageToel;
public interface BranService {
	PageToel<TbBrand> selectBran(int pageNum,int pageSize,TbBrand tb);
	
	int insertBran(TbBrand brand);

	TbBrand updataSelect(long id);

	int upDataBran(TbBrand tbBrand);
	void deleteBran(long[] ids);
}
