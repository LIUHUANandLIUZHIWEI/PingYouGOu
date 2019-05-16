app.controller('goodsEditController',function($scope,goodsEditService){
	
	$scope.goodsEditAndDesc={'goodsEdit':{},'goodsDesc':{},'items':{}};
	//添加商品信息
	$scope.shopGoodsOne=function(){
        $scope.goodsEditAndDesc.goodsDesc.introduction=editor.html();
        $scope.goodsEditAndDesc.items=$scope.itemList;
        $scope.goodsEditAndDesc.goodsDesc.specificationItems=$scope.TextOptionName;
		goodsEditService.shopGoodsOne($scope.goodsEditAndDesc).success(function(response){
			alert(response.success);
			history.go(0);
		})
	}  
	//图片集合
	//点击新建
	$scope.news=function(){
		$scope.image_entity={};
	}
	$scope.image_entity={};
	//上传
	$scope.imageOne=function(){
		goodsEditService.imageOn().success(function(response){
			if(response.judge){
				$scope.image_entity.url=response.success;
			}else{
				alert(response.success);
			}
		}).error(function(){
			alert("上传发生错误");
		})
	}
	$scope.goodsEditAndDesc.goodsDesc.itemImages=[{}];
	//保存图片及颜色
	$scope.saveImage=function(){
		$scope.goodsEditAndDesc.goodsDesc.itemImages.push($scope.image_entity);
	}
	//删除图片
	$scope.deleteImage=function(index){
		$scope.goodsEditAndDesc.goodsDesc.itemImages.splice(index,1);
    }
   
    $scope.entry_Bran=[{'category1IdList':{}},{'category2IdList':{}},{'category2IdList':{}}];
    //查询一级分类
        $scope.initEntry=function(id){
             if(id==null){
               return false;
           }
            goodsEditService.initEntry(id).success(function(response){
                $scope.entry_Bran.category1IdList=response;
            })
        }
        
    //查询二级分类
        $scope.$watch('goodsEditAndDesc.goodsEdit.category1Id',function(newValue,oldValue){
           if(newValue==null){
               return false;
           }
            goodsEditService.initEntry(newValue).success(function(response){
                $scope.entry_Bran.category2IdList=response;
            })

        })
    //查询三级分类
    $scope.$watch('goodsEditAndDesc.goodsEdit.category2Id',function(newValue,oldValue){
         if(newValue==null){
               return false;
           }   
        goodsEditService.initEntry(newValue).success(function(response){
                $scope.entry_Bran.category3IdList=response;
            })
    })
        $scope.goodsItem=[{'id':'','text':'','option':{}}]
    //查询 模板

    $scope.$watch('goodsEditAndDesc.goodsEdit.category3Id',function(newValue,oldValue){
         if(newValue==null){
               return false;
           }
            $scope.typeTemlateList={'spectificationOption':{},'bran':{}};
            $scope.goodsEditAndDesc.goodsEdit.IsEnableSpec=0;
        //查询品牌 和 扩展内容
        goodsEditService.typeTemlate(newValue).success(function(response){
                $scope.goodsEditAndDesc.goodsEdit.typeTemplateId=response.id;
                $scope.goodsEditAndDesc.goodsDesc.customAttributeItems=JSON.parse(response.customAttributeItems);
                $scope.typeTemlateList.bran=JSON.parse(response.brandIds);
         //根据 模板id查询规格id和 在通过规格id查询 和规格多对多的表
                 goodsEditService.spectificationOption(response.id).success(function(response){
                    $scope.goodsItem=response;
                })          
            }); 
            $scope.TextOptionName=[];
            $scope.itemList=[{'spec':{},'price':'9','num':9999,'status':0,'idDefult':0}];
            //循环 得到规格数据列表
            $scope.forItem=function(text,optionName,$event){
                var booler=true;
                var list=$scope.TextOptionName;
                   for(var i=0;i<list.length;i++){
                        if(list[i]['attributeName']==text){
                            if($event.target.checked){
                                 list[i]['attributeValue'].push(optionName);
                            }else{
                                var index=list[i]['attributeValue'].indexOf(optionName);
                                 list[i]['attributeValue'].splice(index,1);
                                 if(list[i]['attributeValue'].length<1){
                                        list.splice(i,1);
                                 }
                                 
                            } 
                               booler=false;
                        }
                     
                   }
                   if(booler){
                   list.push({'attributeName':text,'attributeValue':[optionName]});
                   }
                    $scope.itemList=[{'spec':{},'price':'9','Ku':9999,'status':0,'idDefult':0}];
                   for(var sum=0;sum<list.length;sum++){
                 $scope.itemList=ItemList($scope.itemList,list[sum]['attributeName'],list[sum]['attributeValue']);
                   }
                 

                 }
            
                 //规格列表 不使用
                ItemList=function(list,Name,ValueArray){
                    var newList=[];
                    for(var i=0;i<list.length;i++){
                            var old=list[i];
                            for(var j=0;j<ValueArray.length;j++){
                                    var news=JSON.parse(JSON.stringify(old));
                                    news.spec[Name]=ValueArray[j];
                                    newList.push(news);
                            }
                    }
                        return newList;
                }
        
        
                    
    })
    
   
});