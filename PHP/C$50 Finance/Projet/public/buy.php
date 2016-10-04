<?php

    	// configuration
    	require("../includes/config.php"); 

	if ($_SERVER["REQUEST_METHOD"] == "POST")
	{
		if (empty($_POST["symbol"]) || empty($_POST["shares"]))
		{
		    apologize("You must enter a stock symbol and quantity of shares to buy.");
		}
		$stock = lookup($_POST["symbol"]);
		if ($stock === false) 
		{
			apologize("Invalid stock symbol");
		}
		if (preg_match("/^\d+$/", $_POST["shares"]) == false)
		{
		    apologize("You must enter a whole, positive integer.");
		}

        	$cost = $stock["price"] * $_POST["shares"];
		$cash =	query("SELECT cash FROM users WHERE id = ?", $_SESSION["id"]);	
		if ($cash < $cost)
		{
		    apologize("You don't have enough money.");
		}  
		else
		{
			$type = "BOUGHT";			
			$_POST["symbol"] = strtoupper($_POST["symbol"]);
			query("INSERT INTO Portfolios (id, symbol, shares) VALUES(?, ?, ?) 
                ON DUPLICATE KEY UPDATE shares = shares + VALUES(shares)", $_SESSION["id"], $_POST["symbol"], $_POST["shares"]);
			query("UPDATE users SET cash = cash - ? WHERE id = ?", $cost, $_SESSION["id"]);
 			query("INSERT INTO history (id, type, symbol, shares, price) VALUES (?, ?, ?, ?, ?)", $_SESSION["id"], $type, $_POST["symbol"], $_POST["shares"], $stock["price"]);
		}
		render("buy_results.php", ["title" => "Buy", "shares" => $_POST["shares"], "symbol"=> $_POST["symbol"]]);
	}
	else
    	{
     		// render portfolio
    		render("buy_form.php", ["title" => "Buy"]);
    	}

?>
