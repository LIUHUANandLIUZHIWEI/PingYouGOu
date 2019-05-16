app.service('longinService',function($http){

    this.loginName=function(){
        return $http.post('../loginNameSeller/user.do');
        
    }

    this.inserSeller=function(seller){
        return $http.post('../loginNameSeller/inserSeller.do',seller);
    }
})