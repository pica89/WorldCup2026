package com.istea.worldcup.navigation

import androidx.navigation.NavController

class RouterImpl(val navController: NavController): Router {

    override fun openGroupDetail(groupId: String) {
        navController.navigate("detalle/$groupId")
    }

    override fun back() {
        navController.popBackStack()
    }
}
