<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <spring:message code="profile.add.favorites" var="addFav"/>
    <spring:message code="profile.remove.favorites" var="removeFav"/>
    <spring:message code="profile.share" var="shareProfile"/>
</head>
<body>
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="isMainPage" value="${true}"/>
    <jsp:param name="uid" value="${currentUser.id}"/>
</jsp:include>
<div class="page-container">
    <div class="profile-container">
        <div class="info-container">
            <img src="${pageContext.request.contextPath}/image/${uid}" class="profile-img" alt="teacherImg">
            <div class="profile-info">
                <div class="profile-name">
                    <h1><c:out value="${user.name}"/></h1>
                    <div class="profile-stars">
                        <c:if test="${user.teacher}">
                            <jsp:include page="../components/ratingStars.jsp">
                                <jsp:param name="rating" value="${rating.value1}"/>
                            </jsp:include>
                            <p>
                                <c:choose>
                                    <c:when test="${rating.value2 != 1}">
                                        (${rating.value2} <spring:message code="profile.rating.times"/>s)
                                    </c:when>
                                    <c:otherwise>
                                        (${rating.value2} <spring:message code="profile.rating.times"/>)
                                    </c:otherwise>
                                </c:choose>
                            </p>
                        </c:if>
                    </div>
                </div>
                <div class="profile-info-btn">
                    <c:if test="${user.teacher}">
                        <a href="${pageContext.request.contextPath}/editCertifications"
                           class="btn btn-custom" style="margin-right: 10px">
                            <spring:message code="profile.btn.editCertifications"/>
                        </a>
                    </c:if>
                    <c:choose>
                        <c:when test="${edit == 1}">
                            <a href="${pageContext.request.contextPath}/editProfile" class="btn btn-custom"
                               style="margin-right: 10px">
                                <spring:message code="profile.btn.edit"/>
                            </a>
                            <c:choose>
                                <c:when test="${user.teacher}">
                                    <a href="${pageContext.request.contextPath}/editSubjects/${currentUser.id}"
                                       class="btn btn-custom">
                                        <spring:message code="profile.btn.edit.subjects"/>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/editProfile/startTeaching"
                                       class="btn btn-custom">
                                        <spring:message code="profile.btn.give.class"/>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <sec:authorize access="isAuthenticated()">
                                <c:choose>
                                    <c:when test="${!isFaved}">
                                        <form action="<c:url value="/addFavourite/${user.id}"/>" method="post">
                                            <input type="submit" class="btn btn-custom" value="${addFav}"/>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <form action="<c:url value="/removeFavourite/${user.id}"/>" method="post">
                                            <input type="submit" class="btn btn-custom" value="${removeFav}"/>
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </sec:authorize>
                            <sec:authorize access="!isAuthenticated()">
                                <a href="${pageContext.request.contextPath}/login" class="btn btn-custom"
                                   style="margin-right: 10px">
                                        ${addFav}
                                </a>
                            </sec:authorize>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${user.teacher}">
                        <a class="btn btn-custom" style="margin-left: 10px" onclick="copyToClipboard()"><c:out
                                value="${shareProfile}"/></a>
                    </c:if>
                </div>
            </div>
        </div>
        <c:if test="${user.teacher}">
        <div class="main-container h-100">
            <div class="tabs-container">
                <ul class="nav nav-tabs nav-fill" id="myTab" role="tablist">
                    <li class="nav-item active" role="presentation">
                        <button class="nav-link nav-link-custom active" id="personal-info-tab" data-bs-toggle="tab"
                                data-bs-target="#personal-info" type="button"
                                role="tab" aria-controls="personal-info" aria-selected="true">
                            <spring:message code="profile.personal.info"/>
                        </button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link nav-link-custom" id="subjects-tab" data-bs-toggle="tab"
                                data-bs-target="#subjects-info" type="button"
                                role="tab" aria-controls="subjects-info" aria-selected="false">
                            <spring:message code="profile.subjects"/>
                        </button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link nav-link-custom" id="reviews-tab" data-bs-toggle="tab"
                                data-bs-target="#reviews" type="button"
                                role="tab" aria-controls="reviews" aria-selected="false">
                            <spring:message code="profile.reviews"/>
                        </button>
                    </li>
                </ul>
            </div>
            <div class="classes-container h-100">
                <div class="tab-content" id="myTabContent">
                    <div class="tab-pane fade show active" id="personal-info" role="tabpanel"
                         aria-labelledby="personal-info-tab">
                        <div class="section-info">
                            <div class="profile-desc-sch">
                                <h1><spring:message code="profile.description"/></h1>
                                <p><c:out value="${user.description}"/></p>
                            </div>
                            <div class="profile-desc-sch">
                                <h1><spring:message code="profile.schedule"/></h1>
                                <p><c:out value="${user.schedule}"/></p>
                            </div>
                            <c:if test="${userFiles.size() != 0}">
                                <div class="profile-desc-sch">
                                    <h1><spring:message code="profile.certifications"/></h1>
                                    <ul>
                                        <c:forEach var="file" items="${userFiles}">
                                            <li>
                                                <a href="/profile/${user.id}/${file.fileName}"
                                                   target="_blank">${file.fileName}</a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </c:if>
                        </div>
                    </div>
                    <div class="tab-pane fade" id="subjects-info" role="tabpanel" aria-labelledby="subjects-tab">
                        <div class="profile-subjects">
                            <table class="subjects-info">
                                <c:choose>
                                    <c:when test="${edit == 1}">
                                        <tr class="subjects-row">
                                            <td class="row-title" style="width: 55%">${tableSubject}</td>
                                            <td class="row-title" style="width: 15%">${tablePrice}</td>
                                            <td class="row-title" style="width: 30%">${tableLevel}</td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <tr class="subjects-row">
                                            <td class="row-title" style="width: 20%">${tableSubject}</td>
                                            <td class="row-title" style="width: 17%">${tablePrice}</td>
                                            <td class="row-title" style="width: 47%">${tableLevel}</td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                                <c:forEach items="${subjectsList}" var="subject">
                                <tr class="subjects-row">
                                    <c:choose>
                                        <c:when test="${edit == 1}">
                                            <td class="row-info" style="width: 55%"><c:out
                                                    value="${subject.name}"/></td>
                                            <td class="row-info" style="width: 15%">$<c:out
                                                    value="${subject.price}"/>/${tableHour}</td>
                                            <td class="row-info" style="width: 30%"><spring:message
                                                    code="subjects.form.level.${subject.level}"/></td>
                                        </c:when>
                                        <c:otherwise>
                                            <td class="row-info" style="width: 22%"><c:out
                                                    value="${subject.name}"/></td>
                                            <td class="row-info" style="width: 20%">$<c:out
                                                    value="${subject.price}"/>/${tableHour}</td>
                                            <td class="row-info" style="width: 25%"><spring:message
                                                    code="subjects.form.level.${subject.level}"/></td>
                                            <td class="remove-btn">
                                                <sec:authorize access="isAuthenticated()">
                                                    <form action="<c:url value="/requestClass/${uid}/${subject.subjectId}/${subject.level}"/>" method="post">
                                                        <input type="submit" class="btn btn-custom"
                                                               value="<spring:message code="profile.btn.contact"/>">
                                                    </form>
                                                </sec:authorize>
                                                <sec:authorize access="!isAuthenticated()">
                                                    <a href="${pageContext.request.contextPath}/login"
                                                       class="btn btn-custom">
                                                        <spring:message code="profile.btn.contact"/>
                                                    </a>
                                                </sec:authorize>
                                            </td>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <div class="tab-pane fade" id="reviews" role="tabpanel" aria-labelledby="reviews-tab">
                        <div class="section-info">
                            <c:forEach items="${ratingList}" var="rating">
                                <div class="profile-desc-sch">
                                    <div class="review-title">
                                        <h4><c:out value="${rating.student.name}"/></h4>
                                        <jsp:include page="../components/ratingStars.jsp">
                                            <jsp:param name="rating" value="${rating.rate}"/>
                                        </jsp:include>
                                    </div>
                                    <p><c:out value="${rating.review}"/></p>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </c:if>
    </div>
</div>
<div id="snackbar"><spring:message code="profile.share.success"/></div>
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
<script>
    function copyToClipboard() {
        navigator.clipboard.writeText(window.location.href);
        showSnackbar();
    }
</script>
<script>
    function showSnackbar() {
        // Get the snackbar DIV
        var x = document.getElementById("snackbar");

        // Add the "show" class to DIV
        x.className = "show";

        // After 3 seconds, remove the show class from DIV
        setTimeout(function () {
            x.className = x.className.replace("show", "");
        }, 3000);
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"
        integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js"
        integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/"
        crossorigin="anonymous"></script>
</body>
</html>
