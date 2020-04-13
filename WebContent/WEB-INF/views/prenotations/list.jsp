<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<body style="background-color: rgb(52,58,64);">

<div class="row" style="margin-top: 4em;">
<div class="col-12 col-md-10">
<table class="table">
  <thead class="thead text-center" style="background-color: #696969;">
    <tr>
      <th scope="col">Utente</th>
      <th scope="col">Quota</th>
      <th scope="col">Aula</th>
      <th scope="col">Nome evento</th>
      <th scope="col">Ora inizio</th>
      <th scope="col">Ora fine</th>
      <th scope="col">Note</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach items="${prenotations}" var="p">
    <tr class="text-center" style="color: white;">
      <td>${p.user.username}</td>
      <td>${p.aula.quota}</td>
      <td>${p.aula.nome}</td>
      <td>${p.nomeEvento}</td>
      <td>${formatter.format(p.oraInizio.toDate())}</td>
      <td>${formatter.format(p.oraFine.toDate())}</td>
      <td>${p.note}</td>
    </tr>
    </c:forEach>
  </tbody>
</table>
</div>


<!-- Button trigger modal -->
<div class="col-12 col-md-2" style="text-align: center; margin-bottom: 2em;">
  <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#modalSearch">
    <i class="fas fa-search"></i> Cerca
  </button>
</div>

<!-- Modal -->
<div class="modal fade" id="modalSearch" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content" style= "background-color: #696969; color: white;">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalCenterTitle">Ricerca Prenotazione</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <c:url value="/prenotations/search" var="action_url" />

            <form name='login' action=${action_url} method='POST'>    

              <div class="form-group">
                <label for="exampleInputEmail1">Cognome Professore</label>
                <input type="text" class="form-control" name="prof_surname">
              </div>
              <div class="form-group">
                <label for="exampleInputEmail1">Nome Professore</label>
                <input type="text" class="form-control" name="prof_name">
              </div>
              <div class="form-group">
                <label for="exampleInputEmail1">Quota</label>
                <input type="text" class="form-control" name="quota">
              </div>
              <div class="form-group">
                <label for="exampleInputEmail1">Aula</label>
                <input type="text" class="form-control" name="aula">
              </div>
              <div class="form-group">
    			<label for="exampleInputEmail1">Giorno</label>
    			<input type="Date" class="form-control" name="data">
  			  </div>
              <div class="input-group mb-3">
                <div class="input-group-prepend">
                  <label class="input-group-text" for="inputGroupSelect01">Ora Inizio</label>
                </div>
                <select class="custom-select" id="inputGroupSelect01" name="ora_inizio">
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
                <select class="custom-select" id="inputGroupSelect02" name="ora_fine">
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
              <button type="submit" class="btn btn-danger" role="button" aria-pressed="true"><i class="fas fa-search"></i> Cerca </button>
            </form>
          </div>
        </div>
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



