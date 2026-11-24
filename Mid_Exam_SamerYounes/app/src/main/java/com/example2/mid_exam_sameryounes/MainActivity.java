package com.example2.mid_exam_sameryounes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;

import android.os.Bundle;
import com.example2.mid_exam_sameryounes.R;
import com.example2.mid_exam_sameryounes.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    public static ActivityMainBinding mainBinding;
    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private boolean isBackPressedOnce = false;
    private final Handler backPressHandler = new Handler(Looper.getMainLooper());
    private final Runnable resetBackPressRunnable = () -> isBackPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());


        editTextEmail = findViewById(R.id.editText_email);
        editTextPassword = findViewById(R.id.editText_password);
        buttonLogin = findViewById(R.id.button_login);



        TextWatcher validationWatcher = new TextWatcher(){     //هان نراقب النص الذي يدخله المستخدم و نفحصه

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateInputs();                             // هان نراقب النص وقت الادخال بالزبط
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };



        editTextEmail.addTextChangedListener(validationWatcher);
        editTextPassword.addTextChangedListener(validationWatcher);

        buttonLogin.setOnClickListener(v -> {          // ماذا يحدث عند الضغط على الزر ؟   و ينقلنا الى الشاشة الثانية عند الضغط عليه
            Intent intent = new Intent(MainActivity.this, ModeActivity.class);
            startActivity(intent);
        });



        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isBackPressedOnce) {
                    backPressHandler.removeCallbacks(resetBackPressRunnable);
                    finishAffinity(); // إغلاق التطبيق بالكامل  
                } else {
                    isBackPressedOnce = true;                      // لما المستخدم يضغط على زر الرجوع مرة واحدة يظهر له sanckbar لمدة قصيرة
                    Snackbar.make(findViewById(android.R.id.content),
                                    "Press back again to exit",
                                    Snackbar.LENGTH_SHORT)
                            .show();


                    backPressHandler.postDelayed(resetBackPressRunnable, 2000);
                }
            }
        });



    }




    private void validateInputs() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString();

        boolean isEmailValid = !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
        boolean isPasswordValid = password.length() >= 6;

        if (isEmailValid && isPasswordValid) {
            buttonLogin.setEnabled(true);
        } else {
            buttonLogin.setEnabled(false);
        }
    }


    protected void onDestroy() {
        super.onDestroy();
        backPressHandler.removeCallbacks(resetBackPressRunnable);
    }
}