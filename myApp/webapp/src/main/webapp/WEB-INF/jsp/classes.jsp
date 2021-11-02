<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<html>
    <head>
        <title><spring:message code="myClasses.mainTitle"/></title>
        <link rel="shortcut icon" href="<c:url value="resources/images/favicon.png"/>" type="image/x-icon">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous"/>
        <link rel="stylesheet" type="text/css" href="/resources/styles/main.css"/>
        <script type="text/javascript" src="<c:url value="/resources/js/script.js"/>"></script>
    </head>
    <body>
    <jsp:include page="../components/navbar.jsp">
        <jsp:param name="isMainPage" value="${true}"/>
        <jsp:param name="uid" value="${user.id}"/>
        <jsp:param name="section" value="2"/>
    </jsp:include>
    <div class="page-container">
        <c:if test="${param.error != null}">
            <p class="form-error"><spring:message code="exception.class"/></p>
        </c:if>
        <div class="classes-separator-container">
            <div class="main-tab-container">
                <ul class="nav nav-tabs flex-column" id="request-tab" role="tablist">
                    <li class="nav-item" role="presentation">
                        <c:choose>
                            <c:when test="${type == 'requested'}">
                                <button class="nav-link nav-link-custom active" style="width: 100%;" id="requested-tab"
                                        data-bs-toggle="tab" data-bs-target="#student-table" type="button"
                                        role="tab" aria-controls="student-table" aria-selected="true" onclick="window.location.href='/myClasses/requested/${status}'">
                                    <spring:message code="myClasses.requested"/>
                                </button>
                            </c:when>
                            <c:otherwise>
                                <button class="nav-link nav-link-custom" style="width: 100%;" id="offered-tab"
                                        data-bs-toggle="tab"
                                        data-bs-target="#teacher-table" type="button"
                                        role="tab" aria-controls="teacher-table" aria-selected="false" onclick="window.location.href='/myClasses/requested/${status}'">
                                    <spring:message code="myClasses.requested"/>
                                </button>
                            </c:otherwise>
                        </c:choose>
                    </li>
                    <c:if test="${user.teacher}">
                        <li class="nav-item" role="presentation">
                            <c:choose>
                                <c:when test="${type == 'offered'}">
                                    <button class="nav-link nav-link-custom active" style="width: 100%;" id="requested-tab"
                                            data-bs-toggle="tab" data-bs-target="#student-table" type="button"
                                            role="tab" aria-controls="student-table" aria-selected="true" onclick="window.location.href='/myClasses/offered/${status}'">
                                        <spring:message code="myClasses.incoming"/>
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button class="nav-link nav-link-custom" style="width: 100%;" id="offered-tab"
                                            data-bs-toggle="tab"
                                            data-bs-target="#teacher-table" type="button"
                                            role="tab" aria-controls="teacher-table" aria-selected="false" onclick="window.location.href='/myClasses/offered/${status}'">
                                        <spring:message code="myClasses.incoming"/>
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </li>
                    </c:if>
                </ul>
                <ul class="nav nav-tabs flex-column" id="" role="tablist">
                    <div style="flex-direction: column" class="price-level-container">
                        <div>
                            <p style="margin: 0">Filtrar por: </p>
                        </div>
                        <select name="filterby" id="filter" onchange="this.options[this.selectedIndex].value && (window.location = '/myClasses/${type}/'+this.options[this.selectedIndex].value);">
                            <c:choose>
                                <c:when test="${status == 0}">
                                    <option value="0" selected >Pending</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="0" >Pending</option>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${status == 1}">
                                    <option value="1" selected>Active</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="1">Active</option>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${status == 2}">
                                    <option value="2" selected>Finished</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="2">Finished</option>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${status == 3}">
                                    <option value="3" selected>Any</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="3">Any</option>
                                </c:otherwise>
                            </c:choose>
                        </select>
                    </div>
                </ul>
            </div>
            <div class="tab-content class-card-holder">
                <c:if test="${fn:length(allClasses) == 0}">
                    <h3 class="empty-classes-title">
                        <spring:message code="myClasses.emptyPendingClasses"/>
                    </h3>
                </c:if>
                <c:forEach var="myClass" items="${allClasses}">
                    <jsp:include page="../components/classCard.jsp">
                        <jsp:param name="subjectName" value="${myClass.subject.name}"/>
                        <jsp:param name="teacherName" value="${myClass.teacher.name}"/>
                        <jsp:param name="price" value="${myClass.price}"/>
                        <jsp:param name="level" value="${myClass.level}"/>
                        <jsp:param name="cid" value="${myClass.classId}"/>
                        <jsp:param name="isTeacher" value="${user.teacher}"/>
                        <jsp:param name="classStatus" value="${myClass.status}"/>
                        <jsp:param name="isOffered" value="${type == 'offered'}"/>
                    </jsp:include>
                </c:forEach>
            </div>
        </div>
    </div>
    <jsp:include page="../components/footer.jsp">
        <jsp:param name="" value=""/>
    </jsp:include>
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
