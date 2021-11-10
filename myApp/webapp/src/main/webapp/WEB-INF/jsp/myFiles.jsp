<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <h2 class="form-title"><spring:message code="myFiles.addFileTitle"/></h2>
        <div class="form-body-container">
            <form name="subject-files-form" id="subject-files-form" action="${pageContext.request.contextPath}/myFiles"
                  method="post" enctype="multipart/form-data">
                <div class="files-input-container">
                    <label id="label-for-file-input" class="btn btn-custom">
                        <spring:message code="myFiles.button.chooseFiles"/>
                        <input type="file" id="file" name="files" accept="application/pdf" style="display:none;"
                               multiple required>
                    </label>
                    <ul id="selected-files-ul" class="selected-files-ul" style="display: none;"></ul>
                </div>
                <h5 style="margin: 10px 0 10px 0; color: #026670;"><spring:message code="myFiles.addFileHelp"/></h5>
                <div class="selected-files-options-container">
                    <div class="file-option">
                        <label class="files-form-label" style="width: 43%;" for="subject-select"><spring:message
                                code="myFiles.subjectLabel"/>:</label>
                        <select id="subject-select" name="subject" required>
                            <c:forEach var="subject" items="${userSubjects}">
                                <option value="${subject.id}">${subject.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="file-option">
                        <label class="files-form-label" style="width: 43%;" for="level-select"><spring:message
                                code="myFiles.levelLabel"/>:</label>
                        <select id="level-select" name="level" required>
                            <option selected value="0"><spring:message code="myFiles.select.anyLevel"/></option>
                            <c:forEach var="index" begin="1" end="3">
                                <option value="${index}"><spring:message
                                        code="myFiles.filesTable.level${index}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </form>
            <button class="btn btn-custom" form="subject-files-form" style="align-self:center;" type="submit">
                <spring:message code="myFiles.button.loadFiles"/>
            </button>
        </div>
    </div>
    <div class="form-container" style="margin-top: 15px;">
        <h2 class="form-title"><spring:message code="myFiles.title"/></h2>
        <h5 style="margin: 10px 0 10px 0; color: #026670;"><spring:message code="myFiles.filterTitle"/></h5>
        <form action="${pageContext.request.contextPath}/myFiles" method="get" style="display: flex; width: 90%">
            <div class="files-filter-container">
                <label class="files-form-label" for="subject-select-filter" style="margin-right: 10px;">
                    <spring:message code="myFiles.subjectLabel"/>:
                </label>
                <select name="subject-select-filter" id="subject-select-filter">
                    <option selected value="0"><spring:message code="myFiles.filter.anySubjects"/></option>
                    <c:forEach var="subject" items="${userSubjects}">
                        <option value="${subject.id}">${subject.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="files-filter-container">
                <label class="files-form-label" for="level-select-filter" style="margin-right: 10px;">
                    <spring:message code="myFiles.levelLabel"/>:
                </label>
                <select name="level-select-filter" id="level-select-filter">
                    <option selected value="0"><spring:message code="myFiles.select.anyLevel"/></option>
                    <c:forEach var="index" begin="1" end="3">
                        <option value="${index}"><spring:message
                                code="myFiles.filesTable.level${index}"/></option>
                    </c:forEach>
                </select>
            </div>
            <button type="submit" id="submit-filter" style="display: none"></button>
        </form>

        <c:choose>
            <c:when test="${userSubjectFiles.size() != 0}">
                <table class="subjects-table">
                    <tr class="subjects-row">
                        <td class="row-title" style="width: 40%"><spring:message code="myFiles.rowTitle.file"/></td>
                        <td class="row-title" style="width: 15%"><spring:message code="myFiles.rowTitle.subject"/></td>
                        <td class="row-title" style="width: 25%"><spring:message code="myFiles.rowTitle.level"/></td>
                        <td style="width: 4%;align-self: center;display: flex;justify-content: center;">
                            <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor"
                                 class="bi bi-trash" viewBox="0 0 16 16">
                                <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"></path>
                                <path fill-rule="evenodd"
                                      d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"></path>
                            </svg>
                        </td>
                    </tr>
                    <form name="delete-form" id="delete-form"
                          action="${pageContext.request.contextPath}/myFilesDelete/${user.id}" method="post">
                        <c:forEach var="file" items="${userSubjectFiles}">
                            <tr class="subjects-row">
                                <td class="row-info" style="width: 40%">${file.fileName}</td>
                                <td class="row-info" style="width: 15%">${file.subject.name}</td>
                                <td class="row-info" style="width: 25%"><spring:message
                                        code="myFiles.filesTable.level${file.level}"/></td>
                                <td style="width: 3%;">
                                    <input type="checkbox" name="deleted-files" class="form-check-input"
                                           value="${file.fileId}">
                                </td>
                            </tr>
                        </c:forEach>
                    </form>
                </table>
            </c:when>
            <c:otherwise>
                <h4><spring:message code="myFiles.noFilesYet"/></h4>
            </c:otherwise>
        </c:choose>
        <div class="myFiles-main-btn-container">
            <button class="btn btn-custom" id="delete-btn" style="display: none; margin-bottom: 5px;align-self: center;" form="delete-form"
                    type="submit">
                <spring:message code="myFiles.button.deleteSelected"/>
            </button>
            <a href="${pageContext.request.contextPath}/profile/${user.id}" class="btn btn-custom submit-btn">
                <spring:message code="form.btn.save"/>
            </a>
        </div>
    </div>


</div>
<jsp:include page="../components/footer.jsp">
    <jsp:param name="" value=""/>
</jsp:include>
<script>

    function keepFiltersSelected() {
        const queryString = window.location.search;
        const urlParams = new URLSearchParams(queryString);
        var selectedSubject = urlParams.get("subject-select-filter");
        var levelSubject = urlParams.get("level-select-filter");
        if(selectedSubject !== null)
            document.getElementById("subject-select-filter").value = urlParams.get("subject-select-filter");
        if(levelSubject !== null)
            document.getElementById("level-select-filter").value = urlParams.get("level-select-filter");
    }
    function filter() {
        const btn = document.getElementById('submit-filter');
        document.getElementById("subject-select-filter").addEventListener("input", () => btn.click());
        document.getElementById("level-select-filter").addEventListener("input", () => btn.click());
    }

    function showDeleteButton() {
        var checkboxes = document.getElementsByName("deleted-files");
        var checkedOne = Array.prototype.slice.call(checkboxes).some(x => x.checked);

        var deleteBtn = document.getElementById("delete-btn");
        if (checkedOne) {
            deleteBtn.style.display = "block";
        } else deleteBtn.style.display = "none";
    }

    const removeItemFromFilesArray = (item) => {
        const fileInput = document.getElementById("file");
        const files = fileInput.files;
        const dataTransfer = new DataTransfer();
        for (var i = 0; i < files.length; i++) {
            if (files[i].name !== item.textContent) {
                dataTransfer.items.add(files[i])
            }
        }
        fileInput.files = dataTransfer.files;
        var ul = document.getElementById("selected-files-ul");
        ul.querySelectorAll('li').forEach(n => {
                if (n.childNodes[0] === item) {
                    n.remove();
                }
            }
        );
    }

    function showUlOfFiles() {
        var files = document.getElementById("file").files;
        var ul = document.getElementById("selected-files-ul");
        if (files.length === 0) {
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
            btn.onclick = () => removeItemFromFilesArray(btn.previousSibling);
            li.appendChild(btn);
            li.className = 'subjects-row'
            ul.appendChild(li);
        }

    });
    keepFiltersSelected();
    filter();
    document.getElementsByName("deleted-files").forEach(n => n.addEventListener('click', showDeleteButton));
</script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"
        integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js"
        integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/"
        crossorigin="anonymous"></script>
</body>
</html>
