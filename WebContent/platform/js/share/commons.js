 /**
   * 公共JS处理 
   * author by gong yong
   * 2013-05-29 至 2013-06-30
  **/
$.namespace = function() {  
	var a = arguments, o = null, i, j, d;    
	for (i = 0; i < a.length; i++) {        
		d = a[i].split("."); o = window;        
		for (j = 0;j < d.length; j++) {            
			o[d[j]] = o[d[j]] || {};            
			o = o[d[j]];        
        }    
    }    
    return o;
};

//新产品名称空间
var $xcp = $.namespace('lpbc');

//获得上下文
lpbc.getRootPath = function(){
	return rootPath;
};

//获得session id
lpbc.getEAP_SID = function(){
	return EAP_SID;
};

//获得系统语言
lpbc.getEAP_LANGUAGE = function(){
	return EAP_LANGUAGE;
};

//设置语言
lpbc.setEAP_LANGUAGE = function(lang){
	EAP_LANGUAGE =  lang;
};

//获取用户id
lpbc.getUSERID = function(){
	return USERID;
};
//获取用户账号
lpbc.getUSERNAME = function(){
	return USERNAME;
};
//获得用户自定义的查看面函报文的方式
lpbc.getUserRptMtd = function(){
	if(USER_RPTMTD!=null && USER_RPTMTD!=undefined && USER_RPTMTD.length> 0 && "null"!=USER_RPTMTD){
		return $xcp.parseJson(USER_RPTMTD).viewReportMethod;
	}
	return "0";
};

/**
 * 如 combobox numberbox ....
 * 现统一使用$.xcpVal()获得值,$.xcpVal(value) 赋值
 */
$.fn.xcpVal = function(value,triggerEventType,isTrig) {
	/**
	 * 获取元素的值
	 * $('#lccur').getVal()
	 * @param param
	 */
	function getElementVal (src){
		src = $(src);
		var rs  = null,componentName = src.attr('componentName');	
		if(componentName != ''){
			if(componentName == "numberbox")
				rs = src.numberbox("getValue");
			else if(componentName == "combobox")
				rs = src.combobox("getValue");
			else if(componentName == "datebox")
				rs = src.datebox("getValue");
			else if(componentName == "searchbox")
				rs = src.searchbox("getValue");
			else if(componentName == "combotree")
				rs = src.combotree("getValue");
			else
				rs = src.val();
		}else{
			rs = src.val();
		}
		return rs == null || rs == undefined ? "" : rs ;
	};

	/**
	 * 元素赋值
	 * @param param
	 */
	 function setElementVal(src,value){
		src = $(src);
		
		var componentName = src.attr('componentName');
		var setV = isTrig == true ? "setValue" : "setValueNotEvent";
		
		if(componentName != ''){
			if(componentName == "numberbox")
				 src.numberbox(setV,value);
			else if(componentName == "combobox")
				 src.combobox(setV,value);
			else if(componentName == "datebox")
				 src.datebox(setV,value);
			else if(componentName == "searchbox")
				 src.searchbox("setValue",value);
			else if(componentName == "combotree")
				rs = src.combotree(setV,value);
			else
				src.val(value);
		}else{
			src.val(value);			
		}
		
		//业务的change事件
		if(triggerEventType){
			src.trigger(triggerEventType);
		}
		
		//公用的标签 change事件
		//$(src).trigger("change.EBPfixed");
	};
	
	if(value == null){
		return getElementVal(this);	
	}else{
		setElementVal(this,value);
	}
	 
};

//只能用于设置readonly,disabled等属性
$.fn.xcpAttr = function(attrName,bool) {
	var componentName = $(src).attr('componentName');
	if(componentName == "numberbox")
		$(src).numberbox(attrName,bool);
	else if(componentName == "combobox")
		$(src).combobox(attrName,bool);
	else if(componentName == "datebox")
		$(src).datebox(attrName,bool);
	else if(componentName == "searchbox")
		$(src).searchbox(attrName,bool);
	else if(componentName == "combotree")
		$(src).combotree(attrName,bool);
	else
		$(src).attr(attrName,bool);
};

/**
 * 如 combobox numberbox ....
 * 获取页面的name值  有的name初始化以后被easyui设定成comboname 或者其他 统一方法获取name值
 */
$.fn.xcpGetName = function(_this) {
	if(_this == null){
		return "";	
	}
	
	if(_this.attr("id")){
		return _this.attr("id");
	}else if(_this.attr("name")){
		return _this.attr("name");
	}else if(_this.attr("numberboxname")){
		return _this.attr("numberboxname");
	}else if(_this.attr("comboboxid")){// 原为  comboname
		return _this.attr("comboboxid");
	}else if(_this.attr("searchboxname")){
		return _this.attr("searchboxname");	
	}else if(_this.hasClass('datebox-text')){
		var input = _this.parent().find('input[name]');
		return input && input.length > 0 ? input.attr('name') : '';
	}
	return "";
};

