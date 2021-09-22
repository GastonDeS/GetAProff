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
        <spring:message code="subjects.form.level.0" var="nonePlaceholder"/>
        <spring:message code="subjects.form.level.1" var="primaryPlaceholder"/>
        <spring:message code="subjects.form.level.2" var="secondaryPlaceholder"/>
        <spring:message code="subjects.form.level.3" var="tertiaryPlaceholder"/>
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
                        <div class="subject-input-container">
                            <form:input type="text" path="name" class="form-control w-100" placeholder="${subjectPlaceholder}"/>
                            <form:errors path="name" element="p" cssClass="form-error"/>
                        </div>
                        <div class="price-input-container">
                            <form:input type="number" path="price" class="form-control w-100" placeholder="${pricePlaceholder}"/>
                            <form:errors path="price" element="p" cssClass="form-error"/>
                        </div>
                        <div class="level-container">
                            <form:select path="level">
                                <form:option cssClass="select-level" value="0" label="${nonePlaceholder}"/>
                                <form:option cssClass="select-level" value="1" label="${primaryPlaceholder}"/>
                                <form:option cssClass="select-level" value="2" label="${secondaryPlaceholder}"/>
                                <form:option cssClass="select-level" value="3" label="${tertiaryPlaceholder}"/>
                            </form:select>
                        </div>
                        <input type="submit" class="btn-custom" value="+"/>
                    </div>
                    <table class="subjects-table">
                        <c:forEach items="${subjects}" var="subject">
                            <tr class="subjects-row">
                                <td class="subjects-col"><c:out value="${subject.name}"/></td>
                                <td class="subjects-col">$<c:out value="${subject.price}"/></td>
                                <td class="subjects-col"><spring:message code="subjects.form.level.${subject.level}"/></td>
                                <td class="subjects-col">
                                    <a href="/remove/${subject.id}" class="btn btn-custom">-</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <input type="button" class="btn-custom submit-btn" value="<spring:message code="submit.button"/>"/>
                </div>
            </form:form>
        </div>
    </body>
</html>