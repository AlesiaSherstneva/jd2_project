<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%--<!DOCTYPE html>
<html>
    <head>
        <title>ВОтделении</title>
        <link rel="stylesheet" type="text/css"
            href="${pageContext.request.contextPath}/resources/css/user-profile.css"/>
        <link rel="stylesheet" type="text/css"
                		 href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    </head>
    <body>--%>
<jsp:include page="../header.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/user-profile.css"/>
<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <c:set var="gender" value="${user.gender}"/>
        <c:choose>
            <c:when test="${gender == 'женский'}">
                <div class="woman-postcard" accept-charset="UTF-8">
            </c:when>
            <c:otherwise>
                <div class="man-postcard" accept-charset="UTF-8">
            </c:otherwise>
        </c:choose>
            <h1>${user.name} ${user.surname}</h1>
            <h2><i>${user.userJob.postoffice}, ${user.userJob.role}</i></h2>

            <sec:authorize access="hasAnyAuthority('ADMIN', 'REGISTERED')">

            <hr/>
            <h3><b>Дата рождения:</b> ${user.userDetails.birthday}</h3>
            <h3><b>О себе:</b> ${user.userDetails.about}</h3>
            <h3><b>Хобби:</b> ${user.userDetails.hobby}</h3>

            </sec:authorize>

            <div class="confirm" style="margin-top: 20px;">
                <a href="#" onclick="history.back();return false;"
                    class="btn btn-primary" role="button" aria-pressed="true">Назад</a>
                <a href="${pageContext.request.contextPath}"
                    class="btn btn-primary" role="button" aria-pressed="true">На главную страницу</a>
            </div>
        </div>
<jsp:include page="../footer.jsp"/>