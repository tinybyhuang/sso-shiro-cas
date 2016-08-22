<!DOCTYPE html>
    <%@ page pageEncoding="UTF-8" %>
    <%@ page contentType="text/html; charset=UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="en" class="login_page">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>单点登录</title>
        <!-- Bootstrap framework -->
        <link rel="stylesheet" href="/cas/bootstrap/css/bootstrap.min.css" />
        <!-- theme color-->
        <link rel="stylesheet" href="/cas/css/blue.css" />
        <!-- tooltip -->
        <link rel="stylesheet" href="/cas/lib/qtip2/jquery.qtip.min.css" />
        <!-- main styles -->
        <link rel="stylesheet" href="/cas/css/style.css" />
        <!-- favicon -->
        <link rel="shortcut icon" href="favicon.ico" />
    </head>
    <body>
    <div class="login_box">
    <%final String queryString = request.getQueryString() == null ? "" : request.getQueryString().replaceAll("&locale=([A-Za-z][A-Za-z]_)?[A-Za-z][A-Za-z]|^locale=([A-Za-z][A-Za-z]_)?[A-Za-z][A-Za-z]", "");%>
    <c:set var='query' value='<%=queryString%>' />
    <c:set var="xquery" value="${fn:escapeXml(query)}" />
        <form action="/cas/login?${xquery}&locale=zh_CN" method="post" id="login_form">
            <input type="hidden" name="_eventId" value="submit" />
            <input type="hidden" name="lt" value="${loginTicket}" />
            <input type="hidden" name="execution" value="${flowExecutionKey}" />



            <div class="top_b">登录界面</div>
             <spring:hasBindErrors name="credential">
                <div class="alert alert-info alert-login">
                    <c:forEach var="error" items="${errors.allErrors}">
                           <spring:message code="${error.code}" text="${error.defaultMessage}" />
                    </c:forEach>
                 </div>
            </spring:hasBindErrors>
            <div class="cnt_b">
                <div class="form-group">
                     <div class="input-group">
                        <span class="input-group-addon input-sm">
                            <i class="glyphicon glyphicon-user"></i>
                        </span>
                        <input class="form-control input-sm" type="text" id="username" name="username" placeholder="请输入用户名"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon input-sm">
                                <i class="glyphicon glyphicon-lock"></i>
                        </span>
                        <input class="form-control input-sm" type="password" id="password" name="password"  autocomplete="off" placeholder="请输入密码"/>
                    </div>
                </div>

            </div>
            <div class="btm_b clearfix">
                <button class="btn btn-default btn-sm pull-right" type="submit">登录</button>
            </div>
        </form>
    </div>
    <script src="/cas/js/jquery.min.js"></script>
    <script src="/cas/lib/validation/jquery.validate.js"></script>
    <script src="/cas/bootstrap/js/bootstrap.min.js"></script>
    <script src="/cas/js/login.js"></script>

    </body>
</html>


