$(function() {

	initWorkFlowData();
});

function initWorkFlowData(){
	$xcp
	.ajax({
		url : $xcp.def
				.getFullUrl("./transitTradeQuery.do?method=readWFPXml&templateNo="+templateNo),				
		async : true
	},null,function(result){
		if(result && result != null){
			var nodeIdArr = nodeIds.split(",");
			var active = {};
			var activeRects = [];
			var historyRects = [];
			var endRects = [];
			var data = $xcp.parseJson(result.templateData);
			var states = data.states;
			var paths = data.paths;
			$.each(states,function(sIdx,sEle){
				var eId = sEle.props.nodeId.value;
				if($.inArray(eId,nodeIdArr) >= 0){
					var row = {};
					row.id = eId;
					row.paths = [];
					activeRects.push(row);
				}
				if(eId == nodeIdArr[nodeIdArr.length - 1]){
					var row = {};
					row.id = eId;
					row.paths = [];
					endRects.push(row);
				}
			});
			$.each(nodeIdArr,function(idx,ele){
				if(idx != 0){
					var fromId = nodeIdArr[idx - 1];
					var toId = nodeIdArr[idx];
					$.each(paths,function(pIdx,pEle){
						if(pEle.from == "rect"+fromId && pEle.to=="rect"+toId){
							var hisIdx = getIdxInArrById(fromId,historyRects);
							if(hisIdx == null){
								var row = {};
								row.id = fromId;
								row.paths = [];
								row.paths.push(pEle.text.text);
								historyRects.push(row);
							}else{
								historyRects[hisIdx].paths.push(pEle.text.text);
							}
						}
					});
				}
			});
			
			
			active.activeRects = {};
			active.activeRects.rects = activeRects;
			active.historyRects = {};
			active.historyRects.rects = historyRects;
			active.endRects = {};
			active.endRects.rects = endRects;
			var a = $('#workFlow')
			.workFlow(
					$.extend(true,{
						basePath : "",
						restore : $xcp.parseJson(result.templateData),
						editable : false
					},active)//{activeRects:{rects:[{paths:[],name:"经办"}]},historyRects:{rects:[{paths:["startMode = 1"],name:"开始"}]}})
					);
			/*a.setCfgPropsPro(eval({
				"name" : result.templateName,
				"templateNo" : result.templateNo
			}));*/
		}
	});	
	
}
function getIdxInArrById(val,arr){
	var retIdx = null;
	$.each(arr,function(idx,ele){
		if(ele.id == val){
			retIdx = idx;
			return false;
		}
	});
	return retIdx;
}
