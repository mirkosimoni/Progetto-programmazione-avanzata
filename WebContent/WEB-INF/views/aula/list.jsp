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
      <th scope="col">Quota</th>
      <th scope="col">Nome</th>
      <th scope="col">Numero posti</th>
      <th scope="col">Prese</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach items="${aula}" var="a">
    <tr class="text-center" style="color: white;">
      <td>${a.quota}</td>
      <td>${a.nome}</td>
      <td>${a.numeroPosti}</td>
      <td>${a.presentiPrese}</td>

    </tr>
    </c:forEach>
  </tbody>
</table>
</div>

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
        <h5 class="modal-title" id="exampleModalCenterTitle">Ricerca aule libere</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <c:url value="/aula/search" var="action_url" />

            <form name='login' action=${action_url} method='POST'>    

              <div class="form-group">
                <label for="exampleInputEmail1">Giorno</label>
                <input type="Date" class="form-control" name="giorno">
              </div>
              <div class="input-group mb-3">
                <div class="input-group-prepend">
                  <label class="input-group-text" for="inputGroupSelect01">Ora Inizio</label>
                </div>
                <select class="custom-select" id="inputGroupSelect01" name="ora_inizio">
                  <option selected>Scegli</option>
                  <option value="1">08:30</option>
                  <option value="2">09:30</option>
                  <option value="3">10:30</option>
                  <option value="3">11:30</option>
                  <option value="3">12:30</option>
                  <option value="3">13:30</option>
                  <option value="3">14:30</option>
                  <option value="3">15:30</option>
                  <option value="3">16:30</option>
                  <option value="3">17:30</option>
                  <option value="3">18:30</option>
                </select>
              </div>
              <div class="input-group mb-3">
                <div class="input-group-prepend">
                  <label class="input-group-text" for="inputGroupSelect02">Ora Fine</label>
                </div>
                <select class="custom-select" id="inputGroupSelect02" name="ora_fine">
                  <option selected>Scegli</option>
                  <option value="1">08:30</option>
                  <option value="2">09:30</option>
                  <option value="3">10:30</option>
                  <option value="3">11:30</option>
                  <option value="3">12:30</option>
                  <option value="3">13:30</option>
                  <option value="3">14:30</option>
                  <option value="3">15:30</option>
                  <option value="3">16:30</option>
                  <option value="3">17:30</option>
                  <option value="3">18:30</option>
                </select>
              </div>
              <div class="form-group">
                <label for="exampleInputEmail1">Quota</label>
                <input type="Text" class="form-control" name="prof_surname">
              </div>
              <div class="form-group">
                <label for="exampleInputEmail1">Nome</label>
                <input type="Text" class="form-control" name="prof_surname">
              </div>
              <div class="form-group">
                <label for="exampleInputEmail1">Numero posti minimi</label>
                <input type="Text" class="form-control" name="prof_surname">
              </div>
              <div class="form-check form-check-inline">
  				<input class="form-check-input" type="checkbox" id="prese" value="prese">
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





