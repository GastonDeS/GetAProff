<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <title>GetAProff</title>
        <link rel="shortcut icon" href="<c:url value="resources/images/favicon.png"/>" type="image/x-icon">
        <link rel="stylesheet"  type="text/css" href="resources/styles/main.css">
        <script type="text/javascript" src="<c:url value="resources/js/script.js"/>"></script>
        <spring:message code="home.search.placeholder" var="searchPlaceholder"/>
    </head>
    <body>
        <jsp:include page="../components/navbar.jsp">
            <jsp:param name="isMainPage" value="${true}"/>
        </jsp:include>
        <div class="page-container">
            <form name="Search" action="${pageContext.request.contextPath}/tutors" method="get">
                <div class="search-bar">
                    <input type="search" id="query" name="query"  class="form-control" style="width: 30vw" placeholder="${searchPlaceholder}" required/>
                    <button type="submit" class="btn btn-custom">
                        <spring:message code="home.search.buttonText"/>
                    </button>
                </div>
            </form>
            <div class="main-img-container">
                <div class="txt-img">
                    <img class="main-img" src="<c:url value="${pageContext.request.contextPath}/resources/images/teacher_icon.png"/>">
                    <p class="main-txt"><spring:message code="home.findTeacher"/></p>
                </div>
                <div class="txt-img">
                    <img class="main-img" src="<c:url value="${pageContext.request.contextPath}/resources/images/fill_form_icon.png"/>">
                    <p class="main-txt"><spring:message code="home.fillForm"/></p>
                </div>
                <div class="txt-img">
                    <img class="main-img" src="<c:url value="${pageContext.request.contextPath}/resources/images/connect_icon.png"/>">
                    <p class="main-txt"><spring:message code="home.contactTeacher"/></p>
                </div>
            </div>
        </div>
    </body>
</html>