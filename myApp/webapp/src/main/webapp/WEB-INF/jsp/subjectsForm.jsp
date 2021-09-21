<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
    <head>
        <title><spring:message code="subjects.form.title"/> – GetAProff</title>
        <link rel="shortcut icon" href="<c:url value="resources/images/favicon.png"/>" type="image/x-icon">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <link rel="stylesheet"  type="text/css" href="<c:url value="resources/styles/main.css"/>">
        <spring:message code="subjects.form.enter" var="subjectPlaceholder"/>
        <spring:message code="subjects.form.price" var="pricePlaceholder"/>
        <spring:message code="subjects.form.level.none" var="nonePlaceholder"/>
        <spring:message code="subjects.form.level.primary" var="primaryPlaceholder"/>
        <spring:message code="subjects.form.level.secondary" var="secondaryPlaceholder"/>
        <spring:message code="subjects.form.level.tertiary" var="tertiaryPlaceholder"/>
        <spring:message code="subjects.form.level.select" var="levelPlaceholder"/>
    </head>
    <body>
        <jsp:include page="../components/navbar.jsp">
            <jsp:param name="isMainPage" value="${false}"/>
        </jsp:include>
        <div class="page-container">
            <c:url value="/subjectsForm" var="subjectsURL"/>
            <form:form modelAttribute="subjectsForm" action="${subjectsURL}"  method="post">
                <div class="form-container">
                    <p class="form-title"><spring:message code="subjects.form.enter"/></p>
                    <div class="subject-form-container">
                        <div class="subject-input-container w-40">
                            <form:input type="text" path="name" class="form-control w-100" placeholder="${subjectPlaceholder}"/>
                            <form:errors path="name" element="p" cssClass="form-error"/>
                        </div>
                        <div class="subject-input-container w-20">
                            <form:input type="number" path="price" class="form-control w-100" placeholder="${pricePlaceholder}"/>
                            <form:errors path="price" element="p" cssClass="form-error"/>
                        </div>
                        <form:select path="level" cssClass="level-container">
                            <form:option cssClass="select-level" value="NONE" label="${levelPlaceholder}" />
                            <form:option cssClass="select-level" value="0" label="${nonePlaceholder}"/>
                            <form:option cssClass="select-level" value="1" label="${primaryPlaceholder}"/>
                            <form:option cssClass="select-level" value="2" label="${secondaryPlaceholder}"/>
                            <form:option cssClass="select-level" value="3" label="${tertiaryPlaceholder}"/>
                        </form:select>
                    </div>
                    <input type="submit" class="btn-custom submit-btn" value="<spring:message code="submit.button"/>"/>
                </div>
            </form:form>
        </div>
    </body>
</html>