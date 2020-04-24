<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>

<sec:authorize access="hasRole('Teacher')" var="isTeacher" />
<sec:authorize access="isAuthenticated()" var="isAuth" />
<sec:authorize access="hasRole('Student')" var="isStudent" />
<sec:authorize access="hasRole('Admin')" var="isAdmin" />

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
      <th scope="col">Cognome</th>
      <th scope="col">Nome</th>
      <th scope="col">Quota</th>
      <th scope="col">Aula</th>
      <th scope="col">Nome evento</th>
      <th scope="col">Ora inizio</th>
      <th scope="col">Ora fine</th>
      <th scope="col">Note</th>
      <c:if test="${isAdmin || isTeacher}">
      	<th scope="col">Modifica</th>
      	<th scope="col">Elimina</th>
      </c:if>
    </tr>
  </thead>
  <tbody>
  <c:forEach items="${prenotations}" var="p">
    <tr class="text-center" style="color: white;">
      <td>${p.user.profile.cognome}</td>
      <td>${p.user.profile.nome}</td>
      <td>${p.aula.quota}</td>
      <td>${p.aula.nome}</td>
      <td>${p.nomeEvento}</td>
      <td>${formatter.format(p.oraInizio.toDate())}</td>
      <td>${formatter.format(p.oraFine.toDate())}</td>
      <td>${p.note}</td>
      <c:if test="${p.user.username == user.username || isAdmin}">
      	<td><a href="<c:url value="/prenotations/${p.id}/edit"/>"><i class="far fa-hand-paper" style="color: rgb(218,56,73);"> </i></a></td>
      	<td><a href="<c:url value="/prenotations/delete/${p.id}"/>"><i class="fas fa-trash" style="color: rgb(218,56,73);"></i></a></td>
      </c:if>
    </tr>
    </c:forEach>
  </tbody>
</table>
</div>




<!-- Button trigger modal -->
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

<!-- Modal Create-->
<div class="modal fade" id="modalCreate" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content" style= "background-color: #696969; color: white;">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalCenterTitle">Creazione Prenotazione</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      <div id="div_error" class="alert alert-secondary" role="alert" style="height: 3em; color: black;"><span id="span_error"></span></div>
        <c:url value="/prenotations/create" var="action_url2" />
            <form name='create' action="${action_url2}" method='POST'>    



              <div class="form-group">
                <label for="exampleInputEmail1">Nome Evento</label>
                <input id = "nomeve" type="text" class="form-control controllo" name="nome_evento">
              </div>
              <div class="form-group">
                <label for="exampleInputEmail1">Note</label>
                <input  id = "not" type="text" class="form-control controllo" name="note">
              </div>
              <div class="form-group">
                <label for="exampleInputEmail1">Quota</label>
                <input  id = "quot" type="text" class="form-control controllo" name="quota">
              </div>
              <div class="form-group">
                <label for="exampleInputEmail1">Aula</label>
                <input  id = "aul" type="text" class="form-control controllo" name="aula">
              </div>
              <div class="form-group">
    			<label for="exampleInputEmail1">Giorno</label>
    			<input  id = "gio" type="Date" class="form-control controllo" name="data">
  			  </div>
              <div class="input-group mb-3">
                <div class="input-group-prepend">
                  <label class="input-group-text" for="inputGroupSelect01">Ora Inizio</label>
                </div>
                <select class="custom-select controllo" id="inputGroupSelect01" name="ora_inizio">
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
                <select class="custom-select controllo" id="inputGroupSelect02" name="ora_fine">
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
              <button type="submit" class="btn btn-danger" role="button" aria-pressed="true"><i class="fas fa-plus-circle"></i> Crea </button>
            </form>
          </div>
        </div>
      </div>
    </div>


<script type="text/javascript">
	$(".controllo").change(function(){
		var form2 = {
			
			"nome_evento": document.getElementById("nomeve").value,
			"note": document.getElementById("not").value,
			"quota": document.getElementById("quot").value,
			"nome_aula": document.getElementById("aul").value,
			"giorno": document.getElementById("gio").value,
			"oraInizio": document.getElementById("inputGroupSelect01").value,
			"oraFine": document.getElementById("inputGroupSelect02").value,
		}
		console.log(JSON.stringify(form2)),
  		 $.ajax({
  			headers: { 
        		'Accept': 'application/json',
        		'Content-Type': 'application/json' 
    		},
            dataType: "text", // and this
  		 	type: 'POST',
            url : '${pageContext.request.contextPath}/prenotations/ajaxtestcreate',
            data: JSON.stringify(form2),
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
    		cache: false,    //This will force requested pages not to be cached by the browser          
    		processData:false, //To avoid making query String instead of JSON
            success : function(data) {
            	console.log("Data: "+data);
            	//alert(data);
            	if(data == 1) {
                	$("#span_error").text("Aula non trovata");
                	$("#div_error").removeClass('alert-secondary');
                	$("#div_error").removeClass('alert-success');
                	$("#div_error").addClass('alert-danger');
            	}
            	if(data == 2) {
                	$("#span_error").text("Scegliere orario corretto");
                	$("#div_error").removeClass('alert-secondary');
                	$("#div_error").removeClass('alert-success');
                	$("#div_error").addClass('alert-danger');
            	}
            	if(data == 3) {
                	$("#span_error").text("Crezione possibile premi il testo crea");
                	$("#div_error").removeClass('alert-secondary');
                	$("#div_error").removeClass('alert-danger');
                	$("#div_error").addClass('alert-success');
            	}
            	if(data == 4) {
            		$("#span_error").text("Prenotazione già esistente");
                	$("#div_error").removeClass('alert-secondary');
                	$("#div_error").removeClass('alert-succes');
                	$("#div_error").addClass('alert-danger');
            	}
            	if(data == 5) {
            		$("#span_error").text("Riempi tutti i campi");
                	$("#div_error").removeClass('alert-secondary');
                	$("#div_error").removeClass('alert-succes');
                	$("#div_error").addClass('alert-danger');
            	}
            	if(data == 6) {
            		$("#span_error").text("Scegli una data successiva ad oggi");
                	$("#div_error").removeClass('alert-secondary');
                	$("#div_error").removeClass('alert-succes');
                	$("#div_error").addClass('alert-danger');
            	}
            },
            error: function(e) {console.log(e);}
        });
	});
</script>







































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
            <form name='login' action="${action_url}" method='POST'>    

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