/**
 * 判断对象是否为空,为空返回 true,否则返回false
 * 返回true的情况对象可能是: undefined,'',null,'null','   '
 * @param obj 被判别对象
 */
lpbc.isNull = function(obj) {
	// 如果该对象被定义 并且 不等于 null
	if (obj != undefined && obj != null) {
		// 对于字符串还需做点判断
		if (typeof (obj) == 'string') {
			// 去掉2端空格,全局匹配
			var regex = /(^\s*)|(\s*$)/g;
			var objCopy = obj.replace(regex, '').toLowerCase();
			if (objCopy.length > 0 && objCopy != 'null') {
				return false;
			}
		} else {// 对于其他类型 直接...
			return false;
		}
	}
	return true;
};

//克隆对象数组
lpbc.cloneObject = function(o){
	if(!o || 'object' != typeof o){
		return o;
	}
	var c = Object.prototype.toString.call(o) == '[object Array]' ? [] : {};
	var p,v;
	for(p in o){
		if(o.hasOwnProperty(p)){
			v = o[p];
			if( v && 'object' == typeof v){
				c[p] = this.cloneObject(v);
			}else{
				c[p] = v;
			}
		}
	}
	return c;
};

//ajax请求出现异常后的提示
lpbc.dispAjaxError = function(ajaxResponse){
	//弹出提示框
	var html = 		"<div id=\"errorMsgDialog\" class=\"easyui-dialog\" style=\"width:370px;height:260px;padding:10px\">";
		html += 	   "<table class=\"etable\">",
		html += 		 "<tr><td style='width:70px;text-align: right;color:black;font-weight: bold'>"+$xcp.i18n('sys.errorCode')+":&nbsp;&nbsp;</td><td>"+ajaxResponse.errorCode+"</td></tr>",
		html += 		 "<tr><td style='text-align: right;color:black;font-weight: bold'>"+$xcp.i18n('sys.errorMsg')+":&nbsp;&nbsp;</td><td>"+ajaxResponse.errorMsg+"</td></tr>",
		html += 	   "</table>";
		html += 	"</div>";
	$(html).appendTo('body');
	$.parser.parse($("#errorMsgDialog"));
	
	$('#errorMsgDialog').dialog({
		title   : $xcp.i18n('sys.tip'),
		modal   : true,
		buttons : [{
			text:$xcp.i18n('app.confirm'),
			handler:function(){//点击确定按钮触发的函数
				//销毁该窗口
					//alert("销毁了")
					$(this).parent().parent().dialog('destroy');
				//判读错误代码
				if(ajaxResponse.errorCode && typeof(ajaxResponse.errorCode) == 'string'){
					//如果错误代码是以  EBP-1 或 EBP-2开头 直接跳转至登陆首页
					if(ajaxResponse.errorCode.indexOf('EBP-1') == 0 || ajaxResponse.errorCode.indexOf('EBP-2') == 0){
						//取出 最顶层的 window 然后跳转至登陆首页
						window.top.location = rootPath;
					}
				}
			}
		}],
		onClose     : function(){
			$(this).dialog('destroy');
		} 
	});
};

//公共的JSON处理，页面中需引入jQuery.Json-x.x.js
lpbc.toJson = function(o){
	 return $.toJSON ? $.toJSON(o) : '';
};

/**
 * 将json字符串转换成json格式
 * @param src   必填项  要转的json字符串
 * @param isAll 选填项  是否深层转换  isAll 为true 时不实现深层转换
 * var k = {"aa":"bb","bb":{"11":"22"}} 
 * typeof $xcp.parseJson(k).bb  ==  object 
 * typeof $xcp.parseJson(k,true).bb  ==  string 
 * @returns {String}
 */
lpbc.parseJson = function(src,isAll){
	var jsn = "";
	if(typeof src == "string" && src != "" ){
	    jsn = $.evalJSON ? $.evalJSON(src) : null;
	}else{
		jsn = src;
	}
	
	if(isAll === "true" || isAll == true){
		for(i in jsn){
			if(jsn[i] instanceof  Object){
				jsn[i] = $xcp.toJson(jsn[i]) || "";
			}
		}
	}
	return jsn;
};

if (!Function.prototype.bind) {
	Function.prototype.bind = function (oThis) {
	    if (typeof this !== "function") {
	      // closest thing possible to the ECMAScript 5 internal IsCallable function
	      throw new TypeError("Function.prototype.bind - what is trying to be bound is not callable");
	    }
	 
	    var aArgs = Array.prototype.slice.call(arguments, 1), 
	        fToBind = this,
	        fNOP = function () {},
	        fBound = function () {
	          return fToBind.apply(this instanceof fNOP && oThis ? this : oThis,
                     aArgs.concat(Array.prototype.slice.call(arguments)));
	        };
	 
	    fNOP.prototype = this.prototype;
	    fBound.prototype = new fNOP();
	    return fBound;
	};
}

