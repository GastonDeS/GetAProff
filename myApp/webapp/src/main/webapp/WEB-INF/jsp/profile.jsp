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
    <header class="headerMain mb-3">
        <div class="container">
            <div class="row">
                <div class="col-md-3">
                    <img class="img-thumbnail mb-2" src="http://placehold.it/200x200" alt="...">
                </div>
                <div class="col">
                    <h1>Segundo Espina</h1>
                    <small>Profesor</small>
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Eligendi inventore provident quae.
                        At delectus dolores excepturi neque quasi ratione sit. Dolor dolores doloribus dolorum fugiat id
                        laborum
                    </p>
                    <c:choose>
                        <c:when test="${edit == 1}">
                            <a href="/contact" class="btn nav-link active btn-custom">
                                <spring:message code="profile.btn.contact"/>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="/subjectsForm" class="btn nav-link active btn-custom">
                                <spring:message code="profile.btn.edit"/>
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </header>

    <div class="container">
        <div class="row mb-4">
            <div class="col-md-5">
                <h3>Horarios</h3>
                <ul class="list-group">
                    <li class="list-group-item">Lunes:</li>
                    <li class="list-group-item">Martes:</li>
                    <li class="list-group-item">Miercoles:</li>
                    <li class="list-group-item">Jueves:</li>
                    <li class="list-group-item">Viernes:</li>
                    <li class="list-group-item">Sabado:</li>
                    <li class="list-group-item">Domingo:</li>
                </ul>
            </div>
            <div class="col-md-7">
                <h3>Materias</h3>
                <ul class="list-group">
                    <li class="list-group-item">Nivel Inicial
                        <div>
                            <span class="badge rounded-pill pill-custom mb-2">Fisica</span>
                            <span class="badge rounded-pill pill-custom mb-2">Matematica</span>
                            <span class="badge rounded-pill pill-custom mb-2">Ingles</span>
                            <span class="badge rounded-pill pill-custom mb-2">Jardineria</span>
                            <span class="badge rounded-pill pill-custom mb-2">Jardineria</span>
                            <span class="badge rounded-pill pill-custom mb-2">Jardineria</span>
                            <span class="badge rounded-pill pill-custom mb-2">Jardineria</span>
                            <span class="badge rounded-pill pill-custom mb-2">Jardineria</span>
                        </div>
                    </li>
                    <li class="list-group-item">Nivel Secundario</li>
                    <li class="list-group-item">Nivel Terciario</li>
                    <li class="list-group-item">No niveladas</li>
                </ul>
            </div>
        </div>
    </div>
</div>

</body>
