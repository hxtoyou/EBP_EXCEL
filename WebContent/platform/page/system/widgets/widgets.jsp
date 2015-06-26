<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/eap.tld" prefix="eap"%>
<div id='defWidgetsContentList' class='defWidgetsContentList'>
 	<div class="widgetIconArea kscx" style="display:block;">
 		<div class='widget widgetIcon-2'></div>
 		<div class='widgetText'><eap:language name="main.kuaiscx" type="main" /></div>
 	</div>
 	<div class="widgetIconArea widgetOpenTaskrenwlb" refIconCls='icon-1' refHasLeft="N" refTradeFlag='N' fixedPostionMenu='Y' refGroupTrade='Y' refurl ="./platform/page/system/taskMgr.jsp"  refId='mainPageTaskMgr' reftradeno ="taskmgr" refname =<eap:language name="main.taskMgr" type="main" />>
 		<div class='widget widgetIcon-8'></div>
 		<div class='widgetText'><eap:language name="main.taskMgr" type="main" /></div>
 	</div>
 	<div class="widgetIconArea widgetOpenTaskdaygl" refIconCls='icon-3' refHasLeft="N"  refTradeFlag='N' fixedPostionMenu='Y' refurl ="./printSys.do?method=getPrint" refId='printSys'   reftradeno='printSys' refname =<eap:language name="main.daygl" type="main" />>
 		<div class='widget widgetIcon-7'></div>
 		<div class='widgetText'><eap:language name="main.daygl" type="main" /></div>
 	</div>
 	<div class="widgetIconArea widgetOpenTasktradeQuery" refIconCls='icon-1' refHasLeft="N"  refTradeFlag='N' fixedPostionMenu='Y' refurl ="./tradeQuery.do?method=view" refId='butxnar'  reftradeno ="matureHintNo" refname ='<eap:language name="main.tradeQuery" type="main"/>'>
 		<div class='widget widgetIcon-1'></div>
 		<div class='widgetText'><eap:language name="main.tradeQuery" type="main" /></div>
 	</div>
 	<div class="widgetIconArea widgetOpenTaskbutxntp" refIconCls='icon-1' refHasLeft="N"  refTradeFlag='N' fixedPostionMenu='Y' refurl ="./transitTradeQuery.do?method=view" refId='transitTradeQuery'  reftradeno ="transitTradeQueryNo" refname ='<eap:language name="main.butxntp" type="main" />'>
 		<div class='widget widgetIcon-5'></div>
 		<div class='widgetText'><eap:language name="main.butxntp" type="main" /></div>
 	</div>
 	<div class="widgetIconArea widgetOpenTasklogoutMag" refIconCls='icon-1' refHasLeft="N"  refTradeFlag='N' fixedPostionMenu='Y' refurl ="./logoutMag.do?method=view" refId='logoutMag'  reftradeno ="logoutMagNo" refname ='<eap:language name="main.logoutMag" type="main" />'>
 		<div class='widget widgetIcon-3'></div>
 		<div class='widgetText'><eap:language name="main.logoutMag" type="main" /></div>
 	</div>
 	<div class="widgetIconArea widgetOpenTasktaskAssign" refIconCls='icon-1' refHasLeft="N"  refTradeFlag='N' fixedPostionMenu='Y' refurl ="./taskAssign.do?method=getTask" refId='taskAssign'  reftradeno ="taskAssign" refname ='<eap:language name="main.taskAssign" type="main" />'>
 		<div class='widget widgetIcon-4'></div>
 		<div class='widgetText'><eap:language name="main.taskAssign" type="main" /></div>
 	</div>
 	<div class="widgetIconArea widgetOpenTaskdayEnd" refIconCls='icon-1' refHasLeft="N"  refTradeFlag='N' fixedPostionMenu='Y' refurl ="./dayEnd.do?method=init" refId='dayEnd'  reftradeno ="dayEnd" refname ='<eap:language name="main.dayEnd" type="main" />'>
 		<div class='widget widgetIcon-6'></div>
 		<div class='widgetText'><eap:language name="main.dayEnd" type="main" /></div>
 	</div>
 	
 	<div class="widgetIconArea widgetOpenTaskdayEnd" refIconCls='icon-1' refHasLeft="N"  refTradeFlag='N' fixedPostionMenu='Y' refurl ="./birtConsole.do?method=prepare&action=buildQueryButxnar" refId='buildQueryButxnar'  reftradeno ="buildQueryButxnar" refname ='<eap:language name="main.report" type="main" />'>
 		<div class='widget widgetIcon-6'></div>
 		<div class='widgetText'><eap:language name="main.report" type="main" /></div>
 	</div>
</div>