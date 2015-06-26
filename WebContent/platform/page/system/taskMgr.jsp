<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/eap.tld" prefix="eap"%>
<div id="taskMgrMain_Html" ></div>
<%-- <div id="taskMgrMain" class="easyui-tabs" fit="true" border="false" style='width:1254px;height:448px;overflow-x:hidden;overflow-y:auto'>
	<!-- 我的任务(之前待处理任务) -->
	<div id='wdrw' title="<eap:language name="main.woderw" type="main" />" refUrl="./pendingTask.do?method=view" refDataGridId='pendingTask_letterTable'>
		<iframe id='taskMgrMain-iframe-a' src=''  frameborder='0'  style='width:100%;height:100%;overflow-x:hidden;overflow-y:auto'></iframe>
	</div>
	<!-- 已完成任务(之前为参与任务) -->
	<div id='ywcrw' title="<eap:language name="main.yiwcrw" type="main" />" refurl ="./pendingTask.do?method=viewdo" refDataGridId='currentTask_letterTable'>
		<iframe id='taskMgrMain-iframe-b' src='' frameborder='0'  style='width:100%;height:100%;overflow-x:hidden;overflow-y:auto'></iframe>
	</div>
	TASK #204 任务管理-（请查看需求，后台请王新林配合，主要做前台功能） 屏蔽 begin
	<!-- 完成任务 -->
	<div title="<eap:language name="main.wancrw" type="main" />" refurl ="./pendingTask.do?method=viewdone" refDataGridId='finishedTaskTable'>
		<iframe id='taskMgrMain-iframe-c' src='' frameborder='0'  style='width:100%;height:100%;overflow-x:hidden;overflow-y:auto'></iframe>
	</div>
	 TASK #204 任务管理-（请查看需求，后台请王新林配合，主要做前台功能） 屏蔽 end
	
	<!-- 任务列表 -->
	<!-- 
	<div title="<eap:language name="main.renwlb" type="main" />" refurl ="./pendingTask.do?method=viewCustom" refDataGridId='customTask_letterTable'>
		<iframe id='taskMgrMain-iframe-d' src='' frameborder='0'  style='width:100%;height:100%;overflow-x:hidden;overflow-y:auto'></iframe>
	</div>
	 -->
</div> --%>

<script>
   var taskHtml = $("#taskMgrMain_Html");
   var mgHeight =  ($(window).outerHeight() - 119 )+ "px";
   if(taskHtml.closest("#mainCenterArea").length > 0){
	   mgHeight = ($(window).outerHeight() - 119 - 60 ) + "px";
   }
   
   var strHtml  = '<div id="taskMgrMain" class="easyui-tabs" fit="true" border="false" style=\'width:'+($(window).outerWidth() - 20)+'px;height:'+mgHeight+';overflow-x:hidden;overflow-y:auto\'>'+
				  '<div id="wdrw" title="<eap:language name="main.woderw" type="main" />" refUrl="./pendingTask.do?method=view" refDataGridId="pendingTask_letterTable">'+
				  '		<iframe id="taskMgrMain-iframe-a" src=""  frameborder="0"  style="width:100%;height:100%;overflow-x:hidden;overflow-y:auto"></iframe>'+
				  '</div>'+
				  '<div id="ywcrw" title="<eap:language name="main.yiwcrw" type="main" />" refurl ="./pendingTask.do?method=viewdo" refDataGridId="currentTask_letterTable">'+
				  '	<iframe id="taskMgrMain-iframe-b" src="" frameborder="0"  style="width:100%;height:100%;overflow-x:hidden;overflow-y:auto"></iframe>'+
				  '</div>'+
				  '</div>';

    taskHtml.html(strHtml);
	$('#wdrw').attr('title',$xcp.i18n("sys.mytask"));
	$('#ywcrw').attr('title',$xcp.i18n("sys.finish"));
	$.parser.parse(taskHtml);
	
   $("#taskMgrMain").tabs({
	   border:false,
	   onSelect:function(title){
		   var panel =  $("#taskMgrMain").tabs('getTab',title);
		   var url   = $(panel).attr('refUrl'); 
		   var iframeSrc = $(panel).find('iframe').attr("src");
		   top.window.onbeforeunload = null;
		   if(url && iframeSrc == ''){
			   $(panel).find('iframe').attr("src",$xcp.def.getFullUrl(url) + "&uuid=mainPageTaskMgr");
		   }else{
			   var dagaGridId =  $(panel).attr('refDataGridId'); 
			   var dwindw = $(panel).find('iframe')[0].contentWindow;
			   if(dwindw.$("#" + dagaGridId).length>0){
				   dwindw.$("#" + dagaGridId).datagrid('reload');
			   }
		   }
	   }
   });
   $("#taskMgrMain-iframe-a").attr("src",$xcp.def.getFullUrl("./pendingTask.do?method=view") + "&uuid=mainPageTaskMgr");
   
   $(function(){
	   $(window).resize(function(){
		   var taskMgrMain = $("#taskMgrMain");
		   taskMgrMain.css({
			   height : ($(window).outerHeight() - 119  )
		   }).tabs("resize");
		});
   });
</script>