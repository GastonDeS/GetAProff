<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="tutor-card card" style="width: 18rem;" onclick="window.location.href='/profile/${param.uid}'">
    <img width="294" height="171" src="/resources/images/tutor1.png" class="card-img-top" alt="...">
    <div class="card-body">
        <h5 class="card-title"><c:out value="${param.name}"/></h5>
        <p class="card-text-custom"><c:out value="${param.description}"/></p>
        <p class="card-text-custom card-price">$<c:out value="${param.price}"/></p>
    </div>
</div>
