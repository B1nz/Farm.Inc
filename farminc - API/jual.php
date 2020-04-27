<?php

include("config.php");



$sql = "SELECT * FROM jual";
$result = array();
$query = mysqli_query($db, $sql);

while ($row = mysqli_fetch_array($query)) {
    array_push($result, array(
        'id_jual' => $row['id_jual'],
        'user_id' => $row['user_id'],
        'produk_id' => $row['produk_id']
    ));
}
echo json_encode(array("result" => $result));
