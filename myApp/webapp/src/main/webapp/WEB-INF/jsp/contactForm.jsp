<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
    <head>
        <title>Contact Form – GetAProff </title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <link rel="shortcut icon" href="<c:url value="${pageContext.request.contextPath}/resources/images/favicon.png"/>" type="image/x-icon">
        <link rel="stylesheet"  type="text/css" href="<c:url value="${pageContext.request.contextPath}/resources/styles/main.css"/>"/>
        <script type="text/javascript" src="<c:url value="${pageContext.request.contextPath}/resources/js/script.js"/>"></script>
    </head>
    <body>
        <jsp:include page="../components/navbar.jsp">
            <jsp:param name="isMainPage" value="${false}"/>
        </jsp:include>
        <div class="container">
            <h1>Contactarse con ${user.name} .. ${user.mail} .... ${subjectName}</h1>
            <div class="container jumbotron">
                <div class="row">
                    <div class="col-md-8 mx-auto ">
                        <c:url value="/contact?uid=${user.id}&subjectName=${subjectName}" var="contactUrl"/>
                        <form:form modelAttribute="contactForm" action="${contactUrl}" method="post">
                            <div class="form-group">
                                <form:label path="name"/>
                                <form:input type="text" path="name" placeholder="Tu nombre"/>
                                <form:errors path="name" element="p" cssStyle="color: red"/>
                            </div>
                            <div class="form-group">
                                <form:label path="email"/>
                                <form:input type="text" path="email" placeHolder="Tu email"/>
                                <form:errors path="email" element="p" cssStyle="color: red"/>
                            </div>
                            <div class="form-group">
                                <form:label path="message"/>
                                <form:textarea type="text" path="message" placeHolder=" Tu mensaje, ejemplo: que tal, me gustar[ia que fueses mi profesor, se encuantra libre en el horario..."/>
                                <form:errors path="message" element="p" cssStyle="color: red"/>
                            </div>
                            <div>
                                <input type="submit" class="btn btn-primary" value="Enviar!">
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js" integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js" integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/" crossorigin="anonymous"></script>
    </body>
</html>
