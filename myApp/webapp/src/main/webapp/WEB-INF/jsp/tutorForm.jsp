<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Tutor Form – GetAProff</title>
    <link rel="shortcut icon" href="<c:url value="${pageContext.request.contextPath}/resources/images/favicon.png"/>" type="image/x-icon">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <link rel="stylesheet"  type="text/css" href="<c:url value="${pageContext.request.contextPath}/resources/styles/main.css"/>"/>
</head>
<body>
    <jsp:include page="../components/navbar.jsp">
        <jsp:param name="isMainPage" value="${false}"/>
    </jsp:include>
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

            <div class="subject-container">
                <p class="section-title subject-title"><c:out value="Materias"/></p>
                <div class="subjects-selector-container">
                    <table>
                        <thead>
                            <tr>
                                <th>
                                    <div class="dropdown">
                                        <input onclick="myFunction()" class="form-control  search-stl" type="text" placeholder="Selecciona tu materia" id="myInput" onkeyup="filterFunction()">
                                        <div id="myDropdown" class="dropdown-content">
                                            <c:forEach var="subject" items="${subjects}" varStatus="loop">
                                                <c:choose>
                                                    <c:when test="${loop.index<=5}">
                                                        <a href="#${subject.name}" onclick="searchFunction('${subject.name}')"><c:out value="${subject.name}"/></a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="#${subject.name}" style="display: none" onclick="searchFunction('${subject.name}')"><c:out value="${subject.name}"/></a>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </th>
                                <th>
                                    <div class="price-container">
                                        <span class="price-icon">$</span>
                                        <input  class="form-control subject-price-input " placeholder="Precio" type="text" id="pricebox">
                                    </div>
                                </th>
                                <th>
                                    <button class="btn-custom btn-custom-plus">+</button>
                                </th>
                            </tr>
                        </thead>

                        <c:forEach var="subject" items="${subjects}">
                            <tr>
                                <td>
                                    ${subject.name}
                                </td>
                                <td class="price-container" style="justify-content: center">
                                    $965
                                </td>
                                <td>
                                    <button class="btn-custom btn-custom-plus">x</button>
                                </td>
                            </tr>
                        </c:forEach>

                    </table>
                </div>
            </div>

            <script>
                function searchFunction(value) {
                    document.getElementById("myInput").setAttribute("value",value);
                }

                function myFunction() {
                    document.getElementById("myDropdown").classList.toggle("show");
                }

                function filterFunction() {
                    var input, filter, ul, li, a, i, j;
                    input = document.getElementById("myInput");
                    filter = input.value.toUpperCase();
                    div = document.getElementById("myDropdown");
                    a = div.getElementsByTagName("a");
                    for (i = 0, j = 0; i < a.length; i++) {
                        txtValue = a[i].textContent || a[i].innerText;
                        if (txtValue.toUpperCase().indexOf(filter) > -1 && j <= 5) {
                            a[i].style.display = "";
                            j = j + 1;
                        } else {
                            a[i].style.display = "none";
                        }
                    }
                }
            </script>
            <div class="section-container">
                <jsp:include page="../components/timetable.jsp"/>
            </div>
            <div class="btn-container">
                <button type="submit" class="btn btn-custom-outline submit-button p-2 bd-highlight"><c:out value="Volver atras"/></button>
                <button type="submit" class="btn btn-custom submit-button p-2 bd-highlight"><c:out value="Enviar Formulario"/></button>
            </div>
        </form:form>
    </div>
</body>
</html>
