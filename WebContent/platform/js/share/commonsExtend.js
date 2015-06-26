/**
 * ls 2014-02-10
 * 扩展 jquery , jquery-easyui , javascript 等 js
 */

/**
 * 重写js中toFixed的方法，
 * 避免出现：0.009 四舍五入变成 0.00
 * @param fractionDigits  小数点保留位数
 * @returns {String}
 */
Number.prototype.toFixed = function(fractionDigits) {
    var f = parseInt(fractionDigits) || 0;
    if( f < -20 || f > 100 ) { 
        throw new RangeError("Precision of " + f + " fractional digits is out of range");
    }
    var x = Number(this);
    if( isNaN(x) ) {
        return "NaN";
    }
    var s = "";
    if(x < 0) {
        s = "-";
        x = -x;
    }
    if( x >= Math.pow(10, 21) ) {
        return s + x.toString();
    }
    var m;
    // 10. Let n be an integer for which the exact mathematical value of 
    // n ÷ 10^f - x is as close to zero as possible. 
    // If there are two such n, pick the larger n.
    n = Math.round(x * Math.pow(10, f) );

    if( n == 0 ) {
        m = "0";
    }
    else {
        // let m be the string consisting of the digits of the decimal representation of n (in order, with no leading zeroes).
        m =  n.toString();
    }
    if( f == 0 ) {
        return s + m;
    }
    var k = m.length;
    if(k <= f) {
        var z = Math.pow(10, f+1-k).toString().substring(1);
        m = z + m;
        k = f+1;
    }
    if(f > 0) {
        var a = m.substring(0, k-f);
        var b = m.substring(k-f);
        m = a + "." + b;
    }
    return s + m;
};

/**  
 * $.fn.form.getXcpFormData 表单扩展，用于ajax提交时获取所有表单域对象值
 * 注意：1：表单域必须有name属性，2：disabled属性值不能等于true
 */ 
$.extend($.fn.form.methods, {  
    getXcpFormData: function(jq, params){  
        var formArray = jq.serializeArray();  
        var oRet = {};  
        for (var i in formArray) {  
        	if(oRet[formArray[i].name] != undefined )
        		oRet[formArray[i].name] = oRet[formArray[i].name] + "," +formArray[i].value;  
        	else
        		oRet[formArray[i].name] = formArray[i].value;  
        }  
        return oRet;  
    }  
}); 

/**
 * $.fn.combobox.defaults  commobox 模糊查询 输入字符匹配不区分大小分 （本来区分大小写）
 **/
$.extend($.fn.combobox.defaults, {  
	filter: function(q, row) {
		var opts = $(this).combobox("options");
		return row[opts.textField].toUpperCase().indexOf(q.toUpperCase()) == 0;
	} 
}); 
/**判断一个对象是否有竖向进度条*/
$.fn.hasScrollHeight = function() {
    return this.get(0) ? this.get(0).scrollHeight > this.innerHeight() : false;
};

//字符串扩展，主要用于格式化
String.prototype.format = function(args) {
    var result = this;
    if (arguments.length > 0) {    
        if (arguments.length == 1 && typeof (args) == "object") {
            for (var key in args) {
                if(args[key]!=undefined){
                    var reg = new RegExp("({" + key + "})", "g");
                    result = result.replace(reg, args[key]);
                }
            }
        }
        else {
            for (var i = 0; i < arguments.length; i++) {
                if (arguments[i] != undefined) {
                	var reg= new RegExp("({)" + i + "(})", "g");
                    result = result.replace(reg, arguments[i]);
                }
            }
        }
    }
    return result;
};

//将每段文本的前后空格\t\r\n去掉,
String.prototype.trim = function(){
	return this == null ? "" : this.replace(/(^[ \t\n\r]+)|([ \t\r\n]+$)/g,"");
};

//将每段文本的前空格\t\r\n去掉
String.prototype.ltrim = function(){
	return this == null ? "" : this.replace(/(^[ \t\n\r]+)/g,"");
};

//将每段文本的尾空格\t\r\n去掉
String.prototype.rtrim = function(){
	return this == null ? "" : this.replace(/([ \t\r\n]+$)/g,"");
};

