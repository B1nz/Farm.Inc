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

    }
}