//获取JS国际化脚本，延迟加载JS国际化文件
lpbc.i18n = function($){
	var jsResourceFileLoaded = false,i18nArray = {};
	
	//加载JS国际化的资源文件，需要避免异步调用
	function i18nLoad(){
		if(jsResourceFileLoaded == false){
			$xcp.ajax({
				url : $xcp.def.getFullUrl('./lanQuery.do?method=getLanguageCollection')
			},null,null,function(result){
				if(typeof result == "object"){
					i18nArray = result;
				}else if(result != null){
					i18nArray = $xcp.parseJson(result);
				}
			});
		}
		jsResourceFileLoaded = true;
	}
	
	function prop(key){
		if(i18nArray[key]){
			return i18nArray[key];
		}
		return "[" + key  + "]";
	}
	
	return function(name){
		if(window != top){
			return top.window.$xcp.i18n(name);
		}else{
			if(jsResourceFileLoaded == false){
				i18nLoad();
			}
			return  prop(name);
		}
	};
}(jQuery);
 
/**
 * 设置所有的公共组件用到的一些全局变量和函数
 */
$xcp.def = lpbc.defaults = function($){
	var sysCurDate      = null;        //从后台获取的系统当前日期yyyy-mm-dd
    var userCurTheme    = '';
    var userCurQuickbar = '';
    var offenCurDetails = null;   //常用币种集，包括币种代码，币种名称，国家，保留小数位数等
    var userRptMtd = '';

    /**
     * 由后往前 往字符串中插入符号
     * @param str 字符串
     * @param symbol 将插入的符号
     * @param interval 数字,表示间隔该 数字 个字符便插入1个符号
     * @param lastIndex 起始计算的位置,不传默认为 最后1位开始 
     */
    function insertSymbolStringToLast(str,symbol,interval,lastIndex){
    	if(str){
    		var len = str.length;
        	lastIndex = lastIndex>-1 ? lastIndex:len-1;
        	//字符串缓冲区
        	var strBuf = '';
        	//剩余字符数量, -1 可得当前位置索引
        	var remain = len - (len - (lastIndex+1))-interval;
        	// 上一次位置,索引
        	var prePos = len-1;
        	while(remain > 0){
        		strBuf = symbol+str.substring(remain,prePos+1)+strBuf;
        		//更新上一次位置 和当前位置
        		prePos = remain-1;
        		//减少 interval 个字符
        		remain -= interval;
        	}
        	strBuf = str.substring(0,prePos+1) + strBuf;
        	str = strBuf;
        	if(new RegExp("^-").test(str) && str.split(symbol)[0] == "-"){
        		str = str.replace(symbol,"");
        	}
    	}
    	return str;
    }
    /**判断是否是一个json对象*/
    function isJson(data){
    	 return typeof(data) == "object" && Object.prototype.toString.call(data).toLowerCase() == "[object object]" && !data.length;
    }
    /**比较两个json是否相等*/
    function compareJson(jsn1,jsn2){
		if(!jsn1 || !jsn2)return false;
		if(($.isEmptyObject(jsn1) && $.isEmptyObject(jsn2)))return true;
		if($.isEmptyObject(jsn1) || $.isEmptyObject(jsn2))return false;
		
		var lean = true;
		$.each(jsn1||[],function(i,o){
			if(o != jsn2[i]){blean = false;return false;}
		});
		if(lean){
			$.each(jsn2||[],function(i,o){
				if(o != jsn1[i]){blean = false;return false;}
			});
		}
		return lean;
	}
    
    /**如果常用可以提供一个传入数组里面是json 的方法 实现多个元素 多个定义属性的统一创建**/
    function createXcpHidEvent(evtId,docum,jon){
    	if(typeof evtId == "string"){
    		return createEvent(evtId,docum);
    	}else if(typeof evtId == "object"){
    		for ( var int = 0; int < evtId.length; int++) {
    			createEvent(evtId[int],docum,jon);
    		}
    	}
	}
    
    function createEvent(evtId,docum,jon){
    	var evtObj = document.getElementById(evtId);
		if(evtObj  != undefined) return $(evtObj);
		
		var evStr = jon ? jon.target : "input type='hidden' "; 
		var str = "<" + evStr + " id='" + evtId + "' name='" + evtId + "' />";
		
		var formEvent = docum || $("#" + $xcp.formPubMgr.defaults.currFormId);
		return $(str).appendTo(formEvent);
    }
    
	return {//参数配置已经放到 configOveral配置
		url             : '',
		localCur        : 'CNY',  //牌价计算时的本币
		localCurUtil    : 100,    //牌价计算时的本币单位，比如人民币以'100'为单位
		rateFmtUtil     : 8,      //利率保留小数位数
		qpFmtUtil       : 8,      //汇率保留小数位数
		maxFmtUtil      : 18,      //金额最大长度
		ptFmtUtil       : 2,       //普通金额保留小数位数
		liborUtil       : 6,      //libor利率保留小数位数
		defFeeCur       : 'CNY',  //默认费用处理扣费币种       
		defFeeAcctType  : '',     //默认费用处理扣费的账户类型
		defCapiCur      : 'CNY',  //默认资金流向处理时的入账或扣账币种
		defCapiAcctType : '',     //默认资金流向处理时的入账或扣账账户类型
		zsAcctType      : '',     //默认暂收处理的账户类型
		isContextMenu   : true,   //是否允许出现右键菜单
		holidayType     : '0',    //节假日判断类型 默认为0 0按币种 1按国家
		
		roundAmt      : function(amt,curOrPercision){
			//格式化金额（四舍五入） 
			var returnAmt = parseFloat(amt);
			if(typeof curOrPercision == "number"){
				returnAmt = returnAmt.toFixed(curOrPercision);
			}else{
				var num = this.getAmtFmtUtil(curOrPercision);
				returnAmt = returnAmt.toFixed(num);
			}
			return returnAmt;
		},
		/**
		 * 将金额格式化成一个标准的字符串,如 : 1000 --> '1,000.00'
		 * @param amt 金额
		 * @param curOrPercision 该金额所对应的币种
		 * @returns 字符串
		 */
		formatterAmt : function(amt,curOrPercision){
			if(amt=='' || amt==null || amt==undefined){
				amt = 0;
			}
			//将该金额转换成一个小数
			var amtStr = this.roundAmt(amt,curOrPercision);
			//拿到小数点的位置
			var decimalPoint =  amtStr.lastIndexOf('.');
			//有小数点就从小数点的前1为开始索引(排除'.'嘛),若字符串中没有小数点,就由字符串的最后1位开始索引嘛
			var lastIndex = decimalPoint > -1?decimalPoint-1:amtStr.length-1;
			//若以后 有什么需求 需要针对不同的币种使用不同的切割符号,使用不同的位数
//			if(curOrPercision == 'USD'){
//				var interval = 根据币种选用位数啦,symbol = 根据币种选用切分符号啦
//				amtStr = insertSymbolStringToLast(amtStr,symbol,interval,lastIndex);
//			}
			amtStr = insertSymbolStringToLast(amtStr,',',3,lastIndex);
			return amtStr;
		}
		,
		formatRate     : function(rate){
			//格式化利率（四舍五入）
			return parseFloat(rate).toFixed(this.getDefValue('rateFmtUtil'));
		},
		
		formatQp       : function(qp){
			//格式化汇率（四舍五入）
		    return parseFloat(qp).toFixed(this.getDefValue('qpFmtUtil'));
		},
		
		getOffenCurs  : function(){
			//获取参数中配置的常用币种
			var rs = new Array();
			$.each(this.getOffenCurDetails(),function(i,o){
				rs.push(o.curSign);
			});
			return rs;
		},
		
		getAmtFmtUtil : function(cur){
			//获取某个币种的金额格式化时保留小数位数
			var rs = null;
			if(cur){
				$.each(this.getOffenCurDetails(),function(i,o){
					if(o.curSign == cur){
						//rs = o.amtFmtUtil;
						rs = parseInt(o.percision);
						return false;
					}
				});
			}
			if(rs == null){
				rs = cur && (cur == 'JPY' || cur == "KWR") ? 0 : 2;
			}
			return rs;
		},
		
		setDefOptions : function(options){
			options = options || {};
			for(var i in options){
				if($.isFunction(options[i])	&& $.inArray(i,['defFeeCur','defFeeAcctType','defCapiCur','defCapiAcctType','zsAcctType']) < 0 ){
					//确保只有少数几个参数能赋函数
					delete options[i];
				}
			}
			//业务设置参数
			$.extend(this, options);
		},
		
		getOffenCurDetails : function(){
			return offenCurDetails = offenCurDetails == null ? $xcp.getConstant('cur') : offenCurDetails;
		},
		
		getDefValue : function(opName){
			var o = eval("this['" + opName + "']");
			if(o && $.isFunction(o)){
				return o.call(this);
			}
			return o;
		},
		
		getSysCurrDate : function(){
			return sysDate;
		},
		
		getEAP_SID : function(){
			return $xcp.getEAP_SID();
		},
		
		getEAP_LANGUAGE : function(){
			return EAP_LANGUAGE ;
		},
		
		getUSERID : function(){
			return USERID;
		},
		
		setEAP_LANGUAGE : function(lang){
			return  setEAP_LANGUAGE(lang);
		},
		
		setCookie : function (name,value){
		    var exp = new Date();
		    exp.setTime(exp.getTime() + 30*60*1000);
		    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
		},
		
		getFullUrl : function(opt){
			var sid = this.getEAP_SID();
			$xcp.def.setCookie("EAP_SID", sid.substring(sid.indexOf("=") + 1));
			var url = typeof opt ==  'string' ? opt : opt.url ? opt.url : $xcp.formPubMgr.defaults.url ;  // this .url
			if(url != null && url.length > 0){
				url = url.indexOf('?') >=0 ? url + "&" + this.getEAP_SID() : url + "?" + this.getEAP_SID(); //添回SESSION ID
				url = url+'&locale='+this.getEAP_LANGUAGE();
				if(opt.urlMethod){
					url += "&method=" + opt.urlMethod;
				}
				url = url + '&userId=' + this.getUSERID() + '&random='+ this.getTimeUuid();
			}
			return url;
		},
		
		isLocalCur : function(cur){
			if(cur){
				return cur == this.localCur ? true : false;
			}
			return false;
		},
		
		getTimeUuid : function(){
			return (new Date()).getTime() + "-" + Math.ceil(Math.random()*1000);
		},
		
		getUserCurrentTheme : function(){
			return userCurTheme || 'bootstrap';
		},
		
		setUserCurrentTheme : function(theme){
			userCurTheme = theme;
		},
		setUserCurrentQuickbar : function(quickbar){
			userCurQuickbar = quickbar;
		},
		
		getUserCurrentQuickbar : function(){
			return userCurQuickbar || null;
		},
		
		setUserViewRptMethod : function(rptMtd){
			userRptMtd = rptMtd;
		},
		
		getUserViewRptMethod : function(){
			if(userRptMtd && userRptMtd.length > 0 ){
				return $xcp.parseJson(userRptMtd).viewReportMethod;
			}
			return "0";
		},
		
		isJson : function(jon){
			return isJson(jon);
		},
		compareJson : function(jsn1,jsn2){
			compareJson(jsn1,jsn2);
		},
		createXcpHidEvent : function(evtId,docum,jon){
			return createXcpHidEvent(evtId,docum,jon);
		}
	};
	 
}(jQuery);
//js日期扩展
lpbc.date = {
    //日期分割符
	dateSepartor : '-', 
	
    //日期类型格式化成字符串
	format : function(date,fmt){
		fmt = fmt || ('yyyy' + this.dateSepartor + 'MM' + this.dateSepartor + 'dd');
        var o =
        { 
            "M+" : date.getMonth() + 1, //月份 
            "d+" : date.getDate()       //日 
        }; 
        if (/(y+)/.test(fmt)) 
            fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length)); 
        for (var k in o) 
            if (new RegExp("(" + k + ")").test(fmt)) 
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length))); 
        return fmt; 
	},
	
    //字符串类型格式化成日期
	toDate : function(strDateValue) {
		if(strDateValue){
			try{
				var tmp = strDateValue.indexOf('-') > 0 ? strDateValue.split('-') : strDateValue.split('/');
				if(strDateValue.indexOf('-') > 0){
					return new Date(tmp[0],tmp[1]-1,tmp[2]);
				}else{
					if(tmp[0].length == 4 ){// 支持 MM/DD/YYYY 和 YYYY/MM/DD
						return new Date(tmp[0],tmp[1]-1,tmp[2]);
					}else {
						return new Date(tmp[2],tmp[0]-1,tmp[1]);
					}
				}
			}catch(e){
			}
		}
		return null;
	},
	
	//字符串日期转化成日期类型减去天数，再格式化成字符日期
	minusDays : function(strDateValue,d){
		var date = this.toDate(strDateValue);
		if(date){
			return  this.format(new Date(date.setDate(date.getDate() - d)));
		}
		return '';
	},
	
    //字符串日期转化成日期类型加上天数，再格式化成字符日期
	addDays  : function(strDateValue,d){
		var date = this.toDate(strDateValue);
		if(date){
			return  this.format(new Date(date.setDate(date.getDate() + d)));
		}
		return '';
	},
	
	//字符串日期转化成日期类型加上几周，再格式化成字符日期
	addWeeks : function(strDateValue,w){
        return this.addDays(strDateValue,w * 7);
    },
    
    //字符串日期转化成日期类型再加上几月，再格式化成字符日期
    addMonths : function(strDateValue,m){
    	var date = this.toDate(strDateValue);
		if(date){
			return  this.format(new Date(date.setMonth(date.getMonth() + m)));
		}
		return '';
    },
    
    //字符串日期转化成日期类型，加上几年，再格式化成字符日期
    addYears : function(strDateValue,y){
    	var date = this.toDate(strDateValue);
		if(date){
			return  this.format(new Date(date.setFullYear(date.getFullYear() + y)));
		}
		return '';
    },
    
    //计算FIRST - SECODE的间隔，LX不输入则计算日期间隔天数
    dateDiff : function(firstStrDateValue,secondStrDateValue,lx){
    	lx = lx || 'd';
    	var first = this.toDate(firstStrDateValue),second = this.toDate(secondStrDateValue);
    	if(first && second){
    		switch (lx)      
            {             
                //case "s":   //計算秒差                        
                //    return parseInt((first - second)/1000);                                  
                //case "n":   //計算分差                    
                //    return parseInt((first - second)/60000);               
                //case "h":   //計算時差                        
                //    return parseInt((first - second)/3600000);                 
                case "d":   //計算日差             
                    return parseInt((first - second)/86400000);            
                case "w":   //計算周差            
                    return parseInt((first - second)/(86400000*7));            
                case "m":   //計算月差                 
                    return (first.getMonth() + 1) + ((first.getFullYear() - second.getFullYear()) * 12) - (second.getMonth() + 1);           
                case "y":   //計算年差                       
                    return first.getFullYear() - second.getFullYear();            
                default:    //輸入有誤                      
                    return undefined;       
            }    
    	}
    	return undefined;
    },
    
    //字符串日期比较，第一个参数大返回1,相等返回0，第二个参数大返回-1
    compareDate : function(firstStrDateValue,secondStrDateValue,lx){
    	if(!firstStrDateValue && secondStrDateValue){
    		return -1;
    	}else if(firstStrDateValue && !secondStrDateValue){
    		return 1;
    	}else{
    		var t = this.dateDiff(firstStrDateValue,secondStrDateValue);
    		return t > 0 ? 1 : t == 0 ? 0 : -1;
    	}
    },
    
    //计算月差
    monthDiff :function(firstStrDateValue,secondStrDateValue){
        return null;
    }
};

