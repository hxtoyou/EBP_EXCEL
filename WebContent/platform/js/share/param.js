/**
 * 参数配置  共用js负责展现参数模板的
 */
lpbc.paramMar = function($){
	/***edintFromWindow 双击弹出窗口的window对象。linkButLskAdd - 新增按钮。linkButLsdSave - 弹出框的确认按钮。linkButLsdBreak - 弹出框的取消按钮。
	 * linkButLskExport 导出按钮对象。paramQueryGridId
	 * 
	 * ***/
	var target = null ,  chrHidField = null, chrInitData = null, formTarget = null,
	formDataTarget = null, comboxObj = null,  keyColsObj = null,keyColsObjField = null,
	keyField = null,keyColsObjMy = {},mentStats = null, 
	temMentSt = null, dbClickObj = null, inited = false;
	var paramDef = null, edintFromWindow = null , linkButLskAdd = null,linkButLsdSave = null,
	linkButLsdBreak = null,linkButLskExport = null,paramQueryGridId = null;
	//缓存区域  批注信息
	var annotationJson = {} , annotationField = null;
	
	/**静态参数初始化**/
	function init(){
		getChrHidFieldValue();	//生成初始数据
		createparamTab();	    //创建下面列表
		 
		bindEventFun();			//绑定事件
		paramDmlWrapDispInit();
		
		//初始化完毕后 的函数
		initCondition();
	};
	
	/**初始化时获取隐藏字段的值(用于前后台交互)  默认赋初始值*/
	function getChrHidFieldValue(){
		var bodyEment = $("body");
		
		target = $xcp.def.createXcpHidEvent(target,bodyEment);
		formTarget = $xcp.def.createXcpHidEvent(formTarget,bodyEment);
		formDataTarget = $xcp.def.createXcpHidEvent(formDataTarget,bodyEment,{target:"form"});
		
		//参数操作列表值存放隐藏域
		chrHidField = $xcp.def.createXcpHidEvent("sysDataParamInfo");
		chrInitData = $xcp.parseJson(chrHidField.val() || '[]');
		
		//参数批注值存放隐藏域
		annotationField = $xcp.def.createXcpHidEvent("param_annotationJson");
		
		//经办和经办更正下显示查询列表 
		if($xcp.task.isUpdateData()){
			paramDef.queryJs["dblClick"] = dbClcikQueryFun;
			//创建查询列表
			paramQuery();
		}else{
			target.attr("id",paramDef.paramSDiv + "_doCheck");
			$("#paramQueryListTab").attr("id",paramDef.paramSDiv); 
			$(".paramDmlWrap").show();
		}
		
		//双击弹出框的window对象
		edintFromWindow = $("#edintFromWindow");
		linkButLsdSave = $("#linkButLsdSave");linkButLsdBreak = $("#linkButLsdBreak");
		paramQueryGridId = $("linkButLskExport");linkButLskAdd = $("#linkButLskAdd");
		linkButLskExport = $("linkButLskExport");
	};
	
	/**创建结果集列表**/
	function createparamTab(){
		var paramList = paramDef.paramList;
		var toolbar = [
	   		    {
	   		    	text    :'<b>'+$xcp.i18n("paramMar.shujlb")+'</b>',
	   		    	iconCls : ''
	   		    } 
	   		];
		
   		//初始化表格数据
		target.datagrid({
   			columns:getRow(paramList),
   			height: 337,
   			toolbar : toolbar,
   			loadMsg : $xcp.i18n('sys.loadMsg'),
   			singleSelect: true,
   			rownumbers:true,
   			remoteSort:false,
   			fitColumns:true,
   			pagination:true,
   			border:false,
   			nowrap:true,			    
   			striped:true,
   			autoRowHeight:false,
   			idField : "closparamId",
   			pageSize: 10,//每页显示的记录条数，默认为10  
   		    pageList: [5,10,15,20],//可以设置每页记录条数的列表  
   			onDblClickRow : function(index,rowData){
				dListEdintForm(rowData,index);
   			}
   		});
   	   
   		if(chrInitData != null && chrInitData != ""){
   			target.datagrid('loadData',chrInitData); 
   			$(".paramDmlRecordCnt").html(chrInitData.length);
   		}
	};
	
	/**生成显示的列**/
    function getRow(comlList){
    	if(!comlList) return comlList;
    	
		var columnsJs = [[]];
   	    $.each(comlList,function(i,o){
   		   var strJs = {
 	   			  title : o.text,field : o.name, 
	   			  width : o.width != null ? o.width :170,   
	   			  align : o.align != null ? o.align :'center',
	   			  hidden : o.hidden == true ? o.hidden :false ,
				  formatter : $.isFunction(o.formatter)? o.formatter :null,
				  sortable : true,
				  sorter : $.isFunction(o.sorter)? o.sorter : null  
	   	   };
   		   columnsJs[0].push(strJs);
   	    });
	   	columnsJs[0].push({
	   		  title : $xcp.i18n("paramMar.caoz"), 
	   		  field : "enditData", 
 			  width : 100,   
 			  align : 'center',
 			  formatter:enditDataField
	   	});
		return columnsJs;
	};
	
	function enditDataField(value,rowData,rowIndex){ 
		var html  = "<div class='paramDmlOperaBtnWrap'>";
		    html += 	'<span class="modL" onClick = "$xcp.paramMar.dListEdintForm(\''+rowData.closparamId+'\',\'sava\');"></span>';
		if($xcp.task.isUpdateData()){
			html += 	'<span class="delL" onClick = "$xcp.paramMar.dListdelForm(\''+rowData.closparamId+'\');" ></span>';
		}
			html += "</div>";
		return html;
	}
	
	/**绑定事件**/
	function bindEventFun(){
		var wh = paramDef.windowHegith || $(window).height();
		    wh = wh > 500 ? wh -100 : ( paramDef.windowHegith || 400);
		var tanCk = $xcp.task.isUpdateData() ? $xcp.i18n("paramMar.bianjsj") : $xcp.i18n("paramMar.chaksj");
		edintFromWindow.window({
			width:paramDef.windowWidth,
			height: wh,
			style : {borderColor:'#000'},
			maximized : paramDef.maximized || false,
			minimizable : false,
			collapsible : false,
			title : tanCk,
			modal : false,
			shadow: false,
			closed :true
		}).show();
		 
		if($xcp.task.isUpdateData()){
			//新增按钮 点击事件绑定
			linkButLskAdd.bind("click",function(){
				//如果已经提交了 则让新增按钮时失效
				if($xcp.task.isUpdateData() && $xcp.formPubMgr.defaults.doHandle.submission == true){
					//提示用户
					parent.$.messager.alert($xcp.i18n("paramMar.tisxx"),$xcp.i18n("paramMar.cannotAddOrDelOrUpdMessage"));
					return ;
				}
			
				//其次弹出添加数据的from表单
				$xcp.paramMar.insterFromData();
			});
			
			//批量导出数据
			linkButLskExport.bind("click",function(){
				paramDataExport();
			});
			bindParamDmlCntEvent();
		}else{
			$(".paramDmlRecordCnt").hide();
		}
		linkButLsdSave.bind("click",function(){$xcp.paramMar.savaFromData();});
		linkButLsdBreak.bind("click",function(){$xcp.paramMar.refreshFromData();});
	}
	
	/**自适应调整**/
	function initCondition(){
		var nortHeight = $("#condition").outerHeight();
		linkButLskExport.height(nortHeight);
		linkButLskAdd.height(nortHeight);
		$('.paramQueryAll').height(nortHeight);
		$(window).resize(function() {
			setTimeout(function(){
				var ght = $("#condition").outerHeight();
				linkButLskExport.height(ght);
				linkButLskAdd.height(ght);
				$('.paramQueryAll').height(ght);
				paramQueryGridId.datagrid('resize', {
			        width:function(){return $("#npanel").outerWidth();}
			    });
				$('body').layout('panel', 'center').panel('resize', {
					width : $(window).outerWidth()
				});
				$('body').layout('resize');
			},200);
		});
		
		$xcp.leftTreeMgr.addWestSizeFun(function(){
			setTimeout(function(){
				var ght = $("#condition").outerHeight();
				linkButLskExport.height(ght);
				linkButLskAdd.height(ght);
				$('.paramQueryAll').height(ght);
				paramQueryGridId.datagrid('resize', {
			        width:function(){return $("#npanel").outerWidth();}
			    });
			},50);
		});
   };
   
	//增删改查的表格显示状态初始化
	function paramDmlWrapDispInit(isHidden){
		var wrap =  $('.paramDmlWrap');
		var bg   =  $('.paramDmlWrapBg');
		
		if(isHidden == null){
			bg.hide();
			bg.css({
				"filter":"alpha(opacity=60)",
				"-ms-filter": "progid:DXImageTransform.Microsoft.Alpha(Opacity=60)",
				"opacity": 0.6
			});
			wrap.css({left:'-10000px'});
		}else{
	        if(isHidden === true){
	        	wrap.css({left:'-10000px'});
	        	bg.hide();
			}else{
				var l = ($(window).width()  - wrap.width()) / 2;
				var t = ($(window).height() - wrap.height()) / 2;
				
				bg.show();
				wrap.css({top: (t < 20 ? 20 : t) + "px",left: (l < 20 ? 20 : l) + 'px'});
			}
		}
		$('.paramDmlRecordCnt').attr('hiddenFlag',isHidden == null || isHidden == true? "Y" : "N"); 
	}
	
	/**------------双击 保存 修改 函数区------------**/
	
	/**查询列表双击执行函数**/
	function dbClcikQueryFun(rowData){
		if($xcp.task.isUpdateData() && $xcp.formPubMgr.defaults.doHandle.submission == true){
			parent.$.messager.alert($xcp.i18n("paramMar.tisxx"),$xcp.i18n("paramMar.cannotAddOrDelOrUpdMessage"));
			return;
		}
		
		//如果是点击的修改按钮 不是双击
		if(typeof rowData != "object"){
			paramQueryGridId.datagrid("selectRow",rowData);
			rowData = paramQueryGridId.datagrid("getSelected");
		}
		
		showEdintForm(rowData,"update");
		
		//保存初始值 页面没有修改清空下不允许确认
 		savaDbClickObj();
	}
	
	/**如果当前机构不在执行机构的下属机构列表不允许保存**/
	function byAllow(rowData){
		var isAllow = false;
		var mvcName = paramDef.mvcName;
		if( rowData.allow && 'N' == rowData.allow && rowData[mvcName+'_orgNo'] && rowData[mvcName+'_orgNo'] != $('#butxn_tranOrgNo').val() ){
			isAllow = true;
		}
		return isAllow;
	}
	
	/**显示弹出列表**/
	function showEdintForm(rowData,stats){
		if(!paramDef.defOperation || paramDef.defOperation["update"] != false){
			linkButLsdSave.show();
		}else{
			linkButLsdSave.hide();
		}
		
		if( byAllow(rowData) ){
			edintFromWindow.window("setTitle",$xcp.i18n("paramMar.chaksj")); 
			linkButLsdSave.hide();
		}
		
		if(stats == "sava"){
			keyColsObjMy = {};
			if(keyColsObj != null){
				$.each(keyColsObj,function(i,k){
					keyColsObjMy[i] = rowData[i];
				});
			};
		}
		
	    if(keyColsObj != null){
			 var blean = analyseColsKey(rowData,target.datagrid('getData').rows,false);
			 if(!blean){
				 $.messager.confirm($xcp.i18n("paramMar.tixck"),$xcp.i18n("paramMar.cunzsjlbyz"),function(r){   
					   if (r){
					    	var srArray = showParmList(rowData);
					    	dListEdintForm(srArray,'sava'); 
					    	
					    	$.isFunction(paramDef.queryJs.dblClickTrue) 
					 		? paramDef.queryJs.dblClickTrue(rowData) : "";
			 		
					    	return false;
					   }
				 });
				 return false;
			 }
		}
		 
		createClosparamId();
		 
		edintFromWindow.window("open"); 
		 
  		temMentSt = stats;
		 
		//清空值
		closeDataFun(rowData);
		 
		//load表单数据
		formDataTarget.form("load",dataParamPx(rowData));
		  
		//隐藏和显示
		hidShow(temMentSt);
		 
		//执行查询列表双击业务注册函数
		$.isFunction(paramDef.queryJs.dblClickTrue) ? paramDef.queryJs.dblClickTrue(rowData) : "";
	}
	
	/**批注双击更换信息*/
    function dListEdintForm(rowData,stats){
    	if(typeof rowData != "object"){
    		target.datagrid('selectRecord',rowData);
    		rowData = target.datagrid('getSelected');
		}
    	
		emptyFromData();
		
		//展示修改内容
		showAnnotationParam(rowData);
		
		formDataTarget.form('load',dataParamPx(rowData));
		
		edintFromWindow.window("open");
		
    	if(!$xcp.task.isUpdateData()){
    		linkButLsdSave.hide();
		}else{
			linkButLsdSave.show();
		}
    	$.isFunction(paramDef.paramList.dblClick) ? paramDef.paramList.dblClick(rowData) : "";
		
		keyColsObjMy = {};
		if(keyColsObj != null){
			$.each(keyColsObj,function(i,k){
				keyColsObjMy[i] = rowData[i];
			});
		}
		
		hidShow("sava");
		
		return  "";
    }
    
	/**关闭弹出窗口**/
	function hidShow(stat){
		if(stat != "" && stat != null){
			mentStats = stat;
		}else{
			edintFromWindow.window("close"); 
			//更新条数
			fuDsum();
		}
	}
	
	/**清空表单值*/
	function emptyFromData(){
		$xcp.annotationMgr.closeAnnotationSpr(formDataTarget);//情况批注值
		formDataTarget.find(paramDef.classPackDocument).each(function(){
			 if($(this).attr("id") && !$(this).hasClass("easyui-numberbox")){
				 $(this).xcpVal("");
			 }else{
				 $(this).val("");
			 }
		});
		$("[type='checkbox']:checked").attr("checked",false);//[type='radio']:checked,
	}
	
	function closeDataFun(rowData){
		emptyFromData();
		$.isFunction(paramDef.defBevent) ? paramDef.defBevent(mentStats,rowData) : '';
		$(".validatebox-invalid","#edintFromWindowForm").removeClass("validatebox-invalid");
	}
	
	/**保存信息*/
	function savaDbClickObj(){
		dbClickObj = formDataTarget.form('getXcpFormData',true);
	}
	
	/**创建隐藏主键id栏位**/
	function createClosparamId(){
    	$xcp.def.createXcpHidEvent("closparamId",formDataTarget).attr("value","-1");
    }
	
	/*** 当双击数据已存在数据列表时 返回数据列表值*/
	function showParmList(rowData){
		var data = target.datagrid('getData').rows;
		var blean = null;var paramData = null;
		$.each(data,function(i,o){
			if(blean && i!=0) return false;
			blean = true;
			$.each(keyColsObj,function(k,l){
				if(k == "repeatKeyMes_Param") return;
				if(o[k] != rowData[k]){
				   blean = false;
				   return false;
				}
			});
			
			if(blean){
				paramData = o;
				return false;
			}
		});
		return paramData;
    }
	
    /**展示批注信息**/
	function showAnnotationParam(data){
		if(valitAnnotation(data) == false) return ;
		var jon = annotationJson[data.closparamId];
		if(!jon)return;
		$xcp.annotationMgr.showAnnotationSpr(jon);
	}
	
	function valitAnnotation(data){
		if($xcp.task.isCorrect() && data.status == "add"){
			if(!annotationJson["anto_" + data.closparamId]){
				annotationJson["anto_" + data.closparamId] = $.extend({},data);
			}
			return true;
		}
		if(data.status != "update") return false;
		return true;
	}
	
	function showAnnotationParam(data){
		if(valitAnnotation(data) == false) return ;
		var jon = annotationJson[data.closparamId];
		if(!jon)return;
		$xcp.annotationMgr.showAnnotationSpr(jon);
	}
	
	function delAnnotationJson(data){
		if(valitAnnotation(data) == false) return ;
		delete annotationJson[data.closparamId];
		delete annotationJson["anto_" + data.closparamId];
	}
	/**展示批注信息 end**/
	
	/**
	 * 判断用户输入主键列 是否存在
	 * 0.循环key对象修改的时候如果提交的数据和原来数据一样不走下面流程
	 * 1.循环结果集列表数据  遇到一个匹配主键的存在就返回  rowsData
	 * 2.循环主键列（包括多个主键列）  keyColsObj
	 * 3.主键遇到一个主键数据存在往新建数组里面记录  pads
	 * 4.比较新主键是否和主键数组相等 相等则表示多个主键值都相等
	 */
	function analyseColsKey(queryValue,rowsData,isSava){
		if(rowsData == null || rowsData == "" || keyColsObj == null){
			return true;
		}
		var blean = true;
		var pads = null;
		
		var twEca = false;
		if(false !== isSava){
			$.each(keyColsObj,function(j,k){
				if(j == "repeatKeyMes_Param"){
					return;
				}
				twEca = true;
				if(queryValue[j] != keyColsObjMy[j]){
					twEca = false;
					return false;
				}
			});
		}
		
		 if(twEca) return true;
		 $.each(rowsData,function(i,o){
			pads = {};
			$.each(keyColsObj,function(j,k){
				var inpVar = queryValue[j];
				if(j == "repeatKeyMes_Param"){
					return;
				}
				if(o[j] == inpVar && inpVar != ""){
					pads[j] = k;
				}
			});
			 
			 keyColsObj["repeatKeyMes_Param"] = "";
			 $.each(keyColsObj,function(j,k){
				if(j == "repeatKeyMes_Param"){
					return;
				}
				twEca = true;
				if(!pads[j] ){
					twEca = false;
					return false;
				}else{
					keyColsObj["repeatKeyMes_Param"] = (keyColsObj["repeatKeyMes_Param"]||"") + k + ",";
				};
			});
			if(twEca){
				blean = false;
				return false;
			}
		});
		return blean;
	}
	
	function fuDsum(){
		$(".paramDmlRecordCnt").html(target.datagrid("getData").total);
	}
	
	/**列表值加载到form表单时加前缀*/
	function dataParamPx(oldData){
		if(paramDef.paramPrefix != null){
			var ptPrex = paramDef.paramPrefix.general || "";
			var newData = {};
			
			$.each(oldData,function(i,k){
				if(i === "closparamId"){
					newData[i] = k;
					return ;
				}
				if($("#"+ptPrex+i).length > 0){
					newData[ptPrex+i] = k;
					return ;
				}
				newData[i] = k;
			});
			return newData;
		}
		return oldData;
	}
	
	/**form表单时到列表值去掉前缀*/
	function dataParamElim(oldData){
		if(paramDef.paramPrefix != null){
			var ptPrex = paramDef.paramPrefix.general || "";
			var newData = {};
			$.each(oldData,function(i,k){
				if(i === "closparamId"){
					newData[i] = k;
					return ;
				}
				if(i.substring(0,ptPrex.length) == ptPrex){
					newData[i.substring(ptPrex.length,i.length)] = k;
				}else{
					newData[i] = k;
				}
			});
			return newData;
		}
		return oldData;
	}

	/**调用查询列表对象的初始化函数**/
	function paramQuery(){
		$xcp.paramQueryPul(paramDef.queryJs);
	};
	
	function bindParamDmlCntEvent(){
		$('.paramDmlRecordCnt').bind('click.xcpParam',function(){
			var isHidden = $(this).attr('hiddenFlag') == "Y" ? false : true;
			paramDmlWrapDispInit(isHidden);
		}).show();
	}
	
	//增删改查的表格显示状态初始化
	function paramDmlWrapDispInit(isHidden){
		var wrap =  $('.paramDmlWrap');
		var bg   =  $('.paramDmlWrapBg');
		
		if(isHidden == null){
			bg.hide();
			bg.css({
				"filter":"alpha(opacity=60)",
				"-ms-filter": "progid:DXImageTransform.Microsoft.Alpha(Opacity=60)",
				"opacity": 0.6
			});
			wrap.css({left:'-10000px'});
		}else{
	        if(isHidden === true){
	        	wrap.css({left:'-10000px'});
	        	bg.hide();
			}else{
				var l = ($(window).width()  - wrap.width()) / 2;
				var t = ($(window).height() - wrap.height()) / 2;
				
				bg.show();
				wrap.css({top: (t < 20 ? 20 : t) + "px",left: (l < 20 ? 20 : l) + 'px'});
			}
		}
		$('.paramDmlRecordCnt').attr('hiddenFlag',isHidden == null || isHidden == true? "Y" : "N"); 
	}
	
	/**点击新增按钮执行函数**/
	function insterFromData(){
		linkButLsdSave.show();
		edintFromWindow.window("open"); 
		hidShow("add");
		//清空表单数据
		closeDataFun();
		
		//最后触发点击的函数
		if($.isFunction(paramDef.queryJs.addClick)){
			paramDef.queryJs.addClick();
		}
	}
	
	function maxLengthValid(total){
		var nt = paramDef.maxLength;
		if(parseFloat(total) >= parseFloat(nt)){
			$.messager.alert($xcp.i18n("paramMar.tisxx"),$xcp.i18n("paramMar.maxlength"));
			return false;
		}
		return true;
	}
	
	/**弹出框确定按钮
	 * 根据 mentStats 状态表示区分 增 删 该 在调用不同的处理函数
	 * **/
	function savaFromData(){
		if(mentStats == null){ return; };
		
		$xcp.validErrorMes.refresh();
		//表单校验
		if(!formDataTarget.form('validate')){
			$xcp.validErrorMes.show();
			return;
		}
		
		var drigData = target.datagrid('getData');
		//验证最大条数
		if(mentStats != "sava" && maxLengthValid(drigData.total) == false)return false;
		
		var fromData = formDataTarget.form('getXcpFormData',true);
		//操作数据列表主键校验
		if(!analyseColsKey(fromData,drigData.rows)){
			 parent.$.messager.alert($xcp.i18n("paramMar.tisxx"),$xcp.i18n("paramMar.yijczsj").format(fengStr(keyColsObj.repeatKeyMes_Param)));
			 return false;
		}
		
		//保存之前做页面交易验证处理
		if(($.isFunction(paramDef.validate) ? paramDef.validate():true) == false){
			return false;
		};
		
		//校验数据库存在主键 只在新增的时候校验
		if(!validatePriKeyForData()){
			return false;
		}
		
		switch(mentStats){
		    case "add":
		    	addparamFromData(fromData);
		    	break;
		    case "update":
		    	$xcp.paramMar.alterparamFromData(fromData);
		    	break;
		    case "sava":
		    	$xcp.paramMar.savaparamFromData(fromData);
		    	break;
		}
		
		emptyFromData();
		hidShow();
	}
	
	/**新增数据**/
	function addparamFromData(jsonData){
		var cloStr = "paramId-" + $xcp.paramMar.getParamId() + "ls";
		jsonData.closparamId = cloStr;
		jsonData.paramNo = $xcp.paramMar.getParamId();
		jsonData.status = "add";
		target.datagrid('insertRow',{row : dataParamElim(jsonData)});
		return true;
	}
	
	/**修改数据**/
	function alterparamFromData(fromData){
		var cloStr = "paramId-" + $xcp.paramMar.getParamId() + "ls";
    	if(isUpdateDbClickObj(cloStr,fromData)){
    		parent.$.messager.alert($xcp.i18n("paramMar.tisxx"),$xcp.i18n("paramMar.pageCanNotBeSaved"));
    		return;
    	}
		var jsonData =  fromData; 
		if(jsonData.closparamId == "-1"){
			jsonData.status = "update";
			jsonData.closparamId = cloStr;
			target.datagrid('insertRow',{row : dataParamElim(jsonData)});
		}else{
			parent.$.messager.alert($xcp.i18n("paramMar.tisxx"),$xcp.i18n("paramMar.cuowdcz"));
		}
		dbClickObj = null; //清楚保存数据
		return true;
	}
	
	/**右下角结果集列表**/
	function savaparamFromData(jsonData){
		if(jsonData.closparamId != "-1" && jsonData.closparamId != "" 
				&& jsonData.closparamId != undefined ){
			var indexK = analyseColsIndexRow({closparamId:jsonData.closparamId},target.datagrid('getData').rows);
			target.datagrid('updateRow',{row : dataParamElim(jsonData),index:indexK});
		    alrAnnotations(jsonData);
		}else {
			parent.$.messager.alert($xcp.i18n("paramMar.tisxx"),$xcp.i18n("paramMar.meiysj"));
		}
		keyColsObjMy = {};//清空编辑列表双击保存的主键值
    	$.each(keyColsObjMy||[],function(i,o){
    		keyColsObjMy[i] = "";
    	});
		return true;
	}
	
	/**
	 * 校验主键函数（可以校验自定义字段）
	 * 参数格式 params = {tabname:'paccy',Msg:'币种编号',validateData:{cursign:'12'}};
	 * tabname -- 表名
	 * Msg -- 提示信息（传递国家化格式的）
	 * validateData -- 校验的字段名 和 值
	 */
	function validatePriKeyForData(params){
		var param;
		var flag = true;
		var msg = "";
		if(params){
			param = {
					"tabname" : params.tabname,
					"validateData" : ""
			};
			msg = params.Msg;
			param.validateData = $xcp.toJson(params.validateData);
		}else{
			if(keyColsObj){
				param = {
						"tradeNo" : $('#butxn_tradeNo').xcpVal(),
						"validateData" : ""
				};
				keyColsObj["repeatKeyMes_Param"] = "";
				var copykeyColsObj = {} , keyObj = {};
				
				$.each(keyColsObj,function(index,b){
					if(index!='repeatKeyMes_Param'){
						if($('#'+index).xcpVal() == "" || (keyField != null && keyField[index])) return;
						//2015.1.15  正则清空前后空格
						copykeyColsObj[keyColsObjField[index]] = $('#'+index).xcpVal().replace(/^\s+|\s+$/g,'');
						keyColsObj["repeatKeyMes_Param"] = (keyColsObj["repeatKeyMes_Param"]||"") + b + ",";
					}
				});
				
				delete copykeyColsObj["repeatKeyMes_Param"];
				
				if($.isEmptyObject(copykeyColsObj)) return true;
				
				param.validateData = $xcp.toJson(copykeyColsObj);
				
				if($.isEmptyObject(keyObj)){
					param.keyObj = $xcp.toJson(keyObj);
				}
			}
		}
		if((mentStats =='add' || $('#status').xcpVal() =='add') ){
			$xcp.ajax({
				url : $xcp.def.getFullUrl("./validateData.do?method=validateData"),
				data : param
			},null,function(rs){
				if(rs.flag == "false"){
					flag = false;
				}
			});
		}
		if(!flag){
			parent.$.messager.alert($xcp.i18n("paramMar.tisxx"), $xcp.i18n(
			"paramMar.validatePriKey").format(msg || fengStr(keyColsObj.repeatKeyMes_Param)));
			return false;
		}
		return true;
	}
	
	/**获取参数操作记录的唯一主键 只用于前端处理**/
	function getParamId(){
		function S4() {
			return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
		}
		return   ("param-"+S4() + "-" + S4() + "-" + S4());
	}
	
	/**删除事件**/
	function delparamFromData(rowDatas){
		if($xcp.task.isUpdateData() &&
				$xcp.formPubMgr.defaults.doHandle.submission == true){
			//提示用户
			parent.$.messager.alert($xcp.i18n("paramMar.tisxx"),$xcp.i18n("paramMar.cannotAddOrDelOrUpdMessage"));
			return ;
		}
		
		paramQueryGridId.datagrid("selectRow",rowDatas);
		var jsonData = paramQueryGridId.datagrid("getSelected");
		if(!analyseColsKey(jsonData,target.datagrid('getData').rows,false)){
			 parent.$.messager.alert($xcp.i18n("paramMar.tisxx"),$xcp.i18n("paramMar.yijczsj").format(fengStr(keyColsObj.repeatKeyMes_Param)));
			 return false;
		}
		 
		$.messager.confirm($xcp.i18n("paramMar.tixck"),$xcp.i18n('validate.messager'),function(r){   
		    if (r){
		    	if(maxLengthValid() == false)return false;
		    	//点击删除时，delValidateFun（） 20141010
		    	var bleans = $.isFunction(paramDef.delValidateFun) ? paramDef.delValidateFun(mentStats,jsonData) : '';
		    	if(bleans === false)return parent.$.messager.alert($xcp.i18n("paramMar.tisxx"),$xcp.i18n("paShopTypeName")) ;
		    	
		    	var cloStr = "paramId-"+$xcp.paramMar.getParamId()+"ls";
				jsonData.status = "delete";
				jsonData.closparamId = cloStr;
				target.datagrid('insertRow',{row : dataParamElim(jsonData)});
				fuDsum();
		    }
		});
	}
	
	/**右下角删除**/
	function dListdelForm(rowIndex){
		if(!$xcp.task.isUpdateData()){
				return ;
		}
		$.messager.confirm($xcp.i18n("paramMar.tixck"),$xcp.i18n('validate.messager'),function(r){   
		    if (r){   
		    	target.datagrid('selectRecord',rowIndex);
		    	var row = target.datagrid('getSelected');
				if(row != null  ){
					delAnnotationJson(row);
					var rowIndex = target.datagrid('getRowIndex',row);
					target.datagrid('deleteRow',rowIndex);
					fuDsum();
				}
		    }   
	    });  
	}
	
	/**
	 * 批注双击更换信息
	 */
    function dListEdintForm(rowData,stats){
    	if(typeof rowData != "object"){
    		target.datagrid('selectRecord',rowData);
    		rowData = target.datagrid('getSelected');
		}
    	
		emptyFromData();
		
		//展示修改内容
		showAnnotationParam(rowData);
		
		formDataTarget.form('load',dataParamPx(rowData));
		
		edintFromWindow.window("open");
		
		$.isFunction(paramDef.paramList.dblClick) ? paramDef.paramList.dblClick(rowData) : "";
    	
    	if(!$xcp.task.isUpdateData()){
    		linkButLsdSave.hide();
		}else{
			linkButLsdSave.show();
		}
		
		keyColsObjMy = {};
		if(keyColsObj != null){
			$.each(keyColsObj,function(i,k){
				keyColsObjMy[i] = rowData[i];
			});
		}
		
		hidShow("sava");
		
		return  "";
    }
    
    function analyseColsIndexRow(keyObj,rowsData){
		if(rowsData == null || rowsData == ""){
			return true;
		}
		var str = "";
		$.each(keyObj,function(i,o){
			return $.each(rowsData,function(j,k){
				if(k[i] == o){
					//str = k.indexRow;
				    str =   target.datagrid('getRowIndex',k); 
					return false;
				}
			});
		});
		return str;
	}
    
    //提交时赋值
	function chargeCommit(){
		if(chrHidField != null){
			var data = target.datagrid('getData');
			if(data.total == "0"){
				parent.$.messager.alert($xcp.i18n("paramMar.cuowts"),$xcp.i18n("paramMar.liebmysjbntj"),'info');
				return false;
			}
			chrHidField.val($xcp.toJson(data.rows));
			
			if($.isFunction(paramDef.formatValidate)){
				var retval = paramDef.formatValidate(data.rows);
				if(retval!=null && retval!= undefined){
					chrHidField.val($xcp.toJson(retval));
				}
			}
			if(annotationField != null){
				annotationField.val($xcp.toJson(annotationJson));
			}

		}
		return true;
	};
	
	function isUpdateDbClickObj(cloStr,fromData){
		var blean = true , jon = {};
		if(dbClickObj != null){
			var objList  = fromData || formDataTarget.form('getXcpFormData',true);
			for(i in objList){
				if(typeof dbClickObj[i] == "undefined" || $.trim(objList[i]) != $.trim(dbClickObj[i])){
					blean =  false;
					jon[i] = dbClickObj[i];
				}
			}
		}
		if(!blean){
			annotationJson[cloStr] =  jon;
			annotationJson["anto_" + cloStr] =  dbClickObj;
		}
		return blean;
	}
	
	/**
	 * 参数批注右下角修改
	 */
	function alrAnnotations(objList){
		var  jon = {} , parAnObj = null , cloStr = objList.closparamId;
		if(cloStr == "-1" || cloStr == "") return;
		
		parAnObj = annotationJson["anto_" + cloStr];
		if(parAnObj != null){
			for(i in objList){
				if(typeof parAnObj[i] == "undefined" || $.trim(objList[i]) != $.trim(parAnObj[i])){
					jon[i] = parAnObj[i];
				}
			}
		}
		annotationJson[cloStr] =  jon;
	}
	
	function fengStr(str){
		 if(str == "") return "";
		 return str.substring(0,str.length-1);
	};
	
	//解析load数据里面的combobox
	function analyseColsData(obj,filed){
		var strVal = "";
		if(comboxObj[filed] != undefined){
			strVal = analyseCombox(comboxObj[filed],obj);
		}
		return strVal;
	}
	
	function analyseCombox(comId,val){
		$("#"+comId).combobox("setValue",val);
		var str  = $("#"+comId).combobox("getText");
		//if val != $("#"+comId).combobox("getValue") 就要先setValue
		return str;
	}
	
	//combobox 的  数据解析
	function formatterFunCom(val,row,index,filed){
		 return analyseColsData(val,filed);
	};
	
	//普通的显示和取值不同的formatter 
	function formatterFunorVal(value,row,index){
		if(value === "add"){
			return $xcp.i18n("paramMar.xinz");
		}else if(value === "update"){
			return $xcp.i18n("paramMar.xiug"); 
		}else if(value === "delete"){
			return $xcp.i18n("paramMar.shanc");
		}
		return "";
	};
	
	
	function validate(){
		
	}
	
	/**导出数据**/
	function paramDataExport(){
		var corp = { 
				params:paramDef.queryJs.params
	    };
		var excelColumns = paramDef.queryJs.excelColumns;
		var homeHtmlId = "#paramQueryListTab";
		var queryComGridId = paramQueryGridId; //列表id对象
		qrParams = queryComGridId.datagrid('options').queryParams;	
		qrParams["parTranOrgNo"] = function(){return $("#butxn_tranOrgNo").val();};
		qrParams["parTradeNo"] = function(){return $("#butxn_tradeNo").val();};
		if( window["paramOrgNo"] ){
			qrParams["paramOrgNo"] = paramOrgNo;
		}
		if( window["paramOrgNoMore"] ){
			qrParams["paramOrgNoMore"] = paramOrgNoMore;
		}
		var _xcp_sys_formInitData = $xcp.parseJson(window["xcp_sys_formInitData"]);
		if( _xcp_sys_formInitData.butxn_tradeNo ){
			qrParams["butxn_tradeNo"] = _xcp_sys_formInitData.butxn_tradeNo;
		}
		sortName = queryComGridId.datagrid('options').sortName;
		sortOrder = queryComGridId.datagrid('options').sortOrder;
		if(excelColumns == null || excelColumns == ""){
			columns = queryComGridId.datagrid('getColumnFields');
			var titles = "";
			for(var i = 0;i<columns.length - 1;i++){
				var coloptions = queryComGridId.datagrid('getColumnOption',columns[i]);
				titles += coloptions.title + ";";
			}
			if(titles!="") titles = titles.substring(0, titles.length-1);
			qrParams["dataNameList"] = titles;
		}
		else{
			qrParams["dataNameList"] = excelColumns;
		}
		if(corp.params && !$.isEmptyObject(corp.params)){
			$.each(corp.params,function(i,x){
				if($.isFunction(x)){
					qrParams[i] = x();
				}else  if($("#"+x,homeHtmlId).length > 0){
					qrParams[i] = $("#"+x,homeHtmlId).xcpVal();
				}else{
					//qrParams[i] = x;
				}
			});
		}
		var tradeName = paramDef.mvcName;
		$xcp.getQueryJs(tradeName).srchBoxExportData(qrParams);
	}
	
	//点击查询和导出时候的扩展查询条件可以动态注册或者 整个函数扩展
	function setParam(param){//lsm2
		if($.isFunction(paramDef.setQueryParam)){
			$.extend(param,paramDef.setQueryParam());
		}
		return param;
	}
	
	return {
		defaults : { //TODO defaults object
			queryJs      : {},     //弹出查询框的对象集合  设置哪些查询条件 url 等信息
			fromDataId   : "",
			paramList    : {},
			paramSubFormId : "",
			paramSDiv    : "",
			toolbarName  :  "",
			defBevent : null,     //新增给标签赋默认值
			defOperation : null,	  
			validate : null,
			delValidateFun : null,//删除验证函数
			windowWidth : "800",
			windowHegith : null,
			maxLength : "50",
			mvcName : null,
			maximized : null,
			paramPrefix : null,//页面标签name在表名前缀 需用户传过来
			classPackDocument : "input[id!='closparamId'][type!='radio'][type!='checkbox'],textarea,select",
			formatValidate : null,
			fileName : ""  //参数导出文件名称
		},
		//静态参数初始化
		init : function(){
			if(inited == true || target == null)return;
			
			init();
			
			inited = true;
		},
		setOption : function(options){
			$.extend(this.defaults, options || {});
			paramDef = $xcp.paramMar.defaults;
			target = paramDef.paramSDiv;
			formTarget = paramDef.paramSubFormId;
			formDataTarget = paramDef.fromDataId;
		},
		//提交前赋值
		commit : function(){
			if(inited == false)return true;
			return chargeCommit();
		},
		//新增一行记录
		insertparamTab : function(carteData){
			target.datagrid('insertRow',{row : dataParamElim(carteData)});
		},
		//新增按钮
		insterFromData : function(){
			insterFromData();
		},
		//刷新按钮
		refreshFromData : function(){
			hidShow();
			emptyFromData();
		},
		savaFromData : function(){
			savaFromData();
		},
		savaparamFromData : function(){
			return savaparamFromData();
		},
		validatePriKeyForData : function(params){
			return validatePriKeyForData(params);
		},
		getParamId : function() {
			return getParamId();
		},
		//刪除按钮
		delFromData : function(rowDatas){
			delparamFromData(rowDatas);
		},
		dListdelForm : function(indexrow){
			dListdelForm(indexrow);
		},
		dListEdintForm : function (rowDatas,stats){
			dListEdintForm(rowDatas,stats); 
		},
		dbClcikQueryFun : function(rowDatas){
			dbClcikQueryFun(rowDatas);
		},
		fuDsum :function(){
			fuDsum();
		},
		getInitState : function(){
			return inited;
		},
		validate : function(){
			validate();
		},
		setMentStats : function(stuts){
			mentStats = stuts;dbClickObj = {};
			if(stuts == "update")createClosparamId();
		},
		isUpdateField :function(){  // 给前段调用者使用判断修改时页面数据是否有变化 返回true 无变化 false 有变化
			return isUpdateDbClickObj();
		},
		setParam : function(param){
			return setParam(param);
		}
		
	};//TODO
}(jQuery);
/**组件注册*/
if($xcp.compMgr){
	$xcp.compMgr.addComponent("xcpparamMgrDiv",$xcp.paramMar);
}
/**标注为参数交易*/
if($xcp.formPubMgr)$xcp.formPubMgr.defaults.tradeType = "CS";

