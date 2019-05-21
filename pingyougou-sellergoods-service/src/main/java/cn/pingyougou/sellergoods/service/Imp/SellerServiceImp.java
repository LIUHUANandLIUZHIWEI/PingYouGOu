package cn.pingyougou.sellergoods.service.Imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import BeanPingYouGou.PingYouGouPoJo;
import cn.pingyougou.sellergoodsServiceInterface.SellerService;
import cn.pinyougou.mapper.TbSellerMapper;
import cn.pinyougou.pojo.TbSeller;
import cn.pinyougou.pojo.TbSellerExample;
import entryPingYouGou.PageToel;
@Service
@Transactional
public class SellerServiceImp implements SellerService{
	
	@Autowired
	private TbSellerMapper tbSellerMapper;
	
	@Override
	public void insertSeller(TbSeller tbseller) {
		
		if(tbseller!=null) {
			tbseller.setStatus("0");
		}
		tbSellerMapper.insert(tbseller);
	}
	@Override
	public TbSeller selectSellerOne(String username) {
		TbSeller tbsel=tbSellerMapper.selectByPrimaryKey(username);
		return tbsel;
	}
	@Override
	public PageToel<TbSeller> selectSellerList(int pageTole, int pageSize, TbSeller tbseller) {
		
		PageHelper.startPage(pageTole, pageSize);
		TbSellerExample example=null;
		if(tbseller!=null) {
			System.out.println(tbseller.getName()+"---"+tbseller.getNickName());
			example=new TbSellerExample();
			example.createCriteria().andStatusEqualTo("0");
			if(tbseller.getName()!=null) {
				example.createCriteria().andNameLike("%"+tbseller.getName()+"%");
			}
			if(tbseller.getNickName()!=null) {
				example.createCriteria().andNickNameEqualTo("%"+tbseller.getNickName()+"%");
			}
		}
		Page<TbSeller> sell=(Page<TbSeller>) tbSellerMapper.selectByExample(example);
			
		return new PageToel<TbSeller>(sell.getTotal(),sell.getResult());
	}
	@Override
	public TbSeller selectAll(String sellerId) {
		return tbSellerMapper.selectByPrimaryKey(sellerId);
	}
	@Override
	public void updateStatus(TbSeller tbSeller) {
		tbSellerMapper.updateByPrimaryKey(tbSeller);
	}

}
