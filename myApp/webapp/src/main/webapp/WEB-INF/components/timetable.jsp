<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <link rel="stylesheet"  type="text/css" href="<c:url value="${page.Context.request.contextPath}/resources/styles/main.css"/>"/>
    </head>
    <body class="timetable">
        <div class="container">
            <div class="timetable-img text-center">
                <img src="img/content/timetable.png" alt="">
            </div>
            <div class="table-responsive">
                <table class="table table-bordered text-center">
                    <thead>
                        <tr class="bg-light-gray">
                            <th class="text-uppercase">Monday</th>
                            <th class="text-uppercase">Tuesday</th>
                            <th class="text-uppercase">Wednesday</th>
                            <th class="text-uppercase">Thursday</th>
                            <th class="text-uppercase">Friday</th>
                            <th class="text-uppercase">Saturday</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="t" begin="8" end="21">
                            <tr>
                                <c:forEach var="d" begin="1" end="6">
                                    <td id="${d}${t}" onclick="setColor('${d}${t}')">
                                        <p class="time-range"><c:out value="${t}:00 - ${t+1}:00"/></p>
                                    </td>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <script>
            function setColor(btn){
                let color = document.getElementById(btn).style.backgroundColor;
                let darkblueElem = document.createElement("p");
                darkblueElem.style.backgroundColor = "#85BDBF";
                if (color === darkblueElem.style.backgroundColor ){
                    document.getElementById(btn).style.backgroundColor = "transparent"
                }
                else{
                    document.getElementById(btn).style.backgroundColor = "#85BDBF"
                }
            }
        </script>
    </body>
</html>