package com.example.lmigu.salasqr;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuPrincipal extends AppCompatActivity {

    Button entrada_saida;
    Button sala;
    Button problema;
    Button logout;

    String numeroAluno="";//vem da activity anterior
    String lugar = "";

    String url ="";
    String parametros ="";
    boolean voltou = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Intent intent = getIntent();
        //serve para +assar o numero do login
        Bundle b = intent.getExtras();
         if(b!=null) {
              numeroAluno = (String) b.get("numero");

         }


        final boolean finalResult = intent.getBooleanExtra("teste", false); //acede ao boolean criado na atividade teste
        entrada_saida = findViewById(R.id.Entrada_Saida);
        if(finalResult){//verifica se já foi criada uma atividade teste, e altera o texto do botão, de acordo com o valor
            entrada_saida.setText("Sair");
            voltou = true;


        }
        sala = findViewById(R.id.Ver_Sala);
        problema = findViewById(R.id.Problema);
        logout = findViewById(R.id.Logout);

        entrada_saida.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                if(!finalResult)
                {
                    changeActivity(new MenuActivity());

                }
                else if(finalResult && voltou)
                {
                    url = "http://10.2.218.6/SalasQR/desmarca.php";
                    parametros = "idAluno="+numeroAluno;


                    new SolicitaDados().execute(url);
                }

            }
        });//alterar para a atividade leituraQR ou saida, dependendo se o user deu entrada ou não

        sala.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                changeActivity(new Sala());
            }
        });//alterar para o mapa de salas

        problema.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                changeActivity(new Problemas());
            }
        });

        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                clickLogout();
            }
        });

    }

    public void clickLogout(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:

                        changeActivity(new LoginActivity());//alterar para a atividade de login
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to logout?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    public void changeActivity(Activity activity){//função para mudar de atividade
        Class myActivity = activity.getClass();
        Intent intent = new Intent(MenuPrincipal.this, myActivity);
        intent.putExtra("numero",numeroAluno);
        startActivity(intent);

    }
    public class SolicitaDados extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... urls ) {

            return Conexao.posDados(urls[0],parametros);
        }

        @Override
        protected void onPostExecute(String resultado) {

            if(resultado.equals("1")) {
                Toast.makeText(getApplicationContext(),"Lugar desmarcado "+lugar,Toast.LENGTH_LONG).show();
                finish();

            }

            if(resultado.equals("0")){
                Toast.makeText(getApplicationContext(),"erro ao desmarcar lugar",Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

}