package com.example.lmigu.salasqr;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistoActivity extends AppCompatActivity {

    EditText editNome, editNumero, editEmail, editPassword, editCurso;
    Button btnCancelar, btnRegisto;
    String url = "";
    String parametros = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo);


        editNome = (EditText) findViewById(R.id.editTextNome);
        editNumero = (EditText) findViewById(R.id.editTextNumeroAluno);
        editEmail = (EditText) findViewById(R.id.editTextEmail2);
        editPassword = (EditText) findViewById(R.id.editTextPassword2);
        editCurso = (EditText) findViewById(R.id.editTextCurso);
        btnCancelar = (Button) findViewById(R.id.buttonCancelar);
        btnRegisto = (Button) findViewById(R.id.buttonRegistar);


        btnRegisto.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {

                    String nome = editNome.getText().toString();
                    String numero = editNumero.getText().toString();
                    String email = editEmail.getText().toString();
                    String password = editPassword.getText().toString();
                    String curso = editCurso.getText().toString();

                    if (nome.isEmpty() || numero.isEmpty() || email.isEmpty() || password.isEmpty() || curso.isEmpty()) {

                        Toast.makeText(getApplicationContext(), "Insira os dados todos!!", Toast.LENGTH_LONG).show();
                    } else {

                        url = "http://10.2.218.6/SalasQR/registar.php";//btw colocar o ipv4 em que tlmv está ligado e desligar cago Ethernet do pc ou então usam hotspot
                        parametros = "numero_aluno=" + numero + "&nome=" + nome + "&email=" + email + "&curso=" + curso + "&senha=" + password;

                        new SolicitaDados().execute(url);

                    }
                }
            }

        });


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent voltaLogin = new Intent(RegistoActivity.this, LoginActivity.class);
                startActivity(voltaLogin);
            }


        });


    }

    @Override
    public void onBackPressed() {
        finish();
    }


    private class SolicitaDados extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return Conexao.posDados(urls[0], parametros);
        }

        @Override
        protected void onPostExecute(String resultado) {

            if (resultado.contains("registo_ok")) {
                Toast.makeText(getApplicationContext(), "Dados Inseridos com sucesso!!!", Toast.LENGTH_LONG).show();
                Intent abreInicio = new Intent(RegistoActivity.this, LoginActivity.class);
                startActivity(abreInicio);
            }
            if (resultado.contains("registo_erro")) {
                Toast.makeText(getApplicationContext(), "Insira os dados corretos!!!", Toast.LENGTH_LONG).show();
            }
        }
    }
}