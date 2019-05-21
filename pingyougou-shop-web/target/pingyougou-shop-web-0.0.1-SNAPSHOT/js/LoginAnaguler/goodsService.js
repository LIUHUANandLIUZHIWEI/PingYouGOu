app.service('goodsService',function($http){
    //查询所有数据
    this.fanAll=function(pageTole,pageSize,goodsEienAll){
            return $http.post('../goodsEditAndDesc/selectAllList.do?pageTole='+pageTole+'&pageSize='+pageSize,goodsEienAll); 
        }
    //删除数据
    this.delgoods=function(longs){
        return $http.get('../goodsEditAndDesc/deleteGoodsAll.do?longs='+longs);
    }
    //查询所有分类
    this.selectItemCat=function(){
        return $http.get('../goodsEditAndDesc/selectItemCat.do');
    }

});