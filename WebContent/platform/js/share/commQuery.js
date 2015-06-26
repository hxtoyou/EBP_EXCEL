/**
 * 银行行选择管理（款项来源管理）
 * sourceId            款项来源类型  如账户行、开户行、清算行
 * bankNoId            银行编号
 * curId               业务币种
 * codeId              银行SWIFTCODE 对应SWIFTCODE的表单元素的ID
 * nameAddreId         银行地址      对应银行名称地址的表单元素ID
 * deBankNoId          大额银行编号  只适用于 款项来源是大额的情况
 * deNameAddr          大额名称地址
 */
lpbc.bankWithAcctMgr = function(sourceId,curId,bankNoId,codeId,nameAddreId,outOrInnerAcctNoId,deBankNoId,deNameAddr){
	this.sourceId  = sourceId || '';
	this.curId   =  curId  || '';
	this.bankNoId = bankNoId || '';
	this.codeId   = codeId || '';
	this.nameAddreId = nameAddreId || '';
	this.outOrInnerAcctNoId = outOrInnerAcctNoId || '';
	this.deBankNoId  = deBankNoId || '';
	this.deNameAddr = deNameAddr  || '';
	
	this.acctInfos = [];
	this.bindEvent();
};

$.extend(lpbc.bankWithAcctMgr.prototype,{
	
	initJspElement : function(){
		if($('#'+this.sourceId).xcpVal() != 'bigOrSmallPay' && $('#'+this.deBankNoId) != null){
			if(('input[name="'+this.deBankNoId+'"]'))
				$('input[name="'+this.deBankNoId+'"]').parent().parent().parent().hide(); 
		}
		if($('#'+this.sourceId).xcpVal() == 'bigOrSmallPay'){
			$('input[name="'+this.deBankNoId+'"]').parent().parent().parent().show(); 
		}
	},
	
	bindEvent : function(){
		if (this.curId) {
			this.onCurChange(this.curId);
		}
		if (this.sourceId) {
			this.onSourceChange();
		}
		if (this.codeId) {
			this.swiftCodeChange();
		}
	},

	getAcctsByBankInfo : function (bankNo){
		var aopt = {
			url	 :	$xcp.def.getFullUrl("acctQuery.do?method=getCorpAcctInfo"),
			data :  {custBankNo:bankNo}
		};
		var rs = $xcp.ajax(aopt);
		this.acctInfos = rs.corpAccts;
	},

	getCurrentAccoutByCur : function (curSign) {
		var currentAcct = "";
		if(this.acctInfos.length > 0 ){
			$.each(this.acctInfos ,function(i,o){
				if(o.curSign == curSign){
					currentAcct = o;
					return false;
				}
			});
		}
		return currentAcct;
	},

	onCurChange : function(curId) {
		var _this = this;
		$('#'+curId).unbind('change.bankWithAcctMgr').bind('change.bankWithAcctMgr',function(){
			if($('#'+_this.outOrInnerAcctNoId)){
				$('#'+_this.outOrInnerAcctNoId).xcpVal('');
			}
			_this.setAcctByCurSign(curId);
		});
		
	},
	
	onSourceChange : function(){
		if($('#'+this.sourceId)){
			var _this = this;
			$('#'+this.sourceId).unbind('change.bankWithAcctMgr').bind('change.bankWithAcctMgr',function(){
				$('#'+_this.outOrInnerAcctNoId).xcpVal('');
				$('#'+_this.curId).xcpVal('');
				$('#'+_this.bankNoId).xcpVal('');
				$('#'+_this.codeId).xcpVal('');
				$('#'+_this.nameAddreId).xcpVal('');
				$('#'+_this.deBankNoId).xcpVal('');
				_this.acctInfos = [];
				_this.initJspElement();
			});
		}
	},
	
	swiftCodeChange : function(){
		var _this = this;
		if($('#'+this.codeId)){
			$('#'+this.codeId).unbind('change.bankWithAcctMgr').bind('change.bankWithAcctMgr',function(){
				$('#'+_this.outOrInnerAcctNoId).xcpVal('');
				$('#'+_this.nameAddreId).xcpVal('');
			});
		}
		
	},
	
	setAcctByCurSign : function(curSign){
		var _this = this;
		if(curSign && this.acctInfos.length > 0){
			$.each(this.acctInfos ,function(i,o){
				if(o.curSign == $('#'+curSign).xcpVal()){
					$('#'+_this.outOrInnerAcctNoId).xcpVal(o.acctNo);
					return false;
				}
			});
		}else{
			$('#'+this.outOrInnerAcctNoId).xcpVal('');
		}
	},
	
	setBankInfos : function(bankData){
		if(bankData){
			$('#'+this.bankNoId).xcpVal(bankData.bankNo);
			$('#'+this.codeId).xcpVal(bankData.swiftCode);
			$('#'+this.nameAddreId).xcpVal(bankData.address);
			if($('#'+this.deBankNoId)){
				$('#'+this.deBankNoId).xcpVal(bankData.deBankNo) || '';
			}
			//获取帐号
			this.getAcctsByBankInfo(bankData.bankNo);
			if(this.acctInfos){
				//设置帐号
				this.setAcctByCurSign(this.curId);
			}
		}
	}
});


/**
 * 节假日管理
 */
