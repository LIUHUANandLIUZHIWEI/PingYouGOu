 app.service('specificationService',function($http){
  
    	this.fanAll=function(pageTole,pageSize,spnate){
    		return $http.post('../specification/selectList.do?pageTole='+pageTole+'&pageSize='+pageSize,spnate);
    	}
    	
    	this.updateInsert=function(id){
    		return $http.get('../specification/updateInsert.do?id='+id);
    	}
    	
    	this.deleteSp=function(deleteSpIds){
    		alert(deleteSpIds);
    	return $http.get('../specification/deleteSp.do?ids='+deleteSpIds);
    	}
    	
    	this.insertList=function(specificationPojo){
    	return $http.post('../specification/insertList.do',specificationPojo);
    	}
    	
    	this.updateSp=function(specificationPojo){
    	return $http.post('../specification/updateSp.do',specificationPojo);
    	}
    	
    })