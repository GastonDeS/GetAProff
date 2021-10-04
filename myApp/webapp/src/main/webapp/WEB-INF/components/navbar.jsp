<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<nav class="navbar navbar-expand-sm nav-custom">
     <div class="nav-main">
         <div class="nav-icon">
             <a href="${pageContext.request.contextPath}/" class="navbar-brand mb-0 h1">
                 <img src="<c:url value="/resources/images/logo_green.png"/>" alt="logo" class="d-inline-block align-top">
             </a>
         </div>
     <c:if test="${param.isMainPage}">
         <sec:authorize access="!isAuthenticated()">
             <div class="nav-btn-container">
                 <a href="${pageContext.request.contextPath}/login" class="nav-btn btn nav-link active btn-custom">
                     <spring:message code="nav.button.login"/>
                 </a>
                 <a href="${pageContext.request.contextPath}/register" class="nav-btn btn nav-link active btn-custom">
                     <spring:message code="nav.button.register"/>
                 </a>
             </div>
         </sec:authorize>
         <sec:authorize access="isAuthenticated()">
             <div class="nav-sections-container" id="navbarSupportedContent">
                 <ul class="nav-sections navbar-nav me-auto mb-2 mb-lg-0">
                     <c:forEach begin="1" end="3" varStatus="loop">
                         <c:if test="${loop.index == 1}">
                            <c:set var="url" value="${pageContext.request.contextPath}/"/>
                         </c:if>
                         <c:if test="${loop.index == 2}">
                            <c:set var="url" value="${pageContext.request.contextPath}/myClasses"/>
                         </c:if>
                         <c:if test="${loop.index == 3}">
                             <c:set var="url" value="${pageContext.request.contextPath}/favourites"/>
                         </c:if>
                         <li class="nav-item">
                            <c:if test="${param.section == loop.index}">
                                 <a class="nav-font nav-link active-section" href="${url}">
                                     <spring:message code="nav.button.${loop.index}"/>
                                 </a>
                            </c:if>
                            <c:if test="${param.section != loop.index}">
                                 <a class="nav-font nav-link" href="${url}">
                                     <spring:message code="nav.button.${loop.index}"/>
                                 </a>
                            </c:if>
                         </li>
                     </c:forEach>
                 </ul>
             </div>
             <div class="nav-drop">
                 <li class="nav-item dropdown">
                     <a class="nav-font nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                         <spring:message code="nav.button.account"/>
                     </a>
                     <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                         <li><a class="dropdown-item" href="${pageContext.request.contextPath}/profile/${param.uid}">
                             <spring:message code="nav.button.profile"/>
                         </a></li>
                         <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">
                             <spring:message code="nav.button.logout"/>
                         </a></li>
                     </ul>
                 </li>
             </div>
         </sec:authorize>
     </c:if>
     </div>
 </nav>