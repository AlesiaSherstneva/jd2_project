<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
    <head>
        <title>ВОтделении - Администратор</title>
        <link rel="stylesheet" type="text/css"
            href="${pageContext.request.contextPath}/resources/css/admin.css" />
    </head>
    <body>
        <div id="wrapper">
            <div id="header">
                <h2>Пользователи</h2>
            </div>
        </div>
        <div id="container">
            <div id = "content">
                <table>
                    <tr>
                        <th>Имя</th>
                        <th>Фамилия</th>
                        <th>Email</th>
                        <th>Отделение</th>
                        <th>Должность</th>
                        <th>Статус</th>
                        <th>Действия</th>
                    </tr>
                    <jsp:useBean id="users" scope="request" type="java.util.List"/>
                    <c:forEach var="user" items="${users}">
                        <c:url var="banLink" value="/admin/ban">
                            <c:param name="userId" value="${user.id}" />
                        </c:url>
                        <c:url var="unbanLink" value="/admin/unban">
                            <c:param name="userId" value="${user.id}" />
                        </c:url>
                        <c:url var="deleteLink" value="/admin/delete">
                            <c:param name="userId" value="${user.id}" />
                        </c:url>
                        <tr>
                            <td>${user.name}</td>
                            <td>${user.surname}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/users/user/${user.id}">${user.email}</a>
                            </td>
                            <td>${user.userJob.postoffice}</td>
                            <td>${user.userJob.role}</td>
                            <td>
                                <c:set var="enabled" value="${user.enabled}"/>
                                <c:choose>
                                    <c:when test="${enabled == 0}">
                                        Забанен
                                    </c:when>
                                    <c:otherwise>
                                        Активен
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${enabled == 0}">
                                        <a href="${unbanLink}">Разбанить</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="${banLink}">Забанить</a>
                                    </c:otherwise>
                                </c:choose>
                                |
                                <a href="${deleteLink}"
                                    onclick="if (!(confirm('Уверен, что юзера нужно удалить? Потом назад не вернёшь!'))) return false">Удалить</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <br>
        <form:form accept-charset="UTF-8"
            action="${pageContext.request.contextPath}/logout" method="POST">
            <input type="submit" value="Выход из аккаунта" class="logout-button"/>
        </form:form>
    </body>
</html>