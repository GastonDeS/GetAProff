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
        <spring:message code="user.form.schedule" var="schedulePlaceholder"/>
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
                    <div class="img-upload">
                        <c:choose>
                            <c:when test="${image == null}">
                                <img src="${pageContext.request.contextPath}/resources/images/user_default_img.jpeg" class="profile-img" alt="teacherImg">
                            </c:when>
                            <c:otherwise>
                                <img src="${pageContext.request.contextPath}/image/${uid}" class="profile-img" alt="teacherImg">
                            </c:otherwise>
                        </c:choose>
                        <div class="edit-btn-container">
                            <label class="btn btn-custom">
                                <form:input type="file" accept="image" name="file" style="display: none" path="imageFile"/>
                                <form:input name="hasImage" path="hasImage" style="display:none" />
                                <spring:message code="user.form.choose.image"/>
                            </label>
                            <a href="${pageContext.request.contextPath}/removeImg" class="btn btn-custom">
                                <spring:message code="user.form.remove.image"/>
                            </a>
                        </div>
                    </div>
                    <form:errors path="imageFile" element="p" cssClass="form-error"/>
                    <div class="form-input-container">
                        <form:textarea type="text" cssClass="form-control" cssStyle="height: 20vh" path="description" placeholder="${descriptionPlaceholder}"/>
                        <form:errors path="description" element="p" cssClass="form-error"/>
                    </div>
                    <div class="form-input-container">
                        <form:textarea type="text" cssClass="form-control" cssStyle="height: 20vh" path="schedule" placeholder="${schedulePlaceholder}"/>
                        <form:errors path="schedule" element="p" cssClass="form-error"/>
                    </div>
                    <div class="save-btn-container">
                        <a href="${pageContext.request.contextPath}/profile/${uid}" class="btn btn-custom submit-btn">
                            <spring:message code="form.btn.cancel"/>
                        </a>
                        <input type="submit" class="btn btn-custom submit-btn" value="<spring:message code="form.btn.save"/>"/>
                    </div>
                </div>
            </form:form>
        </div>
    </body>
</html>
