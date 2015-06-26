<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/eap.tld" prefix="eap"%>
<div class="taskRightIconArea widgetIcon-8" refIconCls='icon-1' refHasLeft="N" refTradeFlag='N' fixedPostionMenu='Y' refGroupTrade='Y' refurl ="./platform/page/system/taskMgr.jsp"  refId='mainPageTaskMgr' reftradeno ="taskmgr" title ='<eap:language name="main.taskMgr" type="main" />'  refname ='<eap:language name="main.taskMgr" type="main" />'>
</div>
<div class="taskRightIconArea widgetIcon-5" refIconCls='icon-1' refHasLeft="N"  refTradeFlag='N' fixedPostionMenu='Y' refurl ="./transitTradeQuery.do?method=view" refId='transitTradeQuery'  reftradeno ="transitTradeQueryNo" title ='<eap:language name="main.butxntp" type="main" />' refname ='<eap:language name="main.butxntp" type="main" />'>
</div>
<div class="taskRightIconArea widgetIcon-1" refIconCls='icon-1' refHasLeft="N"  refTradeFlag='N' fixedPostionMenu='Y' refurl ="./tradeQuery.do?method=view" refId='butxnar'  reftradeno ="matureHintNo" title ='<eap:language name="main.tradeQuery" type="main"/>' refname ='<eap:language name="main.tradeQuery" type="main"/>'>
</div>
<%-- <div class="taskRightIconArea widgetIcon-7" refIconCls='icon-3' refHasLeft="N"  refTradeFlag='N' fixedPostionMenu='Y' refurl ="./printSys.do?method=getPrint" refId='printSys'   reftradeno='printSys' title ='<eap:language name="main.daygl" type="main" />' refname ='<eap:language name="main.daygl" type="main" />'>
</div> --%>
<div class="taskRightIconArea kscx widgetIcon-2"    reftradeno =""  title = "<eap:language name="main.kuaiscx" type="main" />"  refname ="<eap:language name="main.kuaiscx" type="main" />">
</div>
<div class="taskRightIconArea gdpj widgetIcon-4"    reftradeno =""  title = "<eap:language name="main.gdpj" type="main" />"  refname ="<eap:language name="main.gdpj" type="main" />">
</div>

<div class="taskRightIconArea maturyHint widgetIcon-9" refIconCls='icon-1' refHasLeft="N"  refTradeFlag='N' fixedPostionMenu='Y' refurl ="./matureHint.do?method=view"      refId='matureHintMgr'  reftradeno ="maturyHint" title ='<eap:language name="main.maturyTaskInfo" type="main"/>' refname ='<eap:language name="main.maturyTaskInfo" type="main"/>'>
</div>


<script>
$(function(){
	$(".taskRightIconArea","#taskRight").tooltip({
		position: 'top',
		content : $(this).attr("refname"),
		onShow : function(){
			$(this).tooltip('tip').css({
				backgroundColor :  '#666',
				borderColor : '#666',
				color:'#fff'
			});
		}
	});
});
</script>