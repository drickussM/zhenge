<?php
require "init.php";

$username = $_POST["username"];
$def_owner = $_POST["def_owner"];
$def_phoneNumber = $_POST["def_phoneNumber"];
$def_room = $_POST["def_room"];

mysqli_set_charset($con, 'utf8'); 

if (empty($def_owner) || empty($def_phoneNumber) || empty($def_room))
				{
				$json['UpdateState'] = 'Error';
				echo json_encode($json);
				mysqli_close($con);
				}	
				
if((strlen($def_phoneNumber) < 11) || (strlen($def_room) < 3))
				{
				$json['UpdateState'] = 'Error';
				echo json_encode($json);
				mysqli_close($con);
				}
				$query = "Select * from default_info where username = '$username'";
				$result = mysqli_query($con, $query);
				
if(mysqli_num_rows($result)>0)
				{
				$query = "update default_info set def_owner = '$def_owner', def_phoneNumber = '$def_phoneNumber', def_room = '$def_room' where username = '$username'";
   				$result = mysqli_query($con, $query);
   				$json['UpdateState'] = 'Updated';
   				$json['owner'] = $def_owner;
   				$json['phoneNumber'] = $def_phoneNumber;
   				$json['room'] = $def_room;
				echo json_encode($json);
				mysqli_close($con);
				}
				
else 			{
				$query1 = "insert into default_info values('$username', '$def_owner','$def_phoneNumber','$def_room');";
				$result = mysqli_query($con, $query1);
				$json['UpdateState'] = 'Success';
				$json['owner'] = $def_owner;
   				$json['phoneNumber'] = $def_phoneNumber;
   				$json['room'] = $def_room;
				echo json_encode($json);
				mysqli_close($con);
				}		
?>