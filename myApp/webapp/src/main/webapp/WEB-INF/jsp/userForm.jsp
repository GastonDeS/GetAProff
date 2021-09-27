<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
    <head>
        <title><spring:message code="edit.profile.title"/> – GetAProff</title>
        <link rel="shortcut icon" href="<c:url value="resources/images/favicon.png"/>" type="image/x-icon">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <link rel="stylesheet"  type="text/css" href="<c:url value="resources/styles/main.css"/>"/>
        <spring:message code="user.form.description" var="descriptionPlaceholder"/>
    </head>
    <body>
        <jsp:include page="../components/navbar.jsp">
            <jsp:param name="isMainPage" value="${false}"/>
        </jsp:include>
        <div class="page-container">
            <c:url value="/editProfile" var="editProfileURL"/>
            <form:form modelAttribute="userForm" action="${editProfileURL}" method="post" enctype="multipart/form-data">
                <div class="form-container">
                    <p class="form-title"><spring:message code="user.form.title"/></p>
                    <div class="form-input-container">
                        <c:if test="${empty description}">
                            <c:set var="placeholder" value="${descriptionPlaceholder}"/>
                        </c:if>
                        <c:if test="${not empty description}">
                            <c:set var="placeholder" value="${description}"/>
                        </c:if>
                        <form:textarea type="text" cssClass="form-control" cssStyle="height: 30vh" path="description" placeholder="${placeholder}"/>
                        <form:errors path="description" element="p" cssClass="form-error"/>
                    </div>
                    <input type="file" width="200px" height="200px" accept="image" name="file"/>
                    <input type="submit" class="btn-custom submit-btn" value="<spring:message code="form.btn.save"/>"/>
                </div>
            </form:form>
        </div>
    </body>
</html>
