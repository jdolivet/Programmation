<div>
Hi your amount of cash is : <strong><?=number_format($cash[0]["cash"],2)?> </strong>$.
</div>
<br>
<br>

<div>
Here is your historic :
</div>
<br>
<br>
<table class="table table-stripped">
    	<thead>
        	<tr>
			<th>Transaction</th>
			<th>Date/Time</th>
			<th>Symbol</th>
			<th>Shares</th>
			<th>Price per unit</th>
			<th>Total</th>
		</tr>
	</thead>

	<tbody>
		<?php foreach ($table as $row): ?>

		    <tr>
			<th><?= $row["type"] ?></th>
			<th><?= $row["time"] ?></th>
			<th><?= $row["symbol"] ?></th>
			<th><?= $row["shares"] ?></th>
			<th><?= number_format($row["price"],2) ?></th>
			<th>
			<?php 
			if ($row["type"] == "BOUGHT")
			{
				print("- ".number_format($row["shares"]*$row["price"],2));
			}
			else
			{
				print("+ ".number_format($row["shares"]*$row["price"],2));
			}
			?>
			</th>
		    </tr>

		<?php endforeach ?>
	</tbody>
</table>

<div>
    <a href="index.php">Home page</a>
</div>
