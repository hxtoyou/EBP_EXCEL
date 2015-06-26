/***
 * 批注管理
 */
lpbc.annotationMgr = lpbc.annotationMgr = function($){
	
	var chkComment = {},   		//经办更正数据修改信息
		pzList = {} ,			//记录增加批注的字段
		opComment = {},    		//复合批注的信息 经办更正使用
		hisInfo = "";
		COData = {};
		oldPzList = {};
		
	
	//颜色属性对象
	var annotStyle = {current:"red",changed:"#0000FF",history:"#4F8D00"};
	
	/**
	 * 初始化方法 初始化批注信息
	 */
	function init(formData){
		COData = formData;
		chkComment = $xcp.parseJson(formData.chkComment || "");
		opComment  = $xcp.parseJson(formData.opComment || "");
		//chkCommentNoAlter =  chkComment.chkCommentNoAlter || "";
		
		if(!$xcp.task.isHandle() && !$xcp.task.isFinish()){
			//如果是经办或者经办更正不允许添加批注
			bindAnnotation();
		}
		if(!$xcp.paramMar){
			showAnnotation(opComment,chkComment);
		}
	};
	
	/**
	 * 绑定批注双击出发事件
	 */
	function bindAnnotation(targetEle,e){
		var clickEvent = targetEle || document;
		$(clickEvent).dblclick(function(e){
			if(e.target instanceof HTMLTableCellElement){
				if($(e.target).find("input").size() == 0){
					var pzName = $(e.target).next().find("[name]").attr("name");
					
					if(pzName == undefined)return false;
					//lsm closest  <--  parents  jQuery.prev()，返回上一个兄弟节点，不是所有的兄弟节点
					var strTar = $('#' + pzName).closest("td").prev();
					if($xcp.task.isCorrect() && $(strTar).css("text-decoration") !="underline")
						return false;
					
					var valuePz =  "";
					var tmpListEle = "";
					if($xcp.paramMar){ //如果是参数页面
						var clospar = $("#closparamId").val();
						tmpListEle = pzList[clospar] ? pzList[clospar][pzName] : "";
					}else{
						tmpListEle = pzList[pzName];
					}
					if(tmpListEle !=undefined   &&  tmpListEle[tmpListEle.length-1]!=undefined && tmpListEle[tmpListEle.length-1]["flag"]=="0"){
						valuePz = tmpListEle[tmpListEle.length-1]["value"];
						if(pzList[pzName]){
							pzList[pzName].splice(-1,1);
						}
					}else{
						valuePz = "";
					}
					
					//创建批注输入窗口
					createDialog(
								{title    : $(e.target).html().replace(new RegExp(":", 'g'),"")+ "  "+ $xcp.i18n("anno.fieldDesc"),
								 taskUuid : uuidHead,
								 method   : "addAnnotation",
								 value    : valuePz||"",
								 name     : pzName});
					
					var obj = pzList[pzName];//oldPzList[pzName];
					var hisStr = "";
					if(obj != undefined){
						$.each(obj,function(i,o){
							//$xcp.i18n("pendingTask.annPerson")+":"+o.creatator+$xcp.i18n("pendingTask.annTime")+":"+o.createTime+$xcp.i18n("pendingTask.annContent")+":"+o.value +"<br>";
							hisStr += o.creatator+"&nbsp;&nbsp;&nbsp;&nbsp;"+o.createTime+"<br>"+o.value +"<br>";
						});
					}
					hisInfo = hisStr;
					$("#hisAnnotation").html(hisStr);
				}
			}
		});
	}
	
	/**
	 * 增加批注
	 */
	function addAnnotation(pzValue,pzName){
		var obj = new Array();
		var opts = {};
		if($.trim(pzValue) == "" ){
			if(pzList[pzName]&& hisInfo==""  ){
				delete pzList[pzName];
				//
				var strTar = $('#' + pzName).closest("td").prev();
				$(strTar).css("text-decoration","");
			}
		}else{
			var paramPz = "";
			opts.value = pzValue;
			opts.creatator = $xcp.getConstant('user')[0].userName;
			var d = new Date(new Date().getTime());
			opts.createTime =  d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
			//opts.createTime =  new Date();
			opts.flag = "0";
			if($xcp.paramMar && $("#closparamId").length > 0){
				paramPz  = $("#closparamId").val();
				if(!pzList[paramPz]){
					pzList[paramPz] = {};
				}
				if(pzList[pzName] != undefined){
					pzList[pzName].push(opts);
				}else{
					obj.push(opts);
					pzList[paramPz][pzName] = obj;
				}
			}else{
				if(pzList[pzName] != undefined){
					pzList[pzName].push(opts);
				}else{
					obj.push(opts);
					pzList[pzName] = obj;
				}
			}
		}
		if(pzName && pzValue.length > 0){
			var strTar = $('#' + pzName).closest("td").prev();
			$(strTar).css("text-decoration","underline");
			//$(strTar).css("color",annotStyle.changed);
		}
	}
	
	/**
	 * 获得批注信息
	 */
	function getAnnotation(){
		var obj = $xcp.parseJson(pzList);
		for(i in obj){
			$.each(obj[i],function(idx,ele){
				//ele.flag = "0";
			});
		}
		return $xcp.toJson(obj);
	}
	
	/**
	 * 展示批注信息
	 */
	function showAnnotation(opComment,chkComment){
		if(!$xcp.formPubMgr.isExistsAsyncInitFun()){
			setTimeout(function(){
				showAnnotation(opComment,chkComment);
			},1000);
		}else{
			var chaName = "";
			if(typeof opComment != "undefined"){
				var opComment = $xcp.parseJson(opComment);
				for ( i in opComment){
					var tdJqueryObj = $('#' + i).closest("td").prev();
					if(!$xcp.task.isFinish()){
						 $(tdJqueryObj).css("text-decoration","underline");
						 if(typeof opComment[i] == "object"){
							 $.each(opComment[i],function(j,k){
								 if(k.flag == "0"){
									 $(tdJqueryObj).css("color",annotStyle.changed);
									 k.flag = "1";
									 return false;
								 }
							 });
						 }else{
							 if(opComment[i].flag == "0"){
								 $(tdJqueryObj).css("color",annotStyle.changed);
								 opComment[i].flag = "1";
							 }
						 }
					}
					 
					pzList[i] = opComment[i];
					//oldPzList[i] = opComment[i];
				}
			}
			 
			if(typeof chkComment != "undefined" ){
				 
				var chkComment = $xcp.parseJson(chkComment);
				for ( i in chkComment){
					var oldVal = chkComment[i];
					if($("#"+i).length == 0) return ;
					if($("#"+i).hasClass("easyui-combobox") && !$("#"+i).is(":hidden")){//$("#"+i).parents("div.datagrid-row-detail").length != 0
						var comboxData = $("#"+i).combobox("options");//.data
						var comValField = comboxData["valueField"];
						var comTextField = comboxData["textField"];
						$.each(comboxData.data,function(j,k){
							if(k[comValField] == oldVal){
								oldVal = k[comTextField];
								return false;
							} 
						});
					}
					var tdJqueryObj = $('#' + i).closest("td").prev();
					tdJqueryObj.tooltip({
							position: 'top',
							content:  '<div style="color:#fff">'+(chaName||"")+oldVal+'</div>',
							onShow: function(){
								$(this).tooltip('tip').css({
									backgroundColor: '#666',
									borderColor: '#666'
								});
							} 
					 });
					 if(!$xcp.task.isHandle() && !$xcp.task.isCorrect()){
						 $(tdJqueryObj).css("color",annotStyle.changed);
					 }
				}
			}
		}
	}
	
	function alterDataJxi(jData,cData,subpzList){
		cData.opComment = $xcp.parseJson(cData.opComment);
		jData.chkComment = $xcp.parseJson(jData.chkComment);
		for( i in jData){
			if(cData[i] != undefined && chkCommentDataDel(i) 
					&& cData[i] != null && jData[i] != cData[i]){
				if(cData[i] == "" ){
					subpzList[i] = $xcp.i18n("anno.noData");
				} else {
					subpzList[i] = cData[i];
				}
			}
		}
		
		/*var subpdkf = {};
		for( i in cData.opComment){
			if(subpzList[i] == undefined && i != "opComment" && i!= "chkComment"){
				subpdkf[i] = cData.opComment[i];
			}
		}
		//subpzList["chkCommentNoAlter"] = subpdkf;*/		
		return subpzList;
	}
	
	function paramAlterDataJxi(jData,cData,subpzList){
		var cloas = jData["closparamId"];
		for( i in jData){
			if(cData[i] != undefined && chkCommentDataDel(i) 
					&& cData[i] != null && jData[i] != cData[i]){
				if(!subpzList[cloas]) subpzList[cloas] = {};
				if(cData[i] == "" ){
					subpzList[cloas][i] = "经办数据为空";
				} else {
					subpzList[cloas][i] = cData[i];
				}
			}
		}
		return subpzList;
	}
	
	/**
	 * 经办更正比较数据是否修改
	 */
	function isAlterData(jsonData){
		if(jsonData !== null && pzList != null){
			var pppzList = new Object();
			if($xcp.paramMar){
				var dataParamInfo = $xcp.parseJson(jsonData["sysDataParamInfo"]||[]);
				var oldDataParamInfo = $xcp.parseJson(COData["sysDataParamInfo"]||[]);
				
				var blean = true;
				$.each(dataParamInfo,function(i,k){
					$.each(oldDataParamInfo,function(l,m){
						if(k["closparamId"] == m["closparamId"]){
							pppzList = paramAlterDataJxi(k,m,pppzList);blean = false;
							return false;
						}
					});
					if(blean){
						//新增加的数据
					}
				});
			}else{
				pppzList = alterDataJxi(jsonData,COData,pppzList);
			}
			return $xcp.toJson(pppzList);
		}
		return "";
	}
	
	/**
     * 批注查看信息是否修改的时候 去除隐藏字段和控件的值 
     */
   function chkCommentDataDel(dataKey){
	   	var defaul = {
	   			"messageInfoList":"0","xcpBill":"0",
	   			"chargeHidFieldInfo":"0","fundsFlowHidFieldInfo":"0"
	   	};
	   	
	   	if(defaul[dataKey] != "undefined" && defaul[dataKey] != undefined){
	   		return false;
	   	}
	   	return true;
   }
	
	/**
	 * 弹出框
	 */
	function createDialog(opts){
		var taskUuid = opts.taskUuid || '';
		var html = 	'<div id="'+taskUuid+'-frmDialog"  class="easyui-dialog" style="width:450px;height:260px;padding:10px">';
			html += 	'<div id=\"hisAnnotation\" style=\"width:412px;height:75px;background:none repeat scroll 0 0 #CCCCCC;overflow-y: scroll;\"></div>';
			html += 	'<textarea class="pubFrmDialogOpinion" cols=75 rows=5 style="width:410px;border: 1px solid #999999;" ></textarea>';
			html += '	<div id="dlg-buttons">';
			html += 		'<a  class="easyui-linkbutton  pubDialogSaveBtn" data-options="plain:true">'+$xcp.i18n('app.confirm')+'</a>';
			html += 		'<a id="frm-DelClose" class="easyui-linkbutton pubDialogCloseBtn" data-options="plain:true">'+$xcp.i18n('app.cancel')+'</a>';
			html += 	'</div>';
			html += '</div>';
		
		$(html).appendTo('body');
		$.parser.parse($("#"+taskUuid+"-frmDialog"));
		
		$('#'+taskUuid+'-frmDialog').dialog({
			title   : opts.title,
			modal   : true,
			iconCls : 'icon-tip',
			buttons : '#dlg-buttons',
			onClose     : function(){
				$(this).dialog('destroy');
			}
		});
		
		
		$('#'+taskUuid+'-frmDialog').find('a.pubDialogSaveBtn').bind('click',function(){
			if(opts.method){
				var frmDialogVal = $('#'+taskUuid+'-frmDialog').find('.pubFrmDialogOpinion').xcpVal();
				$('#'+taskUuid+'-frmDialog').dialog('destroy');
				if($xcp.formPubMgr[opts.method]){
					$xcp.formPubMgr[opts.method](frmDialogVal,opts.name || "");
				}else{
					if(opts.data){
						opts.method(frmDialogVal,opts.data);
					}else{
						opts.method(frmDialogVal);
					}
				}
					
			}else{
				$('#'+taskUuid+'-frmDialog').dialog('destroy');
			}
		});
		
		$('#'+taskUuid+'-frmDialog').find('a.pubDialogCloseBtn').bind('click',function(){
			$('#'+taskUuid+'-frmDialog').dialog('destroy');
		});
		if(opts.value){
			$('#'+taskUuid+'-frmDialog').find(".pubFrmDialogOpinion").val(opts.value);
		}
		
		if($xcp.task.isCorrect()){
			$('#'+taskUuid+'-frmDialog').find('.pubFrmDialogOpinion').hide();
			$("#hisAnnotation").css("height",$('#'+taskUuid+'-frmDialog').find('.pubFrmDialogOpinion').height()+$("#hisAnnotation").height());
			$("#dlg-buttons").find(".pubDialogSaveBtn").hide();
			
		}
	}

	function paramOpComment(paramId){
		showAnnotation(opComment[paramId],chkComment[paramId]);
	}
	
	function paramChkComment(paramId){
		showAnnotation(opComment[paramId],chkComment[paramId]);
	}
	function showAnnotationSpr(data,doum){
		var obj;
		$.each(data,function(i,o){
			var eventThis = $("#" + i);
			obj = eventThis.closest("td").prev();
			
			if(eventThis.hasClass("easyui-combobox")){
				eventThis.xcpVal(o);
				o = eventThis.combobox("getText");
			}
			o = o == "" ? " 空  " : o;
			
			obj.tooltip({
				position: 'top',
				content:  '<div style="color:#fff">' + o + '</div>',
				onShow: function(){
					$(this).tooltip('tip').css({
						backgroundColor: '#666',
						borderColor: '#666'
					});
				} 
			});
			obj.css("color",annotStyle.changed).attr("annotation","annotation");
		});
	}
	function closeAnnotationSpr(doum){
		doum =  doum || document;
		$("td[annotation]",$(doum)).tooltip("destroy").css("color","");
	}
	
	return {
		
		init : function(formData){
			init(formData);
		},
		
		addAnnotation :function(value,name){
			addAnnotation(value,name);
		},
		
		getAnnotation : function(){
			return getAnnotation();
		},
		paramChkComment : function(paramId){
			paramChkComment(paramId);
		},
		paramOpComment : function(paramId){
			paramOpComment(paramId);
		},
		isAlterData : function (jsonData){
			return  isAlterData(jsonData);
		},showAnnotationSpr : function(data,doum){
			showAnnotationSpr(data,doum);
		},
		closeAnnotationSpr : function(doum){
			closeAnnotationSpr(doum);
		} 
		
	};
	
}(jQuery);