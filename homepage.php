<?php
require "init.php";

date_default_timezone_set ("Asia/Shanghai");
$date_time = date("y/m/d\nH:i:s",time());
$username = $_POST["username"];
$company = $_POST["company"];
$owner = $_POST["owner"];
$phoneNumber = $_POST["phoneNumber"];
$pickUpCode = $_POST["pickUpCode"];
$delivery_date = $_POST["delivery_date"];
$delivery_time = $_POST["delivery_time"];
$delivery_building = $_POST["delivery_building"];
$delivery_room = $_POST["delivery_room"];

mysqli_set_charset($con, 'utf8'); 

if (empty($owner) || empty($phoneNumber) || empty($pickUpCode) || empty($delivery_room))
				{
				$json['SendingState'] = 'Error';
				echo json_encode($json);
				mysqli_close($con);
				}	
				
if((strlen($phoneNumber) < 11) || (strlen($pickUpCode) < 3) || (strlen($delivery_room) < 3))
				{
				$json['SendingState'] = 'Error';
				echo json_encode($json);
				mysqli_close($con);
				}
					
				$query = "Select * from pack_details where pickUpCode = '$pickUpCode' && company = '$company'";
				$result = mysqli_query($con, $query);
				
if(mysqli_num_rows($result)>0)
				{
				$json['SendingState'] = 'Error';
				echo json_encode($json);
				mysqli_close($con);
				}
				
else 			{
				$query = "insert into pack_details values('$date_time', '$username', '$company', '$owner','$phoneNumber','$pickUpCode', 
				'$delivery_date', '$delivery_time', '$delivery_building', '$delivery_room');";
				$result = mysqli_query($con, $query);
				$json['SendingState'] = 'Success';
				echo json_encode($json);
				mysqli_close($con);
				}
?>







