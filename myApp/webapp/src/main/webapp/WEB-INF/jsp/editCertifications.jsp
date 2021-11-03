<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: agus_
  Date: 11/3/2021
  Time: 9:26 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="editCertifications.title"/> – GetAProff </title>
    <link rel="shortcut icon" href="<c:url value="resources/images/favicon.png"/>" type="image/x-icon">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="<c:url value="resources/styles/main.css"/>"/>
</head>
<body>
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="isMainPage" value="${false}"/>
</jsp:include>
<div class="page-container">
    <form:form modelAttribute="certificationForm" action="/editCertifications" method="post"
               enctype="multipart/form-data">
        <div class="form-container">
            <p class="form-title"><spring:message code="editCertifications.title"/></p>
            <ul style="list-style: none;width: 90%;padding: 0;">
                <c:forEach var="file" items="${userFiles}">
                    <li>
                        <div class="file-container">
                            <a>${file.fileName}</a>
                            <div class="delete-file-btn-container">
                                <label class="btn btn-custom">
                                    <form:radiobutton path="fileToRemove" value="${file.fileId}"
                                                      oninput="document.getElementById('deleteFile').click()"
                                                      cssStyle="display: none;"/>
                                    <spring:message code="editCertifications.button.deleteFile"/>
                                </label>
                            </div>
                            <button type="submit" name="deleteFile" value="deleteFile" id="deleteFile"
                                    style="display: none"></button>
                        </div>

                    </li>
                </c:forEach>
            </ul>
            <div class="edit-btn-container">
                <label class="btn btn-custom">
                    <form:input type="file" accept="application/pdf" path="userFileToUpload"
                                oninput="document.getElementById('submitFile').click()" style="display: none;"/>
                    <spring:message code="editProfile.button.loadFile"/>
                </label>
                <a href="${pageContext.request.contextPath}/profile/${userId}" class="btn btn-custom">
                    <spring:message code="editCertifications.button.goBackToProfile"/>
                </a>
            </div>
            <button type="submit" name="submitFile" value="submitFile" id="submitFile" style="display: none"></button>
        </div>
    </form:form>
</div>

</body>
</html>
