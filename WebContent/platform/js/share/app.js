/**
 * Created by gongyong 参考网络上夏悸编写的的桌面管理系统风格
 * 2013-05-20 
 * author gongyong 于2013-09重新整理
 */

(function ($) {
	
	var northHeight = 85,
	    southHeight = 34;
	    menuNavigation = 22,
	    menuNavigationWidth = 210;
	    widgetsHeight  = 10;//120
	    isChangeDef    = true;     //是否作了定制处理，如果是则退出时统一作保存处理
	    isShortDef     = false;    //分页排序标识 true 为可以设置配需
	    curContextmenuTrade = null; //当前右键菜单正在处理的交易图标
	    
	var poolWins = [], poolSize = 8;
	
	//用户自定义的设置
	var userDefData = {};
	var loaded = false;
	var curMasterMenuIconAreaId   = 'MasterMenuIconArea-1';
	var curMasterMenuNavigationId = 'MasterMenuNavigation-1';
	var curMastNt = 1;//分页数总数量
	var menuIcosList = {};
	var tasks = [], currTaskId = null;
	
	//获取主界面中间区域的高度和宽度
	function getCenterRegionHW(){
		var h = $(window).height() - northHeight - southHeight, w = $(window).width();
		return {
			top : northHeight,
			left: 0,
			width:  w,
			height: h < 0 ? 0 : h			
		};
	}
	
	//设置自定义图标区域的高度和宽度，设置导航栏的左侧边距
	function setMasterMenuAreaHW(){
		var  hw = getCenterRegionHW(); 
		
		//设置自定义区域的总高度
		$("#mainCenterArea").css({ height : hw.height + 'px'});
		//设置交易图标定制区域的高度
		var defHeight = hw.height - menuNavigation - widgetsHeight;
		
		$("div.defMasterMenuIconAreaWrap").css({height : (defHeight < 0 ? 0 : defHeight) + 'px'});
	
		masterMenuArrowDisp(hw);
		
		//设置自定义菜单导航按钮的左侧边距
		var left = Math.ceil((hw.width  -  menuNavigationWidth) / 2 );
		left = left < 0 ? '0px' : left + 'px';
		$(".defMasterMenuNavigation").css({paddingLeft : left});
		
		var s = (hw.width - 160 - 0) % 164,w=0;
		$("#defWidgetsContent").css({width : (hw.width - 185 - s) + 'px'});
		
		//改变自适应图标大小
		if(hw.width <= 1024){
			$('div.tradeIconArea').each(function(e){
				if(!$(this).hasClass('adustTradeIconArea')){
					$(this).addClass('adustTradeIconArea');
				}
			});
		}else{
			$('div.tradeIconArea').each(function(e){
				if($(this).hasClass('adustTradeIconArea')){
					$(this).removeClass('adustTradeIconArea');
				}
			});
		}
		
		/*$("div.defWidgetsAreaWrap").find('.toogleBtn').each(function(e){
			if($(this).hasClass('down')){
				$(this).css({left: ($(window).width() - 40) + "px"});
			}else{
				$(this).css({left:  "0px"});
			}
		});*/
	}
	
	function masterMenuArrowDisp(hw,flag){
		hw = hw || getCenterRegionHW(); 
		//设置交易图标定制区域的高度
		var wrapHeight = $("#defMasterMenuIconAreaWrap").height();
		var curH       = $("#" + curMasterMenuIconAreaId)[0].offsetHeight;
		var curOt      = $("#" + curMasterMenuIconAreaId)[0].offsetTop;
		var menuUp     = $("#defMasterMenuIconAreaWrap").find('.menuUp');
		var menuDown   = $("#defMasterMenuIconAreaWrap").find('.menuDown');
		//重置TOP
		flag === true ? $("#" + curMasterMenuIconAreaId).css({top : '0px'}) : '';
		//设置箭头的样式
		curH > wrapHeight ? menuUp.show() : menuUp.hide();
		Math.abs(curOt) > 0 ? menuDown.show() : menuDown.hide();
	}
	
	
	//创建桌面任务
	function createTask(task){
		if(task.name || task.txnNo){
			 var taskUuid = getExistTaskUuid(task);
			 if(taskUuid == null || taskUuid == ''){
				 if(tasks.length >= 8){
					  $.messager.alert($xcp.i18n('sys.tip'), $xcp.i18n('sys.appTips'), "info");
					  //此时也需要释放任务 TX 2015-2-9
					  realseTask(task);
		        	  return ;
				 }
				 
				 taskUuid = task.id || $xcp.def.getTimeUuid() , hw = getCenterRegionHW(); 
				 
				 var win = getWindow(taskUuid);
				 var widWidth = $(window).width();
				 
				 $(win).parent().css({borderWidth: 0, padding : 0, left : '0px', top : northHeight + 'px', height : hw.height, width : hw.width, borderRadius : 0});
				 $(win).css({height:'100%',width:'100%'});					
				 
				 //设置任务ID并加入到任务数组中
				 task.taskUuid = taskUuid; 
				 
				 //根据交易名称的长短控制打开交易窗体的个数 2015-1-16 TX
				 var startIcnWidth = $("#taskStart").width();  //左下角开始按钮的宽度
				 var taskRightWidth = $("#taskRight").width(); //右边快捷区域的宽度
				 var taskBarWidth = $("#taskBars").width();    //打开的任务窗口的总宽度
				 
				 var canUseWidth = widWidth - startIcnWidth - taskRightWidth - taskBarWidth;
				 
				 //150为9个字长度的交易，170为150+框之间的大概间隔,为了避免生成的任务框覆盖右边的快捷区域（2015-01-26改）
				 if(canUseWidth < 170){
					 $.messager.alert($xcp.i18n('sys.tip'), $xcp.i18n('sys.appWidthTips'), "info");
					 //此时也需要释放任务 TX 2015-2-9
					 realseTask(task);
		        	 return ;
				 }
				 
				 tasks.push(task);
				 appendTaskItem(task);
				 
				 if(task.groupTrade === "Y"){
					//组合交易或查询等
					$.get($xcp.def.getFullUrl(task.url) + "&uuid=" + taskUuid,function(data){
						 win.html(data);	
					});
				 }else{
					 if(task.hasLeft == undefined || task.hasLeft == ""){
						 task.hasLeft = 'Y';
					 }
					 var html = "";
					 if(task.url == ''){
						 html +=  "<div>页面正在建设中......</div>";
					 }else{
						 html +=  "<iframe id='centeriframe" + taskUuid +"' frameborder='0'  style='width:100%;height:100%;overflow-x:hidden;overflow-y:auto'></iframe>";
					 }
				 	 $(html).appendTo($(win));
					 //给iframe 添加src 属性
					 $("#centeriframe"+taskUuid).attr("src",$xcp.def.getFullUrl(task.url) + "&uuid="+ taskUuid);
				 }				 
			 	 //打开窗口
				 $(win).window("open");
				 
				 if(task.url){
					 $xcp.startPorcess();					 
				 }
			 }
			 //选择某个任务栏Item并展示其WINDOW窗口
			 selectTaskBarItem(taskUuid);
		}
	}
	
	function realseTask(task){
		 if (task.taskId != '' && task.taskId) {
	    		var taskUrl = $xcp.def.getFullUrl('./common.do?method=back&taskId=' + task.taskId);
	    		var ajaxOpt = {
	    			url:taskUrl,
	    			async:false,
	    			error:function (XMLHttpRequest, textStatus, errorThrown) {
						$.messager.alert("", textStatus || errorThrown, "error");
					}
	    		};
	    		$xcp.ajax(ajaxOpt);
	  }
	}
	
	// 复核意见 取消原因 授权审批信息对话框
	function createDialog(opts){
		var taskUuid = opts.taskUuid || '';
		var html = 	'<div id="'+taskUuid+'-frmDialog"  class="easyui-dialog" style="width:480px;height:280px;padding:10px;text-align:center;">';
			html += 	'<textarea   class="pubFrmDialogOpinion" cols=75 rows=10 style="border: 1px solid #999999;" ></textarea>';
			html +=      '<span style=" margin-left: 280px;">'+$xcp.i18n("app.inputLen").format(opts.inputLen)+'</span>';
			html += '	<div id="dlg-buttons">';
			html += 		'<a  class="easyui-linkbutton  pubDialogSaveBtn" data-options="plain:true">'+$xcp.i18n('app.confirm')+'</a>';
			if(opts.cancel !== false){
				html += 	'<a id="frm-DelClose" class="easyui-linkbutton pubDialogCloseBtn" data-options="plain:true">'+$xcp.i18n('app.cancel')+'</a>';
			}
			html += 	'</div>';
			html += '</div>';
		
		$(html).appendTo('body');
		$.parser.parse($("#"+taskUuid+"-frmDialog"));
		
		$('#'+taskUuid+'-frmDialog').dialog({
			title   : opts.title,
			modal   : true,
			iconCls : 'icon-tip',
			buttons : '#dlg-buttons',
			onClose     : function(){
				$(this).dialog('destroy');
			}
		});
		
		$('#'+taskUuid+'-frmDialog').find('a.pubDialogSaveBtn').bind('click',function(){
			var frmDialogVal = $('#'+taskUuid+'-frmDialog').find('.pubFrmDialogOpinion').xcpVal();
			//opts.inputLen
			if(opts.inputLen && (frmDialogVal.length > opts.inputLen) ){
				$.messager.alert($xcp.i18n("sys.tip"),$xcp.i18n("app.inputLenTip"), "info");
				return false;
			}else if(frmDialogVal.length == 0){
				$.messager.alert($xcp.i18n("sys.tip"),$xcp.i18n("app.noInputTip"), "info");
				return false;
			}
			if(opts.method){ //调用createDialog方法时传入回调方法
				$('#'+taskUuid+'-frmDialog').dialog('destroy');
				var target = null;
				if(opts.wTarget != undefined){ //桌面中任务列表界面target，参考(pendingTask.js文件中wTarget的使用)
					target = $('#'+opts.wTarget)[0];
				}else{
					target = $('#centeriframe'+taskUuid)[0];
				}
				
				if(target && target.contentWindow.$xcp.formPubMgr[opts.method]){//publicForm.js中定义的method
					target.contentWindow.$xcp.formPubMgr[opts.method](frmDialogVal,opts.name || "");
				}else if(target && target.contentWindow.$xcp.menuMgrFun[opts.method]){//formMenuFun.js中定义的method
					target.contentWindow.$xcp.menuMgrFun[opts.method](frmDialogVal,opts.name || "",opts);
				}else{
					if(opts.data){
						opts.method(frmDialogVal,opts.data,opts);
					}else{
//						window[opts.method](frmDialogVal,opts);// window[opts.method] is not a function
//						opts.method(frmDialogVal,opts);
						target.contentWindow[opts.method](frmDialogVal,opts);
					}
				}					
			}else{
				$('#'+taskUuid+'-frmDialog').dialog('destroy');
			}
		});
		
		$('#'+taskUuid+'-frmDialog').find('a.pubDialogCloseBtn').bind('click',function(){
			$('#'+taskUuid+'-frmDialog').dialog('destroy');
		});
		var pubOpin = $('#'+taskUuid+'-frmDialog').find(".pubFrmDialogOpinion");
		if(opts.value){
			pubOpin.val(opts.value);
		}
		
		if(opts.isReadonly == true){
			pubOpin.attr("readonly","readonly").addClass("P");
		}else{
			pubOpin.focus();
		}
	}
	
	//创建WINDOW弹出窗口，不添加到任务栏里面
	function createPopWindow(opts){
		var opt = $.extend({
			width : 800,
			height: 600
		},opts || {});
			
	    if(opt.src && opt.data){
	    	opt.src += opt.src.indexOf("?") != -1 ?"" : "?1=1";
			$.each(opt.data||[],function(i,o){
				opt.src += "&" + i + "=" +encodeURI(encodeURI(o.replace(new RegExp("\"", 'g'),"\\\"")));
			});
		}
	    
		var taskUuid = opt.taskUuid || $xcp.def.getTimeUuid(),id = taskUuid + "-popWindow";

		if($("#" + id).length > 0){
			$("#" + id).window('open');
			if(opt.hasFrame !== false && opt.src ){
				$("#" + id).find('iframe')[0].src = opt.src;
			}
		}else{
			var html  = '<div id="'+ id + '"  class="easyui-window" style="width:' + opt.width +'px;height:' + opt.height + 'px;padding:0px">';
			if(opt.content){
		    	html += "<div>" + opt.content + "</div>";
		    }
		    if(opt.hasFrame !== false){
		    	html += 	"<iframe id='centeriframe" + taskUuid + "' src='" + (opt.src || '') + "' frameborder='0'  style='width:100%;height:100%;overflow-x:hidden;overflow-y:auto'></iframe>";
		    }
			html += '</div>';
			 
			$(html).appendTo('body');
			
			$('#'+taskUuid+'-popWindow').window({
				 title   : opt.title || '',
				 modal   : true,
				 maximizable : true,
				 closable    : true,
				 collapsible : false,
				 minimizable : false,
				 inline      : true,
				 onClose     : function(){
					$(this).window('destroy');
				 },			
				 onBeforeDestroy : function(){
					
					  var frame = $('iframe', this);
					  if (frame.length > 0) {
						  frame.unbind();
						  
						  if( $(frame).contentDocument){//$(frame).length == 1 &&
						     frame.contentDocument.write('');
							 frame.contentWindow.close();
							 frame.src = '';
						  }
						  frame.remove();
						  
						  if ($.browser.msie) {
							  CollectGarbage();
						  }
					  }
				 },
				 onOpen : function(){
					 
				 }
			});
		}
	}
	
	//添加任务链接到任务栏
	function appendTaskItem(task){
		 
		 var html  = "<div class='task current' id='taskBarId-" + task.taskUuid + "' taskWinId='" +  task.taskUuid + "'>" ;
	         html += 	"<div>" + task.name +"</div>";
	         html +=   "<div class='close'></div>";
	         html += "</div>";
	      
	      var taskBars = $("#taskBars");
	      taskBars.find(".task").each(function(i){
	    	   $(this).removeClass("current");
	      });

	      taskBars.append(html);
		  
		  var taskBar = $("#taskBarId-" + task.taskUuid);
		  
		  if(task.groupTrade === 'Y'){
			  taskBar.attr("groupTradeTask",'Y');
		  }
	     
		  taskBar.bind("click.rwlUuis",function(e){
	    	    //单击切换
	    	    if($(this).attr("taskWinId") !== currTaskId){
	    	    	 $("#" + currTaskId).window('close');
	    	    	 $("#taskBarId-" + currTaskId).removeClass("current");
	    	    	 
	    	    	 $(this).addClass("current");
	    	    	 $("#" + $(this).attr('taskWinId')).window('open');
	    	    	 
	    	    	 var groupTradeTask =   $(this).attr("groupTradeTask");
    				 if(groupTradeTask === "Y"){
    					$("#" + $(this).attr('taskWinId')).find('div.easyui-tabs').each(function(i,tab){
    						try{
	    						var panel = $("#" + $(tab).attr("id")).tabs('getSelected');
	    						var dagaGridId =  $(panel).attr('refDataGridId'); 
	    						if(dagaGridId){
	    						  	$(panel).find('iframe')[0].contentWindow.$("#" + dagaGridId).datagrid('reload');
	    						}
    						}catch(e){
    							
    						}
    					 });
    				 }
	    	    	 
	    	    	 currTaskId = $(this).attr('taskWinId');
	    	    }else{
	    	    	if($(this).is(".current")){
	    	    		 $("#" + $(this).attr('taskWinId')).window('close');
	    	    		 $(this).removeClass("current");
	    	    		 currTaskId = null;
	    	    	}else{
	    	    		 $("#" + $(this).attr('taskWinId')).window('open');
	    	    		 $(this).addClass("current");
	    	    		 currTaskId = $(this).attr('taskWinId');
	    	    	}
	    	    }
	    	    //窗口变化任务列表 lsm 04/25
	    		$(window).trigger("resize");
	    	    //阻止事件冒泡
	    		e.stopPropagation(); e.preventDefault();	
	      }) ;
	      
		  taskBar.find('.close').bind("click.rwlUu21",function(e){
	    	  var _this = this;
	    	   $.messager.confirm($xcp.i18n("tip"), $xcp.i18n("sys.closeWindow"), function(r){
					if (r){
						var curId = $(_this).parent("div").attr('taskWinId');
			    	    //双击关闭任务并删除任务栏Item
						$("#" + curId).window('destroy');
			    	    $(_this).parent("div").remove();
			    	    
			    	    $.each(tasks,function(i,o){
							if( o.taskUuid == curId ){
								tasks.splice(i,1); return false;					
							}
						});
			    	    currTaskId = null;
					}
				});
	    	    //阻止事件冒泡
	    	   e.preventDefault(); e.stopPropagation(); 	
	      });
	      
	      if(currTaskId == null){
	    	  currTaskId = task.taskUuid;
		  };
	}
	
	function selectTaskBarItem(taskUuid){
		 if(currTaskId == taskUuid) return false;
		 
		 $("#taskBarId-" + currTaskId).removeClass("current");
		 $("#" + currTaskId).window('close');
		 
		 $("#taskBarId-" + taskUuid).addClass("current");
		 $("#" + taskUuid).window('open');

		 currTaskId = taskUuid;
	}
	
	//设置任务栏的宽度
	function setTaskBarWidth(){
		
	}
	
	//从任务栏中列表中移除某个任务ID的链接
	function removeTaskItem(taskUuid){
		var src =  $("#taskBarId-" + taskUuid);
		if(src && src.size() > 0){
			
			var curId =  src.attr('taskWinId');
			$("#" + taskUuid).window('destroy');
	  	    src.remove();			
	  	    $.each(tasks,function(i,o){
				if( o.taskUuid == curId ){
					tasks.splice(i,1); return false;					
				}
			});	  	 
	  	    if(taskUuid == currTaskId){
	  	    	currTaskId = null;
	  	    }	 	 
		}
	}
	
	//清除所有任务栏当中的任务
	function closeAllTask(){
		$.each(tasks,function(i,o){
			removeTaskItem (o.taskUuid);
			tasks = [];
		});
	}
	
	//回到首页
	function breakHome(){
		if(currTaskId != null){
			$("#taskBarId-" + currTaskId).removeClass("current");
			$("#" + currTaskId).window('close');
		}
	}
	
	//查看任务栏是否有任务 
	function isTaskRw(){
		if(tasks.length > 0)
			return true;
		return false;
	}
	
	function isCurrentOpenTask(){
		if(tasks.length > 0){
			if($('div.current','#taskBars').length > 0 ){
				return true;
			}
		}
		return false;
	}
	
	//根据任务名称或流水号查找，返回任务UUID
	function getExistTaskUuid(task){
		//同一状态的经办任务不允话多次登入
		if(!task.id && task.txnNo){
			task.id = task.txnNo + "-" + (task.taskFlag || 'x');
		}
		var rs = null;
		$.each(tasks,function(i,o){
			if(task.id && task.id == o.taskUuid){
				rs = o.taskUuid;
			}
		});
		return rs;
	}
	
	//关闭所有任务，并显示某个ID的任务
	function selectExistTask(taskUuid){
		$.each(tasks,function(i,o){
			if(o.taskUuid !== taskUuid){
				//$("#" + taskUuid).window('close');
			}
		});
		//打开任务窗口并移到指定位置
		$("#" + taskUuid).window('open');
		$("#" + taskUuid).window('move',{
			left : '0px', top : northHeight +'px'
		});
	}
	
	function addEventOfDesktopIcon(eles){
		//绑定单击打开任务事件
		eles.bind('dblclick.tradeIconArea',function(e){
	      	var tradeNo = $(this).attr("refTradeNo");
	      	
	      	if(tradeNo != ''){
			  var task = {
					  name      : $(this).attr('refName'),
					  tradeNo   : tradeNo,
					  //url     : $xcp.def.getFullUrl($(this).attr('refUrl')) ,
					  url       : $(this).attr('refUrl') || '' ,
					  id        : $(this).attr('refId')  || '',
					  params    : $(this).attr('refMethodParam'),
					  hasLeft   : $(this).attr('refHasLeft'), 
					  tradeFlag : $(this).attr('refTradeFlag'),
					  groupTrade: $(this).attr('refGroupTrade')
			  };
			  
			  $xcp.formPubMgr.openTrade(task);
	      	}
	      	//阻止事件冒泡
	      	e.stopPropagation(); e.preventDefault();	
		});
		
		//绑定单击删除图标事件
		
		eles.find('.close').bind("click.xcpApp",function(e){
			var _this = this;
			$.messager.confirm($xcp.i18n("sys.tip"), $xcp.i18n("app.deleteMenu"), function(r){
			     if(r){
			    	 $(_this).parent().remove();
			    	 //isChangeDef = true;
			    	 //重新计算上下箭头是否显示
					 masterMenuArrowDisp();
			     }
			});
			//阻止事件冒泡
	      	e.stopPropagation(); e.preventDefault();	
		});
		return eles;
	}
	
	function loadDesktopDefMenu(id,data){
		 var html = "";
		 var  hw = getCenterRegionHW(); 
		 
		 var adustClass = hw.width <= 1024 ? " adustTradeIconArea" : "";
		 
		 if($xcp.def.isJson(data)){
			    if(data["homeKey"] == "taskmgr"){
			    	html = "";
			    }else{
					html = "<iframe id='centeriframe_"+data["homeKey"]+"' frameborder='0' src = ''"+ 
		   				"style='width:"+(hw.width - 20)+"px;height:"+(hw.height- southHeight - 26)+"px;overflow-x:hidden;overflow-y:auto'></iframe>";
			    }
		 }else{
	 		 $.each(data,function(i,o){
	 			  if(o.fixedPostionMenu == "true" || o.fixedPostionMenu == "Y"){
	 				  if(o.refMenuId){
	 					  $("div[refId=" + o.refMenuId + "]").attr("refIconCls",o.iconCls || 'icon-impCredit');
	 					  $("div[refId=" + o.refMenuId + "]").find('.tradeIcon').addClass(o.iconCls || 'icon-impCredit');
	 				  }
	 			  }else{
					  html += "<div class='tradeIconArea" + adustClass 
					  			+ "' refTradeFlag='" + (o.tradeFlag || '') 
					  			+ "' refHasLeft='" + (o.hasLeft || '' ) 
					  			+ "' refTradeNo='" + (o.tradeNo || '') 
					  			+ "' refName='" + (o.name || '') 
					  			+ "' refUrl='" + (o.url || '') 
					  			+ "' refIconCls ='" + (o.iconCls || '') 
					  			+ "' refMenuId ='" + (o.refMenuId || '') 
					  			+ "' refMethodParam ='" + (o.methodParam || '') + "'>";
					  html += 	"<div class='tradeIcon  " + (o.iconCls || 'icon-impCredit') + "'></div>";
					  
					  var str = "";
					   
					  if((o.name || o.tradeNo || '').length >= 10){
						  str = 'style="font-size:12px"';
					  }
					  html += 	"<div class='tradeText' "+str+">" + (o.name || o.tradeNo || '')  + "</div>";
					  html += 	"<div class='close'></div>";			  
					  html += "</div>";
	 			  }
			 });
		}
        $("#" + id).append(html);
	}
	
	//通过AJAX方式获取用户自定义数据
	function getUserDefData(target){
		var opts = $.data(target, 'xcpApp').options;
		if (opts.loadUrl.app && !loaded) {
			var ajaxOpt = {
				url:opts.loadUrl.app,
				error:function (XMLHttpRequest, textStatus, errorThrown) {
					$.messager.alert("", textStatus || errorThrown, "error");
				}
			};
			userDefData = $xcp.ajax(ajaxOpt,"object");
			if (userDefData && null != userDefData) {
				$xcp.def.setUserCurrentTheme(userDefData.themes || '');
				$xcp.def.setUserCurrentQuickbar(userDefData.quickbar ||{});
				$xcp.def.setUserViewRptMethod(userDefData.otherDef || {});
			}
		}
	}
	
	//获取用户自定义的图标
	function getDesktopIcons(context){
		var rs  =[];
		$('div.tradeIconArea',context).each(function(i,o){
			 //if($(o).attr('fixedPostionMenu') != 'true'){
				 rs.push({
					 name        : $(o).attr('refName')        || '' ,
					 tradeNo     : $(o).attr('refTradeNo')     || '' ,
					 url         : $(o).attr('refUrl')         || '' ,
					 iconCls     : $(o).attr('refIconCls')     || '' ,
					 methodParam : $(o).attr('refMethodParam') || '' ,
					 hasLeft     : $(o).attr('refHasLeft')     || '' ,
					 tradeFlag   : $(o).attr('tradeFlag')      || '' ,
					 refMenuId   : $(o).attr('refMenuId') || $(o).attr('refId') || '',
					 fixedPostionMenu : $(o).attr('fixedPostionMenu') || 'N'
				 });
			 //}
		});
		return rs;
	}
	
	//获取用户菜单信息
	function getDeaskMemui(){
		var jso = {};
		/*$("div[id^='MasterMenuIconArea']").each(function(i,o){
			if($("#MasterMenuNavigation-"+(i+1)).attr("quickbarkey") != undefined ){
				jso["desktop"+(i+1)] = $xcp.toJson({"homeKey":$("#MasterMenuNavigation-"+(i+1)).attr("quickbarkey")});
			}else{
				jso["desktop"+(i+1)] = getDesktopIcons($(this));
			}
		});*/
		$("div[id^='MasterMenuNavigation']").each(function(i,o){
			if($(this).attr("quickbarkey") != undefined ){
				jso["desktop"+(i+1)] = $xcp.toJson({"homeKey":$(this).attr("quickbarkey")});
			}else{
				var str = $(this).attr("id").split("-");
				jso["desktop"+(i+1)] = getDesktopIcons($("#MasterMenuIconArea-"+str[1]||""));
			}
		}); 
		//alert($xcp.toJson(jso));
		return jso;
	}
	
	//生成用户自定义的JSON格式的数据
	function generateUserDefData(){
		
		//user defined table field :==> userId,menuIcons,widgets,themes,i18nLang,otherDef
		var userDefData = {};
		 
		userDefData.userId   = $xcp.getUSERID();
		userDefData.themes   = $xcp.def.getUserCurrentTheme();
		userDefData.i18nLang = $xcp.def.getEAP_LANGUAGE() || "";
		userDefData.otherDef = $xcp.userTradeGlobalDefine.options || {};
		
		userDefData.widgets  = {};
		
		userDefData.menuIcons = getDeaskMemui();
		
		userDefData.quickbar  = $xcp.toolsMgr.getUserDataQuickbar();
		
		return userDefData;
	}
	
	/**
	 * 根据菜单数据生成分页区域lsm2
	 */
	function loadIconNavi(){
		var menuIcons = $xcp.parseJson(userDefData.menuIcons||{});
		var quickbar = $xcp.parseJson(userDefData.quickbar||{});
		
		str = "";
		var str1 = "<div id='MasterMenuNavigation-";
		var str2 = "' class='iconNavigation'  refIconAreaId='MasterMenuIconArea-";
		
		$.each(menuIcons,function(i,o){
			if(o.length == 0 || o == "") return ;
			o = typeof o == "string" ? $xcp.parseJson(o) : o;
			
			if($xcp.def.isJson(o)){
				if((o.homeKey && quickbar[o.homeKey] && quickbar[o.homeKey]["home"] != "false" && quickbar[o.homeKey]["home"] != false) || quickbar ==undefined){
					str += str1+curMastNt+str2+curMastNt+"' quickbarkey='"+o.homeKey+"' i18nId='"+ (o.homeKey||"") +"'></div>";
					quickbar[o.homeKey]["isExist"] = true;
				}else{
					o = null;return ;
				}
			}else{
				str += str1+curMastNt+str2+curMastNt+"' i18nId='"+ $xcp.i18n("sys.menuPage") +"'></div>"; 
			}
			menuIcosList["desktop"+curMastNt] = o;
			curMastNt++;
		});
		
		$.each(quickbar,function(i,o){
			if(o["isExist"] == true || o['home'] == false || o['home'] == "false") return ;
			str += str1+curMastNt+str2+curMastNt+"' quickbarkey='"+i+"' i18nId='"+ (i||"") +"'></div>"; 
			menuIcosList["desktop"+curMastNt] = {"homeKey":i};
			curMastNt++ ;
		});
		
		str = str == "" ? str1+curMastNt+str2+curMastNt+"'></div>" : str; 
//		$(".defMasterMenuNavigation").html(str);
		$("#masterMenuAdd").before(str);
		str = "";
		for ( var int = 1; int <curMastNt; int++) {
			str += "<div id='MasterMenuIconArea-"+int+"' class='defMasterMenuIconArea'></div>";
		}
		str = str == "" ? "<div id='MasterMenuIconArea-1' class='defMasterMenuIconArea'></div>" : str; 
		$(".menuUp").before(str);
		
		//分页区域加减事件绑定
		$("#masterMenuAdd").bind("click",function(){
			var siz = $(".defMasterMenuNavigation div[id^=MasterMenuNavigation-]").length;
			var pagesize = configOveralObj["mainPage"].pageSize;
			if (siz<pagesize){
				$(".menuUp").before("<div id='MasterMenuIconArea-"+curMastNt+"' class='defMasterMenuIconArea'></div>");
				$("#masterMenuAdd").before("<div id='MasterMenuNavigation-"+curMastNt
						+"' class='iconNavigation' isEndit='true' refIconAreaId='MasterMenuIconArea-"+curMastNt+"'></div>");
				curMastNt++;
				masterMenuAddClickFun();
			}
		});
		
		//分页区域加减事件绑定
		$("#masterMenuDel").bind("click",function(){
			//lsm
			$.messager.confirm($xcp.i18n("paramMar.tixck"),$xcp.i18n('validate.messager'),function(r){   
			    if (r){
			    	var source = $(".defMasterMenuNavigation").find(".current[id^='MasterMenuNavigation']");
					$(source).remove();
					$("#"+$(source).attr("reficonareaid")).remove();
					source = $(".defMasterMenuNavigation").find("[id^='MasterMenuNavigation']");
					if(source.length != 0 ){
						source.eq(0).addClass("current");
						$("#"+$(source).eq(0).attr("reficonareaid")).addClass("current");
					}
			    }
			});
			
		});
		$("#masterMenuUpdate").bind("click",function(){//startCust
			$('body').xcpApp('setIsShortDef',true);
			$(this).hide();
			$("#masterMenuOk").show();
		});
		
		$("#masterMenuOk").bind("click",function(){ 
			$('body').xcpApp('setIsShortDef',false);
			$(this).hide();
			$("#masterMenuUpdate").show();
			setUserDefMenu(getDeaskMemui()); 
		});
		//setDesktopMenuIcondel($("#masterMenuDel"));//删除容器可删除
		
		function setUserDefMenu(menuicons){		
			if(menuicons){
				$xcp.ajax({
					url:$xcp.def.getFullUrl('./menu.do?method=savaMenuicons'),
					data :{"user_menuicons":$xcp.toJson(menuicons)}
				},null,null,function(rs,result){
				});
			}
		}
		
		addTitleForMasterMenu();
	}
	
	/**增加主界面 title tooltip**/
	function addTitleForMasterMenu(){
		$.fn.xcpApp("createToolTop",[
			      		               {obj:"masterMenuUpdate",center:$xcp.i18n("param.setUp")},
			      		               {obj:"masterMenuOk",center:$xcp.i18n("param.confirm")},
			    		               {obj:"masterMenuDel",center:$xcp.i18n("paramMar.shanc")},
			    		               {obj:"masterMenuAdd",center:$xcp.i18n("paramMar.xinz")}
			    		               ]); 
		var refName  = "";
		$("div[i18nid]","#mainCenterArea").each(function(){
			if($(this).attr("quickbarkey") != undefined){
				refName = $("div[reftradeno='"+$(this).attr("i18nid")+"']","#taskRight").attr("refname")||"";
				$.fn.xcpApp("createToolTop",[
				      		               {obj:$(this),center:refName}
				    		                ]); 
			}else{
				$.fn.xcpApp("createToolTop",[
				      		               {obj:$(this),center:$(this).attr("i18nid")}
				    		               ]);
			}
		});
	}
	//初始化桌面管理布局
	function initLayout(target){
		//当主界面窗口大小改变时，改变大小
		$(window).resize(function(){
			 setMasterMenuAreaHW();
			 var hw = getCenterRegionHW(); 
			 $("div.xcpTaskWindow").each(function(o){
				 $(this).parent().css({borderWidth: 0, padding : 0, left : '0px', top : northHeight + 'px', height : hw.height, width : hw.width, borderRadius : 0});
				 $('.easyui-layout',$(this)).layout();
			 });
		});
		
		loadIconNavi();//根据菜单数据生成分页区域lsm2
		
		bindPageLan(); //绑定点击页面事件
		//setMasterMenuAreaHW(); 延迟1s钟事件再去设置子div的高度，否则offsetHeight为0 by tanxi 20141127
		setTimeout(function(){setMasterMenuAreaHW();},1000);
	}
	
	function setIsShortDef(blean){
		isShortDef = blean;
		if(blean){
			$(".masterMenuDel,.masterMenuAdd").addClass("current");
		}else{
			bindPageLan();
			$(".masterMenuDel,.masterMenuAdd").removeClass("current");
			//defMasterMenuNavigation
			if($(".defMasterMenuNavigation .current").length == 0){
				$(".defMasterMenuNavigation .iconNavigation").eq(0).addClass("current").trigger("click");
			}
		}
	}
	
	//新增一页函数事件绑定
	function masterMenuAddClickFun(){
		setDesktopMenuIconNacigat($("div.iconNavigation[isEndit='true']"));
		$("div.iconNavigation[isEndit='true']").attr("isEndit","false");
	}
	
	function iconNacTagClick (_thisV){
		 var _this = $(_thisV);
		 if(_this.is('current')) return ;
		 var refId = _this.attr('refIconAreaId');
		 
		 if(_this.attr("quickbarkey") != undefined ){
			 var qaurObj = $("div[reftradeno='"+_this.attr("quickbarkey")+"']");
			 if(qaurObj.attr("refGroupTrade") == 'Y' && $("#" + refId).html() == ""){
				 $.get($xcp.def.getFullUrl(qaurObj.attr("refurl")),function(data){
					 $("#" + refId).html(data);		
				});	 
			 }else{
				 var sIframe = $("#centeriframe_"+_this.attr("quickbarkey"));
				 if(sIframe.attr("src") == ""){
					 sIframe.attr("src",qaurObj.attr("refurl"));
				 }; 
			 }
		 }
		 
		 $("#" + curMasterMenuIconAreaId).removeClass('current');
		 $("#" + refId).addClass('current');
		 //重新图标区域ID
		 curMasterMenuIconAreaId = refId;
		 
		 $("#" + curMasterMenuNavigationId).removeClass('current');
		 _this.addClass('current');
		 //重置导航ID
		 curMasterMenuNavigationId = _this.attr('id');
	}
	function bindPageLan(){
		//事件绑定
		$('div.iconNavigation').bind('click.iconNavigation',function(e){
			iconNacTagClick(this);
			//点击切换自定义页面时重新计算是否需要显示上下滚动  20141127 by tanxi
			setMasterMenuAreaHW();
		});
		
		$("#defMasterMenuIconAreaWrap").find('.arrow').bind('click.xcpApp',function(e){
			 var curMasterMenuIconArea = $("#" + curMasterMenuIconAreaId);
			 if(!curMasterMenuIconArea.is(":animated")){
				 var menuDown =  $("#defMasterMenuIconAreaWrap").find('.menuDown');
				 var menuUp   =  $("#defMasterMenuIconAreaWrap").find('.menuUp');
				
				 if($(this).hasClass('menuDown')){
					 var oft = curMasterMenuIconArea[0].offsetTop;
					 var t = oft < 0 ? oft < -130  ? 130 : Math.abs(oft) : 0;
					 if( t > 0){
						 curMasterMenuIconArea.animate({top : '+=' + t }, "slow",function(){
							 if(curMasterMenuIconArea[0].offsetTop == 0){	
								 menuDown.hide();
							 }	
							 if(menuUp.is(":hidden")){
								 menuUp.show();
							 }
						 });
					 }
				 }else{
					 curMasterMenuIconArea.animate({top : '-=' + 130 }, "slow",function(){
						 var oft = curMasterMenuIconArea[0].offsetTop;
						 var ch  = curMasterMenuIconArea.height();
						 //var oh  = curMasterMenuIconArea[0].offsetHeight;
						 var wh  = $("#defMasterMenuIconAreaWrap").height();
						 if(Math.abs(oft) + wh > ch){
							 menuUp.hide();
						 }
						 menuDown.show();
					 });
				 }
			 }
		});
	}
	//初始化任务栏
	function initTaskBar(target){
		//绑定事件，当点击开始按钮时，隐藏所有已打开的窗口
		$("#taskStart").bind("click.xcpApp",function(){
			$("#taskBars > div.current").each(function(i,o){
				 $("#" + $(this).attr('taskWinId')).window('close');
	    		 $(this).removeClass("current");
			});
		});
	}
	
	//初始化桌面自定义功能区域
	function initDeskTopIcon(target){
	    //用户图标定义
		//var iconDefData = $xcp.parseJson(userDefData['menuIcons']) || {};	
		
		//lsm2
		for ( var int = 1; int <curMastNt; int++) {
			loadDesktopDefMenu("MasterMenuIconArea-"+int, menuIcosList['desktop'+int] || []);
		}
		
		masterMenuArrowDisp();
		
		/*//添加右键菜单事件
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
		});*/

		$('.defTradeIconClsArea').bind('click.xcpApp',function(e){
			 var refCls = $(this).attr('refCls');
			 if(refCls && curContextmenuTrade){
				 var  iconCls   =  curContextmenuTrade.attr('refIconCls'); 
				 if(iconCls){
					 curContextmenuTrade.find('div.tradeIcon').removeClass(iconCls).addClass(refCls);
				 }else{
					 curContextmenuTrade.find('div.tradeIcon').addClass(refCls);
				 }
				 curContextmenuTrade.attr('refIconCls',refCls);
				 $('#defTradeIconClsMenu').menu('hide');
				 curContextmenuTrade = null;
				 //isChangeDef = true;
			 }
		});		
		
		//接受拖拽图标'defMasterMenuIconAreaWrap'
		//$("div.defMasterMenuIconArea").droppable({
		$("div.defMasterMenuIconAreaWrap").droppable({
			accept : '.tradeMenuDrag',
			onDragEnter:function(e,source){
				if(!$(source).hasClass('tradeMenuDrag')) return ;
			},
			onDragLeave:function(e,source){
				if(!$(source).hasClass('tradeMenuDrag')) return ;
			},
			onDrop:function(e,source){
				
				if(!$(source).hasClass('tradeMenuDrag')) return ;
				
				var sourceProxy = $(source).draggable('proxy');
				if(sourceProxy != null){
					var pl = sourceProxy.offset().left;
					var sl = $(source).offset().left;
					if(Math.abs(pl - sl) <= 10){
						return ;
					}
				}
			
		        var refName = $(source).attr('refName') || '';
		        if(refName != ''){
		        	if($("div.tradeIconArea[refName='" + refName+ "']").size() > 0 ) {
		        		$.messager.alert($xcp.i18n("sys.tip"), $xcp.i18n("sys.appExsists"), "info");
		        		return ;
		        	}
		        	var refTradeNo = $(source).attr('tradeNo') || '';
			        var refUrl     = $(source).attr('refUrl')  || '';
			        var refIconCls = $(source).attr('refIconCls')   || '';
			        var refParams  = $(source).attr('methodParam')  || '';
			        var hasLeft    = $(source).attr('refHasLeft')   || '';
			        var tradeFlag  = $(source).attr('refTradeFlag') || '';
			        var refMenuId  = $(source).attr('refMenuId') || '';
			     
					var  hw = getCenterRegionHW(); 
					var adustClass = hw.width <= 1024 ? " adustTradeIconArea" : "";
				        
					var html  = "<div class='tradeIconArea"   + adustClass + "' tradeFlag='" + (tradeFlag || '') 
									  + "' refHasLeft='" + (hasLeft || '') 
									  + "'refTradeNo='" + refTradeNo 
									  + "' refName='" + refName 
									  + "' refUrl='" + refUrl 
									  + "' refIconCls ='" + refIconCls 
									  + "' refMethodParam ='" + refParams 
									  + "' refMenuId='"+refMenuId+"' >";
					
					    html += 	"<div class='tradeIcon "  + (refIconCls || 'icon-impCredit') + "'></div>";
//					    alert((refName || refTradeNo || '').length );
					    var str = "";
					    if((refName || refTradeNo || '').length >= 11){
							  str = 'style="font-size:12px"';
						  }
					    html += 	"<div class='tradeText' "+str+">" + (refName || refTradeNo || '') + "</div>";
					    html += 	"<div class='close'></div>";
					    html += "</div>";
					    
				     
					$("#" + curMasterMenuIconAreaId).append(html);
				  
				    //新加入的图标设置为可拖拽
					setDesktopMenuDragAndDrop(addEventOfDesktopIcon($("div.tradeIconArea[refName='" + refName+ "']")));
					//addEventOfDesktopIcon($("div.tradeIconArea[refName='" + refName+ "']"));
					
					//isChangeDef = true;
					/*//重新计算上下箭头是否显示*/
					masterMenuArrowDisp();
					
		        }
			}
		});
		
		//addEventOfDesktopIcon($("div.defMasterMenuIconArea > .tradeIconArea"));
		//给桌面菜单添加事件处理
		setDesktopMenuDragAndDrop(addEventOfDesktopIcon($("div.tradeIconArea")));
		setDesktopMenuIconNacigat($("div.iconNavigation"));
	}
    
	function iconNaviganShow(_this){
		iconNacTagClick(_this);
		 //设置主界面箭头样式
		 //masterMenuArrowDisp(null,true);
		//$(_this).trigger("click");
		/*if($(_this).hasClass("current")) return ;
		var source = $(".defMasterMenuNavigation").find(".current[id^='MasterMenuNavigation']");
		$(source).removeClass("current");
		$("#"+$(source).attr("reficonareaid")).removeClass("current");
		$(_this).addClass("current");//lsm7
		$("#"+$(_this).attr("reficonareaid")).addClass("current");*/
	}
	//设置桌面图标可拖拽
	function setDesktopMenuIconNacigat(eles){
		eles.draggable({
			revert: true,
			proxy : 'clone',
			cursor: 'pointer',
			onBeforeDrag : function(e){
				if(isShortDef == false) return false;
				iconNaviganShow($(this));
				$("#masterMenuDel").addClass("masterMenuDelHer");
			},
			onStartDrag: function(e){
				if(isShortDef == false) return false;
				//return true;
			},
			onStopDrag : function(){
				//$(this).draggable('options').cursor='pointer';
				$("#masterMenuDel").removeClass("masterMenuDelHer");
			},
			onDrag     : function(e){
			}
		}).droppable({
			onDragEnter:function(e,source){
				$(source).draggable('options').cursor='pointer';
				$(source).draggable('proxy').css('border','1px solid red');
			},
			onDragLeave:function(e,source){
				$(source).draggable('proxy').css('border','1px solid #ccc');
			},
			onDrop:function(e,source){
				if($(this).attr('fixedPostionMenu') === 'true') return false;
				if($(source).attr('fixedPostionMenu') === 'true') return false;
				
				//根据两个元素的左侧位置的判断是插入到DROP元素的前面还是后面
				var o = $(this).offset(), s = $(source).offset();
				if(s.left < o.left || (s.left - o.left) < 20 ){
					$(this).after(source); 
				}else{
					$(this).before(source);
				}
			}
		});
	}
	//设置桌面图标可删除
	function setDesktopMenuIcondel(eles){
		eles.droppable({
			onDragEnter:function(e,source){
				$(source).draggable('options').cursor='pointer';
				$(source).draggable('proxy').css('border','1px solid red');
			},
			onDragLeave:function(e,source){
				$(source).draggable('proxy').css('border','1px solid #ccc');
			},
			onDrop:function(e,source){
				if($(this).attr('fixedPostionMenu') === 'true') return false;
				if($(source).attr('fixedPostionMenu') === 'true') return false;
				
				$(source).draggable('options').disabled = true;
				$(source).remove();
				$("#"+$(source).attr("reficonareaid")).remove();
				$(this).removeClass("masterMenuDelHer");
			}
		});
	}
	//设置桌面图标可拖拽
	function setDesktopMenuDragAndDrop(eles){
		var blean = true;
		eles.draggable({
			revert: true,
			proxy : 'clone',
			cursor: 'pointer',
			onBeforeDrag : function(e){
				if(isShortDef == false) return false;
				//if(isChangeDef== false) return false;
			},
			onStartDrag: function(e){
				if(isShortDef == false) return false;
				$("div.tradeIconArea[refName!='" + $(this).attr("refName")+ "']").css({"position":"static"});
				//if(isChangeDef== false) return false;
				//return true;
			},
			onStopDrag : function(){
				$(this).draggable('options').cursor='pointer';
				$("div.tradeIconArea[refName!='" + $(this).attr("refName")+ "']").css({"position":"relative"});
			},
			onDrag     : function(e){
			}
		}).droppable({
			onDragEnter:function(e,source){
				if(!$(source).hasClass('tradeIconArea')) return ;
				if($(this).is(":hidden")) return;
				
				$(source).draggable('options').cursor='pointer';
				$(source).draggable('proxy').css('border','1px solid red');
			},
			onDragLeave:function(e,source){
				if(!$(source).hasClass('tradeIconArea')) return ;
				if($(this).is(":hidden")) return;
				
				$(source).draggable('proxy').css('border','1px solid #ccc');
			},
			onDrop:function(e,source){
				if(!$(source).hasClass('tradeIconArea')) return ;
				if($(this).is(":hidden")) return;
				
				if($(this).attr('fixedPostionMenu') === 'true') return false;
				if($(source).attr('fixedPostionMenu') === 'true') return false;
				
				//根据两个元素的左侧位置的判断是插入到DROP元素的前面还是后面
				var o = $(this).offset(), s = $(source).offset();
				if(s.left < o.left || (s.left - o.left) < 20 ){
					$(this).after(source); 
				}else{
					$(this).before(source);
				}
			}
		});
	}
	
	//初始化辅助功能菜单
	function initTools(target){
		$xcp.toolsMgr.init();
	}
	
	//初始化小功能组件
	function initWidget(target){
		//widgets 右边滚动
		$("#widgetArrowNext").bind('click.xcpApp',function(){
			var content = $("#defWidgetsContent"); 
			var contentBar = $("#defWidgetsContentList");	
			var l  = contentBar[0].offsetLeft;
			var w  = content.width() + (l > 0 ? 0 : Math.abs(l));
			var wt = 0,nextW = 0,nextWidget = null;
		
			contentBar.find('div.widgetIconArea').each(function(){
				 var curW = $(this).width() + 20; wt += curW;
				 if(wt > w){
					 nextW = curW; nextWidget = this;
					 return false;
				 }
			});
			
			if(nextWidget != null && nextW > 0){
			   if(!contentBar.is(":animated"))
				    $("#defWidgetsContentList").animate({ left : '-=' + nextW }, "slow"); 
			}
		});		
		
		//widgets 左边滚动
		$("#widgetArrowPrev").bind('click.xcpApp',function(){
			var contentBar = $("#defWidgetsContentList");	
			var l  = contentBar[0].offsetLeft;
			var wt = 0,nextW = 0,nextWidget = null;
		    if( l < 0 ){
		    	l = Math.abs(l);
		    	contentBar.find('div.widgetIconArea').each(function(){
					 var curW = $(this).width() + 20; wt += curW;
					 if(wt > l){
						 nextW = curW; nextWidget = this;
						 return false;
					 }
				});
		    }
			if(nextWidget != null && nextW > 0){
				if(!contentBar.is(":animated"))
					contentBar.animate({ left : '+=' + nextW }, "slow"); 
			}
		});	
	
		/*$("div.defWidgetsAreaWrap").find('.toogleBtn').bind('click.xcpAppWidget',function(e){
			  if($(this).hasClass('down')){
				  $("div.defWidgetsAreaWrap,.defWidgetsBg").animate({bottom:'-=' + 80,left:'+=' + ($(window).width() - 40)},'slow');
				  $("div.defMasterMenuIconAreaWrap").animate({height:'+=' + 80},'slow');
				  $(this).removeClass('down').addClass('up').css({left:'4px'});
			  }else{
				  $("div.defWidgetsAreaWrap,.defWidgetsBg").animate({bottom:'+=' + 80,left:0},'slow');
				  $("div.defMasterMenuIconAreaWrap").animate({height:'-=' + 80},'slow');
				  $(this).removeClass('up').addClass('down').css({left: ($(window).width() - 40) + 'px'});
			  }
			  setTimeout(function(){masterMenuArrowDisp();},1000);
		});*/
	}
	
	/**动态添加tooltop*/
	function createToolTop(opts){
		if($.isEmptyObject(opts) && opts != null && opts.obj != undefined) return;
		$(typeof opts.obj == "string" ? $("#"+opts.obj) : opts.obj).tooltip({
			position: 'top',
			content : opts.center,
			onShow : function(){
				$(this).tooltip('tip').css({
					backgroundColor :  '#666',
					borderColor : '#666',
					color:'#fff'
				});
			}
		});
	}
	/**
	 * 初始化小功能组件事件绑定
	 */
	function bindWidgetElementEvent(target){
		  //快速查询
		  $xcp.widgetsMgr.initQuickQuery();
		  //任务管理 等双击打开任务的插件
		  $xcp.widgetsMgr.openTaskMgr();
		  
	}
	
	function initWinsPool(){
		for(var i = 0; i < poolSize; i++){
			createPoolWindow();
		}
	}
	
	function createPoolWindow(){
		var win = $("<div class='easyui-window xcpTaskWindow' data-options='resizable:false,doSize:false,noheader:true,border:false,modal:false,shadow:false' style='border-top-width: 0px'></div>").appendTo('body');
		$(win).window({
			 closed : true,			 
			 onBeforeDestroy : function(){
				 var frames = $('iframe', this);
				  if (frames.length > 0) {
					  $.each(frames,function(i,frame){
						  if($(frame)[0].contentWindow.$xcp &&  $(frame)[0].contentWindow.$xcp.formPubMgr){
							  $(frame)[0].contentWindow.$xcp.formPubMgr.formBack(true);
						  }
						  
						  $(frame).unbind();						
						  $(frame)[0].contentWindow.document.write('');
						  $(frame)[0].contentWindow.close();
						  $(frame)[0].src = '';
						  if($(frame)[0].remove){
							  $(frame)[0].remove();						 
						  }
						  if ($.browser.msie) {
							  CollectGarbage();
						  }	
						 
					  });
				  }			
				  $(win).window('close');				
				  $(win).empty();				
				  releaseWindow(win);				
				  return false;
			 },
			 
			 onOpen : function(){
				  $(this).window('move',{
						left : '0px', top : northHeight + 'px'
				  });
				
			 }
		 });	
		
	     if(poolWins.length < poolSize){
	    	 poolWins.push(win);
		 }
	     
	     return win;
	}
	
	function getWindow(taskUuid){
		if(poolWins.length == 0){
			createPoolWindow();
		}
		return poolWins.pop().attr("id",taskUuid);
	}
	
	function releaseWindow(win){
		if(poolWins.length >= poolSize ){
			$(win).remove();
		}else{
			$(win).removeAttr("id");
			poolWins.push(win);
		}
	}
	
	/**桌面初始化步骤*///lms1
	var initMethods = [getUserDefData,initLayout, initTaskBar, initDeskTopIcon, initTools, initWidget,bindWidgetElementEvent];	
	
	
	/**
	 * 桌面初始化
	 * @param target
	 * @param options
	 */
	function init(target, options) {
		var progress = $.messager.progress({
				title : options.lang.progress.title,
				msg   : options.lang.progress.msg,
				interval : null
		});
		
		var progressBar = $.messager.progress('bar'); 
		$.ajaxSetup({
			async : false
		});
		for (var i = 0;i<initMethods.length;i++) {
			var step = initMethods[i];
			progressBar.progressbar({
				text : options.lang[step.name]
			}).progressbar('setValue', ((parseInt(i) + 1) / initMethods.length) * 100);
			step.call(this, target);
		}
		
		$.messager.progress('close');
		
		$.ajaxSetup({
			async : true
		});
		
		options.onLoaded.call(target);
		//显示触发事件
		$("#MasterMenuNavigation-1").trigger("click.iconNavigation");
		setTimeout(function () {
			$('body').attr({ //禁用全局事件
				//oncontextmenu : 'return false',
				onselectstart : 'return false',
				ondragstart   : 'return false',
				onbeforecopy  : 'return false'
			});
		}, 500);
		
		$xcp.toolsMgr.bindBefUnLoad();
	}
	
	$.fn.xcpApp = function (options, params) {
		if (typeof options === 'string') {
			return $.fn.xcpApp.methods[options].call(this, params);
		}
		options = options || {};
		return this.each(function () {
			var state = $.data(this, 'xcpApp');
			if (state) {
				options = $.extend(state.options, options);
				state.options = options;
			} else {
				options = $.extend({}, $.fn.xcpApp.defaults, $.fn.xcpApp.parseOptions, options);
				$.data(this, 'xcpApp', {
					options : options
				});
			}			
			init(this, options);
			//初始化WIN窗口池 
			initWinsPool();
			
		});
	};
	
	$.fn.xcpApp.methods = {
			
		options : function () {
			return this.data().app.options;
		},		
		closeTask   : function (taskUuid,method) {
			removeTaskItem (taskUuid,method);
		},		
		closeAllTask: function(){
			closeAllTask();
		},		
		createTask  : function (task) {
			createTask(task);
		},		
		createDialog : function (opts){
			createDialog(opts);
		},	
		createPopWindow : function (opts){
			createPopWindow(opts);
		},
		refreshTask : function (taskId) {
			
		},
		breakHome : function () {
			breakHome();
		},
		generateUserDefData : function(changeFlag){	
			var data = changeFlag!==true || (changeFlag === true ) ?  generateUserDefData() : {};//&& isChangeDef === true
			return $xcp.toJson(data);
		},
		isTaskData : function(){
			return isTaskRw();
		},		
		isCurrentOpenTask : function(){
			//检查任务栏中是否存在已打开的当前在于在操作的任务
			return isCurrentOpenTask();
		},
		addEventOfDesktopIcon : function(event){
			addEventOfDesktopIcon(event);
		},
		setIsShortDef : function(blean){
			setIsShortDef(blean);
		},
		createToolTop : function (opts){
			if (!$.isArray(opts)){
				createToolTop(opts);
			}else{
				$.each(opts,function(i,o){
					createToolTop(o);
				});
			}
		}
	};
	
	$.fn.xcpApp.parseOptions = function () {};
	
	$.fn.xcpApp.defaults = {
		wallpaper     : null, 
		onTaskBlankContextMenu : function () {},       //任务栏右键事件
		onWallContextMenu      : function () {},       //桌面右键事件
		onAppContextMenu       : function () {},       //app右键事件
		onBeforeOpenApp        : function () {},       //打开app之前的事件,可以返回自定义的窗口options
		onLoaded               : function () {},       //app实例化完成事件
		onOpenApp              : function () {},       //app打开事件
		onClosedApp            : function () {},       //app关闭事件
		onStartMenuClick       : function (item) {},   //开始菜单点击事件
		iframeOpen             : true,
		loadUrl : {                                    //远程数据加载路径
			app       : './custQuery.do?method=userCust', //app.json修改为desktop.json,主要是修改桌面为WIN8风格
			startMenu : null,//'startMenu.json',               //开始菜单数据
			widget    : null,
			appData : {"userId" : $xcp.getUSERID()}
		},
		lang : {
			initLayout    : $xcp.i18n("app.initLayout"),
			initTaskBlank : $xcp.i18n("app.initTaskBlank"),
			initDeskTop   : $xcp.i18n("app.initDeskTop"),
			initStartMenu : $xcp.i18n("app.initStartMenu"),
			initTools     : $xcp.i18n("app.initTools"),
			initWidget    : $xcp.i18n("app.initWidget"),
			progress : {
				title :     $xcp.i18n("app.title"),
				msg   :     $xcp.i18n("app.msg")
			}
		}
	};
})(jQuery);



/**
 * 单例管理，同一个主界面下的交易共用属性
 * 主要用于设置用户操作时与交易相关的个性化定制
 */
lpbc.userTradeGlobalDefine = function($){
	return {
		options : {
			viewReportMethod : '0'  //查看面函时的展示方式，0--表示在交易中以TAB页展示，1--表示以弹窗方式展示
		},
		get     : function(key){
			return this.options[key] || '';
		}
	};
}(jQuery);

var loadTime = {};
loadTime["newTime"] = new Date().getTime();

function setTest(_key){
	if(_key == undefined){
		var str = "";
		loadTime["newTime"] = 0;
		$.each(loadTime,function(i,o){
			str += i + "  :  " + o + "\r\n";
		});
		alert(str);
	}
	
	if(_key == true){
		loadTime["newTime"] = new Date().getTime();
		return ;
	}
	
	if(_key){
		loadTime[_key] = (new Date().getTime() - loadTime["newTime"])/1000;
	}
}