<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div>
    <!-- Button trigger modal -->
    <div class="class-card-btn-holder">
        <button type="button" class="btn btn-custom cancel-btn" data-bs-toggle="modal" data-bs-target="#modal${param.type}">
            <spring:message code="class.card.${param.type}"/>
        </button>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="modal${param.type}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel"><spring:message code="class.modal.title.${param.type}"/></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <spring:message code="class.modal.text.${param.type}"/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-custom btn-secondary" data-bs-dismiss="modal">
                        <spring:message code="close"/>
                    </button>
                    <form action="<c:url value='${param.urlConfirm}'/>" method="post" class="class-card-btn-holder">
                        <input type="submit" class="btn btn-custom cancel-btn" value="<spring:message code="class.card.${param.type}"/>">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
