package com.mini.instagramclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mini.instagramclone.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    val binding by lazy{
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        binding.signUpButton.setOnClickListener {
                if (binding.name.editText?.text.toString().equals("") or
                    binding.email.editText?.text.toString().equals("") or
                    binding.password.editText?.text.toString().equals("")
                ) {

                    Toast.makeText(this@SignUpActivity, "please fill the above details", Toast.LENGTH_SHORT).show()
                } else {
                }
            }
        }
    }
