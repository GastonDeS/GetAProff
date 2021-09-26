<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="section-info">
    <p><c:out value="${param.schedule}"/></p>
    <a href="${pageContext.request.contextPath}/timeForm" class="btn btn-custom">
        <spring:message code="profile.btn.edit.schedule"/>
    </a>
</div>

