<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

            <!-- Latest compiled JavaScript -->
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
            <title>Document</title>
        </head>

        <body>
            <div class="container mt-5">
                <div class="row">
                    <div class="col-md-12 col-12 mx-auto">
                        <div class="d-flex justify-content-between">
                            <h3>Table Users</h3>
                            <a class="btn btn-primary" style="font-size: 20px;" href="/admin/user/create"> Create
                                new User</a>
                        </div>
                        <hr />
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Email</th>
                                    <th>FullName</th>
                                    <th>Address</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="user" items="${userList}">
                                    <tr>
                                        <td>${user.id}</td>
                                        <td>${user.email}</td>
                                        <td>${user.fullName}</td>
                                        <td>${user.address}</td>
                                        <td>
                                            <a href="user/${user.id}" class="btn btn-info">View</a>
                                            <a href="user/update/${user.id}" class="btn btn-warning">Update</a>
                                            <a href="user/delete/${user.id}" class="btn btn-danger">Delete</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

        </body>

        </html>