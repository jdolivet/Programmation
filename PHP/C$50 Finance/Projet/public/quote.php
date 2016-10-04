<?php

    	// configuration	
    	require("../includes/config.php");

   	// else if user reached page via POST (as by submitting a form via POST)
	if ($_SERVER["REQUEST_METHOD"] == "POST")
	{
		if (empty($_POST["symbol"]))
		{
			apologize("Need to enter the stock symbol");
		}
		$stock = lookup($_POST["symbol"]);
		if ($stock === false) 
		{
			apologize("It's not possible to have informations for the symbol : ".$_POST["symbol"].".");
		}
		else
		{					
			render("quote_results.php", ["title" => "Quote", "symbol" => $stock["symbol"], "name" => $stock["name"], "price" => $stock["price"]]);
		}	
	}
	else
    	{
     		// else render form
     		render("quote_form.php", ["title" => "Register"]);
    	}
	

?>
