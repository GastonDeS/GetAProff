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
    <spring:message code="home.search.placeholder" var="searchPlaceholder"/>
</head>
<body>
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="isMainPage" value="${false}"/>
</jsp:include>
<div class="page-container">
    <header class="headerMain mb-3 w-100">
        <div class="container">
            <div class="row">
                <div class="col-md-3 mb-2">
                    <img class="img-thumbnail mb-2 mt-2" src="http://placehold.it/200x200" alt="...">
                </div>
                <div class="col">
                    <h1>${user.name}</h1>
                    <c:choose>
                        <c:when test="${user.userRole == 1}">
                            <small>
                                <spring:message code="profile.teacher"/>
                            </small>
                        </c:when>
                        <c:otherwise>
                            <small>
                                <spring:message code="profile.student"/>
                            </small>
                        </c:otherwise>
                    </c:choose>
                    <p>${user.description}</p>
                    <c:if test="${user.userRole == 1}">
                        <c:choose>
                            <c:when test="${edit == 0}">
                                <a href="${pageContext.request.contextPath}/contact/${uid}"
                                   class="btn nav-link active btn-custom">
                                    <spring:message code="profile.btn.contact"/>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a href="#" class="btn nav-link active btn-custom">
                                    <spring:message code="profile.btn.edit"/>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </div>
            </div>
        </div>
    </header>

    <c:if test="${user.userRole == 1}">
        <div class="container">
            <div class="row mb-4">
                <div class="col-md-5">
                    <div class="d-flex flex-row justify-content-between">
                        <h3><spring:message code="profile.timeTable"/></h3>
                        <c:if test="${edit == 1}">
                            <a href="${pageContext.request.contextPath}/timeRegister"
                               class="btn nav-link active btn-custom mb-2">
                                <spring:message code="profile.btn.edit.timeTable"/>
                            </a>
                        </c:if>
                    </div>
                    <ul class="list-group">
                        <c:forEach var="day" items="${timetable}" varStatus="loop">
                            <c:if test="${day != null}">
                                <li class="list-group-item">
                                    <spring:message code="days.${loop.index}"/>
                                    <p class="mt-1"><c:out value="${day}"/></p>
                                </li>
                            </c:if>
                        </c:forEach>
                    </ul>
                </div>
                <div class="col-md-7">
                    <div class="d-flex flex-row justify-content-between">
                        <h3><spring:message code="profile.subjects"/></h3>
                        <c:if test="${edit == 1}">
                            <a href="${pageContext.request.contextPath}/subjectsForm"
                               class="btn nav-link active btn-custom mb-2">
                                <spring:message code="profile.btn.edit.subjects"/>
                            </a>
                        </c:if>
                    </div>
                    <li class="list-group">
                        <li class="list-group-item">Nivel Inicial
                            <div>
                                <c:forEach var="subject" items="${primaryLevel}">
                                    <span class="badge rounded-pill pill-custom mb-2">${subject}</span>
                                </c:forEach>
                            </div>
                        </li>
                        <li class="list-group-item">Nivel Secundario
                            <c:forEach var="subject" items="${secondaryLevel}">
                                    <div>
                                        <span class="badge rounded-pill pill-custom mb-2">${subject}</span>
                                    </div>
                            </c:forEach>
                        </li>
                        <li class="list-group-item">Nivel Terciario
                            <c:forEach var="subject" items="${tertiaryLevel}">
                                <div>
                                    <span class="badge rounded-pill pill-custom mb-2">${subject}</span>
                                </div>
                            </c:forEach>
                        </li>
                        <li class="list-group-item">No niveladas
                            <c:forEach var="subject" items="${noLevel}">
                                <div>
                                    <span class="badge rounded-pill pill-custom mb-2">${subject}</span>
                                </div>
                            </c:forEach>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </c:if>
</div>

</body>
