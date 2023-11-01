package com.mini.instagramclone

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mini.instagramclone.Models.User


//import com.google.firebase.Firebase
import com.mini.instagramclone.databinding.ActivitySignUpBinding
import com.mini.instagramclone.utils.USER_NODE
import com.mini.instagramclone.utils.USER_PROFILE_FOLDER
import com.mini.instagramclone.utils.uploadImage

class SignUpActivity : AppCompatActivity() {
    val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    lateinit var user: User
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()){
    uri->
    uri?.let {
        uploadImage(uri, USER_PROFILE_FOLDER){
            if(it==null){

            }
            else{
                user.image=it
                binding.profileImage.setImageURI(uri)
            }
        }
    }
}
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val text="<font color=#FF000000>Already have an Account </font><font color=#1E88E5>Login</font"
        binding.login.setText(Html.fromHtml(text))
        user =User()

        binding.signUpButton.setOnClickListener {
                if (binding.name.editText?.text.toString().equals("") or
                    binding.email.editText?.text.toString().equals("") or
                    binding.password.editText?.text.toString().equals("")
                ) {

                    Toast.makeText(this@SignUpActivity, "please fill the above details", Toast.LENGTH_SHORT).show()
                } else {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        binding.email.editText?.text.toString(),
                        binding.password.editText?.text.toString()
                    ).addOnCompleteListener {
                        result->
                        if(result.isSuccessful){
//                         Toast.makeText(this@SignUpActivity, "Login Successfully", Toast.LENGTH_SHORT).show()
                            user.name=binding.name.editText?.text.toString()
                            user.password=binding.password.editText?.text.toString()
                            user.email=binding.email.editText?.text.toString()
                            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).set(user)
                                .addOnSuccessListener {
//                                    Toast.makeText(this@SignUpActivity, "Login", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this@SignUpActivity,HomeActivity::class.java))
                                    finish()
                                }
                        }
                        else{
                            Toast.makeText(this@SignUpActivity, result.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
        binding.plus.setOnClickListener{
            launcher.launch("image/*")
            }
        }

}
