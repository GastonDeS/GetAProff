<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title><spring:message code="tutorCreate.title"/> – GetAProff</title>
    <link rel="shortcut icon" href="<c:url value="${pageContext.request.contextPath}/resources/images/favicon.png"/>" type="image/x-icon">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <link rel="stylesheet"  type="text/css" href="<c:url value="${pageContext.request.contextPath}/resources/styles/main.css"/>"/>
    <spring:message code="tutorCreate.form.namePlaceholder" var="namePlaceholder"/>
    <spring:message code="tutorCreate.form.mailPlaceholder" var="mailPlaceholder"/>
</head>
<body>
    <jsp:include page="../components/navbar.jsp">
        <jsp:param name="isMainPage" value="${false}"/>
    </jsp:include>
    <div class="main-container">
        <c:url value="/create" var="postPath"/>
        <form:form modelAttribute="tutorForm" action="${postPath}"  method="post">
            <div class="section-container">
                <p class="section-title"><spring:message code="tutorCreate.form.title"/> </p>
                <div class="input-section">
                    <div class="form-input">
                        <form:input type="text" class="form-control" path="name" placeholder="${namePlaceholder}"/>
                        <form:errors path="name" cssClass="formError" element="p"/>
                    </div>
                    <div class="form-input">
                        <div class="mail-input">
                            <form:input type="email" class="form-control" path="mail" aria-describedby="emailHelp" placeholder="${mailPlaceholder}"/>
                            <small id="emailHelp" class="form-text text-muted"><spring:message code="tutorCreate.form.mailHint"/> user@mail.com</small>
                            <form:errors path="mail" cssClass="formError" element="p"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="section-container">
                <p class="section-title"><spring:message code="tutorCreate.form.subjectsTitle"/></p>
                <div class="subject-section">
                    <c:forEach var="subject" items="${subjects}">
                        <div class="subject-box">
                            <input type="checkbox" id="${subject}box">
                            <label class="subject-check-label" for="${subject}box"><c:out value="${subject}"/></label>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="section-container">
                <jsp:include page="../components/timetable.jsp"/>
            </div>
            <div class="btn-container">
                <button type="submit" class="btn btn-custom ms-auto p-2 bd-highlight">
                    <spring:message code="tutorCreate.form.buttonText"/>
                </button>
            </div>
        </form:form>
    </div>
</body>
</html>
