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
                            <a href="${pageContext.request.contextPath}/login" class="btn nav-link active btn-custom">
                                <spring:message code="nav.button.login"/>
                            </a>
                            <a href="${pageContext.request.contextPath}/register" class="btn nav-link active btn-custom">
                                <spring:message code="nav.button.register"/>
                            </a>
                        </sec:authorize>
                        <sec:authorize access="isAuthenticated()">
                            <a href="${pageContext.request.contextPath}/myClasses" class="btn nav-link active btn-custom">
                                <spring:message code="nav.button.classes"/>
                            </a>
                            <a href="${pageContext.request.contextPath}/profile/${param.uid}/subjects" class="btn nav-link active btn-custom">
                                <spring:message code="nav.button.profile"/>
                            </a>
                            <a href="${pageContext.request.contextPath}/logout" class="btn nav-link active btn-custom">
                                <spring:message code="nav.button.logout"/>
                            </a>
                        </sec:authorize>
                    </div>
                </c:if>
            </div>
 </nav>