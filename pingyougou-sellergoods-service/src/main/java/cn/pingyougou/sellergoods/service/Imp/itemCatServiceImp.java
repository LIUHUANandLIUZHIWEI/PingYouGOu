package cn.pingyougou.sellergoods.service.Imp;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.pingyougou.sellergoodsServiceInterface.itemCatService;
import cn.pinyougou.mapper.TbItemCatMapper;
import cn.pinyougou.pojo.TbItemCat;
import cn.pinyougou.pojo.TbItemCatExample;
import entryPingYouGou.PageToel;
@Service
@Transactional
public class itemCatServiceImp implements itemCatService{
		@Autowired
		private TbItemCatMapper tbItemCatMapper;
	@Override
	public PageToel<TbItemCat> selectList(long id, int pageTole, int pageSize) {
		PageHelper.startPage(pageTole, pageSize);
		TbItemCatExample example=new TbItemCatExample();
		example.createCriteria().andParentIdEqualTo(id);
		Page<TbItemCat> page=(Page<TbItemCat>) tbItemCatMapper.selectByExample(example);
		redis();
		return new PageToel<TbItemCat>(page.getTotal(),page.getResult());
	}
	@Autowired
	private RedisTemplate redisTemplate;
	//数据缓存
	private void redis() {
		List<TbItemCat> list = tbItemCatMapper.selectByExample(null);
		for(TbItemCat itemcat:list) {
			redisTemplate.boundHashOps("item").put(itemcat.getName(), itemcat.getTypeId());
		}
		System.out.println("缓冲分类");
	}
	@Override
	public TbItemCat selectOne(long id) {
		return tbItemCatMapper.selectByPrimaryKey(id);
	}
	@Override
	public void inserItemCat(TbItemCat tbItemCat) {
		if(tbItemCat.getId()!=null&&tbItemCat.getId().toString().length()>0) {
			tbItemCatMapper.deleteByPrimaryKey(tbItemCat.getId());
		}
		tbItemCatMapper.insert(tbItemCat);
	}
	@Override
	public int deleteItemCat(long[] logs) {
		TbItemCatExample example=new TbItemCatExample();
		for(long lo:logs) {
			example.createCriteria().andParentIdEqualTo(lo);
			tbItemCatMapper.deleteByExample(example);
			tbItemCatMapper.deleteByPrimaryKey(lo);
		}
		return 0;
	}
	
	

}
