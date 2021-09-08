<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <title>getAProff</title>
    <link rel="stylesheet"  type="text/css" href="<c:url value="${page.Context.request.contextPath}/resources/styles/main.css"/>"/>
    <script type="text/javascript" src="<c:url value="${page.Context.request.contextPath}/resources/js/script.js"/>"></script>
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

    <div class="search ">
        <div class="search-bar">
            <div class="dropdown">
                <input onclick="myFunction()" class="form-control  search-stl" type="text" placeholder="Search.." id="myInput" onkeyup="filterFunction()">
                <div id="myDropdown" class="dropdown-content">
                    <c:forEach var="materia" items="${materias}" varStatus="loop">
                        <c:choose>
                            <c:when test="${loop.index<=5}">
                                <a href="#${materia.name}" onclick="searchFunction('${materia.name}')"><c:out value="${materia.name}"/></a>
                            </c:when>
                            <c:otherwise>
                                <a href="#${materia.name}" style="display: none" onclick="searchFunction('${materia.name}')"><c:out value="${materia.name}"/></a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </div>
            <div style="margin-left: 20px; ">
                <button onclick="window.location.href='${pageContext.request.contextPath}/tutors?search='+document.getElementById('myInput').value;" type="button" class="btn btn-custom">Buscar</button>
            </div>
        </div>
    </div>

    <script>
        function searchFunction(value) {
            document.getElementById("myInput").setAttribute("value",value);
        }

        function myFunction() {
            document.getElementById("myDropdown").classList.toggle("show");
        }

        function filterFunction() {
            var input, filter, ul, li, a, i, j;
            input = document.getElementById("myInput");
            filter = input.value.toUpperCase();
            div = document.getElementById("myDropdown");
            a = div.getElementsByTagName("a");
            for (i = 0, j = 0; i < a.length; i++) {
                txtValue = a[i].textContent || a[i].innerText;
                if (txtValue.toUpperCase().indexOf(filter) > -1 && j <= 5) {
                    a[i].style.display = "";
                    j = j + 1;
                } else {
                    a[i].style.display = "none";
                }
            }
        }
    </script>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js" integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js" integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/" crossorigin="anonymous"></script>
</body>

<style>



</style>
</html>