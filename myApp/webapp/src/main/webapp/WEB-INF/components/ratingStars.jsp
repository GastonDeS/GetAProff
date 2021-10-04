<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<c:forEach begin="1" end="${param.rating}" varStatus="loop">
            <span class="fa fa-star checked"></span>
</c:forEach>
<c:forEach begin="1" end="${5 - param.rating}" varStatus="loop">
    <span class="fa fa-star-o checked"></span>
</c:forEach>