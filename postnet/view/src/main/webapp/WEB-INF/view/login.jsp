<%--suppress HtmlFormInputWithoutLabel --%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="header.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/login.css"/>
<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="${pageContext.request.contextPath}/resources/js/show-password.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<div class="hello">
    <h1>Привет! Добро пожаловать</h1>
    <h1>в социальную сеть</h1>
    <h1>для работников производства</h1>
    <h1>"Минская почта"!</h1>
    <h1>Заходи, не стесняйся!</h1>
</div>
<div>
    <div id="loginbox" style="margin-top: 20px; margin-left: 20px;"
         class="mainbox col-md-3 col-md-offset-2 col-sm-6 col-sm-offset-2">
        <div class="panel panel-info">
            <div class="panel-heading">
                <div class="panel-title">Форма авторизации</div>
            </div>
            <div style="padding-top: 15px" class="panel-body">
                <form:form action="${pageContext.request.contextPath}/authenticate"
                           method="POST" class="form-horizontal">
                    <div class="form-group">
                        <div class="col-xs-15">
                            <div>
                                <c:if test="${param.error != null}">
                                    <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                                        Неверное имя и/или пароль
                                    </div>
                                </c:if>
                                <c:if test="${param.logout != null}">
                                    <div class="alert alert-success col-xs-offset-1 col-xs-10">
                                        Ты вышел/вышла из аккаунта
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                    <div style="margin-bottom: 25px" class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>
                        <input type="text" name="username" placeholder="Введи email" class="form-control">
                    </div>
                    <div style="margin-bottom: 5px" class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                        <input type="password" name="password" placeholder="Введи пароль"
                               class="form-control" id="pass">
                    </div>
                    <div style="margin-bottom: 5px">
                        <label><input type="checkbox" onclick="show()"/>Показать пароль</label>
                    </div>
                    <div style="margin-top: 10px" class="form-group">
                        <div class="col-sm-6 controls">
                            <button type="submit" class="btn btn-success">Авторизация</button>
                        </div>
                        <a href="${pageContext.request.contextPath}/register-step-1"
                           class="btn btn-primary" role="button" aria-pressed="true">
                            Регистрация </a>
                    </div>
                </form:form>
                <div style="margin-top: 15px; margin-bottom: 10px;">
                    <form:form action="${pageContext.request.contextPath}/users/1" method="GET" class="search">
                        <input type="text" name="searchString"
                               placeholder="Введи фамилию или номер ОПС" class="input"/>
                        <input type="submit" name="" value="" class="submit"/>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>