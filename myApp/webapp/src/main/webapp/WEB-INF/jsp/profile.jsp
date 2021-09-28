<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <title>Profile</title>
        <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.png"/>" type="image/x-icon">
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/styles/main.css"/>">
        <script type="text/javascript" src="<c:url value="/resources/js/script.js"/>"></script>
        <spring:message code="subjects.table.subject" var="tableSubject"/>
        <spring:message code="subjects.table.price" var="tablePrice"/>
        <spring:message code="subjects.table.level" var="tableLevel"/>
        <spring:message code="subjects.table.hour" var="tableHour"/>
    </head>
    <body>
        <jsp:include page="../components/navbar.jsp">
            <jsp:param name="isMainPage" value="${true}"/>
            <jsp:param name="uid" value="${user.id}"/>
        </jsp:include>
        <div class="page-container">
            <div class="profile-container">
                <div class="info-container">
                    <c:set var="maybeImg" value="/image/${uid}"/>
                    <c:choose>
                        <c:when test="${not empty maybeImg}">
                            <c:set var="imageURL" value="${maybeImg}"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="imageURL" value="/resources/images/user_default_img.jpeg"/>
                        </c:otherwise>
                    </c:choose>
                    <img src="${imageURL}" class="profile-img" alt="teacherImg">
                    <div class="profile-info">
                        <h1><c:out value="${user.name}"/></h1>
                        <c:choose>
                            <c:when test="${edit == 0}">
                                <sec:authorize access="isAuthenticated()">
                                    <a href="${pageContext.request.contextPath}/contact/${uid}" class="btn btn-custom">
                                        <spring:message code="profile.btn.contact"/>
                                    </a>
                                </sec:authorize>
                                <sec:authorize access="!isAuthenticated()">
                                    <a href="${pageContext.request.contextPath}/login" class="btn btn-custom">
                                        <spring:message code="profile.btn.contact"/>
                                    </a>
                                </sec:authorize>
                            </c:when>
                            <c:otherwise>
                                <div class="profile-btn">
                                    <a href="${pageContext.request.contextPath}/editProfile" class="btn btn-custom">
                                        <spring:message code="profile.btn.edit"/>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/editSubjects" class="btn btn-custom">
                                        <spring:message code="profile.btn.edit.subjects"/>
                                    </a>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="main-container h-100">
                    <div class="tabs-container">
                        <ul class="nav nav-tabs nav-fill" id="myTab" role="tablist">
                            <li class="nav-item active" role="presentation">
                                <button class="nav-link nav-link-custom active" id="personal-tab" data-bs-toggle="tab" data-bs-target="#personal" type="button"
                                        role="tab" aria-controls="personal" aria-selected="false">
                                    <spring:message code="profile.personal.info"/>
                                </button>
                            </li>
                            <li class="nav-item" role="presentation">
                                <button class="nav-link nav-link-custom" id="subjects-tab" data-bs-toggle="tab" data-bs-target="#subjects" type="button"
                                        role="tab" aria-controls="subjects" aria-selected="true">
                                    <spring:message code="profile.subjects"/>
                                </button>
                            </li>
                        </ul>
                    </div>
                    <div class="classes-container h-100">
                        <div class="tab-content" id="myTabContent">
                            <div class="tab-pane fade show active" id="personal" role="tabpanel" aria-labelledby="personal-tab">
                                <div class="section-info">
                                    <div class="profile-desc-sch">
                                        <h1><spring:message code="profile.description"/></h1>
                                        <p><c:out value="${description}"/></p>
                                    </div>
                                    <div class="profile-desc-sch">
                                        <h1><spring:message code="profile.schedule"/></h1>
                                        <p><c:out value="${schedule}"/></p>
                                    </div>
                                </div>
                            </div>
                            <div class="tab-pane fade" id="subjects" role="tabpanel" aria-labelledby="subjects-tab">
                                <table>
                                    <tr class="subjects-row" style="width: 100%">
                                        <td class="row-title" style="width: 55%">${tableSubject}</td>
                                        <td class="row-title" style="width: 15%">${tablePrice}</td>
                                        <td class="row-title" style="width: 30%">${tableLevel}</td>
                                    </tr>
                                    <c:forEach items="${subjectsList}" var="subject">
                                        <tr class="subjects-row" style="width: 100%">
                                            <td class="row-info" style="width: 55%"><c:out value="${subject.name}"/></td>
                                            <td class="row-info" style="width: 15%">$<c:out value="${subject.price}"/>/${tableHour}</td>
                                            <td class="row-info" style="width: 30%"><spring:message code="subjects.form.level.${subject.level}"/></td>
                                        </tr>
                                    </c:forEach>
                                </table>
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
