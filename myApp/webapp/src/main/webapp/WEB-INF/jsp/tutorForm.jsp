<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="tutorCreate.title"/> – GetAProff</title>
    <link rel="shortcut icon" href="<c:url value="resources/images/favicon.png"/>" type="image/x-icon">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <link rel="stylesheet"  type="text/css" href="<c:url value="resources/styles/main.css"/>"/>
    <spring:message code="tutorCreate.form.namePlaceholder" var="namePlaceholder"/>
    <spring:message code="tutorCreate.form.mailPlaceholder" var="mailPlaceholder"/>
</head>
<body>
    <jsp:include page="../components/navbar.jsp">
        <jsp:param name="isMainPage" value="${false}"/>
    </jsp:include>
    <div class="main-container">
        <c:url value="/create" var="postPath"/>
        <form:form modelAttribute="tutorForm" action="${postPath}"  method="post">
            <div class="section-container">
                <p class="section-title"><spring:message code="tutorCreate.form.title"/> </p>
                <div class="input-section">
                    <div class="form-input">
                        <form:input type="text" class="form-control" path="name" placeholder="${namePlaceholder}"/>
                        <form:errors path="name" cssClass="formError" element="p"/>
                    </div>
                    <div class="form-input">
                        <div class="mail-input">
                            <form:input type="email" class="form-control" path="mail" aria-describedby="emailHelp" placeholder="${mailPlaceholder}"/>
                            <small id="emailHelp" class="form-text text-muted"><spring:message code="tutorCreate.form.mailHint"/> user@mail.com</small>
                            <form:errors path="mail" cssClass="formError" element="p"/>
                        </div>
                    </div>
                </div>
            </div>

            <div class="subject-container">
                <p class="section-title subject-title"><spring:message code="tutorCreate.form.subjectsTitle"/></p>
                <div class="subjects-selector-container">
                    <table id="subjectTable">
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
                                    <button type="button" onclick="addSubject()"  class="btn-custom btn-custom-plus">+</button>
                                </th>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>

            <script>


                function removeSubject(id) {
                    let elem = document.getElementById(id);
                    elem.parentNode.removeChild(elem);
                }

                function addSubject(){
                    let subject = document.getElementById("myInput").value;
                    let price = document.getElementById("pricebox").value;
                    price = Number(price);
                    if ( subject!=null && subject.toString().length >= 2 && price>=0  ) {
                        let tr = document.createElement("tr");
                        let name = document.createElement("td");
                        let priceElem = document.createElement("td");
                        let deleteTd = document.createElement("td");
                        let deleteBtn = document.createElement("button");

                        let text = document.createTextNode(subject);
                        let priceTextElem = document.createTextNode("$ "+price);
                        let x = document.createTextNode("x");
                        deleteBtn.appendChild(x);
                        name.appendChild(text);
                        priceElem.appendChild(priceTextElem);

                        let table = document.getElementById("subjectTable");

                        priceElem.className += "price-container"
                        deleteBtn.className += "btn-custom btn-custom-plus"

                        tr.id = table.childElementCount.toString();

                        deleteTd.appendChild(deleteBtn);
                        deleteTd.setAttribute("onClick","removeSubject("+tr.id+")");

                        tr.appendChild(name);
                        tr.appendChild(priceElem);
                        tr.appendChild(deleteTd);


                        table.appendChild(tr);

                        document.getElementById("myInput").value = null;
                        document.getElementById("pricebox").value = null;
                    }
                }

                function searchFunction(value) {
                    document.getElementById("myInput").value = value;
                }

                function myFunction() {
                    if (document.getElementById("myDropdown").style.display === "flex") {
                        document.getElementById("myDropdown").style.display = "none";
                    } else {
                        document.getElementById("myDropdown").style.display = "flex";
                        document.getElementById("myDropdown").style.flexDirection = "column";
                    }
                }

                function filterFunction() {
                    document.getElementById("myDropdown").style.display = "flex";
                    document.getElementById("myDropdown").style.flexDirection = "column";
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
                <button type="button" onclick="window.location.href='${pageContext.request.contextPath}/'" class="btn btn-custom-outline submit-button p-2 bd-highlight"><c:out value="Volver atras"/></button>
                <button type="submit" class="btn btn-custom submit-button p-2 bd-highlight">
                    <spring:message code="tutorCreate.form.buttonText"/>
                </button>
            </div>
        </form:form>
    </div>
</body>
</html>
