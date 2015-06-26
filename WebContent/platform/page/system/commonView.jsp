<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="/platform/page/system/head.jsp"%>
<%@taglib uri="/WEB-INF/eap.tld" prefix="eap"%>
<%@page import="java.net.URLDecoder"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<script type="text/javascript" src="<%=webAppPath%>platform/js/jquery-core/jquery-1.8.0.min.js"></script> 
<script type="text/javascript" src="<%=webAppPath%>platform/js/share/include.js"></script>
<script type="text/javascript" src="<%=webAppPath%>platform/js/share/packets.js"></script>

<script type="text/javascript">
	$(function(){
		var extParams = <%=URLDecoder.decode(request.getParameter("extParams")==null?"":request.getParameter("extParams"),"UTF-8")%>;
		if(extParams == undefined || extParams == null || extParams == ""){
			return false;
		}
		var extParamsObj = $xcp.parseJson(extParams);
		var viewType = (extParamsObj.type);
		
		if(viewType == "doc"){          //面函预览
			var vpo = extParamsObj.vpo;
			var id = extParamsObj.id;
			var formData = extParamsObj.formData;
			var url = extParamsObj.url;
			
			//重新画导出按钮的位置
			vpo.exhtml = '<div id="reportExportDiv_'+id+'" class="reportExportDiv" title="面函导出"></div>';
			
			$xcp.report.formViewReportInnerFun(vpo,id,formData,url);
			
			$("#"+id).height($(window).height());
			
			$(window).resize(function(){
				$("#"+id).height($(this).height() + 20);
				
			}); 
			setTimeout(function(){
				$("#reportExportDiv_"+id).bind('click.exp',function(){
					$.post($xcp.def.getFullUrl('./preview.do?method=getDocReport&reportId='+vpo.reportId+'&birtShowType=doc'),formData,function(data){
						$('#' + id)[0].contentWindow.document.write(data);
					
					});
				});
			},1000);
			
			
		}else if(viewType == "msg"){    //报文预览
			//导出按钮事件
			$("span[id^='packets_export']").bind("click.msgExp",function(){
				var title = $(".easyui-tabs").tabs("getSelected").panel("options").title;
				var ajaxUrl = "./preview.do?method=printPackets&printMsg="+title+"&messageInfoList="+$xcp.toJson(extParamsObj.packetsName)+"&taskState="+taskState;
				$.post($xcp.def.getFullUrl(ajaxUrl),extParamsObj.formData,function(data){
					window.document.write(data);
				});
			});
		}
		
	});
	
	

	function dyniframesize(down) {
		var pTar = null;
		if (document.getElementById) {
			pTar = document.getElementById(down);
		} else {
			eval('pTar = ' + down + ';');
		}
		if (pTar && !window.opera) {
			//begin resizing iframe 
			pTar.style.display = "block";
			if (pTar.contentDocument && pTar.contentDocument.body.offsetHeight) {
				//ns6 syntax 
				pTar.height = pTar.contentDocument.body.offsetHeight + 20;
				pTar.width = pTar.contentDocument.body.scrollWidth + 20;
			} else if (pTar.Document && pTar.Document.body.scrollHeight) {
				//ie5+ syntax 
				pTar.height = pTar.Document.body.scrollHeight;
				pTar.width = pTar.Document.body.scrollWidth;
			}
		}
	}
</script>

</HEAD>
<BODY style="background: #FFFFFF" id="currentTask_sysQueryList" class="" border=false>
	<%=URLDecoder.decode(request.getParameter("content")==null?"":request.getParameter("content"),"UTF-8")%>
</BODY>
</HTML>