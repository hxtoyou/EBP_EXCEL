<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/eap.tld" prefix="eap"%>
<%
Integer login = 0;
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
<div class="userSettingPanel">
<div id="userSettingPanelLeft" class='userSettingPanelLeft'>
	<div class='leftIcon current' refRightIconId = "rightIcon-1">
		<span ><eap:language name="main.yuyxz" type="main" /></span>
	</div>
	<%-- <div  class='leftIcon ' refRightIconId = "rightIcon-2">
		<span ><eap:language name="main.zhutxz" type="main" /></span>
	</div> --%>
	<div  class='leftIcon ' refRightIconId = "rightIcon-3">
		<span ><eap:language name="main.chakmhms" type="main" /></span>
	</div> 
	<div  class='leftIcon ' refRightIconId = "rightIcon-4">
		<span ><eap:language name="main.kuaislsz" type="main" /></span>
	</div>
	<div  class='leftIcon ' refRightIconId = "rightIcon-5" <% if(!"true".equalsIgnoreCase(printRole)) out.print("style=\"display: none\""); %>>
		<span ><eap:language name="main.printSetting" type="main" /></span>
	</div>
</div>

<div id="userSettingPanelRight" class="userSettingPanelRight">
   <div  id='rightIcon-1' class='rightIcon current' >
   		<span class='btn btnSpan' refMethod='language' refValue='zh_CN'><eap:language name="main.zhongw" type="main" /></span>
		<span class='btn btnSpan' refMethod='language' refValue='en_US'><eap:language name="main.yingw" type="main" /></span>
   </div>
  <%-- <div  id='rightIcon-2' class='rightIcon'>
   		<span class='btn' refMethod='theme' refValue='bootstrap'><eap:language name="main.baosl" type="main" /></span>
		<span class='btn' refMethod='theme' refValue='metro-gray'><eap:language name="main.gaojh" type="main" /></span>
		<span class='btn' refMethod='theme' refValue='default'><eap:language name="main.yandh" type="main" /></span>
   </div>--%>
   <div  id='rightIcon-3' class='rightIcon' >
   		<span class='btn btnSpan' refMethod="viewReportMethod"  refValue="0"><eap:language name="main.tabyq" type="main" /></span>
		<span class='btn btnSpan' refMethod="viewReportMethod"  refValue="1"><eap:language name="main.tanc" type="main" /></span>
   </div> 
   <div  id='rightIcon-4' class='rightIcon' >
    	<div >
    		<div >
    			<span style="width: 38%;" class="btnSpan"><eap:language name="main.quicTask" type="main" /> </span>
    			<span style="width: 20%;" class="btnSpan"><eap:language name="main.quickBar" type="main" /></span>
    			<span style="width: 20%;" class="btnSpan"><eap:language name="main.theMainInter" type="main" /></span>
    		</div>
    		<div class='divTab'></div>
    		<div id = "bingpUl" style="height: 192px;overflow-y:auto;" >
    		</div>
    	</div>
    	<span class='btn btnSpan' id="settingCon" style='margin-top: 5px;'><eap:language name="main.shezqr" type="main"/></span>
		<%-- <span class='btn' id="startCust"><eap:language name="main.kaisdz" type="main"/></span> --%>
   </div> 
   <div  id='rightIcon-5' class='rightIcon' >
    	<div >
    		<!-- <div >style="background-color: #f1f1f8"
    			<span class="btnSpan"> </span>
    		</div> -->
    		<div id = "printSet" style="height: 192px;overflow-y:auto;" >
    		</div>
    	</div>
    	<span class='btn btnSpan' id="printSetting" style='margin-top: 5px;'><eap:language name="main.shezqr" type="main"/></span>
		<%-- <span class='btn' id="startCust"><eap:language name="main.kaisdz" type="main"/></span> --%>
   </div> 
</div>
</div>