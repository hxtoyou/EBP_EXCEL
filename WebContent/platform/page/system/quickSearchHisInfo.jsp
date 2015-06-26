<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/eap.tld" prefix="eap"%>
<%@include file="/platform/page/system/head.jsp"%>
<%
	String txnNo = request.getParameter("txnNo");
	String mvc = request.getParameter("tradeName");
	String curtBizNo =  request.getParameter("curtBizNo");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<link rel="stylesheet" type="text/css" href="<%=webAppPath%>css/gjjs-themes/default/uploadify.css">
<script type="text/javascript" src="<%=webAppPath%>platform/js/jquery-core/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=webAppPath%>platform/js/share/include.js"></script>
</head>


<body>

	<div class="easyui-layout" data-options="fit:true,border:false">
		<div region="center" border="false">
			<div id ="mainTab"  class="easyui-tabs">
				<div id="bus" title="业务信息" ></div>
				<div id="bal" title="余额信息" style="height:487px;"></div>
				<div id="his" title="历史信息" style="height:487px;"></div>
			</div>
		</div>


	</div>

</body>
</html>


<script>


	$(function() {
		var panels = $("#mainTab").tabs("tabs");
		$.each(panels,function(i,o){
			var opts = {};
			if(i == 0){
				opts.url =$xcp.def.getFullUrl('./<%=mvc%>.do?method=getInfo&queryFlag=History&taskState=2&txnNo=<%=txnNo%>');
				
			}else if(i==1){
				opts.url =$xcp.def.getFullUrl('./xmlcfg/page/platform/common/balance.jsp?curtBizNo=<%=curtBizNo%>');
			}else if(i==2){
				opts.url =$xcp.def.getFullUrl('./xmlcfg/page/platform/common/curHistoryPage.jsp');
			}
		
			$.ajax({
				type : "get",
				url      : opts.url,
				dataType : 'html',
				data     : {},
				async    : false,
				success  : function(result) {
					o.panel("options").content = result;
					alert(checkData);
				}
			});
			
		});
		$("#mainTab").tabs("select","业务信息") ; 
	});
	
</script>