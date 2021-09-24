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
        <spring:message code="subjects.form.level.select" var="levelPlaceholder"/>
        <spring:message code="subjects.form.add" var="addBtnPlaceholder"/>
    </head>
    <body>
        <jsp:include page="../components/navbar.jsp">
            <jsp:param name="isMainPage" value="${false}"/>
        </jsp:include>
        <div class="page-container">
            <c:url value="/subjectsForm" var="subjectsURL"/>
            <form:form modelAttribute="subjectsForm" action="${subjectsURL}"  method="post">
                <div class="form-container">
                    <p class="form-title">${subjectPlaceholder}</p>
                    <div class="subject-form-container">
                        <form:select path="subjectid">
                            <c:forEach items="${toGive}" var="subject">
                                <form:option value="${subject.id}" label="${subject.name}"/>
                            </c:forEach>
                        </form:select>
                        <div class="price-level-container">
                            <p>${pricePlaceholder}</p>
                            <form:input cssClass="price-form-control" type="number" path="price"/>
                        </div>
                        <form:errors path="price" element="p" cssClass="form-error"/>
                        <div class="price-level-container">
                            <p>${levelPlaceholder}</p>
                            <form:select path="level" cssStyle="width: 65%">
                                <c:forEach begin="0" end="3" varStatus="loop">
                                    <c:set var="label"><spring:message code="subjects.form.level.${loop.index}"/></c:set>
                                    <form:option value="${loop.index}" label="${label}"/>"/>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                    <input type="submit" class="btn btn-custom" value="${addBtnPlaceholder}"/>
                    <table class="subjects-table">
                        <c:forEach items="${given}" var="subject">
                            <tr class="subjects-row">
                                <td style="width: 45%"><c:out value="${subject.name}"/></td>
                                <td style="width: 10%">$<c:out value="${subject.price}"/></td>
                                <td style="width: 25%"><spring:message code="subjects.form.level.${subject.level}"/></td>
                                <td class="remove-btn">
                                    <a href="/subjectsForm/remove/${subject.id}" class="btn btn-custom">
                                        <spring:message code="subjects.form.remove"/>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <a href="${pageContext.request.contextPath}/profile" class="btn btn-custom">
                        <spring:message code="subjects.form.save"/>
                    </a>
                </div>
            </form:form>
        </div>
    </body>
</html>