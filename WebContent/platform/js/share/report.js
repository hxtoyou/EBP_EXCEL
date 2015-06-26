/**
 * 面函和回单公用控制js
 */
lpbc.report =  function($){
	/**
	 * 组件逻辑 手工经办 第一次点击面函按钮 从后台查询一次产品对应的面函数据
	 * 面函数据分产品数据tagData 和 扩展数据rePeyData
	 * **/
	var tagData = null , tagId = "moduleComReport" ,tagOpId = "moduleOpemReport" , load = true ,rePeyData = null, reLoad = false;
	var inited = false;
	
	function init(){
		if(load == false)return;
		tagData = $("#" + tagId).val();
		$xcp.formPubMgr.defaults.viewReport =  tagData = tagData != "" ? $xcp.parseJson(tagData): [];
		rePeyData = $("#" + tagOpId).val();
		rePeyData = rePeyData != "" ? $xcp.parseJson(rePeyData): [];
		load = false;
	}
	
	function commit(){
		if(load == true){
			doChangeData(); load = false;
		}
		
		$("#" + tagId).val($xcp.toJson($xcp.formPubMgr.defaults.viewReport||[]));
		$("#" + tagOpId).val($xcp.toJson($xcp.comBtn ? $xcp.comBtn.getComBtn():[]));
	}
	
	/**根据数据初始化*/
	function doChangeData(){
		var aopt = {
    			url   : $xcp.def.getFullUrl('./commonOther.do?method=getDocument'),
    			data  : $.extend({},$xcp.formPubMgr.getPrductPostVal("docReport"),$xcp.formPubMgr.getFormHiddenField())
    	};
		$xcp.ajax(aopt,null,null,function(rs){
			setReportData(rs["docReport"]);
		});
	}
	
	function getData(){
		if(load == true && ($xcp.task.isMuHandle() || $xcp.task.isLanunchHandle())){
			doChangeData(); load = false;
		}else{
			init();
		}
	}
	
	function getReportData(){
//		if(reLoad == false){reLoad = true; return [];}
		if(!$xcp.task.isUpdateData()){ 
			if(load == true){init();}
			return tagData.concat(rePeyData); 
		}else{
			if(load == true){
				getData();
			}
		};
		
		
		$xcp.formPubMgr.defaults.viewReport = $xcp.formPubMgr.defaults.viewReport ||[];
		return $xcp.comBtn ? $xcp.formPubMgr.defaults.viewReport.concat($xcp.comBtn.getComBtn()):$xcp.formPubMgr.defaults.viewReport;
	}
	
	function setReportData(data){
		$xcp.formPubMgr.defaults.viewReport = data;
	}
	
	function getMhList(){
		var rp  = [];//$xcp.comBtn.getComBtn()
		
		$.each($xcp.report.getReportData(),function(i,k){//lsm4
			if(k){
				var o = $.extend({},k);
				o.url = './preview.do?method=getDocReport&'; //面函给一个统一的URL
				if(o.docId){
					o.url += "cardNo="+o.docId+"&";
				}
				o.name = o.docName || o.name;
				o.tradeType = "BU";
				o.oldfun = o.clfun; //将原处理函数重载
				o.clfun = function(){
					if(o.oldfun && $.isFunction(o.oldfun)) o.oldfun();
					//打开面函
					$xcp.report.formViewReport(o);
					//执行原注入参数中的回调函数（这里有可能要修正，因为存在异步的问题）
					if(o.callback != null && $.isFunction(o.callback)) o.callback();
				};
				o.validateFun = isDocRept.bind($xcp,o.isValid);
				o.exhtml = "<div class='reportExportDiv' title='" + $xcp.i18n("formPubMgr.reportExport") + "'></div>";
				o.exportfun = function(){ createDialog(createViewReportDialogOption(o));};
				rp.push(o);
			}
		});
		
		return rp;
	}
	
	/**
	 * 查看面函(vpo 为面函数数对象)
	 */
	function formViewReport(vpo){
		var url = vpo.url ? vpo.url + 'reportId=' + vpo.reportId : ''
					+"&orgNo="+$("#butxn_tranOrgNo").xcpVal();
		if(url){
			//birtShowType in ( 'pdf','html','doc','xls' )
			vpo.birtShowType = vpo.birtShowType ? vpo.birtShowType : 'pdf';
			url += "&birtShowType=" + vpo.birtShowType;
			var id = "reportContentDiv-" + vpo.name + "-" + vpo.birtShowType;
			var name = vpo.birtShowType == "pdf" ? vpo.name : vpo.name + "->" + vpo.birtShowType;
			var reportContent = '<iframe id="' + id + '" scrolling="no" frameborder="0"  src="" style="width:100%;height:100%;"></iframe>';
			if($xcp.xcpBill)$xcp.xcpBill.commit();
			var formData =  $xcp.formPubMgr.getFormData($xcp.formPubMgr.defaults.currFormId); 
			if(vpo.data){
				formData = $.extend(formData,vpo.data);
			}
			$xcp.formPubMgr.formAddTabs(reportContent,name,null,null,null,{vpo:vpo,id:id,url:url,formData:formData,type:"doc"});
			if($xcp.getUserRptMtd() != "1" ){
				formViewReportInnerFun(vpo,id,formData,url);
			}
		}
	}
	
	function formViewReportInnerFun(vpo,id,formData,url){
		$.post($xcp.def.getFullUrl(url),formData,function(data){
			$('#' + id)[0].contentWindow.document.write(data);
			$('#' + id).contents().find("iframe").css({
				width :"95%",
				height : ($(window).height()-$("#xcpFormMenuDiv").height() - 45),
				"z-index" : 1
			},'json');
			if(vpo.birtShowType == 'pdf' && vpo.exhtml){
				$(vpo.exhtml).bind("click",vpo.exportfun).appendTo($('#' + id).parent());
			}
		});
	}
	
	function isDocRept(isValid){
		if(isValid == "Y") return true;
		if(isValid == "N") return false;
	}
	
	//生成Dialog
	function createDialog(opt){
		
		var o = opt.getInputValues ? opt.getInputValues(target) : {};
		if(opt.clfun && $.isFunction(opt.clfun)){
			opt.clfun(opt,o);
		}
		return;
		
		var id = opt.id || opt.reportId || $("#txnNo").val() || (new Date()).getTime();
		var html  = '<div id="' + id +'-menuDialog"  class="easyui-dialog" style="width:400px;height:200px;padding:10px; z-index:8000;">';
			html +=        opt.content || '';
			html += 	'<div id="dlg-buttons">';
			html += 		'<a  class="easyui-linkbutton  pubMenuDialogSaveBtn" data-options="plain:true">' + $xcp.i18n('app.confirm') + '</a>';
			html += 		'<a  class="easyui-linkbutton  pubMenuDialogCloseBtn" data-options="plain:true">' + $xcp.i18n('app.cancel') + '</a>';
			html += 	'</div>';
			html += '</div>';
	
		$(html).appendTo('body');
		var target = $("#" + id + "-menuDialog");
		$.parser.parse(target);
		
		target.dialog({
			title   : opt.title,
			draggable: false,
			modal   : true,
			//iconCls : 'icon-tip',
			buttons : '#dlg-buttons',
			onClose     : function(){
				$(this).dialog('destroy');
			}
		});
		
		if(opt.eleDatas && $.isArray(opt.eleDatas)){
			$.each(opt.eleDatas , function(i,o){
				if(o && o.id){
					$("#" + o.id,target).combobox({
						valueField : o.valueField,
						textField  : o.textField ,
						data       : o.data
					});
				}
			});			
		}
		
		target.find('a.pubMenuDialogSaveBtn').bind('click',function(){
		    var o = opt.getInputValues ? opt.getInputValues(target) : {};
			if(opt.clfun && $.isFunction(opt.clfun)){
				opt.clfun(opt,o);
			}
			target.dialog('destroy');
		});
		
		target.find('a.pubMenuDialogCloseBtn').bind('click',function(){
			target.dialog('destroy');
		});
	}
	
	//创建面函导出时在DIALOG中显示的内容
	function createViewReportDialogOption(tvop){
		var opt = {};
		opt.title = "面函导出";
	    
		var rps = [];
		$.each($xcp.formPubMgr.defaults.viewReport,function(i,o){
			if(!o.validateFun ||  $.isFunction(o.validateFun) && o.validateFun() !== false){
				rps.push(o);
			}
		});
	
		var exportFileTypes = [
		       {name : 'doc',text : 'Word'},    
		       {name : 'xls',text : 'Excel'}
		];
		
		opt.eleDatas = [
		     {textField : 'name',valueField :'reportId', id:'reportId',data : rps},     
		     {textField : 'text',valueField :'name', id:'birtShowType',data : exportFileTypes},     
		];
		
	    
		var tabHtml = "<table style='width:95%'>";
			tabHtml += '<tr>';
			tabHtml +=     '<td width="40%" align=right>导出文件类型:&nbsp;&nbsp;</td>';
			tabHtml +=     '<td width="50%" align=left><input class="easyui-combobox" value="doc" id="birtShowType" name="birtShowType"></input></td>';
			tabHtml += '</tr>';
			tabHtml += '</table>';
			
	     opt.content = tabHtml;
	     
	     opt.clfun =  function(opt,p){
	    	 if(p && p.reportId){
	    		 var vpo = $.extend({},tvop,p);
	    		 if(vpo != null){
	    			 if(vpo.oldfun && $.isFunction(vpo.oldfun)) vpo.oldfun();
					 //打开面函
	    			 $xcp.menuMgrFun.formViewReport(vpo);
					 //执行原注入参数中的回调函数（这里有可能要修正，因为存在异步的问题）
					 if(vpo.callback != null && $.isFunction(vpo.callback)) vpo.callback();
	    		 }
	    	 }
	     };
	     
	     opt.getInputValues = function(target){
	    	 var o = {};
	    	 o.reportId     = tvop.reportId;//$("#reportId",target).combobox('getValue');
	    	 o.birtShowType = 'doc';//$("#birtShowType",target).combobox('getValue');
	    	 return o;
	     };
		 return opt;
	}
	
	function validate(){
	}

	function resize(){
	}
	
	/**插入菜单按钮**/
	function impMenu(){
		var jon = {
                "tradeType": "BU",
                "btnName": "formPubMgr.surfaceCont",
                "iconCss": "form",
                "bindClkFun": "",
                "subBtns": $xcp.report.getMhList,
                "index":3
            };
        $xcp.menuMgr.addMenu(jon);
	}
	
	return {
		init : function(){
			inited = true;
			//impMenu();
			//init();
		},
		commit : function(){
			commit();
		},
		doChangeData : function(){
			doChangeData();
		},
		getReportData : function(){
			return getReportData();
		},
		setReportData : function(data){
			setReportData(data);
		},
		getData : function(){
			getData();
		},
		getMhList : function(){
			return getMhList();
		},
		//查看面函
		formViewReport  : function(vpo){
			formViewReport(vpo);
		},
		formViewReportInnerFun : function(vpo,id,formData,url){
			formViewReportInnerFun(vpo,id,formData,url);
		},
		validate : function(){
			validate();
		},
		resize: function (){
			resize();
		},
		getInitState : function(){
			return inited;
		}
	};
}(jQuery);
if($xcp.compMgr){
	$xcp.compMgr.addComponentNoTitle("moduleComReport",$xcp.report);
}