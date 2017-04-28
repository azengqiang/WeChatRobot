<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>素材管理</title>
  <script type="text/javascript" src="../../resources/bootstrap-3.3.7/js/jquery-3.1.1.min.js"></script>
  <link type="text/css" href="../../resources/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet"/>
  <script type="text/javascript" src="../../resources/bootstrap-3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div style="margin-left: 20px;">

      <ol class="breadcrumb">
          <li><a href="#">主菜单</a></li>
          <li><a href="#">素材管理</a></li>
          <li class="active">素材管理</li>
      </ol>
      <ul id="myTab" class="nav nav-tabs">
          <li class="active">
            <a href="#home" data-toggle="tab">
              图文消息
            </a>
          </li>
      </ul>
      <div id="myTabContent" class="tab-content">
            <div class="tab-pane fade in active" id="home">
                <form method="post" action="fileUploadLocal" enctype="multipart/form-data">
                    <div>
                      <input type="file" multiple="multiple" name="file" accept="image/bmp,image/png,image/jpeg,image/jpg,image/gif">
                      <input type="submit" value="上传" name="submit" />

                    </div>
              </form>
          </div>
      </div>
</div>
<script type="text/javascript">
    var getMaterialCount =  function (){
        $.ajax({
            type: "POST",
            url: "${base.contextPath}/admin/getMaterialCount",
            dataType: "json",
            contentType: 'application/json',
            success: function (data) {
                debugger;
                if (data["success"] == true) {

                } else {

                }
            }
        });
    }
</script>
</body>
</html>