<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <title><spring:message code="register.form.title"/> – GetAProff</title>
    <link rel="shortcut icon" href="<c:url value="resources/images/favicon.png"/>" type="image/x-icon">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <link rel="stylesheet"  type="text/css" href="<c:url value="resources/styles/main.css"/>"/>
    <spring:message code="register.form.name" var="namePlaceholder"/>
    <spring:message code="register.form.mail" var="mailPlaceholder"/>
    <spring:message code="register.form.pass" var="passPlaceholder"/>
    <spring:message code="register.form.confirmPass" var="confirmPassPlaceholder"/>
    <spring:message code="user.form.description" var="descriptionPlaceholder"/>
    <spring:message code="user.form.schedule" var="schedulePlaceholder"/>
</head>
<body>
    <jsp:include page="../components/navbar.jsp">
        <jsp:param name="isMainPage" value="${false}"/>
    </jsp:include>
    <div class="page-container">
        <h1 class="error-view">${exception}</h1>
        <c:url value="register" var="registerURL"/>
        <form:form name="form" modelAttribute="register" action="${registerURL}"  method="post" enctype="multipart/form-data">
            <div class="form-container" style="margin-bottom: 15px">
                <div class="radio-btn-container">
                    <label class="rad-label">
                        <form:radiobutton cssClass="rad-input" path="userRole" value="1" id="r1" onclick="updateForm(this)"/>
                        <div class="rad-design"></div>
                        <div class="rad-text">
                            <spring:message code="register.teacher"/>
                        </div>
                    </label>
                    <label class="rad-label">
                        <form:radiobutton cssClass="rad-input" path="userRole" value="0" id="r2" onclick="updateForm(this)"/>
                        <div class="rad-design"></div>
                        <div class="rad-text">
                            <spring:message code="register.student"/>
                        </div>
                    </label>
                </div>
            </div>
            <div class="form-container">
                <p class="form-title"><spring:message code="user.form.title"/></p>
                <div class="img-upload">
                    <img src="${pageContext.request.contextPath}/resources/images/user_default_img.jpeg" class="profile-img" alt="teacherImg">
                    <div class="edit-btn-container">
                        <label class="btn btn-custom">
                            <form:input type="file" accept="image" name="file" style="display: none" path="imageFile"/>
                            <spring:message code="user.form.choose.image"/>
                        </label>
                        <a id="remove-img" href="${pageContext.request.contextPath}/removeImg" class="btn btn-custom">
                            <spring:message code="user.form.remove.image"/>
                        </a>
                    </div>
                </div>
                <form:errors path="imageFile" element="p" cssClass="form-error"/>
                <div class="form-input-container">
                    <form:input type="text" class="form-control" path="name" placeholder="${namePlaceholder}"/>
                    <form:errors path="name" element="p" cssClass="form-error"/>
                </div>
                <div class="form-input-container">
                    <form:input type="text" class="form-control" path="mail" placeholder="${mailPlaceholder}"/>
                    <form:errors path="mail" element="p" cssClass="form-error"/>
                </div>
                <div class="form-input-container">
                    <form:input type="password" class="form-control" path="password" placeholder="${passPlaceholder}"/>
                    <form:errors path="password" element="p" cssClass="form-error"/>
                </div>
                <div class="form-input-container">
                    <form:input type="password" class="form-control" path="confirmPass" placeholder="${confirmPassPlaceholder}"/>
                    <form:errors path="confirmPass" element="p" cssClass="form-error"/>
                </div>
                <div id="description" class="form-input-container">
                    <form:textarea type="text" cssClass="form-control" cssStyle="height: 20vh" path="description" placeholder="${descriptionPlaceholder}"/>
                    <form:errors path="description" element="p" cssClass="form-error"/>
                </div>
                <div id="schedule" class="form-input-container">
                    <form:textarea type="text" cssClass="form-control" cssStyle="height: 20vh" path="schedule" placeholder="${schedulePlaceholder}"/>
                    <form:errors path="schedule" element="p" cssClass="form-error"/>
                </div>
                <input type="submit" class="btn btn-custom sign-btn" value="<spring:message code="submit.button"/>"/>
                <div class="account-check-container">
                    <p class="account-check-text"><spring:message code="register.registered"/></p>
                    <a class="account-check-link" href="login">
                        <spring:message code="nav.button.login"/>
                    </a>
                </div>
            </div>
        </form:form>
    </div>
    <script>
        function change(id) {
            document.getElementById("remove-img").style.display = id === "r1" ? 'none' : 'block';
            document.getElementById("description").style.display = id === "r1" ? 'block' : 'none';
            document.getElementById("schedule").style.display = id === "r1" ? 'block' : 'none';
        }
        if (document.getElementById('r1').checked) {
            change("r1");
        }
        if (document.getElementById('r2').checked) {
            change("r2");
        }
        function updateForm(control) {
            change(control.id);
        }
    </script>
</body>
</html>
