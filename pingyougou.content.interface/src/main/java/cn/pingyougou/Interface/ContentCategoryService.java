package cn.pingyougou.Interface;

import java.util.List;

import cn.pinyougou.pojo.TbContentCategory;
import entryPingYouGou.PageToel;

public interface ContentCategoryService {

	void contentCatSave(TbContentCategory tbContentCategory);

	void contentCatDel(Long[] longs);

	PageToel<TbContentCategory> contentCatList(int pageNum,int  pageSize);

	TbContentCategory contentCatOne(long id);
}
