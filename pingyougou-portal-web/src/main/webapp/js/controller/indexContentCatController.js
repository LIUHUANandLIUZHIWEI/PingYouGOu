app.controller('indexContentService',function($scope,indexContentCatService){
    $scope.ContentCateglist=[{'luenbo':{}}];
    //查询首页轮播广告
    $scope.ContentCategoryList=function(id){
        indexContentCatService.ContentCatList(id).success(function(response){
            $scope.ContentCateglist.luenbo=response;
        })
    }

})