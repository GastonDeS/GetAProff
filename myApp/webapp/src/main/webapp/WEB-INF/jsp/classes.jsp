<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="resources/styles/main.css"/>
    <script type="text/javascript" src="<c:url value="/resources/js/script.js"/>"></script>
</head>
<body>
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="isMainPage" value="${true}"/>
    <jsp:param name="uid" value="${uid}"/>
</jsp:include>
<div class="main-container">
    <h1><spring:message code="myClasses.mainTitle"/></h1>
    <div class="classes-separator-container">
        <div class="main-tab-container">
            <ul class="nav nav-tabs flex-column" id="myTab1" role="tablist">
                <li class="nav-item" role="presentation">
                    <button class="nav-link nav-link-custom active" style="width: 100%;" id="home-tab"
                            data-bs-toggle="tab" data-bs-target="#home" type="button"
                            role="tab" aria-controls="home" aria-selected="true"><spring:message code="myClasses.requested"/>
                    </button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link nav-link-custom" style="width: 100%;" id="profile-tab" data-bs-toggle="tab"
                            data-bs-target="#profile" type="button"
                            role="tab" aria-controls="profile" aria-selected="false"><spring:message code="myClasses.incoming"/>
                    </button>
                </li>
            </ul>
        </div>
        <div class="tab-content" id="myTabContent">
            <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                <div class="tabs-container">
                    <ul class="nav nav-tabs nav-fill" id="myTab2" role="tablist">
                        <li class="nav-item" role="presentation">
                            <button class="nav-link nav-link-custom" id="pending-tab2" data-bs-toggle="tab"
                                    data-bs-target="#pending2" type="button"
                                    role="tab" aria-controls="pending2" aria-selected="false"><spring:message
                                    code="myClasses.pending"/>
                            </button>
                        </li>
                        <li class="nav-item active" role="presentation">
                            <button class="nav-link active nav-link-custom" id="active-tab2" data-bs-toggle="tab"
                                    data-bs-target="#active2" type="button"
                                    role="tab" aria-controls="active2" aria-selected="true"><spring:message
                                    code="myClasses.active"/>
                            </button>
                        </li>
                        <li class="nav-item" role="presentation">
                            <button class="nav-link nav-link-custom" id="finished-tab2" data-bs-toggle="tab"
                                    data-bs-target="#finished2" type="button"
                                    role="tab" aria-controls="finished2" aria-selected="false"><spring:message
                                    code="myClasses.finished"/>
                            </button>
                        </li>
                    </ul>
                </div>
                <div class="classes-container">
                    <div class="tab-content" id="myTabContent2">
                        <div class="tab-pane fade show active" id="active2" role="tabpanel"
                             aria-labelledby="active-tab">
                            <div class="card w-100">
                                <div class="card-body">
                                    <h5 class="card-title">Card Activa</h5>
                                    <p class="card-text">With supporting text below as a natural lead-in to additional
                                        content.</p>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="pending2" role="tabpanel" aria-labelledby="pending-tab">
                            <c:forEach var="pendingClass" items="${pendingClasses}">
                                <div class="card w-100">
                                    <div class="card-body">
                                        <h5 class="card-title">Card pending</h5>
                                        <p class="card-text">With supporting text below as a natural lead-in to
                                            additional
                                            content.</p>
                                        <a href="#" class="btn btn-custom">Cancelar</a>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="tab-pane fade" id="finished2" role="tabpanel" aria-labelledby="finished-tab">
                            <div class="card w-100">
                                <div class="card-body">
                                    <h5 class="card-title">Card terminada</h5>
                                    <p class="card-text">With supporting text below as a natural lead-in to additional
                                        content.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
                <div class="tabs-container">
                    <ul class="nav nav-tabs nav-fill" id="myTab" role="tablist">
                        <li class="nav-item" role="presentation">
                            <button class="nav-link nav-link-custom" id="pending-tab" data-bs-toggle="tab"
                                    data-bs-target="#pending" type="button"
                                    role="tab" aria-controls="pending" aria-selected="false"><spring:message
                                    code="myClasses.pending"/>
                            </button>
                        </li>
                        <li class="nav-item active" role="presentation">
                            <button class="nav-link active nav-link-custom" id="active-tab" data-bs-toggle="tab"
                                    data-bs-target="#active" type="button"
                                    role="tab" aria-controls="active" aria-selected="true"><spring:message
                                    code="myClasses.active"/>
                            </button>
                        </li>
                        <li class="nav-item" role="presentation">
                            <button class="nav-link nav-link-custom" id="finished-tab" data-bs-toggle="tab"
                                    data-bs-target="#finished" type="button"
                                    role="tab" aria-controls="finished" aria-selected="false"><spring:message
                                    code="myClasses.finished"/>
                            </button>
                        </li>
                    </ul>
                </div>
                <div class="classes-container">
                    <div class="tab-content" id="myTabContent3">
                        <div class="tab-pane fade show active" id="active" role="tabpanel" aria-labelledby="active-tab">
                            <div class="card w-100">
                                <div class="card-body">
                                    <h5 class="card-title">Card Activa</h5>
                                    <p class="card-text">With supporting text below as a natural lead-in to additional
                                        content.</p>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="pending" role="tabpanel" aria-labelledby="pending-tab">
                            <c:forEach var="pendingClass" items="${pendingClasses}">
                                <div class="card w-100">
                                    <div class="card-body">
                                        <h5 class="card-title">Card pending</h5>
                                        <p class="card-text">With supporting text below as a natural lead-in to
                                            additional
                                            content.</p>
                                        <a href="#" class="btn btn-custom">Cancelar</a>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="tab-pane fade" id="finished" role="tabpanel" aria-labelledby="finished-tab">
                            <div class="card w-100">
                                <div class="card-body">
                                    <h5 class="card-title">Card terminada</h5>
                                    <p class="card-text">With supporting text below as a natural lead-in to additional
                                        content.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var triggerTabList = [].slice.call(document.querySelectorAll('#myTab a'))
    triggerTabList.forEach(function (triggerEl) {
        var tabTrigger = new bootstrap.Tab(triggerEl)

        triggerEl.addEventListener('click', function (event) {
            event.preventDefault()
            tabTrigger.show()
        })
    })
</script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"
        integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js"
        integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/"
        crossorigin="anonymous"></script>
</body>
</html>
