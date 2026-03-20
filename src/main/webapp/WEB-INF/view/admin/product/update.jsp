<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="" />
                <meta name="author" content="" />
                <title>Dashboard - Product</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>

                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

                <script>
                    $(document).ready(() => {
                        const avatarFile = $("#avatarFile");
                        avatarFile.change(function (e) {
                            const imgURL = URL.createObjectURL(e.target.files[0]);
                            $("#avatarPreview").attr("src", imgURL);
                            $("#avatarPreview").css({ "display": "block" });
                        });
                    });
                </script>

            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />
                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4"></h1>
                            </div>

                            <body>
                                <div class="container">
                                    <div class="row">
                                        <div class="col-md-6 col-12 mx-auto">
                                            <h3>Update product</h3>
                                            <hr />
                                            <form:form method="post" action="/admin/product/update"
                                                modelAttribute="updateProduct" class="row"
                                                enctype="multipart/form-data">
                                                <div class="mb-3" style="display: none;">
                                                    <label class="form-label">Id:</label>
                                                    <form:input type="text" class="form-control" path="id" />
                                                </div>
                                                <!-- Name -->
                                                <div class="mb-3 col-12 col-md-6">
                                                    <label class="form-label">Name:</label>
                                                    <form:input path="name" cssClass="form-control"
                                                        cssErrorClass="form-control is-invalid" />
                                                    <form:errors path="name" cssClass="invalid-feedback" />
                                                </div>

                                                <!-- Price -->
                                                <div class="mb-3 col-12 col-md-6">
                                                    <label class="form-label">Price:</label>
                                                    <form:input path="price" type="number" cssClass="form-control"
                                                        cssErrorClass="form-control is-invalid" />
                                                    <form:errors path="price" cssClass="invalid-feedback" />
                                                </div>

                                                <!-- Detail -->
                                                <div class="mb-3 col-12">
                                                    <label class="form-label">Detail description:</label>
                                                    <form:textarea path="detailDesc" rows="3" cssClass="form-control"
                                                        cssErrorClass="form-control is-invalid" />
                                                    <form:errors path="detailDesc" cssClass="invalid-feedback" />
                                                </div>

                                                <!-- Short -->
                                                <div class="mb-3 col-12 col-md-6">
                                                    <label class="form-label">Short description:</label>
                                                    <form:input path="shortDesc" cssClass="form-control"
                                                        cssErrorClass="form-control is-invalid" />
                                                    <form:errors path="shortDesc" cssClass="invalid-feedback" />
                                                </div>

                                                <!-- Quantity -->
                                                <div class="mb-3 col-12 col-md-6">
                                                    <label class="form-label">Quantity:</label>
                                                    <form:input path="quantity" type="number" cssClass="form-control"
                                                        cssErrorClass="form-control is-invalid" />
                                                    <form:errors path="quantity" cssClass="invalid-feedback" />
                                                </div>

                                                <!-- Factory -->
                                                <div class="mb-3 col-12 col-md-6">
                                                    <label class="form-label">Factory:</label>
                                                    <form:select path="factory" cssClass="form-select"
                                                        cssErrorClass="form-select is-invalid">
                                                        <form:option value="Apple">Apple</form:option>
                                                        <form:option value="Dell">Dell</form:option>
                                                        <form:option value="Asus">Asus</form:option>
                                                        <form:option value="Nitro">Nitro</form:option>
                                                        <form:option value="LG">LG</form:option>
                                                    </form:select>
                                                    <form:errors path="factory" cssClass="invalid-feedback" />
                                                </div>

                                                <!-- Target -->
                                                <div class="mb-3 col-12 col-md-6">
                                                    <label class="form-label">Target:</label>
                                                    <form:select path="target" cssClass="form-select"
                                                        cssErrorClass="form-select is-invalid">
                                                        <form:option value="Gaming">Gaming</form:option>
                                                        <form:option value="Designer">Designer</form:option>
                                                        <form:option value="Office">Office</form:option>
                                                        <form:option value="Study">Study</form:option>
                                                    </form:select>
                                                    <form:errors path="target" cssClass="invalid-feedback" />
                                                </div>

                                                <!-- Avatar -->
                                                <div class="mb-3 col-12 col-md-6">
                                                    <label class="form-label">Avatar:</label>
                                                    <input type="file" class="form-control" accept=".png, .jpg, .jpeg"
                                                        id="avatarFile" name="fileUpload" />
                                                </div>

                                                <div class="col-12 mb-3">
                                                    <img style="max-height: 250px; display: none;" id="avatarPreview">
                                                </div>

                                                <div class="col-12 mb-3">
                                                    <button type="submit" class="btn btn-primary">Update</button>
                                                </div>
                                            </form:form>
                                        </div>
                                    </div>
                                </div>

                            </body>
                        </main>
                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="../js/scripts.js"></script>
            </body>

            </html>