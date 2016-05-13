<?php
require "init.php";

$id = "";
$username = $_POST["username"];
$phoneNumber = $_POST["phoneNumber"];
$password = $_POST["password"];
$confPassword = $_POST["confPassword"];


if (empty($username) || empty($phoneNumber) || empty($password))
				{
				$json['error'] = 'Please fill all the fields';
				echo json_encode($json);
				mysqli_close($con);
				}	
if ((strlen($username)<6) || (strlen($password)<6) || (strlen($phoneNumber)<11))
				{
				$json['error'] = '';
				echo json_encode($json);
				mysqli_close($con);
				}
				
if ($_POST["password"] != $_POST["confPassword"])
				{
				$json['error'] = '';
				echo json_encode($json);
				mysqli_close($con);
				}
				
				$query = "Select * from user_info where username = '$username' || phoneNumber = '$phoneNumber'";
				$result = mysqli_query($con, $query);
				
if(mysqli_num_rows($result)>0)
				{
				$json['error'] = '';
				echo json_encode($json);
				mysqli_close($con);
				}
				
else 			{
				$query = "insert into user_info values ('$id', '$username', '$phoneNumber','$password','$confPassword');";
				$result = mysqli_query($con, $query);
				$json['success'] = '';
				echo json_encode($json);
				mysqli_close($con);
				}	
?>
