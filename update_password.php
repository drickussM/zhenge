<?php
require "init.php";

$oldPassword = $_POST["oldPassword"];
$newPassword = $_POST["newPassword"];
$confnewPass = $POST["confnewPass"];
$username = $_POST["username"];

$query = "SELECT * FROM user_info where username = '$username' and password = '$oldPassword'";
$result = mysqli_query($con, $query);

if (empty($username) || empty($oldPassword) || empty($newPassword))
				{
				$json['error'] = '';
				echo json_encode($json);
				mysqli_close($con);
				}	
if ((strlen($oldPassword)<6) || (strlen($newPassword)<6))
				{
				$json['error'] = '';
				echo json_encode($json);
				mysqli_close($con);
				}
if ($_POST["newPassword"] != $_POST["confnewPass"])
				{
				$json['error'] = '';
				echo json_encode($json);
				mysqli_close($con);
				}

if(mysqli_num_rows($result)>0)
				{
   				$query = "update user_info set password = '$newPassword', confpassword = '$confnewPass' where username = '$username'";
   				$json['msg'] = 'Updated';
   				$result = mysqli_query($con, $query);
				}

?>
