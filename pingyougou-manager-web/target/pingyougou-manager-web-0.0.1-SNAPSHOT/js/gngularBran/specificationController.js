app.controller('spController',function($scope,$controller,specificationService){

   				$controller('pingyougouController',{$scope:$scope});
   				$scope.spnate={};
   				
   				$scope.fanAll=function(pageTole,pageSize){
   					specificationService.fanAll(pageTole,pageSize,$scope.spnate).success(function(response){
   						$scope.list=response.list;
   						$scope.paginationConf.totalItems=response.pageTole;
   					})
   				}
   				
   				
					
					    $scope.insertSpcifi=function(){
                                    $scope.specificationPojo.option.push({});
                                }
   				$scope.refren=function(){
   					$scope.specificationPojo={'option':[],'spification':{}};
   				}
   				
   					
   					$scope.insert=function(id){
   					var urlName=specificationService.insertList($scope.specificationPojo);
   						if($scope.specificationPojo.spification.id!=null){
   								urlName=specificationService.updateSp($scope.specificationPojo);
   							}
   						urlName.success(function(response){
   							alert(response.success);
   							$scope.reloadList();		
   						})
   					};
   					
   			
   				$scope.updateInsert=function(id){
   					specificationService.updateInsert(id).success(function(response){
   							$scope.refren();
   							$scope.specificationPojo.spification=response.spification;
   							$scope.specificationPojo.option=response.option;
   							
   					})
   				};
   				
   			$scope.deleteSp=function(){
   				specificationService.deleteSp($scope.checkBox).success(function(response){
   							alert(response.success);
   							$scope.reloadList();
   							$scope.checkBox=[];
   					})
   			}
   		}); 