package com.coursera.sacbe.petagramaxelsegura;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.coursera.sacbe.petagramaxelsegura.mail.SendMail;

public class ContactActivity extends AppCompatActivity {

    private TextInputEditText ietNombreCompleto, ietEmail, ietComentario;
    private TextInputLayout tilNombreCompleto, tilEmail, tilComentario;
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Toolbar miActionBar = (Toolbar) findViewById(R.id.miActionBar);
        setSupportActionBar(miActionBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tilNombreCompleto = (TextInputLayout) findViewById(R.id.tilNombreCompleto);
        tilEmail = (TextInputLayout) findViewById(R.id.tilEmail);
        tilComentario = (TextInputLayout) findViewById(R.id.tilComentario);
        ietNombreCompleto = (TextInputEditText) findViewById(R.id.ietNombreCompleto);
        ietEmail = (TextInputEditText) findViewById(R.id.ietEmail);
        ietComentario = (TextInputEditText) findViewById(R.id.ietComentario);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);

        ietNombreCompleto.addTextChangedListener(new MyTextWatcher(ietNombreCompleto));
        ietComentario.addTextChangedListener(new MyTextWatcher(ietComentario));
        ietEmail.addTextChangedListener(new MyTextWatcher(ietEmail));

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitFormulario();
            }
        });
    }

    private void submitFormulario() {
        if (!validaNombreCompleto()) {
            return;
        }

        if (!validaEmail()) {
            return;
        }

        if (!validaComentario()) {
            return;
        }

        SendMail mail = new SendMail();
        mail.setEmail_cc(ietEmail.getText().toString().trim());
        mail.setName(ietNombreCompleto.getText().toString().trim());
        mail.setTextMessage(ietComentario.getText().toString().trim());

        if(mail.sendMail()) {
            Toast.makeText(getApplicationContext(), "Mensaje enviado, gracias!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ContactActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else
            Toast.makeText(getApplicationContext(), "No se puede enviar el mensaje!", Toast.LENGTH_SHORT).show();
    }

    private boolean validaNombreCompleto() {
        if (ietNombreCompleto.getText().toString().trim().isEmpty()) {
            tilNombreCompleto.setError(getString(R.string.err_msg_nombreCompleto));
            requestFocus(ietNombreCompleto);
            return false;
        } else {
            tilNombreCompleto.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validaEmail() {
        String email = ietEmail.getText().toString().trim();

        if (email.isEmpty()) {
            tilEmail.setError(getString(R.string.err_msg_email));
            requestFocus(ietEmail);
            return false;
        }
        else if(!isValidEmail(email)){
            tilEmail.setError(getString(R.string.err_msg_email_valido));
            requestFocus(ietEmail);
            return false;
        }
        else {
            tilEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validaComentario() {
        String comentario = ietComentario.getText().toString().trim();
        if (comentario.isEmpty()) {
            tilComentario.setError(getString(R.string.err_msg_comentario));
            requestFocus(ietComentario);
            return false;
        } else {
            tilComentario.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.ietNombreCompleto:
                    validaNombreCompleto();
                    break;
                case R.id.ietEmail:
                    validaEmail();
                    break;
                case R.id.ietComentario:
                    validaComentario();
                    break;
            }
        }
    }
}