lpbc.holidayMgr = function($){
	//绑定节假日事件
	function holidayBindEvent(rqEle,curEleOrFun,gjEleOrFun){
		$(rqEle).unbind("change.holidayMgr").bind("change.holidayMgr",function(){
			var n = $(rqEle).xcpVal();
			if(n){
				var cur = $.isFunction(curEleOrFun) ? curEleOrFun() : $(curEleOrFun).xcpVal();
				var gj  = $.isFunction(gjEleOrFun)  ? gjEleOrFun()  : $(gjEleOrFun).xcpVal();
				if($xcp.def.holidayType == '0' )
					gj = '';
				else
					cur = '';
				isHoliday(n,cur,gj);
			}
		});
		
		var paramObj="";
		if($xcp.def.holidayType == '0'){
			paramObj = curEleOrFun;
		}else{
			paramObj = gjEleOrFun ;
		}
		
		if (!$.isFunction(paramObj)) {
			$(paramObj).unbind("change.holidayMgr").bind("change.holidayMgr",function(){
				var cur = $.isFunction(curEleOrFun) ? curEleOrFun(): $(curEleOrFun).xcpVal();
				var gj = $.isFunction(gjEleOrFun) ? gjEleOrFun(): $(gjEleOrFun).xcpVal();
				var rq = $(rqEle).xcpVal();
				if($xcp.def.holidayType == '0' )
					gj = '';
				else
					cur = '';
				
				isHoliday(rq, cur, gj);
			});
		}
	}
	
	//是否节假日
	function isHoliday(currentDate,cur,countryNo,orgNo,tradeNo){
		if( currentDate == '' || (cur == ''&& countryNo =='')) return;
		var aopt = {
			url  : $xcp.def.getFullUrl('./holiday.do?method=getWorkDate'),
			data : {
				type : "1", // 标识是否节假日 1是标识节假日判断，2 标识获取下一个工作日的后台参数
				holidayType : $xcp.def.holidayType,
				executeYear : $xcp.date.toDate(currentDate).getFullYear(),
				curSign : cur,
				countryNo : countryNo,
				dateParam : currentDate
			}
		};
		var result = $xcp.ajax(aopt);
		if(result && null!=result && data.isWorkHld == true){
			$.messager.alert($xcp.i18n('sys.warning'),$xcp.i18n('sys.holiday'));
		}
	};
	
	/**
	 * 根据当前日期和期限天数得到下一个工作日
	 * 
	 * currentDate 当前日期
	 * cur         币种
	 * countryNo   国家编号
	 * term        期限天数
	 * isContainCurrDay 是否包含当天
	 */
	function getNextWorday(currentDate,cur,countryNo,isContainCurrDay,term){
		var retDate = null;
		var aopt = {
			url	 :	$xcp.def.getFullUrl('./holiday.do?method=getWorkDate'),
			data :  {
	        		type        : "2", //标识是否节假日 1是标识节假日判断，2 标识获取下一个工作日的后台参数
	        		holidayType : $xcp.def.holidayType,
	        		executeYear : $xcp.date.toDate(currentDate).getFullYear(),
	        		curSign     : cur,
	        		countryNo   : countryNo,
	        		numDay      : term,
	        		isContainCurrDay : isContainCurrDay,
	        		dateParam   : currentDate
     		}
			
		};
		var data = $xcp.ajax(aopt);
		if(data && data != null){
			retDate = data.workDate;
		}
		return retDate;
	}
	
	//获取N个工作日后的日期
	function addDayNoHoliday(opt){
		var retDate = null;
		var aopt = {
			url	 :	$xcp.def.getFullUrl('./holiday.do?method=getWorkDate'),
			data :  {
	        		type        : "3", //标识是否节假日 1是标识节假日判断，2 标识获取下一个工作日的后台参数
	        		holidayType : $xcp.def.holidayType,
	        		curSign     : opt.cur,
	        		countryNo   : opt.countryNo,
	        		numDay      : opt.term,
	        		dateParam   : opt.currentDate
     		}
			
		};
		var data = $xcp.ajax(aopt);
		if(data && data != null){
			retDate = data.workDate;
		}
		return retDate;
	}
	
	//获取N个自然日 如果返回日期为节假日 则查找返回日期的下一个工作日
	function addDay(opt){
		var retData = $xcp.date.addDays(opt.currentDate,opt.term);
		retData = getNextWorday(retData,opt.cur,opt.countryNo,true,0);
		return retData;
	}
	
	return{
		holidayBindEvent : function(rqEle,curEleOrFun,gjEleOrFun){
			holidayBindEvent(rqEle,curEleOrFun,gjEleOrFun);
		},
		
		isHoliday : function(rq,cur,gj){
			return isHoliday(rq,cur,gj);
		},
		
		getNextWorday : function(currentDate,cur,countryNo,isContainCurrDay,term){
			return getNextWorday(currentDate,cur,countryNo,isContainCurrDay,term);
		},
		//获取N个工作日后的日期
		addDayNoHoliday : function(opt){
			return addDayNoHoliday(opt);
		},
		//获取N个自然日 如果返回日期为节假日 则查找返回日期的下一个工作日
		addDay : function(opt){
			return addDay(opt);
		}
		
	};
	
}(jQuery);


/**
 * 黑名单检查提醒
 * @param vlue  当前文本域的值
 * @param asyncFlag 同步或者异步
 * @isCx 公共查询组件调用，asyncFlag=true
 */
lpbc.hmdjc = function(value,asyncFlag,isCx){
	if(value == null || value == undefined || value == ""){
		 $(".hmdjc").each(function(i){
			  $(this).unbind('.hmdjc').bind('change.hmdjc',function(){
					var value =  $(this).val();
					if(value != ""){
						ajaxHmdjc( value,true,isCx);
					}
			  });
		 });
	}else{
	 	return	ajaxHmdjc(value,true,isCx);
	}
	
	function ajaxHmdjc(value,asyncFlag,isCx){
		if(value =="222"){
			$.messager.alert($xcp.i18n('sys.warning'),$xcp.i18n('sys.hmdjc.tip'));
		}
		return false;
		
		var aopt = {
			url     :  $xcp.def.getFullUrl("./commonQuery.do?method=checkBlackList"),
			data    :  {orgId:value},
			success :  function(data) {
                var resData = 1;//data.outEntity.recordeNum;
                if(resData > 0 ){
                	if(isCx === true){
                		 return false;
                	}
                	$.messager.alert($xcp.i18n('sys.warning'),$xcp.i18n('sys.hmdjc.tip'));
	            }
            }
		};
		$xcp.ajax(aopt);
	}	
};


