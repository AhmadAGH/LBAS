package tu.hw.lbas.UserManager;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tu.hw.lbas.AuthManager.LoginActivity;
import tu.hw.lbas.R;

public class RegisterActivity extends Activity {

    EditText etName,etPassword,etEmail,etCompanyName;
    Button btnRegister;
    View focusView;
    LinearLayout lLRegister;
    ProgressBar pBRegister;
    RegisterActivity registerActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        registerActivity = this;
        etCompanyName = findViewById(R.id.etCompanyName);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        lLRegister = findViewById(R.id.lLRegister);
        pBRegister = findViewById(R.id.pBRegister);

        etEmail.setText(getIntent().getStringExtra("email"));
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                attemptRegister();
            }});
    }
    private void attemptRegister()
    {
        String name = etName.getText().toString();
        String password = etPassword.getText().toString();
        String email = etEmail.getText().toString();
        String companyName = etCompanyName.getText().toString();
        boolean cancel = !validate(email,password,name,companyName);
        if(cancel)
        {
            focusView.requestFocus();
        }else
        {
            new RegisterTask().execute();
        }
    }
    private boolean validate(String email, String password,String name,String companNamey/*,String confermPassword*/) {

        // Reset errors.
        etEmail.setError(null);
        etPassword.setError(null);
        etName.setError(null);
        etCompanyName.setError(null);
       // etConfermPassword.setError(null);

        if(TextUtils.isEmpty(name))
        {
            etName.setError("Full name is required");
            focusView = etName;
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            focusView = etPassword;
            return false;
        } else if (!isPasswordValid(password)) {
            etPassword.setError("Password must contain at least 6 characters");
            focusView = etPassword;
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            focusView = etEmail;
            return false;
        } else if (!isEmailValid(email)) {
            etEmail.setError("Please enter a valid email");
            focusView = etEmail;
            return false;
        }


        if(TextUtils.isEmpty(companNamey))
        {
            etCompanyName.setError("Company Name is required");
            focusView = etCompanyName;
            return false;
        }
/*
        if (TextUtils.isEmpty(confermPassword)) {
            etConfermPassword.setError("Please Re-type your password");
            focusView = etConfermPassword;
            return false;
        } else if (!password.equals(confermPassword)) {
            etConfermPassword.setError("Passwords Don't Match");
            focusView = etConfermPassword;
            return false;
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            etPhoneNumber.setError("Phone Number is required");
            focusView = etPhoneNumber;
            return false;
        } else if (!isPhoneNumberValid(phoneNumber)) {
            etPhoneNumber.setError("Please enter a valid Phone Number e.g(+966501234567");
            focusView = etPhoneNumber;
            return false;
        }

        if(selectedSpecs.isEmpty())
        {
            Toast.makeText(context,"Please Selecet At Least One Specialization",Toast.LENGTH_LONG).show();
            focusView = btnSelectedSpecs;
            return false;
        }
        */
        return true;
    }

    private static boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 6;
    }


    public class RegisterTask extends AsyncTask<Void,Integer,Boolean>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            lLRegister.setVisibility(View.GONE);
            pBRegister.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Void... voids)
        {

            return UsersManager.register(new User(
            etName.getText().toString(),
            etPassword.getText().toString(),
            etEmail.getText().toString(),
            etCompanyName.getText().toString()));
        }

        @Override
        protected void onPostExecute(Boolean aBoolean)
        {
            lLRegister.setVisibility(View.VISIBLE);
            pBRegister.setVisibility(View.GONE);
            if(aBoolean)
            {
                Toast.makeText(RegisterActivity.this, "Registered New User Successfully", Toast.LENGTH_SHORT).show();
                LoginActivity loginActivity = new LoginActivity();
                Intent intent = new Intent(registerActivity,loginActivity.getClass());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                startActivity(intent);
            }else
            {
                Toast.makeText(RegisterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
