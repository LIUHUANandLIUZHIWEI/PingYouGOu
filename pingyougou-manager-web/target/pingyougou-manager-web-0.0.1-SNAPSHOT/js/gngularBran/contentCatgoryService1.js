app.service('contentCatgoryService',function($http){
   //广告分类管理
   

	//删除
        this.contentCatDel=function(longs){
            return $http.get('../contentCaregory/contentCatDel.do?longs='+longs);
        }
    //查找所有
     this.contentCatList=function(pageNum,pageSize){
            return $http.get('../contentCaregory/contentCatList.do?pageNum='+pageNum+'&pageSize='+pageSize);
        }
    
    //根据id查询一个
         this.contentCatOne=function(id){
            return $http.get('../contentCaregory/contentCatOne.do?id='+id);
        }
    //修改和保存
     this.contentCatSave=function(controll){
            return $http.post('../contentCaregory/contentCatSave.do',controll);
        }


//广告管理
	

//广告管理
	
	//删除
        this.contentDel=function(longs){
            return $http.get('../content/contentDel.do?longs='+longs);
        }
    //查找所有
     this.contentList=function(pageNum,pageSize){
            return $http.get('../content/contentList.do?pageNum='+pageNum+'&pageSize='+pageSize);
        }
    
    //根据id查询一个
         this.contentOne=function(id){
            return $http.get('../content/contentCatOne.do?id='+id);
        }
    //修改和保存
     this.contentSave=function(controll){
            return $http.post('../content/contentSave.do',controll);
        }
        //上传图片
        this.contentImage=function(){
            var fordate=new FormData();
            fordate.append('file',file.files[0]);
            return $http({
                    method:'post',
                    url:'../content/contentImage.do',
                    data:fordate,
                    headers:{"Content-Type":undefined},
                    transformRequest: angular.identity
            });
        }

        //查询所有分类
        this.selectContentCat=function(){
            return $http.get('../content/selectContenCat.do');
        }
})