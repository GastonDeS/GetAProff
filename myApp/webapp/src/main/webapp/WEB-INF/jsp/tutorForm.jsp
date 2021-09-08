<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Tutor Form</title>
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
    <div class="main-container">
        <c:url value="/create" var="postPath"/>
        <form:form modelAttribute="tutorForm" action="${postPath}"  method="post">
            <div class="section-container">
                <p class="section-title"><c:out value="Información Personal"/></p>
                <div class="input-section">
                    <div class="form-input">
                        <form:input type="text" class="form-control" path="name" placeholder="Nombre"/>
                        <form:errors path="name" cssClass="formError" element="p"/>
                    </div>
                    <div class="form-input">
                        <div class="mail-input">
                            <form:input type="email" class="form-control" path="mail" aria-describedby="emailHelp" placeholder="Correo Electrónico"/>
                            <small id="emailHelp" class="form-text text-muted"><c:out value="Por ejemplo, user@mail.com"/></small>
                            <form:errors path="mail" cssClass="formError" element="p"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="section-container">
                <p class="section-title"><c:out value="Materias"/></p>
                <div class="subject-section">
                    <c:forEach var="subject" items="${subjects}">
                        <div class="subject-box">
                            <input type="checkbox" id="${subject}box">
                            <label class="subject-check-label" for="${subject}box"><c:out value="${subject}"/></label>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="section-container">
                <jsp:include page="../components/timetable.jsp"/>
            </div>
            <div class="btn-container">
                <button type="submit" class="btn btn-custom ms-auto p-2 bd-highlight"><c:out value="Submit"/></button>
            </div>
        </form:form>
    </div>
</body>
</html>
