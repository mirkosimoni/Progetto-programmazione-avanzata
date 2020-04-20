<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>

<body style="background-color: rgb(52,58,64);">
	<c:url value="/prenotations/save/${prenot.id}" var="action_url" />
	<div class="jumbotron jumbotron-fluid" style="background-color: rgb(52,58,64);">
  		<div class="col-md-6 offset-md-3 col-10 offset-1" style="margin-top: 5em; color: white; background-color:#696969; width: 80%; padding: 2em; border-radius: 1em;">
  			<div id="div_error" class="alert alert-secondary" role="alert" style="height: 3em; color: black;"><span id="span_error"></span></div>
  			<p hidden id="id_prenotazione">${prenot.id}</p>
			<form id="my-form" name='edit' action="${action_url}" method='POST'>    
		         <div class="form-group">
		            <label for="exampleInputEmail1">Nome Evento</label>
		            <input type="text" id="nomeve" class="form-control controllo" name="nome_evento" value="${prenot.nomeEvento}">
		        </div>
		        <div class="form-group">
		            <label for="exampleInputEmail1">Note</label>
		            <input type="text" id="not" class="form-control controllo" name="note" value="${prenot.note}">
		        </div>
		        <div class="form-group">
		            <label for="exampleInputEmail1">Quota</label>
		            <input type="text" id="quot" class="form-control controllo" name="quota" value="${prenot.aula.quota}">
		         </div>
		         <div class="form-group">
		            <label for="exampleInputEmail1">Aula</label>
		            <input type="text" id="aul" class="form-control controllo" name="aula" value="${prenot.aula.nome}">
		         </div>
		         <div class="form-group">
		    		<label for="exampleInputEmail1">Giorno</label>
		    		<input type="Date" id="gio" class="form-control controllo" name="data" value="${formatter_giorno.format(prenot.oraInizio.toDate())}">
		  		</div>
		            <div class="input-group mb-3">
		            	<div class="input-group-prepend">
		                	<label class="input-group-text" for="inputGroupSelect01">Ora Inizio</label>
		                </div>
		                <select class="custom-select controllo" id="inputGroupSelect01" name="ora_inizio">
		                  <option selected>${formatter_ora.format(prenot.oraInizio.toDate())}</option>
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
		                  <option selected>${formatter_ora.format(prenot.oraFine.toDate())}</option>
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
		            <button type="submit" class="btn btn-danger" role="button" aria-pressed="true"><i class="far fa-hand-paper"></i> Modifica </button>
		     </form>
		</div>
	</div>
</body>

<script type="text/javascript">
	$(".controllo").change(function(){
		var form = {
			"id": document.getElementById("id_prenotazione").value, 
			"nome_evento": document.getElementById("nomeve").value,
			"note": document.getElementById("not").value,
			"quota": document.getElementById("quot").value,
			"nome_aula": document.getElementById("aul").value,
			"giorno": document.getElementById("gio").value,
			"oraInizio": document.getElementById("inputGroupSelect01").value,
			"oraFine": document.getElementById("inputGroupSelect02").value,
		}
		console.log(JSON.stringify(form)),
  		 $.ajax({
  			headers: { 
        		'Accept': 'application/json',
        		'Content-Type': 'application/json' 
    		},
            dataType: "text", // and this
  		 	type: 'POST',
            url : '${pageContext.request.contextPath}/prenotations/ajaxtest',
            data: JSON.stringify(form),
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
    		cache: false,    //This will force requested pages not to be cached by the browser          
    		processData:false, //To avoid making query String instead of JSON
            success : function(data) {
            	console.log("Data: "+data);
            	alert(data);
            	if(data == 1) {
                	$("#span_error").text("Aula non trovata");
                	$("#div_error").removeClass('alert-secondary');
                	$("#div_error").removeClass('alert-success');
                	$("#div_error").addClass('alert-danger');
            	}
            	if(data == 2) {
                	$("#span_error").text("Scegli valori diversi");
                	$("#div_error").removeClass('alert-secondary');
                	$("#div_error").removeClass('alert-success');
                	$("#div_error").addClass('alert-danger');
            	}
            	if(data == 3) {
                	$("#span_error").text("Modifica possibile premi il testo modifica");
                	$("#div_error").removeClass('alert-secondary');
                	$("#div_error").removeClass('alert-danger');
                	$("#div_error").addClass('alert-success');
            	}
            },
            error: function(e) {console.log(e);}
        });
	});
</script>