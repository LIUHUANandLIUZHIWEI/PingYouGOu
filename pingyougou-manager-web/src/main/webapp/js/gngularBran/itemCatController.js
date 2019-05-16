app.controller('itemCatController',function($scope,$controller,itemCatService){
	//分页
	$scope.paginationConf = {
			 currentPage: 1,
			 totalItems: 10,
			 itemsPerPage: 10,
			  perPageOptions: [10, 20, 30, 40, 50],
			 onChange: function(){
			     $scope.reloadList();
			 }
			 };
			//分页参数
			$scope.paraentId="0";
			$scope.ID=0;
			//上一级计数器
			
			$scope.reloadList=function(id){
				if(id!=null){
					$scope.paraentId=id;
				}
			$scope.fanAll($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage,$scope.paraentId);
		};
		
    //查询所有
    $scope.fanAll=function(pageTole,pageSize,id){
    	$scope.list={};
    	itemCatService.fanAll(pageTole,pageSize,id).success(function(response){
    				$scope.list=response.list;
    				$scope.paginationConf.totalItems=response.pageTole;
    	})
    }
    
    //面包屑
    $scope.entryOne=null;
    $scope.entryTwo=null;
    $scope.ID=0;
    
    $scope.SetID=function(Ids){
    	$scope.ID=Ids;
    }
    $scope.XiaId=function(){
    	$scope.ID=$scope.ID+1;
    }
    $scope.entityList=function(entyItemCat,id){
    	var ids=$scope.ID;
    	if(ids==0){
    		$scope.entryOne=null;
    	    $scope.entryTwo=null;
    	}
    	if(ids == 1){
    		$scope.entryTwo=null;
    		$scope.entryOne=entyItemCat;
    	}
    	if(ids==2){
    		$scope.entryTwo=entyItemCat;
    	}
    	$scope.reloadList(id)
    }
    $scope.itemCatOne={};
    //查询一个数据
    $scope.updateItemCat=function(id){
    	$scope.itemCatOne={};
    	itemCatService.updateItemCat(id).success(function(response){
    		$scope.itemCatOne=response;
    	})
    }
    //修改和保存
    $scope.save=function(itemCat){
    	if(itemCat.parentId==null){
    		if($scope.entryTwo==null){
    			if($scope.entryOne==null){
    				itemCat.parentId='0';
    			}else{
    				itemCat.parentId=$scope.entryOne.id;
    			}
    		}else{
    			itemCat.parentId=$scope.entryTwo.id;
    		}
    	}
    	alert(itemCat.name);
    	 $scope.itemCatOne={};
    	 itemCatService.insertItemCat(itemCat).success(function(response){
    			alert(response.success);
    			 $scope.reloadList(itemCat.parentId);
    	})
    }
    $scope.logids=[];
    $scope.checkBox=function($event,id){
    	if($event.target.checked){
    		$scope.logids.push(id);
    	}else{
    		var index=$scope.logids.indexOf(id);
    		$scope.logids.splice(index,1);
    	}
    }
    //删除
    $scope.deleteItemCat=function(){
    	if(confirm('确定删除它或以下的子分类？')){
        	itemCatService.deleteItemCat($scope.logids).success(function(response){
        		alert(response.success);
        		if($scope.entryTwo==null){
        			if($scope.entryOne==null){
        				$scope.reloadList('0');
        			}else{
        				$scope.reloadList($scope.entryOne.id);
        			}
        		}else{
        			$scope.reloadList($scope.entryTwo.id);
        		}
        		
        	})
    	}else{return false;}
    	
    }
     
});