<?php
require "init.php";

$id = "";
$username = $_POST["username"];
$password = $_POST["password"];
date_default_timezone_set ("Asia/Shanghai");
$time = date("y/m/d -- H:i:s", time());

$query = "Select * from user_info where username = '$username' and password = '$password'";
$result = mysqli_query($con, $query);

if(mysqli_num_rows($result)>0)
				{
				$json['loginState'] = 'Success';
				$json['loggedInUser'] = $username;
				$json['userPassword'] = $password;
				echo json_encode($json);
				$query1 = "Insert into loggedIn_user values ('$time', '$username');";
				$result = mysqli_query($con, $query1);
				mysqli_close($con);
				}
else
				{
				$json['loginState'] = 'Error';
				echo json_encode($json);
				mysqli_close($con);
				}
?>
