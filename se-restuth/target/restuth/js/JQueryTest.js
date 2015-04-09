/**
 * 
 */

$(document).ready(
	
		function()
		{
			alert("document is ready");
			handleEvents();
	//closure test
		
			

		}	
);




function handleEvents()
{
	$("#hibtn").click(
			function()
			{
				var name=$("#fname").val();
			alert("hey you clicked me ");	
			var arr="sri esh".split('');
			alert("array 1: length=" + arr.length);
			}

			);
}

