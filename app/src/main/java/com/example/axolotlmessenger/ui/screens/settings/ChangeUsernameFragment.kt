package com.example.axolotlmessenger.ui.screens.settings

import com.example.axolotlmessenger.R
import com.example.axolotlmessenger.database.*
import com.example.axolotlmessenger.ui.screens.base.BaseChangeFragment
import com.example.axolotlmessenger.utilits.*
import kotlinx.android.synthetic.main.fragment_change_username.*
import java.util.*

/* Фрагмент для изменения username пользователя */

class ChangeUsernameFragment : BaseChangeFragment(R.layout.fragment_change_username) {

    lateinit var mNewUsername: String

    override fun onResume() {
        super.onResume()
        settings_input_username.setText(USER.username)
    }

    override fun change() {
        mNewUsername = settings_input_username.text.toString().toLowerCase(Locale.getDefault())
        if (mNewUsername.isEmpty()){
            showToast("Поле пустое")
        } else {
            REF_DATABASE_ROOT.child(
                NODE_USERNAMES
            ).addListenerForSingleValueEvent(AppValueEventListener{
                if (it.hasChild(mNewUsername)){
                    showToast("Такой пользователь уже существует")
                } else{
                    changeUsername()
                }
            })

        }
    }

    private fun changeUsername() {
        /* Изменение username в базе данных */
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(mNewUsername).setValue(
            CURRENT_UID
        )
            .addOnCompleteListener {
                if (it.isSuccessful){
                    updateCurrentUsername(mNewUsername)
                }
            }
    }




}