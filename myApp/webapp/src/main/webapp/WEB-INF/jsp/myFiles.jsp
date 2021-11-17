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
    <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
         aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog modal-xl">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="form-title"><spring:message code="myFiles.addFileTitle"/></h4>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body" style="background-color: #9fedd7;padding: 10px 20px 5px 20px;">
                    <div class="form-container" style="width: 100%;">
                        <div class="form-body-container">
                            <c:choose>
                                <c:when test="${userSubjects.size() != 0}">
                                    <form name="subject-files-form" id="subject-files-form"
                                          action="${pageContext.request.contextPath}/myFiles"
                                          method="post" enctype="multipart/form-data"
                                          style="display: flex; flex-direction: column; margin: 0">
                                        <h3 style="align-self: center; color: #026670;"><spring:message
                                                code="myFiles.addFileHelp"/></h3>
                                        <div class="selected-files-options-container">
                                            <div class="files-filter-container">
                                                <label class="files-form-label" style="margin-right: 10px;"
                                                       for="subject-select"><spring:message
                                                        code="myFiles.subjectLabel"/>:</label>
                                                <select id="subject-select" name="subject" onchange="showAndHide()"
                                                        required>
                                                    <c:forEach var="subject" items="${userSubjects}">
                                                        <option value="${subject.subjectId}">${subject.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="files-filter-container">
                                                <label class="files-form-label" style="margin-right: 10px;"
                                                       for="level-select"><spring:message
                                                        code="myFiles.levelLabel"/>:</label>
                                                <select id="level-select" name="level" required>
                                                    <option selected value="0"><spring:message
                                                            code="myFiles.select.anyLevel"/></option>
                                                    <c:forEach var="subject" items="${userSubjectInfo}">
                                                        <option value="${subject.subjectId}${subject.level}">
                                                            <spring:message
                                                                    code="myFiles.filesTable.level${subject.level}"/></option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="files-input-container">
                                            <ul id="selected-files-ul" class="selected-files-ul"
                                                style="display: none;margin-top: 20px"></ul>
                                        </div>
                                        <label id="label-for-file-input" class="btn btn-custom"
                                               style="align-self: center; margin-top: 12px;">
                                            <spring:message code="myFiles.button.loadFiles"/>
                                            <input type="file" id="file" name="files" accept="application/pdf"
                                                   style="display:none;" onclose="showUlOfFiles()"
                                                   multiple required>
                                        </label>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <div class="no-subjects-container">
                                        <h3 style="color:#026670;"><spring:message code="myFiles.no.subject.given"/></h3>
                                        <a href="${pageContext.request.contextPath}/editSubjects"
                                           class="btn btn-custom submit-btn">
                                            <spring:message code="myFiles.add.subject"/>
                                        </a>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-custom" style="background: #848e8b;" data-bs-dismiss="modal">
                        <spring:message code="myFiles.button.cancelModal"/>
                    </button>
                    <button class="btn btn-custom" form="subject-files-form" style="align-self:center;"
                            type="submit">
                        <spring:message code="myFiles.button.saveChanges"/>
                    </button>
                </div>
            </div>
        </div>
    </div>
    <div class="form-container" style="margin-top: 15px;">
        <h2 class="form-title"><spring:message code="myFiles.title"/></h2>
        <c:choose>
            <c:when test="${userSubjectFiles.size() != 0}">
                <h5 style="margin: 10px 0 10px 0; color: #026670;"><spring:message code="myFiles.filterTitle"/></h5>
                <form action="${pageContext.request.contextPath}/myFiles" method="get"
                      style="display: flex; width: 90%" id="filter-form" name="filter-form">
                    <div class="files-filter-container">
                        <label class="files-form-label" for="subject-select-filter" style="margin-right: 10px;">
                            <spring:message code="myFiles.subjectLabel"/>:
                        </label>
                        <select name="subject-select-filter" id="subject-select-filter">
                            <option selected value="0"><spring:message code="myFiles.filter.anySubjects"/></option>
                            <c:forEach var="subject" items="${userSubjects}">
                                <option value="${subject.subjectId}">${subject.name}</option>
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
                </form>

                <table class="subjects-table">
                    <tr class="subjects-row">
                        <td class="row-title" style="width: 40%"><spring:message code="myFiles.rowTitle.file"/></td>
                        <td class="row-title" style="width: 15%"><spring:message code="myFiles.rowTitle.subject"/></td>
                        <td class="row-title" style="width: 25%"><spring:message code="myFiles.rowTitle.level"/></td>
                        <td class="row-title" style="width: 11%"><spring:message
                                code="myFiles.rowTitle.selection"/></td>
                    </tr>
                    <form name="delete-form" id="delete-form"
                          action="${pageContext.request.contextPath}/myFilesDelete/${user.id}" method="post">
                        <c:forEach var="file" items="${userSubjectFiles}">
                            <tr class="subjects-row">
                                <td class="row-info" style="width: 40%">${file.fileName}</td>
                                <td class="row-info" style="width: 15%">${file.teachesInfo.subject.name}</td>
                                <td class="row-info" style="width: 25%"><spring:message
                                        code="subjects.form.level.${file.teachesInfo.level}"/></td>
                                <td style="width: 11%;padding-left: 4.5%;">
                                    <input type="checkbox" name="deleted-files" class="form-check-input"
                                           value="${file.fileId}">
                                </td>
                            </tr>
                        </c:forEach>
                    </form>
                </table>
            </c:when>
            <c:when test="${filtering && userSubjectFiles.size() == 0}">
                <h5 style="margin: 10px 0 10px 0; color: #026670;"><spring:message code="myFiles.filterTitle"/></h5>
                <form action="${pageContext.request.contextPath}/myFiles" method="get"
                      style="display: flex; width: 90%" id="filter-form" name="filter-form">
                    <div class="files-filter-container">
                        <label class="files-form-label" for="subject-select-filter" style="margin-right: 10px;">
                            <spring:message code="myFiles.subjectLabel"/>:
                        </label>
                        <select name="subject-select-filter" id="subject-select-filter">
                            <option selected value="0"><spring:message code="myFiles.filter.anySubjects"/></option>
                            <c:forEach var="subject" items="${userSubjects}">
                                <option value="${subject.subjectId}">${subject.name}</option>
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
                </form>
                <div class="no-files-found-filtering">
                    <h3 style="color: #026670;"><spring:message code="myFiles.noFilesFound.filtering"/></h3>
                    <a style="" href="${pageContext.request.contextPath}/myFiles"><spring:message
                            code="myFiles.cleanFilter"/></a>
                </div>

            </c:when>
            <c:otherwise>
                <h4 style="margin: 10px 0 20px 0;">
                    <spring:message code="myFiles.noFilesYet"/></h4>
            </c:otherwise>
        </c:choose>
        <div class="myFiles-main-btn-container">
            <button class="btn btn-custom" id="delete-btn" style="display: none; margin-bottom: 5px;align-self: center;"
                    form="delete-form"
                    type="submit">
                <spring:message code="myFiles.button.deleteSelected"/>
                <button type="button" class="btn btn-custom" style="display: none;background: #848e8b"
                        id="unmark-delete-button" onclick="cleanDeleteCheckbox()"><spring:message
                        code="myFiles.button.cancelSelection"/>
                </button>
            </button>
        </div>
        <button type="button" id="upload-button" class="btn btn-custom" style="align-self: center"
                data-bs-toggle="modal"
                data-bs-target="#staticBackdrop">
            <spring:message code="myFiles.button.addFile"/>
        </button>
    </div>
    <button type="submit" id="submit-filter" form="filter-form" style="display: none"></button>
    <div id="hidden" style="display: none">
    </div>
</div>
<jsp:include page="../components/footer.jsp">
    <jsp:param name="" value=""/>
</jsp:include>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script>

    $(document).ready(showAndHide);

    function showAndHide() {
        var ids = $("#subject-select").val();
        $("#hidden").children().each(function () {
            $("#level-select").append($(this).clone());
            $(this).remove();
        });
        $("#level-select").children().each(function () {
            if ($(this).val() == (ids + '0') || $(this).val() == (ids + '1') || $(this).val() == (ids + '2') || $(this).val() == (ids + '3')) {
                $(this).show();
            } else {
                $("#hidden").append($(this).clone());
                $(this).remove();
            }
        });
    }


    function keepFiltersSelected() {
        const queryString = window.location.search;
        const urlParams = new URLSearchParams(queryString);
        var selectedSubject = urlParams.get("subject-select-filter");
        var levelSubject = urlParams.get("level-select-filter");
        if (selectedSubject !== null)
            document.getElementById("subject-select-filter").value = urlParams.get("subject-select-filter");
        if (levelSubject !== null)
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
        var cancelDeleteBtn = document.getElementById("unmark-delete-button");
        var uploadBtn = document.getElementById("upload-button");

        if (checkedOne) {
            deleteBtn.style.display = "block";
            cancelDeleteBtn.style.display = "block";
            uploadBtn.style.display = "none"

        } else {
            deleteBtn.style.display = "none";
            cancelDeleteBtn.style.display = "none";
            uploadBtn.style.display = "block"
        }
    }

    function cleanDeleteCheckbox() {
        const checkboxes = document.getElementsByName("deleted-files");
        checkboxes.forEach((cb) => {
            cb.checked = false;
        });
        showDeleteButton();
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
        if (ul.childNodes.length === 0)
            ul.style.display = 'none';
    }

    function showUlOfFiles() {
        var files = document.getElementById("file").files;
        var ul = document.getElementById("selected-files-ul");
        if (files.length === 0 || ul.childNodes.length === 0) {
            ul.style.display = 'none';
        } else {
            ul.style.display = 'block';

        }
    }

    document.getElementById('label-for-file-input').addEventListener('click', showUlOfFiles);

    document.getElementById('file').addEventListener('input', function () {
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
        showUlOfFiles();
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
