draw2d.Start=function(_url){
	draw2d.ResizeImage.call(this, '开始', _url);
	this.rightOutputPort=null;
	this.leftOutputPort=null;
	this.topOutputPort=null;
	this.bottomOutputPort=null;
	this.eventId='start';
	this.eventName='开始';
	this.setDimension(30,30);
};
draw2d.Start.prototype=new draw2d.Node();
draw2d.Start.prototype.type="draw2d.Start";

draw2d.Start.prototype.setEventName = function(_eventName) {
	this.eventName = _eventName;
	draw2d.ResizeImage.prototype.setTitle.call(this, _eventName);
};

draw2d.Start.prototype.createHTMLElement=function(){
	var item = draw2d.ResizeImage.prototype.createHTMLElement.call(this);
	return item;
};

draw2d.Start.prototype.setDimension=function(w, h){
	draw2d.ResizeImage.prototype.setDimension.call(this, w, h);
};

draw2d.Start.prototype.setWorkflow=function(_4fe5){
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

draw2d.Start.prototype.getContextMenu=function(){
	if(this.workflow.disabled)return null;
	var menu =new draw2d.ContextMenu(100, 25);
	var data = {event:this};
	menu.appendMenuItem(new draw2d.ContextMenuItem("Properties", "properties-icon",data,function(x,y)
	{
		var data = this.getData();
		var event = data.event;
		var tid = event.getId();
		if(typeof openNodeProperties != "undefined"){
			openNodeProperties(tid);
		}
	}));
	
	return menu;
};

draw2d.Start.prototype.onDoubleClick=function(event){
	var tid = this.getId();
	if(typeof openNodeProperties != "undefined"){
		openNodeProperties(tid);
		return false;
	}
};

draw2d.Start.prototype.toXML=function(){
	var xml='<startEvent id="'+this.eventId+'" name="'+this.eventName+'"></startEvent>\n';
	return xml;
};

draw2d.Start.prototype.toWFLWDI=function(){
	var w=this.getWidth();
	var h=this.getHeight();
	var x=this.getAbsoluteX();
	var y=this.getAbsoluteY();
	var xml='<WFLWShape wflwElement="'+this.eventId+'" id="WFLWShape_'+this.eventId+'">\n';
	xml=xml+'<Bounds height="'+h+'" width="'+w+'" x="'+x+'" y="'+y+'"/>\n';
	xml=xml+'</WFLWShape>\n';
	return xml;
};