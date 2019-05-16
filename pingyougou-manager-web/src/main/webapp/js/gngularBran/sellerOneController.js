app.controller('sellerOneController',function($scope,sellerOneService,$controller){
    $controller('pingyougouController',{$scope:$scope});
    $scope.sellerlike={};
        //查询所有
        $scope.fanAll=function(pageTole,pageSize){
                sellerOneService.fanAll(pageTole,pageSize,$scope.sellerlike).success(function(response){
                   
                    $scope.list=response.list;
                   $scope.paginationConf.totalItems=response.pageTole;
                });
        }
        //查看详情
        $scope.sellerAll=function(sellerId){
            $scope.seAll={};
                sellerOneService.sellerAll(sellerId).success(function(response){
                $scope.seAll=response;
                })
                //审核
            $scope.updateStatus=function(sellAll,status){
                sellerOneService.updateStatus(sellAll,status).success(function(response){
                    alert(response.success);
                })
            }
        }

    
        
})