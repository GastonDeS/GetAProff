<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <div>
        <div>
            <form method="post" action="${pageContext.request.contextPath}/uploadFile" enctype="multipart/form-data">
                <table>
                    <tr>
                        <td><label>Select a file to upload</label></td>
                        <td><input type="file" width="200px" height="200px" accept="image" name="file" /></td>
                    </tr>
                    <tr>
                        <td><input type="submit" value="Submit" /></td>
                    </tr>
                </table>
            </form>
        </div>

    </div>
</body>
</html>