lpbc.comQueryPul = function(queryAcJs){
	var queryCWinObj = '#xcpComCxWinId';//整个弹出层的Id对象
	var queryComGridId = '#xcpComCxGridId'; //列表id对象
	
	if($(queryCWinObj)){
		$(queryCWinObj).window('destroy');
	}
	
	//查询按钮 查询条件为 o.params
	lpbc.comQueryPul.query = function(){
		 qrParams = $(queryComGridId).datagrid('options').queryParams;			
		if(o.params && !$.isEmptyObject(o.params)){
			$.each(o.params,function(i,x){
				if($("#"+x,queryCWinObj).length > 0){
					qrParams[i] = $("#"+x,queryCWinObj).val();
				}else{
					qrParams[i] = x;
				}
			});
		}
		$(queryComGridId).datagrid('load');
	};
    var corp = { 
			cols :getRow(queryAcJs.cols),
			url : $xcp.def.getFullUrl(queryAcJs.url == undefined ? "" : queryAcJs.url),  
			width:800,
			height:420,
			hmdjc:'corpNo',
			params:queryAcJs.params,
		    condition : creQueryBut(queryAcJs.condition),
		    dblClick : queryAcJs.dblClickTrue
   };
    
   var o = corp;	   
   var html = '<div id="xcpComCxWinId" title="'+queryAcJs.titleName+'" style="padding:2px;" >';
   	   html +=    '<div id="xcpComCxLayout" class="easyui-layout" data-options="fit:true">';
   	   html +=       '<div id="conditionDiv"  region="north" style="50px;" data-options="border:false">';
   	   html +=          o.condition;
   	   html +=       '</div>';
   	   html +=       '<div id="tt" region="center" data-options="border:false">';
   	   html +=			 '<div id="xcpComCxGridId"></div>';
   	   html +=       '</div>';
   	   html +=    '</div>';
   	   html += '</div>';

   $(html).appendTo('body');	
   
   
   
   if($('.but_query',queryCWinObj)){
	   $('.but_query',queryCWinObj).unbind('click.comQueryPul').bind('click.comQueryPul',function(){
		   $xcp.comQueryPul.query();
	   });
   }

	//初始化窗体
	$(queryCWinObj).window({
			modal:true,
			closed:false,
			/*iconCls:'icon-save',*/
			inline : true,
			collapsible : false,
			width:o.width,
			height:o.height,
			minimizable : false,
			onClose     : function(){
				$(this).window('destroy');
			}
	});
	
	var qrParams ={};			
	if(o.params && !$.isEmptyObject(o.params)){
		$.each(o.params,function(i,x){
			if($("#"+x,queryCWinObj).length > 0){
				qrParams[i] = $("#"+x,queryCWinObj).val();
			}else{
				qrParams[i] = x;
			}
		});
	}
	$('#xcpComCxLayout').layout();
	//初始化表格数据
	$(queryComGridId).datagrid({
		loadMsg : $xcp.i18n('sys.loadMsg'),
		singleSelect: true,
		url: o.url,
		columns:o.cols,
		fit: true,			
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		border:false,
		striped:true,
		autoRowHeight:false,
		pageSize: 10,//每页显示的记录条数，默认为10  
	    pageList: [5,10,15,20],//可以设置每页记录条数的列表  
		//queryParams : getQueryParam(o.params),
		onDblClickRow : function(rowIndex, rowData){
			if(o.dblClick(rowData) != false){
				$(queryComGridId).datagrid('getPanel').panel('destroy');
				$(queryCWinObj).window('destroy');
			}
		},
		queryParams : qrParams
	});
    
	
    //生成查询按钮
    function creQueryBut(conditionQ){
    	var html = '<div style="padding:4px"><table>' ;
    	if(conditionQ != null ){
    		if(typeof conditionQ == "object" ){
    			$.each(conditionQ,function(i,k){
    				if(i%3 == 0 ){
    					if(i != 0)
    						html += "</tr>";
    					html += "<tr>";
    				}
    				html += '<td>'+k.text+ ':</td><td><input type="text"   id ="'+k.id+'"' ;
    				if(k.value != "undefined" && k.value != undefined){
    					html += ' value="'+k.value;
    				}
    				html +='"/> </td>';
    				if(i == conditionQ.length - 1  ){
    					if (i%3 == 2)
    						html += ' <tr><td align="right"><input class="but_query" type="button" value="'+ $xcp.i18n('sys.btn.query') + '"/></td></tr>';
    					else 
    						html += ' <td align="right"><input class="but_query" type="button" value="'+ $xcp.i18n('sys.btn.query') + '"/></td></tr>';
    				}
    			});
    		}
    	}else {
    		html += '<tr><td align="right"><input class="but_query" type="button" value="'+ $xcp.i18n('sys.btn.query') + '"/></td>'+
					'</tr>';
    	}
    	html += '</table></div>';
    	return html;
    }
    //生成显示的列
    function getRow(comlList){
		var columnsJs = [[]];
   	    $.each(comlList,function(i,o){
   		   var strJs = {
 	   			  title : o.text,field : o.name, 
	   			  width : o.width != null ? o.width :176,   
	   			  align : o.align != null ? o.align :'center',
	   			  hidden : o.hidden == true ? o.hidden :"" ,
				  formatter : $.isFunction(o.formatter)? o.formatter :null
	   		   };
   		   columnsJs[0].push(strJs);
   	    });
		return columnsJs;
	};
};


/**
 * srchBox组件
 * 
 * 公共查询 包括客户查询，银行(账户行、清算行、开户行等)查询，机构查询
 * @param lxOrFun     如账户行、开户行、清算行
 * @param callback    回调处理函数
 * @param params      查询条件
 * queryType  1为普通的srchBox查询 2是接口查询
 * @returns {Boolean}
 */
$.fn.xcpSrchBox = function(opts) {
	var _this = $(this);
	if(_this == undefined || _this == null || _this.length == 0 )return;
	
	var queryType = opts.lxOrFun == "QueryInterface" ? "2":"1";
	if(queryType == "2"){
	}else if(queryType == "1"){
		$(_this).unbind("click.srchBox").bind("click.srchBox",function(){
			if($(this).hasClass("P"))return;
			$xcp.srchBoxMgr(opts,false,_this);
		});
		
		//$(_this).searchbox("textbox").bind("keydown.srchBox", function(e) {
		$(_this).unbind("keydown.srchBox").bind("keydown.srchBox",function(e,e1){
			if($(this).hasClass("P"))return;
			if(e1) e = e1;
			if (e.keyCode == 13) {
				e.preventDefault();
				opts.value = $(this).xcpVal();
				$xcp.srchBoxMgr(opts,true,_this);
				return false;
			}
		});
	}
	
};


