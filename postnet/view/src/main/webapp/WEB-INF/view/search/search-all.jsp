<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
    <head>
        <title>ВОтделении</title>
        <link rel="stylesheet" type="text/css"
            href="${pageContext.request.contextPath}/resources/css/register.css"/>
        <link rel="stylesheet" type="text/css"
            href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    </head>
    <body>
        <div class="postcard" accept-charset="UTF-8">
            <c:forEach var="user" items="${users}">
                <a href="${pageContext.request.contextPath}/users/user/${user.id}">${user.name} ${user.surname},
                    <br>${user.userJob.role}<br>из ОПС ${user.userJob.postoffice}</a>
                    <hr/>
            </c:forEach>
            <c:set var="currentPage" value="${currentpage}"/>
            <c:forEach begin="${startpage}" end="${endpage}" var="page">
                <c:choose>
                    <c:when test="${page == currentpage}">
                        <<${page}>>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/users/${page}?searchString=${search}">
                            <<${page}>></a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <div class="confirm" style="margin-top: 20px; margin-left: 20px;">
                <a href="${pageContext.request.contextPath}"
                    class="btn btn-primary" role="button" aria-pressed="true">На главную страницу</a>
            </div>
        </div>
    </body>
<html>