<?php

    	// configuration
    	require("../includes/config.php"); 
	

	// Query current users shares
	$rows = query("SELECT * FROM Portfolios WHERE id = ?", $_SESSION["id"]);

	// Create array to store
	$positions = [];
	foreach ($rows as $row)
	{
	    $stock = lookup($row["symbol"]);
	    if ($stock !== false)
	    {
		$positions[] = [
		    "name" => $stock["name"],
		    "price" => $stock["price"],
		    "shares" => $row["shares"],
		    "symbol" => $row["symbol"]
		];
	    }
	}
	$cash = query("SELECT username,cash FROM users WHERE id = ?", $_SESSION["id"]);

    	// render portfolio
    	render("portfolio.php", ["positions" => $positions, "title" => "Portfolio", "cash" => $cash]);

?>
