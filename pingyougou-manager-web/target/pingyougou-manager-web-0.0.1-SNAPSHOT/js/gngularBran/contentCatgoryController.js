app.controller('contentCatgoryController',function($scope,$controller,contentCatgoryService){
    $controller('pingyougouController',{$scope:$scope});
//广告分类管理
            $scope.longs=[];
        //删除
        $scope.contentCatDel=function(){
            contentCatgoryService.contentCatDel($scope.longs).success(function(response){
                    alert(response.success) ;
                    $scope.longs=[];
                    $scope.reloadList();                  
            })
        }

        $scope.checkBox=function($event,id){
                if($event.target.checked){
                    $scope.longs.push(id);
                }else{
                    var index=$scope.longs.indexOf(id);
                    $scope.longs.splice(index,1);
                }
        }
        $scope.contentStr='d';

        //查找所有
            $scope.fanAll=function(pageNum,pageSize){
                contentCatgoryService.contentCatList(pageNum,pageSize).success(function(response){
                    $scope.ContenCategoryList=response.list;
                    $scope.paginationConf.totalItems=response.pageTole;
                })
            }
            $scope.ContenCategoryOne={};
        //根据id查询一个
            $scope.contentCatOne=function(id){
                $scope.ContenCategoryOne={};
                    contentCatgoryService.contentCatOne(id).success(function(response){
                        $scope.ContenCategoryOne=response;
                       $scope.reloadList();
                        
                    })
            }   
        //修改和保存
             $scope.contentCatSave=function(){
                 contentCatgoryService.contentCatSave($scope.ContenCategoryOne).success(function(response){
                        alert(response.success);
                        $scope.reloadList();
                 })
             }   
 
})
