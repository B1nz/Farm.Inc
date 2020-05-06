package com.gluthfi.farminc

class ApiEndPoint {
    companion object {

        private val SERVER = "http://192.168.100.8/farminc/"
        val REGISTER = SERVER+"register.php"
        val LOGIN = SERVER+"ceklogin.php"
        val FRESH = SERVER+"freshproduk.php"
        val SEARCH = SERVER+"cariproduk.php"
        val PROFILE = SERVER+"showuser.php"
        val PROFILE_UPDATE = SERVER+"profile_update.php"
        val GETDATA = SERVER+"get_hpalamat.php"
        val ADMIN_PRODUK = SERVER+"user_produk.php"
        val ADD_PRODUCT = SERVER+"addproduct.php"
        val EDIT_PRODUCT = SERVER+"edit_produk.php"
        val DELETE_PRODUCT = SERVER+"delete_produk.php"

    }
}