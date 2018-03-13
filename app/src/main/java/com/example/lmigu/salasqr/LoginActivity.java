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


public class LoginActivity extends AppCompatActivity {

    EditText editNumeroAluno,editPassword;
    Button btnLogin;
    TextView registo;

    String numero="";

    String url ="";
    String parametros ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editNumeroAluno = (EditText)findViewById(R.id.editTextNumeroAluno);
        editPassword = (EditText)findViewById(R.id.editTextPassword);
        btnLogin = (Button)findViewById(R.id.buttonLogin);
        registo = (TextView)findViewById(R.id.txtReg);

        registo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                Intent abreRegisto = new Intent(LoginActivity.this, RegistoActivity.class);
                startActivityForResult(abreRegisto,1);
                //startActivity(abreRegisto);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if(networkInfo != null && networkInfo.isConnected()) {

                    numero = editNumeroAluno.getText().toString();
                    String senha = editPassword.getText().toString();



                    if(numero.isEmpty() || senha.isEmpty())
                    {
                        Toast.makeText(getApplicationContext(),"Nenhum campo pode estar vazio",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        url = "http://10.2.218.6/SalasQR/logar.php";//btw colocar o ipv4 em que tlmv está ligado e desligar cago Ethernet do pc ou então usam hotspot
                        parametros = "numero_aluno="+numero+"&senha="+senha;

                        new SolicitaDados().execute(url);
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Nenhuma conexão detetada",Toast.LENGTH_LONG).show();
                }
            }

        });

    }
    private class SolicitaDados extends AsyncTask <String, Void, String>
    {
        @Override
        protected String doInBackground(String... urls ) {

            return Conexao.posDados(urls[0],parametros);
        }

        @Override
        protected void onPostExecute(String resultado) {

            if(resultado.contains("login_ok")) {
                Intent abreInicio = new Intent(LoginActivity.this, MenuPrincipal.class);
                abreInicio.putExtra("numero",numero);
                startActivity(abreInicio);
            }
            if(resultado.contains("login_erro")){
                Toast.makeText(getApplicationContext(),"Insira os dados corretos!!!",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
