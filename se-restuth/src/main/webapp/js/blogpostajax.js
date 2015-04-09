/**
 * 
 */

$(document).ready(
	
		function()
		{
			alert("document is ready");
			handleEvents();
		}	
);



function handleEvents()
{
	$("#sub").click(
			
			function()
			{
			
				var restdata=JSON.parse($("#postcontent").val());
			//	var restdata={"content":"this is blog text. read this blog", "author": "eshhhh"};
			//	var url=$("#url");
				alert("Inside JQuery function");
				alert(restdata);
			 $.ajax({
		           type: "POST",
		           url: "rest/blog/post",
		           dataType: "json",
		           contentType: "application/json",
		           data:JSON.stringify(restdata),
		           success: function (msg, status) {
		               if (msg) {
		                   alert("Result from service is "+JSON.stringify(msg)+" status is "+status);
		               //    location.reload(true);
		               } else {
		                   alert("Status is ");
		               }
		           },
		 
			 error:function(XMLHttpRequest, textStatus, errorThrown)
			 {
				 alert("Error occured in jQuery "+errorThrown+"  "+textStatus);
				 alert(XMLHttpRequest.statusText);
			 }
		       });
			 
			}
			
			
				);
		
			

			
}
