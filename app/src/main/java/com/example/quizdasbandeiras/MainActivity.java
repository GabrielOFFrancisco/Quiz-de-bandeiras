package com.example.quizdasbandeiras;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    Button btnIniciar, btnSair;
    EditText edtNome, edtRm;
    ImageView imgPessoaInicio;
    Bitmap imagemCapturada;
    TextView txtViewFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIniciar = findViewById(R.id.btnIniciar);
        btnSair = findViewById(R.id.btnSair);
        edtNome = findViewById(R.id.edtNome);
        edtRm = findViewById(R.id.edtRm);
        txtViewFoto = findViewById(R.id.textView6);

        imgPessoaInicio = findViewById(R.id.imgPessoaInicio);

        final TextView txtNomeRm = findViewById(R.id.txtNomeRm);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                        Manifest.permission.CAMERA
            },100);
        }

        imgPessoaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String nome = edtNome.getText().toString();
                String rm = edtRm.getText().toString();

                if (!nome.isEmpty() && !rm.isEmpty()) {
                    btnIniciar.setEnabled(true);
                } else {
                    btnIniciar.setEnabled(false);
                }

                txtNomeRm.setText("Nome: "+nome+"\nRGM: "+rm);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };

        edtNome.addTextChangedListener(textWatcher);
        edtRm.addTextChangedListener(textWatcher);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imagemCapturada = (Bitmap) data.getExtras().get("data");
            imgPessoaInicio.setImageBitmap(bitmap);
            txtViewFoto.setText("Foto adicionada com sucesso!");
        }
    }

    public void Inicio(View v){

        String nome = edtNome.getText().toString();
        String rm = edtRm.getText().toString();

        if (!nome.isEmpty() && !rm.isEmpty()) {
            Intent intent = new Intent(MainActivity.this, TelaQuestoes.class);

            if (imagemCapturada != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imagemCapturada.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("imagemCapturada", byteArray);
            }

            intent.putExtra("nome", nome);
            intent.putExtra("rm", rm);
            startActivity(intent);
            finish();
        }
    }

    public void Sair(View v){
        finish();
    }
}