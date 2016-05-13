<?php
require "init.php";

$username = $_POST["username"];
mysqli_set_charset($con, 'utf8'); 

$query="SELECT * FROM pack_details WHERE username='$username'";
$result = mysqli_query($con, $query);

while($row = mysqli_fetch_assoc($result))
				{
				$packs[] = $row;
				}  
				echo json_encode($packs);
?>