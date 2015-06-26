/**
 * 业务界面的TAB页签分组处理，组件管理容器
 */
lpbc.compMgr = lpbc.componentManager = function($){
	
	var mainTabs = {}, allComponents = {}, firstTitle = "",selectEventInited = false;
	var currOperCompId = null,currOperCompRowIndex = null;
	var allComponentsNoTtile = {};
	var formMainTabs = null;
		
	function analysisMainTabs(){
		formMainTabs = $(".formMainTabs");
	    if($xcp.formPubMgr && !$xcp.paramMar){
	    	
			var rs = $("#" + $xcp.formPubMgr.defaults.tableDivId + " > .plf-mainTabsPanelLytArea"); 
			
			if(rs.length > 0 || window.easyui_parse_process_group == true){
				
				window.easyui_parse_process_group = true;		
	
				$.each(rs,function(i,o){
					var title = $(o).attr('title');
					if( title != undefined && title != ''){
						if(firstTitle == "")firstTitle = title;
						//src表示title代表的div对象，components表示src对象下包含的组件  mainTabs[title] = {src : o, components : {}, running : false};
						mainTabs[title] = {src : o, components : {}, running : false,content:$(o).html()};//content:$(o).html()
					}
				});
				
				$.each(allComponents,function(id,obj){
					var tab = $("#" + id).closest('.plf-mainTabsPanelLytArea');
					if($(tab).length == 1){
						var title = $(tab).attr('title');
						if(title != undefined && title != '' && mainTabs[title] != undefined){
							//仅标记该组件没有被处理过
							mainTabs[title]['components'][id] = '';
						}
					}
				});
				
				//新增加不在页签下的组件注册不验证id
				analysisMainNoTabs(rs);
				
				$.each(rs,function(i,o){
					$(o).empty();
				});
			}
	    }
	}
	
	/**新增加不在页签下的组件注册不验证id**/
	function analysisMainNoTabs(rs){
		var oneTitle = rs.eq(0).attr("title");
		$.each(allComponentsNoTtile,function(id,obj){
			//仅标记该组件没有被处理过
			mainTabs[oneTitle]['components'][id] = '';
		});
		$.extend(allComponents,allComponentsNoTtile);
	}
	
	/**判断当前组件是否可见**/
	function compScrollIsHidden(obj){
		obj  = typeof obj == "object" ? obj : $("#" + obj);
		var y = obj.offset().top;
        var clientHeight = document.body.clientHeight;
        if(y < clientHeight && y != 0){
        	return false;
        }
        return true;
	}
	
	/**给组件绑定下拉框滚动事件可见事件**/
	function bindCompScroll(obj,comp){
		var isExistFormMainTabs =  formMainTabs.length == 1 ? true : false;
		
		var fun = function(){
	        if(compScrollIsHidden(obj) == false){
	        	//解除绑定
	        	unbindCompScroll(obj);
	        	setTimeout(function(){
	        		comp.showComp.call(comp);
	        	},1);
	        }
		};
		
		 if(isExistFormMainTabs === false){
			 $(window).bind("scroll." + obj.attr("id"), fun);
		 }else{
			 $(formMainTabs.tabs('tabs')).each(function(i,o){
				 $(o).bind("scroll." + obj.attr("id"), fun);
			 });
		 }
	}
	
	/**根据组件是否隐藏是否可见决定是否延迟加载组件
	 * 先判断当前组件是否隐藏 在判断是否可见**/
	function getCompScroll(id,comp){
		var obj = typeof id == "object" ? id : $("#" + id);
		if(obj.length == 0)return false;
		if(obj.is(':hidden') == false && compScrollIsHidden(obj) == true){
			bindCompScroll(obj,comp.src);
			return false;
		}
		return true;
	}
	
	/**接触事件绑定**/
	function unbindCompScroll(id){
		var objId = typeof id == "object" ? $(id).attr("id") : id;
		var isExistFormMainTabs =  formMainTabs.length == 1 ? true : false;
		if(isExistFormMainTabs === false){
			$(window).unbind("scroll." + objId);
		}else{
			$(formMainTabs.tabs('tabs')).each(function(i,o){
				$(o).unbind("scroll." + objId);
			});
		}
	}
	
	function tabParse(title){
		
		if(title == undefined || title == "") {
			if(selectEventInited === false){
				selectEventInited = true;
				addTabEvent();
			}
			title = firstTitle;
		}
		
		var o = mainTabs[title] ;
		
		if(o != undefined && o.running === false){
			 o.running = true;
			 //首先解析UI组件 
			 if($xcp.task.isUpdateData()){
				$.parser.parse(o.src,false,["combobox","datebox",'validatebox',"linkbutton","searchbox","tabs"]); 
			 }else{
				$.parser.parse(o.src,false,["combobox","tabs"]); 
			 }
//			 delete  mainTabs[title];
		}
		
		if(o != undefined ){
			var components = o.components || {};
			 $.each(components,function(id,component){
				 var comp = allComponents[id];
				 if(comp && comp.running == false){
					 if(comp.src.getInitState && comp.src.getInitState() !== true){
						 if($.isFunction(comp.src.showComp)){
							 getCompScroll(id,comp);
						 }
					 	 comp.src.init.call(comp.src);
					 }else{
						 if($.isFunction(comp.src.resize)){
							 comp.src.resize.call(comp.src);
						 }
					 }
				 }
			});
		}
	}
	
	function addComponent(id,obj,name){
		if(allComponents[id] == undefined){
			allComponents[id] = {running : false ,src : obj,name : name };
		}
	}
	
	/***增加不在title下的组件**/
	function addComponentNoTitle(id,obj,name){
		if(allComponentsNoTtile[id] == undefined){
			allComponentsNoTtile[id] = {running : false ,src : obj,name : name };
		}
	};
	
	function removeComponent(id){
		delete allComponents[id];	
		$.each(mainTabs,function(i,o){
			if(o.components[id] != undefined){
				delete o.components[id];
				return;
			}
		});
	}
	
	function execComponent(id,calleeParam){
		 if(allComponents[id] && allComponents[id]['running'] == false){
			 allComponents[id]['running'] == true;
			 allComponents[id].src.init.call(allComponents[id].src);
			 
			 if(calleeParam != undefined && $.isFunction(calleeParam)){
				 calleeParam();
			 }
			 //移除组件
			 removeComponent(id);
		 }
	}
	
	function addTabEvent(){
		var opt = $("#" + $xcp.formPubMgr.defaults.tableDivId).tabs('options');
		var onselectfun = opt.onSelect || function(){};
		$.extend(opt,{
			onSelect : function(title){
				onselectfun(title);
				$xcp.compMgr.tabParse(title);
			}
		});
	}
	
	//如果提交时，页面中还有某些页签没有做easyui解析时，做解析处理，主要用于提交的校验判断,该方法应在表单校验之前调用
	function commitProcess(){
		for(var title in mainTabs){
			var o = mainTabs[title];
			if(o && o.running !== true ){
				tabParse(title,false);
			}
		}
	}
	
	function exec(){
		
		if(window.easyui_parse_process_group === true){
			tabParse();
		}else{
			$.each(allComponents,function(id,o){
				//execComponent(id);
			});
		}
	}
	
	function rwTabContent(){
		if(window.easyui_parse_process_group === true){
			$.each(mainTabs,function(title,o){
				$(o.src).html(o.content);
				delete o.content;
			});
			$.parser.parse(null,false,["numberbox"]); 
		}
	}
	
	function componentInitState(){
		var blean = true;
		var defViewComp = typeof $xcp.formPubMgr.defaults.viewComponent == "string" ?
				$xcp.formPubMgr.defaults.viewComponent.split(",") : "";
		var str = "";
		$.each(defViewComp,function(i,o){
			if(allComponents[o] && allComponents[o].src.getInitState() == false){
				str += allComponents[o].name + ",";
				allComponents[o].src.init();
			}
		});
		if(str != ""){
			blean = false;
			str = str.substring(0,str.length - 1);
			$.messager.alert($xcp.i18n("paramMar.tisxx"), str + $xcp.i18n("components.messages"));
		}
		return blean;
	}
	
	function componentFun(fName){
		var blean = true;
		$.each(allComponents,function(i,o){
			if($.isFunction(o.src[fName])){
				blean = o.src[fName]();
			}
			if(blean == false)return false;
		});
		return blean !== false ? true : false;
	}
	
	function componentValidate(){
		return componentFun("validate");
	}
	
	function commit(){
		return componentFun("commit");
	}
	
	//设置当前操作的组件，绑定document.click事件
	function setCurrOperComp(compId,editRowIndex){
		if(currOperCompId != null){
			if(currOperCompId != compId ||  currOperCompRowIndex != editRowIndex){
				execSaveEdit();//执行当前组件保存行编辑的函数
			}
		}		
		currOperCompId       = compId;
		currOperCompRowIndex = editRowIndex;		
		//绑定事件
		bindCompEditEvent();
	}
	
	function removeCurrOperComp(compId,editRowIndex){
		if(currOperCompId == compId &&  currOperCompRowIndex == editRowIndex){
			currOperCompId = currOperCompRowIndex = null;
			$(document).unbind('click.CompEdit');
		}
	}
	
	function bindCompEditEvent(){
		if(currOperCompId != null && currOperCompRowIndex != null){
			$(document).unbind('click.CompEdit').bind('click.CompEdit',function(e){
				 execSaveEdit();//执行当前组件保存行编辑的函数
				 if($("#" + currOperCompId).datagrid('validateRow', currOperCompRowIndex)){
					 $(document).unbind('click.CompEdit');
				 }
			});
		}
	}
	
	function  execSaveEdit(){
		var src = allComponents[currOperCompId] &&  allComponents[currOperCompId].src;
		if(src && $.isFunction(src.saveEditRow)){
			src.saveEditRow(currOperCompRowIndex);
		}
	}
	
	return {
		init : function(){
			analysisMainTabs();
		},
		
		/*增加组件*/
		addComponent : function(id,obj,name){
			addComponent(id,obj,name);
		},
		/*移除组件*/
		removeComponent:function(id){
			removeComponent(id);
		},
		
		execComponent : function(id,calleeParam){
			execComponent(id,calleeParam);
		},
		
		tabParse : function(title){	
			tabParse(title);
		},
		
		/*提交时校验处理*/
		commitProcess : function(){
			commitProcess();
		},
		
		exec : function(){
			exec();
		},
		
		rwTabContent : function(){
			rwTabContent();
		},
		//提交时组件验证交易数据是否复合规则
		componentValidate : function(){
			return componentValidate();
		},
		//提交时验证当前交易是否必须加载
		componentInitState : function(){
			return componentInitState();
		},
		//datagrid 组件的保存处理，主要用于去掉接受修改功能
		setCurrOperComp : function(compId,editRowIndex){
			setCurrOperComp(compId,editRowIndex);
		},
		//移除当前操作的组件
		removeCurrOperComp : function(compId,editRowIndex){
			removeCurrOperComp(compId,editRowIndex);
		},
		commit : function(){
			return commit();
		},
		/*增加组件  不验证id是否存在 只做 key*/
		addComponentNoTitle : function(id,obj,name){
			addComponentNoTitle(id,obj,name);
		},
		//判断当前组件是否可见 
		isHidden : function(obj){
			return compScrollIsHidden(obj);
		},
		//解除组件下拉框事件绑定
		unbindCompScroll : function(id){
			unbindCompScroll(id);
		},
		//obj --> 元素隐藏显示栏位id或对象  comp组件对象
		bindCompScroll : function(obj,comp){
			bindCompScroll(obj,comp);
		}
	};
}(jQuery);