//业务界面中的元素栏位光票处理
$.extend(lpbc,{
	//获取当前文本框选中的文本
	getSelectedText : function (domObj) {
		if (domObj.selectionStart || domObj.selectionStart == '0') {
			return domObj.value.substring(domObj.selectionStart, domObj.selectionEnd);
		}
		else if (document.selection) { //for IE
			domObj.focus();
			var sel = document.selection.createRange();
			return sel.text;
		}
		else return '';
	},

	//获取当前光标在文本框的位置
	getCurPosition	 : function(domObj) {
		var position = 0;
		if (domObj.selectionStart || domObj.selectionStart == '0') {
			position = domObj.selectionStart;
		}
		else if (document.selection) { //for IE
			domObj.focus();
			var currentRange = document.selection.createRange();
			var workRange = currentRange.duplicate();
			domObj.select();
			var allRange = document.selection.createRange();
			while (workRange.compareEndPoints("StartToStart", allRange) > 0) {
				workRange.moveStart("character", -1);
				position++;
			}
			currentRange.select();
		}
		return position;
	}
});

//加载进度条（统一在顶层窗口中使用）
lpbc.startPorcess = function(){
	 $.messager.progress({
			title : $xcp.i18n('progress.title'),
			text : $xcp.i18n('progress.text')
	 });
};

