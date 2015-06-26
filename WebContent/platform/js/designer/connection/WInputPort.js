draw2d.WInputPort=function(_5920){
draw2d.InputPort.call(this,_5920);
};
draw2d.WInputPort.prototype=new draw2d.InputPort();
draw2d.WInputPort.prototype.type="WInputPort";
draw2d.WInputPort.prototype.setWorkflow=function(){
	draw2d.InputPort.prototype.setWorkflow.call(this,workflow);
};
draw2d.WInputPort.prototype.onDrop=function(port){
	if(port.getMaxFanOut&&port.getMaxFanOut()<=port.getFanOut()){
		return;
	}
	if(this.parentNode.id==port.parentNode.id){
	}else{
		var _5922=new draw2d.CommandConnect(this.parentNode.workflow,port,this);
		var connection = new draw2d.DecoratedConnection();
		var id = "flow"+Sequence.create();
		connection.id=id;
		connection.lineId=id;
		//connection.lineName=id;
		//connection.setLabel(id);
		//connection.setId(id);
		_5922.setConnection(connection);
		this.parentNode.workflow.getCommandStack().execute(_5922);
	}
};
