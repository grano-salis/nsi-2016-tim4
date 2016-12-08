function loadTemplateTypes(){
	
	$.ajax({
		type : "GET",
		url : "/ReportTemplateEngine/api/services/data/template_type",
		dataType : "json",
	    contentType: "application/json; charset=utf-8",
		success : function(data) {
			
			$(".template-types-dropdown").append("<option value='' disabled selected>Odaberi</option>");
			
			data["data"].forEach(function(element, index){
				$(".template-types-dropdown").append("<option value='" + element["id"] + "'>" + element["name"] + "</option>");
			});
			
		},
		error : function(e) {
			console.log("ERROR: ", e);
		},
		done : function(e) {
			console.log("DONE");
		}
		});
	
}
 
function loadTemplateType(templateId){

	$.ajax({
		type : "GET",
		url : "/ReportTemplateEngine/api/services/data/template_type/" + templateId,
		dataType : "json",
	    contentType: "application/json; charset=utf-8",
		success : function(data) {
			
			// TODO: write implementation
			
		},
		error : function(e) {
			console.log("ERROR: ", e);
		},
		done : function(e) {
			console.log("DONE");
		}
		});
}

function loadTemplates(){
	
	$.ajax({
		type : "GET",
		url : "/ReportTemplateEngine/api/services/data/template",
		dataType : "json",
	    contentType: "application/json; charset=utf-8",
		success : function(data) {
			
			data["data"].forEach(function(element, index){
				
				var newTableRow = '<tr>' +
                    '<td><input type="checkbox" class="flat" name="table_records"></td>' +
                    '<td style="padding-left:3%; text-align:left;">' + element["name"] + '</td>' +
                    '<td style="padding-left:3%; text-align:left;"> Template: ' + element["templateDefinition"]["templateType"]["name"] + '</td>' +
                    '<td style="text-align:center">' +
                    '<a style="cursor:pointer; margin-right:5%;"><i class="fa fa-pencil"></i> </a>' +
                    '<a style="cursor:pointer; color:red; margin-left:5%;"><i class="fa fa-trash"></i> </a>' +
                    '</td>' +
                    '</tr>';
				
				$("#datatable-checkbox").find("tbody").append(newTableRow);
				
			});
			
		},
		error : function(e) {
			console.log("ERROR: ", e);
		},
		done : function(e) {
			console.log("DONE");
		}
		});
	
}

$(function(){

	$(document).on('click', '.templates', function(){
		if ($(this).attr("name") === "add")
			loadTemplateTypes();
		
		else
			loadTemplates();
		
	});
	
	
});