/**swift textarea[rows*cols]格式化*/
$.fn.formatSwiftTextarea = function(){

	/*********************************************
    * swift Textarea[rows * cols] => 格式化说明
	* 1: 去掉尾部换行符和前后空格,根据换行符分割成多个字符数组
	* 2: 对数组作循环处理
	* 3:    如果一行中的字符长度小于或等于cols则直接返回该行记录
	* 4:    如果一行中的字符长度大于cols,则按正则表达式分割作特殊处理 /(\s*)(\S+)(\s*)/g  ==> /空格,非空格字符,空格/g
	* 5:    示例 this is a good idea 可解析为 'this' ,' ', 'is', ' ','a' ,' ', 'good', ' ','idea' 等字符串,据此结果再作运算和拼串处理
	* 6:    字符串可包含中文,多个空格,单词长度超过cols等多种情况，控制每行开头不出现空格字符
	* 7:    对于中文字符按五个字符长度计算
	* 8: 返回一个新的经过格式化后的字符串
	**********************************************/
  
   //内部函数，获取字符串长度，中文按五个长度计算
   function getLength(str){
	   var len = 0 ,cnArrs = null;
	   if(str != ''){
		  cnArrs = str.match(/[^\x00-\x80]/g); 
		  //说明:SWIFT一个中文要占五个字符位(四位电报码 + 一个空格)						  
		  len = str.length + (cnArrs == null ? 0 : cnArrs.length * 4);
	   }				 
	   return len;
   }

   var enterSplit = "\n"; //换行分割符，区分浏览器
   var cols = $(this).attr('cols') || 0 , rows = $(this).attr('rows') , arrs = [];

   var value = $(this).val().replace(/\n*$/,'').trim(); //去掉结尾的换行符和前后空格
   if(value == "" || cols == 0 || getLength(value) <= cols ) return value;
   
   var swfValidFlag =  $(this).attr('swftFmtMust');
   if(swfValidFlag != undefined && swfValidFlag == 'N'){
	   return value;
   }
   
   var vr = value.split(enterSplit);                    //先根据换行符分割成多行,然后再对每一行作特殊处理	

   try{
		for(var k = 0 ; k < vr.length; k++){            //循环处理每一行字符
			
			 var o = (vr[k] || '').trim();
			
			 if(o == '' || getLength(o) <= cols) {								
				 arrs.push(o);	                        //当某一行的字符长度小于或等于cols时不作处理,原样返回					
			 }else{							 
				 var t = '';                            //当某一行中存在中文或英文且长度超过cols时,需要作特殊处理，中文按五个字符计算	 
				 o.replace(/(\s*)(\S+)(\s*)/g, function(m,a,b,c,n){									 
					
					 var  al = a.length, bl = getLength(b),cl=c.length;
					 var  tl = getLength(t);								
					 
					 //匹配字符前的空格(有可能为空),行首要去掉空格字符
					 if(t != ''){
						 for(var i = 0; i < al; i++){									 
							   if(tl == cols){
							        //添加行时去掉尾空格
									arrs.push(t.rtrim());  t= '';  tl = 0;
							   }
							   //t += a.charAt(i); tl++; 其中 a.charAt(i) 是一个空格,可以直接用 t += ' '
							   //当a 中存在多个空格且换行时，换行后多余的空格舍弃掉
							   if(t != ''){
								   t += ' '; tl++;
							   }
						 }	
					 }
													
					 if(bl > 0){
						 var tmpBl = b.length;
						 if(tmpBl == bl && tmpBl <= cols){									
							  //tmpBl == bl 表示 b 中不包含中文字符,此时 b 是一段不包含空格的文本
							  if(tl == cols){
								  arrs.push(t);  t= '';  tl = 0;
							  }
							  if(tl + bl > cols){										
								  //如果b中包含','也需要再次分割,则在此作优化处理
								  arrs.push(t);  t = b;  tl = bl;
							  }else if(tl + bl == cols){
								  //如果 t + b == cols则另启一行
								  arrs.push(t + b);  t = '';  tl = 0;
							  }else{
								  t += b;
								  tl = getLength(t);
							  }
						 }else{									
							 //当b字符串中存在有中文的情况,一个中文当成两个字符,按匹配前后空格的方式处理	
							 //当b字符串的长度大于cols时,也按匹配前后空格的方式处理
							 for(var i = 0; i < tmpBl; i++){
								   var curChar =  b.charAt(i);
								   if(tl == cols){
										arrs.push(t);  t= '';  tl = 0;
								   }
								   var tmp = getLength(curChar);											   
								   if(tl + tmp > cols){
										arrs.push(t);  t= curChar;  tl = tmp;
								   }else if(tl + tmp == cols){
										arrs.push(t + curChar);  t= '';  tl = 0;
								   }else{
										t += curChar; tl = tl + tmp;
								   }
							 }
						 }
					 }
					 
					 //匹配字符后的空格(有可能为空)
					 if(t != ''){
						 for(var i = 0; i < cl; i++){									 
							   if(tl == cols){
							        //添加行时去掉尾空格
									arrs.push(t.rtrim());  t= '';  tl = 0;
							   }
							   //t += c.charAt(i); tl++; c.charAt(i) 是一个空格,可以直接用 t += ' '
							   //当c中存在多个空格且换行时，换行后多余的空格舍弃掉
							   if(t != ''){
								  t += ' '; tl++;
							   }
						 }	
					 }

					 //防止退出循环时t的长度 == cols,需要重新处理一次
					 if(tl == cols && t.trim() != ''){
					      //去掉前后空格
						  arrs.push(t.trim());  t= '';  tl = 0;
					 }
				 });					 
				 
				 if(t.ltrim() != ''){
					arrs.push(t.trim());							
				 }
			 }
		}
   }catch(e){
		arrs = vr;
   }
   return arrs.join(enterSplit);				  
};

