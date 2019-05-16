 app.controller('indexController',function($scope,$http){
    $scope.user="";
 $scope.userName=function(){
     $http.post('../sellerOne/userName.do').success(function(response){
        $scope.user=response;
        })
    }

 })
 