//关闭顶层窗口中的进度条
lpbc.stopPorcess = function(){
	try{
		//关闭顶层窗口的进度条显示
		top.window.$.messager.progress('close');
	}catch(e){
		
	}
};

/**
 * def 默认错误信息码,可以替换和扩展
 * 错误操作对象
 * EBP 错误定义
 * 前缀 EBP-
 * 错误码由3位组成
 * 第一位错误类型: 1,登录类异常.2,session异常
 * 第二位三位为具体错误代码
 */
lpbc.errorOpera = {
	"201" : function(){
		$.messager.confirm($xcp.i18n("paramMar.tixck"),"session超时，请重新登陆!",function(r){
		    if (r){
		    	top.location.href = "./";
		    	return false;
		    }else{
		    	//top.location.href = "./";
		    }
		});
	},
	"301" : function(result){
		setTimeout(function(){
			$.messager.confirm($xcp.i18n("paramMar.tixck"),result.errorMsg,function(r){
			    if (r){
			    	$xcp.formPubMgr.formCloaseTask();
			    	return false;
			    }
			});
		},1000);
	}
};

/**
 * 统一封装AJAX的处理
 * @param opt，包括ajaxType,url,data,dataType,asyncFlag...
 * @param cbfun 成功获取后的回调函数，可为空，此函数适应于异步获取数据
 * @param fmtfun 对于数组类型的返回值作格式化处理
 * @param retype in ('object','array','') 返回值的数据类型，可以是object--{}对象,array--[]数组,''--则表示基本的数据类型，比如字符，数字，boolean等
 */
