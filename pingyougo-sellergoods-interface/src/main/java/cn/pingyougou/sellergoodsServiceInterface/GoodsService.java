package cn.pingyougou.sellergoodsServiceInterface;

import java.util.List;
import java.util.Map;

import cn.pinyougou.pojo.TbGoods;
import cn.pinyougou.pojo.TbItemCat;
import cn.pinyougou.pojo.TbSpecificationOption;
import cn.pinyougou.pojo.TbTypeTemplate;
import entryPingYouGou.GoodsShopInsert;
import entryPingYouGou.PageToel;

public interface GoodsService {

	void insertShop(GoodsShopInsert goodsShopInsert);

	List<TbItemCat> selectItemcat(long id);

	TbTypeTemplate selectTypeTemplate(long id);

	List<Map> selectItemList(Long typeId);

	PageToel<TbGoods> selectGoodsList(int pageTole, int pageSize, TbGoods goods);

	void delgoodsList(Long[] longs);

	List<TbSpecificationOption> selectSpecificationOption();

}
