app.controller('goodsController',function($scope,goodsService){

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
                                        $scope.lons=[];
									$scope.fanAll($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
								};
                                        $scope.goods={};
                                $scope.fanAll=function(pageNum,pageSize){
                                    goodsService.goodsSelectList(pageNum,pageSize,$scope.goods).success(function(response){
                                            $scope.list=response.list;
                                             $scope.paginationConf.totalItems =response.pageTole;
                                    })
                                }
                                 $scope.itemCat=[];
                                //所有品牌
                                $scope.itemCatList=function(){
                                    goodsService.itemCatList().success(function(response){
                                        for(var i=0;i<response.length;i++){
                                            var name=response[i].name;
                                            var id = response[i].id;
                                            $scope.itemCat[id]=name;
                                        }
                                    })
                                }
                                $scope.stat=['未审核','已审核','审核未通过','关闭'];

                                //修改状态 
                                    $scope.goodsStatus=function(ids){
                                         alert(JSON.stringify($scope.lons))
                                         alert(ids)
                                         goodsService.goodsStatus($scope.lons,ids).success(function(response){
                                            alert(response.success);
                                            $scope.reloadList();
                                         })   
                                    }

                                    $scope.lons=[];
                                //删除
                                $scope.goodsDel=function(){
                                    alert(JSON.stringify($scope.lons))
                                    goodsService.goodsDel($scope.lons).success(function(response){
                                            alert(response.success);
                                            $scope.reloadList();  
                                            
                                    })
                                }

                                $scope.checkbox=function($event,ids){
                                        if($event.target.checked){
                                            $scope.lons.push(ids);
                                        }else{
                                            var index=$scope.lons.indexOf(ids);
                                            $scope.lons.splice(index,1);
                                        }

                                }
                              });