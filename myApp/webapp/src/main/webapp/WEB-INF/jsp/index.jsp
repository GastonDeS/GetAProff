<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <title>GetAProff</title>
        <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.png"/>" type="image/x-icon">
        <link rel="stylesheet"  type="text/css" href=" <c:url value="/resources/styles/main.css"/>">
        <spring:message code="home.search.placeholder" var="searchPlaceholder"/>
    </head>
    <body>
        <jsp:include page="../components/navbar.jsp">
            <jsp:param name="isMainPage" value="${true}"/>
            <jsp:param name="uid" value="${uid}"/>
            <jsp:param name="section" value="1"/>
        </jsp:include>
        <div class="page-container">
            <div class="search-bar">
                <form name="Search" action="tutors/1" method="get" >
                    <input class="form-control" list="datalistOptions" id="query" name="query" placeholder= "${searchPlaceholder}"/>
                    <datalist id="datalistOptions">
                        <c:forEach var="subject" items="${subjects}">
                            <option value="${subject.name}">
                        </c:forEach>
                    </datalist>
                    <button type="submit" class="btn btn-custom">
                        <spring:message code="home.search.buttonText"/>
                    </button>
                </form>
                <div class="hottest-subject-container">
                    <c:forEach var="subject" items="${hottestSubjects}">
                        <a class="badge rounded-pill subject-pill"
                           href="${pageContext.request.contextPath}/tutors/1?query=${subject.name}">${subject.name}</a>
                    </c:forEach>
                </div>
            </div>
            <div class="main-img-container">
                <p class="explore-title"><spring:message code="explore.top.rated"/></p>
                <div class="row row-cols-1 row-cols-md-4 g-4 explore-card-container" style="margin-bottom: 25px">
                    <c:forEach var="tutor" items="${topRated}" varStatus="loop">
                        <div class="col tutor-card-container">
                            <jsp:include page="../components/tutorCard.jsp">
                                <jsp:param name="name" value="${tutor.name}"/>
                                <jsp:param name="uid" value="${tutor.userId}"/>
                                <jsp:param name="rate" value="${tutor.rate}"/>
                                <jsp:param name="description" value="${tutor.description}"/>
                                <jsp:param name="maxPrice" value="${tutor.maxPrice}"/>
                                <jsp:param name="minPrice" value="${tutor.minPrice}"/>
                            </jsp:include>
                        </div>
                    </c:forEach>
                </div>
                <p class="explore-title"><spring:message code="explore.hottest"/></p>
                <div class="row row-cols-1 row-cols-md-4 g-4 explore-card-container">
                    <c:forEach var="tutor" items="${hottest}" varStatus="loop">
                        <div class="col tutor-card-container">
                            <jsp:include page="../components/tutorCard.jsp">
                                <jsp:param name="name" value="${tutor.name}"/>
                                <jsp:param name="uid" value="${tutor.userId}"/>
                                <jsp:param name="rate" value="${tutor.rate}"/>
                                <jsp:param name="description" value="${tutor.description}"/>
                                <jsp:param name="maxPrice" value="${tutor.maxPrice}"/>
                                <jsp:param name="minPrice" value="${tutor.minPrice}"/>
                            </jsp:include>
                        </div>
                    </c:forEach>
                </div>
<%--                <div class="txt-img">--%>
<%--                    <img class="main-img" src="<c:url value="resources/images/teacher_icon.png"/>">--%>
<%--                    <p class="main-txt"><spring:message code="home.findTeacher"/></p>--%>
<%--                </div>--%>
<%--                <div class="txt-img">--%>
<%--                    <img class="main-img" src="<c:url value="resources/images/fill_form_icon.png"/>">--%>
<%--                    <p class="main-txt"><spring:message code="home.fillForm"/></p>--%>
<%--                </div>--%>
<%--                <div class="txt-img">--%>
<%--                    <img class="main-img" src="<c:url value="resources/images/connect_icon.png"/>">--%>
<%--                    <p class="main-txt"><spring:message code="home.contactTeacher"/></p>--%>
<%--                </div>--%>
            </div>
        </div>
        <jsp:include page="../components/footer.jsp">
            <jsp:param name="" value=""/>
        </jsp:include>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"
                integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp"
                crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js"
                integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/"
                crossorigin="anonymous"></script>
    </body>
</html>