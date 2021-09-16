<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 <nav class="navbar navbar-expand-sm navbar-custom">
            <div class="container">
                <a href="${pageContext.request.contextPath}/" class="navbar-brand mb-0 h1">
                    <img src="<c:url value="/resources/images/logo_green.png"/>" alt="logo" class="d-inline-block align-top">
                </a>
                <c:if test="${param.isMainPage}">
                    <div class="navbarNav">
                        <a href="${pageContext.request.contextPath}/login" class="btn nav-link active btn-custom">
                            <spring:message code="nav.button.login"/>
                        </a>
                        <a href="${pageContext.request.contextPath}/register" class="btn nav-link active btn-custom">
                            <spring:message code="nav.button.register"/>
                        </a>
                    </div>
                </c:if>
            </div>
 </nav>