<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<body style="background-color: rgb(52,58,64);">
	<c:url value="/prenotations/save/${prenot.id}" var="action_url" />
	<div class="jumbotron jumbotron-fluid" style="background-color: rgb(52,58,64);">
  		<div class="col-md-6 offset-md-3 col-10 offset-1" style="margin-top: 5em; color: white; background-color:#696969; width: 80%; padding: 2em; border-radius: 1em;">
			<form name='edit' action="${action_url}" method='POST'>    
		         <div class="form-group">
		            <label for="exampleInputEmail1">Nome Evento</label>
		            <input type="text" class="form-control" name="nome_evento" value="${prenot.nomeEvento}">
		        </div>
		        <div class="form-group">
		            <label for="exampleInputEmail1">Note</label>
		            <input type="text" class="form-control" name="note" value="${prenot.note}">
		        </div>
		        <div class="form-group">
		            <label for="exampleInputEmail1">Quota</label>
		            <input type="text" class="form-control" name="quota" value="${prenot.aula.quota}">
		         </div>
		         <div class="form-group">
		            <label for="exampleInputEmail1">Aula</label>
		            <input type="text" class="form-control" name="aula" value="${prenot.aula.nome}">
		         </div>
		         <div class="form-group">
		    		<label for="exampleInputEmail1">Giorno</label>
		    		<input type="Date" class="form-control" name="data" value="${formatter_giorno.format(prenot.oraInizio.toDate())}">
		  		</div>
		            <div class="input-group mb-3">
		            	<div class="input-group-prepend">
		                	<label class="input-group-text" for="inputGroupSelect01">Ora Inizio</label>
		                </div>
		                <select class="custom-select" id="inputGroupSelect01" name="ora_inizio">
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
		                <select class="custom-select" id="inputGroupSelect02" name="ora_fine">
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