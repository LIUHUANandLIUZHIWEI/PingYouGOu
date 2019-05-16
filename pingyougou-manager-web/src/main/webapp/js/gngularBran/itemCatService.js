app.service('itemCatService',function($http){
        //查询所有分类
	this.fanAll=function(pageTole,pageSize,id){
		return $http.get('../itemCat/selectItemCatList.do?id='+id+'&pageaTole='+pageTole+'&pageSize='+pageSize);
	}

	//修改
	this.updateItemCat=function(tbItemCat){
		return $http.get('../itemCat/updateItemCat.do?id='+tbItemCat);
	}
	//删除
	this.deleteItemCat=function(lons){
		return $http.post('../itemCat/deleteItemCat.do?lo='+lons);
	}
	//新建
	this.insertItemCat=function(itemCat){
		return $http.post('../itemCat/insertItemCat.do',itemCat);
	}
	
	
})