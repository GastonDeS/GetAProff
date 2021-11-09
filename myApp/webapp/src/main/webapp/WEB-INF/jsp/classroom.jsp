<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set value="0" var="statusPending"/>
<c:set value="1" var="statusAccepted"/>
<c:set value="2" var="statusFinished"/>
<c:set value="3" var="statusCancelStudent"/>
<c:set value="4" var="statusCancelTeacher"/>
<c:set value="5" var="statusRejected"/>
<c:set value="6" var="statusRated"/>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <title>Class</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.png"/>" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/styles/main.css"/>">
    <spring:message code="class.enter.message.${currentClass.status}" var="enterMessagePlaceholder"/>
    <spring:message code="class.publish" var="publishPlaceholder"/>
</head>
<body>
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="isMainPage" value="${true}"/>
    <jsp:param name="uid" value="${currentUser.id}"/>
</jsp:include>
<div class="page-container">
    <img class="class-img-header" src="<c:url value="/resources/images/matematica_banner.png"/>" alt="subject banner">
    <div class="class-row-section-container">
        <div class="classroom-status-left-panel">
            <div class="class-content class-side-section">
                <h2>Classroom</h2>
                <p><spring:message code="class.card.subject.intro"/> <c:out value="${currentClass.subject.name}"/></p>
                <c:choose>
                    <c:when test="${currentClass.teacher.id == currentUser.id}">
                        <p><spring:message code="class.card.student.intro"/> <c:out
                                value="${currentClass.student.name}"/></p>
                    </c:when>
                    <c:otherwise>
                        <p><spring:message code="class.card.teacher.intro"/> <c:out
                                value="${currentClass.teacher.name}"/></p>
                    </c:otherwise>
                </c:choose>
                <p><spring:message code="class.card.price.intro"/> $<c:out
                        value="${currentClass.price}"/>/<spring:message code="class.card.price.outro"/></p>
                <p><spring:message code="class.card.level.intro"/> <spring:message
                        code="subjects.form.level.${currentClass.level}"/></p>
            </div>
            <%--                Status front--%>
            <div class="class-content class-side-section">
                <c:choose>
                    <c:when test="${currentClass.status == statusPending }">
                        <div class="classroom-status" style="background-color: darkorange">
                            <h6 style="color: black ;margin: 0"><spring:message code="myClasses.status.0"/></h6>
                        </div>
                    </c:when>
                    <c:when test="${currentClass.status == statusAccepted}">
                        <div class="classroom-status" style="background-color: green">
                            <h6 style="color: black ;margin: 0"><spring:message code="myClasses.status.1"/></h6>
                        </div>
                    </c:when>
                    <c:when test="${currentClass.status == statusFinished}">
                        <div class="classroom-status" style="background-color: white">
                            <h6 style="color: black ;margin: 0"><spring:message code="myClasses.status.2"/></h6>
                        </div>
                    </c:when>
                </c:choose>
                <%--                    BOTONES--%>
                <c:choose>
                    <c:when test="${currentClass.teacher.id == currentUser.id}">
                        <c:choose>
                            <c:when test="${currentClass.status == statusPending }">
                                <div class="class-cancel-btn">
                                    <jsp:include page="../components/rejectOrCancelModal.jsp">
                                        <jsp:param name="type" value="decline"/>
                                        <jsp:param name="urlConfirm"
                                                   value="/myClasses/0/${currentClass.classId}/REJECTED"/>
                                    </jsp:include>
                                    <form action="<c:url value="/myClasses/1/${currentClass.classId}/ACCEPTED"/>"
                                          method="post" class="class-card-btn-holder">
                                        <input type="submit" class="btn btn-custom cancel-btn"
                                               value="<spring:message code="class.card.accept"/>">
                                    </form>
                                </div>
                            </c:when>
                            <c:when test="${currentClass.status == statusAccepted}">
                                <div class="class-cancel-btn">
                                    <jsp:include page="../components/rejectOrCancelModal.jsp">
                                        <jsp:param name="type" value="cancel"/>
                                        <jsp:param name="urlConfirm"
                                                   value="/myClasses/0/${currentClass.classId}/CANCELEDT"/>
                                    </jsp:include>
                                    <jsp:include page="../components/rejectOrCancelModal.jsp">
                                        <jsp:param name="type" value="finish"/>
                                        <jsp:param name="urlConfirm"
                                                   value="/myClasses/1/${currentClass.classId}/FINISHED"/>
                                    </jsp:include>
                                </div>
                            </c:when>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${currentClass.status == statusPending}">
                                <div class="class-card-active-btn-holder">
                                    <jsp:include page="../components/rejectOrCancelModal.jsp">
                                        <jsp:param name="type" value="cancel"/>
                                        <jsp:param name="urlConfirm"
                                                   value="/myClasses/0/${currentClass.classId}/CANCELEDS"/>
                                    </jsp:include>
                                </div>
                            </c:when>
                            <c:when test="${currentClass.status == statusAccepted}">
                                <div class="class-card-active-btn-holder">
                                    <jsp:include page="../components/rejectOrCancelModal.jsp">
                                        <jsp:param name="type" value="cancel"/>
                                        <jsp:param name="urlConfirm"
                                                   value="/myClasses/0/${currentClass.classId}/CANCELEDS"/>
                                    </jsp:include>
                                </div>
                            </c:when>
                            <c:when test="${currentClass.status == statusFinished}">
                                <div class="class-card-active-btn-holder">
                                    <a href="${pageContext.request.contextPath}/rate/${currentClass.classId}"
                                       class="btn btn-custom"><spring:message code="class.card.rate"/></a>
                                </div>
                            </c:when>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
                <%--                    FIN DE BOTONES--%>
            </div>

        </div>
        <div class="classroom-center-panel">
            <%--                UPLOAD POST--%>
            <c:url value="/classroom/${currentClass.classId}" var="classroomURL"/>
            <form:form name="form" modelAttribute="classUploadForm" action="${classroomURL}" method="post"
                       enctype="multipart/form-data" cssClass="class-content upload-box">
                <div class="form-input-container" style="width: 94%">
                    <form:textarea type="text" cssClass="form-control"
                                   cssStyle="height: 20vh; resize: none;" path="message"
                                   placeholder="${enterMessagePlaceholder}"/>
                    <form:errors path="message" element="p" cssClass="form-error"/>
                </div>
                <div class="file-upload" style="justify-content: space-between">
                    <div style="display: flex;flex-direction: row; justify-content: center;align-items: center">
                        <label class="btn btn-custom">
                            <form:input type="file" accept="image/*,.pdf" name="file" style="display: none" path="file"
                                        id="file"/>
                            <spring:message code="class.upload.file"/>
                        </label>
                        <p style="margin: 0 5px 0" id="fileName"></p>
                    </div>
                    <input type="submit" class="btn btn-custom" value=${publishPlaceholder}>
                </div>
            </form:form>
            <%--                FIN DE UPLOAD POST--%>
            <%--                Fin de status fronnt--%>
            <%--            POSTS--%>
            <div class="posts-big-box">
                <c:forEach items="${posts}" var="post">
                    <div class="post-box">
                        <div class="class-card-active-btn-holder">
                            <h3><c:out value="${post.uploader.name}"/></h3>
                            <p style="font-size: 0.8em"><c:out value="${post.time.toLocaleString()}"/></p>
                        </div>
                        <p style="margin: 0"><c:out value="${post.message}"/></p>
                        <a target="_blank" href="${pageContext.request.contextPath}/classroom/open/${post.postId}"
                           class="class-link">${post.filename}</a>
                    </div>
                </c:forEach>
            </div>
            <%--            FIN DE POSTS--%>
        </div>
        <div class="classroom-right-panel">
            <div class="class-content class-side-section" style="min-height: 120px; height: fit-content;">
                <c:choose>
                    <c:when test="${currentUser.id == currentClass.teacher.id}">
                        <h2>Mis Archivos</h2>
                        <c:choose>
                            <c:when test="${teacherFiles.size() == 0}">
                                <span style="align-self: center;margin: 8px 0 4px 0;font-size: 20px;"><spring:message
                                        code="myFiles.allFilesShared"/></span>
                            </c:when>
                            <c:otherwise>
                                <h6 style="margin: 2px 0 2px 0;"><spring:message
                                        code="myFiles.chooseFilesToShareHint"/></h6>
                                <form action="${pageContext.request.contextPath}/classroom/${classId}"
                                      class="shared-files-container" method="post">
                                    <ul style="padding: 0; margin: 0;">
                                        <c:forEach var="file" items="${teacherFiles}">
                                            <li class="subjects-row">
                                                <a style="font-weight: bold;"
                                                   href="${pageContext.request.contextPath}/classFile/${currentClass.classId}/${file.fileId}"
                                                   target="_blank">${file.fileName}</a>
                                                <input type="checkbox" name="sharedFiles" class="form-check-input"
                                                       style="width: 18px;height: 18px;margin-right: 4px;"
                                                       value="${file.fileId}"
                                                       onclick="showButton(this.name,'share-button')">
                                            </li>
                                        </c:forEach>
                                    </ul>
                                    <button type="submit" class="btn btn-custom" id="share-button"
                                            style="align-self: center; margin-top: 10px; display: none"><spring:message
                                            code="myFiles.button.share"/>
                                    </button>
                                </form>
                            </c:otherwise>
                        </c:choose>
                        <h2 style="margin: 2px 0 2px 0;"><spring:message code="myFiles.sharedFilesTitle"/></h2>
                        <c:choose>
                            <c:when test="${sharedFiles.size() == 0}">
                                <span style="align-self: center;margin: 10px 0 4px 0;font-size: 20px;"><spring:message
                                        code="myFiles.noFilesSharedToStudent"/></span>
                            </c:when>
                            <c:otherwise>
                                <form action="${pageContext.request.contextPath}/classroom/${classId}"
                                      class="shared-files-container" method="post">
                                    <ul style="padding: 0; margin: 0;">
                                        <c:forEach var="file" items="${sharedFiles}">
                                            <li class="subjects-row">
                                                <a style="font-weight: bold;"
                                                   href="${pageContext.request.contextPath}/classFile/${currentClass.classId}/${file.fileId}"
                                                   target="_blank">${file.fileName}</a>
                                                <input type="checkbox" name="filesToStopSharing"
                                                       class="form-check-input"
                                                       style="width: 18px;height: 18px;margin-right: 4px;"
                                                       value="${file.fileId}"
                                                       onclick="showButton(this.name,'stop-sharing-button')">
                                            </li>
                                        </c:forEach>
                                    </ul>
                                    <button type="submit" class="btn btn-custom" id="stop-sharing-button"
                                            style="align-self: center;margin-top: 10px; display: none"><spring:message
                                            code="myFiles.button.stopSharing"/>
                                    </button>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <h2><spring:message code="myFiles.classFilesTitle"/></h2>
                        <c:choose>
                            <c:when test="${sharedFiles.size() == 0}">
                                <span style="align-self: center;margin: 10px 0 4px 0;font-size: 20px;"><spring:message
                                        code="myFiles.noFilesSharedFromTeacher"/></span>
                            </c:when>
                            <c:otherwise>
                                <div class="shared-files-container">
                                    <ul style="padding: 0; margin: 0;">
                                        <c:forEach var="file" items="${sharedFiles}">
                                            <li class="subjects-row">
                                                <a style="font-weight: bold;"
                                                   href="${pageContext.request.contextPath}/classFile/${currentClass.classId}/${file.fileId}"
                                                   target="_blank">${file.fileName}</a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

</div>
</body>
<script>
    document.getElementById('file').addEventListener('change', function () {
        document.getElementById('fileName').innerText = this.files[0].name
    });

    function showButton(checkboxName, actionBtnId) {
        var checkboxes = document.getElementsByName(checkboxName);
        if (!checkboxes)
            return;
        var checkedOne = Array.prototype.slice.call(checkboxes).some(x => x.checked);
        var deleteBtn = document.getElementById(actionBtnId);
        if (!deleteBtn)
            return;
        if (checkedOne) {
            deleteBtn.style.display = "block";
        } else deleteBtn.style.display = "none";
    }

</script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"
        integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js"
        integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/"
        crossorigin="anonymous"></script>
</html>
