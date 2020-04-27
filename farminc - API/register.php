<?php

include("config.php");

    $nama= $_POST['nama'];
    $email= $_POST['email'];
    $password = $_POST['password'];
    $alamat = $_POST['alamat'];
    $nohp = $_POST['nohp'];

    $sql= "INSERT INTO `user` (`id_user`, `nama`, `email`, `password`, `alamat`, `nohp`, `created_at`, `updated_at`) 
        VALUES (NULL, '$nama', '$email', '$password', '$alamat', '$nohp', NULL, NULL)";
    $query = mysqli_query($db, $sql);

    if($query) {

    } else {
        die("Gagal menyimpan perubahan...");
    }

?>