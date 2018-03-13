package com.example.lmigu.salasqr;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.Result;

import java.io.IOException;


public class MenuActivity extends AppCompatActivity {

    SurfaceView cameraPreview;
    TextView textResult;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;
    String lugar;
    String numeroAluno="";

    String url ="";
    String parametros ="";


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);////////////////////////////
        Intent intent = getIntent();
        //serve para +assar o numero do login
        Bundle b = intent.getExtras();
        if(b!=null) {
            numeroAluno = (String) b.get("numero");
        }

        cameraPreview = (SurfaceView) findViewById(R.id.cameraPreview);
        textResult = (TextView) findViewById(R.id.textResult);

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();
        //Add Event
        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //Request permission
                    ActivityCompat.requestPermissions(MenuActivity.this,
                            new String[]{Manifest.permission.CAMERA},RequestCameraPermissionID);
                    return;
                }
                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();

            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();
                if(qrcodes.size() != 0)
                {
                    textResult.post(new Runnable() {
                        @Override
                        public void run() {
                            //Create vibrate
                            Vibrator vibrator = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(500);
                            textResult.setText(qrcodes.valueAt(0).displayValue);
                            lugar = textResult.getText().toString();

                        }
                    });
                    //finish();
                    boolean teste = true;
                    int a=108;
                    url = "http://10.2.218.6/SalasQR/guarda.php";
                    parametros = "idLugar="+lugar+"&idAluno="+numeroAluno;

                    new SolicitaDados().execute(url);

                    Intent intent = new Intent(MenuActivity.this, MenuPrincipal.class);
                    intent.putExtra("teste", teste);
                    intent.putExtra("numero",numeroAluno);
                    startActivity(intent);
                    //finish();
                }
            }
        });
    }
    public class SolicitaDados extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... urls ) {

            return Conexao.posDados(urls[0],parametros);
        }

        @Override
        protected void onPostExecute(String resultado) {

            if(resultado.equals("0")) {
                Toast.makeText(getApplicationContext(),"Lugar marcado "+lugar,Toast.LENGTH_SHORT).show();
                finish();

            }
            if(resultado.equals("1")){
                Toast.makeText(getApplicationContext(),"erro ao marcar lugar",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}