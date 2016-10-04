<form action="change.php" method="post">
    <fieldset>
	<p>
	Enter your actual password
	</p>
        <div class="form-group">
            <input autofocus class="form-control" name="old_pass" placeholder="Old password" type="text"/>
        </div>
	<p>
	Enter your new password
	</p>
        <div class="form-group">
            <input class="form-control" name="password" placeholder="New password" type="password"/>
        </div>
        <div class="form-group">
            <input class="form-control" name="confirmation" placeholder="Confirmation" type="password"/>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-default">Change</button>
        </div>
    </fieldset>
</form>
<div>
    <a href="index.php">Home page</a>
</div>
