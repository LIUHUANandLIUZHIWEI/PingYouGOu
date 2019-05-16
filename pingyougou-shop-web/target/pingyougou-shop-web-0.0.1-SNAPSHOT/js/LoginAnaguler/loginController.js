app.controller('loginController',function($scope,longinService,$http){
    $scope.username="";
    $scope.loginName=function(){
        longinService.loginName().success(function(response){
                $scope.username=response;  
        })
    };

    //娉ㄥ唽鍔熻兘
    $scope.insertSeller=function(){
        if($scope.brankUsername==null){
                alert("你还没同意协议")
                
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