<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="by.academy.it.pojo.options.Postoffices" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="unreg-header.jsp"/>
<%--@elvariable id="userjob" type="by.academy.it.pojo.UserJob"--%>
<form:form class="postcard" accept-charset="UTF-8" action="${pageContext.request.contextPath}/register-step-3"
           modelAttribute="userjob" method="POST">
    <h3>Публичный профиль - Шаг 2</h3>

    Отделение связи:
    <form:select path="postoffice">
        <form:options items="<%= new Postoffices().getPostoffices() %>"/>
        <form:errors path="postoffice" cssClass="error"/>
    </form:select>
    <br><br>

    Должность:<br>
    Почтальон <form:radiobutton path="role" value="почтальон"/><br>
    Оператор связи <form:radiobutton path="role" value="оператор связи"/><br>
    Специалист по почтовой деятельности <form:radiobutton path="role" value="специалист по почтовой деятельности"/><br>
    <form:errors path="role" cssClass="error"/>
    <br>

    <input type="submit" value="Подтвердить" class="add-button"/>
</form:form>
<jsp:include page="unreg-footer.jsp"/>