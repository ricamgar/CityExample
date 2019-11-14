package com.utad.networking.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.utad.networking.R
import com.utad.networking.data.local.PreferenceLocalRepository
import com.utad.networking.ui.citysearch.CitySearchActivity

class LoginActivity : AppCompatActivity(), LoginView {

    private lateinit var usernameTxt: EditText
    private lateinit var passwordTxt: EditText
    private lateinit var loginBtn: Button
    private lateinit var clearBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameTxt = findViewById(R.id.usernameTxt)
        passwordTxt = findViewById(R.id.passwordTxt)
        loginBtn = findViewById(R.id.loginBtn)
        clearBtn = findViewById(R.id.clearBtn)

        val localRepository =
            PreferenceLocalRepository(
                getSharedPreferences(
                    "login_preference",
                    Context.MODE_PRIVATE
                )
            )
        val presenter = LoginPresenter(this, localRepository)

        loginBtn.setOnClickListener {
            val username = usernameTxt.text.toString()
            val password = passwordTxt.text.toString()
            presenter.onLoginClicked(username, password)
        }

        clearBtn.setOnClickListener {
            presenter.onClearClicked()
        }

        presenter.init()
    }

    override fun showLoginSuccessful() {
        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
        goToSearch()
    }

    override fun showLoginError() {
        Toast.makeText(this, "Login error", Toast.LENGTH_SHORT).show()
        clearFields()
    }

    override fun showFieldRequiredError(emptyList: List<String>) {
        Toast.makeText(
            this,
            "The fields ${emptyList.joinToString()} are required",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun clearFields() {
        usernameTxt.text.clear()
        passwordTxt.text.clear()
    }

    override fun goToSearch() {
        startActivity(Intent(this, CitySearchActivity::class.java))
        finish()
    }
}
