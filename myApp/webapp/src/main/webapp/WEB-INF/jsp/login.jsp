<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <title><spring:message code="login.form.title"/> – GetAProff</title>
    <link rel="shortcut icon" href="<c:url value="resources/images/favicon.png"/>" type="image/x-icon">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <link rel="stylesheet"  type="text/css" href="<c:url value="resources/styles/main.css"/>"/>
    <spring:message code="login.form.mail" var="mailPlaceholder"/>
    <spring:message code="login.form.pass" var="passPlaceholder"/>
</head>
<body>
    <jsp:include page="../components/navbar.jsp">
        <jsp:param name="isMainPage" value="${false}"/>
    </jsp:include>
    <div class="page-container">
        <c:url value="/login" var="loginURL"/>
        <form method="post" action="${loginURL}">
            <div class="form-container">
                <p class="form-title"><spring:message code="login.form.title"/></p>
                <div class="form-input-container">
                    <input type="text" class="form-control" name="j_email" placeholder="${mailPlaceholder}"/>
                </div>
                <div class="form-input-container">
                    <input type="password" class="form-control" name="j_password" placeholder="${passPlaceholder}"/>
                </div>
                <c:if test="${param.error != null}">
                    <p class="form-error"><spring:message code="login.error"/></p>
                </c:if>
                <p class="form-error">${exception}</p>
                <label class="login-checkbox">
                    <input class="checkbox" name="j_rememberme" type="checkbox"/>
                    <spring:message code="remember.me"/>
                </label>
                <input type="submit" class="btn btn-custom sign-btn" value="<spring:message code="submit.button"/>"/>
                <div class="account-check-container">
                    <p class="account-check-text"><spring:message code="login.not.registered"/></p>
                    <a class="account-check-link" href="register">
                        <spring:message code="nav.button.register"/>
                    </a>
                </div>
            </div>
        </form>
    </div>
</body>
</html>
