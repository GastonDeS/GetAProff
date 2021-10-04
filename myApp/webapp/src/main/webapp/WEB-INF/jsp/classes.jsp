<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title><spring:message code="myClasses.mainTitle"/></title>
        <link rel="shortcut icon" href="<c:url value="resources/images/favicon.png"/>" type="image/x-icon">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="resources/styles/main.css"/>
        <script type="text/javascript" src="<c:url value="/resources/js/script.js"/>"></script>
    </head>
    <body>
    <jsp:include page="../components/navbar.jsp">
        <jsp:param name="isMainPage" value="${true}"/>
        <jsp:param name="uid" value="${user.id}"/>
    </jsp:include>
    <div class="main-container">
        <div class="classes-separator-container">
            <c:if test="${isTeacher == 1}">
                <div class="main-tab-container">
                    <ul class="nav nav-tabs flex-column" id="request-tab" role="tablist">
                        <li class="nav-item" role="presentation">
                            <button class="nav-link nav-link-custom active" style="width: 100%;" id="requested-tab"
                                    data-bs-toggle="tab" data-bs-target="#student-table" type="button"
                                    role="tab" aria-controls="student-table" aria-selected="true">
                                <spring:message code="myClasses.requested"/>
                            </button>
                        </li>
                        <li class="nav-item" role="presentation">
                            <button class="nav-link nav-link-custom" style="width: 100%;" id="offered-tab"
                                    data-bs-toggle="tab"
                                    data-bs-target="#teacher-table" type="button"
                                    role="tab" aria-controls="teacher-table" aria-selected="false">
                                <spring:message code="myClasses.incoming"/>
                            </button>
                        </li>
                    </ul>
                </div>
            </c:if>
            <div class="tab-content" id="outer-nav-content">
                <div class="tab-pane fade show active" id="student-table" role="tabpanel" aria-labelledby="requested-tab">
                    <div class="tabs-container">
                        <ul class="nav nav-tabs nav-fill" id="student-nav" role="tablist">
                            <li class="nav-item" role="presentation">
                                <button class="nav-link nav-link-custom" id="student-pending-tab" data-bs-toggle="tab"
                                        data-bs-target="#student-pending" type="button"
                                        role="tab" aria-controls="student-pending" aria-selected="false">
                                    <spring:message code="myClasses.pending"/>
                                </button>
                            </li>
                            <li class="nav-item active" role="presentation">
                                <button class="nav-link active nav-link-custom" id="student-active-tab" data-bs-toggle="tab"
                                        data-bs-target="#student-active" type="button"
                                        role="tab" aria-controls="student-active" aria-selected="true">
                                    <spring:message code="myClasses.active"/>
                                </button>
                            </li>
                            <li class="nav-item" role="presentation">
                                <button class="nav-link nav-link-custom" id="student-finished-tab" data-bs-toggle="tab"
                                        data-bs-target="#student-finished" type="button"
                                        role="tab" aria-controls="student-finished" aria-selected="false">
                                    <spring:message code="myClasses.finished"/>
                                </button>
                            </li>
                        </ul>
                    </div>
                    <div class="classes-container">
                        <div class="tab-content" id="student-content">
                            <div class="tab-pane fade show active" id="student-active" role="tabpanel"
                                 aria-labelledby="student-active-tab">
                                <c:if test="${fn:length(activeClasses) == 0}">
                                    <h3 class="empty-classes-title">
                                        <spring:message code="myClasses.emptyActiveClasses"/>
                                    </h3>
                                </c:if>
                                <c:forEach var="activeClass" items="${activeClasses}">
                                    <jsp:include page="../components/classCard.jsp">
                                        <jsp:param name="subjectName" value="${activeClass.subject}"/>
                                        <jsp:param name="teacherName" value="${activeClass.teacher}"/>
                                        <jsp:param name="price" value="${activeClass.price}"/>
                                        <jsp:param name="level" value="${activeClass.level}"/>
                                        <jsp:param name="reply" value="${activeClass.reply}"/>
                                        <jsp:param name="cid" value="${activeClass.classId}"/>
                                    </jsp:include>
                                </c:forEach>
                            </div>
                            <div class="tab-pane fade" id="student-pending" role="tabpanel" aria-labelledby="student-pending-tab">
                                <c:if test="${fn:length(pendingClasses) == 0}">
                                    <h3 class="empty-classes-title">
                                        <spring:message code="myClasses.emptyPendingClasses"/>
                                    </h3>
                                </c:if>
                                <c:forEach var="pendingClass" items="${pendingClasses}">
                                    <jsp:include page="../components/classCard.jsp">
                                        <jsp:param name="subjectName" value="${pendingClass.subject}"/>
                                        <jsp:param name="teacherName" value="${pendingClass.teacher}"/>
                                        <jsp:param name="price" value="${pendingClass.price}"/>
                                        <jsp:param name="level" value="${pendingClass.level}"/>
                                        <jsp:param name="request" value="${pendingClass.request}"/>
                                        <jsp:param name="cid" value="${pendingClass.classId}"/>
                                    </jsp:include>
                                </c:forEach>
                            </div>
                            <div class="tab-pane fade" id="student-finished" role="tabpanel" aria-labelledby="student-finished-tab">
                                <c:if test="${fn:length(finishedClasses) == 0}">
                                    <h3 class="empty-classes-title">
                                        <spring:message code="myClasses.emptyFinishedClasses"/>
                                    </h3>
                                </c:if>
                                <c:forEach var="finishedClass" items="${finishedClasses}">
                                    <jsp:include page="../components/classCard.jsp">
                                        <jsp:param name="subjectName" value="${finishedClass.subject}"/>
                                        <jsp:param name="teacherName" value="${finishedClass.teacher}"/>
                                        <jsp:param name="price" value="${finishedClass.price}"/>
                                        <jsp:param name="level" value="${finishedClass.level}"/>
                                        <jsp:param name="finished" value="${finishedClass.status}"/>
                                        <jsp:param name="cid" value="${finishedClass.classId}"/>
                                    </jsp:include>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="tab-pane fade" id="teacher-table" role="tabpanel" aria-labelledby="offered-tab">
                    <div class="tabs-container">
                        <ul class="nav nav-tabs nav-fill" id="teacher-nav" role="tablist">
                            <li class="nav-item" role="presentation">
                                <button class="nav-link nav-link-custom" id="teacher-pending-tab" data-bs-toggle="tab"
                                        data-bs-target="#teacher-pending" type="button"
                                        role="tab" aria-controls="teacher-pending" aria-selected="false">
                                    <spring:message code="myClasses.pending"/>
                                </button>
                            </li>
                            <li class="nav-item active" role="presentation">
                                <button class="nav-link active nav-link-custom" id="teacher-active-tab" data-bs-toggle="tab"
                                        data-bs-target="#teacher-active" type="button"
                                        role="tab" aria-controls="teacher-active" aria-selected="true">
                                    <spring:message code="myClasses.active"/>
                                </button>
                            </li>
                            <li class="nav-item" role="presentation">
                                <button class="nav-link nav-link-custom" id="teacher-finished-tab" data-bs-toggle="tab"
                                        data-bs-target="#teacher-finished" type="button"
                                        role="tab" aria-controls="teacher-finished" aria-selected="false">
                                    <spring:message code="myClasses.finished"/>
                                </button>
                            </li>
                        </ul>
                    </div>
                    <div class="classes-container">
                        <div class="tab-content" id="teacher-content">
                            <div class="tab-pane fade show active" id="teacher-active" role="tabpanel"
                                 aria-labelledby="teacher-active-tab">
                                <c:if test="${fn:length(teacherActiveClasses) == 0}">
                                    <h3 class="empty-classes-title">
                                        <spring:message code="myClasses.emptyActiveClasses"/>
                                    </h3>
                                </c:if>
                                <c:forEach var="teacherActiveClass" items="${teacherActiveClasses}">
                                    <jsp:include page="../components/classCard.jsp">
                                        <jsp:param name="subjectName" value="${teacherActiveClass.subject}"/>
                                        <jsp:param name="studentName" value="${teacherActiveClass.student}"/>
                                        <jsp:param name="price" value="${teacherActiveClass.price}"/>
                                        <jsp:param name="level" value="${teacherActiveClass.level}"/>
                                        <jsp:param name="active" value="1"/>
                                        <jsp:param name="reply" value="${teacherActiveClass.reply}"/>
                                        <jsp:param name="cid" value="${teacherActiveClass.classId}"/>
                                    </jsp:include>
                                </c:forEach>
                            </div>
                            <div class="tab-pane fade" id="teacher-pending" role="tabpanel" aria-labelledby="teacher-pending-tab">
                                <c:if test="${fn:length(teacherPendingClasses) == 0}">
                                    <h3 class="empty-classes-title">
                                        <spring:message code="myClasses.emptyPendingClasses"/>
                                    </h3>
                                </c:if>
                                <c:forEach var="teacherPendingClass" items="${teacherPendingClasses}">
                                    <jsp:include page="../components/classCard.jsp">
                                        <jsp:param name="subjectName" value="${teacherPendingClass.subject}"/>
                                        <jsp:param name="studentName" value="${teacherPendingClass.student}"/>
                                        <jsp:param name="price" value="${teacherPendingClass.price}"/>
                                        <jsp:param name="level" value="${teacherPendingClass.level}"/>
                                        <jsp:param name="cid" value="${teacherPendingClass.classId}"/>
                                        <jsp:param name="request" value="${teacherPendingClass.request}"/>
                                    </jsp:include>
                                </c:forEach>
                            </div>
                            <div class="tab-pane fade" id="teacher-finished" role="tabpanel" aria-labelledby="teacher-finished-tab">
                                <c:if test="${fn:length(teacherFinishedClasses) == 0}">
                                    <h3 class="empty-classes-title">
                                        <spring:message code="myClasses.emptyFinishedClasses"/>
                                    </h3>
                                </c:if>
                                <c:forEach var="teacherFinishedClass" items="${teacherFinishedClasses}">
                                    <jsp:include page="../components/classCard.jsp">
                                        <jsp:param name="subjectName" value="${teacherFinishedClass.subject}"/>
                                        <jsp:param name="studentName" value="${teacherFinishedClass.student}"/>
                                        <jsp:param name="price" value="${teacherFinishedClass.price}"/>
                                        <jsp:param name="level" value="${teacherFinishedClass.level}"/>
                                        <jsp:param name="finished" value="${teacherFinishedClass.status}"/>
                                        <jsp:param name="cid" value="${teacherFinishedClass.classId}"/>
                                    </jsp:include>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
        var triggerTabList = [].slice.call(document.querySelectorAll('#myTab a'))
        triggerTabList.forEach(function (triggerEl) {
            var tabTrigger = new bootstrap.Tab(triggerEl)
            triggerEl.addEventListener('click', function (event) {
                event.preventDefault()
                tabTrigger.show()
            })
        })
    </script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"
            integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js"
            integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/"
            crossorigin="anonymous"></script>
    </body>
</html>
