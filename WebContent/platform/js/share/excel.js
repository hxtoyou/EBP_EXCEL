$(function() {
	// var contextData = '${context.user}';
	var data = JSON.parse(contextData);
	var html = "";
	var pageSize;
	var pageNum;
	var statisticPageNum = 1;
	var statisticPageSize = 10;
	var fileName = "test3.xlsx";
	var totalPage = 0;
	var param = "";
	var templateType = '';
	var types = '';
	var type = '';
	var re = new RegExp("\\{(.*?)\\}");
	var statisticPagination="";
	var contentPagination="";
	var me = this;
	createForm(data);
	/**
	*提交查询
	**/
	$("#submit").click(function() {
		param = $("#form").serialize();
		if (type === "statistic") {
			pageNum = 1;
			pageSize = 10;
		} else if (type === "detail") {
			pageNum = statisticPageNum;
			pageSize = statisticPageSize;
		}
		var opt = "";
		var url = "./preview.do?method=getMsg&" + param;
		$xcp.ajax({
			url: url,
			data: {
				"templateType": type,
				"templateName": fileName,
				"pageNumber": pageNum,
				"pageSize": pageSize
			},
			async: true,
			success: function(data) {
				// var data = JSON.parse(data);
				if(type===''){
					templateType = data.outEntity.showType;
					types = templateType.split('&');
					var pagination=data.outEntity.pagination;
					console.log("pagination:",pagination);
					type = types[0];
					$("#swift").removeClass("display_1");
					$("#out").removeClass("display_1");
					if(types.length===1){
						if(types[0]==="detail"){
							 contentPagination=pagination;
							 $("#statistic").addClass("display_a");
							 $("#statisticContent").addClass("display_1");
						}else if(types[0]==="statistic"){
							 statisticPagination=pagination;
							  $("#statistic").addClass("display_a");
							   $("content").addClass("display_1");
						}
					}else if(types.length===2){
						if(types[0]==="detail"){
							 contentPagination=pagination;
							$("#statisticContent").addClass("display_1");
						}else if(types[0]==="statistic"){
							statisticPagination=pagination;
							 $("#bottom").addClass("display_1");
							 $("content").addClass("display_1");
							
						}

					}else{
						console.log("无显示方式参数，默认显示所有");
					}


				}
				console.log("contentPagination",contentPagination);
				console.log("statisticPagination",statisticPagination);
				console.log("type:",type);
				var titleLines = data.outEntity.tableTitle;
				var data = data.outEntity.data;
				data = JSON.parse(data);
				totalPage = data.totalCount;
				console.log("totalPage:",totalPage);
				var contentHtml = "";
				$('#dialog').dialog('close');
				if (type === "detail") {
					generateHtml(contentHtml, "content", data,titleLines);
					if(contentPagination==="YES"){
						setPagination(totalPage, data.result[0].tableWidth,"pp");
					}
				} else {
					generateHtml(contentHtml, "statisticContent", data,titleLines);
					if(statisticPagination==="YES"){
						setPagination(totalPage, data.result[0].tableWidth,"pps");
					}

				}
			},

		});
		// $xcp
	});


	/**
	*生成html
	**/
	function generateHtml(html, statisticContent, data,titleLines) {
		var html = '<table class="ta_1 bg_small" width="' + data.result[0].tableWidth + '" height="auto" border="0" cellpandding="0" cellspacing="0">';
		var tag=0;
		var titleTag=0;
		$.each(data.result[0].htmlRowStructure, function(i, value) {
			titleTag++;
			var tLineint = parseInt(titleLines);
			if(titleTag<tLineint){
				html+='<tr class="bg_wirter">';
			}else{
				if(tag===0){
					if(titleTag===tLineint){
						html+='<tr class="h_top">';
					}else{
						html+='<tr class="bg_color">';
					}
					tag=1;
				}else{
					if(titleTag===tLineint){
						html+='<tr class="h_top">';
					}else{
						html+='<tr class="bg_wirter">';
					}
					tag=0;
				}
			}
			$.each(value.htmlTdStructure, function(j, item) {
				var value = "";
				if (!re.test(item.value)) {
					value = item.value;
				}
				var isBold="";
				if(item.isBold===true){
					isBold	= "bolder";
				}
				html += '<td colspan="' + item.colspan + '" rowspan="' + item.rowspan + '" align="center" width="' + item.width + '" height="' + item.height + '" style="font-size:'+item.font_size+'pt;font-family:'
				+item.fontFamily+';color:'+item.font_color+';background-color:'+item.bg_color+';  border-top-width:'+item.border_top+';border-bottom-width:'+
				item.border_bottom+';border-right-width:'+item.border_right+';border-left-width:'+item.border_left+';text-align:'+item.horizontalAlign+';vertical-align:'+item.verticalAlign+';font-weight:'+isBold+';">' + value + '</td>'
			});
			html += '</tr>';
		});
		html += "</table>";
		$("#" + statisticContent).css("width", data.result[0].tableWidth);
		$("#" + statisticContent).html(html);
	}
	/**
	*统计表格与详细数据表格切换
	**/
	$("#statistic").click(function() {
		if (type === "detail") {
			getStatisticData();
			$("#statisticContent").removeClass("display_1");
			$("#statistic").html("切换明细表");
			$("#content").addClass("display_1");
			$("#pp").addClass("display_1");
			console.log("statisticPagination:",statisticPagination);
			if(statisticPagination==="YES"){
				$("#pps").removeClass("display_1");
			}
			$.parser.parse('#statistic');  
			type = "statistic";
			
		} else if (type === "statistic") {
			getData(pageNum, pageSize);
			$("#statisticContent").addClass("display_1");
			$("#content").removeClass("display_1");
			$("#statistic").html("切换统计表");
			$("#pps").addClass("display_1");
			if(contentPagination==="YES"){
				$("#pp").removeClass("display_1");
			}
			type = "detail";
			$.parser.parse('#statistic');  
		}

	});
	/**
	*获取统计数据表格
	**/
	function getStatisticData() {
		$xcp.ajax({
			type: 'POST',
			url: "./preview.do?method=getMsg&" + param,
			async: true,
			data: {
				"templateType": "statistic",
				"templateName": fileName,
				"pageSize": me.pageSize,
				"pageNumber": me.pageNum
			},
			success: function(data) {
				// var data = JSON.parse(data);
				var titleLines = data.outEntity.tableTitle;
				statisticPagination = data.outEntity.pagination;
				console.log("titleLines",statisticPagination);
				var data = data.outEntity.data;
				data = JSON.parse(data);
				totalPage = data.totalCount;
				console.log("totalPage:",totalPage);
				var statisticHtml = "";
				generateHtml(statisticHtml, "statisticContent", data,titleLines);
				if(statisticPagination==="YES"){
					setPagination(totalPage, data.result[0].tableWidth,"pps");
				}else{
					$("#pps").addClass("display_1");
				}
			},
			error: function() {
				console.log("error");
			} 
		});
	}
	/**
	*获取详细数据表格
	**/
	function getData(pageNumber, pageSize) {
		$xcp.ajax({
			type: 'POST',
			url: "./preview.do?method=getMsg&" + param,
			async: true,
			data: {
				"templateType": "detail",
				"templateName": fileName,
				"pageNumber": statisticPageNum,
				"pageSize": statisticPageSize
			},
			success: function(data) {
				var titleLines = data.outEntity.tableTitle;
				contentPagination=data.outEntity.pagination;
				console.log("titleLines",titleLines);
				var data = data.outEntity.data;
				data = JSON.parse(data);
				totalPage = data.totalCount;
				console.log("totalPage:",totalPage);
				var contentHtml = "";
				generateHtml(contentHtml, "content", data,titleLines);
				if(contentPagination==="YES"){
					setPagination(totalPage, data.result[0].tableWidth,"pp");
				}else{
					$("#pp").addClass("display_1");
				}
			},
			error: function() {
				console.log("error");
			}
		});
	}
	/**
	*弹出查询窗口
	**/
	$("#query").click(function() {
		$('#dialog').dialog('open');
	});
	/**
	*分页控件
	**/
	function setPagination(totalPage, tableWidth,id) {
		if($("#"+id).hasClass("display_1")){
			$("#"+id).removeClass("display_1");
		}
		$("#"+id).pagination({
			total: totalPage,
			// pageSize:5 ,
			pageList: [1, 3, 5, 10, 15, 20],
			onSelectPage: function(pageNumber, pageSize) {
				console.log(pageNumber);

				if(id==="pp"){
					statisticPageNum = pageNumber;
					statisticPageSize = pageSize;
					getData(statisticPageNum, statisticPageSize);
				}else if(id==="pps"){
					me.pageNum=pageNumber;
					me.pageSize=pageSize;
					getStatisticData();
				}
				$(this).pagination('loading');
				// alert('pageNumber:'+pageNumber+',pageSize:'+pageSize);
				$(this).pagination('loaded');
			},
			onChangePageSize: function(pageSize) {
				console.log("id",id);
				statisticPageSize = pageSize;
				// getData(statisticPageNum, statisticPageSize);
			}
		});
		// $('#pp').css("width", tableWidth + "px");
			$.parser.parse("#"+id);  
	}


	/**
	*根据后台传回数据生成查询窗口
	**/
	function createForm(data) {
		$("#form").html(html);
	html+="<table class='table_tc commQueryCondWrap'  border='0'>";
		for (var i = 0; i <=data.length/2; i++) {
			html+="<tr>";
			if(i*2<data.length){
				var value = data[i*2];
				html+="<td class='bzh'>"+value.name+":</td>";
				if (value.comboAttr == "") {
					html+="<td><input id='" + value.varName + "' class='tc_input " + value.classType + " ' name='" + value.varName + "'></td>";
				} else {
					html += "<div class='input'><td><input id='" + value.varName + "' class='tc_input " + value.classType + " ' name='" + value.varName + "' " + "data-options=valueField:'val',textField:'name',data:$xcp.getConstant('" + value.comboAttr + "'),></input></td>"
				}
			}
			if((i*2+1)<data.length){
				var value = data[i*2+1];
				html+="<td class='bzh'>"+value.name+":</td>";
				if (value.comboAttr == "") {
					html+="<td><input id='" + value.varName + "' class='tc_input " + value.classType + " ' name='" + value.varName + "'></td>";
				} else {
					html += "<td><input id='" + value.varName + "' class='tc_input " + value.classType + " ' name='" + value.varName + "' " + "data-options=valueField:'val',textField:'name',data:$xcp.getConstant('" + value.comboAttr + "'),></input></td>"
				}
			}
			html+="</tr>";
		};

		html+="</table>";
		var lines = Math.ceil(data.length/2) ;
		// if(data.length%2!==0){
		// 	lines = data.length/2+2;
		// }else{
		// 	lines = data.length/2+1;
		// }
		var height = (lines+3)*40;
        // html+="<div class='tc_dbbox'>";
        // html+="<a href='#' class='tc_buttom easyui-linkbutton' id='submit'>查询</a></div>";
		$("#form").html(html);
		$("#dialog").dialog({
			  title: formName,    
			    width: 550,    
			    height: height,    
			    closed: false,    
			    cache: false,    
			    modal: true,
			    shadow:true,
			    buttons:[{
			    	text:'查询',
					iconCls:'icon-search',
					id:"submit"
			}]  
		});
		$('#dialog').dialog('open');
		$.parser.parse();
	}
	function mouseOver(event){
		this.addClass("bg_colorout");
	}
	function mouseOut(event){
		this.removeClass("bg_colorout");
	}
})