lpbc.ajax = function(opt,retype,fmtfun,cbfun,erfun){
	opt = opt || {}; var rs ;
	opt.async = opt.async === true ? true : false;
	
	if(opt.url){
		if(opt.url.indexOf(this.getEAP_SID()) == -1){
			opt.url = $xcp.def.getFullUrl(opt.url);
		}
		$.ajax({
			type     : opt.ajaxType === 'get'  || opt.ajaxType === 'GET' ? 'get' : 'post',
			url      : opt.url,
			data     : opt.data || null,
			dataType : opt.dataType || "json",	
			//async    : opt.async === true ? true : false,
			async    : opt.async,
			//不允许使用缓存
			cache : false,
			success  : $.isFunction(opt.success) ? opt.success : function(result) {
				var errorCode = resolveErrorCode(result,opt.dataType || "json");
				 
				if(errorCode != "" && errorCode.length >= 7 && errorCode.substring(0,4)== "EBP-"){
					//错误信息 2 为session 超时错误
					errorCode = errorCode.substring(4,7);
					
					if($xcp.errorOpera[errorCode]){
						var blean = $.isFunction($xcp.errorOpera[errorCode])? $xcp.errorOpera[errorCode](result) : true;
						if(false == blean){
							return ;
						}
					}
				}
				if(result == null)return ;
				
				if(result.success  == "1"){
					if(erfun != null && $.isFunction(erfun)){
						erfun(result,opt);
					}
					$xcp.dispAjaxError(result); //显示异常错误
				}else{
					rs = result.outEntity;
					if(retype != null  && retype.toLowerCase() === 'array'){
						if(rs && !$.isArray(rs)){
							rs = [rs];
						}						
					}
					
					if(fmtfun != null && $.isFunction(fmtfun)){
						fmtfun(rs);
					}
					
					if(cbfun != null && $.isFunction(cbfun)){
						//对于异步处理，需要请求端传递一个回调函数，
						cbfun(rs,opt,result);
					}					
				}
			},
			beforeSend : function(XMLHttpRequest, textStatus){
				
			},
			error : $.isFunction(opt.error) ? opt.error : function(XMLHttpRequest, textStatus, errorThrown){
			}
		});
	}
	
	//如果是同步请求，则返回结果
	if(opt.async === false){
		return rs;
	}else{
		return null;
	}
	
	/**
	 * 内部函数
	 */
	function resolveErrorCode(result,dataType){
		var errorCode = "";
		if(dataType == "json"){
			 errorCode = result != null ?  result.errorCode : "";
		}else if(dataType == "html"){
			if(result.indexOf("success") != -1 && result.indexOf("success") != -1){
				errorCode = result.substring(result.indexOf("errorCode")+12,result.indexOf("errorCode")+19);
			}
		}else{
			 errorCode = result != null ?  result.errorCode : "";
		}
		errorCode = errorCode||"";
		return errorCode;
	}
};

