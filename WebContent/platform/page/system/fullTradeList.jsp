<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="/platform/page/system/head.jsp"%>
<%@taglib uri="/WEB-INF/eap.tld" prefix="eap"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
	
		<script type="text/javascript" src="<%=webAppPath%>platform/js/jquery-core/jquery-1.8.0.min.js"></script>
		<script type="text/javascript" src="<%=webAppPath%>platform/js/share/include.js"></script>
		<script type="text/javascript" src="<%=webAppPath%>platform/js/share/packets.js"></script> 
		
	</head>
	<body class="easyui-layout" border=true>
		<form id='imlcIssueForm' name='yw020001FormName' style="height: 100%; width: 100%;">
			<jsp:include page="/myDocument/page/system/transCommHideFiled.jsp"/>
	
			<div region="north" border="false">
				<div class="mbar">
					<div id='xcpTitleMbarDiv' scroll="no"></div>
				</div>
			</div>
		
			<div region="center" border="false">
				<div id="tt" class="easyui-tabs formMainTabs" fit="true" border="false">
					<div title="基本信息">
						<div class="mbox">
							<div class="btop"><eap:language name="lc.kzywxq" type="LC" /></div>
							<div class="bcnt">
								<table class="etable">
									 <tr>
									<td class='textlabel' width="20%">历史流水号：</td>
									<td class='textinput' width="30%" colspan="3">
									<input type="text" id="otrlbk_oldTxnNo" name="otrlbk_oldTxnNo" maxlength="20" class="easyui-validatebox O" 
										data-options="required:false,validType:'illegalChar'"></td>
								   </tr>
								</table>
							</div>
						</div>
						

						<br>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>
<script>
	$(function(){
		$xcp.stopPorcess();
	});
</script>