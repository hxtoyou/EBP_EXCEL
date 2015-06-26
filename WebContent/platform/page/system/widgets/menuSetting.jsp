<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/eap.tld" prefix="eap"%>
<div class="userSettingPanel">
<div id="menuSettingPanelLeft" class='userSettingPanelLeft'>
	<div class='leftIcon current' refRightIconId = "menu_business_content">
		<span ><eap:language name="main.yew" type="main" /></span>
	</div>
	<div  class='leftIcon ' refRightIconId = "menu_parameter_content">
		<span ><eap:language name="main.cans" type="main" /></span>
	</div>
	<div  class='leftIcon ' refRightIconId = "menu_declare_content">
		<span ><eap:language name="main.shanb" type="main" /></span>
	</div>
	<div  class='leftIcon ' refRightIconId = "menu_statement_content">
		<span ><eap:language name="main.baob" type="main" /></span>
	</div>
</div>

<div id="menuSettingPanelRight" class="userSettingPanelRight">
   <div   class='rightIcon current '>
   	<div  class='tabRefContent' id='menu_business_content'   sysMenuType ='BU'></div>
   </div>
   <div  id='menu_parameter_content' class='rightIcon  ' sysMenuType ='PB'>
   </div>
   <div  id='menu_declare_content' class='rightIcon ' sysMenuType ='DL'>
   </div>
   <div  id='menu_statement_content' class='rightIcon ' sysMenuType ='RP'>
   </div> 
</div>
</div>