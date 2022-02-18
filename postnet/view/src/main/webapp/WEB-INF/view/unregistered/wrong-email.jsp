<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="unreg-header.jsp"/>
        <link rel="stylesheet" type="text/css"
        		 href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <div class="hello" style="margin-top: 200px; margin-left: 20px; color: #FFFFFF;font-family: Verdana, Arial, Helvetica, sans-serif;">
            <h2>Пользователь с таким email</h2>
            <h2>уже зарегистрирован!</h2>
            <h2>Попробуй ещё раз!</h2>
        </div>
        <div class="confirm" style="margin-top: 20px; margin-left: 20px;">
            <a href="${pageContext.request.contextPath}"
                class="btn btn-primary" role="button" aria-pressed="true">На главную страницу</a>
        </div>
<jsp:include page="unreg-footer.jsp"/>