<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <title><spring:message code="nav.button.register"/> – GetAProff</title>
    <link rel="shortcut icon" href="<c:url value="${pageContext.request.contextPath}/resources/images/favicon.png"/>" type="image/x-icon">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <link rel="stylesheet"  type="text/css" href="<c:url value="${pageContext.request.contextPath}/resources/styles/main.css"/>"/>
    <spring:message code="register.form.name" var="namePlaceholder"/>
    <spring:message code="register.form.mail" var="mailPlaceholder"/>
    <spring:message code="register.form.pass" var="passPlaceholder"/>
    <spring:message code="register.form.confirmPass" var="confirmPassPlaceholder"/>
</head>
<body>
    <jsp:include page="../components/navbar.jsp">
        <jsp:param name="isMainPage" value="${false}"/>
    </jsp:include>
    <div class="page-container">
        <c:url value="/register" var="postPath"/>
        <form:form modelAttribute="register" action="${postPath}"  method="post">
            <div class="register-form-container">
                <p class="register-title"><spring:message code="nav.button.register"/></p>
                <form:input type="text" class="form-control" path="name" placeholder="${namePlaceholder}"/>
                <form:errors path="name" cssClass="formError" element="p"/>
                <div class="mail-form">
                    <form:input type="text" class="form-control w-100" path="mail" placeholder="${mailPlaceholder}"/>
                    <small class="mail-hint"><spring:message code="register.form.mailHint"/> user@mail.com</small>
                    <form:errors path="mail" cssClass="formError" element="p"/>
                </div>
                <form:input type="text" class="form-control" path="password" placeholder="${passPlaceholder}"/>
                <form:errors path="password" cssClass="formError" element="p"/>
                <form:input type="text" class="form-control" path="confirmPass" placeholder="${confirmPassPlaceholder}"/>
                <form:errors path="confirmPass" cssClass="formError" element="p"/>
                <input type="submit" class="btn-custom submit-btn" value="<spring:message code="submit.button"/>"/>
            </div>
        </form:form>
    </div>
</body>
</html>
