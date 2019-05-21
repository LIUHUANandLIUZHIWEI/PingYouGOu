app.service('goodsService',function($http){
        //查询所有goods
        this.goodsSelectList=function(pageNum,pageSize,goods){
            return $http.post('../goods/goodsSelectList.do?pageNum='+pageNum+"&pageSize="+pageSize,goods);
        }

        //审核 关闭
        this.goodsStatus=function(longs,status){
             alert(JSON.stringify(longs));
              alert(status);
            return $http.get('../goods/goodsStatusList.do?longs='+longs+'&status='+status);
        }
        //所有品牌
        this.itemCatList=function(){
            return $http.get('../goods/goodsitemCatList.do');
        }
        //删除
        this.goodsDel=function(longs){
            alert(JSON.stringify(longs));
            return $http.get('../goods/goodsDel.do?longs='+longs);
        }
        
})