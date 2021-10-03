<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 <nav class="navbar navbar-expand-sm nav-custom">
                <a href="${pageContext.request.contextPath}/" class="nav-icon navbar-brand mb-0 h1">
                    <img src="<c:url value="/resources/images/logo_green.png"/>" alt="logo" class="d-inline-block align-top">
                </a>
                <c:if test="${param.isMainPage}">
                    <div class="nav-main">
                        <sec:authorize access="!isAuthenticated()">
                            <a href="${pageContext.request.contextPath}/login" class="nav-btn btn nav-link active btn-custom">
                                <spring:message code="nav.button.login"/>
                            </a>
                            <a href="${pageContext.request.contextPath}/register" class="nav-btn btn nav-link active btn-custom">
                                <spring:message code="nav.button.register"/>
                            </a>
                        </sec:authorize>
                        <sec:authorize access="isAuthenticated()">
                            <div class="nav-sections">
                                <a href="${pageContext.request.contextPath}/myClasses" class="btn nav-link active btn-custom">
                                    <spring:message code="nav.button.classes"/>
                                </a>
                                <a href="#" class="btn nav-link active btn-custom">
                                    <spring:message code="nav.button.favorites"/>
                                </a>
                            </div>
                            <div class="nav-btn-container">
                                <a href="${pageContext.request.contextPath}/profile/${param.uid}" class="btn nav-link active btn-custom">
                                    <spring:message code="nav.button.profile"/>
                                </a>
                                <a href="${pageContext.request.contextPath}/logout" class="btn nav-link active btn-custom">
                                    <spring:message code="nav.button.logout"/>
                                </a>
                            </div>
                        </sec:authorize>
                    </div>
                </c:if>
 </nav>