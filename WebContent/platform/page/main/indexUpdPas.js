function enterKey(e) {
	if (e.keyCode == 13) {
		$("#userId").trigger("blur");
		$("#userPasOld").trigger("blur");
		login();
	}
}

function login() {
	$("#userId").trigger("blur");
	$("#userPasswd2").trigger("blur");
	var checkFlag = true;
	if ($("#userId").attr("dummyUsrName") == "") {
		$("#logShow").html("用户名不能为空");
		checkFlag = false;
		return false;
	}
	if ($("#userPasswd").attr("dummyUsrPwd") =="") {
		$("#logShow").html("密码不能为空");
		checkFlag = false;
		return false;
	}
	 
	
	if (checkFlag) {
		$("#userPasswd").val(b64_md5($("#userPasswd").attr("dummyUsrPwd")));
		document.forms[0].submit();
	}
}

$(function() {
	$(".loginBtn").click(function(){
	    login();
	});
	$("#userPasswd").val("");
	$(document).keydown(function(e){
		enterKey(e);
	});
});


$(function(){

	var tx = $("#userPasswd2"), 
		pwd = $("#userPasswd"), 
		username = $("#userId");
		tx.focus(function(){
			if ($(this).val() != this.defaultValue){
				$(this).attr("dummyUsrPwd",$(this).val());
				$("#userPasswd").attr("dummyUsrPwd",$(this).val());
				return;
			}
			$(this).hide();
			pwd.show();
			pwd.val("");
			pwd.focus();
			$("#logShow").html("");
		});
		pwd.blur(function(){
			if ($(this).val() != this.defaultValue){
				$(this).attr("dummyUsrPwd",$(this).val());
				return;
			}
			$(this).hide();
			tx.show();	
			tx.val(document.getElementById('userPasswd2').defaultValue);
		});
		username.focus(function(){
			if($(this).val() ==this.defaultValue){  
				$(this).val("");
			};	
			$("#logShow").html("");
		}).blur(function(){
			if ($(this).val() == '') {		
				$(this).val(this.defaultValue);
			}else{
				$(this).attr("dummyUsrName",$(this).val());
			}
		});
});