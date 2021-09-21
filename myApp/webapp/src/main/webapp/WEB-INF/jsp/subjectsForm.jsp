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
    </head>
    <body>
        <jsp:include page="../components/navbar.jsp">
            <jsp:param name="isMainPage" value="${false}"/>
        </jsp:include>
        <div class="page-container">
            <c:url value="/register/subjectsForm" var="subjectsURL"/>
            <form:form modelAttribute="subjectsForm" action="${subjectsURL}"  method="post">
                <div class="form-container">
                    <p class="form-title"><spring:message code="subjects.form.enter"/></p>
                    <div class="form-input-container">
                        <c:set var="counter" value="0"/>
                        <c:forEach begin="0" end="14" varStatus="loop">
                            <c:if test="${loop.index == counter}">
                                <div class="subject-input-container">
                                    <form:input path="names[${loop}]" class="subject-input-form w-50" placeholder="${subjectPlaceholder}"/>
                                    <form:input path="prices[${loop}]" class="subject-input-form w-30" placeholder="${pricePlaceholder}"/>
                                    <form:select path="levels[${loop}]">
                                        <form:option value="0" label="${nonePlaceholder}"/>
                                        <form:option value="1" label="${primaryPlaceholder}"/>
                                        <form:option value="2" label="${secondaryPlaceholder}"/>
                                        <form:option value="3" label="${tertiaryPlaceholder}"/>
                                    </form:select>
                                </div>
                                <input type="button" class="btn-custom submit-btn" value="+" onclick="addSubjectForm()"/>
                            </c:if>
                            <c:if test="${loop.index < counter}">
                                <form:input path="names[${counter}]" class="subject-input-form w-50" placeholder="${subjectPlaceholder}"/>
                                <form:input path="prices[${counter}]" class="subject-input-form w-30" placeholder="${pricePlaceholder}"/>
                                <form:select path="levels[${counter}]">
                                    <form:option value="0" label="${nonePlaceholder}"/>
                                    <form:option value="1" label="${primaryPlaceholder}"/>
                                    <form:option value="2" label="${secondaryPlaceholder}"/>
                                    <form:option value="3" label="${tertiaryPlaceholder}"/>
                                </form:select>
                                <input type="button" class="btn-custom submit-btn" value="-" onclick="addSubjectForm()"/>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </form:form>
        </div>
    </body>
</html>