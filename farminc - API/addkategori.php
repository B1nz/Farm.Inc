<?php

include("config.php");

$jenis = $_POST['jenis'];

$sql = "INSERT INTO `kategori` (`id_kategori`, `jenis`, `created_at`, `updated_at`) 
        VALUES (NULL, '$jenis', NULL, NULL)";
$query = mysqli_query($db, $sql);

if ($query) {
} else {
    die("Gagal menyimpan perubahan...");
}
