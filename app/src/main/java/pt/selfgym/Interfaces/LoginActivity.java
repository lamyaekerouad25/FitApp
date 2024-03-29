package pt.selfgym.Interfaces;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import pt.selfgym.R;
import pt.selfgym.databinding.AddExerciseFragmentBinding;
import pt.selfgym.helpers.DatabaseHelper;
import pt.selfgym.services.AdminLogin;
import pt.selfgym.services.InputValidation;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginActivity.this;

    private ConstraintLayout constraintLayout;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;

    private Button btnLogin;

    private TextView tvLinkRegister;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {

        constraintLayout = (ConstraintLayout) findViewById(R.id.constaintlayout);

        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.til_email);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.til_password);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.et_email);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.et_password);

        btnLogin = (Button) findViewById(R.id.btn_login);

        tvLinkRegister = (TextView) findViewById(R.id.tv_linkRegister);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        btnLogin.setOnClickListener(this);
        tvLinkRegister.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);

    }

    /**
     * This implemented method is to listen the click on view
     *
     *
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                verifyFromSQLite();
                break;
            case R.id.tv_linkRegister:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_email))) {
            return;
        }

        if (databaseHelper.checkUser(Objects.requireNonNull(textInputEditTextEmail.getText()).toString().trim()
                , Objects.requireNonNull(textInputEditTextPassword.getText()).toString().trim())) {


            Intent accountsIntent = new Intent(activity, AddExerciseFragmentBinding.class);
            accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);


        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(constraintLayout, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }

    public void Click_Me(View v) {
        Intent i = new Intent(this, AdminLogin.class);
        startActivity(i);
    }
}

