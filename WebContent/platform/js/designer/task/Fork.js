draw2d.Fork=function(_url){
	draw2d.ResizeImage.call(this, '', _url);
	this.rightOutputPort=null;
	this.leftOutputPort=null;
	this.topOutputPort=null;
	this.bottomOutputPort=null;
	this.eventId='fork';
	this.eventName='分支';
	this.eventAction='';
	this.setDimension(30,30);
};

draw2d.Fork.prototype=new draw2d.Node();
draw2d.Fork.prototype.type="draw2d.Fork";

draw2d.Fork.prototype.generateId=function(){
	this.id="fork"+Sequence.create();
	this.eventId=this.id;
};

draw2d.Fork.prototype.setEventName = function(_eventName){
	this.eventName = _eventName;
};

draw2d.Fork.prototype.setEventAction = function(eventAction){
	this.eventAction = _eventAction;
};

draw2d.Fork.prototype.createHTMLElement=function(){
	var item = draw2d.ResizeImage.prototype.createHTMLElement.call(this);
	return item;
};

draw2d.Fork.prototype.setEventName = function(_eventName) {
	this.eventName = _eventName;
};

draw2d.Fork.prototype.setDimension=function(w, h){
	draw2d.ResizeImage.prototype.setDimension.call(this, w, h);
};

draw2d.Fork.prototype.setWorkflow=function(_4fe5){
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

draw2d.Fork.prototype.getContextMenu=function(){
	if(this.workflow.disabled)return null;
	var menu =new draw2d.ContextMenu(100, 50);
	var data = {task:this};
	menu.appendMenuItem(new draw2d.ContextMenuItem("属性", "properties-icon",data,function(x,y)
	{
		var data = this.getData();
		var task = data.task;
		var tid = task.getId();
		if(typeof openForkProperties != "undefined"){
			openForkProperties(tid);
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

draw2d.Fork.prototype.onDoubleClick=function(event){
	var tid = this.getId();
	if(typeof openForkProperties != "undefined"){
		openForkProperties(tid);
	}
};

draw2d.Fork.prototype.toXML=function(){
	var xml='<forkEvent id="'+this.eventId+'" name="'+this.eventName+'" eventAction="' + this.eventAction + '"></forkEvent>\n';
	return xml;
};

draw2d.Fork.prototype.toWFLWDI=function(){
	var w=this.getWidth();
	var h=this.getHeight();
	var x=this.getAbsoluteX();
	var y=this.getAbsoluteY();
	var xml='<WFLWShape wflwElement="'+this.eventId+'" id="WFLWShape_'+this.eventId+'">\n';
	xml=xml+'<Bounds height="'+h+'" width="'+w+'" x="'+x+'" y="'+y+'"/>\n';
	xml=xml+'</WFLWShape>\n';
	return xml;
};