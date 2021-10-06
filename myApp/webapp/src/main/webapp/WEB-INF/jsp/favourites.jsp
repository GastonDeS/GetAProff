<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="en">
<head>
    <title><spring:message code="favourites.title"/> – GetAProff</title>
    <link rel="shortcut icon" href="<c:url value="resources/images/favicon.png"/>" type="image/x-icon">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="<c:url value="resources/styles/main.css"/>"/>
</head>
<body>
    <jsp:include page="../components/navbar.jsp">
        <jsp:param name="isMainPage" value="${true}"/>
        <jsp:param name="uid" value="${uid}"/>
        <jsp:param name="section" value="3"/>
    </jsp:include>
    <div class="page-container">
        <c:choose>
            <c:when test="${fn:length(favouritesTutors) == 0}">
                <p class="no-favourites"><spring:message code="no.favourites.tutors"/></p>
            </c:when>
            <c:otherwise>
                <div class="row w-100">
                    <c:forEach var="tutor" items="${favouritesTutors}" varStatus="loop">
                        <div style="margin-top: 30px" class="col-xxl-3 col-xl-4 col-lg-4 col-md-6 col-sm-12">
                            <div class="container">
                                <jsp:include page="../components/tutorCard.jsp">
                                    <jsp:param name="image" value="${tutor.image}"/>
                                    <jsp:param name="name" value="${tutor.name}"/>
                                    <jsp:param name="uid" value="${tutor.userId}"/>
                                    <jsp:param name="rate" value="${tutor.rate}"/>
                                    <jsp:param name="description" value="${tutor.description}"/>
                                    <jsp:param name="maxPrice" value="${tutor.maxPrice}"/>
                                    <jsp:param name="minPrice" value="${tutor.minPrice}"/>
                                </jsp:include>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
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
