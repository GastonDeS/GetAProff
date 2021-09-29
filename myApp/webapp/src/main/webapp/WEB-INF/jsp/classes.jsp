<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
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
    <h1><spring:message code="myClasses.mainTitle"/></h1>
    <div class="classes-separator-container">
        <c:if test="${isTeacher == 1}">
            <div class="main-tab-container">
                <ul class="nav nav-tabs flex-column" id="myTab1" role="tablist">
                    <li class="nav-item" role="presentation">
                        <button class="nav-link nav-link-custom active" style="width: 100%;" id="home-tab"
                                data-bs-toggle="tab" data-bs-target="#home" type="button"
                                role="tab" aria-controls="home" aria-selected="true"><spring:message
                                code="myClasses.requested"/>
                        </button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link nav-link-custom" style="width: 100%;" id="profile-tab"
                                data-bs-toggle="tab"
                                data-bs-target="#profile" type="button"
                                role="tab" aria-controls="profile" aria-selected="false"><spring:message
                                code="myClasses.incoming"/>
                        </button>
                    </li>
                </ul>
            </div>
        </c:if>
        <div class="tab-content" id="myTabContent">
            <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                <div class="tabs-container">
                    <ul class="nav nav-tabs nav-fill" id="myTab2" role="tablist">
                        <li class="nav-item" role="presentation">
                            <button class="nav-link nav-link-custom" id="pending-tab2" data-bs-toggle="tab"
                                    data-bs-target="#pending2" type="button"
                                    role="tab" aria-controls="pending2" aria-selected="false"><spring:message
                                    code="myClasses.pending"/>
                            </button>
                        </li>
                        <li class="nav-item active" role="presentation">
                            <button class="nav-link active nav-link-custom" id="active-tab2" data-bs-toggle="tab"
                                    data-bs-target="#active2" type="button"
                                    role="tab" aria-controls="active2" aria-selected="true"><spring:message
                                    code="myClasses.active"/>
                            </button>
                        </li>
                        <li class="nav-item" role="presentation">
                            <button class="nav-link nav-link-custom" id="finished-tab2" data-bs-toggle="tab"
                                    data-bs-target="#finished2" type="button"
                                    role="tab" aria-controls="finished2" aria-selected="false"><spring:message
                                    code="myClasses.finished"/>
                            </button>
                        </li>
                    </ul>
                </div>
                <div class="classes-container">
                    <div class="tab-content" id="myTabContent2">
                        <div class="tab-pane fade show active" id="active2" role="tabpanel"
                             aria-labelledby="active-tab">
                            <c:forEach var="activeClass" items="${activeClasses}">
                                <jsp:include page="../components/classCard.jsp">
                                    <jsp:param name="subjectName" value="${activeClass.subject.name}"/>
                                    <jsp:param name="teacherName" value="${activeClass.teacher.name}"/>
                                    <jsp:param name="price" value="${activeClass.subject.price}"/>
                                    <jsp:param name="level" value="${activeClass.subject.level}"/>
                                    <jsp:param name="reply" value="${activeClass.messageReply}"/>
                                </jsp:include>
                            </c:forEach>
                        </div>
                        <div class="tab-pane fade" id="pending2" role="tabpanel" aria-labelledby="pending-tab">
                            <c:forEach var="pendingClass" items="${pendingClasses}">
                                <jsp:include page="../components/classCard.jsp">
                                    <jsp:param name="subjectName" value="${pendingClass.subject.name}"/>
                                    <jsp:param name="teacherName" value="${pendingClass.teacher.name}"/>
                                    <jsp:param name="price" value="${pendingClass.subject.price}"/>
                                    <jsp:param name="level" value="${pendingClass.subject.level}"/>
                                    <jsp:param name="request" value="${pendingClass.messageRequest}"/>
                                </jsp:include>
                            </c:forEach>
                        </div>
                        <div class="tab-pane fade" id="finished2" role="tabpanel" aria-labelledby="finished-tab">
                            <c:forEach var="finishedClass" items="${finishedClasses}">
                                <jsp:include page="../components/classCard.jsp">
                                    <jsp:param name="subjectName" value="${finishedClass.subject.name}"/>
                                    <jsp:param name="teacherName" value="${finishedClass.teacher.name}"/>
                                    <jsp:param name="price" value="${finishedClass.subject.price}"/>
                                    <jsp:param name="level" value="${finishedClass.subject.level}"/>
                                    <jsp:param name="finished" value="${finishedClass.status}"/>
                                </jsp:include>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
            <c:if test="${isTeacher == 1}">
                <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
                    <div class="tabs-container">
                        <ul class="nav nav-tabs nav-fill" id="myTab" role="tablist">
                            <li class="nav-item" role="presentation">
                                <button class="nav-link nav-link-custom" id="pending-tab" data-bs-toggle="tab"
                                        data-bs-target="#pending" type="button"
                                        role="tab" aria-controls="pending" aria-selected="false"><spring:message
                                        code="myClasses.pending"/>
                                </button>
                            </li>
                            <li class="nav-item active" role="presentation">
                                <button class="nav-link active nav-link-custom" id="active-tab" data-bs-toggle="tab"
                                        data-bs-target="#active" type="button"
                                        role="tab" aria-controls="active" aria-selected="true"><spring:message
                                        code="myClasses.active"/>
                                </button>
                            </li>
                            <li class="nav-item" role="presentation">
                                <button class="nav-link nav-link-custom" id="finished-tab" data-bs-toggle="tab"
                                        data-bs-target="#finished" type="button"
                                        role="tab" aria-controls="finished" aria-selected="false"><spring:message
                                        code="myClasses.finished"/>
                                </button>
                            </li>
                        </ul>
                    </div>
                    <div class="classes-container">
                        <div class="tab-content" id="myTabContent3">
                            <div class="tab-pane fade show active" id="active" role="tabpanel"
                                 aria-labelledby="active-tab">
                                <c:forEach var="teacherActiveClass" items="${teacherActiveClasses}">
                                    <jsp:include page="../components/classCard.jsp">
                                        <jsp:param name="subjectName" value="${teacherActiveClass.subject.name}"/>
                                        <jsp:param name="studentName" value="${teacherActiveClass.student.name}"/>
                                        <jsp:param name="price" value="${teacherActiveClass.subject.price}"/>
                                        <jsp:param name="level" value="${teacherActiveClass.subject.level}"/>
                                        <jsp:param name="active" value="1"/>
                                        <jsp:param name="reply" value="${teacherActiveClass.messageReply}"/>
                                    </jsp:include>
                                </c:forEach>
                            </div>
                            <div class="tab-pane fade" id="pending" role="tabpanel" aria-labelledby="pending-tab">

                                <c:forEach var="teacherPendingClass" items="${teacherPendingClasses}">
                                    <jsp:include page="../components/classCard.jsp">
                                        <jsp:param name="subjectName" value="${teacherPendingClass.subject.name}"/>
                                        <jsp:param name="studentName" value="${teacherPendingClass.student.name}"/>
                                        <jsp:param name="price" value="${teacherPendingClass.subject.price}"/>
                                        <jsp:param name="level" value="${teacherPendingClass.subject.level}"/>
                                        <jsp:param name="request" value="${teacherPendingClass.messageRequest}"/>
                                        <jsp:param name="cid" value="${teacherPendingClass.classId}"/>
                                    </jsp:include>
                                </c:forEach>

                            </div>
                            <div class="tab-pane fade" id="finished" role="tabpanel" aria-labelledby="finished-tab">
                                <c:forEach var="teacherFinishedClass" items="${teacherFinishedClasses}">
                                    <jsp:include page="../components/classCard.jsp">
                                        <jsp:param name="subjectName" value="${teacherFinishedClass.subject.name}"/>
                                        <jsp:param name="studentName" value="${teacherFinishedClass.student.name}"/>
                                        <jsp:param name="price" value="${teacherFinishedClass.subject.price}"/>
                                        <jsp:param name="level" value="${teacherFinishedClass.subject.level}"/>
                                        <jsp:param name="finished" value="${teacherFinishedClass.status}"/>
                                    </jsp:include>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
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
