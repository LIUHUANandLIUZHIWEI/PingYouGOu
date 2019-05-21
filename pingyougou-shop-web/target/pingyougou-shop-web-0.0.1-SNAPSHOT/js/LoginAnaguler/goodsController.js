app.controller('goodsController',function($scope,goodsService){
    //��ҳ���
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
                                $scope.goodsAll={};
        //��ѯ����
        $scope.fanAll=function(pageTole,pageSize){
            goodsService.fanAll(pageTole,pageSize,$scope.goodsAll).success(function(response){
                $scope.goodsList=response.list;
                $scope.paginationConf.totalItems=response.pageTole;
                        
            })
        }
        $scope.status=['未审核','已审核','审核未通过','关闭'];
        $scope.sItemCat={};
        //�������
            $scope.selectItemCat=function(){
                goodsService.selectItemCat().success(function(response){
                          for(var i=0;i<response.length;i++){
                            $scope.sItemCat[response[i].id]=response[i].name;
                       }
                })
            }

            //�޸�ҳ����ת
            $scope.localhost=function(goodsid){
                if(goodsid!=null){
                     window.location.href='goods_edit.html#?goodsid='+goodsid;
                }else{
                      window.location.href='goods_edit.html';
                }
            }
            $scope.longs=[];
            $scope.dels=function($event,id){
                    if($event.target.checked){
                            $scope.longs.push(id);
                    }else{
                        var index=$scope.longs.indexOf(id);
                        $scope.longs.splice(index,1);
                    }
            }
            //ɾ��
            $scope.del=function(){
                if(!confirm('确定删除？')){
                    return false;
                }
                    goodsService.delgoods($scope.longs).success(function(response){
                            alert(response.success);
                            $scope.reloadList();
                    });
            }

        });