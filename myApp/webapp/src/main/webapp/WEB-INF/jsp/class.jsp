<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <title>Class</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.png"/>" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/styles/main.css"/>">
</head>
<body>
    <jsp:include page="../components/navbar.jsp">
        <jsp:param name="isMainPage" value="${true}"/>
        <jsp:param name="uid" value="${currentUser.id}"/>
    </jsp:include>
    <div class="page-container">
        <div style="width: 80%">
            <img style="width: 100%; margin-bottom: 20px ;object-fit: cover; border-radius: 10px" height="200px" src="<c:url value="/resources/images/matematica_banner.png"/>" alt="matematica banner">
            <div class="container">
                <div class="row">
                    <div class="col class-content">
                        <h6>Matematica</h6>
                        <p>Profe Luis</p>
                    </div>
                    <div class="col-6 class-content">
                        <div class="upload-box">
                            <div class="form-input">
<%--                                <label><spring:message code="contact.form.message"/></label>--%>
                                <textarea style="height: 100px" type="text" class="form-control" placeholder="Ingresa una descripcion"></textarea>
<%--                                <errors path="message" element="p" cssClass="form-error"/>--%>
                            </div>
                            <div class="file-upload">
                                <div style="width: fit-content; margin-left: 0" class="btn-container">
                                    <div class="btn btn-custom">
                                        <input type="file" style="display: none"/>
                                        <c:out value="Seleccionar archivo"/>
                                    </div>
                                </div>
                                <div style="width: fit-content" class="btn-container">
                                    <input type="submit" class="btn btn-custom bd-highlight"
                                           value="Publicar">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col class-content">
                        <p>Lorem ipsum.</p>
                    </div>
                </div>
                <div class="row justify-content-center">
                    <div class="col-6 posts-big-box">
                        <div class="post-box">
                            <h3>Gaston</h3>
                            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
                            <h6 style="color: blue;text-decoration: underline">TP6 - TLA automatas de pila</h6>
                        </div>
                        <div class="post-box">
                            <h3>Profe luis</h3>
                            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
<style>
    .posts-big-box {
        padding: 0;
    }
    .post-box {
        padding: 20px;
        border-radius: 10px;
        margin-top: 20px;
        background-color: white;
        /*height: 200px*/
    }
    .class-main-content {
        display: flex;
        flex-direction: column;
        width: 100%;
        justify-content: center;
        align-items: center;
    }
    .class-content {
        padding: 15px;
        background-color: white;
        border-radius: 10px;
        margin: 10px;
    }

    .file-upload {
        width: 80%;
        display: flex;
        justify-content: space-between;
    }
    .text-input {
        border-radius: 5px;
        font-size: 12px;
        background-color: #edeae5;
        width: 80%;
        height: 50px;
        margin: 5px;
        justify-content: start;
        align-self: initial;
    }

    .upload-box {
        margin: 5px;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        width: 100%;
        border-radius: 10px;
        background-color: white;
    }
</style>

</html>
