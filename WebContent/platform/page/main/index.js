function enterKey(e) {
	if (e.keyCode == 13) {
		login();
	}
}

function changeLang(){
	var userId = $('#userId').val();
	var pass = $("#userPasswd").val();
	var lang = $("#selLanguage").val();
	if(lang == 'en_US'){
		$("#main").html('<div><span class="index_span1">Account:&nbsp;</span><input class="search" id="userId" name="userId" type="text" value="" /> </div> <div><span class="index_span1"> Password:&nbsp;</span><input class="search" id="userPasswd"  name="userPasswd" type="password"  /> </div> <div><span class="index_span2"> Language:</span> <select name="selLanguage" id="selLanguage" class="search1" onchange="changeLang()"><option value="">-Language-</option><option value="zh_CN">简体中文</option> <option value="en_US">English</option>	</select></div>');
		$("#loginBtn").html('login');
	}else{
		$("#main").html('<div><span class="index_span1">用户名:&nbsp;</span><input class="search" id="userId" name="userId" type="text" value="" /> </div> <div><span class="index_span1"> 密&nbsp;&nbsp;&nbsp;码:&nbsp;</span><input class="search" id="userPasswd"  name="userPasswd" type="password" /> </div> <div><span class="index_span2"> 语&nbsp;&nbsp;&nbsp;言:</span> <select name="selLanguage" id="selLanguage" class="search1" onchange="changeLang()"><option value="">-语 言-</option><option value="zh_CN">简体中文</option> <option value="en_US">English</option>	</select></div>');
		$("#loginBtn").html('登 录');
	}
	$("#selLanguage").attr('value',lang);
	$('#userId').attr('value',userId);
	$('#userPasswd').attr('value',pass);
	
}

function login() {
	var checkFlag = true;
	if ($("#userId").val() == "") {
		$("#logShow").html("用户名不能为空");
		checkFlag = false;
		return false;
	}
	if ($("#userPasswd").val() =="") {
		$("#logShow").html("密码不能为空");
		checkFlag = false;
		return false;
	}
	//登录时语言选择
	$('#usr_language').val($("#selLanguage").val());
	
	if (checkFlag) {
		$("#hidePass").val($("#userPasswd").val());
		$("#userPasswd").val(b64_md5($("#userPasswd").val()));
		$.ajax({
			type     : 'post',
			url      : './checkLogin.do?userId='+$("#userId").val()+'&logonType=check',
			dataType : "json",	
			async    : true,
			success  : function(result) {
				if(result.success == "1"){
					if(window.confirm('用户已登陆！是否签退？')){
						document.forms[0].submit();
					}else{
						$("#userPasswd").val("");
					}
				}else{
					document.forms[0].submit();
				}
			},
			beforeSend : function(XMLHttpRequest, textStatus){
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
			}
		});
	}
}

function duplicateLogin(backurl) {
	if (confirm('用户已登录，是否进行强制签退操作?')) {
		var ret = $.ajax({
				url   : "./logout.do?method=kickOp&random=" + Math.random(),
				success : function(result){
					//location = backurl;
					$("#userPasswd").val("");
					$("#logShow").empty();
					$("#userPasswd").focus();
			   }
		});
	} else {
		var browserName = navigator.appName;
		if (browserName == "Netscape") {
			window.open('', '_parent', '');
			window.close();
		} else if (browserName == "Microsoft Internet Explorer") {
			window.opener = "whocares";
			window.close();
		}
	}
}
//清除密码
function clearPass(){
	$("#userPasswd").focus();
}

//用户输入获取焦点
function clearUserId(){
	$("#userId").focus();
}

//根据错误信息获取焦点
function setElentFocus(errCode){
	$("#userPasswd").val("");
	if(errCode == null || errCode == undefined || errCode == "null"){
		$("#userId").focus();
		return ;
	}
	switch (errCode) {
		case "030018-2":
			//用戶不正確
			clearUserId();
			break;
		case "030018-8":
			clearPass();
			break;
		case "030018-7":
			clearUserId();
			break;
		default:
			break;
	}
}

$(function(){
	$("#main").html('<div><span class="index_span1">用户名:&nbsp;</span><input class="search"  id="userId" name="userId" type="text" value=""  /> </div> <div><span class="index_span1"> 密&nbsp;&nbsp;&nbsp;码:&nbsp;</span><input class="search" id="userPasswd" name="userPasswd" type="password" /> </div> <div><span class="index_span2"> 语&nbsp;&nbsp;&nbsp;言:</span> <select name="selLanguage" id="selLanguage" class="search1" onchange="changeLang()"><option value="">-语 言-</option><option value="zh_CN">简体中文</option> <option value="en_US">English</option>	</select></div>');
	
	$(".loginBtn").click(function(){
	    login();
	});
	$(document).keydown(function(e){
		enterKey(e);
	});
	
	setElentFocus(errCode);
	$("#userId").val(userCode);
});
