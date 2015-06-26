draw2d.DecoratedConnection=function(){
	draw2d.Connection.call(this);
	var decorator = new draw2d.ArrowConnectionDecorator();
	var connectColor = new draw2d.Color(107,136,53);
	decorator.setBackgroundColor(connectColor);
	this.setTargetDecorator(decorator);
	this.setRouter(new draw2d.ManhattanConnectionRouter());
	this.setLineWidth(1);
	this.setColor(connectColor);
	this.lineId=null;
	this.lineName="";
	this.condition="";
	this.sysCondition="";	//$WFLW_APPROVAL='true'(閫氳繃)  $WFLW_APPROVAL='false'(涓嶉�氳繃)
	this.label=null;
	this.listeners=new draw2d.ArrayList();
};
draw2d.DecoratedConnection.prototype=new draw2d.Connection();
draw2d.DecoratedConnection.prototype.type="DecoratedConnection";

draw2d.DecoratedConnection.prototype.getConditionXML=function(){
	var xml = '';
	if(this.condition != null&&this.condition!=''){
		xml = xml + '<conditionExpression><![CDATA['+this.condition+']]></conditionExpression>\n';
	}
	if(this.sysCondition != null&&this.sysCondition!=''){
		xml = xml + '<sysConditionExpression><![CDATA['+this.sysCondition+']]></sysConditionExpression>\n';
	}
	return xml;
}

draw2d.DecoratedConnection.prototype.toXML=function(){
	var sourceId = this.getSource().getParent().eventId;
	/*var type=this.getSource().getParent().type;
	if(type=='draw2d.Start'){
		sourceId = this.getSource().getParent().eventId;
	}	
	else if(type=='draw2d.ExclusiveGateway'){
		sourceId = this.getSource().getParent().gatewayId;
	}else if(type=='draw2d.ParallelGateway'){
		sourceId = this.getSource().getParent().gatewayId;
	}else{
		sourceId = this.getSource().getParent().taskId;
	}*/
	var targetId = this.getTarget().getParent().eventId;
	/*type=this.getTarget().getParent().type;
	if(type=='draw2d.End'){
		targetId = this.getTarget().getParent().eventId;
	}else if(type=='draw2d.ExclusiveGateway'){
		targetId = this.getTarget().getParent().gatewayId;
	}else if(type=='draw2d.ParallelGateway'){
		targetId = this.getTarget().getParent().gatewayId;
	}else{
		targetId = this.getTarget().getParent().taskId;
	}*/
	var xml = '<transaction id="'+this.lineId+'" name="'+trim(this.lineName)+'" sourceRef="'+sourceId+'" targetRef="'+targetId+'">\n';
	xml = xml+this.getConditionXML();
	xml = xml+'</transaction>\n';
	return xml;
};

draw2d.DecoratedConnection.prototype.setLabel=function(text){
	if(this.label == null){
		this.label=new draw2d.Label(text);
		this.label.setFontSize(10);
		this.label.setAlign("left");
		//this.label.setBackgroundColor(new draw2d.Color(230,230,250));
		//this.label.setBorder(new draw2d.LineBorder(1));
		this.addFigure(this.label,new draw2d.ManhattanMidpointLocator(this));
	}else{
		this.label.setText(text);
	}
};

draw2d.DecoratedConnection.prototype.setLineName=function(_lineName){
	this.lineName = _lineName;
};

draw2d.DecoratedConnection.prototype.setCondition=function(_condition){
	this.condition = _condition;
};

draw2d.DecoratedConnection.prototype.setSysCondition=function(_sysCondition){
	this.sysCondition = _sysCondition;
};

draw2d.DecoratedConnection.prototype.toWFLWDI=function(){
	var xml='<WFLWEdge wflwElement="'+this.lineId+'" id="WFLWEdge_'+this.lineId+'">\n';
	var startX = this.getSource().getAbsoluteX();
	var startY = this.getSource().getAbsoluteY();
	var endX = this.getTarget().getAbsoluteX();
	var endY = this.getTarget().getAbsoluteY();
	xml=xml+'<waypoint x="'+startX+'" y="'+startY+'"/>\n';
    xml=xml+'<waypoint x="'+endX+'" y="'+endY+'"/>\n';
	xml=xml+'</WFLWEdge>\n';
	return xml;
};

draw2d.DecoratedConnection.prototype.onDoubleClick=function(){
	var id = this.getId();
	openConnectProperties(id);
};

draw2d.DecoratedConnection.prototype.getContextMenu=function(){
	if(this.workflow.disabled)return null;
	var menu =new draw2d.ContextMenu(100, 50);
	var data = {line:this};
	menu.appendMenuItem(new draw2d.ContextMenuItem("灞炴��", "properties-icon",data,function(x,y)
	{
		var data = this.getData();
		var line = data.line;
		var lid = line.getId();
		if(typeof openConnectProperties != "undefined"){
			openConnectProperties(lid);
		}
	}));
	menu.appendMenuItem(new draw2d.ContextMenuItem("鍒犻櫎", "icon-remove",data,function(x,y)
	{
		var data = this.getData();
		var line = data.line;
		var lid = line.getId();
		var wf = line.getWorkflow();
		wf.getCommandStack().execute(new draw2d.CommandDelete(line));
	}));
	
	return menu;
};

draw2d.DecoratedConnection.prototype.getListener=function(id){
	for(var i=0;i<this.listeners.getSize();i++){
		var listener = this.listeners.get(i);
		if(listener.getId()=== id){
			return listener;
		}
	}
};

draw2d.DecoratedConnection.prototype.deleteListener=function(id){
	var listener = this.getListener(id);
	this.listeners.remove(listener);
};

draw2d.DecoratedConnection.prototype.setListener=function(listener){
	this.listeners.add(listener);
};