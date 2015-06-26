draw2d.WCanvas=function(id){
		draw2d.Workflow.call(this,id);
		this.html.style.backgroundImage="";//remove default backgourd
		//this.html.className="WCanvas";
		this.disabled=false;
		//this.processCategory=null;
		//this.processId=null;
		//this.processName=null;
		this.process=new draw2d.Process();
		//this.listeners=new draw2d.ArrayList();
};

draw2d.WCanvas.prototype = new draw2d.Workflow();
draw2d.WCanvas.prototype.type = "WCanvas";
/*
draw2d.WCanvas.prototype.showConnectionLine=function(x1, y1, x2, y2){
	var connectionLine = new draw2d.DecoratedConnection();
	connectionLine.setStartPoint(x1, y1);
	connectionLine.setEndPoint(x2, y2);
	if (connectionLine.canvas === null) {
		draw2d.Canvas.prototype.addFigure.call(this, connectionLine);
	}
};
*/
draw2d.WCanvas.prototype.setDisabled = function(){
	this.disabled = true;
	return this.readOnly;
};

draw2d.WCanvas.prototype.addFigure = function(figure, xPos, yPos){
	var parent = this.getBestCompartmentFigure(xPos,yPos);
	if(parent === null){
		draw2d.Workflow.prototype.addFigure.call(this,figure, xPos, yPos);
	}else{
		this.getCommandStack().execute(new draw2d.CommandAdd(this,figure,xPos,yPos,parent));
	}
};

draw2d.WCanvas.prototype.addModel = function(figure, xPos, yPos){
	var parent = this.getBestCompartmentFigure(xPos,yPos);
	this.getCommandStack().execute(new draw2d.CommandAdd(this,figure,xPos,yPos,parent));
};

draw2d.WCanvas.prototype.getContextMenu=function(){
	if(this.readOnly)return null;
	var menu =new draw2d.ContextMenu(100, 25);
	var data = {workflow:this};
	menu.appendMenuItem(new draw2d.ContextMenuItem("Properties", "properties-icon",data,function(x,y)
	{
		var data = this.getData();
		var workflow = data.workflow;
		var pid = workflow.processId;
		openProcessProperties(pid);
	}));
	
	return menu;
	
};

draw2d.WCanvas.prototype.onContextMenu=function(x,y){
	if(this.readOnly)return;
	var f = this.getBestFigure(x, y);
	if(f==null)
		f = this.getBestLine(x, y);
	if(f !=null){
		var menu = f.getContextMenu();
		if (menu !== null) {
			this.showMenu(menu, x, y);
		}
	}else{
		var menu = this.getContextMenu();
		if (menu !== null) {
			this.showMenu(menu, x, y);
		}
	}
};

draw2d.WCanvas.prototype.getXMLHeader=function(){
	var xml='<?xml version="1.0" encoding="UTF-8"?>\n';
	return xml;
};

draw2d.WCanvas.prototype.getDefinitionsStartXML=function(){
	var xml = '<definitions>\n';
	return xml;
};

draw2d.WCanvas.prototype.getDefinitionsEndXML=function(){
	var xml='</definitions>\n';
	return xml;
};

draw2d.WCanvas.prototype.toXML=function(){
	var xml = this.getXMLHeader();
	xml = xml+this.getDefinitionsStartXML();
	xml=xml+'<process id="'+this.process.id+'" name="'+this.process.name+'" version="'+this.process.version+'">\n';
	xml=xml+this.process.getDocumentationXML();
	xml=xml+this.process.getExtensionElementsXML();
	var wflowDigramXml='<WFLWDiagram id="WFLWDiagram_'+this.process.id+'">\n'
	wflowDigramXml=wflowDigramXml+'<WFLWPlane wflwElement="'+this.process.id+'" id="WFLWPlane_'+this.process.id+'">\n'
	var models = this.getFigures();
	for(var i=0;i<models.getSize();i++){
		var model=models.get(i);
		for(var j=0;j<DefaultModelTypeEnum.length;j++){
			if(DefaultModelTypeEnum[j]==model.type){
				//alert(model.type);
				xml=xml+model.toXML();
				wflowDigramXml=wflowDigramXml+model.toWFLWDI();
				break;
			}
		}
	}
	var lines = this.getLines();
	for(var i=0;i<lines.getSize();i++){
		var line = lines.get(i);
		for(var j=0;j<DefaultModelTypeEnum.length;j++){
			if(DefaultModelTypeEnum[j]==line.type){
				//alert(line.type);
				xml=xml+line.toXML();
				wflowDigramXml=wflowDigramXml+line.toWFLWDI();
				break;
			}
		}
	}
	xml=xml+'</process>\n';
	wflowDigramXml=wflowDigramXml+'</WFLWPlane>\n'
	wflowDigramXml=wflowDigramXml+'</WFLWDiagram>\n';
	xml=xml+wflowDigramXml;
	xml=xml+this.getDefinitionsEndXML();
	xml=formatXml(xml);
	return xml;
};