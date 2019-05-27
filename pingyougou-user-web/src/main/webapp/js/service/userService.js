app.service('userService',function($http){
    //短信发送
    this.gainSms=function(phone){
       return  $http.get('../user/gainSms.do?phone='+phone);
    }

    //注册
    this.login=function(smsCode,user){
        return $http.post('../user/login.do?smsCode='+smsCode,user);
    }
})