<?php

    require(__DIR__ . "/../includes/config.php");

    // numerically indexed array of places
    $places = [];

    // search database for places matching $_GET["geo"]

	$keywords = preg_split("/[\s,]+/",$_GET["geo"]);

	$requete = "";
	for ($i = 0, $num_entries = count($keywords); $i < $num_entries; $i++)
	{
		if ($i == 0)
		{
			$requete = $requete."+";
		}		
		$requete = $requete.$keywords[$i];
		if ($i < $num_entries -1)
		{
			$requete = $requete." +";
		}
	}


	$requete = $requete."";

	$places = query("SELECT * FROM places WHERE MATCH location AGAINST (? IN BOOLEAN MODE)", $requete);


    // output places as JSON (pretty-printed for debugging convenience)
    header("Content-type: application/json");
    print(json_encode($places, JSON_PRETTY_PRINT));

?>
