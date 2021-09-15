<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <link rel="stylesheet"  type="text/css" href="<c:url value="${page.Context.request.contextPath}/resources/styles/main.css"/>"/>
</head>
<body>
    <div class="card" style="width: 18rem;">
        <img src="/resources/images/tutor1.png" class="card-img-top" alt="...">
        <div class="card-body">
            <ul class="list-group list-group-flush">
                <li class="list-group-item"><c:out value="${param.name}"/></li>
            </ul>
            <p class="card-text">Esta es una demostracion de lo que podria ser una descripcion de un profesor</p>
        </div>
    </div>

</body>
</html>

<script>

</script>
