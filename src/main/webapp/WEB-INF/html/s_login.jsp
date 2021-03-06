<%@ page pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<title>后台登录</title>
<link rel="shortcut icon" href="/static/images/icon/favicon.ico" type="image/x-icon" />
<meta name="author" content="DeathGhost" />
<link rel="stylesheet" type="text/css" href="static/css/style.css" />
<style>
body{height:100%;background:#16a085;overflow:hidden;}
canvas{z-index:-1;position:absolute;}
</style>
<script src="static/js/jquery.js"></script>
<script src="static/js/verificationNumbers.js"></script>
<script src="static/js/Particleground.js"></script>
<script>
$(document).ready(function() {
  //粒子背景特效
  $('body').particleground({
    dotColor: '#5cbdaa',
    lineColor: '#5cbdaa'
  });
  //验证码
  createCode();
  //测试提交，对接程序删除即可
  $(".submit_btn").click(function(){
	  	var userName = $.trim($("#userName").val());
	  	var password = $.trim($("#password").val());
	  	if(userName.length==0||password.length==0){
		  alert("账号和密码不能为空");
		  return false;
	  	}
	  	 var checkCode = $.trim($("#J_codetext").val());
	  	 if(checkCode.length==0){
	  		 alert("请输入验证码");
	  		 return false;
	  	 }
	  	 if(validate()==false){
	  		alert("验证码错误");
	  		 return false;
	  	 }
	  /* location.href="index.html"; */
		  $.ajax({
	          type: "POST",
	          contentType: "application/json",
	          url: "/supervisor/login",
	          data: JSON.stringify({"userName":$("#userName").val(),"password":$("#password").val()}),
	          dataType: "json",
	          success: function(data){
	                  if(data.msg=="200"){
	                	  window.location="/supervisor/index/"+data.id+"?token="+data.token;
	                  }
	                  else if(data.msg=="201"){
	                	  alert("账号密码不能为空");
	                  }
	                  else if(data.msg=="202"){
	                	  alert("账号或密码错误");
	                  }
	                  else if(data.msg=="203"){
	                	  alert("账号或密码错误");
	                  }
	                  else {
	                	  alert("登录失败");
	                  }
	          }
	      });
	  });
  	
  $('#J_codetext').bind('keypress',function(event){
      if(event.keyCode == "13")    
      {
    	var userName = $.trim($("#userName").val());
  	  	var password = $.trim($("#password").val());
  	  	if(userName.length==0||password.length==0){
  		  alert("账号和密码不能为空");
  		  return false;
  	  	}
  	  	 var checkCode = $.trim($("#J_codetext").val());
  	  	 if(checkCode.length==0){
  	  		 alert("请输入验证码");
  	  		 return false;
  	  	 }
  	  	 if(validate()==false){
  	  		alert("验证码错误");
  	  		 return false;
  	  	 }
  	  /* location.href="index.html"; */
  		  $.ajax({
  	          type: "POST",
  	          contentType: "application/json",
  	          url: "/supervisor/login",
  	          data: JSON.stringify({"userName":$("#userName").val(),"password":$("#password").val()}),
  	          dataType: "json",
  	          success: function(data){
  	                  if(data.msg=="200"){
  	                	  window.location="/supervisor/index/"+data.id+"?token="+data.token;
  	                  }
  	                  else if(data.msg=="201"){
  	                	  alert("账号密码不能为空");
  	                  }
  	                  else if(data.msg=="202"){
  	                	  alert("账号或密码错误");
  	                  }
  	                  else if(data.msg=="203"){
  	                	  alert("账号或密码错误");
  	                  }
  	                  else {
  	                	  alert("登录失败");
  	                  }
  	          }
  	      });
      }
  });
});
</script>
</head>
<body>
<dl class="admin_login">
 <dt>
  <strong>超级管理员后台管理系统</strong>
  <em>Management System</em>
 </dt>
 <dd class="user_icon">
  <input id="userName" type="text" placeholder="账号" class="login_txtbx"/>
 </dd>
 <dd class="pwd_icon">
  <input id="password" type="password" placeholder="密码" class="login_txtbx"/>
 </dd>
 <dd class="val_icon">
  <div class="checkcode">
    <input type="text" id="J_codetext" placeholder="验证码" maxlength="4" class="login_txtbx">
    <canvas class="J_codeimg" id="myCanvas" onclick="createCode()">对不起，您的浏览器不支持canvas，请下载最新版浏览器!</canvas>
  </div>
  <input type="button" value="刷新验证码" class="ver_btn" onClick="validate();">
 </dd>
 <dd>
  <input type="button" value="立即登陆" class="submit_btn"/>
 </dd>
 <dd>
  <p>© 社享网 版权所有</p>
  <p>18817912915</p>
 </dd>
</dl>
</body>
</html>
