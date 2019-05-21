app.controller('goodsEditController',function($scope,goodsEditService,$location){
	
	$scope.goodsEditAndDesc={'goodsEdit':{},'goodsDesc':{},'items':{}};
//添加商品信息
	$scope.shopGoodsOne=function(){
        $scope.goodsEditAndDesc.goodsDesc.introduction=editor.html();
        $scope.goodsEditAndDesc.items=$scope.itemList;
        $scope.goodsEditAndDesc.goodsDesc.specificationItems=$scope.TextOptionName;
        alert( $scope.goodsEditAndDesc.goodsEdit.id)
            goodsEditService.shopGoodsOne($scope.goodsEditAndDesc).success(function(response){
            alert(response.success);
             $scope.goodsEditAndDesc={};
            history.go(0);
		})
        
		
	}  
	//图片集合
	//点击新建
	$scope.news=function(){
		$scope.image_entity={};
	}
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


   
    $scope.entry_Bran=[{'category1IdList':{}},{'category2IdList':{}},{'category3IdList':{}}];
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
            //刷新所有数据
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

        $scope.goodsItem=[{'text':'','option':{}}]
        $scope.goodsEditAndDesc.goodsEdit.IsEnableSpec=0;
    //查询 模板
    $scope.$watch('goodsEditAndDesc.goodsEdit.category3Id',function(newValue,oldValue){
         if(newValue==null){
               return false;
           }
            $scope.typeTemlateList={'spectificationOption':{},'bran':{}};
         
        //查询品牌 和 扩展内容
        goodsEditService.typeTemlate(newValue).success(function(response){
                $scope.goodsEditAndDesc.goodsEdit.typeTemplateId=response.id;
                    if(!bool){
                    $scope.goodsEditAndDesc.goodsDesc.customAttributeItems=JSON.parse(response.customAttributeItems);
                    }
                
                $scope.typeTemlateList.bran=JSON.parse(response.brandIds);
                 //根据 模板id查询规格id和 在通过规格id查询 和规格多对多的表
                 goodsEditService.spectificationOption(response.id).success(function(response){
                    $scope.goodsItem=response;
                })          
            }); 


            //循环 得到规格数据列表
            $scope.forItem=function(text,optionName,$event){
                var booler=true;
                var list=$scope.TextOptionName;
                   for(var i=0;i<list.length;i++){
                        if(list[i].attributeName==text){
                            if($event.target.checked){
                                 list[i].attributeValue.push(optionName);
                            }else{
                                var index=list[i].attributeValue.indexOf(optionName);
                                 list[i].attributeValue.splice(index,1);
                                 if(list[i].attributeValue.length<1){
                                        list.splice(i,1);
                                 }
                            }
                              booler=false; 
                        }
                   }
                   if(booler){
                   list.push({'attributeName':text,'attributeValue':[optionName]});
                   }
                   alert(JSON.stringify(list))
                   $scope.itemList=[{'spec':{},'price':'9','num':9999,'status':0,'idDefult':0}];
                   for(var sum=0;sum<list.length;sum++){
                     $scope.itemList=ItemList($scope.itemList,list[sum].attributeName,list[sum].attributeValue);
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


    //修改
    var bool=false;
    $scope.updateIf=function(){
         $scope.initEntry('0');
         bool= $location.search()['goodsid']!=undefined;
        if(bool){
                $scope.updateSelectList($location.search()['goodsid']);
        }else{
                 $scope.itemList=[{'spec':{},'price':'9','num':9999,'status':0,'idDefult':0}];
                 $scope.TextOptionName=[];
        }
    }
    $scope.updateSelectList=function(id){
            goodsEditService.updateSelectList(id).success(function(response){
                    if(response==null){
                        alert("非法操作");
                        location.href="goods.html";
                        return false;
                    }
                   //分类
                    $scope.goodsEditAndDesc.goodsEdit=response.goodsEdit;
                    //商品介绍
                    $scope.goodsEditAndDesc.goodsDesc=response.goodsDesc;
                    editor.html(response.goodsDesc.introduction);
                    //图片
                    $scope.goodsEditAndDesc.goodsDesc.itemImages=JSON.parse(response.goodsDesc.itemImages);
                    //扩展属性
                    $scope.goodsEditAndDesc.goodsDesc.customAttributeItems= JSON.parse(response.goodsDesc.customAttributeItems);
                      //是否启用规格  
                    $scope.goodsEditAndDesc.goodsEdit.IsEnableSpec=response.goodsEdit.isEnableSpec;
                       //属性
                    $scope.TextOptionName=JSON.parse(response.goodsDesc.specificationItems);
                        $scope.itemList=[{'spec':{},'price':'9','num':9999,'status':0,'idDefult':0}];
                        for(var i=0;i<response.items.length;i++){
                            var newsa={'spec':JSON.parse(response.items[i].spec),'price':response.items[i].price,'num':response.items[i].num,'status':response.items[i].status,'isDefault':response.items[i].isDefault};  
                              $scope.itemList.push(newsa);
                        }
                        alert(JSON.stringify($scope.TextOptionName));
                        alert(JSON.stringify($scope.itemList))
                    })   

        }
        //是否启用规格 
        $scope.checkedOne=function(){
            if($scope.goodsEditAndDesc.goodsEdit.IsEnableSpec==1){
                        return true;
            }
            return false;
        }
        //规格
           $scope.checkedTwo=function(text,name){
             var  tion= $scope.TextOptionName;
             if(tion==null){
                 return false;
             }
                for(var i=0;i<tion.length;i++){
                    if(tion[i].attributeName==text) {
                        for(var j=0;j<tion[i].attributeValue.length;j++){
                                if(tion[i].attributeValue[j]==name){
                                            return true;
                                }
                        }
                    }
                }
            return false;;
        }
        //是否启用
           $scope.moren=function(){
           
            return true;
        }
        //是否默认
           $scope.qiyong=function(){
            return true;
        }
});