<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/platform/page/system/head.jsp"%>
<%
Integer login = 0;
Integer timeOutDays = -1;
if(request.getAttribute("login") != null){
	login = (Integer)request.getAttribute("login");
	if(login == 0){
		if(request.getAttribute("timeOutDays") != null){
			timeOutDays = (Integer)request.getAttribute("timeOutDays");
		}
	}
}
String printRole = "false";
if(request.getAttribute("login") != null){
	login = (Integer)request.getAttribute("login");
	if(login == 0){
		if(request.getAttribute("printRole") != null){
			printRole = (String)request.getAttribute("printRole");
		}
	}
} else {
	if(request.getAttribute("printRole") != null){
		printRole = (String)request.getAttribute("printRole");
	}
}
%>
<script>
	var usrLogin = '<%=login%>';
	var timeOutDays = '<%=timeOutDays%>';
	var printRole = '<%=printRole%>';
	
	function initApplet(){
		//var str = '<applet code="AppletTest.class" id="AppletTest" name = "AppletTest" codebase="./" archive="'+rootPath+'/report/printTest.jar"></applet>';
		if(printRole == 'true'){
			var str = '';
			if ($.browser.msie ) {
				str+='<object id="AppletTest" name = "AppletTest" classid="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93" ';
				str+='	codebase="<%=hostAppPath%>/report/jre-6u37-windows-i586.exe" height=0 width=0>';
				str+='	<PARAM NAME="code" VALUE="AppletTest.class">';
				str+='	<PARAM NAME="id" VALUE="AppletTest">';
				str+='	<PARAM NAME="name" VALUE="AppletTest">';
				str+='	<PARAM NAME="CODEBASE" VALUE="./">';
				str+='	<PARAM NAME="ARCHIVE" VALUE="'+rootPath+'/report/printTest.jar">';
				str+='	<param name="type" value="application/x-java-applet;version=1.6">';
				str+='	<param name="scriptable" value="false">';
				str+='</object>';
			}else{
				str += '<applet code="AppletTest.class" id="AppletTest" name = "AppletTest" codebase="./" archive="'+rootPath+'/report/printTest.jar"></applet>';
			}
			$(str).appendTo('body');
		}
	}
	
</script>
<html>
<head>
<TITLE><eap:language name="main.title" type="main" /></TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<link rel="stylesheet" type="text/css"
	href="./platform/css/gjjs-themes/<%=xcp_sys_usertheme%>/main.css"></link>
<script type="text/javascript"
	src="./platform/js/jquery-core/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="./platform/js/share/include.js"></script>
<script type="text/javascript" src="./platform/js/share/md5.js"></script>
<script type="text/javascript" src="./platform/js/share/tools.js"></script>
<script type="text/javascript" src="./platform/js/share/app.js"></script>
<script type="text/javascript" src="./myDocument/js/tools/print.js"></script>
<script>	
			$(function() {
				$('body').xcpApp();
				//$xcp.toolsMgr.loadPrintSetting();
				var sysDate1 = sysDate.replace("-","年");
				sysDate1 = sysDate1.replace("-","月");
				sysDate1 +="日"; 
			    var	workDate = "<font size=\"12px\" style='font-family: Tahoma, Arial, \"宋体\";'>"+("日期:")+"</font>"+"<font style=\"color:#0073ea; cursor:pointer\">"+(sysDate1)+"</font>";
				$('.workDate').html(workDate);
			});				
		</script>
