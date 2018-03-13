package com.example.lmigu.salasqr;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;

public class Problemas extends AppCompatActivity {

    EditText Assunto, Descricao;
    String assunto, descricao;
    Button send;
    boolean sentEmail = false;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problemas);

        Assunto = findViewById(R.id.Assunto);
        Descricao = findViewById(R.id.Descricao);
        send = findViewById(R.id.Send);
        //sentEmail = false;

        Assunto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){

                //if(Subject.getText().equals("Assunto")) {
                Assunto.setText("");
                //}
            }
        });

        send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){

                //if(Subject.getText().equals("Assunto")) {
                //}
                assunto = Assunto.getText().toString();
                descricao = Descricao.getText().toString();
                sendEmail(assunto, descricao);

            }
        });

    }

    protected void sendEmail(String assunto, String descricao) {
        String uriText =
                "mailto:miguel.alex.andrade@gmail.com" +
                        "?subject=" + Uri.encode(assunto) +
                        "&body=" + Uri.encode(descricao);

        Uri uri = Uri.parse(uriText);

        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setData(uri);
        startActivity(Intent.createChooser(sendIntent, "Send email"));

    }

    public void changeActivity(Activity activity){
        Class myActivity = activity.getClass();
        startActivity(new Intent(Problemas.this, myActivity));
    }
}