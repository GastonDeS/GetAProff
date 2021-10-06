<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<c:forEach begin="0" end="4" varStatus="loop">
    <c:choose>
        <c:when test="${param.rating - loop.index >= 1}">
            <span class="fa fa-star checked"></span>
        </c:when>
        <c:when test="${param.rating - loop.index > 0 && param.rating - loop.index < 1}">
            <span class="fa fa-star-half-o checked"></span>
        </c:when>
        <c:otherwise>
            <span class="fa fa-star-o checked"></span>
        </c:otherwise>
    </c:choose>
</c:forEach>