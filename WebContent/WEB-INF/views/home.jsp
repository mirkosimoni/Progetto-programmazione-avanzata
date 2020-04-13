<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
<body style= "background-color:rgb(52,58,64);">

<div class="row">
	<img src="<c:url value="/media/home.jpg"/>" class="img-fluid" alt="home" style="width: 100%;">
</div>

<div class="row" style="padding-top:0.5em; padding-left:1em; padding-right:1em;">
  <div class="col-12 col-sm-6 col-lg-3" style="margin-top:1em;">
    <div class="card" style="height: 100%">
      <div class="card-body">
        <h5 class="card-title">Visualizza prenotazioni</h5>
        <p class="card-text">Visualizza tutte le prenotazioni presenti</p>
        <a href="prenotations/list" class="btn btn-danger">Visualizza</a>
      </div>
    </div>
  </div>
  <div class="col-12 col-sm-6 col-lg-3" style="margin-top:1em;">
    <div class="card" style="height: 100%">
      <div class="card-body">
        <h5 class="card-title">Cerca aula</h5>
        <p class="card-text">Cerca una determinata aula della facoltà</p>
        <a href="aula/list" class="btn btn-danger">Cerca</a>
      </div>
    </div>
  </div>
  <div class="col-12 col-sm-6 col-lg-3" style="margin-top:1em;">
    <div class="card" style="height: 100%">
      <div class="card-body">
        <h5 class="card-title">Profilo</h5>
        <p class="card-text">Visualizza il tuo profilo utente</p>
        <a href="#" class="btn btn-danger">Visualizza</a>
      </div>
    </div>
  </div>
     <div class="col-12 col-sm-6 col-lg-3" style="margin-top:1em;">
    <div class="card" style="height: 100%">
      <div class="card-body">
        <h5 class="card-title">Gestione aule</h5>
        <p class="card-text">Aggiungi o cancella un'aula</p>
        <a href="#" class="btn btn-danger">Gestisci</a>
      </div>
    </div>
  </div>
</div>
</body>