$(function() {
	document.onkeydown = function(e) {
		// 获取事件对象
		var event = e || window.event;
		//屏蔽backspace回退
		if (event.keyCode == 8) {// 判断按键为backSpace键
			// 获取按键按下时光标做指向的element
			var elem = event.srcElement || event.target ||event.currentTarget || event.relatedTarget;
			// 判断是否需要阻止按下键盘的事件默认传递
			var name = elem.nodeName;
			if (name != 'INPUT' && name != 'TEXTAREA') {
				return _stopIt(event);
			}
			
			var type_e = elem.type.toUpperCase();
			if (name == 'INPUT'
					&& (type_e != 'TEXT' && type_e != 'TEXTAREA'
							&& type_e != 'PASSWORD' && type_e != 'FILE')) {
				return _stopIt(event);
			}
			if ((name == 'INPUT'|| name == 'TEXTAREA')
					&& (elem.readOnly == true || elem.disabled == true)) {
				return _stopIt(event);
			}
		}else if(event.keyCode == 9 ){  /** 按键盘的TAB键切换*/
	
			$("input[type!='hidden']").attr("tabIndex","1");
			$("textarea").attr("tabIndex","1");
			var firstEle = $("[tabIndex='1']")[0];
			var target = undefined;
			if(event.target){
				target = $(event.target);
			}else if(event.srcElement){
				target = $(event.srcElement);
			}
			
			if($(target).attr("tabIndex") == undefined){
				if(firstEle != null && firstEle != undefined){
					$(firstEle).focus();
				}
			}
		}
	};
	
	function _stopIt(e) {
		if (e.returnValue) {
			e.returnValue = false;
		}
		if (e.preventDefault) {
			e.preventDefault();
		}
		return false;
	};
});

/***
 * @author gy 2013-10-08
 * 全局的常量对象，可扩展，
 * 主要用途：对于一些币种，国家以及是和否等静态的参数，一次性取出，供同一浏览器界面下的多个交易的下拉列表框使用
 * 如果需要通过AJAX到后台获取数据时，url不带任何参数
 */
