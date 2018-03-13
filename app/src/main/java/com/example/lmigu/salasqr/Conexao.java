package com.example.lmigu.salasqr;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.DataOutputStream;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Conexao {

    public static String posDados(String urlUtilizador, String parametroUtilizador)
    {
        OkHttpClient client = new OkHttpClient();

        Request.Builder builder = new Request.Builder();

        builder.url(urlUtilizador);

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

        RequestBody body = RequestBody.create(mediaType,parametroUtilizador);

        builder.post(body);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();

            return response.body().string();
        }catch(IOException erro) {
            return null;
        }
    }
}

