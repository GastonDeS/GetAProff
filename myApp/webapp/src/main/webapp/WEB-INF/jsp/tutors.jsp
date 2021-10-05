<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="tutors.title"/> – GetAProff</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.png"/>" type="image/x-icon">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/styles/main.css"/>"/>
</head>
<body>
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="isMainPage" value="${true}"/>
    <jsp:param name="uid" value="${uid}"/>
</jsp:include>
<div class="page-container">
    <div class="tutors-container">
        <div class="filter-container">
            <h3 style="padding-left: 10px"><spring:message code="tutors.filtersTitle"/></h3>
            <form action="${pageContext.request.contextPath}/tutors/1" class="search-filters" method="get">
                <input type="hidden" name="query" value="<%=request.getParameter("query")%>">
                <ul class="filter-ulist">
                    <li>
                        <h4><spring:message code="tutors.filters.orderTitle"/></h4>
                        <select name="order" class="form-select" aria-label="Default select example">
                            <option value="0" selected><spring:message code="tutors.orderby.default"/></
                            <option>
                            <option value="1"><spring:message code="tutors.orderby.priceA"/></option>
                            <option value="2"><spring:message code="tutors.orderby.priceD"/></option>
                            <option value="3"><spring:message code="tutors.orderby.ratingA"/></option>
                            <option value="4"><spring:message code="tutors.orderby.ratingD"/></option>

                        </select>
                    </li>
                    <li>
                        <h4><spring:message code="search.filters.priceTitle"/></h4>
                        <div class="d-flex flex-column px-2">
                            <div class="range-wrap">
                                <div class="range-value" id="rangeV"></div>
                                <input type="range" id="priceRange" min="1" max="${maxPrice}"
                                       value="${maxPrice}"
                                       name="price">
                            </div>
                        </div>
                    </li>
                    <li>
                        <h4><spring:message code="search.dropdown.level.buttonText"/></h4>
                        <ul class="filter-ulist">
                            <li>
                                <div class="form-check">
                                    <input name="level" class="form-check-input" type="radio" id="any-level-input"
                                           autocomplete="off"
                                           value="0"
                                           checked
                                    >
                                    <label class="form-check-label" for="any-level-input"><spring:message
                                            code="search.dropdown.level.any"/></label>
                                </div>
                            </li>
                            <li>
                                <div class="form-check">
                                    <input name="level" class="form-check-input" type="radio"
                                           id="elementary-level-input"
                                           autocomplete="off"
                                           value="1">
                                    <label class="form-check-label" for="elementary-level-input"><spring:message
                                            code="search.dropdown.level.elementary"/></label>
                                </div>
                            </li>
                            <li>
                                <div class="form-check">
                                    <input name="level" class="form-check-input" type="radio" id="middle-level-input"
                                           autocomplete="off"
                                           value="2">
                                    <label class="form-check-label" for="middle-level-input"><spring:message
                                            code="search.dropdown.level.middle"/></label>
                                </div>
                            </li>
                            <li>
                                <div class="form-check">
                                    <input name="level" class="form-check-input" type="radio" id="college-level-input"
                                           autocomplete="off"
                                           value="3">
                                    <label class="form-check-label" for="college-level-input"><spring:message
                                            code="search.dropdown.level.college"/></label>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <h4>Rating</h4>
                        <ul class="filter-ulist">
                            <li>
                                <input name="rating" type="radio" class="rating-radio" id="rating-4+"
                                       value="4"
                                       checked
                                >
                                <label class="form-check-label" for="rating-4+">
                                    <jsp:include page="../components/ratingStars.jsp">
                                        <jsp:param name="rating" value="4"/>
                                    </jsp:include>
                                    <spring:message
                                            code="search.filter.ratingExtraText"/></label>
                            </li>
                            <li>
                                <input name="rating" type="radio" class="rating-radio" id="rating-3+"
                                       value="3">
                                <label class="form-check-label" for="rating-3+">
                                    <jsp:include page="../components/ratingStars.jsp">
                                        <jsp:param name="rating" value="3"/>
                                    </jsp:include>
                                    <spring:message
                                            code="search.filter.ratingExtraText"/></label>
                            </li>
                            <li>
                                <input name="rating" type="radio" class="rating-radio" id="rating-2+"
                                       value="2">
                                <label class="form-check-label" for="rating-2+">
                                    <jsp:include page="../components/ratingStars.jsp">
                                        <jsp:param name="rating" value="2"/>
                                    </jsp:include>
                                    <spring:message
                                            code="search.filter.ratingExtraText"/></label>
                            </li>
                            <li>
                                <input name="rating" type="radio" class="rating-radio" id="rating-1+"
                                       value="1">
                                <label class="form-check-label" for="rating-1+">
                                    <jsp:include page="../components/ratingStars.jsp">
                                        <jsp:param name="rating" value="1"/>
                                    </jsp:include>
                                    <spring:message
                                            code="search.filter.ratingExtraText"/></label>

                            </li>
                        </ul>
                    </li>
                </ul>
                <hr>
                <div class="filter-btn-container">
                    <button type="submit" id="filter-button" class="btn btn-custom"
                            style="display: none; width: 100%; margin: 5px 0;">
                        <spring:message code="tutors.filters.applyBtn"/>
                    </button>
                    <button type="button" id="clear-filter-button" class="btn btn-custom"
                            style="display: none; width: 100%; margin: 5px 0;"
                            onclick="resetFilters()">
                        <spring:message code ="tutors.filters.resetBtn"/>
                    </button>
                </div>
            </form>
        </div>
        <div class="search-and-results-container">
            <div class="tutors-search">
                <div class="search-bar">
                    <form name="Search" action="${pageContext.request.contextPath}/tutors" method="get">
                        <input class="form-control" list="datalistOptions" id="query" name="query"
                               value="<%=request.getParameter("query")%>"/>
                        <datalist id="datalistOptions">
                            <c:forEach var="subject" items="${subjects}">
                            <option value="${subject.name}">
                                </c:forEach>
                        </datalist>
                        <button type="submit" class="btn btn-custom">
                            <spring:message code="home.search.buttonText"/>
                        </button>
                    </form>
                </div>
            </div>
            <c:choose>
                <c:when test="${fn:length(tutors)==0}">
                    <h1 class="not-found-header"><spring:message code="tutors.search.empty"/></h1>
                </c:when>
                <c:otherwise>
                    <h3 style="margin: 20px 0 20px 7.5%; align-self: flex-start"> ${fn:length(tutors)} <spring:message
                            code="tutors.search.resultTitle"/> <%=request.getParameter("query")%>
                    </h3>
                </c:otherwise>
            </c:choose>
            <div class="row row-cols-1 row-cols-md-3 g-4" style="width: 85%;">
                    <c:forEach var="tutor" items="${tutors}" varStatus="loop">
                        <div style="margin-top: 30px" class="col">
                                <jsp:include page="../components/tutorCard.jsp">
                                    <jsp:param name="image" value="${tutor.image}"/>
                                    <jsp:param name="name" value="${tutor.name}"/>
                                    <jsp:param name="uid" value="${tutor.userId}"/>
                                    <jsp:param name="rate" value="${tutor.rate}"/>
                                    <jsp:param name="description" value="${tutor.description}"/>
                                    <jsp:param name="maxPrice" value="${tutor.maxPrice}"/>
                                    <jsp:param name="minPrice" value="${tutor.minPrice}"/>
                                </jsp:include>
                        </div>
                    </c:forEach>
            </div>
        </div>
    </div>
</div>
<nav aria-label="Page navigation example">
    <ul class="pagination justify-content-center">
        <li class="page-item disabled">
            <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Previous</a>
        </li>
        <li class="page-item"><a class="page-link" href="#">1</a></li>
        <li class="page-item"><a class="page-link" href="#">2</a></li>
        <li class="page-item"><a class="page-link" href="#">3</a></li>
        <li class="page-item">
            <a class="page-link" href="#">Next</a>
        </li>
    </ul>
</nav>


<script type="text/javascript" src="/resources/js/script.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"
        integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js"
        integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/"
        crossorigin="anonymous"></script>
</body>
</html>