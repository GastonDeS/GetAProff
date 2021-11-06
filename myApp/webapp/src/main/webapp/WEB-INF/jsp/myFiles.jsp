<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: agus_
  Date: 11/5/2021
  Time: 2:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="myFiles.title"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.png"/>" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/styles/main.css"/>">
</head>
<body>
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="isMainPage" value="${true}"/>
    <jsp:param name="uid" value="${user.id}"/>
</jsp:include>
<div class="page-container">
    <div class="form-container">
        <h2 class="form-title">Agregar Archivo</h2>
        <div class="form-body-container">
            <div style="display: flex;">
                <label class="btn btn-custom">
                    Seleccione un archivo
                    <input type="file" id="file" accept="application/pdf" style="display:none;" multiple>
                </label>
                <ul id="selected-files-ul" class="selected-files-ul">
                </ul>
            </div>
            <div class="selected-files-options-container">
                <div class="file-option">
                    <label style="width: 45%;" for="subject-select">Materia:</label>
                    <select class="form-select" id="subject-select" aria-label="Default select example">
                        <option selected><spring:message code="myFiles.select.subjectSelectTitle"/></option>
                        <option value="0">Mate 1</option>
                        <option value="1">Mate 2</option>
                        <option value="2">Mate 3</option>
                    </select>
                </div>
                <div class="file-option">
                    <label style="width: 45%;" for="level-select">Nivel:</label>
                    <select class="form-select" id="level-select" aria-label="Default select example">
                        <option selected><spring:message code="myFiles.select.levelSelectTitle"/></option>
                        <option value="0">Todos los niveles</option>
                        <option value="1">Principiante</option>
                        <option value="2">Intermedio</option>
                        <option value="3">Avanzado</option>
                    </select>
                </div>
            </div>
            <button class="btn btn-custom" style="align-self:center;" type="submit">Cargar Materias</button>
        </div>
    </div>
    <div class="form-container" style="margin-top: 15px;">
        <h2 class="form-title">Mis Archivos</h2>
        <div style="display: flex">
            <select class="form-select" aria-label="Default select example">
                <option selected>Open this select menu</option>
                <option value="Mate 1">One</option>
                <option value="Mate 2">Two</option>
                <option value="Mate 3">Three</option>
            </select>
            <select class="form-select" aria-label="Default select example">
                <option selected>Open this select menu</option>
                <option value="Principiante">One</option>
                <option value="Intermedio">Two</option>
                <option value="Avanzado">Three</option>
            </select>
        </div>
        <table class="subjects-table">
            <tr class="subjects-row">
                <td class="row-title" style="width: 43%">Archivo</td>
                <td class="row-title" style="width: 17%">Materia</td>
                <td class="row-title" style="width: 40%">Nivel</td>
            </tr>
            <c:forEach begin="1" end="3" var="subject">
                <tr class="subjects-row">
                    <td class="row-info" style="width: 40%">${subject}</td>
                    <td class="row-info" style="width: 15%">${subject}</td>
                    <td class="row-info" style="width: 25%">Avanxado</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<jsp:include page="../components/footer.jsp">
    <jsp:param name="" value=""/>
</jsp:include>
<script>

    const removeItemFromFilesArray = (item) => {
        var files = document.getElementById("file").files;
        var newFiles = [];
        for (var i = 0; i < files.length; i++) {
            if (files[i].name !== item)
                newFiles.push(files[i]);
        }
        document.getElementById("selected-files-ul").files = newFiles;
        var ul = document.getElementById("selected-files-ul");
        ul.querySelectorAll('li').forEach(n => {
                if (n.childNodes[0] === item) {
                    n.remove();
                }
            }
        );
    }

    document.getElementById('file').addEventListener('change', function () {
        var files = document.getElementById("file").files;
        var ul = document.getElementById("selected-files-ul");
        ul.querySelectorAll('*').forEach(n => n.remove());
        for (var i = 0; i < files.length; i++) {
            var li = document.createElement("li");
            li.appendChild(document.createTextNode(files[i].name));
            let btn = document.createElement("button");
            btn.innerHTML = "X";
            btn.className = 'btn btn-custom';
            btn.onclick = () =>  removeItemFromFilesArray(btn.parentElement.childNodes[0]);
            li.appendChild(btn);
            li.className = 'subjects-row'
            ul.appendChild(li);
        }

    });
</script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"
        integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js"
        integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/"
        crossorigin="anonymous"></script>
</body>
</html>
