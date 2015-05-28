<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="favicon.ico">

    <title>ESSENCE Challenge</title>

    <!-- Bootstrap core CSS -->
    <link href="bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/signin.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->


    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>

    <div class="container">

	<div class="row">
            <div class="col-md-4"></div>
            <div class="col-md-4">
      		<div id="fdiv">
		      <form>
		      	<h3>Essence Challenge</h3>
				<p class="text-justify">In brief, the objective of the TDG challenge is to produce an agent capable of guessing a destination (the guesser) from increasingly informative "hints" provided by a human user (the describer). </p>
		        <a id="play" class="btn btn-lg btn-primary btn-block" >Let's Play</a>
		      </form>
	      	</div>
	      	<div id="sdiv">
			  <form id="bgame">
		        <h2 class="form-signin-heading">Let's Play</h2>
		        <div class="thumbnail" id="game_field" style="width: 100%; min-height: 100px;"></div>
		        <input type="text" id="inputGuess" class="form-control" placeholder="Your Guess" required autofocus>
		        <a id="send_guess" class="btn btn-lg btn-primary btn-block">Send Guess</a>
		      </form>
			</div>
		</div>
		<div class="col-md-4"></div>
    </div> <!-- /container -->

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script>
	    $(document).ready(function(){
	        $("div#sdiv").hide();
	        $("#play").click(function(){
	        	 if($( 'div#fdiv' ).is(":visible")){
	        		 $.get("http://localhost:8080/com.essence_network.www.ChallengeDescriptor/Entries", function(data, status){
	        			 	//alert("Data: " + data + "\nStatus: " + status);
	        			 	response = data;
	        			 	//alert("Data: " + data.success + "\nStatus: " + status);
	        			 	//alert("orloo "+data+ " "+success);
	        		        //response = JSON.parse(data);
	        		        //alert("surlaa");
	        		        //alert("Hint: " +  response.next_hint  + "\nPrevious Status: " + response.success);
	        		        $("#game_field").append("<p> Describer: "+data.next_hint +" </p>");
	        		        //console.log( JSON.parse( data ) );
	        		 });
	        		 $('div#fdiv').hide();
	        		 $('div#sdiv').show();
	        	 }
	        });
	        
	        $("#send_guess").click(function(){
	        	$("#game_field").append("<p> You: "+$('#inputGuess').val() +" </p>");
	        	$.get("http://localhost:8080/com.essence_network.www.ChallengeDescriptor/Entries?answer="+$('#inputGuess').val(), function(data, status){
    			 	response = data;
    			 	if(response.game_progress=="Over"){
	    			 	if (response.success=="YES")
		    		        $("#game_field").append("<p> Describer: Answer is correct! Well done! </p>");
	    			 	else 
	    			 		$("#game_field").append("<p> Describer: You Lost! The answer was "+response.answer+"</p>");
    			 	} else {
    			 		$("#game_field").append("<p> Describer: No, "+data.next_hint+" </p>");
    			 	}
    		        //console.log( JSON.parse( data ) );
    		 	});
	        });
	    });
	</script>
    
  </body>
</html>
