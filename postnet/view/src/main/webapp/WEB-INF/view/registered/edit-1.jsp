<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../header.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/edit.css"/>
<script src="${pageContext.request.contextPath}/resources/js/show-password.js"></script>
<form:form class="postcard" accept-charset="UTF-8"
           action="${pageContext.request.contextPath}/confirm-1"
           modelAttribute="user" method="POST">

    <h3>Редактирование</h3>
    <form:input path="id" type="hidden"/>

    Имя:<br>
    <form:input path="name"/><br>
    <form:errors path="name" class="error"/>
    <br>

    Фамилия:<br>
    <form:input path="surname"/><br>
    <form:errors path="surname" class="error"/>
    <br>

    Пол:<br>
    Мужчина <form:radiobutton path="gender" value="мужской"/><br>
    Женщина <form:radiobutton path="gender" value="женский"/><br>
    <form:errors path="gender" class="error"/>
    <br>

    Электронная почта:<br>
    <i>При смене email потребуется повторная авторизация</i><br>
    <form:input path="email"/><br>
    <form:errors path="email" class="error"/>
    <br>

    Пароль:<br>
    <i>Должен состоять не менее, чем из 8 символов, содержать как минимум одну прописную букву,
        одну строчную букву и одну цифру </i><br>
    <form:input path="password" type="password" id="pass"/>
    <div style="margin-bottom: 5px">
        <label><input type="checkbox" onclick="show()"/>Показать пароль</label>
    </div>
    <form:errors path="password" class="error"/>
    <br><br>

    <input type="submit" value="Подтвердить" class="add-button"/>
</form:form>
<jsp:include page="../footer.jsp"/>