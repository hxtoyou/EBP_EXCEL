/**
 * 表单公共菜单管理
 */
lpbc.menuMgr = lpbc.formMenuManager = function($){
	
	var formMgr,menuDiv,menuBgDiv,timeOutId,timeOutSubId,formMenuJson = {},timeInId;
	var inited = false , infMenuJson = [];

	//当存在下拉菜单时校验哪些子菜单可以点击
	function subMenuStateProcess(src,cls,name){
		if(!$.data(src,"menuRefresh")){
			$.data(src,"menuRefresh",getMenuJsonForName(name)["refresh"]);
		}
		var subId = $(src).attr('subMenuId');
		if(subId){
			if(cls == "form"  || $.data(src,"menuRefresh") == "true"){
				loadMentClsNam(cls);
			}
			$("#" + subId).find('.subMenuItem').each(function(){
				  var data = $(this).data('subMenuData');
				  if(typeof data.validateFun == "string"){
					  data.validateFun = $xcp.normalBtn[data.validateFun] ? $xcp.normalBtn[data.validateFun]["validateFun"] : "";
				  }
				  
				  if(data && data.validateFun && $.isFunction(data.validateFun)){
					  var bol = data.validateFun();
					  if(bol === false){
						  $(this).addClass('disabled'); return;
					  }
				  }
				  if($(this).hasClass('disabled')) $(this).removeClass('disabled');
			});
		}
	}
	
	//缩小或展开菜单栏处理
	function addToogleMenu(){
		var toogleBtn = $("<div class='toogleBtn showMenu'></div>").appendTo(menuDiv);
		toogleBtn.bind('click.xcpMenu',function(e){
			var _this = this;
			var centerWindth = $("body").layout('panel','center').outerWidth();
			if($(this).hasClass('showMenu')){
				if(!menuDiv.is(":animated")){
					menuDiv.find('.iconBtn').hide();
					menuDiv.animate({left : '+=' + (centerWindth - 76)}, "slow",function(){
						$(_this).removeClass('showMenu');
						$(_this).addClass('hideMenu');
					    $(this).css({width:'56px'});
					});
					
					menuBgDiv.animate({left : '+=' + (centerWindth - 76)}, "slow",function(){
						 $(this).css({width:'56px'});
					});
					$(this).css({'float': 'left'});
				}
			}else{
				if(!menuBgDiv.is(":animated")){
					$(this).css({'float': 'right'});
					var str = $(window).outerWidth() - centerWindth;
					menuBgDiv.css({width:centerWindth - 20}).animate({left : str,right: 76 }, "slow");
					menuDiv.css({width:centerWindth - 20}).animate({left : str,right: 76 }, "slow",function(){
						$(_this).removeClass('hideMenu');
						$(_this).addClass('showMenu');
						menuDiv.find('div.iconBtn').show();
					}); 
				}
			}
			e.stopPropagation(); e.preventDefault();
		})
	}
	
	/***动态添加菜单,grpNo只有在有下接菜单的时候才需要使用,当存在下接菜单时，clfun可以为空*
	 * index 插入的位置 从1开始
	 * **/
	function addMenuItem(name,css,clfun,grpNo,subItems,index){
		var menuId = "menu-" + $xcp.def.getTimeUuid();
		var menuItem = $("<div id ='" +  menuId + "' class='iconBtn " + css + "'></div>");
		if(index){
			menuDiv.children().eq(index).before(menuItem);
		}else {
			menuItem = menuItem.appendTo(menuDiv);
		}
		
		if(grpNo){
			menuItem.attr('menuGrpNo',grpNo);
		}
		
		if(clfun != "" ){
			menuItem.bind('click.xcpMenu',function(){
				clfun();
			});
		}
		
		if(name){
			menuItem.tooltip({
				position: subItems == null ? 'top' : 'right',
				content : name,
				onShow : function(){
					$(this).tooltip('tip').css({
						backgroundColor :  '#666',
						borderColor : '#666',
						color:'#fff'
					});
				}
			});
		}
		
		if(subItems){
			var subMenuId = "subMenu-" + $xcp.def.getTimeUuid();			
			var subMenu = $("<div id='" + subMenuId + "' parentId='" + menuId + "' class='xcpFormSubMenuDiv'></div>").appendTo('body');
			menuItem.attr("subMenuId",subMenuId);
			//下接菜单添加hover事件
			subMenu.hover(
				function(){
					var parentId = $(this).attr('parentId');
					if($("#" + parentId).hasClass('hover')){
						 clearTimeout(timeOutId);
					}else{
						$('.iconBtn',menuDiv).each(function(){
							if(this.id && this.id === parentId){
								return;
							}
							$(this).removeClass("hover");
							if(this.subMenuItemId){
								$("#" + this.subMenuItemId).hide();
							}
						});
					}
				 },
				 function(){
					 var _this = this;
					 timeOutSubId = setTimeout(function(){		
						$(_this).slideUp('fast');	
						$("#" + menuId).removeClass("hover");
					 },100);
				 }
			);
		}
		
		//添加鼠标hover事件  
		menuItem.hover(
			function(e){
				 if(subItems){
					 if(menuItem.data("load") == undefined){
						addMentSubItems(subItems,subMenu);
						menuItem.data("load",true);
					 }
				 }
				 
				 var relatedTargetId = e.relatedTarget && e.relatedTarget.id || '';
				 var subId = $(this).attr('subMenuId');
				 
				 if(subId && subId === relatedTargetId && timeOutSubId){
					 clearTimeout(timeOutSubId);
					 return;
				 }
				 //检查或设置下拉菜单的样式
				 subMenuStateProcess(this,css,name);
				 $(this).addClass("hover");
				 var _this = this;
				 
				 timeInId = setTimeout(function(){
					 if(subId && $("#" + subId).is("hidden") === false){							  
						$("#" + subId).css({bottom:'56px',left: ($(_this).offset().left - 1) + "px"}).slideDown('fast');								
					 }
				},50);
			},
			function(e){
				var _this = this;
				clearTimeout(timeInId);
				timeOutId = setTimeout(function(){
					 var subId = $(_this).attr('subMenuId');							
					 if(subId){								 
						$("#" + subId).slideUp('fast');	
					 }
					 $(_this).removeClass("hover");
				},200);
			}	
		);
	}
	
	function addMentSubItems(subItems,subMenu){
		if($.isFunction(subItems)){
			subItems = subItems();
			if(typeof subItems != "object" ) return ;//|| o.subBtns.length == 0
		}
		
		$.each(subItems,function(i,o){
			if(o == null)return ;
			$("<div class='subMenuItem'>" + o.name + "</div>").bind('click.xcpSubMenu',function(e){
				if($(this).hasClass('disabled') === false){
					if(o.clfun){//  && $.isFunction(o.clfun)
						subMenu.hide(); 
						if(typeof o.clfun == "function"){
							o.clfun();
						}
			    		
						if (!window.ActiveXObject) {
							e.stopPropagation();
							e.preventDefault();
						}else{
							window.event.cancelBubble = true;
							window.event.returnValue = false;
						}
					}
				}
			}).data('subMenuData',o).appendTo(subMenu);						
		});
	}
	
	/**动态添加子菜单**/
	function addMentRefSubItems(name,opt){
		var eq = menuDiv.children().length;
		if(menuDiv.children().eq(opt.index)){
			
		}
		
		menuDiv.
		$.each(subItems,function(i,o){
			if(o == null)return ;
			$("<div class='subMenuItem'>" + o.name + "</div>").bind('click.xcpSubMenu',function(e){
				if($(this).hasClass('disabled') === false){
					if(o.clfun){//  && $.isFunction(o.clfun)
						subMenu.hide(); 
						if(typeof o.clfun == "function"){
							o.clfun();
						}
			    		
						if (!window.ActiveXObject) {
							e.stopPropagation();
							e.preventDefault();
						}else{
							window.event.cancelBubble = true;
							window.event.returnValue = false;
						}
					}
				}
			}).data('subMenuData',o).appendTo(subMenu);						
		});
	}
	
	//菜单子菜单刷新和清理
	function loadMentClsNam(clasName,bleab){
		var submenuid = $("."+clasName,menuDiv).attr("submenuid");
		if($("#"+submenuid).length ==0) return ;
		if(bleab == true){
			$("#"+submenuid).html("");
		}else{
			$.each(formMenuJson,function(i,o){
				if(o.iconCss == clasName){
					if(clasName == "form"){
						if($.isFunction(o.subBtns)){
							o.subBtns = o.subBtns();
						};
						addMentSubItems(o.subBtns,$("#"+submenuid).html(""));
					}
				}
			});
		}
	}
	
	function alterSize(){
		var thsis = $('.toogleBtn',menuDiv);
		if($(thsis).hasClass('showMenu')){
			var widhtCenter = $("body").layout('panel','center').outerWidth();
			$("#xcpFormMenuTranspareBgDiv,#xcpFormMenuDiv").css({
				width : widhtCenter - 20,
				"margin-right" : "20px",
				left : $(window).outerWidth() - widhtCenter
			});
		}else{
			$("#xcpFormMenuTranspareBgDiv,#xcpFormMenuDiv").css({
				width : 56,
				"margin-right" : "20px",
				left : $(window).outerWidth()  - 76
			});
		}
	}
	/*** 获取交易菜单数据 */
	function getFormMenuJson(){
		var menuJsonObj = null;
		if(top.$xcp.formoptBtnJson && "" != top.$xcp.formoptBtnJson){
			menuJsonObj = eval("(" + top.$xcp.formoptBtnJson + ")");
		}else{
			$xcp.ajax({
				url : "./myDocument/js/formoptBtn.json",
				dataType : "text"
			},null,null,function(rs,opt,result){
				top.$xcp["formoptBtnJson"] = result;
				result = eval("(" + result + ")");
				if(typeof result != "object") return;
				menuJsonObj = result;
			});
		}
		if(menuJsonObj == null){
			return ;
		}
		formMenuJson = menuJsonObj["normalBtn"];
		
		var mustBtnJson = formMenuJson != undefined ? formMenuJson["mustBtn"] : null;
		var myTaskFlag = "";
		if(taskFlag){
			//手工节点的处理，控制经办和授权的，在formoptBtn.json不需要重复定义
			if(taskFlag.substring(0,1) == "1" || taskFlag.substring(0,1) == "4"){
				myTaskFlag = taskFlag.substring(0,1)+ "001"  || "";
			}else{
				myTaskFlag = taskFlag || "";//taskFlag.substring(0,1)+ "001"  || "";
			}
		}
		
		formMenuJson = formMenuJson != undefined ? formMenuJson[myTaskFlag] : null;
		formMenuJson  = $.extend({},formMenuJson,mustBtnJson);
	}
	
	/*** 根据btn名称获取对象 */
	function getMenuJsonForName(btnName){
		var obj = null;
		$.each(formMenuJson,function(i,o){
			if(o.btnName == btnName || $xcp.i18n(o.btnName) == btnName){ 
				obj = $("#xcpFormMenuDiv").find("div."+o.iconCss).length > 0 ? o : {};
				return false;
			}
			if(typeof o.subBtns == "object"){
				$.each(o.subBtns,function(j,k){
					if((k.btnName == btnName || $xcp.i18n(o.btnName) == btnName ) && isShowMent(k.tradeType,k.btnName)){
						obj = $("#xcpFormMenuDiv").find("div."+o.iconCss).length > 0 ? k : {};
						return false;
					}
				});
			}
			if(obj != null)return false;
		});
		return obj == null ? {} : obj;
	}
	
	/**
	 * 判断是什么交易 菜单显示函数是否返回true
	 */
	function isShowMent(trandType,showFun){
		if(trandType != "ALL" && trandType.indexOf($xcp.formPubMgr.defaults.tradeType) == -1 ){
			return false;
		}
		if($xcp.normalBtn[showFun] != undefined && $xcp.normalBtn[showFun]["showValidateFun"] && $xcp.normalBtn[showFun]["showValidateFun"]() == false) return false;
		
		return true;
	}
	function init(inFormMgr){//TODO
		getFormMenuJson();//获取菜单数据
		
		formMgr = inFormMgr;
		menuDiv = $("#xcpFormMenuDiv");
		if(menuDiv.length == 1){
			menuBgDiv = $("<div id='xcpFormMenuTranspareBgDiv'></div>").appendTo('body');
			 
			addToogleMenu();
			
			if(formMenuJson != null){
				$.each(formMenuJson,function(i,o){
					addMenu(o);
				});
			}
		}
		
		runInfJson();
	}
	
	function runInfJson(){
		if(infMenuJson.length == 0){
			return false;
		}
		
		$.each(infMenuJson,function(i,o){
			addMenu(o);
		});
	}
	
	function findIcon(coniss){
		return menuDiv.find("div."+coniss).length  != 0;
	}
	
	function refresh(){//TODO
		if(menuDiv.length == 1){
			menuBgDiv = $("#xcpFormMenuTranspareBgDiv");
			
			if(formMenuJson != null){
				$.each(formMenuJson,function(i,o){
					//if(findIcon(o.iconCss||"")) return ;
					if(findIcon(o.iconCss||"")){
						if(!isShowMent(o.tradeType,o.btnName)){
							menuDiv.find("div."+o.iconCss||"").hide();
						}else{
							menuDiv.find("div."+o.iconCss||"").show();
						}
						return ;
					}
					addMenu(o);
				});
			}
		}
	}
	
	function addMenu(o){//TODO
		if(inited == false){
			infMenuJson.push(o);
			return ;
		}
		
		if(typeof o.subBtns == "function"){
			if(isShowMent(o.tradeType,o.btnName)){
				addMenuItem($xcp.i18n(o.btnName||""),o.iconCss||"",null,o.grpNo||"", o.subBtns,o.index);
			 }
			 return ;
		}
		
		if(typeof o.subBtns == "object"){
			if(isShowMent(o.tradeType,o.btnName)){
				var additiveList = [];
				$.each(o.subBtns,function(j,k){
					if(isShowMent(k.tradeType,k.btnName)){
						additiveList.push({name :$xcp.i18n(k.btnName||""),clfun:k.bindClkFun||null,validateFun:k.btnName||null});
					}
				});
				addMenuItem($xcp.i18n(o.btnName||""),o.iconCss||"",null,o.grpNo||"",additiveList);
			};
		}else {
			if(isShowMent(o.tradeType,o.btnName)){
				addMenuItem($xcp.i18n(o.btnName||""),o.iconCss||"",o.bindClkFun||null);
			};
		};
	}
	
	/**
	 * 根据左树来改变按钮区的长度  根据loyout 的center 模块的大小来 动态决定按钮长度
	 * 要绑定浏览器改变事件，左数扩宽缩小事件 隐藏按钮点击事件  分割条移动事件
	 * 以后可优化为获取左树长度(根据箭头的隐藏和显示) 来决定按钮区长度
	 */
	function cssForLeft(){
		alterSize();//根据左边改动大小
		$xcp.leftTreeMgr.addWestSizeFun(function(){
			setTimeout(function(){
				alterSize();
			},200);
		});
		$(window).resize(function(){
			setTimeout(function(){
				alterSize();
			},200);
		});
	}
	
	return {
		init : function(inFormMgr){
			inited = true;
			init(inFormMgr);
			cssForLeft();//给左边机构选择做动态css调整
		},
		getFormMenuJson : function(){
			return formMenuJson;
		},
		//菜单子菜单刷新和清理
		loadMentClsNam : function(cls,blean){
			loadMentClsNam(cls,blean);
		},
		refresh : function(){
			refresh();
		},
		getMenuJsonForName : function(btnName){
			return getMenuJsonForName(btnName);
		},
		addMenu : function(opt){
			addMenu(opt);
		},
		addMenuItem : function(opt){
			addMenuItem(opt);
		}
	};
}(jQuery);
