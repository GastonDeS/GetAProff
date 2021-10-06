<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
    <head>
        <title>403 – GetAProff</title>
        <link rel="shortcut icon" href="<c:url value="resources/images/favicon.png"/>" type="image/x-icon">
        <link rel="stylesheet"  type="text/css" href="<c:url value="resources/styles/main.css"/>"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    </head>
    <body>
        <jsp:include page="../components/navbar.jsp">
            <jsp:param name="isMainPage" value="${true}"/>
            <jsp:param name="uid" value="${uid}"/>
        </jsp:include>
        <div class="page-container">
            <div class="form-container">
                <p>Error</p>
                <a href="${pageContext.request.contextPath}/" class="btn btn-custom">
                    <spring:message code="profile.btn.contact"/>
                </a>
            </div>
        </div>
    </body>
</html>
