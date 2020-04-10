<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row">
	<div class="col-6 offset-3">
		<div class="jumbotron jumbotron-fluid">
			<div class="container">
				<h1 class="display-4">Login</h1>
				<form name='login' action="<c:url value="/login" />" method='POST'>
				<c:if test="${not empty errorMessage}">
					<div style="color: red; font-weight: bold; margin: 30px 0px;">${errorMessage}</div>
				</c:if>
				           
  					<div class="form-group">
    					<label for="exampleInputEmail1">Username</label>
    					<input type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" name="username">
  					</div>
  					<div class="form-group">
    					<label for="exampleInputPassword1">Password</label>
    					<input type="password" class="form-control" id="exampleInputPassword1" name="password">
  					</div>
 					<button type="submit" class="btn btn-primary" role="button" aria-pressed="true">Submit</button>
				</form>
			</div>
		</div>
	</div>
</div>
