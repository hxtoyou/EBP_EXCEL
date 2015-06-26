(function($){
var workFlow = $.workFlow;

$.extend(true,workFlow.config.props.props,{
	name : {name:'name', label:'名称', value:'新建流程', editor:function(){return new workFlow.editors.inputEditor([{disabled:true}]);}},
	templateNo : {name:'templateNo', label:'编号', value:'', editor:function(){return new workFlow.editors.inputEditor([{disabled:true}]);}}
});

$.extend(true,workFlow.config.tools.states,{
	start : {
				showType:'node',
				type : 'start',
				name : {text:'<<start>>'},
				text : {text:'开始'},
				img : {src : 'js/workFlow/img/16/start_event_empty.png',width : 16, height:16},
				props : {
					text: {name:'text',label: '显示', value:'', editor: function(){return new workFlow.editors.textEditor();}, value:'开始'},
					nodeId: {name:'nodeId', label : '节点ID', value:'0000', editor: function(){return new workFlow.editors.inputEditor([{disabled:true}]);}}
				}},
			end : {
				showType:'node',
				type : 'end',
				name : {text:'<<end>>'},
				text : {text:'结束'},
				img : {src : 'js/workFlow/img/16/end_event_terminate.png',width : 16, height:16},
				props : {
					text: {name:'text',label: '显示', value:'', editor: function(){return new workFlow.editors.textEditor();}, value:'结束'},
					nodeId: {name:'nodeId', label : '节点ID', value:'', editor: function(){return new workFlow.editors.inputEditor([{disabled:true}]);}}
				}},
			task : {
				showType:'node',
				type : 'task',
				name : {text:'<<task>>'},
				text : {text:'任务'},
				img : {src : 'js/workFlow/img/16/task_empty.png',width :16, height:16},
				props : {
					text: {name:'text', label: '显示', value:'', editor: function(){return new workFlow.editors.textEditor();}, value:'任务'},
					nodeId: {name:'nodeId', label : '节点ID', value:'', editor: function(){return new workFlow.editors.inputEditor([{disabled:true}]);}},
//					nodeId: {name:'nodeId', label : '节点ID', value:'', editor: function(){return new workFlow.editors.inputEditor([{disabled:true}]);}},
					stepNo: {name:'stepNo', label: '步骤编号', value:'4004', editor: function(){return new workFlow.editors.selectEditor('js/data/stepNo.json');}},
					nodeTypeE: {name:'nodeTypeE', label: '节点类型', value:'3', editor: function(){return new workFlow.editors.selectEditor('js/data/nodeType.json');}},
					jsExp: {name:'jsExp', label: '节点动作js', value:'', editor: function(){return new workFlow.editors.textAreaEditor([{style:'width:195px;height:150px;resize:none;'}]);}},
					loadPct: {name:'loadPct', label: '负荷占比（%）', value:'', editor: function(){return new workFlow.editors.inputEditor();}},
					timeOut: {name:'timeOut', label: '超时时长', value:'', editor: function(){return new workFlow.editors.inputEditor();}},
					exeType: {name:'exeType', label: '执行方式', value:'1', editor: function(){return new workFlow.editors.selectEditor('js/data/exeType.json');}},
					permType: {name:'permType', label: '权限模式', value:'1', editor: function(){return new workFlow.editors.selectEditor('js/data/permType.json');}},
					permStep: {name:'permStep', label: '权限步骤', value:'', editor: function(){return new workFlow.editors.inputEditor();}}
				}}
});
})(jQuery);