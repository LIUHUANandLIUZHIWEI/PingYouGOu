app.service('indexContentCatService',function($http){
//查询首页轮播广告
    this.ContentCatList=function(id){
        return $http.get('indexContent/ContentCategoryList.do?id='+id);
    }

})