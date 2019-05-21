package pingyougou.content.service.Imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.pingyougou.Interface.ContentCategoryService;
import cn.pinyougou.mapper.TbContentCategoryMapper;
import cn.pinyougou.pojo.TbContentCategory;
import cn.pinyougou.pojo.TbContentCategoryExample;
import entryPingYouGou.PageToel;

@Service
@Transactional
public class ContentCategoryServiceImp implements ContentCategoryService{
	
	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;
	@Override
	public void contentCatSave(TbContentCategory tbContentCategory) {
			if(tbContentCategory.getId()==null) {
				tbContentCategoryMapper.insert(tbContentCategory);
			}else {
				tbContentCategoryMapper.updateByPrimaryKey(tbContentCategory);
			}
			
	}

	@Override
	public void contentCatDel(Long[] longs) {
		for(long lo:longs) {
			tbContentCategoryMapper.deleteByPrimaryKey(lo);
		}
	}

	@Override
	public PageToel<TbContentCategory> contentCatList(int pageNum,int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbContentCategory> page=(Page<TbContentCategory>) tbContentCategoryMapper.selectByExample(null);
		return new PageToel<TbContentCategory>(page.getTotal(),page.getResult());
	}

	@Override
	public TbContentCategory contentCatOne(long id) {
		return tbContentCategoryMapper.selectByPrimaryKey(id);
	}



}
