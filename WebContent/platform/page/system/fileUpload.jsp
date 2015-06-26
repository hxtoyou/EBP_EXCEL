<%@page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<script type="text/javascript">
	$(function() {
		$("#file_upload").uploadify({
			'height' : 27,
			'width' : 80,
			'buttonText' : '浏览',
			'swf' : rootPath + "flash/uploadify.swf",
			'uploader' : $xcp.def.getFullUrl('./servlet/FileUploadServlet.do'),
			'auto' : false,
			// 'fileTypeExts'  : '*.xls',
			'formData' : {},
			'multi' : true,
			removeCompleted : false,
			'onUploadStart' : function(file) {
				console.info('file---->>>' + file);
			}
		});

		$('#upload_btn').click(
				function() {
					$.post($xcp.def.getFullUrl('./servlet/FileUploadServlet.do'),
							{}, function(data) {
								if (data == "unclassify") {
									alert(data);
								} else if (data == "undefined") {
									alert("出错！！");
								} else {
									$('#file_upload').uploadify('upload','*');
								}
							});
				});

		$('#cancel_upload_btn').click(function() {
			$('#file_upload').uploadify('cancel', '*');
		});

		$('#fileList').datagrid({
			loadMsg : "正在获取数据......",
			singleSelect : true,
			title : '附件列表',
			url : $xcp.def.getFullUrl('./common.do?method=getHistory&bizNo='),
			width : "900",
			height : "auto",
			fit : true,
			fitColumns : true,
			columns : [ [ {
				title : '文件编号',
				field : 'fileNo',
				width : 150,
				align : 'center'
			}, {
				title : '文件名称',
				field : 'fileName',
				width : 100,
				align : 'center'
			}, {
				title : '上传时间',
				field : 'creatDate',
				width : 100,
				align : 'center'
			}, {
				title : '上传人',
				field : 'op',
				width : 100,
				align : 'center'
			}

			] ],
			pagination : true,
			rownumbers : true,
			onDblClickRow : function(rowIndex, rowData) {
			}
		});
	});
</script>
<input type="file" name="uploadify" id="file_upload" />
<a id="upload_btn" class="easyui-linkbutton" data-options="plain:true"
	href="javascript:void(0)">开始上传</a>
<a id="cancel_upload_btn" class="easyui-linkbutton" data-options="plain:true"
	href="javascript:void(0)">取消所有</a>
<table id="fileList" class="easyui-datagrid">
</table>
</html>