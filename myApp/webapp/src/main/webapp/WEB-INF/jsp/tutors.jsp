<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Tutors</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <link rel="stylesheet"  type="text/css" href="<c:url value="${pageContext.request.contextPath}/resources/styles/main.css"/>"/>

</head>
<body>
    <nav class="navbar navbar-expand-sm navbar-custom">
        <div class="container">
            <a href="#" class="navbar-brand mb-0 h1">
                <img src="<c:url value="${page.Context.request.contextPath}/resources/images/logo_black.png"/>" alt="logo" class="d-inline-block align-top">
            </a>
        </div>
    </nav>

    <hr class="h-divider">

    <div class="tutors-search">
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
                    <button onclick="window.location.href='${pageContext.request.contextPath}/tutors?search=1';" type="button" class="btn btn-custom">Buscar</button>
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

    <div class="container">
        <div class="row">
            <c:forEach var="tutor" items="${tutors}" varStatus="loop">
                <div style="margin-top: 30px" class="col-lg-3 col-md-4 col-sm-6 col-12">
                    <div class="card bg-tutors-card-custom">
                        <div class="card-body">
                            <h5 class="card-title text-center">${tutor.name}</h5>
                        </div>
                        <ul class=" list-group list-group-flush bg-tutors-card-custom">
                            <li class="list-group-item">Materia: ${tutor.subject}</li>
                            <li class="list-group-item">Precio: $${tutor.price}</li>
                        </ul>
                        <div class="card-body d-flex">
                            <button type="button" class="btn btn-custom-outline" data-bs-toggle="modal" data-bs-target="#timeModal${loop.index}">
                                Horarios
                            </button>
                            <button type="button" class="btn btn-custom ms-auto p-2 bd-highlight ">Contactar</button>
                        </div>
                    </div>

                </div>

                <!-- Modal -->
                <div class="modal fade" id="timeModal${loop.index}" tabindex="-1" aria-labelledby="timeModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="timeModalLabel">Horarios de ${tutor.name}</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <c:forEach var="day" items="${tutor.userSchedule}" varStatus="loop">
                                    <c:if test="${day != null}">
                                        <h>${weekDays[loop.index]}: ${day}<br></h>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js" integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js" integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/" crossorigin="anonymous"></script>
</body>
</html>
