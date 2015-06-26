<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="/platform/page/system/head.jsp"%>
<%@taglib uri="/WEB-INF/eap.tld" prefix="eap"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
		<script type="text/javascript" src="<%=webAppPath%>platform/js/jquery-core/jquery-1.8.0.min.js"></script>
		<script type="text/javascript" src="<%=webAppPath%>platform/js/share/include.js"></script>
		<script type="text/javascript" src="<%=webAppPath%>myDocument/js/tools/commQueryCondition.js"></script>
		<script type="text/javascript" src="<%=webAppPath%>/platform/page/system/task/taskAssign.js"></script>
	</head>
	<BODY style="background: #FFFFFF" id="taskAssign_sysQueryList" class="easyui-layout" border=false fit="true">
		<div id="taskAssign_npanel" region="north" title="panel" border="false"
		data-options="tools:'#taskAssign_paneltools'">
				<div id="taskAssign_condition" class='commQueryCondWrap'>
					<!-- 查询条件输入区域 -->
					<div class='query'>
						<div class='querybutton'>
							<div class='text'></div>
							<div class='input' id="taskAssign_queryDiv"></div>
						</div>
					</div>
				</div>
			</div>
		<div id="taskAssign_cpanel" region="center" border="false" style="background-color: #FAFAF9;">
			<table id="taskAssign_letterTable">
			</table>
		</div>
		<div id='taskAssign_paneltools'>
			<div id='taskAssign_commQueryIcon' class='commQueryUpicon'></div>
		</div>
		
		
		<div id="edintFromWindow" style="display: none;" >
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center'" style="padding:10px;">
					 <form id="edintFromWindowForm" >
					 	<div class="bcnt"  ><!-- id="edintFrom" -->
								<table class="etable" >
									<tr>
										<td class="lbl">业务流水号</td>
										<td class="ele"><input type="text" id="txnNo" name="txnNo" maxlength="20" readonly="readonly" class="easyui-validatebox"
											data-options="required:true,validType:'nochinese'"></td>
										<td class="lbl">执行机构</td>
										<td class="ele"><input type="text" id="orgName" name="orgName" maxlength="15" readonly="readonly" class="easyui-validatebox "
											data-options="required:true"></td>
										<td></td>
									</tr>
									<tr>
										<td class="lbl">指定处理人</td>
										<td class="ele"><input type="text" id="fixOp" name="fixOp" class="easyui-combobox"
											data-options="required:true,textField:'userName',valueField:'userId',panelHeight:100"></td>
										<td class="lbl">交易名称</td>
										<td class="ele">
											<input type="text" id="tradeDesc" name="tradeDesc" class="easyui-validatebox" readonly="readonly" data-options="required:true">
										</td>
										<td></td>
									</tr>
								</table>
								
							</div>  
					</form>
			</div>
			<div data-options="region:'south',border:false,split:true" style="text-align:center;padding:5px 0 0;height: 43px;">
			<a title="<eap:language name="param.confirm" type="global" />" id = "linkButLsdSave" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)"  ><eap:language name="param.confirm" type="global" /></a>
			<a title="<eap:language name="param.cancel" type="global" />"  id = "linkButLsdBreak" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" ><eap:language name="param.cancel" type="global" /></a>
			</div> 
		</div>
			
		</div>
</BODY>
	<%-- <body   border="true" >
<!-- 		<form id='paramSubForm' name='paramSubForm'  method="post"> -->
			<jsp:include page="/myDocument/page/system/transCommHideFiled.jsp"/>
<!-- 		</form> -->
	
				<div class="easyui-layout" fit="true" >
					<div id="npanel" region="north" border="false" style="background-color:#DEE9F3;height:75px;overflow-y:hidden;">
						<div id="condition">
							<form id='paramForm' name='paramForm' >
								<table id="paramTab" class="etable">
									<tr>
										<td style="text-align: right;">所属机构</td>
										<td><input type="text"   id ="queryOrgNo" class="easyui-combobox" /> </td>
										<td style="text-align: right;">交易名称</td>
										<td><input type="text"   id ="queryTradeNo" class="easyui-combobox"/> </td>
									</tr>
									<tr>
										<td></td>
										<td align="right" ><button class="but_query paramQueryBtn" type="button" onclick="taskQuery()" >查 询 </button></td>
										<td align="center" ><button class="but_query paramQueryBtn" type="button" onclick="clearParam()">清 除 </button></td>
										<td></td>
									</tr>
								</table>
							</form>
						</div>
					</div>
					<div id="cpanel" region="center" border="false"  style="height:40%;width:40%"  >
						<table id="taskTab">
						</table>
					</div>
				</div>



	</body> --%>
</html>
