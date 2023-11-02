package com.example.quizdasbandeiras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class TelaFinal extends AppCompatActivity {

    TextView edtNomeFinal, edtRmFinal;
    TextView txtPontos;
    Button btnReiniciar, btnTelaInicial;
    int score;
    String nome, rm;
    ImageView imgPessoaFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_final);

        edtNomeFinal = findViewById(R.id.edtNomeFinal);
        edtRmFinal = findViewById(R.id.edtRmFinal);
        txtPontos = findViewById(R.id.txtPontos);
        btnTelaInicial = findViewById(R.id.btnTelaInicial);
        btnReiniciar = findViewById(R.id.btnReiniciar);
        imgPessoaFinal = findViewById(R.id.imgPessoaFinal);

        byte[] imagemCapturadaByteArray = getIntent().getByteArrayExtra("imagemCapturada");
        if (imagemCapturadaByteArray != null) {
            // Converter o byte array em um Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagemCapturadaByteArray, 0, imagemCapturadaByteArray.length);

            // Exibir o Bitmap na ImageView "imgPessoaFinal"
            imgPessoaFinal.setImageBitmap(bitmap);
        }

        Intent intent = getIntent();
        nome = intent.getStringExtra("nome");
        rm = intent.getStringExtra("rm");
        score = intent.getIntExtra("score", 0);

        edtNomeFinal.setText(nome);
        edtRmFinal.setText(rm);
        txtPontos.setText(String.valueOf(score));
    }

    public void reiniciar(View v){
        Intent intent = new Intent(TelaFinal.this, TelaQuestoes.class);
        intent.putExtra("nome", nome);
        intent.putExtra("rm", rm);
        intent.putExtra("imagemCapturada", getIntent().getByteArrayExtra("imagemCapturada"));

        score = 0;
        startActivity(intent);

        finish();
    }

    public void telaInicial(View v){
        Intent intent = new Intent(TelaFinal.this, MainActivity.class);
        score = 0;
        nome = "";
        rm = "";
        startActivity(intent);

        finish();
    }
}