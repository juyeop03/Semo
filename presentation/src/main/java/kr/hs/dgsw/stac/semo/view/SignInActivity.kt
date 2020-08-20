package kr.hs.dgsw.stac.semo.view

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kr.hs.dgsw.stac.semo.R
import kr.hs.dgsw.stac.semo.base.BaseActivity
import kr.hs.dgsw.stac.semo.databinding.ActivitySignInBinding
import kr.hs.dgsw.stac.semo.viewmodel.SignInViewModel
import kr.hs.dgsw.stac.semo.widget.extension.startActivityNoFinish
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SignInActivity : BaseActivity<ActivitySignInBinding, SignInViewModel>() {

    private val GOOGLE_LOGIN_CODE = 9001
    private lateinit var googleSignInClient : GoogleSignInClient

    override val viewModel: SignInViewModel
        get() = getViewModel(SignInViewModel::class)

    override fun init() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    override fun observerViewModel() {
        with(viewModel) {
            onSignInEvent.observe(this@SignInActivity, Observer {
                signIn()
            })
            onGoogleSignInEvent.observe(this@SignInActivity, Observer {
                googleSignIn()
            })
            onSignUpEvent.observe(this@SignInActivity, Observer {
                startActivityNoFinish(applicationContext, SignUpActivity::class.java)
            })
        }
    }

    private fun signIn() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(viewModel.email.value.toString(), viewModel.pw.value.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) Toast.makeText(applicationContext, "로그인을 성공하였습니다.", Toast.LENGTH_SHORT).show()
                else Toast.makeText(applicationContext, "로그인을 실패하였습니다.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_LOGIN_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_LOGIN_CODE) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result!!.isSuccess) {
                val account = result.signInAccount
                firebaseAuthWithGoogle(account!!)
            } else Toast.makeText(applicationContext, result.status.toString(), Toast.LENGTH_SHORT).show()
        }
    }
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "로그인을 성공하였습니다.", Toast.LENGTH_SHORT).show()
                    setUserData(account)
                } else Toast.makeText(applicationContext, "로그인을 실패하였습니다.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
            }
    }
    private fun setUserData(account: GoogleSignInAccount) {
        val fireStore = FirebaseFirestore.getInstance()
        val userData = HashMap<String, Any>()
        userData["email"] = account.email.toString()
        userData["name"] = account.displayName.toString()

        fireStore.collection("user")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        if (document.data["email"]!! == account.email.toString()) return@addOnCompleteListener
                    }
                    fireStore.collection("user")
                        .add(userData)
                        .addOnSuccessListener {
                            Toast.makeText(applicationContext, "DB에 데이터를 추가했습니다.", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                        }
                }
            }
    }
}