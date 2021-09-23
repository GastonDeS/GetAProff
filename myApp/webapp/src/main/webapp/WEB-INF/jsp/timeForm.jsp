<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
    <head>
        <title><spring:message code="time.form.title"/> – GetAProff</title>
        <link rel="shortcut icon" href="<c:url value="resources/images/favicon.png"/>" type="image/x-icon">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <link rel="stylesheet"  type="text/css" href="<c:url value="resources/styles/main.css"/>">
    </head>
    <body>
        <jsp:include page="../components/navbar.jsp">
            <jsp:param name="isMainPage" value="${false}"/>
        </jsp:include>
        <div class="page-container">
            <h1><spring:message code="time.form.description"/></h1>
            <form action="${pageContext.request.contextPath}/profile" method="post">
                <div class="schedule-form">
                    <textarea name="scheduleInput" id="scheduleInput" placeholder="Los lunes estoy libre..." style="height: 40vh; max-height: 45vh; min-height: 38vh; width: 40vw; min-width: 200px"></textarea>
                    <button type="submit" class="btn btn-custom align-self-end"><spring:message code="button.saveChanges"/></button>
                </div>
            </form>

        </div>
    </body>
</html>
