<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
  <title><spring:message code="email.sent.title"/></title>
  <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.png"/>" type="image/x-icon">
  <link rel="stylesheet"  type="text/css" href="/resources/styles/main.css"/>
  <script type="text/javascript" src="<c:url value="/resources/js/script.js"/>"></script>
</head>

<body>
<jsp:include page="../components/navbar.jsp">
  <jsp:param name="isMainPage" value="${true}"/>
</jsp:include>
<div class="page-container">
  <div class="form-container">
    <h1 class="mt-5"><spring:message code="subject.request.sent.title"/></h1>
    <p class="centered-horizontal"><spring:message code ="subject.request.sent.text"/></p>
    <a class="btn btn-custom bd-highlight centered" href="/profile/${uid}">
      <spring:message code="subject.request.sent.back.btn"/>
    </a>
  </div>
</div>
<jsp:include page="../components/footer.jsp">
  <jsp:param name="" value=""/>
</jsp:include>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js" integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js" integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/" crossorigin="anonymous"></script>
</body>
</html>