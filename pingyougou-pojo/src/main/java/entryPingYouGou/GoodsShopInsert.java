package entryPingYouGou;

import java.io.Serializable;
import java.util.List;

import cn.pinyougou.pojo.TbGoods;
import cn.pinyougou.pojo.TbGoodsDesc;
import cn.pinyougou.pojo.TbItem;

public class GoodsShopInsert implements Serializable {
	private TbGoods goodsEdit;
	private TbGoodsDesc goodsDesc;
	private List<TbItem> items;
	
	public List<TbItem> getItems() {
		return items;
	}
	public void setItems(List<TbItem> items) {
		this.items = items;
	}
	public TbGoods getGoodsEdit() {
		return goodsEdit;
	}
	public void setGoodsEdit(TbGoods goodsEdit) {
		this.goodsEdit = goodsEdit;
	}
	public TbGoodsDesc getGoodsDesc() {
		return goodsDesc;
	}
	public void setGoodsDesc(TbGoodsDesc goodsDesc) {
		this.goodsDesc = goodsDesc;
	}
	
}
