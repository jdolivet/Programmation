<?php

    // configuration
    require("../includes/config.php");


    if ($_SERVER["REQUEST_METHOD"] == "POST")
    {
        // validation of the inputs
	if (empty($_POST["old_pass"]))
	{
		apologize("You need to enter your actual password");
	}
	if (empty($_POST["password"]))
	{
		apologize("You need to enter your new password");
	}
	if ($_POST["password"] != $_POST["confirmation"])
	{
		apologize("Your new password and confirmation don't match");
	}

	$pass = query("SELECT hash FROM users WHERE id = ?", $_SESSION["id"]);
	$hash = $pass[0]["hash"];
	
	if (crypt($_POST["old_pass"],$hash) != $hash)
	{
		apologize("The actual password is wrong");
	}
	else
	{
		query("UPDATE users SET hash = ? WHERE id = ?", crypt($_POST["password"]), $_SESSION["id"]);
		render("change_results.php", ["title" => "Change"]);
	}
    }
	else
    {
        // else render form
        render("change_form.php", ["title" => "Change"]);
    }

?>
