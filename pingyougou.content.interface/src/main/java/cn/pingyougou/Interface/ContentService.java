package cn.pingyougou.Interface;

import java.util.List;

import cn.pinyougou.pojo.TbContent;
import cn.pinyougou.pojo.TbContentCategory;
import entryPingYouGou.PageToel;

public interface ContentService {


	TbContent contentOne(long id);

	PageToel<TbContent> contentList(int pageNum, int pageSize);

	void contentDel(Long[] longs);
	
	void contentSave(TbContent tbContentCategory);

	List<TbContentCategory> selectContenCat();

	List<TbContent> contenCatAll(Long id);

}
