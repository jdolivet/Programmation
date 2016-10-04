<?php

    	// configuration
    	require("../includes/config.php"); 

	
	$rows=query("SELECT symbol FROM Portfolios WHERE id = ?", $_SESSION["id"]);
	
	if ($rows == false)
	{
		apologize("Sorry, but you have nothing to sell!");
	}
	else
	{
		// Create array to store
		$symbols = [];
		foreach ($rows as $row)
		{
			$symbols[] = [
			    "symbol" => $row["symbol"]
			];	    
		}

		
	}

	if ($_SERVER["REQUEST_METHOD"] == "POST")
	{
		if ($_POST["symbol"] == "")
		{
			apologize("Need to select a stock");
		}
		else
		{
			$share = query("SELECT shares FROM Portfolios WHERE symbol = ? AND id = ?", $_POST["symbol"],$_SESSION["id"]);
			$shares = $share[0]["shares"];
			$value = lookup($_POST["symbol"]);
			$price = $value["price"];

			$money = query("SELECT cash FROM users WHERE id = ?",$_SESSION["id"]);
			$cash = $money[0]["cash"];

			$type = "SOLD";
			query("DELETE FROM Portfolios WHERE symbol = ? AND id = ?", $_POST["symbol"],$_SESSION["id"]);
			query("UPDATE users SET cash = ? WHERE id = ?", $cash + $price * $shares, $_SESSION["id"]);
 			query("INSERT INTO history (id, type, symbol, shares, price) VALUES (?, ?, ?, ?, ?)", $_SESSION["id"], $type, $_POST["symbol"], $shares, $price);
			render("sell_results.php", ["title" => "Sell", "price" => $price, "shares" => $shares]);
		}
	}
	else
    	{
     		// render portfolio
    		render("sell_form.php", ["title" => "Sell", "symbols" => $symbols]);
    	}

?>
