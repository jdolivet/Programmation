<table class="table table-stripped">
    	<thead>
        	<tr>
			<td><a href="quote.php">Quote</a></td>
			<td><a href="buy.php">Buy</a></td>
			<td><a href="sell.php">Sell</a></td>
			<td><a href="history.php">History</a></td>
			<td><a href="change.php">Password</a></td>
			<td><a href="logout.php"><strong>Log Out</strong></a></td>
		</tr>
	</thead>
</table>
<br>
<br>
<div>
	Hi, <strong><?=$cash[0]["username"]?></strong>, your amount of cash is : <strong><?=number_format($cash[0]["cash"],2)?> </strong>$.
</div>
<br>
<br>

<div>
Here is your portfolio :
</div>
<br>
<br>
<table class="table table-stripped">
    	<thead>
        	<tr>
			<th>Name</th>
			<th>Symbol</th>
			<th>Shares</th>
			<th>Price</th>
			<th>TOTAL</th>
		</tr>
	</thead>

	<tbody>
		<?php foreach ($positions as $position): ?>

		    <tr>
			<th><?= $position["name"] ?></th>
			<th><?= $position["symbol"] ?></th>
			<th><?= $position["shares"] ?></th>
			<th><?= number_format($position["price"],2) ?></th>
			<th><?= number_format($position["price"]*$position["shares"],2) ?></th>
		    </tr>

		<?php endforeach ?>
	</tbody>
</table>


