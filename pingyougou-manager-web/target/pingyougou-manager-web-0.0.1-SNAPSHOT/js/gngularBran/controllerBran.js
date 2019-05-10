app.controller('branController',function($scope,$controller,branService){
			$controller('pingyougouController',{$scope:$scope});

    			$scope.searchEntity={};
    			
    			$scope.fanAll=function(pageNum,pageSize){
    					branService.fanAll(pageNum,pageSize,$scope.searchEntity).success(function(response){
    						$scope.list=response.list;
    						$scope.paginationConf.totalItems=response.pageTole;
    					});
    			};					
							
								$scope.sava=function(){
									var branName=null;
								if($scope.brenEntry.id!=null){
										branName=branService.updateBran($scope.brenEntry);
								}else{
									branName=branService.intsertBrean($scope.brenEntry);
								}
									branName.success(function(response){
												if(response.judge){
													$scope.reloadList();
												}else{
													alert(response.success);
												}
									})
								};
								
								$scope.branEntryTwo=function(){
									$scope.brenEntry={};
								};
						
								
					    			$scope.updataSelect=function(id){
					    				branService.updataSelect(id).success(function(response){
					    						$scope.brenEntry=response;
					    				})
					    			};
					    			
					    			
					    		
							
							
							$scope.deleteBran=function(){
								if(confirm('Sure to delete?')){
								branService.deleteBran($scope.checkBox).success(function(response){
										if(response.judge){
										$scope.reloadList();
										}else{
										alert(response.success);
										}
								})
								}
								
							};
    		});
    		