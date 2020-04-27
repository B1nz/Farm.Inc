<?php

include("config.php");



$sql = "SELECT * FROM kategori";
$result = array();
$query = mysqli_query($db, $sql);

while ($row = mysqli_fetch_array($query)) {
    array_push($result, array(
        'id_kategori' => $row['id_kategori'],
        'jenis' => $row['jenis']
    ));
}
echo json_encode(array("result" => $result));
