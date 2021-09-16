<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="tutor-card card" style="width: 18rem;">
    <img width="294" height="171" src="${pageContext.request.contextPath}/resources/images/tutor1.png" class="card-img-top" alt="...">
    <div class="card-body">
        <h5 class="card-title"><c:out value="${param.name}"/></h5>
        <p class="card-text-custom">Esta es una demostracion de lo que podria ser una descripcion de un profesor</p>
        <p class="card-text-custom card-price">$854</p>
    </div>
</div>
