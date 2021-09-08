<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <link rel="stylesheet"  type="text/css" href="<c:url value="${page.Context.request.contextPath}/resources/styles/main.css"/>"/>
</head>
<body>
    <div class="timetable">
        <div class="day">
            <p>Lunes</p>
        </div>
        <div class="timeRange">
            <input type="time" id="st1" min="09:00" max="18:00" required>
            <input type="time" id="ft1" min="09:00" max="18:00" required>
            <button type="button" class="btn btn-custom ms-auto p-2 bd-highlight ">Agregar rango</button>
        </div>
    </div>
</body>
</html>