var comQueryFamter = {
	"yesorno" : function(value,row,index){
		if(value == ""){
			return $xcp.i18n("chrMgr.n");
		}
		return value;
	}
};
lpbc.srchBoxMgr = function(opts,isEnterKeyDown,_this){
	
	var lxOrFun  = opts.lxOrFun,
		callback = opts.callback,
		srchTabName = opts.srchTabName,
		inParams = {};
	var extendFun = opts.extendFun;
	
	inParams.params = opts.params;
	
	var srchBoxGridId = '#srchBoxGridId'; //列表id对象
	
	var srchBoxcols = [[]];//searchBox需要显示的列
	
	var outputFields = "";//datagrid输出列表，与显示列一一对应
	
	var pageInnerSql = "";//分页查询内部sql，还需要增加外层的分页，外层的分页是由后台的SrchBoxDataAction.java完成的
	
	var queryHtml = "";//datagrid上面查询条件的html串
	
	var queryJs = {};
	
	var lxOrName = $xcp.i18n(lxOrFun) != "["+lxOrFun+"]" ? $xcp.i18n(lxOrFun) : $xcp.i18n('comCx.title');
	//销毁已经存在的window
	if($('#srchBoxWinId') && $('#srchBoxWinId').length > 0){
		$('#srchBoxWinId').window().window('destroy');
	}
	
	//读取srchBox配置
	$.ajax({
		type : "POST",
		url  : $xcp.def.getFullUrl('./srchBox.do?method=getSrchBoxCfg'),
		data : {srchName:lxOrFun,srchTabName:srchTabName},
		dataType : "json",
		async : false,
		success  : function(result) {
			if(result.success =='1'){
				$xcp.dispAjaxError($xcp.cloneObject(result));
				result = [];
			}else{
				var collist = $.parseJSON($xcp.toJson(result.outEntity.columns));
				srchBoxcols = getRow(collist);
				var queryFields = $.parseJSON($xcp.toJson(result.outEntity.queryFields));
				queryHtml = getQueryHtml(queryFields);
				
				//点击查询按钮的条件绑定
				queryJs.params = getParams(collist);
				
				pageInnerSql = $xcp.toJson(result.outEntity.pageInnerSql).replace(/\"/g, "");
				outputFields = $xcp.toJson(result.outEntity.outputFields).replace(/\"/g, "");
				inputFields = $.parseJSON($xcp.toJson(result.outEntity.inputFields));
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			alert(textStatus);
		}
	});
	
	//生成显示的列
	function getRow(comlList){
		var columnsJs = [[]];
		    $.each(comlList,function(i,o){
			   var strJs = {
		   		  field : o.fieldAlias, 
	   			  width : o.width != null ? parseInt(o.width) :176,   
	   			  align : o.align != null ? o.align :'center',
	   			  hidden : o.hidden == "true" ? true :false ,
				  formatter : o.formatter != "" ? o.formatter : null,
	   			  sortable : true
	   		   };
			   if(o.i18nKey != ""){
	    		   strJs.field = "k_"+strJs.field;
	    	   }
			   if(o.customKey && o.customKey != "" ){
	    		   strJs["title"] = $xcp.i18n(o.customKey);
	    	   }else{
	    		   strJs["title"] = $xcp.i18n(o.tabName + "." + o.field);
	    	   }
			   if(strJs["formatter"] == "yesorno"){
				   strJs["formatter"] = comQueryFamter[strJs["formatter"]];
			   }
			   columnsJs[0].push(strJs);
		    });
		return columnsJs;
	};
    
	function parseQueryType(list){
		if(list == null)return [];
		var strArray = [];
		
		for (var i = 0; i < list.length; i++){
			var querObj = $xcp.parseJson(list[i]);
			if(querObj.editHidden == "true")continue;
			if(querObj.isQuery == "INTERVAL"){
				strArray.push($.extend({},querObj,{
					"fieldAlias":querObj.field + "start"
				}));
				strArray.push($.extend({},querObj,{"fieldAlias":querObj.field}));
				strArray.push($.extend({},querObj,{
					"fieldAlias":querObj.field+"end" 
				}));
			}else{
				strArray.push($.extend({},querObj,{"fieldAlias":querObj.field}));
			}
		}
		return  strArray;
	}
	//生成查询条件的html串
	function getQueryHtml(list){
		var rowSize = 3;
		result = [];
		result.push('<div style="padding:0px" >\n');
		result.push('\t<table>\n');
		
		list = parseQueryType(list);
		
		var row = parseInt(list.length / rowSize);
		if ((list.length % 3) != 0){
			row += 1;
		}
		var indexHtml = 0;
		for (var i = 0; i < row; i++){
			result.push('\t\t<tr>\n');
			for (var j = 0; j < rowSize; j++){
				if (indexHtml != list.length ) {
					var  str = '';
					var querObj = $xcp.parseJson(list[indexHtml]);
					if(querObj.editHidden == "true")continue;
					var fieldHtml = querObj.fieldAlias;
					var fiel = querObj.field;
					var customKey = querObj.customKey;
					
					if(querObj.queryType == "combobox"){
						str += 'class="easyui-combobox"';
						str += 'data-options="textField:\'name\',valueField:\'val\',data:$xcp.getConstant(\'' 
								+ querObj.queryCondition+'\')"';
					}else if(querObj.queryType == "datebox" || querObj.queryType == "chardatebox"){
						str += 'class="easyui-datebox"';
						str += ' data-options="validType:\'isDate\'"';
					}else if(querObj.queryType == "datetimebox"){
						str += 'class="easyui-datetimebox"';
						str += ' data-options="validType:\'isTimeDate\'"';
					}else if(querObj.queryType == "numberbox"){
						str += 'class="easyui-numberbox"';
						str += ' data-options="precision:\'2\'"';
					}
					var str1 = $xcp.i18n((customKey != undefined && customKey !="") ? customKey : querObj.tabName + "."+ fiel);
					
					str1 = fieldHtml.indexOf("start") == -1 ? str1 : str1 + " <";
					str1 = (fieldHtml.indexOf("end")   == -1 || fieldHtml.indexOf("send") > -1) ? str1 : str1 + " >";
					str1 = querObj.isQuery != "INTERVAL" ? (str1 +":") : str1 + "=";
					
					result.push('\t\t\t<td>'+ str1
							+ '</td><td><input type="text" name="' + fieldHtml  + 'Query'
							+ '" id ="' + fieldHtml  + 'Query' + '" '+str+'/> </td>\n');
				} else {
					break;
				}
				indexHtml++;
			}
			if(i == 0 && indexHtml !=0){
				result.push('\t\t\t<td align="right"><button id="cq_qryBtn"   class="paramQueryBtn" type="button">'+ $xcp.i18n('sys.btn.query') + '</button></td>\n');
			}
			result.push('\t\t</tr>\n');
		}
		
		result.push('\t</table>\n');
		result.push('</div>\n');
		return result.join('');
	}
	
	
	//生成查询参数对象
	function getParams(comlList){
		var paramsJs = {};
	    $.each(comlList,function(i,o){
	       if (o.isQuery != "N" ){//&& o.editHidden != "true"
	    	   var fiel = "";
	    	   if(o.tabAlias != o.tabName ){ //在配置工具中涉及到取别名的问题
	    		   fiel = o.fieldAlias.replace(o.tabAlias + "_","");
	    	   }else{
	    		   fiel = o.fieldAlias.replace(o.tabName + "_","");
	    	   }
	    	  
	    	   if(o.isQuery == "INTERVAL"){
	    		   paramsJs[o.field] =  fiel + "Query";
	    		   paramsJs[o.field+"start"] =  fiel + "startQuery";
	    		   paramsJs[o.field+"end"] =  fiel + "endQuery";
	    	   }else{
	    		   paramsJs[o.field] =  fiel+ "Query";
	    	   }
	       }
	    });
		return paramsJs;
	};
	
	//查询按钮
	//var destroyFlag = false;//如果标记为true，则在点击查询之后如果查询结果只有一条记录，则带出查询记录并关闭查询窗口
	lpbc.srchBoxMgr.query = function(){
		if(!$(srchBoxGridId) || $(srchBoxGridId).length < 0){
			return false;
		}
		var qrParams = $(srchBoxGridId).datagrid('options').queryParams;
		var btnQueryParams = {};
		if(queryJs.params && !$.isEmptyObject(queryJs.params)){
			$.each(queryJs.params,function(i,x){
				if($.isFunction(x) || $("#"+x).length > 0){
					var funStr = "";
					funStr = $.isFunction(x) ? x() : $("#"+x).xcpVal();
					$("#"+i+"Query").xcpVal(funStr);
					qrParams[i] = funStr;
				}else{
					$.each(inParams.params,function(j,k){
						if(j == i){
							qrParams[i] = k;
							return false;
						}
					});
				}
			});
		}
		btnQueryParams.params = qrParams;
		
		//$('#srchBoxGridId').datagrid("options").url = $xcp.def.getFullUrl('./srchBox.do?method=getSrchBoxData&pageInnerSql=' + pageInnerSql + "&outputFields=" + outputFields),
		$('#srchBoxGridId').datagrid("options").queryParams = getQueryParam(btnQueryParams);
		$('#srchBoxGridId').datagrid("load");
//		$('#srchBoxGridId').datagrid("loadData",getSearchData(getQueryParam(btnQueryParams)));
	};
	
   
   var html = '<div id="srchBoxWinId" title="'+lxOrName+'"  style="padding:2px;">';
   	   html +=    '<div id="xcpComCxLayout" class="easyui-layout" data-options="fit:true">';
   	   html +=       '<div id="conditionDiv" region="north"  data-options="border:false">';
   	   if(extendFun){
   		   html += extendFun();
   	   }
	   html +=       '<div    class="commQueryCondWrapParam">';
   	   html +=          queryHtml;
   	   html +=       '</div>';
   	   html +=       '</div>';
   	   html +=       '<div id="tt" region="center"  data-options="border:false">';
   	   html +=			 '<div id="srchBoxGridId"></div>';
   	   html +=       '</div>';
   	   html +=    '</div>';
   	   html += '</div>';
   $(html).appendTo('body');
   $.parser.parse("#conditionDiv");
   
   /**查询按钮事件*/
   if($('#cq_qryBtn')){
	   $('#cq_qryBtn').unbind('click.srchBoxMgr').bind('click.srchBoxMgr',function(){
		   $xcp.srchBoxMgr.query();
	   });
   }
  
  /**
   * 将业务页面输入的值传入到URL的查询条件
   * @param o
   * @returns {___anonymous31786_31787}
   */
   function getQueryParam(o){
		var queryParams = {};
		if(o.params && !$.isEmptyObject(o.params)){
			$.each(o.params,function(i,x){
				var flag = false;
				try{
					flag = $("#"+x).length > 0;
				}catch(e){}
				if($.isFunction(x) || flag){
					var funStr = "";
					funStr = $.isFunction(x) ? x() : $("#"+x).xcpVal();
					$("#"+i+"Query").xcpVal(funStr);
					queryParams[i] = funStr;
				}else{
					queryParams[i] = x;
				}
			});
		}
		queryParams = $.extend(queryParams,addParmColms(queryParams));
		return queryParams;
	};	
	
	function addParmColms(param){
		var jon = {};
		if(lxOrFun == "SelectBankSrchBox"){//PABANKSRCHBOX 银行查询判断密押 以后需要更换
			jon["orgNo"] = $xcp.formPubMgr.getTranOrgRules("agentOrgNo");
		}
		jon["butxn_tranOrgNo"] = $("#butxn_tranOrgNo").xcpVal();
		return jon;
	}
	//初始化表格数据
	$('#srchBoxGridId').datagrid({
		url : $xcp.def.getFullUrl('./srchBox.do?method=getSrchBoxData&pageInnerSql=' + pageInnerSql + "&outputFields=" + outputFields),
		loadMsg : $xcp.i18n('sys.loadMsg'),
		singleSelect: true,
		columns:srchBoxcols,
		fit: true,	
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		border:false,
		striped:true,
		autoRowHeight:false,
		onDblClickRow : ondblClickFun,
		sortOrder : "desc",
		remoteSort : true, //是否远程排序
		queryParams : getQueryParam(inParams),
		onLoadSuccess : function(data){
			if(isEnterKeyDown == true && data.rows.length == 1 && $.isFunction(callback)){
				setTimeout(function(){
					ondblClickFun(0,data.rows[0]);
				},200);
			}
		},//lsm 
		pageSize: 10,//每页显示的记录条数，默认为10  
	    pageList: [5,10,15,20]//可以设置每页记录条数的列表  
	});
	
	var srchDbClickList = [];
	function getDbClickList(){
		if($("#leftTransOrgNo").length > 0){
			 srchDbClickList.push("leftSrchFun");
		}else{
			if(window["srchboxDblclickFun"] && window["srchboxDblclickFun"]["srchboxDblclickExd"]){
				srchDbClickList.push(srchboxDblclickFun.srchboxDblclickExd(lxOrFun));
			}
		}
	}
	
	getDbClickList();
	 
	function leftSrchFun(){
		if($("#leftTransOrgNo").xcpVal().length == 0){
			$.messager.alert($xcp.i18n("paramMar.tisxx"), $xcp.i18n("leftTree.mustSelectOrg"));
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 *   双击行事件
	 */
	function ondblClickFun (rowIndex,rowData){
		var blean = true;
		$.each(srchDbClickList,function(i,o){
			if(o == ""){
				return ;
			}
			if($.isFunction(o) ){
				if(o(rowData) == false){
					blean = false;
				};
			}else if(eval("("+o+")(rowData)") == false){
				blean = false;
			}
			
			if(blean == false){
				return false;
			}
		});
		
		if(!blean) return;
		
		if($.isFunction(callback))
			callback.call(this,rowIndex,rowData);
		
		if($('#srchBoxWinId').length > 0){
			$('#srchBoxWinId').window().window('close');
		}
	}
	
	/**
	 * 获得查询的数据
	 * @returns
	 */
	function getSearchData(queryParams){
		if($('#srchBoxGridId')!= undefined){
			var options = $('#srchBoxGridId').datagrid("options");
			var ajaxOpt =  {
				   url   : $xcp.def.getFullUrl('./srchBox.do?method=getSrchBoxData&pageInnerSql=' + pageInnerSql + "&outputFields=" + outputFields),
				   data  : $.extend({},{page:options.pageNumber,rows:options.pageSize},queryParams),
				   aync  : false
			};
			return $xcp.ajax(ajaxOpt,"object");
		}
		return {};
	}
	//动态计算宽度
	function getTabTdWidth(tabid){
		var tdList = $(tabid).find("tr:eq(0) td");
		var nt = 0;
		$.each(tdList,function(i,o){
			nt += $(o).outerWidth();
		});
		nt = nt + 50;
		return nt > 800 ? nt  : 800;
	}
	//返回数据的总条数
	var dataTotal = $('#srchBoxGridId').datagrid("getData").total;
	
	//如果返回的数据只有一条记录且是通过回车的KEYDOWN事件触发
	if(isEnterKeyDown && dataTotal == 1){
		onlyOneDataHandle();
	}else{
		//初始化窗体
		$('#srchBoxWinId').window({
					modal:true,
					closed:false,
					inline : true,
					collapsible : false,
					width: getTabTdWidth($("#conditionDiv").find("table")),
					height:423,
					minimizable : false,
					onClose     : function(){
						$("#conditionDiv").remove();
						$(this).window('destroy');
					},
					onDestroy : function(){
					}
		});
			
		$('#xcpComCxLayout').layout();
	}
	
	/**
	 * 返回数据只有一条的处理
	 *
	 */
	function onlyOneDataHandle(){
		if($('#srchBoxGridId').datagrid("getRows").length > 0){
			ondblClickFun(0,$('#srchBoxGridId').datagrid("getRows")[0]);
		}
	}
	
};//END srchBox组件


/**
 * 快速查询
 */
lpbc.quickQueryBal = function($){
	
	/**
	 * 是否有余额信息 历史信息
	 */
	function hasBalInfo(latestFlag){
		if(latestFlag == "1"){
			
			var bizNo = $("#butxn_curtBizNo").xcpVal();
			var txnNo = $("#txnNo").xcpVal();
			if(bizNo){
		       var baInfo = $xcp.ajax({
					   url   : $xcp.def.getFullUrl("common.do?method=quickQueryBal"),
					   data : {"bizNo":bizNo}
				});
		       
		       if(baInfo && baInfo.length > 0 ){
			    	$.get($xcp.def.getFullUrl('./platform/page/system/balance.jsp?txnNo='+txnNo+"&balId=0"),function(data){
			   			$xcp.formPubMgr.formAddTabs(data,$xcp.i18n("historyInfo.BalInfo"),false,false,true);
			   		});	 
			    
		       }
		       
			   $.get($xcp.def.getFullUrl('./platform/page/system/curHistoryPage.jsp?info=Y&curtBizNo='+bizNo),function(data){
		   			$xcp.formPubMgr.formAddTabs(data,$xcp.i18n("formPubMgr.historyInfo"),false,false,true);
		   	   });	 
			}
		}
	}
	
	/**
	 * 查看业务交易的余额
	 */
	function viewBusBalInfo(){
		var stepNo = "";
    	if($xcp.task.isHandle){
    		stepNo = "1001";
    		//提交手续费数据
			if($xcp.chrMgr) $xcp.chrMgr.commit();
			//提交资金流向数据
			if($xcp.fundsMgr)$xcp.fundsMgr.commit();
    	}
     	var formData = $xcp.formPubMgr.getFormData($xcp.formPubMgr.defaults.currFormId);
    	formData.stepNo =  stepNo;
    	
    	
    	var ajaxOpt = {
    		url:$xcp.def.getFullUrl('preview.do?method=veiwBalinfo'),
    		data:formData
    	};
    	var data = $xcp.ajax(ajaxOpt,"object");
    	var containerHtml = "<div id=\"balInfoContainer\"><table class=\"easyui-datagrid\" id=\"balInfoTable\"><table><div>";
    	if(data == undefined ||  data == null ){
    		containerHtml = "";
    		$.messager.alert($xcp.i18n('tip'),$xcp.i18n("formPubMgr.noBalInfo"),'info');
    		return null;
    	}else if(data== undefined){
    		return null;
    	}
    	
		return {html:containerHtml,flag:false,result:data};
	}
	
	function loadBalInfo(data){
		//余额金额
		function famterAmt(value, rowData){
			if( value != undefined && rowData && rowData.cur ){
				return $xcp.def.formatterAmt(value, rowData.cur);
			}
			return value;
		}
		
		//业务编号
		function formatterBizNo(value,rowData){
			return value == "NONREF" ? "" : value;
		}
		
		$('#balInfoTable').datagrid({
			loadMsg : $xcp.i18n("sys.loadMsg"),
			singleSelect: true,
			url: '',
			height: "auto",
			fitColumns: true,
			pageSize: 10,            //每页显示的记录条数，默认为10  
		    pageList: [5,10,15,20],  //可以设置每页记录条数的列表  
		    columns:[[
						{title : $xcp.i18n("formPubMgr.BalName"), field : 'fieldName', width : 30, align : 'center'},
						{title : $xcp.i18n("pendingTask.tranCur"), field : 'cur', width : 4, align : 'center'},
						{title : $xcp.i18n("formPubMgr.BalTranAmt"), field : 'amount', width : 20, align : 'center',formatter:famterAmt}, 
						{title : $xcp.i18n("formPubMgr.Bal"), field : 'balance', width : 20, align : 'center',formatter:famterAmt}, 
						{title : $xcp.i18n("pendingTask.bizNo"), field : 'bizNo', width : 20, align : 'center',formatter:formatterBizNo}
					]],
			/*pagination:true,*/
			rownumbers:true,
			onDblClickRow: function(rowIndex, rowData){
			}
		});
		$('#balInfoTable').datagrid("loadData",data);
		$xcp.leftTreeMgr.addDatagridList("balInfoTable");
	}
	
	/**
	 * 查看业务最新信息
	 */
	function viewLatestBusInfo(){
		
		var uniqueFlag = uuidHead+"-latestBusInfo";
		var url = $xcp.def.getFullUrl($("#tradeNameMvc").xcpVal()+'.do?method=getInfo&queryFlag=History&taskState=2&txnNo='+$("#txnNo").xcpVal()+"&latestFlag=0"+"&butxn_tradeNo="+$("#butxn_tradeNo").xcpVal()+"&bizNo="+$("#butxn_preBizNo").xcpVal());
		
		var ajaxOpt = {url:
						$xcp.def.getFullUrl("commonQuery.do?method=getLatestBusInfo&queryFlag=History&taskState=2&txnNo="+$("#txnNo").xcpVal()+"&latestFlag=0"+"&butxn_tradeNo="+$("#butxn_tradeNo").xcpVal()+"&bizNo="+$("#butxn_preBizNo").xcpVal())
					  };
		
	    $xcp.ajax(ajaxOpt,"object",null,function(){
	    	var data = "<iframe id=\""+uniqueFlag+"\" src=\"" + url + "\" frameborder='0'  style='width:100%;height:100%;overflow-x:hidden;overflow-y:auto'></iframe>";
	 		$xcp.formPubMgr.formAddTabs(data,$xcp.i18n("formPubMgr.latestBusInfo"));
	    },null);
		
	}
	
	return{
		hasBalInfo : function(latestFlag){
			return hasBalInfo(latestFlag);
		},
		
	    viewBusBalInfo : function(){
	    	return viewBusBalInfo();
	    },
		loadBalInfo : function(data){
			return loadBalInfo(data);
		},
		viewLatestBusInfo : function(){
			 viewLatestBusInfo();
		}
	};
	
}(jQuery);


/***接口查询**/

$.fn.xcpIntfSrchBox = function(opts){
	var _this = $(this);
	$(_this).unbind("click.srchBox").bind("click.srchBox",function(){
		if($(this).hasClass("P"))return;
		$xcp.intfSrchBoxMgr(opts,false,_this);
	});
};

/**
 * 接口查询searchbox
 */
lpbc.intfSrchBoxMgr = function(opts,isEnterKeyDown,_this) {

	var lxOrFun  = opts.lxOrFun,
		callback = opts.callback,
		inParams = {};
	var extendFun = opts.extendFun;
	
	inParams.params = opts.params;
	
	var srchBoxGridId = '#intfSrchBoxGridId'; //列表id对象
	
	var srchBoxcols = [[]];//searchBox需要显示的列
	
	var outputFields = "";//datagrid输出列表，与显示列一一对应
	
	var queryHtml = "";//datagrid上面查询条件的html串
	
	var queryJs = {};
	
	var lxOrName = $xcp.i18n(lxOrFun) != "["+lxOrFun+"]" ? $xcp.i18n(lxOrFun) : $xcp.i18n('comCx.title');
	//销毁已经存在的window
	if($('#intfSrchBoxWinId') && $('#intfSrchBoxWinId').length > 0){
		$('#intfSrchBoxWinId').window().window('destroy');
	}
	
	//读取srchBox配置
	$.ajax({
		type : "POST",
		url  : $xcp.def.getFullUrl('./srchBox.do?method=getSrchBoxCfg'),
		data : {srchName:lxOrFun,type:"1"},
		dataType : "json",
		async : false,
		success  : function(result) {
			if(result.success =='1'){
				$xcp.dispAjaxError($xcp.cloneObject(result));
				result = [];
			}else{
				var collist = $.parseJSON($xcp.toJson(result.outEntity.columns));
				srchBoxcols = getRow(collist,result.outEntity.infName);
				queryHtml = getQueryHtml(collist,result.outEntity.infName);
				
				//点击查询按钮的条件绑定
				queryJs.params = getParams(collist);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			alert(textStatus);
		}
	});
	
	//生成显示的列
	function getRow(comlList,infName){
		var columnsJs = [[]];
		    $.each(comlList,function(i,o){
			   var strJs = {
		   		  //title : $xcp.i18n(o.lxOrFun + "." + o.field),
		   		  field : o.field, 
	   			  width : o.width != null ? parseInt(o.width) :176,   
	   			  align : o.align != null ? o.align :'center',
	   			  hidden : o.hidden == "true" ? true :false ,
				  formatter : o.formatter != "" ? o.formatter : null,
	   			  sortable : true
	   		   };
			   if(o.i18nKey != ""){
	    		   strJs.field = "k_"+strJs.field;
	    	   }
			   if(o.customKey && o.customKey != "" ){
	    		   strJs["title"] = $xcp.i18n(o.customKey);
	    	   }else{
	    		   strJs["title"] = $xcp.i18n(infName + "." + o.field);
	    	   }
			   if(strJs["formatter"] == "yesorno"){
				   strJs["formatter"] = comQueryFamter[strJs["formatter"]];
			   }
			   columnsJs[0].push(strJs);
		    });
		return columnsJs;
	};
    
	function parseQueryType(list){
		if(list == null)return [];
		var strArray = [];
		
		for (var i = 0; i < list.length; i++){
			var querObj = $xcp.parseJson(list[i]);
			if(querObj.editHidden == "true")continue;
			if(querObj.isQuery == "INTERVAL"){
				strArray.push($.extend({},querObj,{
					"fieldAlias":querObj.field + "start"
				}));
				strArray.push($.extend({},querObj,{"fieldAlias":querObj.field}));
				strArray.push($.extend({},querObj,{
					"fieldAlias":querObj.field+"end" 
				}));
			}else{
				strArray.push($.extend({},querObj,{"fieldAlias":querObj.field}));
			}
		}
		return  strArray;
	}
	
	//生成查询条件的html串
	function getQueryHtml(list,name){
		var rowSize = 3;
		result = [];
		result.push('<div style="padding:0px" >\n');
		result.push('\t<table>\n');
		
		list = parseQueryType(list);
		
		var row = parseInt(list.length / rowSize);
		if ((list.length % 3) != 0){
			row += 1;
		}
		var indexHtml = 0;
		for (var i = 0; i < row; i++){
			result.push('\t\t<tr>\n');
			for (var j = 0; j < rowSize; j++){
				if (indexHtml != list.length ) {
					var  str = '';
					var querObj = $xcp.parseJson(list[indexHtml]);
					if(querObj.editHidden == "true" || querObj.isQuery =="" || querObj.isQuery =="N") {
						indexHtml++; continue;
					}
					var fieldHtml = querObj.fieldAlias;
					var fiel = querObj.field;  
					
					if(querObj.queryType == "combobox"){
						str += 'class="easyui-combobox"';
						str += 'data-options="textField:\'name\',valueField:\'val\',data:$xcp.getConstant(\'' 
								+ querObj.queryCondition+'\')"';
					}else if(querObj.queryType == "datebox" || querObj.queryType == "chardatebox"){
						str += 'class="easyui-datebox"';
						str += ' data-options="validType:\'isDate\'"';
					}else if(querObj.queryType == "datetimebox"){
						str += 'class="easyui-datetimebox"';
						str += ' data-options="validType:\'isTimeDate\'"';
					}else if(querObj.queryType == "numberbox"){
						str += 'class="easyui-numberbox"';
						str += ' data-options="precision:\'2\'"';
					}
					var str1 = $xcp.i18n(name + "."+ fiel);
//					str1 = $xcp.i18n(querObj.tabName + fiel);
					
					str1 = fieldHtml.indexOf("start") == -1 ? str1 : str1 + " <";
					str1 = fieldHtml.indexOf("end")   == -1 ? str1 : str1 + " >";
					str1 = querObj.isQuery != "INTERVAL" ? (str1 +":") : str1 + "=";
					
					result.push('\t\t\t<td>'+ str1
							+ '</td><td><input type="text" name="' + fieldHtml  + 'Query'
							+ '" id ="' + fieldHtml  + 'Query' + '" '+str+'/> </td>\n');
				} else {
					//result.push('\t\t\t<td align="right"><button  id="cq_qryBtn" class="paramQueryBtn" type="button">'+ $xcp.i18n('sys.btn.query') + '</button></td>\n');
					break;
				}
				indexHtml++;
			}
			if(i == 0 && indexHtml !=0){
				result.push('\t\t\t<td align="right"><button id="cq_qryBtn"   class="paramQueryBtn" type="button">'+ $xcp.i18n('sys.btn.query') + '</button></td>\n');
			}
			result.push('\t\t</tr>\n');
		}
		
		result.push('\t</table>\n');
		result.push('</div>\n');
		return result.join('');
	}
	
	
	//生成查询参数对象
	function getParams(comlList){
		var paramsJs = {};
		//alert($xcp.toJson(comlList));
	    $.each(comlList,function(i,o){
	       if (o.isQuery != "N" ){//&& o.editHidden != "true"
	    	   if(o.isQuery == "INTERVAL"){
	    		   paramsJs[o.field] =  o.field + "Query";
	    		   paramsJs[o.field+"start"] =  o.field + "startQuery";
	    		   paramsJs[o.field+"end"] =  o.field + "endQuery";
	    	   }else{
	    		   paramsJs[o.field] =  o.field+ "Query";
	    	   }
	       }
	    });
		return paramsJs;
	};
	
	
	//查询按钮
	lpbc.intfSrchBoxMgr.query = function(){
		if(!$(srchBoxGridId) || $(srchBoxGridId).length < 0){
			return false;
		}
		var qrParams = $(srchBoxGridId).datagrid('options').queryParams;
		var btnQueryParams = {};
		if(queryJs.params && !$.isEmptyObject(queryJs.params)){
				$.each(queryJs.params,function(i,x){
					$("#"+i+"Query").xcpVal($("#"+x).xcpVal());
					if($("#"+x).length > 0){
						qrParams[i] = $("#"+x).xcpVal();
					}else{
						$.each(inParams.params,function(j,k){
							if(j == i){
								qrParams[i] = k;
								return false;
							}
						});
						//qrParams[i] = x;
					}
				});
		}
		btnQueryParams.params = qrParams;
		$('#intfSrchBoxGridId').datagrid("options").url = $xcp.def.getFullUrl('./intfCall.do?method=intfcall&srchname='+lxOrFun);
		
		$('#intfSrchBoxGridId').datagrid("options").queryParams = getQueryParam(btnQueryParams);
		$('#intfSrchBoxGridId').datagrid("load");
//		$('#intfSrchBoxGridId').datagrid("loadData",getSearchData(getQueryParam(btnQueryParams)));
	};
	
   
   var html = '<div id="intfSrchBoxWinId" title="'+lxOrName+'"  style="padding:2px;">';
   	   html +=    '<div id="xcpComCxLayout" class="easyui-layout" data-options="fit:true">';
   	   html +=       '<div id="conditionDiv" region="north"  data-options="border:false">';
   	   if(extendFun){
   		   html += extendFun();
   	   }
	   html +=       '<div    class="commQueryCondWrapParam">';
   	   html +=          queryHtml;
   	   html +=       '</div>';
   	   html +=       '</div>';
   	   html +=       '<div id="tt" region="center"  data-options="border:false">';
   	   html +=			 '<div id="intfSrchBoxGridId"></div>';
   	   html +=       '</div>';
   	   html +=    '</div>';
   	   html += '</div>';
   $(html).appendTo('body');
   $.parser.parse("#conditionDiv");
  
   
   /**查询按钮事件*/
   if($('#cq_qryBtn')){
	   $('#cq_qryBtn').unbind('click.srchBoxMgr').bind('click.srchBoxMgr',function(){
		   $xcp.intfSrchBoxMgr.query();
	   });
   }
  
  /**
   * 将业务页面输入的值传入到URL的查询条件
   * @param o
   * @returns {___anonymous31786_31787}
   */
   function getQueryParam(o){
		var queryParams = {};
		if(o.params && !$.isEmptyObject(o.params)){
			$.each(o.params,function(i,x){
				if($("#"+x).length > 0){
					$("#"+i+"Query").xcpVal($("#"+x).xcpVal());
					queryParams[i] = $("#"+x).xcpVal();
				}else{
					queryParams[i] = x;
				}
			});
		}
		return queryParams;
	};	
	
	//初始化表格数据
	$('#intfSrchBoxGridId').datagrid({
		//url : $xcp.def.getFullUrl('./intfCall.do?method=intfcall&srchname='+lxOrFun),
		loadMsg : $xcp.i18n('sys.loadMsg'),
		singleSelect: true,
		columns:srchBoxcols,
		fit: true,	
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		border:false,
		striped:true,
		autoRowHeight:false,
		onDblClickRow : ondblClickFun,
		sortOrder : "desc",
		remoteSort : true, //是否远程排序
		queryParams : getQueryParam(inParams),
		onLoadSuccess : function(data){
			
		},//lsm 
		pageSize: 10,//每页显示的记录条数，默认为10  
	    pageList: [5,10,15,20]//可以设置每页记录条数的列表  
	});
	
	var srchDbClickList = [];
	function getDbClickList(){
	}
	getDbClickList();
	 
	/**
	 * 双击行事件
	 */
	function ondblClickFun (rowIndex,rowData){
		$.each(srchDbClickList,function(i,o){
			eval("("+o+")(rowData)");
		});
		
		if($.isFunction(callback))
			callback.call(this,rowIndex,rowData);
		
		if($('#intfSrchBoxWinId')&& $('#intfSrchBoxWinId').length>0){
			$('#intfSrchBoxWinId').window().window('close');
		}
	}
	
	/**
	 * 获得查询的数据
	 * @returns
	 */
	function getSearchData(queryParams){
		if($('#intfSrchBoxGridId')!= undefined){
			var options = $('#intfSrchBoxGridId').datagrid("options");
			var ajaxOpt =  {
				   url   : $xcp.def.getFullUrl(''),
				   data  : $.extend({},{page:options.pageNumber,rows:options.pageSize},queryParams),
				   aync  : false
			};
			return $xcp.ajax(ajaxOpt,"object");
		}
		return {};
	}
	//动态计算宽度
	function getTabTdWidth(tabid){
		var tdList = $(tabid).find("tr:eq(0) td");
		var nt = 0;
		$.each(tdList,function(i,o){
			nt += $(o).outerWidth();
		});
		nt = nt + 50;
		return nt > 800 ? nt  : 800;
	}
	//返回数据的总条数
	var dataTotal = $('#intfSrchBoxGridId').datagrid("getData").total;
	
	//如果返回的数据只有一条记录且是通过回车的KEYDOWN事件触发
	if(isEnterKeyDown && dataTotal == 1){
		onlyOneDataHandle();
	}else{
		//初始化窗体
		$('#intfSrchBoxWinId').window({
					modal:true,
					closed:false,
					inline : true,
					collapsible : false,
					width: getTabTdWidth($("#conditionDiv").find("table")),
					height:423,
					minimizable : false,
					onClose     : function(){
						$("#conditionDiv").remove();
						$(this).window('destroy');
					},
					onDestroy : function(){
					}
		});
			
		$('#xcpComCxLayout').layout();
	}
	
	/**
	 * 返回数据只有一条的处理
	 *
	 */
	function onlyOneDataHandle(){
		if($('#intfSrchBoxGridId').datagrid("getRows").length > 0){
			ondblClickFun(0,$('#intfSrchBoxGridId').datagrid("getRows")[0]);
		}
	}

	
};



lpbc.intfQuery = function(intfName,opts,number,bodrcd){
	if(!intfName || intfName == "") return [];
	var intData = {};
	
	$xcp.ajax({
		url  : $xcp.def.getFullUrl('./intfCall.do?method=intfcall&srchname=' + intfName+'&bodrcd=' + bodrcd) ,
		data : $.extend({page : 1 , rows : number || 1},opts),
		aync  : false
	},null,null,function(result){
		intData =  !$.isEmptyObject(result) ? result.rows : {};
		intData = (number == undefined || number == 1 ) && !$.isEmptyObject(result) ? intData[0] : intData;
	});
	return intData;
};
