<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set value="0" var="statusPending"/>
<c:set value="1" var="statusAccepted"/>
<c:set value="2" var="statusFinished"/>
<c:set value="3" var="statusCancelStudent"/>
<c:set value="4" var="statusCancelTeacher"/>
<c:set value="5" var="statusRejected"/>
<c:set value="6" var="statusRated"/>

<div class="card class-card">
    <c:choose>

        <c:when test="${param.lectureStatus >= statusFinished}">
            <c:choose>
                <c:when test="${param.lectureStatus > statusFinished && param.lectureStatus < statusRated}">
                    <div class="card-header bg-danger text-white">
                        <c:choose>
                            <c:when test="${param.lectureStatus == statusCancelStudent || param.lectureStatus == statusCancelTeacher}">
                                <spring:message code="class.card.canceled"/>
                            </c:when>
                            <c:otherwise>
                                <spring:message code="class.card.declined"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="card-header bg-light text-dark">
                        <c:choose>
                            <c:when test="${param.lectureStatus == statusFinished}">
                                <spring:message code="class.card.finished"/>
                            </c:when>
                            <c:otherwise>
                                <spring:message code="class.card.rated"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${param.lectureStatus == statusPending}">
                    <div class="card-header text-white" style="background-color: darkorange;">
                        <spring:message code="class.card.pending"/>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="card-header bg-success text-white">
                        <spring:message code="class.card.accepted"/>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>
    <div class="card-body">
        <h5 class="card-title">
            <spring:message code="class.card.subject.intro"/> <c:out value="${param.subjectName}"/>
        </h5>
        <c:choose>
            <c:when test="${!param.isOffered}">
                <p class="card-text">
                    <spring:message code="class.card.teacher.intro"/> <c:out value="${param.teacherName}"/>
                </p>
            </c:when>
            <c:otherwise>
                <p class="card-text">
                    <spring:message code="class.card.student.intro"/> <c:out value="${param.studentName}"/>
                </p>
            </c:otherwise>
        </c:choose>
        <p class="card-text">
            <spring:message code="class.card.price.intro"/> $<c:out value="${param.price}"/>/<spring:message code="class.card.price.outro"/>
        </p>
        <p class="card-text"><spring:message code="class.card.level.intro"/>
            <spring:message code="subjects.form.level.${param.level}"/>
        </p>
        <c:choose>
            <c:when test="${param.isOffered}">
                <c:choose>
                    <c:when test="${param.lectureStatus == statusPending }">
                        <div class="class-card-active-btn-holder">
                            <div class="class-cancel-btn">
                                <jsp:include page="../components/rejectOrCancelModal.jsp">
                                    <jsp:param name="type" value="decline"/>
                                    <jsp:param name="urlConfirm" value="/myClasses/0/${param.classId}/REJECTED"/>
                                </jsp:include>
                                <form action="<c:url value="/myClasses/1/${param.cid}/ACCEPTED"/>" method="post"
                                      class="class-card-btn-holder">
                                    <input type="submit" class="btn btn-custom"
                                           value="<spring:message code="class.card.accept"/>">
                                </form>
                            </div>
                            <a style="position:relative;" href="${pageContext.request.contextPath}/classroom/${param.cid}"
                               class="btn btn-custom"><spring:message code="class.card.enter"/>
                                <c:if test="${param.notifications > 0}">
                                    <span class="notifications-badge">
                                            ${param.notifications}
                                    </span>
                                </c:if>
                            </a>
                        </div>
                    </c:when>
                    <c:when test="${param.lectureStatus == statusAccepted}">
                        <div class="class-card-active-btn-holder">
                            <div class="class-cancel-btn">
                                <jsp:include page="../components/rejectOrCancelModal.jsp">
                                    <jsp:param name="type" value="cancel"/>
                                    <jsp:param name="urlConfirm" value="/myClasses/0/${param.cid}/CANCELEDT"/>
                                </jsp:include>
                                <jsp:include page="../components/rejectOrCancelModal.jsp">
                                    <jsp:param name="type" value="finish"/>
                                    <jsp:param name="urlConfirm" value="/myClasses/1/${param.cid}/FINISHED"/>
                                </jsp:include>
                            </div>
                            <a style="position:relative;" href="${pageContext.request.contextPath}/classroom/${param.cid}"
                               class="btn btn-custom"><spring:message code="class.card.enter"/>
                                <c:if test="${param.notifications > 0}">
                                    <span class="notifications-badge">
                                            ${param.notifications}
                                    </span>
                                </c:if>
                            </a>
                        </div>
                    </c:when>
                </c:choose>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${param.lectureStatus == statusPending}">
                        <div class="class-card-active-btn-holder">
                            <jsp:include page="../components/rejectOrCancelModal.jsp">
                                <jsp:param name="type" value="cancel"/>
                                <jsp:param name="urlConfirm" value="/myClasses/0/${param.cid}/CANCELEDS"/>
                            </jsp:include>
                            <a style="position: relative" href="${pageContext.request.contextPath}/classroom/${param.cid}"
                               class="btn btn-custom"><spring:message code="class.card.enter"/>
                                <c:if test="${param.notifications > 0}">
                                    <span class="notifications-badge">
                                            ${param.notifications}
                                    </span>
                                </c:if>
                            </a>
                        </div>
                    </c:when>
                    <c:when test="${param.lectureStatus == statusAccepted}">
                        <div class="class-card-active-btn-holder">
                            <jsp:include page="../components/rejectOrCancelModal.jsp">
                                <jsp:param name="type" value="cancel"/>
                                <jsp:param name="urlConfirm" value="/myClasses/0/${param.cid}/CANCELEDS"/>
                            </jsp:include>
                            <a style="position:relative;" href="${pageContext.request.contextPath}/classroom/${param.cid}"
                               class="btn btn-custom"><spring:message code="class.card.enter"/>
                                <c:if test="${param.notifications > 0}">
                                    <span class="notifications-badge">
                                            ${param.notifications}
                                    </span>
                                </c:if>
                            </a>
                        </div>
                    </c:when>
                    <c:when test="${param.lectureStatus == statusFinished}">
                        <div class="class-card-active-btn-holder">
                            <a href="${pageContext.request.contextPath}/rate/${param.cid}"
                               class="btn btn-custom"><spring:message code="class.card.rate"/></a>
                            <a style="position: relative" href="${pageContext.request.contextPath}/classroom/${param.cid}"
                                   class="btn btn-custom"><spring:message code="class.card.enter"/>
                                <c:if test="${param.notifications > 0}">
                                    <span class="notifications-badge">
                                        ${param.notifications}
                                    </span>
                                </c:if>
                            </a>
                        </div>
                    </c:when>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </div>
</div>