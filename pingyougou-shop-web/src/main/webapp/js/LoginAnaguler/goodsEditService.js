app.service('goodsEditService',function($http){
    //
	this.shopGoodsOne=function(goods){
		return $http.post('../goodsEditAndDesc/insertShop.do',goods);
	}
	//文件上传
	this.imageOn=function(){
		var formdate=new FormData();
		formdate.append("file", file.files[0]);
		return $http({
			method:'post',
			url:"../imageOn.do",
			data:formdate,
			headers:{'Content-Type':undefined},
			transformRequest: angular.identity
		});
    }
    
     //查询一级分类
            this.initEntry=function(id){
                return $http.get('../goodsEditAndDesc/ItemCat.do?id='+id);
            }
        //查询模板
            this.typeTemlate=function(typeId){
                return $http.get('../goodsEditAndDesc/TypeTemplate.do?typeId='+typeId);
            }

            //根据 模板id查询规格id和 在通过规格id查询 和规格多对多的表
            this.spectificationOption=function(typeStr){
                return $http.get('../goodsEditAndDesc/spectificatonOption.do?typeStr='+typeStr);
            }
	
})