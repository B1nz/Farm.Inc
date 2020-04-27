<?php

include("config.php");

    $user_id= $_POST['user_id'];
    $produk_id= $_POST['produk_id'];

    $sql= "INSERT INTO `jual` (`id_jual`, `user_id`, `produk_id`, `created_at`, `updated_at`) 
        VALUES (NULL, '$user_id', '$produk_id', NULL, NULL)";
    $query = mysqli_query($db, $sql);

    if($query) {

    } else {
        die("Gagal menyimpan perubahan...");
    }
