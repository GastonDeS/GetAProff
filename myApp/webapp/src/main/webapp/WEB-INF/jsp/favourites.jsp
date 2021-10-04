<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
    </jsp:include>
    <h2><spring:message code="nav.button.favorites"/></h2>
    <div class="container mb-5">
        <div class="row">
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
    </div>
</body>
</html>
