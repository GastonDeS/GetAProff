<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="card w-100 mt-3">
    <c:if test="${param.finished != null}">
        <c:choose>
            <c:when test="${param.finished > 2}">
                <div class="card-header bg-danger text-white">
                    <c:choose>
                        <c:when test="${param.finished == 3}">
                            <spring:message code="class.card.canceled"/>
                        </c:when>
                        <c:otherwise>
                            <spring:message code="class.card.declined"/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:when>
            <c:otherwise>
                <div class="card-header bg-success text-white">
                    <spring:message code="class.card.finished"/>
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>
    <div class="card-body">
        <h5 class="card-title"><spring:message code="class.card.subject.intro"/> <c:out value="${param.subjectName}"/></h5>
        <c:choose>
            <c:when test="${param.teacherName != null}">
                <p class="card-text"><spring:message code="class.card.teacher.intro"/> <c:out value="${param.teacherName}"/></p>
            </c:when>
            <c:otherwise>
                <p class="card-text"><spring:message code="class.card.student.intro"/> <c:out value="${param.studentName}"/></p>
            </c:otherwise>
        </c:choose>
        <p class="card-text"><spring:message code="class.card.price.intro"/> $<c:out value="${param.price}"/>/<spring:message code="class.card.price.outro"/></p>
        <p class="card-text"><spring:message code="class.card.level.intro"/> <spring:message code="subjects.form.level.${param.level}"/></p>
        <c:if test="${param.finished == null}">
            <c:choose>
                <c:when test="${param.request != null}">
                    <c:if test="${param.request !=''}">
                        <c:choose>
                            <c:when test="${param.teacherName == null}">
                                <p class="card-text"><spring:message code="class.card.student.message"/> ${param.request}</p>
                            </c:when>
                            <c:otherwise>
                                <p class="card-text"><spring:message code="class.card.your.message"/> ${param.request}</p>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <c:if test="${param.reply !=''}">
                        <c:choose>
                            <c:when test="${param.teacherName == null}">
                                <p class="card-text"><spring:message code="class.card.your.message"/> ${param.reply}</p>
                            </c:when>
                            <c:otherwise>
                                <p class="card-text"><spring:message code="class.card.teacher.message"/> ${param.reply}</p>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </c:otherwise>
            </c:choose>

            <c:choose>
                <c:when test="${param.teacherName == null}">
                    <c:choose>
                        <c:when test="${param.active == null}">
                            <a href="#" class="btn btn-warning"><spring:message code="class.card.decline"/></a>
                            <a href="${pageContext.request.contextPath}/accept/${param.cid}" class="btn btn-custom"><spring:message code="class.card.accept"/></a>
                        </c:when>
                        <c:otherwise>
                            <a href="#" class="btn btn-warning"><spring:message code="class.card.cancel"/></a>
                            <a href="#" class="btn btn-custom"><spring:message code="class.card.finish"/></a>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <a href="#" class="btn btn-warning"><spring:message code="class.card.cancel"/></a>
                </c:otherwise>
            </c:choose>
        </c:if>

    </div>
</div>
