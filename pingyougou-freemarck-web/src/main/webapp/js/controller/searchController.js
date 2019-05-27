app.controller('searchController',function($scope,searchService,$location){
    
      $scope.searchMap={"keywords":'',"category":'',"brand":'','specIds':{},'price':'','priceDesc':"",'newproduct':'','pageSize':'40','pageCurrent':'1'};
     //刷新数据
    $scope.fulh=function(){
        $scope.searchMap.pageCurrent='1';
        $scope.searchMap.pageSize='40';
    }
    $scope.init=function(){
        var name=$location.search()['name'];
        if(name!=undefined){
              $scope.searchMap['keywords']=name;
        }
    }
      //搜索
	$scope.search=function(){
		searchService.search($scope.searchMap).success(
			function(response){
                //alert(JSON.stringify(response));
                $scope.resultMap=response;
                    for(var i=0;i<$scope.resultMap.category.length;i++){
                                if($scope.searchMap.keywords.match($scope.resultMap.category[i])){
                                       $scope.resultMap.category=null;  
                                       break;       
                                }
                    }
                    for(var i=0;i<$scope.resultMap.brand.length;i++){
                            if($scope.searchMap.keywords.match($scope.resultMap.brand[i].text)){
                                $scope.resultMap.brand=null;
                                break;
                            }
                    }
                    //分页
                    $scope.pageToles(response.pageTole,response.pageNum,response.pageCurrent);
			}
		);		
    }
    //过滤
	$scope.guolu=function(key,value){
        if(key=="brand" || key=="category" || key=="price" || key=="newproduct" || key=="priceDesc"){
               $scope.searchMap[key]=value;  
        }else{
             $scope.searchMap.specIds[key]=value;
        }
        $scope.search();
      
    }
    //删除过滤
    $scope.delete=function(key,value){
         if(key=="brand"||key=="category" || key=="price" || key=="newproduct" || key=="priceDesc"){
               $scope.searchMap[key]="";  
        }else{
          delete  $scope.searchMap.specIds[key];
        }
         $scope.search();
    }
    //价格排序
        $scope.pricedesc=function(value){
                if(value=="DESC"){
                        return "降序";
                }else{
                    return "升序";
                }
        } 
    //分页 总页数pageTole 总记录数 pageNum 当前页pageCurrent
    //变量固
    $scope.pageToles=function(pageTole,pageNum,pageCurrent){
        $scope.pageArray=[];
        var off=pageCurrent;
        var row=5;
        if(pageTole<=5){
            off=1;
            row=pageTole;
        } else if(pageCurrent+2>pageTole){
            off=pageTole-4;
            row=pageTole;
        }else if(off-2>0){
            off=off-2;
            row=off+4;
        }else{
                off=1;
                row=5;
        }
       
        for(var i=off;i<=row;i++){
             $scope.pageArray.push(i);
        }

    }
    $scope.pagefunctionC=function(value){
        if(value>$scope.resultMap.pageTole){
            $scope.searchMap.pageCurrent=$scope.resultMap.pageTole;
        }
        if(value>0){
            $scope.searchMap.pageCurrent=1;
        }
        $scope.searchMap.pageCurrent=value;
        $scope.search();
    }

   
    })