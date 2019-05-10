    		app.service('branService',function($http){
    				
	    			this.updataSelect=function(id){
	    				return $http.get('../bran/updataSelect.do?id='+id);
	    			};
	    			
	    			
	    			this.fanAll=function(pageNum,pageSize,searchEntity){
	    				return $http.post('../bran/selectBran.do?pageNum='+pageNum+'&pageSize='+pageSize,searchEntity);
	    					};
	    					
	    					
	    					this.deleteBran=function(checkBox){
	    						return $http.get('../bran/deleteBran.do?ids='+checkBox);
	    					};
	    					
	    					
	    					this.intsertBrean=function(brenEntry){
	    					return $http.post('../bran/intsertBrean.do',brenEntry);
	    					};
	    					
	    					this.updateBran=function(brenEntry){
	    					return $http.post('../bran/updateBran.do',brenEntry);
	    					};
    				});