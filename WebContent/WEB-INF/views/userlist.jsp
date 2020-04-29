<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<body style="background-color: rgb(52,58,64);">

<div class="container-fluid">
<div class="row" style="margin-top: 4em;">
<div class="col-12 col-md-10">

<table class="table">
  <thead class="thead text-center" style="background-color: #DCDCDC;">
    <tr>
      <th scope="col">Matricola</th>
      <th scope="col">Nome</th>
      <th scope="col">Cognome</th>
      <th scope="col">Data di nascita</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach items="${user}" var="u">
    <tr class="text-center" style="color: white;">
      <td>${u.username}</td>
      <td>${u.profile.nome}</td>
      <td>${u.profile.cognome}</td>
      <td>${u.profile.dataNascita}</td>
    </tr>
    </c:forEach>
  </tbody>
</table>
</div>
</div>
</div>
</body>