/**创建参数表数据查询列表**/
lpbc.paramQueryPul = function(queryAcJs){
	var homeHtmlId = $("#paramQueryListTab") ;
	var defOpe = $xcp.paramMar.defaults.defOperation;
	
	//公用添加机构号和交易编号 后台可以不用这个参数
	queryAcJs.params = queryAcJs.params || {};
	queryAcJs.params["parTranOrgNo"] = function(){return $("#butxn_tranOrgNo").val();};
	queryAcJs.params["parTradeNo"] = function(){return $("#butxn_tradeNo").val();};
	queryAcJs.params["butxn_tranOrgNo"] = function(){return $("#butxn_tranOrgNo").val();};
	queryAcJs.params["butxn_tradeNo"] = function(){return $("#butxn_tradeNo").val();};
	
    var o = {
		cols : getRow(queryAcJs.cols),
		url : $xcp.def.getFullUrl(queryAcJs.url == undefined ? "" : queryAcJs.url),  
		width:800,
		height:420,
		params:queryAcJs.params,
	    condition : creQueryBut(queryAcJs.condition),//查询列表的查询条件处理
	    dblClick : queryAcJs.dblClick
   };
   
   var	html ="<div style='background: #FFFFFF;width:100%;height:100%' class='easyui-layout' id='queryByParamSrch' border='false' fit='true'>";
		html +="<div id='npanel' region='north' border='false'>";
		html +=          o.condition;
		html +="</div>";
		html +="<div id='cpanel' region='center' border='false' style='background-color: #FAFAF9;'>";
		html +=			 '<div id="paramQueryGridId" ></div>';
		html +="</div>";
		html +="</div>";
   $(html).appendTo(homeHtmlId);	
   $.parser.parse($('#queryByParamSrch'));
   
   var _xcp_sys_formInitData = $xcp.parseJson(window["xcp_sys_formInitData"]);
   o.url += o.url && window["paramOrgNo"] ? "&paramOrgNo=" + paramOrgNo : "";
   o.url += o.url && window["paramOrgNoMore"] ? "&paramOrgNoMore=" + paramOrgNoMore : "";
   o.url += o.url && _xcp_sys_formInitData.butxn_tradeNo ? "&butxn_tradeNo=" + _xcp_sys_formInitData.butxn_tradeNo : "";
   
   var queryComGridId = $('#paramQueryGridId'); //列表id对象
   
   	//查询按钮 查询条件为 o.params
	lpbc.comQueryPul.query = function(){
		qrParams = queryComGridId.datagrid('options').queryParams;			
		qrParams = constQueryParam(o.params,qrParams);
		queryComGridId.datagrid('load');
	};
	
   $('.but_query').unbind('click.comQueryPul').bind('click.comQueryPul',function(){
	   $xcp.comQueryPul.query();
   });
   
   function constQueryParam(paramF,paramT){
	   if(!paramF){  paramF = {};};
	   $.each(paramF,function(i,x){
		    if(x == "")return;
			if($.isFunction(x)){
				paramT[i] = x();
			}else if($("#" + x,homeHtmlId).length > 0){
				paramT[i] = $("#" + x,homeHtmlId).xcpVal();
			}else{
				//qrParams[i] = x;
			}
	   });
	   paramT = $xcp.paramMar.setParam(paramT);
	   return paramT;
   }
   
	var qrParams = {};	
	qrParams = constQueryParam(o.params,qrParams);
	
	//初始化表格数据
	queryComGridId.datagrid({//TODO queryList datagrid
		loadMsg : $xcp.i18n('sys.loadMsg'),
		singleSelect: true,
		url: o.url,
		columns:o.cols,
		rownumbers:true,
		pagination:true,
		//fit:true,
		fitColumns:false,
		pageSize: 10,//每页显示的记录条数，默认为10  
	    pageList: [5,10,15,20],//可以设置每页记录条数的列表  
		onDblClickRow : function(rowIndex, rowData){
			if(o.dblClick(rowData) != false){
			}
		},
		queryParams : qrParams,
		sortOrder : "desc",
		remoteSort : true, //是否远程排序
		onSortColumn : function(sort,order){
		}
	});
	 
	//生成显示的列
    function getRow(comlList){
    	if(!comlList) return comlList;
    	
		var columnsJs = [[]];
   	    $.each(comlList,function(i,o){
   		   var strJs = {
 	   			  title : o.text,field : o.name, 
	   			  width : o.width != null ? o.width :170,   
	   			  align : o.align != null ? o.align :'center',
	   			  hidden : o.hidden == true ? o.hidden :false ,
				  formatter : $.isFunction(o.formatter)? o.formatter :null,
				  sortable : true,
				  sorter : $.isFunction(o.sorter)? o.sorter : null  
	   	   };
   		   columnsJs[0].push(strJs);
   	    });
	   	columnsJs[0].push({
	   		  title : $xcp.i18n("paramMar.caoz"), 
	   		  field : "enditData", 
 			  width : 100,   
 			  align : 'center',
 			  formatter:enditDataField
	   	});
		return columnsJs;
	};
	 
    //生成查询按钮
	function creQueryBut(conditionQ){
    	var html = "" ; 
    	
    	html +="<div class='paramQueryAll'>";
    	
    	if((!defOpe ||  defOpe.add != false) && $xcp.task.isUpdateData()){
    		html += '<div class="paramQueryAdd" title="'+$xcp.i18n("packets.add")+'" id = "linkButLskAdd"></div>' ;
    	}
    	if( defOpe.expt != false){//导入按钮暂时去掉 ls  临时加上了"expt":false 20141210 tx
    		html += '<div class="paramQueryExport" title="'+$xcp.i18n("packets.export")+'" id="linkButLskExport"></div>';
    	}

    	html +="</div>";
    	
    	html += '<div id="condition" class="commQueryCondWrapParam">';
    	html += '<div class="query">';
    	if(conditionQ != null ){
    		
    		conditionQ = parseQueryType(conditionQ);

    		if(typeof conditionQ == "object" ){
    			$.each(conditionQ,function(i,k){
    				var disText = k.text;
    				if(k.lagQuote && k.lagQuote != ""){
    					disText = disText + k.lagQuote;
    				}else if(k.lesQuote && k.lesQuote != ""){
    					disText = disText + k.lesQuote;
    				}
    				html += '<div class="queryblock"><div class="text">'+disText +'</div>';
    				
    				html += '<div class="input"><input type="text" id ="'+k.id+'"' ;
    				
    				k.value = $.isFunction(k.value)?k.value():k.value;
    				
    				if(k.value != "undefined" && k.value != undefined){
    					html += ' value="'+k.value +'"';
    				}
    				if(k.closCombox != "undefined" && k.closCombox != undefined){
    					html += ' class="easyui-'+k.closCombox.inpClass +'"';
    					html += ' data-options="'+k.closCombox.dOption +'"';
    				}
    				html +='> </input></div>';
    				html +='</div>';
    			});
    		}
    	}
    	html +="	<div class='querybutton'>";
    	html +="		<div class='text'></div>";
    	html +="		<div class='input' id='queryDiv'>";
    	html +="			<button class='but_query paramQueryBtn' type='button'>"+ $xcp.i18n('sys.btn.query') + " </button>";
    	html +="		</div>";
    	html +="	</div>";
    	html +="</div>";
    	html +="</div>";
    	return html;
    }
	
	function parseQueryType(list){
		if(list == null)return [];
		var strArray = [];
		
		for (var i = 0; i < list.length; i++){
			var querObj = $xcp.parseJson(list[i]);
			if(querObj.editHidden == "true")continue;
			if(querObj.isQuery == "INTERVAL"){
				strArray.push($.extend({},querObj,{
					"fieldAlias":querObj.field + "start",
					"id" : querObj.fieldAlias + "start",
					"lesQuote":'>='
				}));
				strArray.push($.extend({},querObj,{
					"fieldAlias":querObj.field+"end" ,
					"id" : querObj.fieldAlias + "end",
					"lagQuote":'<='
				}));
			}else{
				strArray.push($.extend({},querObj,{"fieldAlias":querObj.field}));
			}
		}
		return  strArray;
	}
   
	function enditDataField(value,rowData,rowIndex){
		var mvcName = $xcp.paramMar.defaults.mvcName;
		var isAllow = false;
		
		if( rowData.allow && 'N' == rowData.allow && rowData[mvcName+'_orgNo'] && rowData[mvcName+'_orgNo'] != $('#butxn_tranOrgNo').val() ){
			isAllow = true;
		}
		var html  = "<div class='paramDmlOperaBtnWrap'>";
		if(!defOpe || defOpe["update"] != false){
			if( !isAllow ){
				html += "<span title='"+$xcp.i18n("paramMar.xiug")+"' class='mod' onClick=\"$xcp.paramMar.dbClcikQueryFun('"+rowIndex+"');\"></span>";
			}
	    }
		
	    if(!defOpe || defOpe["delete"] != false){
	    	if( !isAllow ){
	    		html += "<span title='"+$xcp.i18n("paramMar.shanc")+"' class='del' onClick=\"$xcp.paramMar.delFromData('"+rowIndex+"');\"></span>";
	    	}
	    }
	    html += "</div>";
		return html;
	}
};

