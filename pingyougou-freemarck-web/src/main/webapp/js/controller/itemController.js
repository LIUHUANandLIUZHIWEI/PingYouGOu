app.controller('itemController',function($scope){
		$scope.Monamey={};
		$scope.secp={};
		$scope.select=function(key,value){
			$scope.secp[key]=value
			for(var k in skuList){
				if($scope.scption(skuList[k].spec,$scope.secp)){
					$scope.Monamey=JSON.parse(JSON.stringify(skuList[k]));
					return true;
				}
			}
			$scope.Monamey.price="---";
			$scope.Monamey.title="----";
				return false;
		}
		
		$scope.scption=function(spec1,spec2){
			for(var i in spec1){
				if(spec2[i]!=spec1[i]){
					return false;
				}
			}
			return true;
		}
		
		
		$scope.facks=function(key,value){
			if($scope.secp[key]==value){
				return true;
			}
			return false;
		}
		
		$scope.num=1;
		//商品 数量加减
		$scope.nums=function(str){
			$scope.num=$scope.num+str;
			if($scope.num<0){
				$scope.num=0;
			}
		}
		
		$scope.loadSku=function(){
			$scope.secp=JSON.parse(JSON.stringify(skuList[0].spec))
			$scope.Monamey= JSON.parse(JSON.stringify(skuList[0]));
			}
			
			$scope.gouwuche=function(){
				alert($scope.Monamey.id);
			}
})