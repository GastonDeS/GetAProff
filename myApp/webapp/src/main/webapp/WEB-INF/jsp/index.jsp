<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <title>getAProff</title>
    <link rel="stylesheet"  type="text/css" href="<c:url value="${page.Context.request.contextPath}/resources/styles/main.css"/>"/>
</head>
<body>
    <nav class="navbar navbar-expand-sm navbar-custom">
        <div class="container">
            <a href="#" class="navbar-brand mb-0 h1">
                <img src="<c:url value="${page.Context.request.contextPath}/resources/images/logo_black.png"/>" alt="logo" class="d-inline-block align-top">
            </a>
            <div class="d-flex flex-row-reverse collapse navbar-collapse " id="navbarNav">
                <a href="${page.Context.request.contextPath}/create" class="btn nav-link active btn-custom btn-small">
                    Dar clases particulares
                </a>
            </div>
        </div>
    </nav>

    <div class="search">
<%--        <form action="#" method="post" novalidate="novalidate">--%>
<%--            <div class="search-bar">--%>
<%--                <div class="main-search">--%>
<%--                    <form class="form-control search-slt" id="exampleFormControlSelect1" autocomplete="off">--%>
<%--                        <div class="autocomplete">--%>
<%--                            <input id="myInput" type="text" class="form-control search-slt" placeholder="Materia">--%>
<%--                        </div>--%>
<%--                    </form>--%>
<%--                </div>--%>


<%--            </div>--%>
<%--        </form>--%>
<%--    </div>--%>
<%--    <div class="input-group">--%>

        <select class="form-select" id="inputGroupSelect04" aria-label="Select materia">
                <c:forEach var="materia" items="${materias}">
                    <option><c:out value="${materia.name}"/></option>
                </c:forEach>
            </select>
        <div style="margin-left: 20px; ">
            <button onclick="window.location.href='${page.Context.request.contextPath}/tutors';" type="button" class="btn btn-custom">Buscar</button>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js" integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js" integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/" crossorigin="anonymous"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/script.js"></script>
</body>

<style>



</style>
</html>