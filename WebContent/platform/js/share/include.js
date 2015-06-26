$.extend({
     includePath: '',
     include: function(file) {
        var files = typeof file == "string" ? [file]:file;
        for (var i = 0; i < files.length; i++) {
            var name = files[i].replace(/^\s|\s$/g, "");
            var att = name.split('.');
            var ext = att[att.length - 1].toLowerCase();
            var isCSS = ext == "css";
            var tag = isCSS ? "link" : "script";
            var attr = isCSS ? " type='text/css' rel='stylesheet' " : " language='javascript' type='text/javascript' ";
            var link = (isCSS ? "href" : "src") + "='" + $.includePath + name + "'";
            if ($(tag + "[" + link + "]").length == 0) 
            	document.write("<" + tag + attr + link + "></" + tag + ">");
        }
      }
});
 
//获取项目绝对路径 默认是./相对路径
$.includePath = "./";
if(typeof rootPath !==  "undefined"){
	$.includePath =  rootPath;
}

//获取项目当前语言 默认为中文
var includeLAN = "easyui-lang-zh_CN.js";
if(typeof EAP_LANGUAGE !==  "undefined"){
	includeLAN =  "easyui-lang-" + EAP_LANGUAGE + '.js';
}

var pubPltbInudeFiel = 
[
        'platform/css/easyui-themes/icon.css',
        'platform/css/easyui-themes/' + xcp_sys_constant_usertheme + '/easyui.css',
        'platform/css/gjjs-themes/'   + xcp_sys_constant_usertheme + '/uploadify.css',
        'platform/css/gjjs-themes/'   + xcp_sys_constant_usertheme + '/commons.css',
        /*'platform/css/easyui-themes/jquery.autocomplete.css',*/
        'platform/js/jquery-core/jquery.easyui.min.js',
        'platform/js/jquery-core/locale/'+includeLAN,
        'platform/js/jquery-core/jquery.json-2.3.js',
        'platform/js/jquery-core/jquery.easyui-datagrid-detailview.js',
        /*'platform/js/jquery-core/jquery.autocomplete.js',*/
        'platform/js/share/commons.js',
        'platform/js/share/commonsExtend.js',
        'platform/js/share/form.js',
        'platform/js/share/formMenu.js',
        'platform/js/share/commQuery.js',
        'platform/js/share/annotation.js',
        'myDocument/js/tools/myInclude.js',
        'platform/js/share/component.js',
        'platform/js/share/report.js'
];
//加载js文件
$.include(pubPltbInudeFiel);