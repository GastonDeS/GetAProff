<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:message code="subjects.table.subject" var="tableSubject"/>
<spring:message code="subjects.table.price" var="tablePrice"/>
<spring:message code="subjects.table.level" var="tableLevel"/>
<spring:message code="subjects.table.hour" var="tableHour"/>

<div class="section-info">
    <table>
        <tr class="subjects-row" style="width: 100%">
            <td class="row-title" style="width: 55%">${tableSubject}</td>
            <td class="row-title" style="width: 15%">${tablePrice}</td>
            <td class="row-title" style="width: 30%">${tableLevel}</td>
        </tr>
<%--        <c:forEach begin="0" end="${requestScope}" varStatus="loop">--%>
<%--            <c:set value="name${loop.index}" var="name"/>--%>
<%--            <c:set value="price${loop.index}" var="price"/>--%>
<%--            <c:set value="level${loop.index}" var="level"/>--%>
<%--            <tr class="subjects-row" style="width: 100%">--%>
<%--                <td class="row-info" style="width: 55%"><c:out value="${param.name}"/></td>--%>
<%--                <td class="row-info" style="width: 15%">$<c:out value="${param.price}"/>/${tableHour}</td>--%>
<%--                <td class="row-info" style="width: 30%"><spring:message code="subjects.form.level.${param.level}"/></td>--%>
<%--            </tr>--%>
<%--        </c:forEach>--%>
    </table>
    <a href="${pageContext.request.contextPath}/subjectsForm" class="btn btn-custom">
        <spring:message code="profile.btn.edit.subjects"/>
    </a>
</div>
