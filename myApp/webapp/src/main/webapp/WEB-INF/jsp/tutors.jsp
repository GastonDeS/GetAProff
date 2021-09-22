<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title><spring:message code="tutors.title"/> – GetAProff</title>
        <link rel="shortcut icon" href="<c:url value="resources/images/favicon.png"/>" type="image/x-icon">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <link rel="stylesheet"  type="text/css" href="<c:url value="resources/styles/main.css"/>"/>
    </head>
    <body>
        <jsp:include page="../components/navbar.jsp">
            <jsp:param name="isMainPage" value="${false}"/>
        </jsp:include>
        <div class="tutors-search">
            <form action="/tutors" class="search-filters" method="get">
                <input type="hidden" name="query" value="<%=request.getParameter("query")%>">
                        <div class="dropdown">
                            <button class="filter-item" type="button" id="priceDropdownButton" data-bs-toggle="dropdown"
                                        aria-expanded="true" aria-haspopup="true">
                                <spring:message code="search.dropdown.price.buttonText"/>
                            </button>
                            <div class="dropdown-menu" aria-labelledby="priceDropdownButton" style="width: 18vw;">
                                <div class="d-flex flex-column px-2">
                                    <h4 id="priceDisplay"><spring:message code="search.dropdown.price.buttonText"/></h4>
                                    <div class="d-flex justify-content-center">
                                        <input type="range" id="priceRange" class="form-range" min="1" max="${maxPrice}" value="${maxPrice}"
                                                   name="price" oninput="updatePrice(this.value)" onfocus="keepPriceButtonFocused()">
                                    </div>
                                    <hr class="dropdown-divider">
                                    <button type="button" class="btn btn-custom align-self-end">Apply</button>
                                </div>
                            </div>
                        </div>
                        <div class="dropdown">
                            <button class="filter-item" type="button" id="levelDropdownButton" data-bs-toggle="dropdown" aria-expanded="false">
                                <spring:message code="search.dropdown.level.buttonText"/>
                            </button>
                            <ul class="dropdown-menu" aria-labelledby="levelDropdownButton">
                                <li>
                                    <input type="radio" name="level" class="btn-check" id="btn-check4" autocomplete="off" value="0"
                                           onclick="updateLevel(this.value)" checked>
                                    <label class="dropdown-item" for="btn-check4"><spring:message code="search.dropdown.level.any"/></label>
                                </li>
                                <li>
                                    <input type="radio" name="level" class="btn-check" id="btn-check1" autocomplete="off" value="1" onclick="updateLevel(this.value)">
                                    <label class="dropdown-item" for="btn-check1"><spring:message code="search.dropdown.level.elementary"/></label>
                                </li>
                                <li>
                                    <input type="radio" name="level" class="btn-check" id="btn-check2" autocomplete="off" value="2"  onclick="updateLevel(this.value)">
                                    <label class="dropdown-item" for="btn-check2"><spring:message code="search.dropdown.level.middle"/></label>
                                </li>
                                <li>
                                    <input type="radio" name="level" class="btn-check" id="btn-check3" autocomplete="off" value="3"  onclick="updateLevel(this.value)">
                                    <label class="dropdown-item" for="btn-check3"><spring:message code="search.dropdown.level.college"/></label>
                                </li>
                            </ul>
                        </div>
                <button type="submit" id="filter-button" class="btn" style="display: none;">Aplicar Filtros</button>
                <button type="button" id="clear-filter-button" style="display: none;" onclick="resetFilters()"> Resetear Filtros</button>
            </form>
            <div class="search-bar">
                <form name="Search" action="/tutors" method="get" >
                    <input type="search" id="query" name="query" class="search-input" value="<%=request.getParameter("query")%>" required/>
                    <button type="submit" class="btn btn-custom">
                        <spring:message code="home.search.buttonText"/>
                    </button>
                </form>
            </div>
        </div>
        <c:if test="${fn:length(tutors)==0}">
            <h1 class="not-found-header"><spring:message code="tutors.search.empty"/></h1>
        </c:if>
        <div class="container">
            <div class="row">
                <c:forEach var="tutor" items="${tutors}" varStatus="loop">
                    <div style="margin-top: 30px" class="col-xxl-3 col-xl-4 col-lg-4 col-md-6 col-sm-12">
                        <div class="container">
                            <jsp:include page="../components/tutorCard.jsp">
                                <jsp:param name="name" value="${tutor.name}"/>
                                <jsp:param name="uid" value="${tutor.userId}"/>
                                <jsp:param name="description" value="${tutor.description}"/>
                            </jsp:include>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <script type="text/javascript" src="resources/js/script.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js" integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js" integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/" crossorigin="anonymous"></script>
    </body>
</html>