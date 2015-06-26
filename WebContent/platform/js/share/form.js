/**
 * 表单公共处理（包括页面初始化，提交，授权，复核等）
 */
lpbc.formPubMgr = function($){
	
	var inited       = false;    //内部变量，表单的初始化状态
	var currForm     = null;     //内部变量，表单对象的引用，在实始化init方法中赋值,后续可直接引用 
	var formLoadData = null;     //内部变量，loadData方法中赋值，存储从后台获取的各个表单元素值
	
	/**表单初始化*/
	function init(options){
		
		$.extend(true,$xcp.formPubMgr.defaults, options || {}); 
		
		getForm();

		if(inited) return;  var _this = this; var checkCount = 1000;
		
		$xcp.compMgr.rwTabContent();
		
		top.window.$xcp.stopPorcess();
		
		function cb(){
			if(formLoadData == null){
				if (--checkCount){
					setTimeout(cb, 10);
				}
				return;
			}
			
			inited = true;			

			if($xcp.menuMgr){
				
				//解析表单数据成JS对象形式
				var data =  $xcp.parseJson(formLoadData,true);
				//给表单赋值
				load(data, window.easyui_parse_process_group === true ? false : true);		
				
				preFormInit(); 					
				
				$xcp.compMgr ? $xcp.compMgr.exec() : '';	
				
				afterFormInit(_this,formLoadData); 				
				
				setTimeout(function(){
					$xcp.menuMgr.init($xcp.formPubMgr);
					
					$xcp.leftTreeMgr ? $xcp.leftTreeMgr.init() : "";
					
					$xcp.formPubMgr.backTop();
					
				},100);
			}
		}
		
		cb();
	}
	
	/**获取当前表单对象 = ${#formId}*/
	function getForm(){
		return  currForm = currForm || $("#" + $xcp.formPubMgr.defaults.currFormId);
	}
	
	/**获取表单中所有元素的值(需有name属性和非disabled),isNeedCompData === true 时表示要包括组件值*/
	function getFormData(isNeedCompData){
		if(isNeedCompData === true){
			//先将所有组件的值提交
			$xcp.compMgr.commit();
		}
		return getForm().form('getXcpFormData',true);
	}
	
	/**ajax 方式获取表单数据，进入JSP页面时调用*/
	function ajaxGetData(jsonData,asyncFlag){
		
		if(!jsonData.txnNo){
			formLoadData = {};  return null;
    	}
    	 
    	var urlp =   "common.do?method=loadData";
        
    	var ajaxOpt = 
    	{    		
    		url : urlp,
    		data: jsonData,
    		async : asyncFlag === true ? true : false
    	};
    	
    	function cb(rs){
    		//easyui comobox datas 加入页面缓存中
    		$.each(rs.combobxDatas || [],function(i,o){
    			$xcp.constantMgr.add({key:i,data:o});
    		});    
    		delete rs.combobxDatas;
    		formLoadData = rs;
    		
    	}
    	
    	return $xcp.ajax(ajaxOpt,'object',null,cb);
	}
	
	/**给表单域赋值(内部调用)**/
	function load(data,isEasyuiLoad){
		//获取当前表单对象
		var form = getForm();
		
		if(isEasyuiLoad === true){
			//通过EASYUI 框架给表单赋值
			currForm.form('load',data);
		}else{
			for ( var name in data) {
				var value = data[name], src = $("#" + name);
				
				if(src && src.length > 0){
					//当经办初始化时，如果某个栏位值为空，则不作处理，以免覆盖掉preFormInit方法中的赋默认值
					if(isEasyuiLoad != "reload" && value === "" && $xcp.task.isHandle()) continue;
					src.xcpVal(value);
				}else if(isEasyuiLoad != "reload"){	
					//isEasyuiLoad == "reload",表示不需要增加隐藏栏位,只导入已有的业务栏位
					$("<input type='hidden'/>").attr("name",name).attr("id",name).attr("value",value).appendTo(form);
				}
			}
		}
	}
	
	/**点击交易返回按钮，退出当前交易页面**/
	function formBack(realClose){
	    if(realClose === true){	    	
	    	//关闭打开的窗体
			colseOpenedViewWIns();
			
	        var taskId  = $("#taskId").val();
	        
	    	if (taskId && taskId != '') {
	    		var ajaxOpt = {
	    			url: './common.do?method=back&taskId=' + taskId 			
	    		};
	    		$xcp.ajax(ajaxOpt);
			}
	    }else{
		   top.window.$('body').xcpApp('closeTask',uuidHead,'formBack');
	    }
	} 
	
	/**关闭弹出的层**/
	function colseOpenedViewWIns(){
		$.each($xcp.formPubMgr.defaults.viewOpWin,function(i,o){
			o.close(); 
		});
	}
	
	/**表单加载值之前*/
	function preFormInit(){
		if(document && document.body){
			document.body.onload = null;  //禁止document.body.onload事件处理
	    }
		
		//调用扩展的表单加载之前的逻辑处理
		$.isFunction($xcp.formPubMgr.breFormInit) ? $xcp.formPubMgr.breFormInit.call() : '';
	}
	
	/**表单加载值之后*/
	function afterFormInit(jq,data){
		//调用扩展的表单加载之后的逻辑处理
		$.isFunction($xcp.formPubMgr.aftFormInit) ? $xcp.formPubMgr.aftFormInit.call(this,jq,data) : '';
	}
	
	/**
	 * ajax方式提交表单  opt.submission防止重复提交
	 * alt 是否弹出提示窗口 默认弹出
	 */
	function ajaxSubmit(ajaxUrl,jsonData,opt,afterSubFun){
		//时间段提交验资
		if("notClick" == opt.submission)return;
		
		//重复提交验资
		if(opt.submission != undefined && opt.submission == true) {
			parent.$.messager.alert($xcp.i18n('formSubmit.validate'),$xcp.i18n("formPubMgr.repeatBusinessError"),'info');
			return;
		}
		
		if(opt.submission != undefined ) {
			opt.submission = "notClick";
		}
		
		if(jsonData != null && !$.isEmptyObject(jsonData)){
			
			//ajax json数据提交方式
			var ajaxOpt = 
	    	{
	    		url:ajaxUrl,//请求的url
	    		aync:true,//异步
	    		data:jsonData,
	    		opt1 : opt,
	    		fun : afterSubFun,
	    		error:function(XMLHttpRequest, textStatus, errorThrown){//错误函数
					parent.$.messager.alert($xcp.i18n('submit.error'),textStatus+'\n'+errorThrown,'error');
					if(opt.submission != undefined ) {
						opt.submission = false;
					}
				}
	    	};
			 
	    	$xcp.ajax(ajaxOpt,null,null,formSubmitAfterProcess,formSubmitAfterProcess);
		}else{
			//ajax 表单提交方式
			$('#' + $xcp.formPubMgr.defaults.currFormId).attr('method','post').form('submit',{
				success : function(data){
					data = $xcp.parseJson(data);
					if(data.success == "0"){
						data = data.outEntity;
					}
					formSubmitAfterProcess(data,{fun:afterSubFun,opt1:opt,isAlert:"true"});
				},
				onSubmit: function(){return true;},
				url :ajaxUrl + "&forwardErrorJsp=false"
			});
		}
		
		function formSubmitAfterProcess(data,opts,result){
			if(data != null && data.success =='1'){
				if(opts.opt1.submission != undefined ) {
					opts.opt1.submission = false;
				}
				if(opts.isAlert != undefined){
					$xcp.dispAjaxError(data);
				}
			}else{
				opt.submission = true;
				$xcp.formPubMgr.submitAfterProcess(data,opts,result);
			}
		}
	}
	
	/**交易提交成功后**/
	function submitAfterProcess(data,opts,result){
		//"该笔业处理成功!<br> 业务流水号:{0} 如需要扩展覆盖此函数
		var messStr = $xcp.i18n("formSubmit.tipMsg") + "<br>";
		messStr += $xcp.task.isTransaction() ? $xcp.i18n("formPubMgr.doTran").format(data.currentBizNo) : "";
		
		$.messager.alert($xcp.i18n('tip'), messStr ,"",function(){
			if($.isFunction(opts.fun)){
				opts.fun(opts.opt1,data);
			}
		});
	}
	
	/**表单提交部分   需要整理**/
	/**
	 * 插件报文等控件js函数提交
	 */
	function validate(){
		if(getForm().form('validate') == false)return false;
		
		if($xcp.compMgr.componentValidate() == false)return false;
		
		if(!$xcp.compMgr.commit())return false;
		return true;
	}
	
	/**提供给表单提交函数**/
	function commit(ajaxUrl,jsonData,opt,afterSubFun){
		ajaxSubmit(ajaxUrl,jsonData,opt,afterSubFun);
	}
	
	/**easyui组件检查错误**/
	function validatEasyuiError(obj,meaggs){
		//默认不做处理 由系统去继承实现
		//$xcp.validErrorMes.add(obj,meaggs);
	}
	
	/**表单提交部分 end**/
	return {//TODO
		init : function(options){
			init(options);	
		},
		
		//公共表单默认初始值，可根据具体交易注入部分或全部参数
		defaults : {
			url               : null,
			launchModeNo      : ''  ,         //发起方式
			currFormId        : null,         //需要处理的FORM的ID名称
			dataEventFun      : null,         //laod数据之前的标签赋值操作 如combobox函数赋值等
			dataChangeEventFun: null,         //laod数据之后的操作
			bindEventFun      : null,         //绑定事件函数
			checkBridging     : false,        //复核时是否需要双敲
			isbackCallout     : "N",          //复核时是否需要批注
			taskFlag          : taskFlag,	  //取request中的值
			viewOpWin		  : [],
			handle            : {clfun:function(){},callback:null,urlMethod:'handle'},
			doHandle          : {clfun:function(){},callback:null,urlMethod:'doHandle',submission:false,subCycleFun:function(){}},
			doSuspend         : {clfun:function(){},callback:null,urlMethod:'doSuspend',url:'./common.do',submission:false},  //挂起
			check             : {clfun:function(){},callback:null,urlMethod:'check'},
			doCheck           : {clfun:function(){},callback:null,urlMethod:'doCheck',submission:false},
			correct           : {clfun:function(){},callback:null,urlMethod:'correct'},
			doCorrect         : {clfun:function(){},callback:null,urlMethod:'doCorrect',submission:false},  //经办更正后的提交
			doDelete          : {clfun:function(){},callback:null,urlMethod:'doDelete',submission:false},   //取消任务
			doAuth            : {clfun:function(){},callback:null,urlMethod:'doAuth'},
			doProcess         : {clfun:function(){},callback:null,urlMethod:'processFin',url:'./common.do'},  //  
			doAccounting      : {clfun:function(){},callback:null,urlMethod:'affirmAcct',url:'./common.do'},  // 重新记账
			doAThrough        : {clfun:function(){},callback:null,urlMethod:'affirmAcct',url:'./common.do'},  // 记账通过
			doAFBManagers     : {clfun:function(){},callback:null,urlMethod:'affirmAcct',url:'./common.do'}   // 打回经办
		},
		
		//返回/退出当前表单界面
		formBack : function(realClose){
			formBack(realClose);
		},
		
		//关闭交易中已打开的窗口
		colseOpenedViewWIns : function(){
			
		},
		//扩展defaults
		setDefaultsFormPubMgr : function(options){
			$.extend(this.defaults, options || {}); 
		},
		//获取后台数据
		ajaxGetData : function(jsonData,asyncFlag){
			ajaxGetData(jsonData,asyncFlag);
		},
		//获取大字段某个值,传入值key
		getFormLoadData : function(key){
			return key ? (formLoadData != null ? formLoadData[key] : undefined) : formLoadData;
		},
		//获取表单数据
		getFormData : function(isNeedCompData){
			return getFormData(isNeedCompData);
		},
		//公用表单的ajax提交 和后台交互中心
		commit : function(ajaxUrl,jsonData,opt,afterSubFun){
			commit(ajaxUrl,jsonData,opt,afterSubFun);
		},
		//公用表单验证 包括页面元素的校验和组件的校验
		validate : function(){
			return validate();
		},
		//交易提交后默认弹窗处理
		submitAfterProcess : function(data,opts,result){
			submitAfterProcess(data,opts,result);
		},
		validatEasyuiError : function(obj,meaggs){
			validatEasyuiError(obj,meaggs);
		},
		getForm : function(){
			return getForm();
		}
	};
}(jQuery);

//$(function(){
	//在经办，复核，授权，浏览等环节(异步/同步)获取表单初始化数据
	var loadFormMgrdata = $xcp.parseJson(xcp_sys_formInitData,true);
	$xcp.formPubMgr.ajaxGetData(loadFormMgrdata,false);
//});
