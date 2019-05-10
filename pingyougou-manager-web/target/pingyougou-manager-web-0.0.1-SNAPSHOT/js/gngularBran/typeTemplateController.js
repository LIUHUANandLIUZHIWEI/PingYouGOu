app.controller('typeTemplate',function($scope,$controller,typeTemplateService){
	
	    $controller('pingyougouController',{$scope:$scope});
		$scope.queryTypeTemplate={};
		$scope.list={};
		$scope.fanAll=function(pageTole,pageSize){
			typeTemplateService.fanAll(pageTole,pageSize,$scope.queryTypeTemplate).success(function(response){
                alert(JSON.stringify(response.list));
                $scope.list=response.list;
				$scope.paginationConf.totalItems=response.pageTole;
			})
		}
		
		//点击新建和修改 获取品牌表 和规格表查出数据
		$scope.BranList={data:[]};
		$scope.SpecificationList={data:[]};
		$scope.NewAndAlter=function(){
            $scope.flushType();
			$scope.BranList={data:[]};
			$scope.SpecificationList={data:[]};
			typeTemplateService.NewAndAlter().success(function(resoponse){
				  $scope.BranList={data:resoponse.bran};
                  $scope.SpecificationList={data:resoponse.specification};
                  	$scope.reloadList();	
			})
        };
        
       
		//刷新需要保存的数据
		
		//保存数据
		$scope.insertTypeTemp=function(){
            typeTemplateService.insertTypeTemp($scope.entity).success(function(response){
                 $scope.reloadList();
                $scope.flushType();
                 alert(response.success);
			})
        };
          

        //刷新
        $scope.flushType=function(){
            $scope.entity={'customAttributeItems':[],'specIds':[],'brandIds':[],'name':"",'id':""};

        }
                //添加扩展属性
        $scope.checkList=function(){
            $scope.entity.customAttributeItems.push({});
        };
        //删除 扩展属性 
            $scope.del=function(index){
                    $scope.entity.customAttributeItems.splice(index,1);
            };

            $scope.updateTypeTemplate=function(id){
               typeTemplateService.updateTypeTemplate(id).success(function(response){
                       $scope.flushType();
                        $scope.entity.name=response.name;
                        $scope.entity.customAttributeItems=JSON.parse(response.customAttributeItems);
                        $scope.entity.specIds=JSON.parse(response.specIds);
                        $scope.entity.brandIds=JSON.parse(response.brandIds);
                        $scope.entity.id=response.id;
                })
            }

            //删除
            $scope.deleteTypeTemplate=function(){
                typeTemplateService.deleteTypeTemplate($scope.checkBox).success(function(response){
                        $scope.reloadList();
                        alert(response.success);

                })

            }

            $scope.jsons=function(jsonStr){
                       var str=JSON.parse(jsonStr);
                       var value="";
                       for(var i=0;i<str.length;i++){
                            if(i==0){
                                value+=str[i].text;
                            }else{
                                value+=",  "+str[i].text;
                            }
                           
                       }
                       return value
            }
	})
