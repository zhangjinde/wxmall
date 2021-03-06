<%@ page pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<title>超级管理员系统</title>
<link rel="shortcut icon" href="/static/images/icon/favicon.ico" type="image/x-icon" />
<meta name="author" content="DeathGhost" />
<link rel="stylesheet" type="text/css" href="static/css/style.css" />
<style type="text/css">
    .btm_btn{text-align: center;}
    .btm_btn .input_btn{display:inline-block;height:35px;border:none;background:none;padding:0 20px;}
    .btm_btn .trueBtn{background:#19a97b;color:white;border:1px #19a97b solid;border-radius:2px;}
</style>
<!--[if lt IE 9]>
<script src="static/js/html5.js"></script>
<![endif]-->
<script src="static/js/jquery.js"></script>
<script src="static/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="static/js/ajaxfileupload.js" type="text/javascript"></script>
<script src="static/js/input_constrain.js" type="text/javascript"></script>
<script>
	(function($){
		$(window).load(function(){
			
			$("a[rel='load-content']").click(function(e){
				e.preventDefault();
				var url=$(this).attr("href");
				$.get(url,function(data){
					$(".content .mCSB_container").append(data); //load new content inside .mCSB_container
					//scroll-to appended content 
					$(".content").mCustomScrollbar("scrollTo","h2:last");
				});
			});
			
			$(".content").delegate("a[href='top']","click",function(e){
				e.preventDefault();
				$(".content").mCustomScrollbar("scrollTo",$(this).attr("href"));
			});
			
		});
	})(jQuery);
</script>
</head>
<body>
<iframe name="mframe" src="/orderOff/hasNew/${id}/?token=${token}" frameborder="0" scrolling="no" width="100%" height="70px" onload="document.all['mframe'].style.height=mframe.document.body.scrollHeight"></iframe>

<!--aside nav-->
<aside class="lt_aside_nav content mCustomScrollbar">
 <h2>社享网</h2>
 <ul>
  <li>
   <dl>
    <dt>订单信息</dt>
    <dd><a href="/orderOn/s_queryall/${id}/1?token=${token}">所有未完成订单</a></dd>
    <dd><a href="/orderOff/s_queryall/${id}/1?token=${token}">所有已完成订单</a></dd>
    <dd><a href="/orderOff/s_query_refund/${id}/1?token=${token}">退款订单</a></dd>
   </dl>
  </li>
   <li>
   <dl>
    <dt>商品信息</dt>
    <!--当前链接则添加class:active-->
    <dd><a href="/product/s_products/${id}/1?token=${token}" class="active">商品库</a></dd>
    <dd><a href="/product/s_threhold/${id}?token=${token}">待补货商品</a></dd>
   </dl>
  </li>
  <li>
   <dl>
    <dt>会员管理</dt>
    <dd><a href="/user/s_queryall/${id}/1?token=${token}">会员中心</a></dd>
   </dl>
  </li>
  <li>
   <dl>
    <dt>区域管理</dt>
    <dd><a href="/area/s_queryall/${id}?token=${token}">区域设置</a></dd>
   </dl>
  </li>
  <li>
   <dl>
    <dt>账号管理</dt>
    <dd><a href="/vendor/s_queryall/${id}?token=${token}">账号管理</a></dd>
   </dl>
  </li>
  <li>
   <dl>
    <dt>礼券管理</dt>
    <dd><a href="/coupon/s_queryall/${id}?token=${token}">优惠券管理</a></dd>
   </dl>
   </li>
   <li>
   <dl>
    <dt>订单统计</dt>
    <dd><a href="/orderOff/s_querydealed/${id}?token=${token}">成交订单统计</a></dd>
    <dd><a href="/orderOff/s_queryreturned/${id}?token=${token}">退货订单统计</a></dd>
   </dl>
  </li>
  <li>
   <p class="btm_infor">© 社享网 版权所有</p>
  </li>
 </ul>
</aside>
<section class="rt_wrap content mCustomScrollbar">
 <div class="rt_content">
     <section>
        <!-- <h3 style="text-align:right;">欢迎您，某某管理员</h3> -->
        <hr/>
     </section>
	<!-- 瞬间消失的提示框 -->
     <section class="loading_area">
      <div class="loading_cont">
       <div class="loading_icon"><i></i><i></i><i></i><i></i><i></i></div>
       <div class="loading_txt"><mark id="tips">操作成功</mark></div>
      </div>
     </section>
     <!-- 瞬间消失的提示框 -->
     <!--弹出框效果-->
     <script>
     $(document).ready(function(){
		 //弹出文本性提示框
		 $(".popAdd").click(function(){
			 $(".pop_bg").fadeIn();
			 });
		 //弹出：确认按钮
		 $("#saveBtn").click(function(){
			 $(".pop_bg").fadeOut();
			 });
		 //弹出：取消或关闭按钮
		 $("#cancelBtn").click(function(){
			 $(".pop_bg").fadeOut();
			 });
		 });
     </script>
     <section class="pop_bg">
      <div class="pop_cont">
       <!--title-->
       <h3>添加分类</h3>
       <!--content-->
       <div class="pop_cont_input">
        <ul>
         <li>
          <span>分类名称</span>
          <input type="text" placeholder="如'水果'" class="textbox"/>
         </li>
         <li>
          <span class="ttl">排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序</span>
          <input type="text" placeholder="请填写整数，从大到小排序" class="textbox"/>
         </li>
        </ul>
       </div>
       <!--以pop_cont_text分界-->
       <div class="pop_cont_text">
        这里是文字性提示信息！
       </div>
       <!--bottom:operate->button-->
       <div class="btm_btn">
        <input type="button" value="保存" id="saveBtn" class="input_btn trueBtn"/>
        <input type="button" value="取消" id="cancelBtn" class="input_btn falseBtn"/>
       </div>
      </div>
     </section>
     <!--结束：弹出框效果-->
	
	  <script>
     $(document).ready(function(){
    	 //移除详情1的图片
         $("#rmdetail1").click(function(){
        	$("#uploadd1").attr('src',"");
        	$("#serverImgNamed1").val("");
        	$(this).css("display","none");
         });
		 $("#prosave").click(function(){
			 	var productName = $.trim($("#proname").val());
			 	var origin = $.trim($("#proorigin").val());
			 	var standard = $.trim($("#prostandard").val());
			 	var price = $.trim($("#proprice").val());
			 	var marketPrice = $.trim($("#promarketprice").val());
			 	var description = $.trim($("#prodescription").val());
			 	
			 	var coverSUrl = $("#serverImgNames").val();
			 	var coverBUrl = $("#serverImgNameb").val();
			 	var subdetailUrl = $("#serverImgNamed1").val();
			 	var detailUrl = $("#serverImgNamed2").val();
			 	
			 	if(productName == "" || standard=="" || price=="" || marketPrice==""){
			 		alert("产品名称、规格、价格、市场价不能为空");
			 		return false;
			 	}
			 	if(coverSUrl == "" || coverBUrl == "" || detailUrl==""){
			 		alert("商品正方形、长方形缩略图、详情2图片必须上传");
			 		return false;
			 	}
			 	$.ajax({
		    		  type: "POST",
		  	          contentType: "application/json",
		  	          url: "/product/snew/"+$("#loginUserId").val()+"/?token="+$("#loginToken").val(),
		  	          dataType: "json",
		  	          data: JSON.stringify({"productName":productName,"origin":origin,"standard":standard,"price":price,
		  	        		"marketPrice":marketPrice,"description":description,
		  	        		"coverSUrl":coverSUrl,"coverBUrl":coverBUrl,"subdetailUrl":subdetailUrl,"detailUrl":detailUrl}),
		  	          success: function(data){
		  	        	  if(data.msg=="200"){
		  	        		alert("商品添加成功");
		  	        		  window.location.reload();
		  	        	  }
		  	        	  else if(data.msg=="201"){
		  	        		  alert("商品添加失败");
		  	        	  }else if(data.msg=="401"){
		  	        	     alert("需要重新登录");
		  	        	  }
		  	          }
		    	 	});
			 });
		 });
     </script>
     
     <section>
      <ul class="ulColumn2" style="padding-left: 22%;">
       <li>
        <span class="item_name" style="width:120px;">商品名称：</span>
        <input type="text" id="proname" class=" textbox_295 length_input_20" placeholder="如'海南小番茄'"/>
       </li>
       <li>
        <span class="item_name" style="width:120px;">原产地：</span>
        <input type="text" id="proorigin" class=" textbox_295 length_input_20" placeholder="如'海南'"/>
       </li>
       <li>
        <span class="item_name" style="width:120px;">商品规格：</span>
        <input type="text" id="prostandard" class=" textbox_295 length_input_30" placeholder="如'一份250克','一份足2斤'"/>
       </li>
       <li>
        <span class="item_name" style="width:120px;">售价(￥)：</span>
        <input type="text" id="proprice" class=" textbox_295 price_input" placeholder=""/>
       </li>
       <li>
        <span class="item_name" style="width:120px;">市场价(￥)：</span>
        <input type="text" id="promarketprice" class=" textbox_295 price_input" placeholder=""/>
       </li>
       <li>
        <span class="item_name" style="width:120px;">商品简介：</span>
        <input type="text" id="prodescription" class=" textbox_295 length_input_20" placeholder="如'香甜可口，消暑止渴'"/>
       </li>
       <li>
        <span class="item_name" style="width:120px;">缩略图：</span>
        <span><img alt="(240 x 240)" id="uploads" src="" style="height:100px;width:100px;cursor:pointer"/></span>
        <span><img alt="(480 x 240)" id="uploadb" src="" style="height:100px;width:200px;cursor:pointer"/></span>
		<div id="fileDivs">
		     <input id="fileToUploads" style="display: none" type="file" name="upfiles">
		</div>
		<input type="hidden" id="serverImgNames"/>
		<div id="fileDivb">
		     <input id="fileToUploadb" style="display: none" type="file" name="upfileb">
		</div>
		<input type="hidden" id="serverImgNameb"/>
       </li>
					<li><span class="item_name" style="width: 120px;">商品详情1<button class="linkStyle" style="padding:0;display:none" id="rmdetail1">(X)</button>：</span>
						<img alt="详情1" id="uploadd1" src=""
						style="height: 170px; width: 305px; cursor: pointer">
						<div id="fileDivd1">
							<input id="fileToUploadd1" style="display: none" type="file"
								name="upfiled1">
						</div> <input type="hidden" id="serverImgNamed1" />
					</li>
					<li>
						<span class="item_name" style="width: 120px;">商品详情2：</span>
						<img alt="详情2" id="uploadd2" src=""
						style="height: 300px; width: 305px; cursor: pointer">
						<div id="fileDivd2">
							<input id="fileToUploadd2" style="display: none" type="file"
								name="upfiled2">
						</div> <input type="hidden" id="serverImgNamed2" />
					</li>
		<li>
       <li>
        <span class="item_name" style="width:120px;"></span>
        <input type="button" id="prosave" value="保存" class="link_btn"/>
       </li>
      </ul>
     </section>

    <!--结束：以下内容则可删除，仅为素材引用参考-->
 </div>
</section>

 <!-- 添加s缩略图 -->
    <script>
     $(document).ready(function(){
    	//获取图片尺寸，并验证是否满足尺寸大小
    	 function checkImageSize(url,callback){
    			var img = new Image();
    			img.src = url;
    			// 如果图片被缓存，则直接返回缓存数据
    			if(img.complete){
    			    callback(img.width, img.height);
    			}else{
    				// 完全加载完毕的事件
    			    img.onload = function(){
    					callback(img.width, img.height);
    			    }
    		    }
    	  }
     $("#uploads").on('click', function() {  
         $('#fileToUploads').click();  
     });
     //这里必须绑定到file的父元素上，否则change事件只会触发一次，即在页面不刷新的情况下，只能上传一次图片，原因http://blog.csdn.net/wc0077/article/details/42065193
     $('#fileDivs').on('change',function() {  
         $.ajaxFileUpload({  
             url:'/product/uploadImgs',  
             secureuri:false,  
             fileElementId:'fileToUploads',//file标签的id  
             dataType: 'json',//返回数据的类型  
             //data:{name:'logan'},//一同上传的数据  
             success: function (data, status) {  
                 //把图片替换  
                 /* var obj = jQuery.parseJSON(data);  
                 $("#upload").attr("src", "../image/"+obj.fileName);   */
                 if(data.msg=="200"){
                     //alert("图片可用");
                     $("#serverImgNames").val(data.imgName);
                     $("#uploads").attr("src", "/static/upload/"+data.imgName);
                     var imgSrc = $("#uploads").attr("src");
                     var require = {wid:240,hei:240};
                     checkImageSize(imgSrc,function(w,h){
                 		if(w!=require.wid || h!=require.hei){
                 			alert("图片尺寸不符合! 请上传"+require.wid+"x"+require.hei+"尺寸的图片");
                 			$("#serverImgNames").val("");
                 			$("#uploads").attr("src", "");
                 		}
                 	 });
                 }
                 else if(data.msg=="201"){
                	 alert("图片不符合");
                 }
                 if(typeof(data.error) != 'undefined') {  
                     if(data.error != '') {  
                         alert(data.error);  
                     } else {  
                         alert(data.msg);  
                     }  
                 }
              }, 
             error: function (data, status, e) {  
                 alert(e);  
             }  
         });  
     });
     });
   </script>
   <!-- 添加s缩略图 -->
   
   <!-- 添加b缩略图 -->
    <script>
     $(document).ready(function(){     
    	//获取图片尺寸，并验证是否满足尺寸大小
    	 function checkImageSize(url,callback){
    			var img = new Image();
    			img.src = url;
    			// 如果图片被缓存，则直接返回缓存数据
    			if(img.complete){
    			    callback(img.width, img.height);
    			}else{
    				// 完全加载完毕的事件
    			    img.onload = function(){
    					callback(img.width, img.height);
    			    }
    		    }
    	  }
     $("#uploadb").on('click', function() {  
         $('#fileToUploadb').click();  
     });
     //这里必须绑定到file的父元素上，否则change事件只会触发一次，即在页面不刷新的情况下，只能上传一次图片，原因http://blog.csdn.net/wc0077/article/details/42065193
     $('#fileDivb').on('change',function() {  
         $.ajaxFileUpload({  
             url:'/product/uploadImgb',  
             secureuri:false,  
             fileElementId:'fileToUploadb',//file标签的id  
             dataType: 'json',//返回数据的类型  
             //data:{name:'logan'},//一同上传的数据  
             success: function (data, status) {  
                 if(data.msg=="200"){
                     //alert("图片可用");
                     $("#serverImgNameb").val(data.imgName);
                     $("#uploadb").attr("src", "/static/upload/"+data.imgName);
                     var imgSrc = $("#uploadb").attr("src");
                     var require = {wid:480,hei:240};
                     checkImageSize(imgSrc,function(w,h){
                 		if(w!=require.wid || h!=require.hei){
                 			alert("图片尺寸不符合! 请上传"+require.wid+"x"+require.hei+"尺寸的图片");
                 			$("#serverImgNameb").val("");
                 			$("#uploadb").attr("src", "");
                 		}
                 	 });
                 }
                 else if(data.msg=="201"){
                	 alert("图片不符合");
                 }
                 if(typeof(data.error) != 'undefined') {  
                     if(data.error != '') {  
                         alert(data.error);  
                     } else {  
                         alert(data.msg);  
                     }  
                 }
              }, 
             error: function (data, status, e) {  
                 alert(e);  
             }  
         });  
     });
     });
   </script>
   <!-- 添加b缩略图 -->
   <!-- 添加详情1图 -->
    <script>
     $(document).ready(function(){     
     $("#uploadd1").on('click', function() {  
         $('#fileToUploadd1').click();  
     });
     //这里必须绑定到file的父元素上，否则change事件只会触发一次，即在页面不刷新的情况下，只能上传一次图片，原因http://blog.csdn.net/wc0077/article/details/42065193
     $('#fileDivd1').on('change',function() {  
         $.ajaxFileUpload({  
             url:'/product/uploadImgd1',  
             secureuri:false,  
             fileElementId:'fileToUploadd1',//file标签的id  
             dataType: 'json',//返回数据的类型  
             //data:{name:'logan'},//一同上传的数据  
             success: function (data, status) {  
                 if(data.msg=="200"){
                     //alert("图片可用");
                     $("#serverImgNamed1").val(data.imgName);
                     $("#uploadd1").attr("src", "/static/upload/"+data.imgName);
                     $("#rmdetail1").css("display","inherit");
                 }
                 else if(data.msg=="201"){
                	 alert("图片不符合");
                 }
                 if(typeof(data.error) != 'undefined') {  
                     if(data.error != '') {  
                         alert(data.error);  
                     } else {  
                         alert(data.msg);  
                     }  
                 }
              }, 
             error: function (data, status, e) {  
                 alert(e);  
             }  
         });  
     });
     });
   </script>
   <!-- 添加详情1图 -->
      <!-- 添加详情2图 -->
    <script>
     $(document).ready(function(){ 
     $("#uploadd2").on('click', function() {  
         $('#fileToUploadd2').click();  
     });
     //这里必须绑定到file的父元素上，否则change事件只会触发一次，即在页面不刷新的情况下，只能上传一次图片，原因http://blog.csdn.net/wc0077/article/details/42065193
     $('#fileDivd2').on('change',function() {  
         $.ajaxFileUpload({  
             url:'/product/uploadImgd2',  
             secureuri:false,  
             fileElementId:'fileToUploadd2',//file标签的id  
             dataType: 'json',//返回数据的类型  
             //data:{name:'logan'},//一同上传的数据  
             success: function (data, status) {  
                 if(data.msg=="200"){
                     //alert("图片可用");
                     $("#serverImgNamed2").val(data.imgName);
                     $("#uploadd2").attr("src", "/static/upload/"+data.imgName);
                 }
                 else if(data.msg=="201"){
                	 alert("图片不符合");
                 }
                 if(typeof(data.error) != 'undefined') {  
                     if(data.error != '') {  
                         alert(data.error);  
                     } else {  
                         alert(data.msg);  
                     }  
                 }
              }, 
             error: function (data, status, e) {  
                 alert(e);  
             }  
         });  
     });
     });
   </script>
   <!-- 添加详情2图 -->
   
<input type="hidden" id="loginUserId" value="${id}"></input>
<input type="hidden" id="loginToken" value="${token}"></input>
</body>
</html>
