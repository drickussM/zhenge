<?php
$db_name="kedaexpress1";
$mysql_username="root";
$mysql_password="7DMKne22";
$server_name="localhost";

$con=mysqli_connect($server_name, $mysql_username, $mysql_password, $db_name);

if(!$con)
				{
				echo "Connection Error...".mysqli_connect_error();
				}
else
				{
				echo "";
				}
?>