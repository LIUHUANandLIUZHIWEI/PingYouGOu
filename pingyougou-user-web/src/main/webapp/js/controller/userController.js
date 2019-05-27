app.controller('userController',function($scope,userService,$interval){

    //注册
    $scope.login=function(){
      if($scope.checkeds==1){

      }else{
          alert("请同意协议")
          return false;
      }

    }

    //发送短息
    $scope.gainSms=function(){
        if(!($scope.user.password==$scope.password)){
                alert("两次密码不正确");
                return false;
        }
        if($scope.user.phone!=null&&$scope.user.phone!=''){
                userService.gainSms($scope.user.phone).success(function(response){
                        alert(response.success);
         })
        }else{
            alert("手机号不能为空");
        }
    }

    //注册
    $scope.login=function(){
          if(!$scope.user.password==$scope.password){
                alert("两次密码不正确");
                return false;
        }
        if($scope.user.phone==null&&$scope.user.phone==""){
            alert("手机号不能为空");
                return false;
        }
          userService.login($scope.smsCode,$scope.user).success(function(response){
            alert(response.success);
            if(response.judge){
                window.location.href="www.baidu.com";
            }
          })  
    }

    //设置短信超时
$scope.description="获取验证码";
    var dsecond=59;
    var timerHandler;
    $scope.timeOut=function(){
        timeHandler=$interval(function(){
            if(dsecond<=0){
                   $interval.cancel(timerHandler);    //当执行的时间59s,结束时，取消定时器 ，cancle方法取消   
                    dsecond=59;
                    $scope.description="获取验证码";
                     $scope.canClick=false;    //因为 ng-disabled属于布尔值，设置按钮可以点击，可点击发送
                }else{
                        $scope.description=dsecond+"s后重发";
                        dsecond--;
                        $scope.canClick=true;
                }
        },1000)//每一秒执行一次$interval定时器方法
    };
})