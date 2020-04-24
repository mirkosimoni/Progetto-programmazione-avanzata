<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<body style="background-color: rgb(52,58,64);">

<div class="container-fluid">

<div class="row" style="margin-top: 4em;">
<div class="col-12 col-md-10">

<c:if test="${not empty errorMessageData}">
	<div class="alert alert-danger" role="alert">${errorMessageData}</div>
</c:if>
<table class="table">
  <thead class="thead text-center" style="background-color: #DCDCDC;">
    <tr>
      <th scope="col">Quota</th>
      <th scope="col">Nome</th>
      <th scope="col">Numero posti</th>
      <th scope="col">Prese</th>
      <th scope="col">Modifica</th>
      <th scope="col">Elimina</th>
    </tr>
  </thead>

  <tbody>
  <c:forEach items="${aula}" var="a">
    <tr class="text-center" style="color: white;">
      <td>${a.quota}</td>
      <td>${a.nome}</td>
      <td>${a.numeroPosti}</td>
      <td>${a.presentiPrese}</td>
      <td><a href="<c:url value="/aula/${a.id}/edit"/>"><i class="far fa-hand-paper" style="color: rgb(218,56,73);"> </i></a></td>
      <td><a href="<c:url value="/aula/delete/${a.id}"/>"><i class="fas fa-trash" style="color: rgb(218,56,73);"></i></a></td>
    </tr>
    </c:forEach>
  </tbody>
</table>
</div>

<div class="col-12 col-md-2" style="text-align: center; margin-bottom: 1em;">
  <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#modalCreate" style="width: 6em; margin-bottom: 1em;">
    <i class="fas fa-plus-circle"></i> Crea
  </button>
  
  <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#modalSearch" style="width: 6em; margin-bottom: 1em;">
    <i class="fas fa-search"></i> Cerca
  </button>
</div>

</div>
</div>
<div class="row">
    <div class="col-md-10 col-0"></div>
    <div class="col-12 col-md-2 text-center">
      <div class="rounded-circle text-center" style="border-color: white; width: 3em; height: 3em; border-style: solid; display: inline-block; vertical-align: middle;">
        <div style="display: inline-block; vertical-align: middle; padding-top: 0.65em;">
          <a href="#" data-attribute="back-to-top" class="back-to-top">
            <i class="fas fa-arrow-up back-to-top"></i>
          </a>
        </div>
      </div>
    </div>
  </div>

</body>


<!-- Modal Create--------------------------------------------------------------------------------------->




<div class="modal fade" id="modalCreate" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content" style= "background-color: #696969; color: white;">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalCenterTitle">Creazione nuova aula</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <c:url value="/aula/create" var="action_url" />

            <form name='create' action="${action_url}" method='POST'>    

              <div class="form-group">
                <label for="exampleInputEmail1">Quota</label>
                <input type="Text" class="form-control" name="quota">
              </div>
              <div class="form-group">
                <label for="exampleInputEmail1">Nome</label>
                <input type="Text" class="form-control" name="nome">
              </div>
              <div class="form-group">
                <label for="exampleInputEmail1">Numero posti</label>
                <input type="Text" class="form-control" name="numPosti">
              </div>
              <div class="form-check form-check-inline">
  				<input class="form-check-input" type="checkbox" name="prese">
 				<label class="form-check-label" for="inlineCheckbox1">Presenza prese</label>
			  </div>
              <div>
              <button type="submit" class="btn btn-danger" role="button" aria-pressed="true"><i class="fas fa-plus-circle"></i> Crea </button>
           	  </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  




<!-- Modal ----------------------------------------------------------------------------------------------------->
<div class="modal fade" id="modalSearch" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content" style= "background-color: #696969; color: white;">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalCenterTitle">Ricerca aule libere</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <c:url value="/aula/search" var="action_url" />

            <form name='login' action="${action_url}" method='POST'>    

              <div class="form-group">
                <label for="exampleInputEmail1">Giorno</label>
                <input type="Date" class="form-control" name="giorno">
              </div>
              <div class="input-group mb-3">
                <div class="input-group-prepend">
                  <label class="input-group-text" for="inputGroupSelect01">Ora Inizio</label>
                </div>
                <select class="custom-select" id="inputGroupSelect01" name="oraInizio">
                  <option selected>Scegli</option>
                  <option value="08:30">08:30</option>
                  <option value="09:30">09:30</option>
                  <option value="10:30">10:30</option>
                  <option value="11:30">11:30</option>
                  <option value="12:30">12:30</option>
                  <option value="13:30">13:30</option>
                  <option value="14:30">14:30</option>
                  <option value="15:30">15:30</option>
                  <option value="16:30">16:30</option>
                  <option value="17:30">17:30</option>
                  <option value="18:30">18:30</option>
                </select>
              </div>
              <div class="input-group mb-3">
                <div class="input-group-prepend">
                  <label class="input-group-text" for="inputGroupSelect02">Ora Fine</label>
                </div>
                <select class="custom-select" id="inputGroupSelect02" name="oraFine">
                  <option selected>Scegli</option>
                  <option value="08:30">08:30</option>
                  <option value="09:30">09:30</option>
                  <option value="10:30">10:30</option>
                  <option value="11:30">11:30</option>
                  <option value="12:30">12:30</option>
                  <option value="13:30">13:30</option>
                  <option value="14:30">14:30</option>
                  <option value="15:30">15:30</option>
                  <option value="16:30">16:30</option>
                  <option value="17:30">17:30</option>
                  <option value="18:30">18:30</option>
                </select>
              </div>
              <div class="form-group">
                <label for="exampleInputEmail1">Quota</label>
				<select class="custom-select" id="inputGroupSelect03" name="quota">
                  <option selected>Scegli</option>
                  <c:forEach items="${quote}" var="q">
                  <option value="${q}">${q}</option>
                  </c:forEach>
                </select>
              </div>
              <div class="form-group">
                <label for="exampleInputEmail1">Nome</label>
				<select class="custom-select" id="inputGroupSelect03" name="nomi">
                  <option selected>Scegli</option>
                  <c:forEach items="${nomi}" var="n">
                  <option value="${n}">${n}</option>
                  </c:forEach>
                </select>
              </div>
              <div class="form-group">
                <label for="exampleInputEmail1">Numero posti minimi</label>
                <input type="Text" class="form-control" name="numPosti">
              </div>
              <div class="form-check form-check-inline">
  				<input class="form-check-input" type="checkbox" name="prese">
 				<label class="form-check-label" for="inlineCheckbox1">Presenza prese</label>
			  </div>
              <div>
              <button type="submit" class="btn btn-danger" role="button" aria-pressed="true"><i class="fas fa-search"></i> Cerca </button>
           	  </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  


  





