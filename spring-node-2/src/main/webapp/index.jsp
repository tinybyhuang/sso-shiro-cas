<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
</head>
<body>
	<h1>hello node2</h1>
	<shiro:hasRole name="管理员">
		<h2>管理员</h2>
	</shiro:hasRole>
	<shiro:hasPermission name="查询">
		<h2>查询</h2>
	</shiro:hasPermission>
	
	<a href="http://127.0.0.1:8081/node1/shiro-cas">节点1</a>
	
	<a href="http://127.0.0.1:8082/node2/shiro-cas">节点2</a>
	
	<a href="http://127.0.0.1:8082/node2/logout">单点登出</a>
</body>
</html>