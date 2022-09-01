<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../header.jsp"/>
        <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <div class="hello" >
            <h2>Пользователь с таким email<br><br>уже зарегистрирован!<br><br>Попробуй ещё раз!<br></h2>
        </div>
        <div class="confirm" style="margin-top: 20px; margin-left: 40px;">
            <a href="${pageContext.request.contextPath}"
                class="btn btn-primary" role="button" aria-pressed="true">На главную страницу</a>
        </div>
<jsp:include page="../footer.jsp"/>