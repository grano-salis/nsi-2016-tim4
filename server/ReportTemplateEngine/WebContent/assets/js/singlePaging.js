function getSinglePage(name) {

	$(".ajax-page-content").load(name);
	
	/*$( "#result" ).load( "ajax/test.html", function() {
		  alert( "Load was performed." );
		});*/
	
	/*$.ajax({
		type : "GET",
		url : "/api/services/data/template",
		success : function(data) {
			alert(data);
		},
		error : function(e) {
			console.log("ERROR: ", e);
			display(e);
		},
		done : function(e) {
			console.log("DONE");
		}
	});*/
}

$(function(){
	
	$(".documents, .templates").click(function(){
		getSinglePage("/ReportTemplateEngine/" + $(this).attr("class") + "/" + $(this).attr("name"));
	});
	
});