<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

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
                    <img class="img-thumbnail mb-2 mt-2" src="${pageContext.request.contextPath}/resources/images/tutor.jpg" alt="userImage">
                    <h1><c:out value="${user.name}"/></h1>
                    <p><c:out value="${description}"/></p>
                    <c:choose>
                        <c:when test="${edit == 0}">
                            <a href="${pageContext.request.contextPath}/contact/${uid}" class="btn btn-custom">
                                <spring:message code="profile.btn.contact"/>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="#" class="btn btn-custom">
                                <spring:message code="profile.btn.edit"/>
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="profile-section-container">
                    <div class="section-btn-container">
                        <c:forEach begin="0" end="1" varStatus="loop">
                            <c:set var="button"><spring:message code="profile.btn.section.${loop.index}"/></c:set>
                            <c:set var="title">${sections[loop.index]}</c:set>
                            <c:choose>
                                <c:when test="${section eq title}">
                                    <a href="${pageContext.request.contextPath}/profile/${uid}/${title}"
                                       class="section-btn" style="border-top-right-radius: 8px">${button}</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/profile/${uid}/${title}"
                                       class="section-btn" style="background-color: rgba(2, 102, 112, 0.5)">${button}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                        <c:choose>
                            <c:when test="${section eq sections[0]}">
                                <div class="section-info">
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
                                    <a href="${pageContext.request.contextPath}/subjectsForm" class="btn btn-custom">
                                        <spring:message code="profile.btn.edit.subjects"/>
                                    </a>
                                </div>
                            </c:when>
                            <c:when test="${section eq sections[1]}">
                                <jsp:include page="../components/${section}.jsp">
                                        <jsp:param name="schedule" value="${scheduleText}"/>
                                </jsp:include>
                            </c:when>
                            <c:otherwise>
                                <p>Empty</p>
                            </c:otherwise>
                        </c:choose>
                </div>
            </div>
        </div>
    </body>
</html>
