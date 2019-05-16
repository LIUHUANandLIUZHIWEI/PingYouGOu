app.service('sellerOneService',function($http){
    this.fanAll=function(pageTole,pageSize,sellerlike){
        return $http.post('../sellerOne/selerOneList.do?pageTole='+pageTole+'&pageSize='+pageSize,sellerlike);
    }

    this.sellerAll=function(sellerId){
        return $http.get('../sellerOne/sellerAll.do?sellerId='+sellerId);
    }
    this.updateStatus=function(sellAll,status){
        return $http.post('../sellerOne/updateStatus.do?status='+status,sellAll);
    }

})