/**获取任务状态***/
/**
 * 交易任务状态管理，提供检验交易状态的通用函数，如$xcp.task.isHandle() 
 */
lpbc.task =   function($){
	var Handle = "Handle" ,Check = "Chenck", Correct = "Correct" , Authorize = "Authorize",
	Accounting = "Accounting", Process = "Process";
	stepList = {
		Handle : 1001 ,Check : 2001 ,Correct : 3001 ,
		Authorize : 4001,Accounting : 6002,Process : 7002
	};
	
	//内部状态检验函数
	function  inculeTask(taskStu,usrDefined){
		if(!taskFlag)return true;
		var myTaskFlag = taskFlag.substring(0,1) || "";
		
		if(usrDefined != null && usrDefined != "undefined"){
			myTaskFlag = usrDefined.substring(0,1) || "";
		}
		
		//taskState在head.jsp中定义
		//taskState 0-可操作，1-未完成，2-已完成
		if(taskState == "0"){
			if(myTaskFlag == "1" && taskStu == Handle){
				 return true;
			}else if( myTaskFlag == "2" && taskStu == Check){
				 return true;
			}else if( myTaskFlag == "3" && taskStu == Correct){
				 return true;
			}else if( myTaskFlag == "4" && taskStu == Authorize){
				 return true;
			}else if( myTaskFlag == "5" && taskStu == Transmitters){
				 return true;
			}else if( myTaskFlag == "6" && taskStu == Accounting){
				 return true;
			}else if( myTaskFlag == "7" && taskStu == Process){
				 return true;
			}else if( myTaskFlag == "1" &&  taskFlagHadle === true && taskStu == "MuHandle"){
				 return true;
			}/*else{
				$.messager.alert("错误提示", "无效的任务状态类型", "warning");
			}*/
		}else if(taskState == "2"){    //已完成
			if(myTaskFlag == "F" && taskStu == "Finish"){
				 return true;
			}
		}
		
		return false;
	}
	
	//内部状态检验函数
	function  getInculeTask(taskStu){
		if(taskStu == Handle){
			 return "1001";
		}else if(taskStu == Check){
			 return "2001";
		}else if(taskStu == Correct){
			 return "3001";
		}else if(taskStu == Authorize){
			 return "4001";
		}else if(taskStu == Accounting){
			 return "6002";
		}else if(taskStu == Process){
			 return "7002";
		}
		return false;
	}
	
	/**
	 * 初始化设置不同任务状态的提醒颜色
	 */
	function initTaskProperties(){
		//bcolor背景色 fcolor字体颜色
		var opts={bcolor:"#BCD6F1",fcolor:"#0773FA",text:""};
		
		if($xcp.task.isHandle()){
			opts.bcolor  = "#BCD6F1";
			opts.fcolor  = "#0773FA";
			opts.text = $xcp.i18n("step.handle");
		}else if($xcp.task.isCheck()){
			opts.bcolor  = "#D2F8CF";
			opts.fcolor  = "#32A428";
			opts.text = $xcp.i18n("step.check");
		}else if($xcp.task.isCorrect()){
			opts.bcolor  = "#F8F2CE";
			opts.fcolor  = "#B58435";
			opts.text = $xcp.i18n("step.correct");
		}else if($xcp.task.isAuthorize()){
			opts.bcolor  = "#F4CDF9";
			opts.fcolor  = "#A746AF";
			opts.text = $xcp.i18n("step.auth");
		}else if($xcp.task.isAccounting()){
			opts.bcolor  = "#F8D6CD";
			opts.fcolor  = "#D82D23";
			opts.text = $xcp.i18n("step.jzrgqr");
		}else if($xcp.task.isFinish()){
			opts.bcolor  = "#C6F090";
			opts.fcolor  = "#4F8D00";
			opts.text = $xcp.i18n("step.finish");
		}
		return opts;
		
	}
	//yw,cs,sb,cx 判断是否是参数
	function isParam(){
		var param = $xcp.formPubMgr.defaults.tradeType;
		if(param!=null){
			if(param == 'cs'){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	function isPageType(typeName){
		var type = $xcp.formPubMgr.defaults.tradeType;
		if(type == "BU" && typeName == "transaction"){
			 return true;
		}else if( type == "CS" && typeName == "param"){
			 return true;
		}else if( type == "SB" && typeName == "shengb"){
			 return true;
		}else if( type == "cx" && typeName == "query"){
			 return true;
		} 
		return false;
	}
	
	function isUpdateData(usrDefined){
		var blean = false;
		if($xcp.task.isHandle(usrDefined) || $xcp.task.isCorrect(usrDefined) ){ //只有手工发起的经办
			blean = true;
		}
		return blean;
	}
	
	function isLaunchModeNo(){
		var laMode = $("#butxn_launchModeNo").xcpVal();
		if("1" == laMode){
			return true;
		}
		return false;
	}
	
	function isDoSupend(){
		return "send" == $("#butxn_doSupend").val();
	}
	function isLanunchHandle(){
		//业务编号为空 不是手工发起的交易 并且在经办状态的 交易第一次从任务列表进来执行handle函数
		if( !isDoSupend() && !isLaunchModeNo() && inculeTask("Handle"))
		{
			return true;
		}
		return false;
	}
	
	return {
		//是否 经办
		isHandle : function(usrDefined){
			return inculeTask(Handle,usrDefined);
		},
		//是否复合
		isCheck : function(usrDefined){
			return inculeTask(Check,usrDefined);
		},
		//是否经办更正
		isCorrect : function(usrDefined){
			return inculeTask("Correct",usrDefined);
		},
		//是否授权
		isAuthorize : function(usrDefined){
			return inculeTask("Authorize",usrDefined);
		},
		//是否发报人工确认
		isTransmitters : function(usrDefined){
			return inculeTask("Transmitters",usrDefined);
		},
		//是否记账人工确认
		isAccounting : function(usrDefined){
			return inculeTask("Accounting",usrDefined);
		},
		//是否 流程完成人工确认
		isProcess : function(usrDefined){
			return inculeTask("Process",usrDefined);
		},
		//是经办或者经办更正返回true 判断是否可以执行事件或者修改数据时用
		isUpdateData : function(usrDefined){
			return isUpdateData(usrDefined);
		},
		//业务是否已完成
		isFinish : function(usrDefined){
			return inculeTask("Finish","F");
		},
		//初始化业务状态
		initTaskProperties : function(){
			return initTaskProperties();
		},
		//判断是否是参数
		isParam : function(){
			return isPageType("param");
		},
		//判断是否是交易
		isTransaction : function(){
			return isPageType("transaction");
		},
		//判断是否暂存后的经办
		isMuHandle : function(){
			return inculeTask("MuHandle");
		},
		//判断任务发起方式 手工发起返回true
		isLaunchModeNo : function(){
			return isLaunchModeNo();
		},
		isLanunchHandle : function(){
			return isLanunchHandle();
		},
		//判断是否是暂存后的交易
		isDoSupend : function(){
			return isDoSupend();
		},
		//获取经办的stepNo
		getHandle : function(){
			return getInculeTask(Handle);
		},
		//获取复核的stepNo
		getCheck : function(){
			return getInculeTask(Check);
		},
		//获取经办更正的stepNo
		getCorrect : function(){
			return getInculeTask(Correct);
		},
		//获取授权的stepNo
		getAuthorize : function(){
			return getInculeTask(Authorize);
		},
		//获取记账的stepNo
		getAccounting : function(){
			return getInculeTask(Accounting);
		},
		//获取人工迁移的stepNo
		getProcess : function(){
			return getInculeTask(Process);
		}
	};
}(jQuery);