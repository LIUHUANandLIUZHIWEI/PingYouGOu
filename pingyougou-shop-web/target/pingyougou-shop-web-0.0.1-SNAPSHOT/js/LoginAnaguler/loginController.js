app.controller('loginController',function($scope,longinService,$http){
    $scope.username="";
    $scope.loginName=function(){
        longinService.loginName().success(function(response){
                $scope.username=response;  
        })
    };

    //注册功能
    $scope.insertSeller=function(){
        if($scope.brankUsername==null){
                alert("�㻹ûͬ��Э��")
                
                return false;
        }
            $http.post('../loginNameSeller/inserSeller.do',$scope.seller).success(function(response){
               
                if(response.judge){
                     location.href="shoplogin.html";
                }else{
                    alter(response.success);
                    location.href="register.html";
                }
                    
            })
    };

});