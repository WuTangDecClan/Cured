package com.example.cured;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cured.R;
import com.example.cured.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.login_new);

        findViewById(R.id.cirLoginButton).setOnClickListener(onClickListener);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) { // If there are current user
            // Go to Main view
            //FirebaseAuth.getInstance().signOut(); // This is logout method
            startMainActivity();
            Log.i("msg", "User email:" + FirebaseAuth.getInstance().getCurrentUser().getEmail());
        }

    }
    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            switch(v.getId()){
                case R.id.cirLoginButton:
                    Login();
                    break;
            }
        }
    };

    public void onLoginClick(View View) { // Go to register view... Why this method named on Login Click?
        startActivity(new Intent(this, RegisterActivity.class));
        //overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }

    public void onGLoginClick(View View){

    }

    private void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void Login(){
        String email = ((EditText)findViewById(R.id.editTextEmail)).getText().toString();
        String password = ((EditText)findViewById(R.id.editTextPassword)).getText().toString();

        if(email.length() > 0 && password.length() > 0){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                startToast("Login is success");
                                startMainActivity();
                            } else {
                                // If sign in fails, display a message to the user.
                                //Log.w(TAG, "signInWithEmail:failure", task.getException());
                                if(task.getException() != null)
                                    startToast(task.getException().toString());
                                //Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                        //Toast.LENGTH_SHORT).show();
                                // UI
                            }

                            // ...
                        }
                    });
        }
        else
            startToast("Please check if your email and password filled or not.");
    }
    private void startToast(String msg) {
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }

}