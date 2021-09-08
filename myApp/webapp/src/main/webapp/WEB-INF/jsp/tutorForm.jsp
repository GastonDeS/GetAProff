<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Tutor Form – GetAProff</title>
    <link rel="shortcut icon" href="<c:url value="${pageContext.request.contextPath}/resources/images/favicon.png"/>" type="image/x-icon">
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
    <div class="mainContainer">
        <c:url value="/create" var="postPath"/>
        <form:form modelAttribute="tutorForm" action="${postPath}"  method="post">
            <div class="form-group">
                <form:label path="name"></form:label>
                <form:input type="text" class="form-control" path="name" placeholder="Nombre"/>
                <form:errors path="name" cssClass="formError" element="p"/>
            </div>
            <div class="form-group">
                <form:label path="mail"></form:label>
                <form:input type="email" class="form-control" path="mail" aria-describedby="emailHelp" placeholder="Ingresar correo electrónico"/>
                <small id="emailHelp" class="form-text text-muted">Por ejemplo, user@mail.com</small>
                <form:errors path="mail" cssClass="formError" element="p"/>
            </div>
            <div class="form-group form-check">
                <input type="checkbox" class="form-check-input" id="exampleCheck1">
                <label class="form-check-label" for="exampleCheck1">Check me out</label>
            </div>
            <div class="timetable">
                <jsp:include page="../components/timeRange.jsp">
                    <jsp:param name="day" value="lunes" />
                </jsp:include>
                <jsp:include page="../components/timeRange.jsp">
                    <jsp:param name="day" value="lunes" />
                </jsp:include>
                <jsp:include page="../components/timeRange.jsp">
                    <jsp:param name="day" value="lunes" />
                </jsp:include>
                <jsp:include page="../components/timeRange.jsp">
                    <jsp:param name="day" value="lunes" />
                </jsp:include>
                <jsp:include page="../components/timeRange.jsp">
                    <jsp:param name="day" value="lunes" />
                </jsp:include>
                <jsp:include page="../components/timeRange.jsp">
                    <jsp:param name="day" value="lunes" />
                </jsp:include>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form:form>
    </div>
</body>
</html>
