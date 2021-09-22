<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 <nav class="navbar navbar-expand-sm navbar-custom">
            <div class="container">
                <a href="${pageContext.request.contextPath}/" class="navbar-brand mb-0 h1">
                    <img src="<c:url value="/resources/images/logo_green.png"/>" alt="logo" class="d-inline-block align-top">
                </a>
                <c:if test="${param.isMainPage}">
                    <div class="navbarNav">
                        <sec:authorize access="!isAuthenticated()">
                            <a href="/login" class="btn nav-link active btn-custom">
                                <spring:message code="nav.button.login"/>
                            </a>
                            <a href="/register" class="btn nav-link active btn-custom">
                                <spring:message code="nav.button.register"/>
                            </a>
                        </sec:authorize>
                        <sec:authorize access="isAuthenticated()">
                            <a href="/profile" class="btn nav-link active btn-custom">
                                <spring:message code="nav.button.profile"/>
                            </a>
                            <a href="/logout" class="btn nav-link active btn-custom">
                                <spring:message code="nav.button.logout"/>
                            </a>
                        </sec:authorize>
                    </div>
                </c:if>
            </div>
 </nav>