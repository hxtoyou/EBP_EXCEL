draw2d.ResizeImage = function(_title, _url) {
	this.url = _url;
	this.img = null;
	this.title = _title,
	draw2d.Node.call(this);
	this.setDimension(100, 100);
	this.setColor(null);
};
draw2d.ResizeImage.prototype = new draw2d.Node;
draw2d.ResizeImage.prototype.type = "ResizeImage";
draw2d.ResizeImage.prototype.createHTMLElement = function() {
	var item = draw2d.Node.prototype.createHTMLElement.call(this);
	
	this.d = document.createElement("div");
	this.d.style.position = "absolute";
	this.d.style.background = "url(" + this.url + ") no-repeat";
	this.d.style.left = "0px";
	this.d.style.top = "0px";
	this.d.style.textAlign = "center";
	this.d.style.verticalAlign = "text-bottom";
	this.d.style.font = "12px Arial";
	//this.textarea = document.createTextNode(this.title); 
	//this.d.appendChild(this.textarea);
	if(window.navigator.userAgent.toLowerCase().indexOf("msie")>=1){
		this.d.innerText = this.title;
	}
	else{
		this.d.textContent = this.title;
	}
	item.appendChild(this.d);
	
	/*if (navigator.appName.toUpperCase() == "MICROSOFT INTERNET EXPLORER") {
		this.d = document.createElement("div");
		this.d.style.position = "absolute";
		this.d.style.left = "0px";
		this.d.style.top = "0px";
		this.d.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader (src='"
				+ this.url + "', sizingMethod='scale')";
		item.appendChild(this.d);
	} else {
		this.img = document.createElement("img");
		this.img.style.position = "absolute";
		this.img.style.left = "0px";
		this.img.style.top = "0px";
		this.img.src = this.url;
		item.appendChild(this.img);
		this.d = document.createElement("div");
		this.d.style.position = "absolute";
		this.d.style.left = "0px";
		this.d.style.top = "0px";
		item.appendChild(this.d);
	}*/
	item.style.left = this.x + "px";
	item.style.top = this.y + "px";
	return item;
};
draw2d.ResizeImage.prototype.setTitle = function(_title) {
	this.title = _title;
	if(window.navigator.userAgent.toLowerCase().indexOf("msie")>=1){
		this.d.innerText = this.title;
	}
	else{
		this.d.textContent = this.title;
	}
};
draw2d.ResizeImage.prototype.setDimension = function(w, h) {
	try{
		draw2d.Node.prototype.setDimension.call(this, w, h);
		if (this.d !== null) {
			this.d.style.width = this.width + "px";
			this.d.style.height = this.height + "px";
			this.d.style.lineHeight = this.height + "px";
		}
		if (this.img !== null) {
			this.img.width = this.width;
			this.img.height = this.height;
		}
	}catch(e){
	}
};
draw2d.ResizeImage.prototype.setWorkflow = function(_4b06) {
	draw2d.Node.prototype.setWorkflow.call(this, _4b06);
};