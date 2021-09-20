<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <title><spring:message code="register.form.title"/> – GetAProff</title>
    <link rel="shortcut icon" href="<c:url value="resources/images/favicon.png"/>" type="image/x-icon">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <link rel="stylesheet"  type="text/css" href="<c:url value="resources/styles/main.css"/>"/>
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
        <c:url value="/register" var="registerURL"/>
        <form:form modelAttribute="register" action="${registerURL}"  method="post">
            <div class="form-container">
                <p class="form-title"><spring:message code="register.form.title"/></p>
                <div class="form-input-container">
                    <form:input type="text" class="form-control" path="name" placeholder="${namePlaceholder}"/>
                    <form:errors path="name" element="p" cssClass="form-error"/>
                </div>
                <div class="form-input-container">
                    <form:input type="text" class="form-control" path="mail" placeholder="${mailPlaceholder}"/>
                    <form:errors path="mail" element="p" cssClass="form-error"/>
                </div>
                <div class="form-input-container">
                    <form:input type="password" class="form-control" path="password" placeholder="${passPlaceholder}"/>
                    <form:errors path="password" element="p" cssClass="form-error"/>
                </div>
                <div class="form-input-container">
                    <form:input type="password" class="form-control" path="confirmPass" placeholder="${confirmPassPlaceholder}"/>
                    <form:errors path="confirmPass" element="p" cssClass="form-error"/>
                </div>
                <div class="radio-btn-container">
                    <label class="rad-label">
                        <form:radiobutton cssClass="rad-input" path="userRole" value="1"/>
                        <div class="rad-design"></div>
                        <div class="rad-text">
                            <spring:message code="register.teacher"/>
                        </div>
                    </label>
                    <label class="rad-label">
                        <form:radiobutton cssClass="rad-input" path="userRole" value="0"/>
                        <div class="rad-design"></div>
                        <div class="rad-text">
                            <spring:message code="register.student"/>
                        </div>
                    </label>
                </div>
                <input type="submit" class="btn-custom submit-btn" value="<spring:message code="submit.button"/>"/>
                <div class="account-check-container">
                    <p class="account-check-text"><spring:message code="register.registered"/></p>
                    <a class="account-check-link" href="login">
                        <spring:message code="nav.button.login"/>
                    </a>
                </div>
            </div>
        </form:form>
    </div>
</body>
</html>
