<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="by.academy.it.pojo.options.Postoffices" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../header.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/edit.css"/>
<form:form class="postcard" accept-charset="UTF-8" action="${pageContext.request.contextPath}/confirm-2"
           modelAttribute="userjob" method="POST">
    <input type="hidden" name="_method" value="PATCH">

    <h3>Редактирование</h3>
    <form:input path="id" type="hidden"/>

    Отделение связи:
    <form:select path="postoffice">
        <form:options items="<%= new Postoffices().getPostoffices() %>"/>
        <form:errors path="postoffice" class="error"/>
    </form:select>
    <br><br>

    Должность:<br>
    Почтальон <form:radiobutton path="role" value="почтальон"/><br>
    Оператор связи <form:radiobutton path="role" value="оператор связи"/><br>
    Специалист по почтовой деятельности <form:radiobutton path="role" value="специалист по почтовой деятельности"/><br>
    <form:errors path="role" class="error"/>
    <br>
    <input type="submit" value="Подтвердить" class="add-button"/>
</form:form>
<jsp:include page="../footer.jsp"/>