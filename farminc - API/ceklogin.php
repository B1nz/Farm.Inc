<?php

include("config.php");

$email= $_POST['email'];
$password= $_POST['password'];

$sql = "SELECT * FROM user WHERE email='$email' AND password='$password'";
$result = array();
$query = mysqli_query($db, $sql);
$stat=mysqli_num_rows ( $query ); 

array_push($result, array('status' => $stat));
echo json_encode(array("result" => $result));
