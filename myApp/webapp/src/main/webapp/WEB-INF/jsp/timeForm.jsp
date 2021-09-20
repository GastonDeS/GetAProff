<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <title><spring:message code="time.form.title"/> – GetAProff</title>
    <link rel="shortcut icon" href="<c:url value="resources/images/favicon.png"/>" type="image/x-icon">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <link rel="stylesheet"  type="text/css" href="<c:url value="resources/styles/main.css"/>">
</head>
<body>
    <jsp:include page="../components/navbar.jsp">
        <jsp:param name="isMainPage" value="${false}"/>
    </jsp:include>
    <div class="page-container">
        <h1><spring:message code="time.form.title"/></h1>
        <h5>Ingresa tus disponibilidad de horarios</h5>
        <c:url value="/timeRegister" var="timeRegisterURL"/>
        <form:form modelAttribute="timeRangeForm" action="${timeRegisterURL}" method="post">
            <div class="form-container">
                <c:forEach begin="0" end="6" varStatus="loop">
                    <h5><spring:message code="days.${loop.index}"/></h5>
                    <div class="hour-form">
                        <form:input type="time" path="day${loop.index}[0]" />
                        <h5 class="hour-separator"> - </h5>
                        <form:input type="time" path="day${loop.index}[1]" />
                    </div>
                </c:forEach>
            </div>
            <input type="submit" class="btn-custom submit-btn" value="<spring:message code="submit.button"/>"/>
        </form:form>
    </div>
</body>
</html>
