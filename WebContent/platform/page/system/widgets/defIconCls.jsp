<%@page language="java" contentType="text/html; charset=UTF-8"%>
<div id="defTradeIconClsMenu" class="easyui-menu" style="width:120px;">
	<div>
		<span>change icon</span>
		<div class="menu-content" style="text-align:left;padding:10px">
			<div style="font-weight:bold;font-size:16px">please select icon:</div>
			<div class='defTradeIconCls'>
				<div class='defTradeIconClsArea' refCls='icon-impCredit'>
					<div class='defIconCls icon-impCredit' ></div>
					<div class='defIconClsText'>icon-impCredit</div>
				</div>
				<div class='defTradeIconClsArea' refCls='icon-1'>
					<div class='defIconCls icon-1' ></div>
					<div class='defIconClsText'>icon-1</div>
				</div>
				<div class='defTradeIconClsArea' refCls='icon-2'>
					<div class='defIconCls icon-2'></div>
					<div class='defIconClsText'>icon-2</div>
				</div>
				<div class='defTradeIconClsArea' refCls='icon-im'>
					<div class='defIconCls icon-im'></div>
					<div class='defIconClsText'>icon-im</div>
				</div>
				<div class='defTradeIconClsArea' refCls='icon-ex'>
					<div class='defIconCls icon-ex'></div>
					<div class='defIconClsText'>icon-ex</div>
				</div>
				<div class='defTradeIconClsArea' refCls='icon-nt'>
					<div class='defIconCls icon-nt'></div>
					<div class='defIconClsText'>icon-nt</div>
				</div>
				<div class='defTradeIconClsArea' refCls='icon-fi'>
					<div class='defIconCls icon-fi'></div>
					<div class='defIconClsText'>icon-fi</div>
				</div>
				<div class='defTradeIconClsArea' refCls='icon-mt'>
					<div class='defIconCls icon-mt'></div>
					<div class='defIconClsText'>icon-mt</div>
				</div>
				<div class='defTradeIconClsArea' refCls='icon-mt700'>
					<div class='defIconCls icon-mt700'></div>
					<div class='defIconClsText'>icon-mt700</div>
				</div>
			</div>					
		</div>
	</div>
</div>


<script>
/*
    $.parser.parse($("#defTradeIconClsMenu"));
    
	//添加右键菜单事件
	$('.defMasterMenuIconAreaWrap').bind('contextmenu.xcpApp',function(e){
		var trade= $(e.target).closest('div.tradeIconArea');
		if(trade && trade.length >=1){
			curContextmenuTrade = trade;
			e.preventDefault();
			$('#defTradeIconClsMenu').menu('show', {
				left: e.pageX,
				top : e.pageY
			});
		}
	});

	$('.defTradeIconClsArea').bind('click.xcpApp',function(e){
		 var refCls = $(this).attr('refCls');
		 if(refCls && curContextmenuTrade){
			 var  iconCls   =  curContextmenuTrade.attr('refIconCls'); 
			 if(iconCls){
				 curContextmenuTrade.find('div.tradeIcon').removeClass(iconCls).addClass(refCls);
				 curContextmenuTrade.attr('refIconCls',refCls);
				 
				 $('#defTradeIconClsMenu').menu('hide');
				 curContextmenuTrade = null;
			 }
		 }
	});
*/
</script>