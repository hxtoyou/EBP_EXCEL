/**
 * 报文前端处理js
 * ***/
$xcp.PacLoad = function($){
	var  savaHtmlObj = {},hideOrDel = "1",inited = false, funlsPack = {} ;//hideOrDel : 1是隐藏 2是删除
	var fireOutFuns = {};
	/**报文值保存域**/
	var messageInfoList = null;
	//处理循环域del事件
	function con_DelBind(listDId,listDId_NOT0,_this){
			 /**sum减一 */
			 $(listDId+"_SUM").val(parseInt($(listDId+"_SUM").val())-1);
			 //删掉层
			 $(_this).parent().remove();
			 /**做循环添加判断*/
			 $(listDId+"_ADD").find("div[id ^= '"+listDId_NOT0+"_CON']").each(function(i){
				var hostCON = $(this).attr("id").replace(listDId_NOT0,"");//本身的ID
				var newIDV = $(this).attr("id").replace(new RegExp(hostCON, 'g'),"_CON"+(i+2));
				var connum = "_CON"+(i+2);
				$(this).attr("id",newIDV);$(this).attr("name",newIDV);
				/**改变con* div的属性值*/
				$(this).find("div[id]").each(function(i,o){
					var str = $(o).attr("id").replace(hostCON,connum);
					$(o).attr("id",str); 
				});
				 $(this).find(":input,:textarea").each(function(){
					  var el = this, atts = el.attributes, len = atts.length, att, i =0 ; 
					  for(; i < len ; i++){  
						  att = atts[i];    
						  if(att.specified)
						  {
							  if(att.value.indexOf("_CON") != -1){
								  att.value = att.value.replace(hostCON,connum);
							  }
						  }
					 }
				  });   
			  });
	};
	
	/**循环域初始化操作  */
	function cyclePac(classPacObj){
		 if(classPacObj == undefined){
			 return ;
		 }
		 
		 classPacObj.each(function(){
			 
			 /**获取 div id 分解成 其他标签关联的标识*/
			 var listDId = "#"+$(this).attr("id").replace("DIV_",""),listDId_NOT0 = listDId.substring(1,listDId.length);
			 var lDId_DEFAULT  = null;
			 
			 /**循环添加的文本  add事件添加的内容  lDId_DEFAULT*/
			 if( savaHtmlObj[listDId_NOT0] == null){
				 lDId_DEFAULT = $(this).find("[id = '"+listDId_NOT0+"_DEFAULT']").html();
			 }else{
				 lDId_DEFAULT =  savaHtmlObj[listDId_NOT0];
			 }
			 
			 /** 原件删除按钮删除*/
			 $("#DEL_" + listDId_NOT0 + "_CON1").remove();
			 
			 //给经办更正的时候原有del添加事件  要判断是否是经办
			 $(listDId+"_ADD").find("[id^='DEL_" + listDId_NOT0 + "_CON']").bind("click",function(){
			 		/**删除按钮绑定具体的事件处理*/
			 		con_DelBind(listDId,listDId_NOT0,this);
			 	});
			 
			 $("#ADD_"+listDId_NOT0).bind("click."+listDId_NOT0,function(){
				 var listDid_SUM = $(listDId+"_SUM");
				 /**获取点击事件时sum 值*/
				 var listSum = parseInt(listDid_SUM.val())+2;
				 /**替换原文本的con* 值  根据 sum值赋*/
				 $(listDId+"_ADD").append(lDId_DEFAULT.replace(new RegExp(listDId_NOT0+'_CON1', 'g'),listDId_NOT0+"_CON"+listSum));
				 /**给del添加事件*/
				 var delId = listDId_NOT0+"_CON"+listSum;
				 $("#"+delId).find("[id='DEL_" + delId+"']")
				 	.bind("click",function(){
				 		/**删除按钮绑定具体的事件处理*/
				 		con_DelBind(listDId,listDId_NOT0,this);
				 	});
				 
				 var lsitDidObj = $(listDId+"_ADD").find("[id = '" + delId + "' ]");
				  
				 PacLoadFUN(lsitDidObj); // 添加事件
				 $.parser.parse(lsitDidObj);
				 
				 /**增加sum字段值*/
				 listDid_SUM.val(parseInt(listDid_SUM.val())+1);
			 });
		 });
		 
	 };
	 
	 /**
	 * 复合域初始化操作    删除循环域的add和del
	 */
	 function initializationPac(classPacObj,pactetsData){
		 if(classPacObj == undefined){
			 return ;
		 }
		classPacObj.each(function(){
			 var listDId = "#"+$(this).attr("id").replace("DIV_",""),listDId_NOT0 = listDId.substring(1,listDId.length);
			  /** 原件删除按钮删除*/
			 // 复合的时候需要删除按钮 要判断是否是复合
			 if(!$xcp.task.isUpdateData()){ 
				 $("#DEL_" + listDId_NOT0 + "_CON1").remove();
				 $("#ADD_"+listDId_NOT0).remove();
			 }
			 /**循环添加的文本  add事件添加的内容  lDId_DEFAULT*/
			 if( savaHtmlObj[listDId_NOT0] == null){
				 lDId_DEFAULT = $(this).find("[id = '"+listDId_NOT0+"_DEFAULT']").html();
			 }else{
				 lDId_DEFAULT =  savaHtmlObj[listDId_NOT0];
			 }
			 var listSum = 0;
			 if(typeof pactetsData != "undefined"){
				 listSum = parseInt(pactetsData[listDId_NOT0+"_SUM"])+2;
			 }
			var listDId_Add = $(listDId+"_ADD");
			 for(var i = 2; i< parseInt(listSum); i++ ){
				 listDId_Add.append(lDId_DEFAULT.replace(new RegExp('_CON1', 'g'),"_CON"+i));
			 }
		 });
	};
	
	/**选择域初始化操作 添加给隐藏域赋值暂时不处理 先屏蔽*/
	function selectFunPac(classPacObj){
		 if(classPacObj == undefined){
			 return ;
		 }
		 
		 classPacObj.each(function(){
//			 var ppList = $(this).find("select[id = '"+$(this).attr("id").replace("DIV_","")+"' ]");
			 var ppList = $("#" + $(this).attr("id").replace("DIV_",""));
			 if(ppList.length == 0 ) return;
			 ppList.bind("change.fixed" + $xcp.PacLoad.defaults.classPacSelect,function(){
	 			selectEventClickFun(this);
		 	 });
		 	 //默认显示报文选择域子项MOP验证 如果不可见则把M样式变化成M_Pac_validate
//			 $(this).trigger("change.fixed" + $xcp.PacLoad.defaults.classPacSelect);
		 });
	};
	
	/**报文选择域点击触发事件**/
	function selectEventClickFun(_this){
		 var thPacID = $(this).attr("id");
	 	 var thPacSel = $("#" + thPacID + "_" + $(_this).xcpVal());
		 
		 //除选中div之外的div 含有M class的转换成 M_Pac_validate
		 $("div[id ^='" + thPacID + "'][id !='" + thPacID+"']",thPacSel.parent())
		  .hide().find(".M[id][name]").addClass("M_Pac_validate").removeClass("M");
		  
		  //选中的div M_Pac_validate 转换成M 做非空验证
		 thPacSel.show().find(".M_Pac_validate[id][name]")
		  .addClass("M").removeClass("M_Pac_validate");
	}
	
	function PacLoadFUN(areaJquery){
		//复合    选择     初始化    参数 为以class变量选择的jquery对象  复合域 验证 easyui 已支持非空验证
		//initializationPac($($xcp.PacLoad.defaults.classPacReview));
		
		selectFunPac(areaJquery == null ? $($xcp.PacLoad.defaults.classPacSelect) : areaJquery.find($xcp.PacLoad.defaults.classPacSelect));
		
		//循环域
		cyclePac(areaJquery == null ? $($xcp.PacLoad.defaults.classPacSelect) : areaJquery.find($xcp.PacLoad.defaults.classPacCycle));
	};
	
	function getPacketsSel(isYN){
		var tDiv = $("#"+$xcp.PacLoad.defaults.tableDivId);
		if(tDiv.length  == 0)return "";
		
		compInit();
		isYN = isYN || "Y";
		var inifoList = [];
		var tabData = tDiv.datagrid("getData");
		if(tabData == undefined){
			 return "";
		}
		var onJso = {};
		
		$.each(tabData.rows,function(i,o){
			var pac = $("input[name='packInt"+o.pacName+"']:checked");
			 
			if(isYN == "Y" && o.isHidden == true)return ;
			var pacVal  =  o.isHidden == true ? "N" : pac.val();
			if(pacVal == isYN){
				onJso = {};
				onJso.msgType = o.pacName;
				onJso.sendType = pac.attr("sendType") == "1"?"1":"0";
				onJso.pacDepict = o.pacDepict;
				onJso.pacDel = o.pacDel;
				onJso.swifCode = o.swifCode;
				onJso.countryCode = o.countryCode;
				inifoList.push(onJso);
			}
		});
		 
		return inifoList;
	};
	
	/**
	 * 提交时间报文隐藏字段messageInfoList 赋值
	 */
	function submitPackets(){
		if($("#" + $xcp.PacLoad.defaults.divPacketsId).length == 0 )return true;
		
		messageInfoList = $xcp.def.createXcpHidEvent($xcp.PacLoad.defaults.messageInfoList);
		var unSenStr  = $xcp.def.createXcpHidEvent($xcp.PacLoad.defaults.unSendMsgList);
		var operMsgList  = $xcp.def.createXcpHidEvent($xcp.PacLoad.defaults.operMsgList);
		
		//删除多余报文数据，不发送的报文的页面元素
		var notSendList = getPacketsSel("N");
		$.each(notSendList,function(i,o){
			loadPackets(o.msgType,"N","true");
		});
		
		unSenStr.val($xcp.toJson(notSendList));
		messageInfoList.val($xcp.toJson(getPacketsSel()));
		operMsgList.val($xcp.toJson(getOperValList()));
		
		return false;
	};
	
	/**
	 * 是否发送报文
	 */
	function loadPackets(pacName,isY,_del,isD,numOder){
		 var pacObj = $("#"+pacName+"_1"); 
//		 if(pacObj.length > 0){
		 if($("#packetsLs"+pacName).length){
			 if(isY == "Y"){
				 pacObj.attr("packetsType","Y").show()
				 	.find($xcp.PacLoad.defaults.classPackDocument).attr("packetsVa","Y");
				 $("#"+$xcp.PacLoad.defaults.divPacketsId).tabs("enableTab",pacName);
				 $("#"+$xcp.PacLoad.defaults.divPacketsId).tabs("select",pacName);
			 }else{
				 if(hideOrDel == "1" &&  _del != true && _del != "true" ){
					 pacObj.attr("packetsType","N").hide()
					 	.find($xcp.PacLoad.defaults.classPackDocument).attr("packetsVa","N");
					 $("#"+$xcp.PacLoad.defaults.divPacketsId).tabs("disableTab",pacName);
				 }else{
					 pacObj.remove();
					 $("#"+$xcp.PacLoad.defaults.divPacketsId).tabs("close",pacName);
				 }
			 }
		 }else {
			 if(isY == "Y"){
				importPacketsFrame(pacName,isD,numOder);
			 }else{
				 $("#"+$xcp.PacLoad.defaults.divPacketsId).tabs("close",pacName);
			 }
		 }
	};
	
	function packValidate(packObj){
		var objClas = ".lsm";
		 
		var objPack = packObj.parents("div"+objClas); 
		 
		objPack = objPack && objPack.length > 1 ? objPack.eq(0) : objPack;
		 
		if(objPack.hasClass($xcp.PacLoad.defaults.classPacCycle)  && packObj.hasClass("O")){
			return resolvePackLOOP(objPack);
		}else if(objPack.hasClass("O")){
			
			return resolvePackCOMPOSITE(objPack);
		}
		
		//递归查找上级是否有 
		if(objPack.parents("div"+objClas).length > 0){
			return packValidate(objPack);
		} 
		return true;
	}
	
	/**
	 * 循环域判断是否必填
	 */
	function resolvePackLOOP(packObj){
		var blean = false;
		var strSUM = packObj.attr("id").replace("DIV_","")+"_SUM";
		if(packObj.find("#"+strSUM).val() > 0){
			return true;
		}
		 
		var strObj = packObj.find($xcp.PacLoad.defaults.classPackDocument);
		strObj.each(function(i,k){
			 if($.trim($(k).xcpVal()) != "" && $(k).attr("type") != "hidden" ){
				 blean = true;
				 return false;
			 }
		});
		return blean;
	}
	
	/**
	 * 循环域判断是否必填
	 */
	function resolvePackCOMPOSITE(packObj){
		var blean = false;
		var strObj = packObj.find($xcp.PacLoad.defaults.classPackDocument);
		
		strObj.each(function(i,k){
			 if($.trim($(k).val()) != "" && $(k).attr("type") != "hidden" ){
				 blean = true;
				 return false;
			 }
		});
		
		return blean;
	}
	
	/**
	 * 验证Receiver中内容是否密押
	 */
	function ReceiverDbCli(_this){
		var pacName1 = "";
		if(_this && $(_this).length > 0 && $(_this).attr("id") != undefined && $(_this).attr("id").indexOf("_Receiver") != -1 ){
			pacName1 = $(_this).attr("id").replace("_Receiver","");
		}
		var str = $("#"+pacName1+"_Receiver").xcpVal();
		var tabsTarge = $("#"+$xcp.PacLoad.defaults.divPacketsId).tabs("getTab",pacName1);//lsm
		if(!tabsTarge){
			return ;
		}
		var tabTitle  = tabsTarge.panel('options').tab;
		if(str.length == 8 || str.length == 11){
			var obj = $xcp.formPubMgr.vaTelegraph(str,pacName1);
			if(obj != null){
				if(obj.hasTestKey ==  "false"){ 
					 tabTitle.attr("title",$xcp.i18n("packets.receiver").format(pacName1)).find(".tabs-icon").addClass("packeTitle");
					 var wid = tabTitle.find(".tabs-title").width() > 0 ? tabTitle.find(".tabs-title").width() : 42;
					 tabTitle.find("a").css({width:wid + 35});
	    		}else{
	    			if(tabTitle.find(".tabs-icon").hasClass("packeTitle")){
	    			   tabTitle.attr("title","").find(".tabs-icon").removeClass("packeTitle");
		    		   tabTitle.find("a").css({width:tabTitle.find("a").width() - 35});
	    			}
	    		}
			}
		}else{
			if(tabTitle.find(".tabs-icon").hasClass("packeTitle")){
				tabTitle.find(".tabs-icon").removeClass("packeTitle");
    			tabTitle.find("a").css({width:tabTitle.find("a").width() - 35});
			}	
		}
	}
	
	function trigDoLoadChange(){
		if($xcp.chrMgr){
//			doLoadCharge();
		}
		fireChangeEvent();
	}
	
	/** //TODO 
	 * 加载报文子页面
	 */
	function importPacketsFrame(pacName,isD,numOder){
		$xcp.formPubMgr.asyncInitFun("add","$xcp.PacLoad.init"); 
		$('#'+$xcp.PacLoad.defaults.divPacketsId).tabs('add',{
			title:pacName,
			content:"<DIV ID='packetsLs"+pacName+"'></DIV>",
			closable:false
		});
		
		var ajaxOpt = 
    	{    		
    		url : "./common.do?method=assembleUi&msgType="+pacName,
		    //url : "MT799.html",
    		data :  {butxn_tranOrgNo:$("#butxn_tranOrgNo").val()||"",
    			"swiftVersion" 	: $("#swiftVersion").xcpVal(),
    			"fmtVersion" 	: $("#fmtVersion").xcpVal(),
    			"rtgsVersion" 	: $("#rtgsVersion").xcpVal(),
    			"ediVersion" 	: $("#ediVersion").xcpVal()
    		},
    		async : true,
    		dataType : "html",
    		//错误函数
			error : function(XMLHttpRequest, textStatus, errorThrown){
				alert(errorThrown);
			}
    	};
		function cb(rs,opt,result){
			var packetsLsPacName = $("#packetsLs" + pacName);
			packetsLsPacName.html(result);
			
			//添加一个选项卡面板
			var pactetsData = $xcp.parseJson($xcp.formPubMgr.getPacketsMessOut()||{})[pacName];
			 
			var pacNameObj_1 = $("#" + pacName + "_1");
			pacNameObj_1.attr("packetsType","Y");
			
			//加载已发送报文时候选择域显示值 显示多个循环域的值 复合不添加事件
			if(isSend(pacName)){
				initializationPac(pacNameObj_1.find($xcp.PacLoad.defaults.classPacCycle),pactetsData);
			}
			
			//给所有报文标签加上标识
			pacNameObj_1.find($xcp.PacLoad.defaults.classPackDocument).attr("packetsVa","Y");
			
			//经办需要添加事件和显示值
			if($xcp.task.isUpdateData()){//是否 复合
				PacLoadFUN(pacNameObj_1); // 添加事件
			}else{
				$.each($($xcp.PacLoad.defaults.classPacSelect,pacNameObj_1),function(i,o){
					 selectEventClickFun($("#" + $(o).attr("id").replace("DIV_","")));
				});
			}
			
			//绑定报文自定义事件 && pactetsData == undefined
			if($xcp.task.isUpdateData()){
				var pacNa = pacName,stu = null,spList = pacName.split("_");
				
				if($.isFunction(window["bindMsgFldEvent_"+pacNa])){
					
				}else if(funlsPack[spList[0]] != undefined && spList.length > 1 && spList[1] == "c"){
					pacNa = spList[0];
					stu = spList[1] + "_" + spList[2];
				}else if(spList.length > 1){
					pacNa = spList[0];
					stu = spList[1];
				}
				
				//报文绑定事件处理 初始化不绑定事件  报文加载成功后才加载事件
				$.isFunction(window["bindMsgFldEvent_" + pacNa]) ? window["bindMsgFldEvent_"+pacNa].call(null,stu) : '';
			}
			
			$.parser.parse(pacNameObj_1);
			
			//报文模板默认赋值 
			var def = $xcp.PacLoad.defaults.defaultsPackData;
			def[pacName] ? FunBindClauseSelect(def[pacName].clause,def[pacName].prefix) : "";
			
			if(pactetsData != undefined && isD != "N"){
				if(pactetsData[pacName + "_message"]){
					loadReeMessage(pacName, pactetsData[pacName + "_message"]);
				}
				pacNameObj_1.form('load',pactetsData);
			}
			
			//报文按钮处理
			packetButFun(pacName,packetsLsPacName);
			
			if(!$xcp.task.isUpdateData()){//不是经办和经办更正
				$xcp.formPubMgr.readonlyAll(true,packetsLsPacName);
			}
			
			//加上M O P 样式
			/*$xcp.formPubMgr.loadElementClassP($("#packetsLs"+pacName));
			
			if(!$xcp.task.isMuHandle()){
				$(".validatebox-invalid",pacName_1).removeClass("validatebox-invalid");
			}*/
			
			//做动态关联M O  样式
			if($xcp.task.isUpdateData()){
				//给报文标签加上键盘事件
				$xcp.formPubMgr.swiftTextareaEventInit(packetsLsPacName);
				
				alterMOptional(pacNameObj_1);
				//报文加载成功后调用业务传递函数
				$.isFunction($xcp.PacLoad.defaults.sendSuccessFunRegs[pacName]) ? $xcp.PacLoad.defaults.sendSuccessFunRegs[pacName].call():"";
				
				//报文加载成功后调用业务传递函数
				$.isFunction(window["packetsSuccess_" + pacName]) ? window["packetsSuccess_" + pacName].call():"";
				
				//发送行增加change事件 (验证是否有密押 提示自动转成999 并在页签做样式控制)
				receiverBindChange(pacName);
			}
			
			if(numOder == -1){
				$xcp.formPubMgr.asyncInitFun("remove","$xcp.PacLoad.init");
			}
    	}
		
		function receiverBindChange(pacName){
			var obj = $("#"+pacName+"_Receiver");
			obj.bind("change.fixed",function(){
				ReceiverDbCli(this);
				trigDoLoadChange();
			});
			/*if($(obj).xcpVal() != ''){
				ReceiverDbCli($(obj));
				trigDoLoadChange();
			}*/
			$xcp.formPubMgr.addBlentClas("P", pacName+"_Sender");
		}
		
		function cw(result){
			if(result != "" && result.length > 9 && result.substring(0,9) == "errorCode"){
				//错误信息
				var errorMsg = result.substring(result.indexOf("|")+1,result.length);
				//错误码
				//var errorCode = result.substring(result.indexOf("=")+1,result.indexOf("|"));
				$("#packetsLs"+pacName).html(errorMsg.length > 9 ? errorMsg.substring(9,errorMsg.length) : "");
			}
			if(numOder == -1){
				$xcp.formPubMgr.asyncInitFun("remove","$xcp.PacLoad.init");
			}
		}
		return $xcp.ajax(ajaxOpt,null,null,cb,cw);
	};
	
	function showIncomePackets(){
		
		var messageWaitPakctes = $("#messageWaitPakctes").val();
		if(!messageWaitPakctes){
			messageWaitPakctes = "";
		}
		var ajaxUrl = "./preview.do?method=getMsg&preview=2&messageWaitPakctes="+messageWaitPakctes +"&txnNo="+$("#txnNo").val();
		$.ajax({
			   url   : ajaxUrl,
			   data :  {
	    			"swiftVersion" 	: $("#swiftVersion").xcpVal(),
	    			"fmtVersion" 	: $("#fmtVersion").xcpVal(),
	    			"rtgsVersion" 	: $("#rtgsVersion").xcpVal(),
	    			"ediVersion" 	: $("#ediVersion").xcpVal()
	    		},
	    		dataType : "html",
	    		success : function (result){
	    			var errorCode = resolveErrorCode(result);
					 
					if(errorCode != "" && errorCode.length >= 7 && errorCode.substring(0,4)== "EBP-"){
						//错误信息 2 为session 超时错误
						errorCode = errorCode.substring(4,7);
						if($xcp.errorOpera[errorCode]){
							$.isFunction($xcp.errorOpera[errorCode])? $xcp.errorOpera[errorCode]() : null;
							return ;
						}
					}
					if(result == null)return ;
					
					result = decodeURIComponent(result);
					result = result.replace(new RegExp("_sp_", 'g'), " ");
					result = $xcp.parseJson(result);
					
					if(result.success  == "1"){
						$xcp.dispAjaxError(result); //显示异常错误
					}else{
		    			if($.isEmptyObject(result.outEntity)){
		    				$.messager.alert($xcp.i18n("paramMar.tisxx"),$xcp.i18n("packets.meiybwkyl"));
		    				 return false;
		    			}
		    			var html = resolvePacketsJson(result.outEntity);
		    			if(html != ""){
		    				$xcp.formPubMgr.formAddTabs(html,$xcp.i18n("formPubMgr.incomeMessages"));
		    			}
		    			return html;
	    			}
	    		}
		});
		return "";
	}
	
	function resolveErrorCode(result){
		var errorCode = "";
		if(result.indexOf("success") != -1 && result.indexOf("success") != -1){
			errorCode = result.substring(result.indexOf("errorCode")+12,result.indexOf("errorCode")+19);
		}
		errorCode = errorCode||"";
		return errorCode;
	}

	/**
	 * 预览报文
	 */
	function showPacketsData(){
		 var html = "";
		 var packetsName = "";
		
		 var preview = "1";
		 if(!$xcp.task.isUpdateData()){
			if(messageInfoList.length > 0 && $.trim(messageInfoList.val()) != ""){
				packetsName = $xcp.parseJson(messageInfoList.val()) || "";
			};
			preview ="0";
		 }else{
			packetsName = getPacketsSel();
		 }
		 
		 if(packetsName == ""){
			 $.messager.alert($xcp.i18n("paramMar.tisxx"),$xcp.i18n("packets.meiybwkyl"));
			 return false;
		 } 
		 
		 var convertN99Flag = configOveralObj["packets"].sendTypeFlag;
		 //-- start
		 var ajaxUrl = "./preview.do?method=getMsg&preview="+preview+"&messageInfoList="+$xcp.toJson(packetsName)+"&taskState="+taskState+"&cFlag="+convertN99Flag;
		 
		$('#' + $xcp.formPubMgr.defaults.currFormId).attr('method','post');
		//ajax 表单提交方式
		$('#' + $xcp.formPubMgr.defaults.currFormId).form('submit',{
			success : function(data){
				//data = data.replace(new RegExp("\\\"", 'g'),"");
				data = decodeURIComponent(data);
				data = data.replace(new RegExp("_sp_", 'g'), " ");
				data = $xcp.parseJson(data);
				html = resolvePacketsJson(data.outEntity);
				
				if(html != ""){
					if($xcp.getUserRptMtd() == "1" ){
						var optObj = {"type":"msg","packetsName": getPacketsSel(),"preview":preview};
						var formData = {"formData": $xcp.formPubMgr.getFormData($xcp.formPubMgr.defaults.currFormId)};
						optObj = $.extend(formData,optObj);
						$xcp.formPubMgr.formAddTabs(html,$xcp.i18n("formPubMgr.viewMessages"),null,null,null,optObj);
					}else{
						$xcp.formPubMgr.formAddTabs(html,$xcp.i18n("formPubMgr.viewMessages"));
					}
					
				};
			},
			onSubmit: function(){
				return true;
			},
			url :ajaxUrl
		});
		 //-- end
		return html ;
	};
	
	
	function packExpoertSubmit(preview, msgId){
		 var ajaxUrl = "";
		 $xcp.formPubMgr.formAddTabs("<iframe id=\"packetPrint\" src=\"\" frameborder='0'  style='width:100%;height:100%;overflow-x:hidden;overflow-y:auto'></iframe>",$xcp.i18n("packets.showprint"),null,null,null,{"type":"msg"});
		if(msgId && msgId != '' && "undefined" != msgId){
			ajaxUrl = $xcp.def.getFullUrl("preview.do?method=receiveMsgPrint&msgId="+msgId);
		} else {
			var packetsName = getPacketsSel();
			
			ajaxUrl = "./preview.do?method=printPackets&printMsg="+preview+"&messageInfoList="+$xcp.toJson(packetsName)+"&taskState="+taskState;
			if($xcp.task.isCheck()){
				ajaxUrl += "&preview=1&txnNo="+$("#txnNo").xcpVal();
			}
		}
		$('#' + $xcp.formPubMgr.defaults.currFormId).attr('method','post');
		$('#' + $xcp.formPubMgr.defaults.currFormId).attr('target','packetPrint');
		$('#' + $xcp.formPubMgr.defaults.currFormId).attr('action',ajaxUrl);
		$('#' + $xcp.formPubMgr.defaults.currFormId).submit();
		/*//ajax 表单提交方式
		$('#' + $xcp.formPubMgr.defaults.currFormId).form('submit',{
			success : function(data){
				if(data != ""){
					
				};
			},
			onSubmit: function(){
				return true;
			},
			url :ajaxUrl
		});*/
		$('#' + $xcp.formPubMgr.defaults.currFormId).attr('action','');
		$('#' + $xcp.formPubMgr.defaults.currFormId).attr('target','');
	}
	/**
	 *报文预览返回结果解析
	 */  
	function resolvePacketsJson(htmlJson){
		var html = '<div class="mbox"><div class="easyui-tabs"  border="false">';
		if(htmlJson == undefined || typeof htmlJson != "object"){
		    return ;
		}
		var _854 = "&nbsp;&nbsp;: &nbsp;";
		$.each(htmlJson,function(i,o){//循环得到每个报文
			html += "<div title='"+i+"' class='packetsDiv'>";
			if(typeof o == "object"){
				if(o.edi == undefined ){
					html += '<span class="packets_resol"  onClick="$xcp.PacLoad.resolveFunSpan(\''+i+'\')" title="'+$xcp.i18n("packets.hidden")+'"></span>';
				}
				 
				if($xcp.toJson(o.error) == "{}" ){
					
					//如果查看报文的方式是弹出单独的WINDOW
					var btnExpt = ' onClick="$xcp.PacLoad.packExpoertSubmit(\''+o.sourceMsgType+'\', \''+o[i+'_msgId']+'\')"';
					var msgTyp = "";
					if($xcp.getUserRptMtd() == "1" ){
						btnExpt = '';
						msgTyp = "_"+o.sourceMsgType;
					}
					html += '<span class="packets_export" id="packets_export' + msgTyp + '"' + btnExpt+'></span>';
				}
				html += "<table class='etable tabMouseCls' id='"+i+"_resolveSpan1'>";
				
				var tagId = o["sourceMsgType"] || "";
				if(o.error != undefined && typeof o.error == "object"){
					$.each(o.error,function(n,m){
						html += "<tr class='errorPackets' ondblclick=$xcp.validErrorMes.submitVadatError($(\"[id^='" + tagId +"_"+n + "']\").eq(0),this);><th class='pacResoTh' >"+n+_854+"</th>";
						html += "<td width='80%' >" + m + "</td>"; 
						html += "</tr>"; //<td width='30%'></td>
					});
					delete o.error; 
				}
				if(o.edi != undefined ){
					html += "<tr ><th class='pacResoTh'>edi"+_854+"</th>";
					//html += "<td width='50%'><pre>" + o.edi.replace(/&/g, "&amp;").replace(/</g, "&lt;") + "</pre></td>"; 
					/*var mess = decodeURIComponent(o.edi);
					mess = mess.replace(new RegExp("_sp_", 'g'), " ");*/
					var mess = o.edi;
					
					html += "<td width='80%'><pre style=\"FONT-FAMILY: '宋体', 'Verdana', 'Arial', 'Helvetica'; FONT-SIZE: 14px;\">" + mess.replace(/&/g, "&amp;").replace(/</g, "&lt;") + "</pre></td>"; 
					html += "</tr>"; 
					delete o.edi; 
				}
				if(o.Sender != undefined  ){
					html += "<tr class='errorPacketsBank'><th class='pacResoTh'>Sender"+_854+"</th>";
					html += "<td width='80%'>" + o.Sender + "</td>"; 
					html += "</tr>"; 
					delete o.Sender; 
				}
				if(o.Receiver != undefined  ){
					html += "<tr class='errorPacketsBank'><th class='pacResoTh'>Receiver"+_854+"</th>";
					html += "<td width='80%'>" + o.Receiver + "</td>"; 
					html += "</tr>"; 
					delete o.Receiver;
				}
				if(o.tags != undefined  ){
					$.each(o.tags,function(j,k){
						html += "<tr><th class='pacResoTh' title ='"+(k.desc||"")+"'>"+k.tag+_854+"</th>";
						if(typeof k == "object"){
							if(k.value != undefined){
								html += "<td width='80%' ><pre>" + k.value + "</pre></td>"; 
							}
							if(k.desc != undefined){
								//html += "<td width='30%'>" + "" + "</td>"; //k.desc
							}
						}else {
							html += "<td width='80%'>" + k + "</td>"; 
							html += ""; 
						}
						html += "</tr>";
					});
				}
				
				//o.message = o.message.replace(new RegExp("</br>", 'g'),"\r\n");
				//o.message = o.message.replace(new RegExp("&nbsp;", 'g')," "); 
				if(o.message != undefined ){
					html += "</table><table class='etable' id='"+i+"_resolveSpan2' style='display: none;'>";
					html += "<tr class='errorPackets'><th width='20%'></th>";//message"+_854+"
					html += "<td width='50%' ><pre style=\"FONT-FAMILY: '宋体', 'Verdana', 'Arial', 'Helvetica'; FONT-SIZE: 14px;\">"+o.message+"</pre></td>"; 
					html += "<td width='30%'></td></tr>"; 
					
					delete o.message; 
				}
				html += "</table>";
			}
			html += "</div>";
		});
		html += "</div></div>";
		
		return html;
	};
	
	function resolveFunSpan(objPackName){
		var blean = $("#"+(objPackName||"")+"_resolveSpan1").is(":hidden");
	    
		if(blean){
			$("#"+objPackName+"_resolveSpan1").show();
			$("#"+objPackName+"_resolveSpan2").hide();
		}else{
			$("#"+objPackName+"_resolveSpan1").hide();
			$("#"+objPackName+"_resolveSpan2").show();
		}
	}
	function createTabPac(){
		if($("#"+$xcp.PacLoad.defaults.tableDivId).length > 0){
			return ;
		}
		//加载tab html 
		var pacDiv = $("#"+$xcp.PacLoad.defaults.divPacketsId);
		if(pacDiv.length > 0){
			var tabHtml = '<div class="mbox"><div id="'+$xcp.PacLoad.defaults.tableDivId+'" ></div></div>';
			pacDiv.parent().before(tabHtml);
		}
	}
	
	/**
	 * 加载报文页签
	 */
	function init(){
		$.parser.parse($("#" + $xcp.PacLoad.defaults.divPacketsId).addClass("easyui-tabs").attr("border",false).parent());
		
		//加载表格	
		messageInfoList = $("#" + $xcp.PacLoad.defaults.messageInfoList);
		if(messageInfoList.length > 0 && $.trim(messageInfoList.val()) != ""){
  			var packList = $xcp.parseJson(messageInfoList.val()) || [];
			$.each(packList,function(i,o){
				if(packList.length -1 == i ){
					importPacketsFrame(o.msgType,"Y",-1);
				}else{
					importPacketsFrame(o.msgType,"Y");
				}
			}); 
		}
		loadDatagridPac();//生成表格
	};
	
	/**在点击不发送的时候默认选择第一个发送的*/
	function selectSendPack(){
		var pakcTab = $("#"+$xcp.PacLoad.defaults.divPacketsId);
		var selTab  = pakcTab.tabs("getSelected").panel("options").tab;
		if(!selTab.hasClass("tabs-disabled"))return;
		
		selTab.removeClass("tabs-selected");
		var tabs = pakcTab.tabs("tabs");
		$.each(tabs,function(i,o){
			var opts = o.panel("options").tab;
			if(!opts.hasClass("tabs-disabled")){
				pakcTab.tabs("select",opts.find("span.tabs-title").html());
				return false;
			}
		});
	}
	
	function datagEventBind(_this){
		if($xcp.task.isUpdateData()){
			var packName = $(_this).attr("packName");
			var paStu = getPackStu(packName);
			if($(_this).xcpVal() == "N" && paStu != "2"){//lsm
				$.messager.confirm($xcp.i18n("paramMar.tixck"),$xcp.i18n('packets.packNoSend'),function(r){   
				    if (r){
				    	$xcp.PacLoad.loadPackets(packName,$(_this).xcpVal());
				    	setPackStu(packName,$(_this).attr("sendtype"));
				    	selectSendPack();
				    	trigDoLoadChange();
				    }else{
				    	alterPackStu(packName);//回退
				    }   
			    }); 
				return ;
			}
			$xcp.PacLoad.loadPackets(packName,$(_this).xcpVal());
			//特殊报文处理 发送某笔报文时需要发送相应报文
			packeCunst(packName);
			setPackStu(packName,$(_this).attr("sendtype"));
			trigDoLoadChange();
		}
	}
	
	/**特殊报文处理 发送某笔报文时需要发送相应报文**/
	function packeCunst(packName){
		var rowJson = {};
		if(packName.indexOf("MT202COV") > -1){
			var mtName = packName.indexOf("_") != -1 ?  "MT103" + packName.substring(packName.indexOf("_"),packName.length):"MT103";
			var str = $("input[packName='" + mtName + "']:checked");
			if(str .length > 0){
				if(str.attr("value") == 'N'){
					datagEventBind($("input[packName='" + mtName + "'][value='Y']").attr("checked", "checked"));
				}
			}else{
				var addList = $xcp.PacLoad.defaults.addPactetsLsit;
				//从数组里面拿到响应报文名称的数据
			    $.each(addList,function(i,k){
			    	if(k.pacName == "MT103"){
			    		rowJson = jQuery.extend({},k);
			    		return false;
			    	}
			    });
			    if(!rowJson || $.isEmptyObject(rowJson)){
			    	return;
			    }
			    rowJson.pacDel = true;//新增的加true 删除按钮显示
			    //rowJson.pacDepict = $("#packetsFrmDialogIpt").xcpVal();
				$("#"+$xcp.PacLoad.defaults.tableDivId).datagrid('insertRow',{row : rowJson});
				loadPackets(rowJson.pacName,"Y",null,"N"); 
			}
		}
		if(packName.indexOf("RTGS202COV") > -1){
			var str = $("input[packName='RTGS103']:checked");
			if(str .length > 0){
				if(str.attr("value") == 'N'){
					datagEventBind($("input[packName='RTGS103'][value='Y']").attr("checked", "checked"));
				}
			}else{
				var addList = $xcp.PacLoad.defaults.addPactetsLsit;
				//从数组里面拿到响应报文名称的数据
				$.each(addList,function(i,k){
					if(k.pacName == "RTGS103"){
						rowJson = jQuery.extend({},k);
						return false;
					}
				});
				
				rowJson.pacDel = true;//新增的加true 删除按钮显示
				//rowJson.pacDepict = $("#packetsFrmDialogIpt").xcpVal();
				$("#"+$xcp.PacLoad.defaults.tableDivId).datagrid('insertRow',{row : rowJson});
				loadPackets(rowJson.pacName,"Y",null,"N"); 
			}
		}
	}
	
	/**rowData 行数据集
	 * sendDef : 为默认是否发送  true = 发送 false = 不发送
	 * sendType：当sendDef = true 的时候有效可以不传默认为0    0 = 发送 1 = 不实际发送   (isPackBusjfs 控制平台是否有此选项)
	 */
	function xuanzFomter(value,rowData){
		var html = ""; 
		var str = '<input name ="packInt'+rowData.pacName+'" packName="'+rowData.pacName+'" onclick="javascript:$xcp.PacLoad.datagEventBind(this);"  class="radio"   type="radio"';
		if(!$xcp.task.isUpdateData()){
			str += " disabled='disabled' ";
		}
		html = str;
		
		if(rowData.sendDef == true || (rowData.pacDel  == true && rowData.sendDef != false) || rowData.sendType == "0"){
			if(rowData.sendType != "1" || rowData.sendType != 1 )
				html +=' checked="checked" ';
			rowData.sendType  = rowData.sendType || "0";
		}
		
		html +=' sendType="0"  value="Y" />  '+$xcp.i18n("packets.statesSD") + str ;
		//sendPac="O" 不实际发送
		if(rowData.sendDef != true  && rowData.sendType != "0"){
			html +='  checked="checked" ';
		}
		
		html += 'value="N" /> '+ $xcp.i18n("packets.bufs") ;
		
		if($xcp.PacLoad.defaults.isPackBusjfs == "1"){
			html += str ;
			if((rowData.sendType == "1" || rowData.sendType == 1) ){ //&& rowData.sendDef == true 不是默认发送的报文如果选择了不实际发送
				html += '  checked="checked" ';
			}
			html +=' sendPac="1" sendType="1" value="Y"/> '+$xcp.i18n("packets.busjfs");
		}
		setPackStu(rowData.pacName,rowData.sendType);
		return html;
	}
	
	function setPackStu(pacName,sType){
		if(!sType){
			$xcp.PacLoad.defaults.packStu[pacName] = "2";
		}else if(sType == "0"){
			$xcp.PacLoad.defaults.packStu[pacName] = "1";
		}else if(sType == "1"){
			$xcp.PacLoad.defaults.packStu[pacName] = "3";
		}
	}
	
	function getPackStu(pacName){
		return $xcp.PacLoad.defaults.packStu[pacName];
	}
	
	function alterPackStu(pacName){
		var status = getPackStu(pacName);
		var str = "";
		if(status == "1" ){
			str = "[value='Y'][sendtype='0']";
		}else if(status == "2"){
			str = "[value='N']"; isY = "N";
		}else if(status == "3"){
			str = "[value='Y'][sendtype='1']";
		}
		$("input[name='packInt"+pacName+"']"+str).attr("checked",true);
	}
	
	function homePacketsSetData(pacNameF){
		
		var str = $(pacNameF).attr("pacsetData")||"";
		if($.isFunction(window["setDataF_"+str])){
			window["setDataF_"+str](str);
		}else{
			//$.messager.alert("提示消息","报文方法没有定义!");return false;
		}
	}
	function setDataBantFomter(value,rowData){
		var html = ""; 
		if($xcp.task.isUpdateData()){
			html  = "<div class='paramDmlOperaBtnWrap'>";
		    html += 	"<span title='"+$xcp.i18n("paramMar.xiug")+"' pacsetData= '"+rowData.pacName+"' id='pacBantFom"+rowData.pacName+"'  class='mod' onClick=\"$xcp.PacLoad.homePacketsSetData(this);\"></span>";
		    html += "</div>";
			/*html = '<a href="javascript:void(0);" onClick = "$xcp.PacLoad.homePacketsSetData(this);" pacsetData= "'+rowData.pacName+'" id="pacBantFom'+rowData.pacName+'" class="icon-edit"  >&nbsp;&nbsp;&nbsp;&nbsp;</a>'; 
		*/}
		return html;
	}
	
	function careaDlog(){
		var str = "packetsFrmDialog";
		var tabHtml =  '<table >';
			tabHtml += '<tr><td>'+$xcp.i18n("packets.dianwlx")+':</td>';
			tabHtml += '<td><input class="easyui-combobox" data-options="panelHeight:200" id="'+str+'Combox" ></input></td></tr>';
			tabHtml += '<tr><td>'+$xcp.i18n("packets.clauseDesc")+':</td>';
			tabHtml += '<td><input   style="width:196px;height:21px;" id="'+str+'Ipt" ></input></td></tr>';
		    tabHtml += '</table>';
		
		var html = 	'<div id="'+str+'"   class="easyui-dialog" style="width:350px;height:210px;padding:10px">';
			html += 	tabHtml;
			html += '	<div id="dlg-buttons">';
			html += 		'<a  class="easyui-linkbutton  pubDialogSaveBtn" data-options="plain:true">'+$xcp.i18n('app.confirm')+'</a>';
			html += 		'<a id="frm-DelClose" class="easyui-linkbutton pubDialogCloseBtn" data-options="plain:true">'+$xcp.i18n('app.cancel')+'</a>';
			html += 	'</div>';
			html += '</div>';
		
		$(html).appendTo('body');
		$.parser.parse($("#"+str));
		
		
		$('#'+str).dialog({
			title   : $xcp.i18n("packets.xinzbwlx"),
			modal   : true,
			iconCls : 'icon-save',
			buttons : '#dlg-buttons',
			onClose     : function(){
				$('#' + str).html("").dialog('destroy');
			}
		});
		//新增弹出层 确定按钮执行bind方法
		$('#'+str).find('a.pubDialogSaveBtn').bind('click',function(){
			var comVal  = $('#'+str+"Combox").xcpVal(),rowJson = {};
			if(comVal == ""){
				$.messager.alert($xcp.i18n("paramMar.tisxx"),$xcp.i18n("packets.qingxzbw"));
				return false;
			}
			var addList = $xcp.PacLoad.defaults.addPactetsLsit;
			//从数组里面拿到响应报文名称的数据
		    $.each(addList,function(i,k){
		    	if(k.pacName == comVal){
		    		rowJson = jQuery.extend({},k);
		    		return false;
		    	}
		    });
		    
	    	//查看tab列表是否有此报文名称 有则返回最大值
	    	rowJson.pacName = resolveTabDataMax(rowJson.pacName);
		    rowJson.pacDel = true;//新增的加true 删除按钮显示
		    rowJson.pacDepict = $('#'+str+"Ipt").xcpVal();
			$("#"+$xcp.PacLoad.defaults.tableDivId).datagrid('insertRow',{row : rowJson});
			//alert(rowJson.pacName);
			loadPackets(rowJson.pacName,"Y",null,"N"); 
			$('#'+str).dialog('destroy');
			trigDoLoadChange();
		});
		
		$('#'+str).find('a.pubDialogCloseBtn').bind('click',function(){
			$('#' + str).html("").dialog('destroy');
		});
		 
		$('#'+str+"Combox").combobox({
			valueField:'pacName',
			textField:'pacName',
			data:$xcp.PacLoad.defaults.addPactetsLsit
		});
		
		//lsm 1-22
		$('#'+str+"Combox").bind("change.com",function(val){
			var sVal = $('#'+str+"Combox").xcpVal();
			//从数组里面拿到响应报文名称的描述数据
		    $.each($xcp.PacLoad.defaults.addPactetsLsit,function(i,k){
		    	if(k.pacName == sVal){
		    		$('#'+str+"Ipt").xcpVal(k.pacDepict);
		    		return false;
		    	}
		    });
		});
	}
	
	function resolveTabDataMax(pacName,data){
		//查看列表数据是否存在此报文名称 存在则加 _1 存在_* 则往后加1   
	    var tabData =  data||$("#"+$xcp.PacLoad.defaults.tableDivId).datagrid("getData");
	    if(tabData.total == "0"){
	    	return pacName;
	    }
		var strSort = [],str = pacName;
		$.each(tabData.rows||[],function(i,k){
			if(k.pacName.indexOf(pacName) != -1){
				if(k.pacName.indexOf(pacName+"_") != -1){
					strSort.push(parseInt(k.pacName.substring(pacName.length+1,k.pacName.length)));
				}else{
					str = pacName+"_1";
				}
			}
		});
		if(strSort.length > 0){
			strSort.sort(function(a,b){return a<b?1:-1;});
			str = pacName + "_" +(parseInt(strSort[0])+1);
		}
		return str;
	}

	function loadDatagridPac(){//lsm5
		var  target = "#"+$xcp.PacLoad.defaults.tableDivId;
		
		var toolbar = [
		   		    {
		   		    	text    :'<b>'+$xcp.i18n("packets.baowfslb")+'</b>',
		   		    	iconCls : ''
		   		    },'-',{
		   				text:$xcp.i18n('chrMgr.xz'),
		   				iconCls:'icon-add packetsIconAdd',	
		   				handler:function(){
		   					if(!$xcp.task.isUpdateData()){
		   						return ;
		   					}
		   					if( $xcp.PacLoad.defaults.addPactetsLsit  && $xcp.PacLoad.defaults.addPactetsLsit.length > 0){
		   						careaDlog();
		   					}else{
		   						$.messager.alert($xcp.i18n("paramMar.tisxx"),$xcp.i18n("packets.meiybwkxz"));
		   					}
		   				}
		   			},'-',{
		   				text:$xcp.i18n('chrMgr.sc') ,
		   				iconCls:'icon-cut  packetsIconDel ',
		   				handler:function(){
		   					if(!$xcp.task.isUpdateData()){
		   						return ;
		   					}
		   					$.messager.confirm($xcp.i18n("paramMar.tixck"),$xcp.i18n('validate.messager'),function(r){
		   					    if (r){   
		   					    	var row = $(target).datagrid('getSelected');
		   							if(row != null  ){
		   								var rowIndex = $(target).datagrid('getRowIndex',row);
		   								$(target).datagrid('deleteRow',rowIndex);
		   							}
		   							$xcp.PacLoad.loadPackets(row.pacName,"N","true");
		   							$(".packetsIconDel").closest("td").hide().prev().hide();
		   							trigDoLoadChange();
		   							removeSendPackets(row.pacName);
		   					     }
		   				     });
		   				}
		   			}/*,'-',{
		   				text:'测试' ,
		   				iconCls:'icon-cut  packetsIconAdd ',
		   				handler:function(){
		   					var str = [{"packName":"MT700","sendDef":true},
		   					           {"packName":"MT740","sendDef":false}];
		   					var str1 = [{"packName":"MT700","sendDef":false},
		   					           {"packName":"MT740","sendDef":true}];
		   					ceshiNt++;
		   					if(ceshiNt %2 != 0){
		   						revisePackList(str);
		   					}else{
		   						revisePackList(str1);
		   					}
		   					var str = getFeePackets()
		   					//,{curSign: 'xxx', acctNo:'xxx',amt:888,mt:'RTGS',swiftCode:'CITIUS33'}
		   					var pList = [{curSign: 'xxx', acctNo:'xxx',amt:888,mt:'MT',swiftCode:'CITIUS33'}
		   					,{curSign: 'xxx', acctNo:'xxx',amt:888,mt:'RTGS',swiftCode:'CITIUS33'}];//lsmcs
		   					$xcp.PacLoad.revisePackForChargeFlow(pList);
		   				}
		   			}*/
		   		];
		    
		    function alertStyler(value,rowData){
		    	if(rowData.pacDel == true){
		    		return "background:#b5fb88";
		    	}
		    	return "background:#ffffff;color:#000000";
		    }
		    //增加列删除操作区域下标需要改动
		    var packColumnsList = [[
		  	   			          {title : $xcp.i18n("packets.dianwlx"), field : 'pacName',    width : 100,   align : 'center',styler:alertStyler}, 
			   			          {title : $xcp.i18n("packets.clauseDesc"),   field : 'pacDepict',  width : 100,   align : 'center' }, 
			   			          {title : $xcp.i18n("packets.caozxx"),   field : 'packest3',   width : 200,   align : 'left',formatter:xuanzFomter },
			   			         /* {title : '赋值', field : 'pacBantFom' ,width : 100, align : 'center',formatter:setDataBantFomter},
			   			          */{title : '是否显示删除', field : 'pacDel', hidden:true },
			   			          {title : '是否是实际发送', field : 'sendType', hidden:true }
			   		            ]];
		    if($xcp.PacLoad.defaults.isOperate == false){
		    	packColumnsList[0].splice(2,1);
		    }
	   		//初始化表格数据
	   		$(target).datagrid({
	   			columns:packColumnsList,
   		        fitColumns:true,
   		        collapsible:true,
	   			toolbar : toolbar,
	   			loadMsg : $xcp.i18n('sys.loadMsg'),
	   			singleSelect: true,
	   			rownumbers:false,
	   			remoteSort:false,
	   			border:false,
	   			nowrap:false,			    
	   			striped:true,
	   			autoRowHeight:false,
	   			onDblClickRow : function(index,rowData){
	   			},
	   			onSelect : function(index,rowData){
	   				if(rowData.pacDel == true && $xcp.task.isUpdateData()){
	   					$(".packetsIconDel").closest("td").show().prev().show();
	   				}else{
	   					$(".packetsIconDel").closest("td").hide().prev().hide();
	   				}
	   			},
	   			onLoadSuccess : function(){
	   				$(".packetsIconDel").closest("td").hide().prev().hide();
	   				if(!$xcp.task.isUpdateData()){//非经办或经办更正 不能执行赋值操作
	   					//$(target).datagrid("hideColumn","pacBantFom");
	   					$(".packetsIconAdd").closest("td").hide().prev().hide();
	   				}
	   			},
	   			rowStyler: function(index,row){
	   				if(row.isHidden == true){
	   					return "display:none";
	   				}
	   				if(row.pacDel == true){
			    		return "color:#000000";
			    	}
					//return 'background-color:#6293BB;color:#fff;font-weight:bold;';
				}
	   		});
	   		var deft = $xcp.PacLoad.defaults;
	   		//暂时用 undefined 来区分手工经办和经办
	   		//经办时根据默认是否发送来获取初始化报文子页面
	   		if($xcp.task.isHandle() && messageInfoList.val() == undefined
	   				&& deft.defaultsPactetsLsit 
	   				&& deft.defaultsPactetsLsit.length > 0){
	   			var strList = [];
	   			$.each(deft.defaultsPactetsLsit,function(i,k){
	   				 if(k.sendDef == true){
	   					strList.push(k);
	   				 }
	   			});
	   			
	   			$.each(strList || [],function(i,k){
	   				if(k.isHidden == true) return ;
   					if(strList.length -1 == i ){
						loadPackets(k.pacName,"Y",null,null,-1);
					}else{
						loadPackets(k.pacName,"Y");
					}
	   			});
	   			$(target).datagrid("loadData",deft.defaultsPactetsLsit);
	   		}else if($xcp.task.isUpdateData() && $("#"+deft.operMsgList).val() != undefined){//lsm
	   			var str = $xcp.parseJson($("#"+deft.operMsgList).val() || "").rows;
	   			$.each(deft.defaultsPactetsLsit,function(i,k){
	   				var blean = true; 
   					$.each(str,function(l,m){
	   					if(m && k.pacName == m.pacName){
	   						blean = false;return false;
	   					}
	   				});
   					if(blean){
   						str.push({
   							"pacName": k.pacName,
   					        "pacDepict": k.pacDepict,
   					        "sendDef": false,
   					        "sendType": "2"
   						});
   					}
	   			});
	   			$(target).datagrid("loadData",str);
	   		}else{//非经办时根据数据库值来获取初始化报文子页面
	   			var str = $xcp.parseJson(messageInfoList.val() || "");
	   			//经办更正时需要显示默认值
	   			if( ($xcp.task.isCorrect() || 
	   					($xcp.task.isHandle() && messageInfoList.val() != "" ))
	   					&& deft.defaultsPactetsLsit 
	   					&& deft.defaultsPactetsLsit.length > 0){
	   				$.each(deft.defaultsPactetsLsit,function(i,k){
	   					k.sendDef = false;
	   					$.each(str,function(l,m){
		   					if(m && k.pacName == m.msgType){
		   						k.sendDef = true;
		   						delete str[l];
		   						return false;
		   					}
		   				});
		   			});
	   				
		   			$(target).datagrid("loadData",cunsnLoadTabData(str,deft.defaultsPactetsLsit));
		   		}else{
		   			$.each(str,function(i,o){
		   				str[i].sendDef =   true  ;
		   			});
		   			$(target).datagrid("loadData",cunsnLoadTabData(str));
		   		}
	   		}
	}
	
	function  cunsnLoadTabData(jDat,dDat){
		if(jDat == "" || jDat == "undefined"){
			jDat = [];
		}
		
		var jso = dDat || [];
		$.each(jDat,function(i,k){
			if(k == null){
				return ;
			}
			jso.push({
				"pacName" : k.msgType,
				"sendType" : k.sendType,
				"pacDel" : k.pacDel,
				"pacDepict": k.pacDepict,
				"swifCode" : k.swifCode,
				"countryCode" : k.countryCode,
				"sendDef" : k.sendDef,
				"isHidden" : k.isHidden
				//"pacDepict" :k.pacDepict  
			});
		});
		return jso;
	}
	
	function getFeePackets (notSed){
		var feeList = "";
		var packetSendList = getPacketsSel();
		 
		$.each(packetSendList||[],function(i,k){
			if(k.sendType == "1" && true == notSed)return ;
			feeList += (i != 0)?",":"";
			if(k.countryCode && $.trim(k.countryCode) != ""){
				feeList += $.isFunction(k.countryCode) ? k.countryCode() : k.countryCode;
			}else if(k.swifCode && $.trim(k.swifCode) != ""){
				var mySwift = $.isFunction(k.swifCode) ? k.swifCode() : k.swifCode;
				feeList += ajaxGetCountry(mySwift);
			}else {
				var paNa_recevi = $("#"+k.msgType+"_Receiver");
				if(paNa_recevi.length > 0 ){
					feeList += ajaxGetCountry(paNa_recevi.val()||"");
				}else{
					feeList += "840";
				}
			}
		});
		return feeList;
	}
	
	/**获取报文发送状态*/ //lsm1
	function getPacktStus(packName){
		var packetSendList = getPacketsSel()||[] ;
		if(!packName) return packetSendList;
		
		var stut;
		$.each(packetSendList,function(i,o){
			if(o.msgType == packName){
				stut =  o.sendType == "1" ? "3" : "1"; 
				return false;
			}
		});
		return stut||"2";
	}
	
	function ajaxGetCountry(swiftCo){
		var counNo = "840";
		if($.trim(swiftCo) == "")return counNo;
		
		$xcp.ajax({
			 url  : "bankQuery.do?method=getBankInfo",//根据swiftCode 获取 国家号
			 data :  {"page":"1","rows":"1","swiftCode":swiftCo}
		},null,function(rs){
			if(rs.total != 0){
				counNo = rs.rows[0].countryNo;
			}
		});
		 
		return counNo;
	}
	
	function packetsOverallHideCon(obj,oKey){
		var oKey_hidTag = $("#"+oKey+"_hidTag");
		oKey_hidTag.attr("title",$xcp.i18n('packets.hideshowTag'));
		if(typeof obj != "object" ){
			return ;
		}
		oKey_hidTag.unbind("click.hidTag").bind("click.hidTag",function(){
			packetsOverallHideCon(obj,oKey);
		});
		var blean = $("#"+(obj[0]||"")).is(":hidden");
		if(blean){
			oKey_hidTag.removeClass("packets_hidTag").addClass("packets_hidTag1");
		}else{
			oKey_hidTag.removeClass("packets_hidTag1").addClass("packets_hidTag");
		}
		$.each(obj,function(i,k){
			if(blean){
				$("#"+k).show();
			}else{
				oKey_hidTag.removeClass("packets_hidTag1").addClass("packets_hidTag");
				$("#"+k).hide();
			}
		});
	}
	
	/**FMT的报文初始化**/
	function searchFmt(prefix){
		var prefix_msgId = $('#' + prefix + '_msgId');
		if(prefix_msgId.length == 0){return; };
		
		prefix_msgId.bind('click.fixed', function() {
			if(prefix_msgId.attr("isSeach") == undefined){
				prefix_msgId.attr("isSeach",true);
				prefix_msgId.xcpSrchBox({
					lxOrFun : 'searchReceiveFmt', 
					callback : function(rowIndex, rowData){
						msgIdCallBack(rowIndex, rowData,prefix);
					},params : { }});
				prefix_msgId.parent().find(".searchbox-button").trigger("click");
			}
		});
	}
	
	function defaultSelectPackets(row){
		var rowData = row.rows;
		var messageWaitPakctes = $("#messageWaitPakctes").val();
        $.each(rowData,function(idx,val){//遍历JSON
        	if(messageWaitPakctes && messageWaitPakctes.length > 0){
        		$.each(messageWaitPakctes.split(","), function (i,packets){
        			if(val.msgId == packets){
        				$("#waitPacketsList_letterTable").datagrid("selectRow", idx);//如果数据行为已选中则选中改行  
        			}
        		});
        	}
        });
	}
	
	function defaultClearSelectPackets(row){
		var rowData = row.rows;
		var messageWaitPakctes = $("#messageWaitPakctes").val();
        $.each(rowData,function(idx,val){//遍历JSON
        	if(messageWaitPakctes && messageWaitPakctes.length > 0){
        		$.each(messageWaitPakctes.split(","), function (i,packets){
        			if(val.msgId == packets){
        				$("#clearPacketsList_letterTable").datagrid("selectRow", idx);//如果数据行为已选中则选中改行  
        			}
        		});
        	}
        });
	}
	
	function FunBindClauseSelect(clause, prefix){ 
		if(!$xcp.task.isUpdateData()){return;};
		
		var tradeNo = $("#butxn_tradeNo").val();
		var msgType = prefix;
		if(msgType.indexOf("_") > -1){
			msgType.substring(0,msgType.indexOf("_"));
		}
		
		$.each(clause,function(i,info){
			if(info == null)return;
			var field = info.field;
			var target = info.target;
			var defaultValue = info.defaultValue;
			var telexCode = info.telexCode;
			var prefixTarget = null;
			if(field && field.length > 0 ){
				prefixTarget = getPrefixTarget();
				if(!prefixTarget.hasClass("P")){
					var fun = "$xcp.formPubMgr.viewPacketsClause('"+tradeNo+"', '"+msgType+"', '"+field+"', '"+prefix + "_" +target+"')";
					var html = '<span class="select" onClick="'+fun+'" title="'+$xcp.i18n("packtes.clauseSelect")+'"></span>';
					$(html).appendTo(prefixTarget.parent());
				}
			}
			if(defaultValue && defaultValue.length > 0){
				getPrefixTarget().xcpVal(defaultValue);
			}
			if(telexCode && telexCode == "1"){
				getPrefixTarget().removeAttr("telexCode");
			}
			
			function getPrefixTarget(){
				return prefixTarget == null ?  $("#"+ prefix + "_" + target) : prefixTarget;
			}
		});
	}
	
	function bindClauseSelect(clause, prefix){
		$xcp.PacLoad.defaults.defaultsPackData[prefix] = {
				"clause" : clause,"prefix":prefix
		};
	}
	
	/**缓存报文数据**/
	function setPackDefaults(opt){
		if(typeof opt != "object"){
			return ;
		}
		var list =  $xcp.PacLoad.defaults.defaultsPactetsLsit ;
		list = list || [];
		
		$.each(opt,function(i,o){
			var blean = true , isSend = o.status == "2" ? false : true;
			$.each(list,function(j,k){
				if(o.packName == k.pacName){
					list[j].sendDef = isSend;
					return blean = false;
				}
			});
			if(blean){
				list.push({pacName : o.packName,pacDepict : "",sendDef : isSend});
			}
		});
	}
	
	/**
	 * 修改报文发送选项 1 发送 2 不发送 3 不实际发送
	 */
	function revisePackStu(packList){
		if(inited == false){
			//在报文组件没有加载的情况下 缓存数据
			setPackDefaults(packList);
			return ;
		}
		
		if(packList == undefined || packList.length == 0 ) return ;
		var str , isY;
		$.each(packList,function(i,k){
			str = "" ,isY = "Y";
			if(k.status == "1" || k.status == 1){
				str = "[value='Y'][sendtype='0']";
			}else if(k.status == "2" || k.status == 2){
				str = "[value='N']"; isY = "N";
			}else if(k.status == "3" || k.status == 3){
				str = "[value='Y'][sendtype='1']";
			}
			$("input[name='packInt"+k.packName+"']"+str).attr("checked",true);
			loadPackets(k.packName,isY);
		});
		trigDoLoadChange();
	}
	
	/**获取报文列表数据*/
	function getOperList(packList){
		if(packList == undefined || packList.length == 0 ) return ;
		var data =  $("#"+$xcp.PacLoad.defaults.tableDivId).datagrid("getData");
		$.each(data.rows||[],function(i,o){
			o.isHidden = o.pacDel == true ? false : true;
			$.each(packList,function(j,k){
				if(k.packName == o.pacName){
					o.isHidden = false;
					o.sendDef = (k.sendDef != undefined ? k.sendDef : o.sendDef);
					//alert(k.sendDef + " | " + o.sendDef);
					o.sendType = (k.sendType != undefined ? k.sendType : (o.sendDef == true ? "0":"2"));
				}
			});
		});
		return data;
	}
	
	/**获取报文列表数据*/
	function getOperValList(){
		var data =  $("#"+$xcp.PacLoad.defaults.tableDivId).datagrid("getData");
		$.each(data.rows||[],function(i,o){
			var megObj = $("input[name='packInt"+o.pacName+"']:checked");
			var sendType = megObj.attr("sendType");
			o.sendType = sendType != undefined ? sendType : "2";
			if(megObj.attr("value") == "N"){
				o.sendDef = false;
			}
		});
		return data;
	}
	/**
	 * 重置报文list列表 
	 */
	function revisePackList(packList){
		if(packList == undefined || packList.length == 0 ) return ;
		var data = getOperList(packList);
		 
		$("#"+$xcp.PacLoad.defaults.tableDivId).datagrid("loadData",data);
		$.each(data.rows||[],function(i,o){
			 var str = $("input[name='packInt"+o.pacName+"']:checked").val();
			 if(o.isHidden == true){
				 if(str == "Y"){
					 hidTabName(o.pacName,"hidden");
				 }else{
					 hidTabName(o.pacName,"hidden");
				 }
			 }else{
				 if(str == "Y"){
					 hidTabName(o.pacName,"show","Y");
				 }else{
					 hidTabName(o.pacName,"show");
				 }
			 }
		});
		
		trigDoLoadChange();
	}
	
	//隐藏某个tab  title
	function hidTabName(title,stu,isY){
		if(stu == undefined )return;
		var str = $("#"+$xcp.PacLoad.defaults.divPacketsId).tabs("getTab",title);
		if(str){
			str = $(str).panel("options");
		}
		if(stu == "hidden"){
			str ? $(str.tab).hide() : "";
			if($("#"+title+"_1").length > 0){
				loadPackets(title,"N");
			} 
		}else if(stu == "show"){
			if($("#"+title+"_1").length > 0){
				$("#"+title+"_1").show();
				loadPackets(title,isY == "Y" ? isY : "N");
			}else{
				loadPackets(title,isY == "Y" ? isY : "N");
			}
			str ? $(str.tab).show() : "";
		}
	}
	
	function rtuPacName(pacName,stu){
//		if(funlsPack[pacName] != undefined){
		
		var str = "";
		str = stu ? "_" +stu : "";
		pacName = pacName + str||"";
		return pacName;
	}
	//新增报文删掉在新增不mapping问题  删除扩展时需要从发送列表清除
	function removeSendPackets(packName){
		if(messageInfoList.val() == "")return;
		
		var sendList = $xcp.parseJson(messageInfoList.val());
		$.each(sendList||[],function(i,o){
			if(o.msgType == packName){
				sendList.splice(i,1);
				return false;
			};
		});
		messageInfoList.val($xcp.toJson(sendList));
	}
	
	function setMsgField(obj,pacName,arrMsg,stu){
		if(obj){
			var id = obj == 'all' ? "" : $(obj).attr('id');
			var isSendMsg = isSend(pacName);
			
			pacName = rtuPacName(pacName,stu);
			
			$.each(arrMsg,function(i,o){
				var packKey = $('#' + pacName + '_' + o.mkey);
				if(obj == 'all' || (!isMsgFldChg(packKey) && id == o.formkey)){
					var blean = true;
					var formkey = $('#' + o.formkey);
					var packVal = packKey.xcpVal() , formVal = formkey.xcpVal();
					
					if(isSendMsg === true){
						if(packVal != formVal && isChange(pacName,pacName + '_' + o.mkey) == true){
							blean = false;
						}
					}else{
						if(formkey.xcpVal() != "" || (formkey.length != 0 && (o.defVal||"") == "")){
							packKey.xcpVal(formkey.xcpVal(),'change.fixed');
						}else{
							if((o.defVal||"") != ""){
								packKey.xcpVal(o.defVal||"", 'change.fixed');
							} 
						}
					}
					
					if(obj != "all")return false;
					if(formkey.length > 0 && blean){
						formkey.bind("change.fixed",function(){
							var packEvent =  $('#' + pacName + '_' + o.mkey);
							if("true" == packEvent.attr("msgfldchg"))return;
							packEvent.xcpVal($(this).xcpVal(),'change.fixed');
//							$('#' + pacName + '_' + o.mkey).xcpVal($(this).xcpVal(),'change.fixed');
						});
					}
				}
			});
		}
		
		function isMsgFldChg(mtkey){
			return mtkey.attr('msgFldChg') == "true";
		}	
		
		function isChange(packName,pacKey){
			var blean = true;
			var paCharge = $xcp.PacLoad.defaults.sendSuccessFunRegs[packName + "Arr"]["pacChange"];
			
			$.each(paCharge||[],function(i,o){
				if(pacKey == o){
					blean = false;  return false;
					delete paCharge[i];
				}
			});
			return blean;
		}
	}
	
	/**判断报文是否发送**/
	function isSend(packName){
		var blean = false;
		var sendList = $xcp.parseJson(messageInfoList.val());
		$.each(sendList||[],function(i,o){
			if(o.msgType == packName){
				blean = true;  return false;
			}
		});
		return blean;
	}
	
	function setMsgClass(pacName,popArr,stu){
		pacName = rtuPacName(pacName,stu);
		
		if(pacName && popArr){
			$.each(popArr,function(i,o){
//				$xcp.formPubMgr.addBlentClas("P",pacName + '_' + o);
				$("#" + pacName + '_' + o).addClass("P");
			});
		}
	}
	
	function setMsgSearchbox(pacName,srchArr){
		if(pacName && srchArr){
			$.each(srchArr,function(i,o){
				if(o){
					var keyNm = '#' + pacName + '_' + o;
					 $(keyNm).addClass("easyui-searchbox").removeClass("easyui-validatebox validatebox-text");
					 $.parser.parse($(keyNm).parent());
				}
			});
		}
	}
	
	function setMsgChg(pacName,popArr,stu){
		if(pacName && popArr){
			var defRegs = $xcp.PacLoad.defaults.sendSuccessFunRegs;
			defRegs[pacName + "Arr"] = defRegs[pacName + "Arr"] ? defRegs[pacName + "Arr"] : {};
			defRegs[pacName + "Arr"]["pacChange"] = popArr;
			
			pacName = rtuPacName(pacName,stu);
			
			$.each(popArr,function(i,o){
				$('#' + pacName + '_' + o).bind('change.setPacktes', function() {
					$('#' + pacName + '_' + o).attr('msgFldChg','true');
				});
			});
		}
	}
	
	function bindFormFldEvent(msgArr,funName){
		//获取函数名称
		var re = funName.toString().match(/function\s*([^(]*)\(/)[1];
		re = re.replace("setDataF_","");
		$xcp.PacLoad.defaults.sendSuccessFunRegs[re + "Arr"] = {
				msgArr : msgArr,funName : funName
		};
	}
	
	function bindMsgEvent(pacNa,stu){
		//lsm4
		/*var jon = $xcp.PacLoad.defaults.sendSuccessFunRegs[pacNa + "Arr"];
		if(!jon) return ;
		
		bindFormFldBind(jon.msgArr,jon.funName);
		delete $xcp.PacLoad.defaults.sendSuccessFunRegs[pacNa + "Arr"];*/
	};
	
	function bindFormFldBind(msgArr,funName){
		if(msgArr && $.isFunction(funName)){
			$.each(msgArr,function(i,o){
				$('#'+o).bind('change.fixed', function() {
					funName(this);
				});
			});
		}
	}
	
	
	function msgIdCallBack(rowIndex, rowData, prefix){  
		$('#'+prefix+'_msgId').xcpVal(rowData.msrcvInfo_msgId);
		$('#'+prefix+'_message').xcpVal(rowData.msrcvInfo_content);
		var content = rowData.MSMSGINFO_content;
		var version = rowData.MSMSGINFO_version;
		var str = $("#"+$xcp.PacLoad.defaults.divPacketsId).tabs("getSelected").panel("options").title;
		loadReeMessage(str, content, version);
	}
	
	function loadReeMessage(str,content, version){
		var ajaxOpt = 
    	{    		
    		url : "common.do?method=loadReexchange",
    		data:{
    			"msgType" : str,
    			"reexchangeMessage" : content,
    			"version" : version
    		},
    		dataType : "html"
    	};
    	$xcp.ajax(ajaxOpt,null,null,function(rs,opt,result){
    		var pacName = $("#"+$xcp.PacLoad.defaults.divPacketsId).tabs("getSelected").panel("options").title;
    		var packetsDiv = $("#" + pacName + "_1");
    		var ree = $("#"+pacName+"_reexchange",packetsDiv);
    		if($(ree).length == 0){
    			$("<div id='"+pacName+"_reexchange'></div>").appendTo(packetsDiv);
    			ree = $("#"+pacName+"_reexchange",packetsDiv);
    		} else {
    			$(ree).html("");
    		}
    		$(ree).html(result);
    		
    		//复合经办时候选择域显示值 显示多个循环域的值 复合不添加事件
			initializationPac($(ree).find($xcp.PacLoad.defaults.classPacCycle));
			
			//经办需要添加事件和显示值
			if($xcp.task.isUpdateData()){//是否 复合
				PacLoadFUN($(ree)); // 添加事件
			}
			$.parser.parse($(ree));
			
			
			var def = $xcp.PacLoad.defaults.defaultsPackData;
			if(def[pacName+"_reexchange"]){
				$(ree).form("load",def[pacName+"_reexchange"].clause);
			}
			
			$.each($($xcp.PacLoad.defaults.classPacSelect, $(ree)),function(i,o){
				 var thPacID=$(o).attr("id").replace("DIV_","");
				 var thPacSel = $("#"+thPacID+"_"+$(o).find("#"+thPacID).xcpVal());//   xcpVal
				  
				  $("div[id ^='" + thPacID + "'][id !='" + thPacID+"']")
				  	.hide().find(".M[id][name]")
				  	.addClass("M_Pac_validate").removeClass("M");
				  
				  //选中的div M_Pac_validate 转换成M 做非空验证
				  thPacSel.show().find(".M_Pac_validate[id][name]")
				  .addClass("M").removeClass("M_Pac_validate");
			});
			
			if(!$xcp.task.isUpdateData()){//不是经办和经办更正
				$xcp.formPubMgr.readonlyAll(true,$(ree));
			}
			//加上M O P 样式
//			$xcp.formPubMgr.loadElementClassP($(ree));
			
			//给所有报文标签加上标识
			$("#"+pacName+"_1").find($xcp.PacLoad.defaults.classPackDocument).attr("packetsVa","Y");
			
			//给报文标签加上键盘事件
			$xcp.formPubMgr.swiftTextareaEventInit($(ree));
			
			//做动态关联M O  样式
			if($xcp.task.isUpdateData()){
				alterMOptional($("#"+pacName+"_1"));
			}
    	});
	}
	
	
	/*
	 * skf 点击报文预览中错误，自动跳转到报文相应位置
	 */
	function jumpCurrentLocation(tabid,subid){
		var obj = $('#' + tabid + '_' + subid).length > 0 ? $('#' + tabid + '_' + subid) : $('[id*=' + tabid + '_' + subid +']');
		if(obj && obj.length > 0){
			$("#architecTabs").tabs("select",$xcp.i18n('tab.packetsTab'));
			$("#divPacketsId").tabs("select",tabid);
			var panel = $("#architecTabs").tabs("getSelected");
			//判断为非标签组
			if(obj.length == 1){
				if(obj.hasClass("combobox-f") || obj.hasClass("datebox-f") || obj.hasclass("easyui-searchbox")){
					obj = $(obj.combobox().next('span').find('input'));
				}	
			}
			//标签组 跳转到第一个非隐藏控件上
			else{
				obj.each(function(){
					if(this.tagName == 'INPUT'){
						if($(this).hasClass("combobox-f") || $(this).hasClass("datebox-f") || $(this).hasClass("easyui-searchbox")){
							obj = $($(this).combobox().next('span').find('input'));
							return false;
						}else if($(this).is(':hidden')){
							return true;
						}
						else{
							obj = $(this);
							return false;
						}
					}
				});
			}
			//滚动到相应标签上，位置居中
			try{
				if(panel && panel.scrollTop() > 0){
					 panel.scrollTop(0);
				}
				var ot = $(obj).offset().top;
				var wt = $(window).height();
				
				if(ot > wt/2){
					$(panel).animate({scrollTop:(ot - wt/2)},'10',function(){obj[0].focus();});	
				}else{
					obj[0].focus();
				}
			}catch(e){
				
			}
		}	
	}
	
	function alterMOptional(obj){
		$(".optional",obj).bind("change.opMo",function(i,o){
			var str = $(this).xcpGetName($(this));
			str = str.split("_").length > 2 ? str.split("_") : null;
			if(str != null ){
				var sprList = $(".M[id^='"+str[0]+"_"+str[1]+"_']");
				var blean = true;
				$.each(sprList,function(i,o){
					if($(o).xcpVal() != ""){
						blean = false;
						return false;
					}
				});
				 
				if(!blean){
					$.each(sprList,function(i,o){
						$xcp.formPubMgr.removeBlentClas($(o).attr("id"),null,"optional");
					});
					sprList.removeClass("validatebox-invalid");
				}else{
					$.each(sprList,function(i,o){
						$xcp.formPubMgr.addBlentClas("optional",$(o).attr("id"));
						$xcp.formPubMgr.removeBlentClas($(o).attr("id"),null,"validatebox-invalid");
					});
				}
			}
		});
	}
	
	function packetButFun(packName,packetsLsPacName){ 
		var addOrDel = $(".packets_add,.packets_del",packetsLsPacName).hide().attr("isHid","true");
		var _hideSpanBut = $("#"+packName+"_hideSpanBut");
		if(addOrDel.length == 0)_hideSpanBut.hide();
		
		_hideSpanBut.attr("title",$xcp.i18n('packets.hideshowBut')).bind("click",function(){
			var delAndAddBut = $(".packets_add,.packets_del",packetsLsPacName);
			var blean = delAndAddBut.eq(0).attr("isHid");
			if(blean == "true"){
				$(this).removeClass("packets_hideSpanBut").addClass("packets_hideSpanBut1");
				delAndAddBut.show().attr("isHid","false");
			}else{
				$(this).removeClass("packets_hideSpanBut1").addClass("packets_hideSpanBut");
				delAndAddBut.hide().attr("isHid","true");
			}
		});
	}
	
	function createTemplateHtml(flag){
		var html = '<div id="packetTemplate">';
		html += '<div id="packetTemplateTabs" class="easyui-tabs " fit="true"  border="false">    ';
		html += '	<div title="'+$xcp.i18n("packets.packetsTempalte")+'">                        ';
		html += '		<div id="querySendPacket_sysQueryList" class="easyui-layout" border=false fit="true" >    ';
		html += '                                                                                 ';
		html += '			<div id="querySendPacket_npanel" region="north" border="false">       ';
		html += '				<div id="querySendPacket_condition" class="commQueryCondWrap">    ';
		html += '                                                                                 ';
		html += '					<div class="query">                                           ';
		html += '						<div class="querybutton">                                 ';
		html += '							<div class="text"></div>                              ';
		html += '							<div class="input" id="querySendPacket_queryDiv"></div>';
		html += '						</div>                                                    ';
		html += '					</div>                                                        ';
		html += '				</div>                                                            ';
		html += '			</div>                                                                ';
		html += '			<div id="querySendPacket_cpanel" region="center" border="false"       ';
		html += '				style="background-color: #FAFAF9;">                               ';
		html += '				<table id="querySendPacket_letterTable">                          ';
		html += '				</table>                                                          ';
		html += '			</div>                                                                ';
		html += '		</div>                                                                    ';
		html += '	</div>                                                                        ';
		if(flag){
		html += '	<div title="'+$xcp.i18n("packets.n99Template")+'" id="n99">                                                         ';
		html += '		<div id="queryTemplatePacket_sysQueryList" class="easyui-layout" border=false fit="true" >                      ';
		html += '                                                                                 ';
		html += '			<div id="queryTemplatePacket_npanel" region="north" border="false">   ';
		html += '				<div id="queryTemplatePacket_condition" class="commQueryCondWrap">';
		html += '                                                                                 ';
		html += '					<div class="query">                                           ';
		html += '						<div class="querybutton">                                 ';
		html += '							<div class="text"></div>                              ';
		html += '							<div class="input" id="queryTemplatePacket_queryDiv"></div>';
		html += '						</div>                                                    ';
		html += '					</div>                                                        ';
		html += '				</div>                                                            ';
		html += '			</div>                                                                ';
		html += '			<div id="queryTemplatePacket_cpanel" region="center" border="false"   ';
		html += '				style="background-color: #FAFAF9;">                               ';
		html += '				<table id="queryTemplatePacket_letterTable">                      ';
		html += '				</table>                                                          ';
		html += '			</div>                                                                ';
		html += '		</div>                                                                    ';
		html += '	</div>                                                                        ';
		html += '</div>                                                                           ';
		}
		html += '</div>';
		return html;
	}
	
	//初始化查询条件值
	function ininSendQueryCondition() {
		//交易名称下拉框
		$("#querySendPacket_condition").find('#querySendPacket_currency').combobox({
			data : $xcp.getConstant("cur"),
			textField : "curSign",
			valueField : "curSign"
		});
	};
	
	function loadSendTemplate(rowData){
		var list = getPacketsSel();
		$(list).each(function(i,o){
			var temp = o.msgType;
			if(temp.indexOf(rowData.msgType) != -1){
				var ajaxOpt = 
		    	{    		
		    		url : "packetsTemplate.do?method=loadSendTpl",
		    		data:{
		    			"msgType" : temp,
		    			"message" : rowData.content
		    		}
		    	};
		    	$xcp.ajax(ajaxOpt,null,null,function(rs,opt,result){
		    		if($.isEmptyObject(result.outEntity)){
		    			$.messager.alert($xcp.i18n("paramMar.tisxx"),$xcp.i18n("packets.claError"));
		    		}else {
		    			$("#" + temp + "_1").form("load",result.outEntity);
		    		}
		    	});
			}
		});
	}
	
	function loadN99Tpl(rowData){
		var list = getPacketsSel();
		$(list).each(function(i,o){
			var temp = o.msgType;
			if(temp.indexOf(rowData.msgType) != -1){
				if(rowData.referenceNo != ""){
					$("#" + temp + "_F20").xcpVal(rowData.referenceNo);
				}
				if(rowData.relatedNo != ""){
					$("#" + temp + "_F21").xcpVal(rowData.relatedNo);
				}
				if(rowData.sender != ""){
					$("#" + temp + "_Sender").xcpVal(rowData.sender);
				}
				if(rowData.receiver != ""){
					$("#" + temp + "_Receiver").xcpVal(rowData.receiver);
				}
				if(rowData.narrative != ""){
					if($("#" + temp + "_Loop1_CON1_F79").length > 0){
						$("#" + temp + "_Loop1_CON1_F79").xcpVal(rowData.narrative);
					}
					if($("#" + temp + "_F79").length > 0){
						$("#" + temp + "_F79").xcpVal(rowData.narrative);
					}
				}
			}
		});
	}
	
	/**
	 * 创建发报模板
	 */
	function createSendPacket(str){
		var queryJs = {};
		queryJs.cols = [[
	        {
	            "title": $xcp.i18n("tpsendMsg.bizNo"),"field": "bizNo","width":"100" 
	        },{
	            "title": $xcp.i18n("tpsendMsg.msgType"),"field": "msgType"  ,"width":"100","sortable":true
	        },{
	            "title": $xcp.i18n("tpsendMsg.channel"),"field": "channel","width":"100","sortable":true
	        },{
	            "title": $xcp.i18n("tpsendMsg.relateNo"),"field": "relateNo","width":"100","sortable":true
	        },{
	            "title": $xcp.i18n("tpsendMsg.currency"),"field": "currency","width":"100","sortable":true
	        },{
	            "title": $xcp.i18n("tpsendMsg.amount"),"field": "amount","width":"100","sortable":true
	        },{
	            "title": $xcp.i18n("tpsendMsg.content"),"field": "content","width":"100",hidden:true,"sortable":true
	        },{
	            "title": $xcp.i18n("tpsendMsg.valueDate"),"field": "valueDate","width":"100","sortable":true
	        },{
	            "title": $xcp.i18n("tpsendMsg.tranDate"),"field": "tranDate","width":"100","sortable":true
	        },{
	            "title": $xcp.i18n("tpsendMsg.recivercode"),"field": "reciverCode","width":"100","sortable":true
	        }
	      ]];
		queryJs.url = "./packetsTemplate.do?method=sendPacketsQuery"; 
		queryJs.dblClickTrue = function(rowData){
			loadSendTemplate(rowData);
		};
		var qrParams = {"msgTypeQuery" : str};
		  
		$('#querySendPacket_letterTable').datagrid({
			loadMsg : $xcp.i18n("sys.loadMsg"),
			columns:queryJs.cols,
			url:queryJs.url,
			width: "900",
			height: "510",
			fit   : true,
			fitColumns: false,
			pageSize: 10,            //每页显示的记录条数，默认为10  
		    pageList: [5,10,15,20],  //可以设置每页记录条数的列表  
			pagination:true,
			rownumbers:true,
			queryParams : qrParams,
			onDblClickRow: function(rowIndex, rowData){
				queryJs.dblClickTrue(rowData);
				$("#querySendPacket_condition").remove();
				$("#queryTemplatePacket_condition").remove();
				$("#packetTemplate").window('destroy');
			}
		});
		 
		var options = [
     	      {id:'bizNo',eleType:'easyui-validate',dataType:'0',disName:$xcp.i18n('tpsendMsg.bizNo'),i18n:'tpsendMsg.bizNo',isMainCondition:true},
     	      {id:'currency',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('tpsendMsg.currency'),i18n:'tpsendMsg.currency',isMainCondition:true},
     	      {id:'amount',eleType:'easyui-numberbox',dataType:'1',disName:$xcp.i18n('tpsendMsg.amount'),i18n:'tpsendMsg.amount',isMainCondition:true},
     	      {id:'reciverCode',eleType:'easyui-validate',dataType:'0',disName:$xcp.i18n('msrcvInfo.reciverCode'),i18n:'msrcvInfo.reciverCode',isMainCondition:true},
     	      {id:'tranDate',eleType:'easyui-datebox',dataType:'2',disName:$xcp.i18n('tpsendMsg.tranDate'),i18n:'tpsendMsg.tranDate',isMainCondition:true}
     	];
		var allparams = {
			title   : $xcp.i18n("packets.sendQuery"),
			options : options, 
			callback: ininSendQueryCondition,
			queryID : "querySendPacket"
		};
		$xcp.comQueryCondMgr.initBeforeLoadMethod(allparams);
	}
	
	function createTemplatePackets(str){
		var queryJs = {};
		queryJs.cols = [[
	        {
	            "title": $xcp.i18n("papactpl.tplName"),"field": "tplName","width":"100" 
	        },{
	            "title": $xcp.i18n("papactpl.referenceNo"),"field": "referenceNo"  ,"width":"100","sortable":true
	        },{
	            "title": $xcp.i18n("papactpl.relatedNo"),"field": "relatedNo","width":"100","sortable":true
	        },{
	            "title": $xcp.i18n("papactpl.narrative"),"field": "narrative","width":"100","sortable":true
	        },{
	            "title": $xcp.i18n("papactpl.sender"),"field": "sender","width":"100","sortable":true
	        },{
	            "title": $xcp.i18n("papactpl.receiver"),"field": "receiver","width":"100","sortable":true
	        },{
	            "title": $xcp.i18n("papactpl.msgType"),"field": "msgType","width":"100","sortable":true
	        }
	      ]];
		queryJs.url = "./packetsTemplate.do?method=n99tpl"; 
		queryJs.dblClickTrue = function(rowData){
			loadN99Tpl(rowData);
		};
		var qrParams = {"msgTypeQuery" : str};
		  
		$('#queryTemplatePacket_letterTable').datagrid({
			loadMsg : $xcp.i18n("sys.loadMsg"),
			columns:queryJs.cols,
			url:queryJs.url,
			width: "900",
			height: "510",
			fit   : true,
			fitColumns: false,
			pageSize: 10,            //每页显示的记录条数，默认为10  
		    pageList: [5,10,15,20],  //可以设置每页记录条数的列表  
			pagination:true,
			rownumbers:true,
			queryParams : qrParams,
			onDblClickRow: function(rowIndex, rowData){
				queryJs.dblClickTrue(rowData);
				$("#querySendPacket_condition").remove();
				$("#queryTemplatePacket_condition").remove();
				$("#packetTemplate").window('destroy');
			}
		});
		 
		var options = [
     	      {id:'tplName',eleType:'easyui-validate',dataType:'0',disName:$xcp.i18n('papactpl.tplName'),i18n:'papactpl.tplName',isMainCondition:true},
     	      {id:'referenceNo',eleType:'easyui-validate',dataType:'0',disName:$xcp.i18n('papactpl.referenceNo'),i18n:'papactpl.referenceNo',isMainCondition:true},
     	      {id:'relatedNo',eleType:'easyui-validate',dataType:'0',disName:$xcp.i18n('papactpl.relatedNo'),i18n:'papactpl.relatedNo',isMainCondition:true}
     	];
		var allparams = {
			title   : $xcp.i18n("packets.sendQuery"),
			options : options,
			queryID : "queryTemplatePacket"
		};
		$xcp.comQueryCondMgr.init(allparams);
	}
	
	function packetsTemplate(){
		var list = getPacketsSel();
		var flag = false;
		var str = "";
		var n99 = "";
		$(list).each(function(i,o){
			var temp = o.msgType;
			if(temp.indexOf("_") != -1){
				temp = temp.substring(0,temp.indexOf("_"));
			}
			if(temp.indexOf("99") != -1){
				flag = true;
				n99 += temp + ",";
			}
			str += temp + ",";
		});
		if(str.length == 0){
			 $.messager.alert($xcp.i18n("paramMar.tisxx"),$xcp.i18n("packets.selectedPacket"));
			 return ;
		}
		
		if(str.substring(str.length -1, str.length) == ","){
			str = str.substring(0, str.length -1);
		}
		if(n99.substring(n99.length -1, n99.length) == ","){
			n99 = n99.substring(0, n99.length -1);
		}
		
		var html = createTemplateHtml(flag); //创建html
		$(html).appendTo('body');
		$.parser.parse($("#packetTemplate"));
		
		$('#packetTemplate').window({
			modal:true,
			closed:false,
			inline : true,
			collapsible : false,
			width: "900",
			height:400,
			minimizable : false,
			title : $xcp.i18n("formPubMgr.importPacketsTemplate"),
			onClose     : function(){
				$("#querySendPacket_condition").remove();
				$("#queryTemplatePacket_condition").remove();
				$xcp.comQueryCondMgr.destroyCommQueryCondWrap();
				$(this).window('destroy');
			},
			onDestroy : function(){
			}
		});
		$('#packetTemplateTabs').tabs({
			onSelect : function(){
				$(window).trigger("resize");
			}
		});
		createSendPacket(str);
		
		if(flag){
			createTemplatePackets(n99);
		}
	}

	function expRegAddPack(tid){
		var str = $.extend(true,{},$xcp.PacLoad.defaults.expftRegList);
		if(!str || str.length == 0)return ;
		 
		$.each(str,function(i,o){
	    	//查看tab列表是否有此报文名称 有则返回最大值
	    	o.pacName = resolveTabDataMax(o.pacName);
	    	o.tid = tid;
			$("#"+$xcp.PacLoad.defaults.tableDivId).datagrid('insertRow',{row : o});
			loadPackets(o.pacName,o.sendDef == true ? "Y" : "N",null,"N"); 
		});
	}
	
	function expRegDelPack(tid){
		 if(tid == "" || !tid) return;
		 
		 var target = $("#"+$xcp.PacLoad.defaults.tableDivId);
		 var tabData =  $.extend({},$(target).datagrid("getData").rows);
		 $.each(tabData||[],function(i,o){
			 if(o && o.tid == tid){
				 var  indexRows= $(target).datagrid("getRowIndex",o);
				 $(target).datagrid('deleteRow',indexRows);
				 $xcp.PacLoad.loadPackets(o.pacName,"N","true");
			 }
		 });
	}
	
	/**
	 * 提供给循环组件报文处理函数
	 * _this 为对象本身
	 * packName 报文名称
	 * bean 当为true时 只发送  false 为不发送   为undefined 时发送packName 不发送其他同类循环组件报文*/
	function expRegNeedPack(_this,packName,bean){
		 var tid = $(_this).attr("loopCmpId");
		 if(tid == "" || !tid) return;
		 var target = $("#"+$xcp.PacLoad.defaults.tableDivId);
		 var tabData =  $(target).datagrid("getData").rows;
		 var arrList = [];
		 $.each(tabData||[],function(i,o){
			 if(o && o.tid == tid ){ 
				 var oPackName = o.pacName != "" ? o.pacName.split("_")[0] : "";
				 if(oPackName == packName){
					 arrList.push({"packName":o.pacName,"status":"1"});//默认为发送
				 }else{
					 bean = bean ? false : bean;
					 if(bean == true ){
						 arrList.push({"packName":o.pacName,"status":"1"});
					 }else{
						 arrList.push({"packName":o.pacName,"status":"2"});
					 }
				 }
			 }
		 });
		 $xcp.PacLoad.revisePackStu(arrList);
	}
	
	/**是否是循环组件报文*/
	function ifLoop(paName){
		var blean = false;
		$.each($xcp.PacLoad.defaults.expftRegList||[],function(i,o){
			if(o.pacName == paName){
				blean = true;
				return false;
			}
		});
		return blean;
	}
	
	function getLoopId(paName){
		 var tid = '';
		 var target = $("#"+$xcp.PacLoad.defaults.tableDivId);
		 var tabData =  $(target).datagrid("getData").rows;
		 $.each(tabData||[],function(i,o){
			 if(paName == o.pacName){
				 tid = o.tid;return false;
			 }
		 });
		 return tid;
	}
	
	function trggerSetDataEvent(){
		$.each($xcp.PacLoad.defaults.defaultsPactetsLsit||[],function(i,o){
			if($.isFunction(window["setDataF_"+o.pacName])){
				window["setDataF_"+o.pacName]("all");
			}
		});
	}
	
	function validPakctetsRegular(value, param, target, regularFlag){
		if(!value || value == ''){
			return true;
		}
		if($(target).is(":hidden")){
			return true;
		}
		var telexCode = $(target).attr("telexCode");
		var temp = value;
		if(telexCode && telexCode == "1"){
    		var regularZh = /[\u0391-\uFFE5]/;
    		if(regularZh.test(temp)){
    			temp = temp.replace(/[\u0391-\uFFE5]/g, " 0000");
    			if(temp.substring(0,1) == " "){
    				temp = temp.substring(1, temp.length);
    			}
    			$(target).css({"border-color" : "red"});
    			$xcp.PacLoad.defaults.alterBorderColor = false;
    		} else {
    			if($xcp.PacLoad.defaults.alterBorderColor == true){
    				$(target).css({"border-color" : ""});
    			}
    		}
		} 
		var regular = $(target).attr("regular");
		if(!regular){
			target = $("#"+param[0]);
			regular = $(target).attr("regular");
			temp = $(target).xcpVal();
			if(!regular || temp == '') return true;
		}
		regular = regular.replace(new RegExp("\r","g"), "").replace(new RegExp("\n","g"), "\\n");
		regular = regular.replace(new RegExp("rightAngle","g"), ">").replace(new RegExp("leftAngle","g"), "<").replace(new RegExp("quotation","g"), "\"");
		var reg = "^" + regular + "$";
		if(regularFlag === true){
			$.fn.validatebox.defaults.rules.swiftTextFmt.message = $xcp.i18n('validate.packetsError') + " format:"+$(target).attr("format");
		} else {
			$.fn.validatebox.defaults.rules.regular.message = $xcp.i18n('validate.packetsError') + " format:"+$(target).attr("format");
		}
		
		return new RegExp(reg).test(temp);
	}
	
	
	/**提供给手续费发送报文接口*/
	/**
	 * 供报文组件使用，
	 * array[i].mt=RTGS时表示要默认发RTGS202,RTGS103,这些报文的收款行，币种，金额等栏位要自动赋值
	 * array[i].mt=MT时表示要默认发202,103,202COV,这些报文的收款行，币种，金额等栏位要自动赋值
	 * array[{curSign: 'xxx', acctNo:'xxx',amt:888,mt:'RTGS or MT',swiftCode:'CITIUS33'},{},...]
	 */
	function revisePackForChargeFlow(pList){ 
		if(inited == false)return;
		expRegDelPack("changeFlow");
		if(!pList || pList.length == 0) return;
		$.each(pList,function(i,o){
			setPackForCFlow(i,o);
		});
	}
	
	function setPackForCFlow(index,obj){
		var mtStr = {"MT"   : ["MT202","MT103","MT202COV"],
					 "RTGS" : ["RTGS202","RTGS103"]};
		if($.isEmptyObject(obj))return;
		$.each(mtStr[obj["mt"]],function(i,o){
			var jon = {};
			jon.pacName = o + "_c_" + index;
			jon.tid = "changeFlow";
			
			jon.sendDef = true;
			funlsPack[o] = o;
			
			$("#"+$xcp.PacLoad.defaults.tableDivId).datagrid('insertRow',{row : jon});
			$xcp.PacLoad.defaults.sendSuccessFunRegs[jon.pacName] = function(){
				eval("set" + o + "Data_changeFlowFun")(obj,jon.pacName);
			};
			loadPackets(jon.pacName,jon.sendDef == true ? "Y" : "N",null,"N");
		});
	}
	
	function setMT202Data_changeFlowFun(_obj,pName){
		var str =  {"_Receiver" : "swiftCode" ,"_F32A_Currency":"curSign",
					"_F32A_Amount":"amt"};
		setData_changeFlow(str,_obj,pName);
	}
	function setMT103Data_changeFlowFun(_obj,pName){
		var str =  {"_Receiver" : "swiftCode" ,"_F32A_Currency":"curSign",
					"_F32A_Amount":"amt"};
		setData_changeFlow(str,_obj,pName);
	}
	function setMT202COVData_changeFlowFun(_obj,pName){
		var str =  {"_Receiver" : "swiftCode" ,"_SeqA_F32A_Currency":"curSign",
					"_SeqA_F32A_Amount":"amt"};
		setData_changeFlow(str,_obj,pName);
	}
	function setRTGS202Data_changeFlowFun(_obj,pName){
		var str =  {"_Receiver" : "swiftCode" ,"_F32A_Currency":"curSign",
				"_F32A_Amount":"amt"};
		setData_changeFlow(str,_obj,pName);
	}
	function setRTGS103Data_changeFlowFun(_obj,pName){
		var str =  {"_Receiver" : "swiftCode" ,"_F32A_Currency":"curSign",
				"_F32A_Amount":"amt"};
		setData_changeFlow(str,_obj,pName);
	}
	function setData_changeFlow(str,_obj,pName){
		$.each(str,function(i,o){
			$("#" + pName + i).xcpVal(_obj[o]);
		});
	}
	
	function compInit(){
		if(inited === false){
			$xcp.PacLoad.init();
		}
	}
	
	function validate(){
	}
	
	function resize(){
		 $("#" + $xcp.PacLoad.defaults.divPacketsId).tabs("resize");
		 $("#" + $xcp.PacLoad.defaults.tableDivId).datagrid("resize");
	}
	
	//主动触发外部函数
	function fireChangeEvent(){		
		$.each(fireOutFuns,function(id,fun){
			if($.isFunction(fun)){
				fun();
			}
		});
	}
	
	
	return { //TODO  
		/**
		 * 报文初始化入口 
		 * 1. 生成 datagrid 静态html   
		 * 2. 解析datagrid  值 默认发送报文 和新增发送报文列表 显示报文列表
		 */
		init : function(){
			if(inited == true)return;
			inited = true;
			$.extend(this.defaults, this.options || {}); 
			
			createTabPac();
			init(); 
		},
		defaults : {
		    missingMessage  : $xcp.i18n("packets.baowgsbzq"),
		    tableDivId      : "divPacketsIdDiv",
		    divPacketsId    : "divPacketsId",  //页面报文 div - id
	        classPacReview  : ".COMPOSITE",   //复合域    class 名称
		    classPacSelect  : ".OPTION",      //选择域    class 名称
		    classPacCycle   : ".LOOP",        //循环域    class 名称
		    classPacketsHtml : ".DIVMT[packetsType='Y']",  //页面标签是否是选择报文的标签
		    classPackDocument : "input[type],textarea,select",
		    messageInfoList : "messageInfoList",
		    unSendMsgList   : "unSendMsgList",
		    operMsgList   : "operMsgList",
		    addPactetsLsit  : [],      //新增报文列表
		    defaultsPactetsLsit  : [], //默认报文列表
		    expftRegList : [],		   //循环组件列表
		    defaultsPackData     : {},
		    packStu     : {},	//报文发送状态对象
		    isOperate   : true,
		    alterBorderColor : true,
		    sendSuccessFunRegs : {},
		    isPackBusjfs    : "1" //1为显示报文列表的不实际发送列 其他值为不显示
		},
		loadPackets : function(pacName,isY,_del,numOder){
			loadPackets(pacName,isY,_del,null,numOder);
		},
		commit : function(){
			compInit();
			submitPackets();
		},
		datagEventBind : function(_this){
			datagEventBind(_this);
		},
		setDefaultsPackets : function(options){
			$.extend(this.defaults, options || {}); 
		},
		showPacketsData : function(){
			if($xcp.task.isUpdateData()){
				compInit();
			}
			return showPacketsData();
		},
		packValidate : function(packObj){
			return packValidate(packObj);
		},
		homePacketsSetData : function(pacNameF){
			homePacketsSetData(pacNameF);
		},
		getFeePackets : function(notSed){//返回发送和不实际发送报文接收行 方法
			if(inited == false) return [];
//			compInit();
			return getFeePackets(notSed);
		},
		packetsOverallHideCon : function(packObj,oKey){//报文页面按钮事件 显示和隐藏数组里面div
			packetsOverallHideCon(packObj,oKey);
		},
		resolveFunSpan : function(objPackName){//报文预览页面切换PCC格式
			resolveFunSpan(objPackName);
		},
		bindClauseSelect : function(clause, prefix){
			bindClauseSelect(clause, prefix);
		},
		//修正报文发送状态
		revisePackStu : function(pacList){
			revisePackStu(pacList);
		},
		//修正报文列表状态
		revisePackList : function(pacList){
			compInit();
			revisePackList(pacList);
		},
		getPacktStus : function(packName){
			return getPacktStus(packName);
		},
		waitPacketsSave : function (rowData){
			waitPacketsSave(rowData);
		},
		setMsgField : function(obj,pacName,arrMsg,stu){
			setMsgField(obj,pacName,arrMsg,stu);
		},
		setMsgClass : function(pacName,arrMsg,stu){
			setMsgClass(pacName,arrMsg,stu);
		},
		setMsgSearchbox : function(pacName,arrMsg){
			setMsgSearchbox(pacName,arrMsg);
		},
		setMsgChg : function(pacName,arrMsg,stu){
			setMsgChg(pacName,arrMsg,stu);
		},
		bindFormFldEvent : function(msgArr,funName){
			bindFormFldEvent(msgArr,funName);
		},
		jumpCurrentLocation : function(tabid,subid){
			jumpCurrentLocation(tabid,subid);
		},
		ReceiverDbCli : function(_this){
			ReceiverDbCli(_this);
		},
		packExpoertSubmit : function(packName,msgId){
			packExpoertSubmit(packName,msgId);
		},
		//导入报文模板
		packetsTemplate : function (){
			compInit();
			packetsTemplate();
		},
		expRegAddPack : function(tid){
			expRegAddPack(tid);
		},
		expRegDelPack : function(tid){
			expRegDelPack(tid);
		},
		showIncomePackets : function (){
			showIncomePackets();
		},
		expRegNeedPack : function(_this,packName,bean){
			expRegNeedPack(_this,packName,bean);
		},
		//导入业务模板的时候触发报文默认列表对应的获取业务栏位的事件
		trggerSetDataEvent : function(){
			trggerSetDataEvent();
		},
		validPakctetsRegular : function(value, param, target, regularFlag){
			return validPakctetsRegular(value, param, target, regularFlag);
		},
		//提供给手续费发送报文接口
		revisePackForChargeFlow : function(pList){
			compInit();
			revisePackForChargeFlow(pList);
		},
		//获取组件加载状态
		getInitState : function(){
			return inited;
		},
		//设置组件加载状态
		setInitState : function(blean){
			inited = blean;
		},
		validate : function(){
			validate();
		},
		resize: function (){
			resize();
		},
		resolvePacketsJson: function (data){
			return resolvePacketsJson(data);
		},
		resolveErrorCode : function(retult){
			return resolveErrorCode(retult);
		},
		getPacketsSel : function(){
			return getPacketsSel();
		},
		rtuPacName :function(pacName,stu){
			return rtuPacName(pacName,stu);
		},
		//添加注册函数
		addFireOutFun : function(fun){
			fireOutFuns[fun] = fun;
		},
		searchFmt : function(prefix){
			searchFmt(prefix);
		}
	};
}(jQuery);

if($xcp.compMgr){
	$xcp.compMgr.addComponent("divPacketsId",$xcp.PacLoad,$xcp.i18n("packets.messages"));
}
