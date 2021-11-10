<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html lang="en">
<head>
    <title><spring:message code="contact.title"/> – GetAProff </title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.png"/>" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/styles/main.css"/>"/>
    <spring:message code="request.message.placeholder" var="messagePlaceHolder"/>
</head>
<body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="isMainPage" value="${true}"/>
    <jsp:param name="uid" value="${currentUid}"/>
</jsp:include>
<div class="page-container">
    <c:url value="/contact/${uid}" var="contactUrl"/>
    <h1 class="d-flex justify-content-center mt-4">
        <spring:message code="contact.form.contact.with"/>
        <c:out value="${user.name}"/></h1>
    <form:form class="d-flex justify-content-center" modelAttribute="contactForm" action="${contactUrl}" method="post">
        <div class="form-container">
            <div class="input-section">
                <div class="form-input">
                    <form:label path="subject"><spring:message code="contact.form.subject"/></form:label>
                    <form:select path="subject" id="select-subject-form" onchange="showAndHide()">
                        <c:forEach var="subject" items="${names}">
                            <form:option value="${subject.subjectId}" label="${subject.name}"/>
                        </c:forEach>
                    </form:select>
                    <form:select path="level" id="levels">
                        <c:forEach var="subject" items="${subjects}">
                            <spring:message code="subjects.form.level.${subject.level}" var="level"/>
                            <form:option value="${subject.subjectId}${subject.level}"  label="${level}"/>
                        </c:forEach>

                    </form:select>

                    <form:errors path="subject" element="p" cssClass="form-error"/>
                </div>
                <div class="btn-container">
                    <input type="submit" class="btn btn-custom bd-highlight"
                           value="<spring:message code="contact.form.request.button"/>">
                </div>
            </div>
        </div>
    </form:form>
    <div class="hidden" id="hidden">

    </div>
</div>
<jsp:include page="../components/footer.jsp">
    <jsp:param name="" value=""/>
</jsp:include>
<style>
    .hidden {
        display: none;
    }
</style>
<script>
    $(document).ready(showAndHide($("#select-subject-form").val()));

    function showAndHide() {
        var ids = $("#select-subject-form").val();
        $("#hidden").children().each(function () {
            $("#levels").append($(this).clone());
            $(this).remove();
        });
        $("#levels").children().each(function () {
            if ($(this).val() == (ids+'0') || $(this).val() == (ids+'1') || $(this).val() == (ids+'2') || $(this).val() == (ids+'3')) {
                $(this).show();
            } else {
                $("#hidden").append($(this).clone());
                $(this).remove();
            }
        });
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"
        integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js"
        integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/"
        crossorigin="anonymous"></script>
</body>
</html>