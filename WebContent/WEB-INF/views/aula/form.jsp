<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>

<body style="background-color: rgb(52,58,64);">
	<c:url value="/aula/save/${aula.id}" var="action_url" />
	<div class="jumbotron jumbotron-fluid" style="background-color: rgb(52,58,64);">
  		<div class="col-md-6 offset-md-3 col-10 offset-1" style="margin-top: 5em; color: white; background-color:#696969; width: 80%; padding: 2em; border-radius: 1em;">
  			<h2><span class="badge badge-danger">Modifica aula</span></h2>
  			<input hidden ="" id="id_aula" value="${aula.id}"> 
			<form id="my-form" name='edit' action="${action_url}" method='POST'>  
			  
		         <div class="form-group">
		            <label for="exampleInputEmail1">Nome</label>
		            <input type="text" id="nomeve" class="form-control controllo" name="nome" maxlength="100" value="${aula.nome}">
		        </div>
		        <div class="form-group">
                	<label for="exampleInputEmail1">Quota</label>
						<select class="custom-select" id="inputGroupSelect03" name="quota">
                  			<option selected>${aula.quota}</option>
                  			<c:forEach items="${quote}" var="q">
                  				<option value="${q}">${q}</option>
                  			</c:forEach>
                </select>
              </div>
		        <div class="form-group">
		            <label for="exampleInputEmail1">Numero posti</label>
		            <input type="text" id="quot" class="form-control controllo" name="numero_posti" value="${aula.numeroPosti}">
		         </div>
		         <div class="form-check form-check-inline">
		         	<c:if test="${aula.presentiPrese}">
  						<input class="form-check-input" type="checkbox" name="prese" checked>
  					</c:if>
  					<c:if test="${!aula.presentiPrese}">
  						<input class="form-check-input" type="checkbox" name="prese">
  					</c:if>
 					<label class="form-check-label" for="inlineCheckbox1">Presenza prese</label>
			  	</div>
		            
		            <button type="submit" class="btn btn-danger" role="button" aria-pressed="true"><i class="far fa-hand-paper"></i> Modifica </button>
		     </form>
		</div>
	</div>
</body>