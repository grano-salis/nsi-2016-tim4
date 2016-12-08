<!DOCTYPE html>
<html>
	<head>
		<title>Report template engine</title>
		<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	</head>
	<body>
		<br>
		<div class="text-center" style="margin-left:30px;margin-right:30px;">
			<h2>
				Report template engine<br> <br>
			</h2>
			<h3>
				<a href="welcome.html">Route </a>
			</h3>

			<form method="POST" action="uploadTemplate" enctype="multipart/form-data">
				<div class="form-group" >
					<input type="file" name="template" class="form-control" />
				</div>
				<div class="form-group">
					<input type="submit" class="btn btn-primary" value="Upload" />
				</div>
			</form>
			<div class="message">
				${message}
			</div>
			<div class="row">
				<div class="col-md-12">
					<h3>Placeholders:</h3>
					<form action="generateReport" method="POST">
						<input type="hidden" name="filePath" value="${params.filePath}" />
						<ul class="list-unstyled">
							<c:forEach var="placeholderList" items="${params.placeholders}">
								
						    	<li>
						    		<label>${placeholderList}:</label>
						    		<input type="text" name="${placeholderList}" class="form-control" />
						    	</li>
							</c:forEach>
						</ul>
						<div class="form-group">
							<input type="submit" class="btn btn-primary" value="Generate report" />
						</div>
					</form>
				</div>
			</div>
			<div class="row">${generated}</div>
		</div>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
	</body>
</html>