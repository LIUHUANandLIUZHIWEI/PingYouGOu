 app.service('typeTemplateService',function($http){
        //查询所有功能
              this.fanAll=function(pageSize,pageTole,queryTypeTemplate){
                   
                    alert(pageTole);
                    alert(pageSize);
                  return $http.post('../typeTemplate/selectList.do?pageTole='+pageTole+'&pageSize='+pageSize,queryTypeTemplate);
              }  
            //新建功能
               this.NewAndAlter=function(){
                return $http.get('../typeTemplate/SelectBranSpType.do');
            }
            //添加功能和更新功能
            this.insertTypeTemp=function(entity){
                return $http.post('../typeTemplate/insertBranSpType.do',entity);
            }
        //删除
            this.deleteTypeTemplate=function(checkBox){
                return $http.post('../typeTemplate/deleteTypeTemplate.do?ids='+checkBox);
            }

            //获取
            this.updateTypeTemplate=function(id){
                return  $http.get('../typeTemplate/updateTypeTemplate.do?id='+id);
            }
    });