app.controller('contentController',function($scope,contentCatgoryService,$controller){
                $scope.paginationConf = {
									 currentPage: 1,
									 totalItems: 10,
									 itemsPerPage: 10,
									  perPageOptions: [10, 20, 30, 40, 50],
									 onChange: function(){
									     $scope.reloadList();
									 }
									 };
    							$scope.reloadList=function(){
									$scope.fanAll($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
								};
                  $scope.contentlongs=[];
//广告管理
$scope.contentCheckBox=function($event,id){
                if($event.target.checked){
                    $scope.contentlongs.push(id);
                }else{
                    var index=$scope.longs.indexOf(id);
                    $scope.contentlongs.splice(index,1);
                }
        }
	
	//删除
        $scope.contentDel=function(){
             contentCatgoryService.contentDel($scope.contentlongs).success(function(response){
                     alert(response.success) ;
                            $scope.contentlongs=[];
                            $scope.reloadList();         
                })
        }
 $scope.contentList=[];
    //查找所有
     $scope.fanAll=function(pageNum,pageSize){
              contentCatgoryService.contentList(pageNum,pageSize).success(function(response){
                       $scope.contentList=response.list;
                        $scope.paginationConf.totalItems=response.pageTole;
                })
        }
        $scope.statu=['无效','有效'];
    $scope.contentOne={};
    //根据id查询一个
         $scope.contents=function(id){
             $scope.contentOne={};
             contentCatgoryService.contentOne(id).success(function(response){
                          $scope.contentOne=response;
                          $scope.reloadList();
                })
        }
    //修改和保存
     $scope.contentSave=function(){
             contentCatgoryService.contentSave($scope.contentOne).success(function(response){
                        alert(response.success);
                          $scope.reloadList();  
                })
        }

        $scope.contentImage=function(){
                contentCatgoryService.contentImage().success(function(response){
                        if(response.judge){
                              $scope.contentOne.pic=response.success;
                        }else{
                            alert(response.success)
                        }
                })
        }
        $scope.checkeds=function(){
            if($scope.contentOne!=null){
                if($scope.contentOne==1){
                    return true;
                }
            }
            return false;
        }

        $scope.contentCategoryList={};
    //查询所有分类
    $scope.contentCatgory=function(){
                $scope.contentCategoryList={};
           contentCatgoryService.selectContentCat().success(function(response){
                $scope.contentCategoryList=response;
           })
    }
    $scope.fluh=function(){
        $scope.contentOne={};
    }
})