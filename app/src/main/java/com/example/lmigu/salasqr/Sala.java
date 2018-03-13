package com.example.lmigu.salasqr;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import android.util.*;




public class Sala extends AppCompatActivity {

    String url ="";
    String parametros = "";
    TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala);

        // requestQueue = Volley.newRequestQueue(this);

        url = "http://10.2.218.6/SalasQR/lugar.php";

        new SolicitaDados().execute(url);

        /*JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray array = response.getJSONArray("aluno_numero_conta");

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject aluno = array.getJSONObject(i);
                                String aluno_numero_conta = aluno.getString("aluno_numero_conta");
                                Log.d("teste",aluno_numero_conta);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                     finish();

                    }
                }

        );

        requestQueue.add(obj);




       // String result = null;


*/



    }
    public class SolicitaDados extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... urls ) {

            return Conexao.posDados(urls[0],parametros);
        }

        @Override
        protected void onPostExecute(String resultado) {

            String[] parts = resultado.split(",");

            String lugar1 = parts[0];
            String lugar2 = parts[1];
            String lugar3 = parts[2];
            String lugar4 = parts[3];
            String lugar5 = parts[4];
            String lugar6 = parts[5];
            String lugar7 = parts[6];
            String lugar8 = parts[7];
            String lugar9 = parts[8];
            String lugar10 = parts[9];
            

            //lugar 101

            texto = findViewById(R.id.lugar_101);

            if (lugar1.equals("0")) {
                texto.setBackgroundColor(Color.parseColor("#00ff00"));
            }
            if(lugar1.equals("1")){
                texto.setBackgroundColor(Color.parseColor("#ff0000"));
            }

            //lugar 102

            texto = findViewById(R.id.lugar_102);

            if (lugar2.equals("0")) {
                texto.setBackgroundColor(Color.parseColor("#00ff00"));
            }
            if(lugar2.equals("1")){
                texto.setBackgroundColor(Color.parseColor("#ff0000"));
            }

            //lugar 103

            texto = findViewById(R.id.lugar_103);

            if (lugar3.equals("0")) {
                texto.setBackgroundColor(Color.parseColor("#00ff00"));
            }
            if(lugar3.equals("1")){
                texto.setBackgroundColor(Color.parseColor("#ff0000"));
            }

            //lugar 104

            texto = findViewById(R.id.lugar_104);

            if (lugar4.equals("0")) {
                texto.setBackgroundColor(Color.parseColor("#00ff00"));
            }
            if(lugar4.equals("1")){
                texto.setBackgroundColor(Color.parseColor("#ff0000"));
            }

            //lugar 105

            texto = findViewById(R.id.lugar_105);

            if (lugar5.equals("0")) {
                texto.setBackgroundColor(Color.parseColor("#00ff00"));
            }
            if(lugar5.equals("1")){
                texto.setBackgroundColor(Color.parseColor("#ff0000"));
            }

            //lugar 106

            texto = findViewById(R.id.lugar_106);

            if (lugar6.equals("0")) {
                texto.setBackgroundColor(Color.parseColor("#00ff00"));
            }
            if(lugar6.equals("1")){
                texto.setBackgroundColor(Color.parseColor("#ff0000"));
            }

            //lugar 107

            texto = findViewById(R.id.lugar_107);

            if (lugar7.equals("0")) {
                texto.setBackgroundColor(Color.parseColor("#00ff00"));
            }
            if(lugar7.equals("1")){
                texto.setBackgroundColor(Color.parseColor("#ff0000"));
            }

            //lugar 108

            texto = findViewById(R.id.lugar_108);

            if (lugar8.equals("0")) {
                texto.setBackgroundColor(Color.parseColor("#00ff00"));
            }
            if(lugar8.equals("1")){
                texto.setBackgroundColor(Color.parseColor("#ff0000"));
            }

            //lugar 109

            texto = findViewById(R.id.lugar_109);

            if (lugar9.equals("0")) {
                texto.setBackgroundColor(Color.parseColor("#00ff00"));
            }
            if(lugar9.equals("1")){
                texto.setBackgroundColor(Color.parseColor("#ff0000"));
            }

            //lugar 110

            texto = findViewById(R.id.lugar_110);

            if (lugar10.equals("0")) {
                texto.setBackgroundColor(Color.parseColor("#00ff00"));
            }
            if(lugar10.equals("1")){
                texto.setBackgroundColor(Color.parseColor("#ff0000"));
            }
        }
    }

}