lpbc.constantMgr =  lpbc.constantManager  =  function($){
	
	/**缓存区域 */
	var constants = {};
	
	/**异步或同步标识，默认为同步*/
	var asyncFlag = false;
	//URL,需要从后台返回对象数组, 其中url的key为一个JS对象，包括url,realTiem(实时获取),param 查询能数等,isFixedData表示是否固定的数据集
	var urls = {
		};
	
	//Y 和 N 固定值在前端定义
	constants.yn = [{name : $xcp.i18n('Y'), value : 'Y'},{name : $xcp.i18n('N'), value : 'N'}];	
	
	//ajax get data
	function getConstantUrlData(key,isAsync){
		 
		if(!urls[key]){
			urls[key] = {url :"utilQuery.do?method=getComboData&tableName="+key,realTime:false, isFixedData:true};
		}
		
		var rs = null, param = getUrlParam(key)/*,formatfun = getOptionByKey(key,'formatText')*/;
		var ajaxOpt = {
			ajaxType : 'POST',
			url      : $xcp.def.getFullUrl(urls[key]['url']),
			data     : isAsync === true  || urls[key]['isFixedData'] === true ? null : param,
			dataType : 'json',
			async    : asyncFlag
		};
		
	    var ajaxCallBack = function(rsdata){
			constants[key] = rsdata || [];
		};
		
		return $xcp.ajax(ajaxOpt,'array',asyncFlag == true ? ajaxCallBack : null ,null);
	}
	
	//获取URL的参数
	function getUrlParam(key){
		var rs = null;
		if(urls[key] && urls[key]['param']){
			if($.isFunction(urls[key]['param'])){
				rs = urls[key]['param']();
			}else{
				rs = urls[key]['param'];
			}
		}
		return rs || null;
	}
	
	function getOptionByKey(key,paramName){
		if(urls[key] && paramName)
			return urls[key][paramName] || null;
		return urls[key] || null;
	}
	
	/*主界面初始化时加载固定数据并缓存，供主界面或其它子界面使用，主要用于提高easyui-combobox 的使用效率*/
	function initLoadConstant(){
		asyncFlag = true;
		$.each(urls,function(key,o){
			try{
				if(o['isFixedData'] === true ){
					if(key == 'cur'){
						getConstantUrlData(key,true);
					}
				}
			}catch(e){
				//...
			}
		});
		asyncFlag = false;
	}
	
	return {
		/**
		 * 根据传递的key 
		 * @param key url对应的key（必传）
		 * @param p 可以是一个对象{}，也可以是一个数组，当为对象时，更新当次查询参数param，当为数组时追加此数组到返回列表中
		 * @param appendArr 是指要追加到返回数组中的值，当P参数不为空时使作，当P参数为空时可将appendArr参数作为P参数引入
		 * @returns url对应的响应资源数据 格式为数组
		 */
		get : function(urlKey,p,appendArr){
			if(urlKey){
				var url = urls[urlKey], rs = null;
				var ap = appendArr == null && p != null && $.isArray(p) ? p : appendArr != null && $.isArray(appendArr)  ?  appendArr :  null;
				
				if(!url){
					url= {url :"utilQuery.do?method=getComboData",realTime:false, isFixedData:true,param:{tableName:urlKey}};
				}
				if( url.isFixedData === true){
					if(!constants[urlKey]){
					    constants[urlKey] =  getConstantUrlData(urlKey);
					}
					rs = constants[urlKey] || [];
				}else{
					if(url && p != null && $.isPlainObject(p) && !$.isArray(p)){
						url._param  = url.param;  $.extend(url['param'],p);
						//当外部传入了参数param时，默认为实时获取并且不做缓存处理
						rs = getConstantUrlData(urlKey);
					}else{
						if(!constants[urlKey] && urls[urlKey] && urls[urlKey]['url']){
							if(urls[urlKey]['realTime'] === true){
								rs = getConstantUrlData(urlKey);
							}else{
								rs = constants[urlKey] = getConstantUrlData(urlKey);
							}
						}else{
							rs = constants[urlKey];
						}
					}
					if(url._param != null){
						//重置默认参数
						url.param = url._param; delete url._param;
					}
				}
			}
			
			rs = rs || [];
			if($.isArray(ap)) {
				var rsClone  = $xcp.cloneObject(rs);
				$.each(ap,function(i,o){rsClone.push(o)});
				return rsClone;
			}
			
			//格式化下拉文本
			var formatfun = getOptionByKey(urlKey,'formatText');
			if(formatfun != null && $.isFunction(formatfun)){
				formatfun(rs);
				if(urls[urlKey] && ( urls[urlKey].realTime === false || urls[urlKey].isFixedData === true) ){
					delete urls[urlKey]['formatText'];
				}
			}
			
			return rs;
		},
		
		//增加一个常量参数: param格式为 {key:'xx',data:[] or null, url: '' or null,realTime: false or true,param:{} or null,formatText:fun}
		add : function(param){
			if(param && param.key != null && constants[param.key] == null){
				if(param.data != null){
					var data = $.isArray(param.data) ? param.data : [param.data];
					constants[param.key] = data;
				}else if(param.url != null && param.url != ''){
					urls[param.key] = param;
				}
			}
		},
		
		//移除一个常量
		remove : function(urlKey){
			if(key){
				delete constants[getCacheKey(urlKey)];
				delete urls[urlKey];
			}
		},
		
		initLoad : function(){
			initLoadConstant();
		},
		
		setAsyncFlag : function(inAsyncFlag){
			asyncFlag = inAsyncFlag === true ? true : false;
		},
		setConstant : function(options){
			$.extend(urls, options || {}); 
		}
	};
}(jQuery);

/**
 * 动态增加静态数据参数,主要用于给combobox下拉列表的data赋值
 **/
lpbc.addConstant = function(param){
	$xcp.constantMgr.add(param);
};

/**
 * 根据传递的key 获取常用的静态参数，主要用于给combobox下拉列表的data赋值
 * @param key url对应的key（必传）
 */
lpbc.getConstant = function(key,p,appendArr){
	return $xcp.constantMgr.get(key,p,appendArr);
};