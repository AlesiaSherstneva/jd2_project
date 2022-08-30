<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../header.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/edit.css"/>
<form:form class="postcard" accept-charset="UTF-8" action="${pageContext.request.contextPath}/confirm-3"
           modelAttribute="userdetails" method="POST">
    <input type="hidden" name="_method" value="PATCH">

    <h3>Редактирование</h3>
    <form:input path="id" type="hidden"/>

    Дата рождения:<br>
    <form:input path="birthday" type="date"/><br>
    <form:errors path="birthday" class="error"/>
    <br>

    О себе:<br>
    <form:textarea path="about" cols="40" rows="3"/>
    <br>

    Увлечения:<br>
    <form:textarea path="hobby" cols="40" rows="3"/>
    <br>

    <input type="submit" value="Подтвердить" class="add-button"/>
</form:form>
<jsp:include page="../footer.jsp"/>