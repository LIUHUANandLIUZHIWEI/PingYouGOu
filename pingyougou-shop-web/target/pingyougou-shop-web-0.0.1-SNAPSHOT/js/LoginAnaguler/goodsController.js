app.controller('goodsController',function($scope,goodsService){
    //分页插件
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
                                
        //查询所有
        $scope.fanAll=function(pageTole,pageSize){
            goodsService.fanAll(pageTole,pageSize,$scope.goodsAll).success(function(response){
                        $scope.goodsList=response.list;
                        $scope.totalItems=response.pageTole;
                        
            })
        }
        $scope.status=['未审核','已审核','审核未通过','关闭'];
        $scope.specificationOption=[];
        //缓存分类 
            $scope.selectSpecificationOption=function(){
                goodsService.selectSpecificationOption().success(function(){
                          for(var i=0;i<response.length;i++){
                            $scope.selectSpecificationOption[responseid]=response.optionName();
                       }
                })
            }
            //修改页面跳转
            $scope.localhost=function(goodsid){
                if(goodsid!=null){
                     window.location.href='goods_edit.html#?goodis='+goodsid;
                }else{
                      window.location.href='goods_edit.html';
                }
            }
            $scope.longs=[];
            $scope.dels=function($event,id){
                    if($event.target.checked){
                            $scope.longs.push(id);
                    }else{
                        var index=$scope.longs.indexOf(id);
                        $scope.longs.splice(index,1);
                    }
            }
            //删除
            $scope.del=function(){
                    goodsService.del($scope.longs).success(function(response){
                            alert(response.success);
                    });
            }
        });