/**
 * 查询静态参数存放的srch文件数据转换成前端静态参数使用数据 
 * lxOrFun srch文件名称
 * @returns
 */
lpbc.getQueryJs = function(lxOrFun){
	var queryJs = {};
	queryJs.cols = [];//参数页面初始展示的列表
	queryJs.url = "";//参数页面初始展示的列表中请求数据的URL
	queryJs.condition = [];//参数页面查询条件列表
	queryJs.params = {};//参数页面查询参数对象
	queryJs.paramList = [];	
	queryJs.querySql = "";
	
	//生成显示的列
	function getCols(comlList){
		var colsJs = [];
	    $.each(comlList,function(i,o){
	       if (o.hidden != 'true'){
	    	   var strJs = {
	 		   		  name : o.fieldAlias, 
	 	   			  width : o.width != null ? parseInt(o.width) :176,   
	 	   			  align : o.align != null ? o.align :'center',
	 	   			  formatter : o.formatter != null ? eval(o.formatter) : ''
	 	   	   };
	    	   if(o.customKey && o.customKey != "" ){
	    		   strJs["text"] = $xcp.i18n(o.customKey);
	    	   }else{
	    		   strJs["text"] = $xcp.i18n(o.tabName + "." + o.field);
	    	   }
	    	   if(o.i18nKey != ""){
	    		   strJs["name"] = "k_"+strJs.name;
	    	   }
	 		   colsJs.push(strJs);
	       }
	    });
	    return colsJs;
	};
	
	//生成查询条件
	function getCondition(comlList){
		var conditionJs = [];
	    $.each(comlList,function(i,o){
	       var closCombox = {};
	       if (o.isQuery != "N"){
	    	   if (o.queryType == 'combobox'){
	    		   closCombox.inpClass = "combobox";
	    		   var str = "";
	    		   if(o.queryCondition == "transOrg"){
	    			   str = ",{orgType:\'query\',orgNo:function(){ return $(\'#butxn_tranOrgNo\').val()}}";
	    		   }
	    		   
	    		   closCombox.dOption = "textField:'name',valueField:'val',data:$xcp.getConstant('" + o.queryCondition+"'" + str + ")";
	    	   } else if (o.queryType == "datebox"){
	    		   closCombox.inpClass = "datebox";
	    		   closCombox.dOption = "validType:'isDate'";
	    	   }
	    	   var strJs = {
	 		   		  //text : $xcp.i18n(o.tabName + "." + o.field),
	 		   		  id : o.fieldAlias + "Query", 
	 		   		  closCombox : closCombox != null ? closCombox : '',
	 		   		  isQuery : o.isQuery,
	 		   		  field : o.field,
	 		   		  fieldAlias : o.fieldAlias
	 	   		   };
	    	   if(o.customKey && o.customKey != "" ){
	    		   strJs["text"] = $xcp.i18n(o.customKey);
	    	   }else{
	    		   strJs["text"] = $xcp.i18n(o.tabName + "." + o.field);
	    	   }
	 		   conditionJs.push(strJs);
	       }
	    });
		return conditionJs;
	};
	
	//生成查询参数对象
	function getParams(comlList){
		var paramsJs = {};
		    $.each(comlList,function(i,o){
		       if (o.isQuery != "N"){
		    	   paramsJs[o.field] =  o.fieldAlias + "Query";
		       }
		       if(o.isQuery == "INTERVAL"){
		    	   paramsJs[o.field + "start"] =  o.fieldAlias + "start";
		    	   paramsJs[o.field + "end"] =  o.fieldAlias + "end";
		       }
		    });
		return paramsJs;
	};	
	
	//生成查询参数列表
	function getParamList(comlList){
		var paramListJs = [];
		    $.each(comlList,function(i,o){
		    	//if (o.editHidden != 'true'){
		    	   var closCombox = {};
				   if (o.isComboboxView == "Y"){
					   closCombox.inputType = "combobox";
					   closCombox.id = o.fieldAlias;
					   closCombox.valueField = "val";
					   closCombox.textField = "name";
				   }
				   var strJs = {
					  text : $xcp.i18n(o.tabName + "." + o.field),
					  name : o.fieldAlias, 
					  closCombox : closCombox != null ? closCombox : '',
					  keyClos : o.isKey == "Y" ? true : false,
					  hidden : (o.editHidden == true || o.editHidden == "true") ? true : "false",
					  field : o.field,
					  key : o.key == "Y" ? true : false
				   };
				   paramListJs.push(strJs);
		    	//}
		    });
		return paramListJs;
	};
	
	queryJs.srchBoxExportData = function(qrParams){
		if(null == qrParams){
			return false;
		}
		//var exportDataUrl = "./srchBox.do?method=exportData";
		$("<div style='display:hidden'><form id='_submitFormHid' action='"+$xcp.def.getFullUrl("./srchBox.do?method=exportData&fileName=" + $xcp.paramMar.defaults.mvcName)+"' target='hiddenIframe' method='post'></form><iframe name='hiddenIframe'  id='hiddenIframe'></iframe></div>").appendTo($("body"));
		if(null != qrParams && typeof qrParams != "undefined")
		{
			$.each(qrParams,function(i,k)
			{
				//exportDataUrl+="&"+i+"="+encodeURIComponent(k);
				$("<input id='" + i + "' name='" + i + "'></input>").appendTo($("#_submitFormHid"));
				$("#_submitFormHid #"+i).val(k);
			});
		}
		//exportDataUrl+="&sort="+sortName;
		$("<input id='" + "sort" + "' name='" + "sort" + "'></input>").appendTo($("#_submitFormHid"));
		$("#_submitFormHid #"+"sort").val(sortName);
		
		//exportDataUrl+="&order="+sortOrder;
		var i = "order";
		var k = sortOrder;
		$("<input id='" + i + "' name='" + i + "'></input>").appendTo($("#_submitFormHid"));
		$("#_submitFormHid #"+i).val(k);
		
		var colNames = '[';
		var colKeys = [];
		var _spt = '';
		$.each(queryJs.cols,function(i,o){
			colNames += (_spt + (o.text) );
			colKeys.push((o.name));
			_spt = ',';
		});
		colNames += ']';
		
		var i = "querySql";
		var k = queryJs.querySql;
		$("<input id='" + i + "' name='" + i + "'></input>").appendTo($("#_submitFormHid"));
		$("#_submitFormHid #"+i).val(k);
		
		var i = "condition";
		var k = $xcp.toJson((queryJs.condition));
		$("<input id='" + i + "' name='" + i + "'></input>").appendTo($("#_submitFormHid"));
		$("#_submitFormHid #"+i).val(k);
		
		var i = "colKeys";
		var k = $xcp.toJson((colKeys));
		$("<input id='" + i + "' name='" + i + "'></input>").appendTo($("#_submitFormHid"));
		$("#_submitFormHid #"+i).val(k);
		
		var i = "colNames";
		var k = colNames;
		$("<input id='" + i + "' name='" + i + "'></input>").appendTo($("#_submitFormHid"));
		$("#_submitFormHid #"+i).val(k);
		
		$("#_submitFormHid").submit().remove();
	};
	
	//读取srchBox配置
	$.ajax({
		type : "POST",
		url  : $xcp.def.getFullUrl('./srchBox.do?method=getSrchBoxCfg'),
		data : {srchName:lxOrFun},
		dataType : "json",
		async : false,
		success  : function(result) {
			if(result.success =='1'){
				$xcp.dispAjaxError($xcp.cloneObject(result));
				result = [];
			}else{
				var collist = $.parseJSON($xcp.toJson(result.outEntity.columns));
				queryJs.cols = getCols(collist);
				queryJs.condition = getCondition(collist);
				queryJs.params = getParams(collist);
				queryJs.paramList = getParamList(collist);
				var pageInnerSql = "";//分页查询内部sql，还需要增加外层的分页，外层的分页是由后台的SrchBoxDataAction.java完成的
				var outputFields = "";//datagrid输出列表，与显示列一一对应
				pageInnerSql = $xcp.toJson(result.outEntity.pageInnerSql).replace(/\"/g, "");
				outputFields = $xcp.toJson(result.outEntity.outputFields).replace(/\"/g, "");
				queryJs.querySql = pageInnerSql;
				queryJs.url = "./srchBox.do?method=getSrchBoxData&pageInnerSql=" + pageInnerSql + "&outputFields=" + outputFields;
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			alert(textStatus);
		}
	});
	
	return queryJs;
};//END srchBox组件

$.include(['myDocument/js/tools/srchTempl.js']);