package cn.pingyougou.sellergoods.service.Imp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.pingyougou.sellergoodsServiceInterface.BranService;
import cn.pinyougou.mapper.TbBrandMapper;
import cn.pinyougou.pojo.TbBrand;
import cn.pinyougou.pojo.TbBrandExample;
import cn.pinyougou.pojo.TbBrandExample.Criteria;
import entryPingYouGou.PageToel;

@Service
@Transactional
public class BranServiceImp implements BranService{
	@Autowired
	private TbBrandMapper tbBrandMapper;

	@Override
	public PageToel<TbBrand> selectBran(int pageNum, int pageSize,TbBrand tbBrand) {
		TbBrandExample tbBrandExample=null;
		if(tbBrand!=null) {
			tbBrandExample=new TbBrandExample();
			Criteria cri=tbBrandExample.createCriteria();
			if(tbBrand.getName()!=null&&tbBrand.getName().length()>0) {
				cri.andNameLike("%"+tbBrand.getName()+"%");
			}
			if(tbBrand.getFirstChar()!=null&&tbBrand.getFirstChar().length()>0) {
				cri.andFirstCharLike("%"+tbBrand.getFirstChar()+"%");
			}
			
		}
		PageHelper.startPage(pageNum, pageSize);
		Page<TbBrand> bran=(Page)tbBrandMapper.selectByExample(tbBrandExample);
		return new PageToel<TbBrand>(bran.getTotal(), bran.getResult());
	}


	@Override
	public int insertBran(TbBrand brand) {
		int inTran=tbBrandMapper.insertSelective(brand);
		return inTran;
	}


	@Override
	public TbBrand updataSelect(long id) {
		return tbBrandMapper.selectByPrimaryKey(id);
	}


	@Override
	public int upDataBran(TbBrand tbBrand) {
		return tbBrandMapper.updateByPrimaryKey(tbBrand);
	}


	@Override
	public void deleteBran(long[] ids) {
		for(long id: ids) {
			tbBrandMapper.deleteByPrimaryKey(id);
		}
		
	}

}
