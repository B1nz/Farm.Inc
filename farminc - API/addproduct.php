<?php

include("config.php");

    $kategori_id= $_POST['kategori_id'];
    $nama_produk= $_POST['nama_produk'];
    $deskripsi = $_POST['deskripsi'];
    $harga = $_POST['harga'];
    $image = $_POST['image'];

    $sql= "INSERT INTO `produk` (`id_produk`, `kategori_id`, `nama_produk`, `deskripsi`, `harga`, `image`, `created_at`, `updated_at`) 
        VALUES (NULL, '$kategori_id', '$nama_produk', '$deskripsi', '$harga', '$image', NULL, NULL)";
    $query = mysqli_query($db, $sql);

    if($query) {

    } else {
        die("Gagal menyimpan perubahan...");
    }
