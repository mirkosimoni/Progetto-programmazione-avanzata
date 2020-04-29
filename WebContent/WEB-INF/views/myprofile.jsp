<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
	
<sec:authorize access="hasRole('Admin')" var="isAdmin" />
<sec:authorize access="hasRole('Teacher')" var="isTeacher" />
	
<body style= "background-color:rgb(52,58,64);">
<div class="row">
	<img src="<c:url value="/media/home.jpg"/>" class="img-fluid" alt="home" style="width: 100%;">
</div>

<c:url value="/myprofile/upload" var="action_url" />

<div class="container">
        <div class="row">
            <div class="col-12">
                <div class="card">

                    <div class="card-body">
                        <div class="card-title mb-4">
                            <div class="d-flex justify-content-start">
                                <div class="image-container">
                                    <img src="<c:url value="${user.profile.immagine}"/>" id="imgProfile" style="width: 150px; height: 150px" class="img-thumbnail" />
                                    <div class="middle">
                                    	<form method="POST" action="${action_url}" enctype="multipart/form-data">
                                			<input type="submit" class="btn btn-danger" id="btnChangePicture" value="Change" />
                                        	<input type="file" id="profilePicture" name="file" />
                                        </form >
                                    </div>
                                </div>
                                <div class="userData ml-3">
                                    <h2 class="d-block" style="font-size: 1.5rem; font-weight: bold"><a>${user.username}</a></h2>
                                </div>                   
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-12">
                                <ul class="nav nav-tabs mb-4" id="myTab" role="tablist">
                                    <li class="nav-item">
                                        <a class="nav-link active" id="basicInfo-tab" data-toggle="tab" href="#basicInfo" role="tab" aria-controls="basicInfo" aria-selected="true">Basic Info</a>
                                    </li>
                                </ul>
                                <div class="tab-content ml-1" id="myTabContent">
                                    <div class="tab-pane fade show active" id="basicInfo" role="tabpanel" aria-labelledby="basicInfo-tab">
                                    	<c:if test="${isTeacher || isAdmin}">
                                    		<form name='myprenot' action="<c:url value="/prenotations/myprenotations/${user.username}" />" method='GET'>
                                         		<div class="row" style="margin-bottom: 2em;">                                
    												<button type="submit" class="btn btn-danger" role="button" aria-pressed="true">Prenotazioni Personali</button>
    											</div>
											</form>
										</c:if>
										<c:if test="${isAdmin}">
                                    		<form name='userlist' action="<c:url value="/myprofile/userlist"/>" method='GET'>
                                         		<div class="row" style="margin-bottom: 2em;">                                
    												<button type="submit" class="btn btn-danger" role="button" aria-pressed="true">Lista utenti</button>
    											</div>
											</form>
										</c:if>
                                        <div class="row">
                                            <div class="col-sm-3 col-md-2 col-5">
                                                <label style="font-weight:bold;">Nome</label>
                                            </div>
                                            <div class="col-md-8 col-6">
                                            ${user.profile.nome}
                                            </div>
                                        </div>
                                        <hr />

                                        <div class="row">
                                            <div class="col-sm-3 col-md-2 col-5">
                                                <label style="font-weight:bold;">Cognome</label>
                                            </div>
                                            <div class="col-md-8 col-6">
                                               ${user.profile.cognome}
                                            </div>
                                        </div>
                                        <hr />
                                        
                                        
                                        <div class="row">
                                            <div class="col-sm-3 col-md-2 col-5">
                                                <label style="font-weight:bold;">Data di nascita</label>
                                            </div>
                                            <div class="col-md-8 col-6">
                                            ${dataNascita}
                                            </div>
                                        </div>
                                        <hr />
                                        <div class="row">
                                            <div class="col-sm-3 col-md-2 col-5">
                                                <label style="font-weight:bold;">Interessi</label>
                                            </div>
                                            <div class="col-md-8 col-6">
                                                ${user.profile.interessi}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>


                    </div>

                </div>
            </div>
        </div>
    </div>
    
</body>