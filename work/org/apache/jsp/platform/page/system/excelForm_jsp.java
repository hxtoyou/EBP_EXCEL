/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.61
 * Generated at: 2015-06-11 08:16:59 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.platform.page.system;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.ebills.util.EbillsCfg;
import org.apache.commons.lang.StringUtils;
import java.util.*;
import java.net.*;
import com.eap.core.*;
import org.apache.commons.bussprocess.context.*;
import org.apache.commons.bussprocess.data.*;

public final class excelForm_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("/WEB-INF/eap.tld", Long.valueOf(1432547002277L));
    _jspx_dependants.put("/platform/page/system/head.jsp", Long.valueOf(1432546944899L));
  }

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      com.eap.channel.web.JspContextServices utb = null;
      utb = (com.eap.channel.web.JspContextServices) _jspx_page_context.getAttribute("utb", javax.servlet.jsp.PageContext.PAGE_SCOPE);
      if (utb == null){
        utb = new com.eap.channel.web.JspContextServices();
        _jspx_page_context.setAttribute("utb", utb, javax.servlet.jsp.PageContext.PAGE_SCOPE);
        out.write('\r');
        out.write('\n');
        out.write('	');

		try {
				utb.initialize(request, response);
		} catch (Exception ex) {
			response.sendRedirect(request.getContextPath()
					+ "/page/session_expire.html");
			return;
		}
	
        out.write('\r');
        out.write('\n');
      }
      out.write('\r');
      out.write('\n');

	Context context = utb.getContext();
	String webAppPath = utb.getWebAppPath();
	String pdfPath = EbillsCfg.getProperty("birt.common.pdf");
	if (null != pdfPath && "" != pdfPath) {
		pdfPath = pdfPath.replace("\\", "/");
	}
	String hostAppPath = "http://"
			+ request.getLocalAddr() + ":"
			+ request.getServerPort() + webAppPath;
	//ç¨æ·ä¸»é¢
	String xcp_sys_usertheme = (String) context.get("userTheme");
	xcp_sys_usertheme = (xcp_sys_usertheme == null || xcp_sys_usertheme
			.equals("")) ? "bootstrap" : xcp_sys_usertheme;
	String EAP_SessionID = utb.getSessionId();
	String xcp_sys_formInitData = "";
	String userId = "";
	String messageOutPut = "";
	try {
		messageOutPut = (String) context.get("messageOutPut");
	} catch (Exception e) {
		System.out.println(e);
	}
	Map<String,String> cfgMap = new HashMap<String, String>();
	try {
		userId = (String) context.getValue("userId");
		xcp_sys_formInitData = (String) context.getValue("initData");
		cfgMap = (Map) context.getValue("cfmMap");
	} catch (Exception e) {
		System.err.println(e);
	}

	String sys_language = (String) context.get("userLanguage");
	String userRptMtd = (String) context.get("userRptMtd");
	String uuidHead = request.getParameter("uuid");
	String taskFlag = request.getParameter("taskFlag");
	String taskState = request.getParameter("taskState");
	String overalParam = request.getParameter("overalParam");

	String sys_workDate = (String) context.get("workDate");

	String latestFlag = request.getParameter("latestFlag") == null
			? ""
			: request.getParameter("latestFlag");
	
	String module_autoTrc = webAppPath + "platform/js/share/autoTrc.js";
	

      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<script>\r\n");
      out.write("\tvar rootPath      = '");
      out.print(webAppPath);
      out.write("';    //æ­¤å¤å®ä¹ç³»ç»çä¸ä¸æwxl\r\n");
      out.write("\tvar hostAppPath   = '");
      out.print(hostAppPath);
      out.write("';   //æå¡å¨çipå ç«¯å£\r\n");
      out.write("\tvar EAP_SID       = '");
      out.print(EAP_SessionID);
      out.write("';\r\n");
      out.write("\tvar EAP_LANGUAGE  = '");
      out.print(sys_language);
      out.write("';  //è®¾ç½®ç³»ç»é»è®¤è¯­è¨\r\n");
      out.write("\tvar xcp_sys_formInitData     = '");
      out.print(xcp_sys_formInitData);
      out.write("'; \r\n");
      out.write("\tvar messageOutPut = '");
      out.print(messageOutPut);
      out.write("';\r\n");
      out.write("\tvar uuidHead      = '");
      out.print(uuidHead);
      out.write("';\r\n");
      out.write("\tvar pdfPath \t  = '");
      out.print(pdfPath);
      out.write("';\r\n");
      out.write("\tvar taskFlag      = '");
      out.print(taskFlag);
      out.write("';\r\n");
      out.write("\tvar taskFlagHadle = false;   //å¤æ­æ¯å¦æ¯æå·¥ç»å\r\n");
      out.write("\t\r\n");
      out.write("\tvar taskState = '");
      out.print(taskState);
      out.write("'; // å¯¹åºå·¥ä½æµæ­¥éª¤    0 å¯æä½ 1æªå®æ 2å·²å®æ\r\n");
      out.write("\t\r\n");
      out.write("\tif(taskState == null || taskState == \"\" || taskState == \"null\"){\r\n");
      out.write("\t\ttaskState = \"0\" ;\r\n");
      out.write("\t}\r\n");
      out.write("\tif(taskState ==\"2\" ){\r\n");
      out.write("\t\ttaskFlag = \"K001\";//å·²ç»å®æ\r\n");
      out.write("\t}\r\n");
      out.write("\tif((taskFlag == null || taskFlag == \"\" || taskFlag == \"null\")){\r\n");
      out.write("\t\ttaskFlag = \"1001\", taskFlagHadle = true;\r\n");
      out.write("\t}\r\n");
      out.write("\tvar overalParam = '");
      out.print(overalParam);
      out.write("';\r\n");
      out.write("\tvar USERID        = '");
      out.print(userId);
      out.write("';\r\n");
      out.write("\tvar sysDate = '");
      out.print(sys_workDate);
      out.write("';\r\n");
      out.write("\t\r\n");
      out.write("\tvar xcp_sys_constant_usertheme = '");
      out.print(xcp_sys_usertheme);
      out.write("';\r\n");
      out.write("\t\r\n");
      out.write("\tvar latestFlag = '");
      out.print(latestFlag);
      out.write("';\r\n");
      out.write("\t\r\n");
      out.write("\tvar USER_RPTMTD = '");
      out.print(userRptMtd);
      out.write("';\r\n");
      out.write("\tif(\"null\" == USER_RPTMTD)USER_RPTMTD == null;\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("\tsrc=\"");
      out.print(webAppPath);
      out.write("platform/js/jquery-core/jquery-1.8.0.min.js\"></script>\r\n");
      out.write("<link href=\"");
      out.print(webAppPath);
      out.write("platform/css/easyui-themes/bootstrap/easyui.css\" rel=\"stylesheet\">\r\n");
      out.write("<link href=\"");
      out.print(webAppPath);
      out.write("platform/css/gjjs-themes/bootstrap/commons.css\" rel=\"stylesheet\">\r\n");
      out.write("<link href=\"");
      out.print(webAppPath);
      out.write("platform/css/gjjs-themes/bootstrap/main.css\" rel=\"stylesheet\">\r\n");
      out.write("<link href=\"");
      out.print(webAppPath);
      out.write("platform/css/gjjs-themes/bootstrap/uploadify.css\" rel=\"stylesheet\">\r\n");
      out.write("\t\t<script type=\"text/javascript\" src=\"");
      out.print(webAppPath);
      out.write("platform/js/share/include.js\"></script>\r\n");
      out.write("\t\t<script type=\"text/javascript\" src=\"");
      out.print(webAppPath);
      out.write("platform/js/share/param.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("\tsrc=\"");
      out.print(webAppPath);
      out.write("platform/js/jquery-core/jquery.cookie.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("$(function() {\r\n");
      out.write("\tvar name = '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${context.user}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("';\r\n");
      out.write("\tvar data = JSON.parse(name);\r\n");
      out.write("\tvar html = \"\";\r\n");
      out.write("\t$(\"#content\").html(html);\r\n");
      out.write("\t$.each(data, function(i, value) {\r\n");
      out.write("\t\tif(value.comboAttr==\"\"){\r\n");
      out.write("\t\t\thtml+=value.name + \" :<input id='\"+value.varName+\"' class='\" + value.classType + \"' name='\" + value.varName + \"'><p/>\"\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\thtml += value.name + \" :<input id='\"+value.varName+\"' class='\" + value.classType + \"' name='\" + value.varName + \"' \"+\"data-options=valueField:'val',textField:'name',data:$xcp.getConstant('\"+value.comboAttr+\"'),></input><p/>\"\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t});\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\thtml+='<input type=\"button\" value=\"提交\" id =\"submit\">';\r\n");
      out.write("\t$(\"#content\").html(html);\r\n");
      out.write("\t$.parser.parse();\r\n");
      out.write("\t$(\"#submit\").click(function(){\r\n");
      out.write("\t\tvar param = $(\"#content\").serialize();\r\n");
      out.write("\r\n");
      out.write("\t\t$.cookie('param', param);\r\n");
      out.write("\t\tvar url = \"./preview.do?method=getDocReport&paramSheetNum=0&formatSheetNum=3&templateName=test3.xlsx&pageSize=4\";\r\n");
      out.write("\t\tpost(url, param);  \r\n");
      out.write("\t});\r\n");
      out.write("\tfunction post(URL, PARAMS) {        \r\n");
      out.write("\t    var temp = document.createElement(\"form\");        \r\n");
      out.write("\t    temp.action = URL;        \r\n");
      out.write("\t    temp.method = \"post\";        \r\n");
      out.write("\t    temp.style.display = \"none\";  \r\n");
      out.write("\t    var paramlist = PARAMS.split(\"&\");\r\n");
      out.write("\t    $.each(paramlist,function(i,value){\r\n");
      out.write("\t    \tvalueList = value.split(\"=\");\r\n");
      out.write("\t    \tvar opt = document.createElement(\"textarea\");\r\n");
      out.write("\t    \topt.name = valueList[0];        \r\n");
      out.write("\t        opt.value = valueList[1]; \r\n");
      out.write("\t        temp.appendChild(opt);  \r\n");
      out.write("\t    });\r\n");
      out.write("\t    document.body.appendChild(temp);        \r\n");
      out.write("\t    temp.submit();        \r\n");
      out.write("\t    return temp;        \r\n");
      out.write("\t}        \r\n");
      out.write("})\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body style=\"background: #FFFFFF;\">\r\n");
      out.write("\t<h>EXCEL</h>\r\n");
      out.write("\t\t<div>\r\n");
      out.write("\t\t\t<form id='content'>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t</form>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
