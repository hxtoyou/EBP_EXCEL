/**
 * 主界面功能菜单管理
 */
lpbc.toolsMgr = lpbc.sysToolsMgr = function($){
	
	var sysMasterMenuRefTargetId = null;
	var sysUserDefSetting = "sysUserDefSetting",userInfo;
	var menuCache = {}; //缓存从后台加载的菜单数据，方便前端作异步处理
	//主菜单初始化
	function sysMasterMenuInit(target){
		var desktopMoreMenu =  $("#desktopMoreMenu");
		var refId  = sysMasterMenuRefTargetId = desktopMoreMenu.attr('refId');
		var refObj = $("#" + refId);
		var mainMenuTabs = $(".tab",refObj);
		sysMasterMenuRefTargetId = refId;
		
		$(desktopMoreMenu).bind('click.xcpAppTools',function(e){	
			 if(refObj.is(":hidden")){
				 $('.toolNavigation').hide();
				 refObj.hide();  refObj.fadeIn('slow'); 
				 //判断是否是第一次点击，如果是则默认第一个菜单列表显示
				 var btn_clicked = $(this).attr("btn_clicked");
				 if(btn_clicked !==  'Y'){	
					$(this).attr("btn_clicked",'Y');
					var tabFirst = $('.tab:first',refObj);
					//手动触发一次点击事件
					tabFirst.trigger('click.xcpAppTools');
				 }
			 }else{
				 refObj.fadeOut('slow');
			 }
		});
		
		$(".tabsWrap .close","#" + sysMasterMenuRefTargetId).bind('click.xpcAppTools',function(){
			 $("#" + sysMasterMenuRefTargetId).fadeOut('slow');
		});
		
		mainMenuTabs.bind('click.xcpAppTools', function(){
			 if(!$(this).hasClass('hover')){
				 mainMenuTabs.removeClass('hover'); $(this).addClass('hover');
				 $('.tabRefContent',refObj).hide();
				 var tabRefId = $(this).attr('refId');
				 if(tabRefId){
					 var refTarget =  $("#" + tabRefId);
					 if(refTarget.attr('menuLoaded') !== 'Y'){
						 //加载菜单
						 loadTradeMenuDrag(refTarget.attr('sysMenuType'),refTarget);
						 refTarget.attr('menuLoaded','Y');
					 }
					 refTarget.show();
				 }
			 }		 
		 });
		
		//初始化登录的用户信息
		initLoginUserInfo();
		
		//用户修改密码
		$('div.mainTop').find('div.modPassword').bind('click.xcpAppTools',function(e){
			if($("#usrPwdDialog").length == 0){
				usrPwdUpdate();
			}else{
				$("#usrPwdDialog").show();
			}
		});
		
		//弹出用户修改信息
		if(usrLogin == "2" || usrLogin == 2){
			if($("#usrPwdDialog").length == 0){
				usrPwdUpdate();
			}else{
				$("#usrPwdDialog").show();
			}
			//lsm4
			$('#usrPwdDialog').find('a.usrCloseBtn').hide();
			$('#usrPwdDialog').dialog({
				closable : false
			});
		}else if((usrLogin == "0" || usrLogin == 0) && timeOutDays != -1 ){
			var str = timeOutDays == 0 ? "即将过期!" : "还有[" + timeOutDays + "]天";
			$.messager.confirm($xcp.i18n('tip'),' 密码'+ str +"!是否立即修改密码!",function(r){
				if(r){
					usrPwdUpdate();
				}
			});
		}
	}
	 
	/**
	 * 初始化登录的用户信息
	 */
	function initLoginUserInfo(){
		userInfo = $xcp.getConstant('user')[0];
		var userMessage = $xcp.i18n('sys.welcome')+", <font style=\"color:#0073ea; cursor:pointer\">"+(userInfo ? userInfo.userName+"("+userInfo.belongOrgName+")" :"")+"</font>";
		$("#userLoginInfo").html(userMessage);
		//处理没修改的用户密码修改
		if (userInfo && !userInfo.lastModifyPwdDate){
			usrPwdUpdate();
			$('#usrPwdDialog').dialog({closable:false});
			$('#usrPwdDialog #frm-DelClose').hide();
		}
	}
	
	//系统牌价查询初始化
	function sysQpCxInit(target){
		var qpBtn =  $("#desktopQuotePrice"); 
		var refId =  qpBtn.attr('refId');
		var refTarget = $("#" + refId);
		
		qpBtn.bind('click.xcpAppTools',function(e){	
			 if(refTarget.attr('qptable') != 'Y'){
				 initQpTable();
				 refTarget.attr('qptable','Y');
			 }else{
				 refTarget.find('.content').datagrid('load');
			 }
			 if(refTarget.is(":hidden")){
				 $('.toolNavigation').hide();  refTarget.fadeIn('slow');
			 }else{
				 refTarget.fadeOut('slow');
			 }
		});
		
		$(".close",refTarget).bind('click.xpcAppTools',function(){
			refTarget.fadeOut('slow');
		});
		
		function initQpTable(){
			refTarget.find('.content').datagrid({
				loadMsg : $xcp.i18n("sys.loadMsg"),
				singleSelect: true,
				//title: '当前系统牌价',
				url:$xcp.def.getFullUrl('rateQuery.do?method=getPrice'),
				fit   : true,
				fitColumns: true,
				pageSize: 10,            //每页显示的记录条数，默认为10  
			    pageList: [5,10,15,20],  //可以设置每页记录条数的列表  
				columns:[[
							{title : '<b>'+$xcp.i18n("qp.curSign")+'</b>',     field : 'curSign', width : 60, align : 'center'},
							{title : '<b>'+$xcp.i18n("qp.buyPrice")+'</b>',   field : 'buyPrice', width : 95, align : 'center'},
							{title : '<b>'+$xcp.i18n("qp.salePrice")+'</b>',   field : 'salePrice', width : 95, align : 'center'},
							{title : '<b>'+$xcp.i18n("qp.billBuyPrice")+'</b>',   field : 'billbuyPrice', width : 95, align : 'center',hidden:true},
							{title : '<b>'+$xcp.i18n("qp.billSalePrice")+'</b>',   field : 'billsalePrice', width : 95, align : 'center'},
							{title : '<b>'+$xcp.i18n("qp.qpTime")+'</b>', field : 'impDate', width : 120, align : 'center'}
						]],
				pagination:true,
				rownumbers:true		
			});
		}
	}
	
	//系统设置（定制）初始化
	function sysSetInit(target){
		var defBtn =  $("#desktopSetting"); 
		var refId  =  defBtn.attr('refId');
		var refTarget = $("#" + refId);
	
		refTarget.find("span.btn").each(function(){
			var method = $(this).attr('refMethod'),value = $(this).attr('refValue');
			if(method == 'language' && $xcp.def.getEAP_LANGUAGE() == value){
				 $(this).addClass('current'); return ;
			}else if(method == 'theme' && $xcp.def.getUserCurrentTheme() == value){
				 $(this).addClass('current'); return ;
			}else if(method == 'viewReportMethod' && $xcp.def.getUserViewRptMethod() == value){
				$(this).addClass('current'); return ;
			}
		});
		
		defBtn.bind('click.xcpAppTools',function(e){		
			 if(refTarget.is(":hidden")){
				 $('.toolNavigation').hide();  refTarget.fadeIn('slow');
			 }else{
				 refTarget.fadeOut('slow');
			 }
		});
		
		$(".close",refTarget).bind('click.xcpAppTools',function(){
			refTarget.fadeOut('slow');
		});
		
		refTarget.find('span.btn').bind('click.xcpAppTools',function(e){
			if($(this).hasClass('current')) return;
			var method = $(this).attr('refMethod'), value = $(this).attr('refValue');
			$xcp.setEAP_LANGUAGE(value);
			if( method =='language'){
				setLanguage(value,this);//lsm1
			}
			else if( method == 'theme'){
				setUserDefTheme(value,this);
			}
			else if( method == 'viewReportMethod'){
				setUserViewReportMtd(value,this);
			}
		});
		
		//切换div
		$("#userSettingPanelLeft > .leftIcon").bind("click.qh",function(){
			$("#userSettingPanelLeft .current").removeClass("current");
			$(this).addClass("current");
			$("#userSettingPanelRight div.current").removeClass("current");
			$("#"+$(this).attr("refRightIconId")).addClass("current");
		});
		
		//设置语言
		function setLanguage(lang,target){
			if(lang ){
				$.messager.confirm($xcp.i18n("sys.tip"),$xcp.i18n("sys.setLanguage"),function(r){
					if(r){
						 top.window.onbeforeunload = null; 
						 if( !/[a-z]{2}_[A-Z]{2}/.test(lang) ){
							 return false;
						 }
						 $("#usr_language").val(lang);
				    	 $('#userLangOrThemeDefForm').attr('action',$xcp.def.getFullUrl('./setUserStyle.do'));
					     $("#userLangOrThemeDefForm").submit();
					}else{
						 return false;
					}
				});
			}
		}
		//设置主题
		function setUserDefTheme(themeValue,target){		
			if(themeValue ){
				$.messager.confirm($xcp.i18n("sys.tip"),$xcp.i18n("sys.setTheme"),function(r){
					if(r){
						top.window.onbeforeunload = null; 
						 $("#usr_theme").val(themeValue);
				    	 $('#userLangOrThemeDefForm').attr('action',$xcp.def.getFullUrl('./setUserStyle.do'));
					     $("#userLangOrThemeDefForm").submit();
					     
					 	 //refTarget.find("span.btn[refMethod='theme']").removeClass('current');
						 //$(target).addClass('current');
					}else{
						return false;
					}
				});
			}
		}
		
		/**
		 * 设置用户查看面函或者报文的方式
		 */
		function setUserViewReportMtd(val,target){		
			if(val ){
				$.messager.confirm($xcp.i18n("sys.tip"),$xcp.i18n("sys.setRptMtd"),function(r){
					if(r){
						top.window.onbeforeunload = null; 
						 var rptValMtd = {"viewReportMethod":val};
						 $("#usr_viewRptMtd").val($xcp.toJson(rptValMtd));
				    	 $('#userLangOrThemeDefForm').attr('action',$xcp.def.getFullUrl('./setUserStyle.do'));
					     $("#userLangOrThemeDefForm").submit();
					}else{
						return false;
					}
				});
			}
		}
		  
		//快速栏设置事件绑定
	   loadUserDefQuickBar();
	   //loadPrintSetting();
	}
	
	function loadApplet(){
		if (document.applets.length > 0){
	        return document.applets[0];
	    }else if(document.embeds.length > 0){
	        return document.embeds[0];
	    } else {
	    	initApplet();
	    }
	}
	
	var tabHtml = "";
	function isAppletReady(){
		var str = sysPrint.drigName;
		if(null == tabHtml || "" == tabHtml){
			tabHtml =  '<div id = "'+str+'">';
			$.each(credTypeList,function(i,k){
				tabHtml += "<div style='height:30px;margin-top:3px;'><span class='comSpanLeft'>"+k.name+":</span>";
				tabHtml += "<span class='comSpanRight'><input mainEasyui = 'true' class='easyui-combobox' id='"+k.id+"' name = '"+k.id+"' ></input></span></div><div class='divTab'></div>";
			});
			tabHtml += "<div style='height:30px;text-align: left;'><span style='width:20%;padding-left: 80px;'>"+$xcp.i18n("printSys.autoPrint")+":</span>";
			tabHtml += "<span style='width:80%;padding-left: 12px;'><input type='checkbox' class='btnSpan' name='autoPrint' id='autoPrint' value='Y'></input> &nbsp;&nbsp; <input class='but_query paramQueryBtn' name='downLoadJRE' onclick='downLoad()' id='downLoadJRE' value='下载JRE'></input></span></div><div class='divTab'></div>";
			tabHtml += "<form id='downLoadJreForm' target='downLoadJre_hidden_frame' action='report/jre-6u37-windows-i586.rar' method='post'>";
			tabHtml += "</form>";
			tabHtml += "<iframe name='downLoadJre_hidden_frame' id=\"downLoadJre_hidden_frame\" style='display:none'></iframe>";
			tabHtml += '</div>';
			$("#printSet").html(tabHtml);
			$.parser.parse($("#"+str));
//			$(tabHtml).appendTo($('#printSet'));
			$.each(credTypeList,function(i,k){
				$('#'+k.id).combobox({
					valueField:'value',
					textField:'text',
					data:[]
				});
			});
		}
	
		if(document.AppletTest){
			try {
				getLocalPrinterParams();
			} catch (e) {
				// TODO: handle exception
				setTimeout(isAppletReady,1000*1*2);
				return ;
			}
			
			//var tempdata = $.merge(busmtcfgInfo,prtNameCountList);
			var tempdata = prtNameCountList;
			$.each(credTypeList,function(i,k){
				$('#'+k.id).combobox({
					valueField:'value',
					textField:'text',
					data:tempdata
				});
			});
			
			var autoPrint = $("#autoPrint");
			if(isAutoPrint == "Y"){
				$xcp.batchPrintMgr.stopPrint();
				print_flag = true;
				printAutomatic();
				$(autoPrint).attr("checked", "checked");
			} else {
				$xcp.batchPrintMgr.stopPrint();
				$(autoPrint).removeAttr("checked");
				print_flag = false;
			}
			//设置机器对应打印机信息
			sitePrintMach();
			return;
		}
		setTimeout(isAppletReady,1000*1);
	}
	
	function loadPrintSetting(){
		//loadApplet();
		//setTimeout(isAppletReady,200);//100毫秒1000*4 
	}
	
	function loadUserQuickBarHtml(){
		var ulId = "bingpUl";
//		var defContentList = "defWidgetsContentList";
		var defContentList = "taskRight";
		var str = "";
		$("#"+defContentList).find("div[reftradeno]").each(function(){
			var Nkey = $(this).attr("reftradeno");
			var refurl = $(this).attr("refurl");
			 
			str +=  "<div style='height:25px;'><span class='btnSpan' style='width: 38%;text-align: riget;'>"+$(this).attr("refname")+"</span>" +
			"<span class='btnSpan' style='width: 20%;'><input type='checkbox' name='"+Nkey+"_fast'  id='"+Nkey+"_fast' checked='checked' value='checkbox'></span>"+
		    "<span class='btnSpan' style='width: 20%;'><input type='checkbox' name='"+Nkey+"_home'  id='"+Nkey+"_home' " + (refurl == undefined ? "disabled='disabled'" : "" ) + " value='checkbox'></span> </div><div class='divTab'></div>";
			});
		
		$("#"+ulId).append(str);
	}
	//快速栏设置事件绑定
	function loadUserDefQuickBar(){
		//加载快速栏显示内容
		loadUserQuickBarHtml();
		//加载选中checked 是否选中lsm2
		var userQuickbar = $xcp.parseJson($xcp.def.getUserCurrentQuickbar());
		
		var nt = 1;
		$.each(userQuickbar||[],function(i,o){
			nt++;
			var fast = "fast";home = "home";
			$("#"+i+"_"+fast).attr("checked",o[fast]);$("#"+i+"_"+home).attr("checked",o[home]);
			//快速栏的处理
			if(o[fast] == true || o[fast] == "true"){
				$("div[reftradeno='"+i+"']").show();
			}
		});
		if(nt == 1){
			$("div[reftradeno]").show();
		}
		
		//选择框绑定事件
		$("[id$='_fast'],[id$='_home']","#"+sysUserDefSetting).bind("click",function(){
			var str = $(this).attr("name").split("_")||[];
			$("#"+str[0]+"_"+((str[1]||"") == "fast"?"home":"fast")).attr("checked",false);
		});
		//确认按钮
		$("#printSetting","#"+sysUserDefSetting).bind("click",function(){
			savaPrintMachSite();
		});
		
		$("#settingCon","#"+sysUserDefSetting).bind("click",function(){
			if($("#masterMenuUpdate").hasClass("masterMenuOk")){
				$("#masterMenuUpdate").trigger("click");
			}
//			alert($xcp.toJson(getUserDataQuickbar()));
			setUserDefQuickBar($xcp.toJson(getUserDataQuickbar()));
		});
		
		
		function setUserDefQuickBar(quickBarValue){		
			if(quickBarValue){
				$.messager.confirm($xcp.i18n("sys.tip"),$xcp.i18n("sys.quickSet"),function(r){
					if(r){
						 top.window.onbeforeunload = null; 
						 $("#user_quickBar").val(quickBarValue);
				    	 $('#userLangOrThemeDefForm').attr('action',$xcp.def.getFullUrl('./setUserStyle.do'));
					     $("#userLangOrThemeDefForm").submit();
					}else{
						return false;
					}
				});
			}
		}
	}
	
	function getUserDataQuickbar(){
		var jso = {};
		$("[id$='_fast']","#"+sysUserDefSetting).each(function(i,o){
			var str = $(this).attr("name").split("_")||[];
			jso[str[0]] = {
					"fast":$(this).prop("checked"),
					"home":$("#"+str[0]+"_home").prop("checked")
			};
		});
		return jso;
	}
	
	//系统帮助初始化
	function sysHelpInit(target){
		/*$("#userHelp").bind("click.xcpAppTools",function(){
			$xcp.menuMgrFun.viewBusHelpInfo();
		});*/
	}
	
	//系统退出初始化
	function sysExitInit(target){
		$("#userSysExit").bind("click.xcpAppTools",function(){
			 var isExistTask = $('body').xcpApp("isTaskData");
			 if(isExistTask === true){
				 $.messager.confirm($xcp.i18n("tip"), $xcp.i18n("sys.exitSys"), function(r){
				     if(r){				    	 
				    	 logonOut();
				     }
				});
			 }else{
				logonOut();
			 }			 
		});
	}
	
	//系统信息交互初始化
	function sysMessageInit(target){
		
	}
	
	//退出的ajax方法
	function logonOut(){
		var userDefData = null;
		try{
			if($('body').xcpApp){
				userDefData = $('body').xcpApp('generateUserDefData',true);
				userDefData = $xcp.parseJson(userDefData,true);
				
			}
		}catch(e){
		
		}
		
		$.messager.confirm($xcp.i18n("tip"), $xcp.i18n("sys.logOut"), function(r){
			if(r){
				var aopt = {
					url : "./logout.do",
					data: userDefData
				};
				$xcp.ajax(aopt,null,function(){
					top.window.onbeforeunload = null;
					top.location.href = "./";
				});
			}
		});
	}
	
	//subSysType为子系统类型，大致分为业务，参数，申报，报表等子系统
	function loadTradeMenuDrag(subSysType,target){
		var aopt = {
			url 	 : $xcp.def.getFullUrl("./menu.do?method=getMenu"),
			data 	 : {"parent": subSysType || ''},
			success  : function(result) {
				if(result.success =='1'){
					$xcp.dispAjaxError($xcp.cloneObject(result));
					result = [];
				}else{
					if(typeof result.outEntity == "object" ){
						try{
							menuCache[subSysType] = result.outEntity;
							jsonJxClon(result.outEntity,target,subSysType);
						}catch(e){
							
						}
					}
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				$.messager.alert('Warning',textStatus);
			}
		};
		$xcp.ajax(aopt);		
	}
	
	function jsonJxClon(menus,target,menuType){
		 //先生成不包含有子菜单的叶子节点的HTML内容，对于子菜单，采用点击再加载方式
		 var html = "";   
		 $.each(menus,function(i,o){
	    	 if(o.sub != '-1' && $.isArray(o.sub)){
	    		 html += "<div class='masterMenuArea'>";
	    		 html += 	"<div class='menuHead " + (o.iconCls || 'mt') + "'>" + o.name + "</div>";
	    		 html +=    	"<div class='menuPanel'>";
	    		 //html +=    		"<div class='up'></div>";
	    		 html +=    		"<div class='menuDetail'>";
	    		 html +=                "<ul class='easyui-tree' data-options=\"animate:false,lines:true\" refId='" + o.id  + "' refMenuType='" + menuType + "'></ul>";
	    		 html += 	 		"</div>";
	    		 //html +=    		"<div class='down'></div>";
	    		 html +=    	"</div>";
	    		 html +=    "</div>";
	    		 html += "</div>";	 
	    	 }
	    });
	    target.html(html);
	    //生成树
	    $('.easyui-tree',target).tree({
	    	loadDataFun    : loadData,
	    	onBeforeExpand : function(node){
	    		//对有下拉菜单的节点生成子菜单
	    		var item = $(node.target).find('.mainSubMenu');
	    		if(item && item.attr('subItemLoaded') != 'Y'){
	    			var menus = loadData(item);
	    			if(menus.length > 0){
	    				$(this).tree('append',{
	    					parent : node.target,
	    					data: menus
	    				});
	    				bindTradeMenuEvent();
	    			}
	    			item.attr('subItemLoaded','Y');
	    			//绑定叶子节点的菜单事件
	    	        bindTradeMenuEvent($(node.target).parent());
	    		}
	    	},
	    	onCollapse : function(node){
	    
	    	},
	    	onClick    : function(node){
	    	
	    	}
	    });
	    
	    //绑定滚动条事件
	    bindTreeScrollEvent(target);
	    //绑定菜单事件
        bindTradeMenuEvent(target);
	    
	    function loadData(target){
	    	var refId = $(target).attr('refId'), refMenuType =  $(target).attr('refMenuType');
	    	return  getMenuData(menuCache[refMenuType],refId,refMenuType);
	    }
	}
	var menuCionCls = {"BU": "icon-impCredit","PB":"icon-2","DL":"icon-3","RP":"icon-4" };
	var treeCionCls = {"BU": "tree-icon-1","PB":"tree-icon-2","DL":"tree-icon-3","RP":"tree-icon-4" };
	//根据ID和菜单类型找到子菜单的数组data
	function getMenuData(menus,id,refMenuType){
		var rs = [];
		
		var menuItems = getSubMenuArray(menus,id);
		
		if(menuItems != null && id != ''){
			
			$.each(menuItems,function(i,o){
			    var item = {},iconClsStr = o.iconCls || menuCionCls[refMenuType] || '';
			    
			    item.id      = o.id;
			    item.iconCls = treeCionCls[refMenuType] || '';
//			    item.iconCls = iconClsStr;
			    item.name    = o.name || '';
			    
			    if(o.sub != '-1' && o.sub.length > 0){
			    	//这类节点有子菜单
			    	item.text  = "<div class='mainSubMenu' style='color:#057ebe;' refId='" + o.id + "' refMenuType='" + refMenuType + "'>" + o.name + "</div>";
			    	item.state = 'closed';
			    }else{
			    	//这个是叶子节点
			    	item.text  = "<div class='tradeMenuDrag isMenuLeaf' style='color:#057ebe;'";
				
			    	item.text += 	" tradeNo='"      + o.tradeNo +  "' ";
			    	item.text += 	" refName='"      + o.name    +  "' "; 
			    	item.text += 	" refUrl='"       + o.url     +  "' ";
			    	item.text += 	" refIconCls='"   + iconClsStr             + "' ";
			    	item.text += 	" refHasLeft='"   + (o.hasLeft || '')     + "' ";
			    	item.text += 	" refTradeFlag='" + (o.tradeFlag || '')   + "' ";
			    	item.text +=    " refMenuId='"    + (o.id)+"' ";
			    	item.text +=    ">" + o.name + "</div>";
			    }
				rs.push(item);
				
			});
		}
		
		//获取某一个ID项下的子菜单数组
		function getSubMenuArray(data ,id){
			var obj = null;
			$.each(data,function(i,o){
				if(o.id == id) {
					obj = o.sub == "-1" ? [] : o.sub;
				}else{
					if(o.sub != '-1' && o.sub.length > 0){
						obj = getSubMenuArray(o.sub,id);
					}
				}				
				if(obj != null) return false;
			});
			return obj;
		}
		
		return rs;
	}
	
	//初始化已加载菜单，使其能拖拽
	function bindTreeScrollEvent(target){
		var areas = $(target).find(".masterMenuArea");
		
		areas.hover(function(i,o){
			$(this).find(".menuPanel").css({
				"overflow-y":"auto",
				"overflow":"auto"
			});
		},function(i,o){
			$(this).find(".menuPanel").closest(".menuPanel").css({
				"overflow-y":"hidden",
				"overflow":"hidden"
			});
		});
	}
	
	//给已加载的菜单绑定事件
	function bindTradeMenuEvent(target){
	  
		var menuItems =  $(".isMenuLeaf",target);
		   
	   menuItems.draggable({
			revert: true,
		    cursor: 'pointer',
			proxy : function(){ 
				    	if($('#dragMenuItemProxy').size() == 0){
				    		var html = "<div id='dragMenuItemProxy' style='position:absolute;z-index:9003;display:none' class='menuDragDropProxy'>menu drag and drop</div>";
				    		$(html).appendTo('body');
				    	}
						return $('#dragMenuItemProxy');
				    },
			onBeforeDrag : function(e){
				
			},
			onStartDrag: function(e){
				$(this).draggable('proxy').addClass('dp').show('fast').delay(300);
			},
			onStopDrag : function(e){
				if($(this).draggable('proxy') != null){
					var pl = $(this).draggable('proxy').offset().left;
					var tl = $(this).offset().left;
					//通过比较代理与原对象之间的像素差来判断是否存在拖拽
					if(Math.abs(pl - tl) <= 2){
						$(this).draggable('proxy').hide();
					}	
				}
			},
			onDrag     : function(e){
				
			}
	   });	
	   
	 //叶子节点绑定双击打开交易的事件
	   menuItems.unbind('dblclick.xcpAppTools').bind('dblclick.xcpAppTools',function(e){
			var tradeNo = $(this).attr("tradeNo");
	      	if(tradeNo != ''){
			  var task = {
					name      : $(this).attr('refName'),
					tradeNo   : tradeNo,
					url       : $xcp.def.getFullUrl($(this).attr('refUrl')) || "" ,
					params    : $(this).attr('refMethodParam') || "",
					hasLeft   : $(this).attr('refHasLeft'),
					tradeFlag : $(this).attr('refTradeFlag') || "" 
			  };				
			  $("#" + sysMasterMenuRefTargetId).hide();	
			  //根据图标属性创建任务栏，并打开窗口  //$('body').xcpApp('createTask',task);
			  $xcp.formPubMgr.openTrade(task);
	      	}
	        //阻止事件冒泡
	      	e.stopPropagation(); e.preventDefault();
	    });
	}
	/**
	  *用户密码修改
	*/
	function usrPwdUpdate(){
		var html = 	"<div id=\"usrPwdDialog\" class=\"easyui-dialog\" style=\"width:370px;height:260px;padding:10px\">";
		html += 	"<table class=\"etable\">",
		html += 		"<tr><td style='text-align: right;'>"+$xcp.i18n("userMar.userName")+":</td><td><input type=\"text\" class=\"P\" readonly=\"readonly\" id=\"usrName\"/></td></tr>",
		html += 		"<tr><td style='text-align: right;'>"+$xcp.i18n("userMar.oldPwd")+":</td><td><input type=\"password\"  class=\"easyui-validatebox\" data-options=\"required:true\" id=\"usrPwd\"/></td></td></tr>",
		html += 		"<tr><td style='text-align: right;'>"+$xcp.i18n("userMar.newPwd")+":</td><td><input type=\"password\"  class=\"easyui-validatebox\" minlength=\"8\" data-options=\"required:true,validType:['length[8,20]','validtPass']\" id=\"usrNewPwd\"></td></tr>",
		html += 		"<tr><td style='text-align: right;'>"+$xcp.i18n("userMar.confirm")+":</td><td><input type=\"password\"  class=\"easyui-validatebox\"  minlength=\"8\" data-options=\"required:true,validType:['length[8,20]','eqPassword[\\\'#usrNewPwd\\\']','validtPass'] \"  id=\"usrNewPwd2\"></td></tr>",
		html += 	"</table><div id=\"usrPwdUpdateError\"></div>";
		html += "	<div id=\"dlg-buttons\" style=\"text-align:center;\">";
		html += 		"<a  class=\"easyui-linkbutton  usrSaveBtn\" data-options=\"plain:true\">"+$xcp.i18n('app.confirm')+"</a>";
		html += 		"<a id=\"frm-DelClose\" class=\"easyui-linkbutton usrCloseBtn\" data-options=\"plain:true\">"+$xcp.i18n('app.cancel')+"</a>";
		html += 	"</div>";
		html += "</div>";
	
		$(html).appendTo('body');
		$.parser.parse($("#usrPwdDialog"));
		
		$('#usrPwdDialog').dialog({
			title   : $xcp.i18n("userMar.modifyPwd"),
			modal   : true,
			/*iconCls : 'icon-save',*/
			buttons : '#dlg-buttons',
			onClose     : function(){
				$(this).dialog('destroy');
			}
		});
		$('#usrName').xcpVal(userInfo.userName);
		$('#usrPwdDialog').find('a.usrSaveBtn').bind('click',function(){
			var obj = $('#usrPwdDialog').find('.easyui-validatebox');
			var isSubmit = true;
			$.each(obj,function(i,o){
				$(o).validatebox('validate');
				if($('#usrPwdDialog').find(".validatebox-invalid").length > 0){
					isSubmit = false;
				}
			});
		
			if(!isSubmit){
				return false;
			}
			if(intf_modpwd=='N'){
				var callFlag=callBODAFA0005($('#usrPwd').val(),$('#usrNewPwd').val());
			    if(!callFlag){
			    	alert('失败');
			    	return false;
			    }
			}
			$.ajax({
				type : "POST",
				url  : $xcp.def.getFullUrl("userPassword.do?method=updatePassword"),
				data : {
					userId:$xcp.getUSERID(),
					oldPassword:b64_md5($('#usrPwd').val()),
					passWord:b64_md5($('#usrNewPwd').val())				
				},
				dataType : "json",
				success  : function(result) {
					if(result.outEntity.errorCode != '' && result.outEntity.errorCode!= null){
						/* $xcp.dispAjaxError($xcp.cloneObject(result));
						result = []; */
						$('#usrPwdUpdateError').html("<font color='red'>"+result.outEntity.errorCode+'||'+$xcp.i18n('sys.'+result.outEntity.errorCode)+"</font>");
					}else{
						$.messager.alert($xcp.i18n("sys.tip"),$xcp.i18n("sys.success"));
					    $('#usrPwdDialog').dialog('destroy');
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){
					alert(errorThrown );
				}
			});
		});
		
		$('#usrPwdDialog').find('a.usrCloseBtn').bind('click',function(){
			$('#usrPwdDialog').dialog('destroy');
		});
	}
	
	/**绑定退出签退事件**/
	function bindBefUnLoad(){
		top.window.onbeforeunload = null;
		top.window.onbeforeunload = function(){
			var userDefData = null;
			try{
				if(top.window.$('body').xcpApp){
					userDefData = top.window.$('body').xcpApp('generateUserDefData',true);
					userDefData = $xcp.parseJson(userDefData,true);
					
				}
			}catch(e){
			
			}
			var aopt = {url : "./logout.do",data: userDefData};
			$xcp.ajax(aopt,null,function(){});	
		};
	}
	
	return {
		init : function(){
			this.options.sysMasterMenu ?  sysMasterMenuInit() : '';
			this.options.sysQpCx       ?  sysQpCxInit()       : '';
			this.options.sysSetting    ?  sysSetInit()        : '';
			this.options.sysHelp       ?  sysHelpInit()       : '';
			this.options.sysExit       ?  sysExitInit()       : '';
			this.options.sysMessage    ?  sysMessageInit()    : '';
		},
		
		options : {
			sysMasterMenu : true,   //系统主菜单展示按钮
			sysQpCx       : true,   //系统主界面牌价查询按钮
			sysSetting    : true,   //系统设置按钮
			sysHelp       : true,   //系统帮助按钮
			sysExit       : true,   //系统退出按钮
			sysMessage    : true    //系统信息交互按钮
		},
		getUserDataQuickbar : function(){
			return getUserDataQuickbar();
		},
		loadPrintSetting : function (){
			loadPrintSetting();
		},
		getUserInfo : function(){
			return userInfo;
		},
		bindBefUnLoad : function(){
			bindBefUnLoad();
		}
	}
}(jQuery);
function callBODAFA0002(){
	var flag=false;
	var param={
			  'brno':$('#butxn_tranOrgNo').xcpVal(),
	      	  'prcscd':'005011',//处理码
	      	  'Eciorgbuf':'',//渠道备注信息
	      	  'strsysid':'07'//系统标识
	      };
	  	var data = $xcp.intfQuery("BODAFA0002",param);
	
	    if(data.errorCode&&data.errorCode=="00000000"){
	  		flag=true;
	    }else{
	      	alert(data.errorCode+data.errorMsg);
	    	flag=false;
	    }
	   return flag;
}//end callBODAFA0002()

/**
*/
function callBODAFA0005(pwd1,pwd2){
var flag=false;
//pwd1="88888888";
var oldpwd=callBODSAF0001(pwd1);
var newpwd=callBODSAF0001(pwd2);
var param={
	  'brno':$('#butxn_tranOrgNo').xcpVal(),
  	  'prcscd':'002007',//处理码
  	  'Eciorgbuf':'',//渠道备注信息
  	  'strsysid':'07',//系统标识
  	  'oldpwd':oldpwd,
  	  'newpwd':newpwd
  };
	var data = $xcp.intfQuery("BODAFA0005",param);
	alert(data.errorCode+data.errorMsg);
if(data.errorCode&&data.errorCode=="00000000"){
		flag=true;
}else{
	flag=false;
}
return flag;
}//end callBODAFA0005()

/**
*/
function callBODSAF0001(password) {
var param = {
	'brno' : $('#butxn_tranOrgNo').xcpVal(),
	'prcscd' : '002009',//处理码
	'Eciorgbuf' : '',//渠道备注信息
	'strsysid' : '07',//系统标识
	'passwd' : password//密码
};
var data = $xcp.intfQuery("BODSAF0001", param);
if (data.errorCode && data.errorCode == "00000000") {
	return data.passwd;
} else {
	$.messager.alert("tip", data.errorCode + data.errorMsg);
	return "";
}
}//end callBODSAF0001(password) 