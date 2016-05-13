<?php
require "init.php";
$username = $_POST["username"];
mysqli_set_charset($con, 'utf8'); 
$query = "Select * from default_info where username = '$username'";
$result = mysqli_query($con, $query);
$row = mysqli_fetch_assoc($result);

if($row){
  echo json_encode($row);
}else{
  echo json_encode(array('def_owner' => '','def_phoneNumber' => '','def_room' => ''));
}
?>