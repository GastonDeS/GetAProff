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
            <form name="subject-files-form" id="subject-files-form" action="${pageContext.request.contextPath}/myFiles"
                  method="post" enctype="multipart/form-data">
                <div class="files-input-container">
                    <label id="label-for-file-input" class="btn btn-custom">
                        Seleccione un archivo
                        <input type="file" id="file" name="files" accept="application/pdf" style="display:none;"
                               multiple>
                    </label>
                    <ul id="selected-files-ul" class="selected-files-ul" style="display: none;"></ul>
                </div>
                <h5 style="margin: 10px 0 10px 0; color: #026670;">Elija en que clases quiere disponiblizar el
                    archivo </h5>
                <div class="selected-files-options-container">
                    <div class="file-option">
                        <label class="files-form-label" style="width: 43%;" for="subject-select">Materia:</label>
                        <select id="subject-select" name="subject">
                            <option selected value="0"><spring:message code="myFiles.select.anySubject"/></option>
                            <c:forEach var="teaches" items="${userSubjects}">
                                <option value="${teaches.subject.id}">${teaches.subject.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="file-option">
                        <label class="files-form-label" style="width: 43%;" for="level-select">Nivel:</label>
                        <select id="level-select" name="level">
                            <option selected value="0"><spring:message code="myFiles.select.anyLevel"/></option>
                            <c:forEach begin="1" end="3" var="idx">
                                <option value="${idx}"><spring:message code="myFiles.filesTable.level.${idx}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </form>
            <button class="btn btn-custom" form="subject-files-form" style="align-self:center;" type="submit">Cargar
                Archivos
            </button>
        </div>
    </div>
    <div class="form-container" style="margin-top: 15px;">
        <h2 class="form-title">Mis Archivos</h2>
        <div style="display: flex; width: 90%">
            <div class="files-filter-container">
                <label class="files-form-label" for="subject-select-filter" style="margin-right: 10px;">
                    Materia:
                </label>
                <select id="subject-select-filter">
                    <option selected>Open this select menu</option>
                    <option value="Mate 1">One</option>
                    <option value="Mate 2">Two</option>
                    <option value="Mate 3">Three</option>
                </select>
            </div>
            <div class="files-filter-container">
                <label class="files-form-label" for="level-select-filter" style="margin-right: 10px;">
                    Nivel:
                </label>
                <select id="level-select-filter">
                    <option selected>Open this select menu</option>
                    <option value="Principiante">One</option>
                    <option value="Intermedio">Two</option>
                    <option value="Avanzado">Three</option>
                </select>
            </div>
        </div>
        <table class="subjects-table">
            <tr class="subjects-row">
                <td class="row-title" style="width: 59%">Archivo</td>
                <td class="row-title" style="width: 30%">Materia</td>
                <td class="row-title" style="width: 30%">Nivel</td>
            </tr>
            <c:forEach var="file" items="${userSubjectFiles}">
                <tr class="subjects-row">
                    <td class="row-info" style="width: 40%">${file.fileName}</td>
                    <td class="row-info" style="width: 15%">${file.subject.name}</td>
                    <td class="row-info" style="width: 25%"><spring:message
                            code="myFiles.filesTable.level${file.level}"/></td>
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
        const fileInput = document.getElementById("file");
        const files = fileInput.files;
        const dataTransfer = new DataTransfer();
        for (var i = 0; i < files.length; i++) {
            console.log(i + ' a ' + item)
            if (i != item) {
                dataTransfer.items.add(files[i])
            }
        }
        document.getElementById("file").files = dataTransfer.files;
        var ul = document.getElementById("selected-files-ul");
        ul.removeChild(ul.childNodes[item]);
    }

    function showUlOfFiles() {
        var files = document.getElementById("file").files;
        var ul = document.getElementById("selected-files-ul");
        if (files.length == 0) {
            ul.style.display = 'none';
        } else {
            ul.style.display = 'block';

        }
    }

    document.getElementById('label-for-file-input').addEventListener('click', showUlOfFiles);

    document.getElementById('file').addEventListener('input', function () {
        showUlOfFiles();
        var files = document.getElementById("file").files;
        var ul = document.getElementById("selected-files-ul");
        ul.querySelectorAll('*').forEach(n => n.remove());
        for (var i = 0; i < files.length; i++) {
            var li = document.createElement("li");
            li.appendChild(document.createTextNode(files[i].name));
            let btn = document.createElement("button");
            btn.innerHTML = "X";
            btn.value = i;
            btn.setAttribute("type", "button");
            btn.className = 'btn btn-custom';
            btn.onclick = () => removeItemFromFilesArray(btn.value);
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
