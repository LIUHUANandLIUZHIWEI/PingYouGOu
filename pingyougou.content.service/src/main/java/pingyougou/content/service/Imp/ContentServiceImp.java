package pingyougou.content.service.Imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.pingyougou.Interface.ContentService;
import cn.pinyougou.mapper.TbContentCategoryMapper;
import cn.pinyougou.mapper.TbContentMapper;
import cn.pinyougou.pojo.TbContent;
import cn.pinyougou.pojo.TbContentCategory;
import cn.pinyougou.pojo.TbContentExample;
import cn.pinyougou.pojo.TbContentExample.Criteria;
import entryPingYouGou.PageToel;
import net.sf.jsqlparser.statement.execute.Execute;

@Service
@Transactional
public class ContentServiceImp implements ContentService{
	@Autowired
	private TbContentMapper tbContentMapper;
	@Autowired
	private RedisTemplate reduisTemplate;
	@Override
	public TbContent contentOne(long id) {
		return tbContentMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageToel<TbContent> contentList(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		TbContentExample example=new TbContentExample();
		example.setOrderByClause("stort_order");
		example.createCriteria().andStatusEqualTo("1");
		Page<TbContent> page=(Page<TbContent>) tbContentMapper.selectByExample(null);
		return new PageToel<TbContent>(page.getTotal(),page.getResult());
	}
	@Override
	public void contentDel(Long[] longs) {
		if(longs!=null) {
			for(Long lo:longs) {
					tbContentMapper.deleteByPrimaryKey(lo);
			}
		}
	}

	@Override
	public void contentSave(TbContent tb) {
		if(tb!=null) {
		if(tb.getId()!=null) {
			TbContent con=tbContentMapper.selectByPrimaryKey(tb.getId());
			System.out.println(con.getId().longValue()==tb.getId().longValue());
			if(con.getId().longValue()==tb.getId().longValue()) {
				reduisTemplate.boundHashOps("conten").delete(con.getCategoryId());
			}
			reduisTemplate.boundHashOps("content").delete(tb.getCategoryId());
			tbContentMapper.updateByPrimaryKey(tb);
		}else {
			if(tb.getCategoryId()!=null) {
				reduisTemplate.boundHashOps("content").delete(tb.getCategoryId());
				System.out.println("删除缓存");
				tbContentMapper.insert(tb);
			}else {
					throw new NullPointerException("categoryId 没有");
			
			}
			
		}
		}
		
	}
	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;
	@Override
	public List<TbContentCategory> selectContenCat() {
	
		return tbContentCategoryMapper.selectByExample(null);
	}

	@Override
	public List<TbContent> contenCatAll(Long id) {
		
		List<TbContent> list=(List<TbContent>) reduisTemplate.boundHashOps("content").get(id);
		
		if(list==null&&list.size()>0) {
			TbContentExample example=new TbContentExample();
			Criteria crie=example.createCriteria();
			crie.andCategoryIdEqualTo(id);
			crie.andStatusEqualTo("1");
			example.setOrderByClause("sort_order");
			list=tbContentMapper.selectByExample(example);
			reduisTemplate.boundHashOps("content").put(id,list);
			System.out.println("查询数据库");
		}else {
			System.out.println("查询缓存");
		}
		
		return list;
	}
}
