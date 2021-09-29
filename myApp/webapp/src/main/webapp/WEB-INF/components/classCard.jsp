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
                            Clase cancelada
                        </c:when>
                        <c:otherwise>
                            Clase rechazada
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:when>
            <c:otherwise>
                <div class="card-header bg-success text-white">
                    Clase Terminada
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>
    <div class="card-body">
        <h5 class="card-title">Clase de <c:out value="${param.subjectName}"/></h5>
        <c:choose>
            <c:when test="${param.teacherName != null}">
                <p class="card-text">Profesor: <c:out value="${param.teacherName}"/></p>
            </c:when>
            <c:otherwise>
                <p class="card-text">Alumno <c:out value="${param.studentName}"/></p>
            </c:otherwise>
        </c:choose>
        <p class="card-text">Precio: $<c:out value="${param.price}"/>/hora</p>
        <p class="card-text">Nivel: <spring:message code="subjects.form.level.${param.level}"/></p>

        <c:if test="${param.finished == null}">
            <c:choose>
                <c:when test="${param.request != null}">
                    <c:if test="${param.request !=''}">
                        <p class="card-text">Mensaje del alumno: ${param.request}</p>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <c:if test="${param.reply !=''}">
                        <p class="card-text">Mensaje del profesor: ${param.reply}</p>
                    </c:if>
                </c:otherwise>
            </c:choose>

            <c:choose>
                <c:when test="${param.teacherName == null}">
                    <c:choose>
                        <c:when test="${param.active == null}">
                            <a href="#" class="btn btn-warning">Rechazar</a>
                            <a href="${pageContext.request.contextPath}/accept/${param.cid}" class="btn btn-custom">Aceptar</a>
                        </c:when>
                        <c:otherwise>
                            <a href="#" class="btn btn-warning">Cancelar</a>
                            <a href="#" class="btn btn-custom">Terminar</a>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <a href="#" class="btn btn-warning">Cancelar</a>
                </c:otherwise>
            </c:choose>
        </c:if>

    </div>
</div>
