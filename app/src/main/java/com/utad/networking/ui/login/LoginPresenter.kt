package com.utad.networking.ui.login

import com.utad.networking.data.RetrofitFactory
import com.utad.networking.data.local.LocalRepository
import com.utad.networking.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginPresenter(val view: LoginView, val localRepository: LocalRepository) {

    fun init() {
        CoroutineScope(Dispatchers.IO).launch {
            val loggedUser = localRepository.getLoggedUser()
            if (loggedUser != null) {
                view.goToSearch()
            }
        }
    }

    fun onLoginClicked(username: String, password: String) {
        if (username.isEmpty() || password.isEmpty()) {
            val emptyList = mutableListOf<String>()
            if (username.isEmpty()) {
                emptyList.add("username")
            }
            if (password.isEmpty()) {
                emptyList.add("password")
            }
            view.showFieldRequiredError(emptyList)
            return
        }

        if (RetrofitFactory.login(username, password)) {
            CoroutineScope(Dispatchers.IO).launch {
                localRepository.setLoggedUser(User(username, password))
            }
            view.showLoginSuccessful()
        } else {
            view.showLoginError()
        }

    }

    fun onClearClicked() {
        view.clearFields()
    }

}

interface LoginView {
    fun showLoginSuccessful()
    fun showLoginError()
    fun showFieldRequiredError(emptyList: List<String>)
    fun clearFields()
    fun goToSearch()
}