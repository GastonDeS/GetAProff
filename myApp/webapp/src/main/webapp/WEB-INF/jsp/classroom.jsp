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
    <spring:message code="class.enter.message" var="enterMessagePlaceholder"/>
    <spring:message code="class.publish" var="publishPlaceholder"/>
</head>
<body>
    <jsp:include page="../components/navbar.jsp">
        <jsp:param name="isMainPage" value="${true}"/>
        <jsp:param name="uid" value="${currentUser.id}"/>
    </jsp:include>
    <div class="page-container">
        <img class="class-img-header" src="<c:url value="/resources/images/matematica_banner.png"/>" alt="subject banner">
        <div class="class-section-container">
            <div class="class-row-section-container">
                <div class="class-content class-side-section">
                    <h6><c:out value="${currentClass.subject.name}"/></h6>
                    <p><spring:message code="class.card.teacher.intro"/> <c:out value="${currentClass.teacher.name}"/></p>
                    <p class="card-text"><spring:message code="class.card.price.intro"/> $<c:out value="${currentClass.price}"/>/<spring:message code="class.card.price.outro"/></p>
                    <p class="card-text"><spring:message code="class.card.level.intro"/> <spring:message code="subjects.form.level.${currentClass.level}"/></p>
                </div>
                <c:url value="/classroom/${currentClass.classId}" var="classroomURL"/>
                <form:form name="form" modelAttribute="classUploadForm" action="${classroomURL}" method="post" enctype="multipart/form-data" cssClass="class-content upload-box">
                    <div class="form-input-container" style="width: 94%">
                        <form:textarea type="text" cssClass="form-control" cssStyle="height: 20vh; resize: none;" path="message" placeholder="${enterMessagePlaceholder}"/>
                        <form:errors path="message" element="p" cssClass="form-error"/>
                    </div>
                    <div class="file-upload">
                        <label class="btn btn-custom">
                            <form:input type="file" accept="image/*,.pdf" name="file" style="display: none" path="file" id="file"/>
                            <spring:message code="class.upload.file"/>
                        </label>
                        <p id="fileName"></p>
                        <input type="submit" class="btn btn-custom" value=${publishPlaceholder}>
                    </div>
                </form:form>
                <div style="padding: 0;background-color: transparent" class="class-content class-side-section">

                    <c:choose>
                        <c:when test="${currentClass.status == statusPending }">
                            <div style="margin: 0; border-radius: 10px; width: 100%; background-color: darkorange; padding: 5px">
                                <h6 style="color: black ;margin: 0">Pending</h6>
                            </div>
                        </c:when>
                        <c:when test="${currentClass.status == statusAccepted}">
                            <div style="margin: 0; border-radius: 10px; width: 100%; background-color: green; padding: 5px">
                                <h6 style="color: black ;margin: 0">Active</h6>
                            </div>
                        </c:when>
                        <c:when test="${currentClass.status == statusFinished}">
                            <div style="margin: 0; border-radius: 10px; width: 100%; background-color: white; padding: 5px">
                                <h6 style="color: black ;margin: 0">Finished</h6>
                            </div>
                        </c:when>
                    </c:choose>
<%--                    BOTONES--%>
                    <c:choose>
                        <c:when test="${currentClass.teacher.id == currentUser.id}">
                            <c:choose>
                                <c:when test="${currentClass.status == statusPending }">
                                    <div class="class-cancel-btn">
                                        <form action="<c:url value="/myClasses/${currentClass.classId}/REJECTED"/>" method="post" class="class-card-btn-holder">
                                            <input type="submit" class="btn btn-custom cancel-btn" value="<spring:message code="class.card.decline"/>">
                                        </form>
                                        <a href="${pageContext.request.contextPath}/accept/${currentClass.classId}" class="btn btn-custom"><spring:message code="class.card.accept"/></a>
                                    </div>
                                </c:when>
                                <c:when test="${currentClass.status == statusAccepted}">
                                    <div class="class-cancel-btn">
                                        <form action="<c:url value="/myClasses/${currentClass.classId}/CANCELEDT"/>" method="post" class="class-card-btn-holder">
                                            <input type="submit" class="btn btn-custom cancel-btn" value="<spring:message code="class.card.cancel"/>">
                                        </form>
                                        <form action="<c:url value="/myClasses/${currentClass.classId}/FINISHED"/>" method="post" class="class-card-btn-holder">
                                            <input type="submit" class="btn btn-custom" value="<spring:message code="class.card.finish"/>">
                                        </form>
                                    </div>
                                </c:when>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${currentClass.status == statusPending}">
                                    <div class="class-card-active-btn-holder">
                                        <form action="<c:url value="/myClasses/${currentClass.classId}/CANCELEDS"/>" method="post" class="class-card-btn-holder">
                                            <input type="submit" class="btn btn-custom cancel-btn" value="<spring:message code="class.card.cancel"/>">
                                        </form>
                                    </div>
                                </c:when>
                                <c:when test="${currentClass.status == statusAccepted}">
                                    <div class="class-card-active-btn-holder">
                                        <form action="<c:url value="/myClasses/${currentClass.classId}/CANCELEDS"/>" method="post" class="class-card-btn-holder">
                                            <input type="submit" class="btn btn-custom cancel-btn" value="<spring:message code="class.card.cancel"/>">
                                        </form>
                                    </div>
                                </c:when>
                                <c:when test="${currentClass.status == statusFinished}">
                                    <div class="class-card-active-btn-holder">
                                        <a href="${pageContext.request.contextPath}/rate/${currentClass.classId}" class="btn btn-custom"><spring:message code="class.card.rate"/></a>
                                    </div>
                                </c:when>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                    
<%--                    FIN DE BOTONES--%>
                </div>
            </div>
            <div class="posts-big-box">
                <c:forEach items="${posts}" var="post">
                    <div class="post-box">
                        <h3><c:out value="${post.uploader.name}"/></h3>
                        <p style="margin: 0"><c:out value="${post.message}"/></p>
                        <a target="_blank" href="/classroom/open/${post.postId}" class="class-link">${post.filename}</a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</body>
<script>
    document.getElementById('file').addEventListener('change',function () {
        document.getElementById('fileName').innerText = this.files[0].name
    });
</script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"
        integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js"
        integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/"
        crossorigin="anonymous"></script>
</html>
