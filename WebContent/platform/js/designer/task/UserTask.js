draw2d.UserTask=function(_url){
	draw2d.ResizeImage.call(this, '任务', _url);
	this.rightOutputPort=null;
	this.leftOutputPort=null;
	this.topOutputPort=null;
	this.bottomOutputPort=null;
	this.eventId='task';
	this.eventName='任务';
	this.eventAction='';
	this.eventType='0';	//0-主动 1-自动
	this.userType='';	//0-机构 1-岗位 2-操作员
	this.eventUsers='';
	this.passType='0';	//0-默认通过  1-设定数量值 2-操作对象数量 3-操作员数量
	this.passNum='';
	this.setDimension(90,40);
};

draw2d.UserTask.prototype=new draw2d.Node();
draw2d.UserTask.prototype.type="draw2d.UserTask";
draw2d.UserTask.prototype.generateId=function(){
	this.id="task"+Sequence.create();
	this.eventId=this.id;
};

draw2d.UserTask.prototype.setEventName = function(_eventName) {
	this.eventName = _eventName;
	draw2d.ResizeImage.prototype.setTitle.call(this, _eventName);
};

draw2d.UserTask.prototype.setEventAction = function(_eventAction){
	this.eventAction = _eventAction;
};

draw2d.UserTask.prototype.setEventType = function(_eventType){
	this.eventType = _eventType;
};

draw2d.UserTask.prototype.setUserType = function(_userType){
	this.userType = _userType;
};

draw2d.UserTask.prototype.setEventUsers = function(_eventUsers){
	this.eventUsers = _eventUsers;
};

draw2d.UserTask.prototype.setPassType = function(_passType){
	this.passType = _passType;
};

draw2d.UserTask.prototype.setPassNum = function(_passNum){
	this.passNum = _passNum;
};

draw2d.UserTask.prototype.createHTMLElement=function(){
	var item = draw2d.ResizeImage.prototype.createHTMLElement.call(this);
	return item;
};

draw2d.UserTask.prototype.setDimension=function(w, h){
	draw2d.ResizeImage.prototype.setDimension.call(this, w, h);
};

draw2d.UserTask.prototype.setWorkflow=function(_4fe5){
	draw2d.ResizeImage.prototype.setWorkflow.call(this,_4fe5);
	if(_4fe5!==null&&this.rightOutputPort===null){
		this.rightOutputPort=new draw2d.WOutputPort();
		this.rightOutputPort.setMaxFanOut(1);
		this.rightOutputPort.setWorkflow(_4fe5);
		this.rightOutputPort.setName("rightOutputPort");
		this.rightOutputPort.setBackgroundColor(new draw2d.Color(107,136,53));
		this.addPort(this.rightOutputPort,this.width,this.height/2);
	}
	if(_4fe5!==null&&this.leftOutputPort===null){
		this.leftOutputPort=new draw2d.WOutputPort();
		this.leftOutputPort.setMaxFanOut(1);
		this.leftOutputPort.setWorkflow(_4fe5);
		this.leftOutputPort.setName("leftOutputPort");
		this.leftOutputPort.setBackgroundColor(new draw2d.Color(107,136,53));
		this.addPort(this.leftOutputPort,0,this.height/2);
	}
	if(_4fe5!==null&&this.topOutputPort===null){
		this.topOutputPort=new draw2d.WOutputPort();
		this.topOutputPort.setMaxFanOut(1);
		this.topOutputPort.setWorkflow(_4fe5);
		this.topOutputPort.setName("topOutputPort");
		this.topOutputPort.setBackgroundColor(new draw2d.Color(107,136,53));
		this.addPort(this.topOutputPort,this.width/2,0);
	}
	if(_4fe5!==null&&this.bottomOutputPort===null){
		this.bottomOutputPort=new draw2d.WOutputPort();
		this.bottomOutputPort.setMaxFanOut(1);
		this.bottomOutputPort.setWorkflow(_4fe5);
		this.bottomOutputPort.setName("bottomOutputPort");
		this.bottomOutputPort.setBackgroundColor(new draw2d.Color(107,136,53));
		this.addPort(this.bottomOutputPort,this.width/2,this.height);
	}
};

draw2d.UserTask.prototype.getContextMenu=function(){
	if(this.workflow.disabled)return null;
	var menu =new draw2d.ContextMenu(100, 50);
	var data = {task:this};
	menu.appendMenuItem(new draw2d.ContextMenuItem("属性", "properties-icon",data,function(x,y)
	{
		var data = this.getData();
		var task = data.task;
		var tid = task.getId();
		if(typeof openTaskProperties != "undefined"){
			openTaskProperties(tid);
		}
	}));
	menu.appendMenuItem(new draw2d.ContextMenuItem("删除", "icon-remove",data,function(x,y)
	{
		var data = this.getData();
		var task = data.task;
		var tid = task.getId();
		var wf = task.getWorkflow();
		wf.getCommandStack().execute(new draw2d.CommandDelete(task));
	}));
	
	return menu;
};

draw2d.UserTask.prototype.onDoubleClick=function(event){
	var task = this;
	var tid = task.getId();
	if(typeof openTaskProperties != "undefined"){
		openTaskProperties(tid);
	}
};

draw2d.UserTask.prototype.toXML=function(){
	var xml='<taskEvent id="' + this.eventId + '" name="' + this.eventName + '" eventAction="' + this.eventAction + '" eventType="' + this.eventType + '" userType="' + this.userType + '" eventUsers="' + this.eventUsers + '" passType="' + this.passType + '" passNum="' + this.passNum + '" ></taskEvent>\n';
	return xml;
};

draw2d.UserTask.prototype.toWFLWDI=function(){
	var w=this.getWidth();
	var h=this.getHeight();
	var x=this.getAbsoluteX();
	var y=this.getAbsoluteY();
	var xml='<WFLWShape wflwElement="'+this.eventId+'" id="WFLWShape_'+this.eventId+'">\n';
	xml=xml+'<Bounds height="'+h+'" width="'+w+'" x="'+x+'" y="'+y+'"/>\n';
	xml=xml+'</WFLWShape>\n';
	return xml;
};
