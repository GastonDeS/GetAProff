<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <title>GetAProff</title>
    <link rel="shortcut icon" href="<c:url value="resources/images/favicon.png"/>" type="image/x-icon">
    <link rel="stylesheet"  type="text/css" href="<c:url value="resources/styles/main.css"/>"/>
    <script type="text/javascript" src="<c:url value="resources/js/script.js"/>"></script>
    <spring:message code="home.search.placeholder" var="searchPlaceholder"/>
</head>
<body>
    <jsp:include page="../components/navbar.jsp">
        <jsp:param name="isMainPage" value="${true}"/>
    </jsp:include>
    <div class="page-container">
        <div class="search-bar">
<%--            <div class="dropdown">--%>
<%--                <input onclick="myFunction()" class="form-control  search-stl" type="text"--%>
<%--                       placeholder= "${searchPlaceholder}" id="myInput" onkeyup="filterFunction()">--%>
<%--                <div id="myDropdown" class="dropdown-content">--%>
<%--                    <c:forEach var="materia" items="${materias}" varStatus="loop">--%>
<%--                        <c:choose>--%>
<%--                            <c:when test="${loop.index<=5}">--%>
<%--                                <a href="#${materia.name}" onclick="searchFunction('${materia.name}')"><c:out value="${materia.name}"/></a>--%>
<%--                            </c:when>--%>
<%--                            <c:otherwise>--%>
<%--                                <a href="#${materia.name}" style="display: none" onclick="searchFunction('${materia.name}')"><c:out value="${materia.name}"/></a>--%>
<%--                            </c:otherwise>--%>
<%--                        </c:choose>--%>
<%--                    </c:forEach>--%>
<%--                </div>--%>
<%--            </div>--%>
            <button onclick="window.location.href='${pageContext.request.contextPath}/tutors?search='+document.getElementById('myInput').value;"
                    type="button" class="btn btn-custom"><spring:message code="home.search.buttonText"/></button>
        </div>
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

<%--    <script>--%>
<%--        function searchFunction(value) {--%>
<%--            document.getElementById("myInput").value = value;--%>
<%--        }--%>

<%--        function myFunction() {--%>
<%--            if (document.getElementById("myDropdown").style.display === "flex") {--%>
<%--                document.getElementById("myDropdown").style.display = "none";--%>
<%--            } else {--%>
<%--                document.getElementById("myDropdown").style.display = "flex";--%>
<%--                document.getElementById("myDropdown").style.flexDirection = "column";--%>
<%--            }--%>
<%--        }--%>

<%--        function filterFunction() {--%>
<%--            document.getElementById("myDropdown").style.display = "flex";--%>
<%--            document.getElementById("myDropdown").style.flexDirection = "column";--%>
<%--            var input, filter, ul, li, a, i, j;--%>
<%--            input = document.getElementById("myInput");--%>
<%--            filter = input.value.toUpperCase();--%>
<%--            div = document.getElementById("myDropdown");--%>
<%--            a = div.getElementsByTagName("a");--%>
<%--            for (i = 0, j = 0; i < a.length; i++) {--%>
<%--                txtValue = a[i].textContent || a[i].innerText;--%>
<%--                if (txtValue.toUpperCase().indexOf(filter) > -1 && j <= 5) {--%>
<%--                    a[i].style.display = "";--%>
<%--                    j = j + 1;--%>
<%--                } else {--%>
<%--                    a[i].style.display = "none";--%>
<%--                }--%>
<%--            }--%>
<%--        }--%>
<%--    </script>--%>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js" integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js" integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/" crossorigin="anonymous"></script>
</body>
</html>