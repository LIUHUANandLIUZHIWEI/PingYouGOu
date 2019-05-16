app.controller('pingyougouController',function($scope){
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

                                
                            $scope.checkBox=[];
                                
							$scope.check=function($event,entryId){
										if($event.target.checked){
											$scope.checkBox.push(entryId);
										}else{
										var index=$scope.checkBox.indexOf(entryId);
										$scope.checkBox.splice(index,1);
                                        }

                                        $scope.del=function(index){
						                        $scope.specificationPojo.option.splice(index,1);
					                    }
									
							};  	
		

});