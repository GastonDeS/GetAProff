<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html lang="en">
<head>
    <title> <spring:message code="rate.title"/> - GetAProff </title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.png"/>" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/styles/main.css"/>"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script type="text/javascript" src="<c:url value="/resources/js/script.js"/>"></script>
    <spring:message code="rate.form.star" var="star"/>
    <spring:message code="rate.form.review.placeholder" var="reviewPlaceholder"/>
    <spring:message code="rate.form.rate" var="rate"/>
</head>
<body>
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="isMainPage" value="${true}"/>
    <jsp:param name="uid" value="${userid}"/>
</jsp:include>
<div class="page-container">
    <c:url value="/rate/${cid}" var="rateUrl"/>
    <h1 class="d-flex justify-content-center mt-4">
        <spring:message code="rate.form.header"/> ${teacher}
    </h1>
    <form:form class="d-flex justify-content-center" modelAttribute="rateForm" action="${rateUrl}" method="post">
        <div class="form-container">
            <div class="input-section">
                <div class="form-input">
                    <form:label path="rating">Rate</form:label>
                    <form:select path="rating">
                        <form:option value="1" label="1 ${star}"/>
                        <c:forEach var="i" begin="2" end="5">
                            <form:option value="${i}">
                                ${i} ${star}s
                            </form:option>
                        </c:forEach>
                    </form:select>
                </div>
                <div class="form-input">
                    <form:label path="review"><spring:message code="rate.form.review"/></form:label>
                    <form:textarea cssStyle="height: 150px" type="text" path="review"
                                   class="form-control"
                                   placeHolder="${reviewPlaceholder}"/>
                    <form:errors path="review" element="p" cssClass="form-error"/>
                </div>
                <div class="btn-container">
                    <input type="submit" class="btn btn-custom bd-highlight"
                           value="${rate}"/>
                </div>
            </div>
        </div>
    </form:form>
</div
<jsp:include page="../components/footer.jsp">
    <jsp:param name="" value=""/>
</jsp:include>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"
        integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js"
        integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/"
        crossorigin="anonymous"></script>
</body>
</html>

