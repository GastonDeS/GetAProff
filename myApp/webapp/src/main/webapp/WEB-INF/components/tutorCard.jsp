<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<div class="tutor-card card h-100" style= "width: 85%;" onclick="window.location.href='${pageContext.request.contextPath}/profile/${param.uid}'">
    <c:choose>
        <c:when test="${param.image == 0}">
            <img src="${pageContext.request.contextPath}/resources/images/user_default_img.jpeg" class="tutor-img" alt="teacherImg">
        </c:when>
        <c:otherwise>
            <img src="${pageContext.request.contextPath}/image/${param.uid}" class="tutor-img" alt="teacherImg">
        </c:otherwise>
    </c:choose>
    <div class="card-body">
        <h5 class="card-title"><c:out value="${param.name}"/></h5>
        <p class="card-text-custom"><c:out value="${param.description}"/></p>
<%--        <p class="card-text-custom"><c:out value="${param.rate}"/></p>--%>
        <jsp:include page="ratingStars.jsp">
            <jsp:param name="rating" value="${param.rate}"/>
        </jsp:include>
        <c:choose>
            <c:when test="${param.minPrice != param.maxPrice}">
                <p class="card-price">$<c:out value="${param.minPrice}"/> - $<c:out value="${param.maxPrice}"/></p>
            </c:when>
            <c:otherwise>
                <p class="card-price">$<c:out value="${param.minPrice}"/></p>
            </c:otherwise>
        </c:choose>
    </div>
</div>
