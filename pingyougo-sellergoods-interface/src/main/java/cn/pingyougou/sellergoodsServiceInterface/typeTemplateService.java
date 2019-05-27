package cn.pingyougou.sellergoodsServiceInterface;

import java.util.List;

import PingYouGouPojo.PingYouGouList;
import cn.pinyougou.pojo.TbTypeTemplate;
import entryPingYouGou.PageToel;

public interface typeTemplateService {
	PageToel<TbTypeTemplate> selectLists(int pageTole,int pageSize,TbTypeTemplate tbTypeTemplate);
	PingYouGouList selectBranSpecification();
	void insertBranSpType(TbTypeTemplate tbtype);
	TbTypeTemplate updateTypeTemp(Long id);
	void deleteTypeTemplate(Long[] ids);
	
	//查询所有
	List<TbTypeTemplate> selectList();
}
