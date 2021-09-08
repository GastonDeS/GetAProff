<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Tutors</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <link rel="stylesheet"  type="text/css" href="<c:url value="${page.Context.request.contextPath}/resources/styles/main.css"/>"/>
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

    <div class="">
        <select class="form-select" id="inputGroupSelect04" aria-label="Select materia">
            <c:forEach var="materia" items="${materias}">
                <option><c:out value="${materia}"/></option>
            </c:forEach>
        </select>
        <div style="margin-left: 20px; ">
            <button onclick="window.location.href='${page.Context.request.contextPath}/tutors';" type="button" class="btn btn-custom">Buscar</button>
        </div>
    </div>

    <div class="container">
        <div class="row">
            <c:forEach var="materia" items="${materias}">
                <div style="margin-top: 30px" class="col-lg-3 col-md-4 col-sm-6 col-12">
                    <div class="card bg-tutors-card-custom">
                        <div class="card-body">
                            <h5 class="card-title text-center">Brittany lin</h5>
                        </div>
                        <ul class=" list-group list-group-flush bg-tutors-card-custom">
                            <li class="list-group-item">Materia: Ingles</li>
                            <li class="list-group-item">Precio: $860</li>
                        </ul>
                        <div class="card-body d-flex">
                            <button type="button" class="btn btn-custom-outline" data-bs-toggle="modal" data-bs-target="#timeModal">
                                Horarios
                            </button>
                            <button type="button" class="btn btn-custom ms-auto p-2 bd-highlight ">Contactar</button>
                        </div>
                    </div>

                </div>

                <!-- Modal -->
                <div class="modal fade" id="timeModal" tabindex="-1" aria-labelledby="timeModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="timeModalLabel">Juan Perez - Ingles - $780</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <p>Lunes:</p>
<%--                                <p>Martes:</p>--%>
                                <p>Miercoles:</p>
<%--                                <p>Jueves:</p>--%>
                                <p>Viernes:</p>
                                <p>Sabado:</p>
                            </div>
<%--                            <div class="modal-footer">--%>
<%--                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>--%>
<%--                            </div>--%>
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
