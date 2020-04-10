<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
<script src="https://kit.fontawesome.com/d7d273ea99.js" crossorigin="anonymous"></script>

<sec:authorize access="isAuthenticated()" var="isAuth" />

<nav class="navbar navbar-dark bg-dark navbar-expand-lg fixed-top">
  <a class="navbar-brand" href="#">Navbar</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">Link</a>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Dropdown
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="#">Action</a>
          <a class="dropdown-item" href="#">Another action</a>
          <div class="dropdown-divider"></div>
          <a class="dropdown-item" href="#">Something else here</a>
        </div>
      </li>
      <li class="nav-item active">
        <a class="nav-link" href="<c:url value="/login" />">Login <span class="sr-only">(current)</span></a>
      </li>
      <c:if test="${isAuth}">
      <li class="nav-item" style="float: right;">
        <a class="nav-link" href="<c:url value="/logout" />" tabindex="-1" aria-disabled="true"><i class="fas fa-sign-out-alt"></i></a>
      </li>
      </c:if>
    </ul>
  </div>
</nav>




<div class="row">
	
	<img src="<c:url value="/media/home.jpg"/>" class="img-fluid" alt="home" style="width: 100%; height: 50%">
	
	
</div>

	<div class="row">
  <div class="col-3">
    <div class="card">
      <div class="card-body">
        <h5 class="card-title">Visualizza prenotazioni</h5>
        <p class="card-text">Visualizza tutte le prenotazioni presenti</p>
        <a href="prenotations/list" class="btn btn-primary">Visualizza</a>
      </div>
    </div>
  </div>
  <div class="col-3">
    <div class="card">
      <div class="card-body">
        <h5 class="card-title">Cerca aula</h5>
        <p class="card-text">Cerca una determinata aula della facoltà</p>
        <a href="#" class="btn btn-primary">Cerca</a>
      </div>
    </div>
  </div>
  <div class="col-3">
    <div class="card">
      <div class="card-body">
        <h5 class="card-title">Profilo</h5>
        <p class="card-text">Visualizza il tuo profilo utenteà</p>
        <a href="#" class="btn btn-primary">Visualizza</a>
      </div>
    </div>
  </div>
     <div class="col-3">
    <div class="card">
      <div class="card-body">
        <h5 class="card-title">Gestione aule</h5>
        <p class="card-text">Aggiungi o cancella un'aula</p>
        <a href="#" class="btn btn-primary">Gestisci</a>
      </div>
    </div>
  </div>
</div>
