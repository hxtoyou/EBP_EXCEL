<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/tlds/birt.tld" prefix="birt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style type="text/css">
</style>
<body>
	<birt:viewer id="birt" reportDesign="${designUrl }.rptdesign"
		width="1000" height="700" locale="${local }" showToolBar="true"
		showParameterPage="false" showTitle="false"
		format="${formatType }">
		<birt:param name="message" value="${message }" />
	</birt:viewer>

</body>
</html>