</head>
<body class="easyui-layout ">
	<div region="center" style="overflow: hidden;">
		<div class='mainTop'>
			<div class="logo"></div>
			<div class='welcome'>
				<div id='userLoginInfo'></div>
				<div class='modPassword'></div>
				
			</div>
			<div class='workDate'></div>

			<div class='rightToolsArea'>
			    
				<div id='desktopMoreMenu' class="icon_sys" refId='sysMasterMenu'
					title='<eap:language name="main.xintcd" type="main" />'></div>
				<%-- <div id='desktopQuotePrice' class="icon_price" refId='sysQuotePriceShow' title='<eap:language name="main.jinrpj" type="main" />'></div>
	                 --%>
				<div id='desktopSetting' class="icon_setting"
					refId='sysUserDefSetting'
					title='<eap:language name="main.changysz" type="main" />'></div>
				<div class="icon_help"
					title='<eap:language name="main.bangz" type="main" />'></div>
				<div id='userSysExit' class="icon_exit"
					title='<eap:language name="main.tuic" type="main" />'></div>
				<!-- title= <eap:language name="lc.issueDate" type="main" /> -->
			</div>
		</div>

		<div class='mainTopSplit'></div>

		<div id="mainCenterArea" class="mainCenter">
			<div id='defMasterMenuIconAreaWrap' class='defMasterMenuIconAreaWrap'>
				<div class='arrow menuDown'></div>
				<!-- 默认有一个菜单页签 -->
				<!-- <div id='MasterMenuIconArea-1' class='defMasterMenuIconArea current'></div> -->
				<div class='arrow menuUp'></div>
			</div>

			<div class="defMasterMenuNavigation">
				<!-- <div id='MasterMenuNavigation-1' class='iconNavigation current'  refIconAreaId='MasterMenuIconArea-1'>
				    </div> -->
				<div id="masterMenuDel" class="masterMenuDel"></div>
				<div id="masterMenuAdd" class="masterMenuAdd"></div>
				<div id="masterMenuUpdate" class="masterMenuUpdate"></div>
				<div id="masterMenuOk" class="masterMenuOk"></div>
			</div>

			<%--   <div class='defWidgetsBg'></div>
				 <div class='defWidgetsAreaWrap'>
				 	<!-- <div class='toogleBtn down'></div> -->
				 	<div class="arrow_left"><div class='prev' id='widgetArrowPrev' ></div></div>
				 	<div id='defWidgetsContent' class='defWidgetsContent'>
				 		<jsp:include page="/platform/page/system/widgets/widgets.jsp"/>
				 	</div>
				 	<div class="arrow_right"><div id='widgetArrowNext' class='next'></div></div>
				</div>--%>
		</div>
	</div>

	<div region="south" style="height: 34px; overflow: hidden;">
		<div class='mainFooter'>
			<div id='taskStart' class='start'></div>
			<div id='taskBars' class='taskBars'></div>
			<div id='taskRight' class='taskRight'>
				<div class="fengX"></div>
				<jsp:include page="/platform/page/system/widgets/taskWidgets.jsp" />
			</div>
		</div>
	</div>

	<div id='sysMasterMenu' class='toolNavigation'>
		<div class='arrowhead'></div>
		<div class='contentWrap'>
			<div class='tabsWrap'>
				<div class='tab business' refId='menu_business_content'>
					<eap:language name="main.yew" type="main" />
				</div>
				<div class='tab parameter' refId='menu_parameter_content'>
					<eap:language name="main.cans" type="main" />
				</div>
				<%-- <div class='tab declare'    refId='menu_declare_content'><eap:language name="main.shanb" type="main" /></div> --%>
				<div class='tab statement' refId='menu_statement_content'>
					<eap:language name="main.baob" type="main" />
				</div>
				<div class='close'></div>
			</div>
			<div class='content'>
				<div class='tabRefContent' id='menu_business_content'
					sysMenuType='BU'></div>
				<div class='tabRefContent' id='menu_parameter_content'
					sysMenuType='PB'></div>
				<!-- <div class='tabRefContent' id='menu_declare_content'    sysMenuType ='DL'></div> -->
				<div class='tabRefContent' id='menu_statement_content'
					sysMenuType='RP'></div>
			</div>
		</div>
	</div>

	<%-- <div id='sysQuotePriceShow' class='toolNavigation'>
			<div class='arrowhead'></div>
				<div class='contentWrap'>
					<div class='qpHead'><eap:language name="main.jinrpj" type="main" /><div class='close'></div></div>				
					<div class='content'>
				</div>
			</div>
		</div>		 --%>

	<div id='sysUserDefSetting' class='toolNavigation'>
		<div class='arrowhead'></div>
		<div class='contentWrap'>
			<div class='qpHead'>
				<eap:language name="main.changysz" type="main" />
				<div class='close'></div>
			</div>
			<div class='content'>
				<jsp:include page="/platform/page/system/widgets/userSetting.jsp" />
			</div>
		</div>
	</div>

	<jsp:include page="/platform/page/system/widgets/defIconCls.jsp" />

	<form id="userLangOrThemeDefForm" method="post">
		<input type="hidden" id="usr_language" name="usr_language" /> <input
			type="hidden" id="usr_theme" name="usr_theme" /> <input type="hidden"
			id="usr_viewRptMtd" name="usr_viewRptMtd" /> <input type="hidden"
			id="user_quickBar" name="user_quickBar" />
	</form>
</body>
<%-- <applet code="AppletTest.class" id="AppletTest" name = "AppletTest" codebase="./" archive="<%=webAppPath%>/report/printTest.jar"></applet>   --%>
</html>