package cn.pingyougou.sellergoodsServiceInterface;
import PingYouGouPojo.SpecificationPojo;
import cn.pinyougou.pojo.TbSpecification;
import entryPingYouGou.PageToel;

public interface SpecificationService {

	PageToel<TbSpecification> selectList(int pageTole,int pageSize,String pageStr);

	int insertList(SpecificationPojo specificationPojo);

	SpecificationPojo updateInsert(long id);

	void updateSp(SpecificationPojo specificationPojo);
 
	int deleteSp(long[] ids);

}
