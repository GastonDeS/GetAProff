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
        <spring:message code="register.form.name" var="namePlaceholder"/>
    </head>
    <body>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <jsp:include page="../components/navbar.jsp">
            <jsp:param name="isMainPage" value="${false}"/>
        </jsp:include>
        <div class="page-container">
            <c:url value="/editProfile" var="editProfileURL"/>
            <form:form modelAttribute="userForm" action="${editProfileURL}" method="post" enctype="multipart/form-data">
                <div class="form-container">
                    <p class="form-title"><spring:message code="user.form.title"/></p>
                    <c:if test="${param.image != null}">
                        <p class="form-error" style="font-size: 20px"><spring:message code="exception.image.not.found"/></p>
                    </c:if>
                    <div class="img-upload">
                        <img src="${pageContext.request.contextPath}/image/${user.id}" class="profile-img" id="img-preview" alt="teacherImg">
                        <div class="edit-btn-container">
                            <label class="btn btn-custom">
                                <form:input type="file" accept="image" name="file" style="display: none" id="photo" path="imageFile"/>
                                <spring:message code="user.form.choose.image"/>
                            </label>
                        </div>
                    </div>
                    <form:errors path="imageFile" element="p" cssClass="form-error"/>
                    <div class="form-input-container">
                        <form:input type="text" class="form-control" path="name" placeholder="${namePlaceholder}"/>
                        <form:errors path="name" element="p" cssClass="form-error"/>
                    </div>
                    <c:if test="${user.teacher || param.teach != null}">
                        <div class="form-input-container">
                            <form:textarea type="text" cssClass="form-control" cssStyle="height: 20vh" path="description" placeholder="${descriptionPlaceholder}"/>
                            <form:errors path="description" element="p" cssClass="form-error"/>
                        </div>
                        <div class="form-input-container">
                            <form:textarea type="text" cssClass="form-control" cssStyle="height: 20vh" path="schedule" placeholder="${schedulePlaceholder}"/>
                            <form:errors path="schedule" element="p" cssClass="form-error"/>
                        </div>
                        <ul>
                            <c:forEach var="file" items="${userFiles}">
                                <li>${file.fileName}</li>
                            </c:forEach>
                        </ul>
                            <input type="file" name="file" id="file" accept="application/pdf" onchange="document.getElementById('file-submit').click();">
                            <input type="submit" value="submit" id="file-submit" formaction="/uploadFile/${user.id}" formmethod="post" style="display: none">
                    </c:if>
                    <div class="save-btn-container">
                        <a href="${pageContext.request.contextPath}/profile/${user.id}" class="btn btn-custom submit-btn">
                            <spring:message code="form.btn.cancel"/>
                        </a>
                        <input type="submit" class="btn btn-custom submit-btn" value="<spring:message code="form.btn.save"/>"/>
                    </div>
                </div>
            </form:form>
        </div>
    <jsp:include page="../components/footer.jsp">
        <jsp:param name="" value=""/>
    </jsp:include>
        <script>
            $(document).ready(() => {
                $("#photo").change(function () {
                    const file = this.files[0];
                    if (file) {
                        let reader = new FileReader();
                        reader.onload = function (event) {
                            $("#img-preview")
                                .attr("src", event.target.result);
                        };
                        reader.readAsDataURL(file);
                    }
                });
            });
        </script>
    </body>
</html>