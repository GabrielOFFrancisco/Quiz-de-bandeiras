package com.example.quizdasbandeiras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class TelaQuestoes extends AppCompatActivity implements View.OnClickListener{

    ImageView imgBandeira, imgPessoaQuestoes;
    Button btnResposta;
    RadioGroup rdgQuestoes;
    RadioButton rdbA, rdbB, rdbC, rdbD;
    TextView txtNomeDigitado, txtRmDigitado;
    int score = 0;
    int questaoAtualIndex = 0;
    String respostaSelecionada;
    private String nome;
    private String rm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_questoes);

        rdgQuestoes = findViewById(R.id.rdgQuestoes);
        rdbA = findViewById(R.id.rdbA);
        rdbB = findViewById(R.id.rdbB);
        rdbC = findViewById(R.id.rdbC);
        rdbD = findViewById(R.id.rdbD);
        btnResposta = findViewById(R.id.btnResposta);
        imgBandeira = findViewById(R.id.imgBandeira);
        imgPessoaQuestoes = findViewById(R.id.imgPessoaQuestoes);

        byte[] imagemCapturada = getIntent().getByteArrayExtra("imagemCapturada");
        if (imagemCapturada != null) {
            // Converter o byte[] em um Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagemCapturada, 0, imagemCapturada.length);

            // Exibir o Bitmap na ImageView "imgPessoaQuestoes"
            imgPessoaQuestoes.setImageBitmap(bitmap);
        }

        Intent intent = getIntent();
        nome = intent.getStringExtra("nome");
        rm = intent.getStringExtra("rm");

        txtNomeDigitado = findViewById(R.id.txtNomeDigitado);
        txtRmDigitado = findViewById(R.id.txtRmDigitado);

        txtNomeDigitado.setText(nome);
        txtRmDigitado.setText(rm);

        btnResposta.setOnClickListener(this);

        carregarQuestao();

        rdgQuestoes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                btnResposta.setEnabled(true);
            }
        });

        rdbA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                respostaSelecionada = rdbA.getText().toString();
                btnResposta.setEnabled(true);
            }
        });

        rdbB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                respostaSelecionada = rdbB.getText().toString();
                btnResposta.setEnabled(true);
            }
        });

        rdbC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                respostaSelecionada = rdbC.getText().toString();
                btnResposta.setEnabled(true);
            }
        });

        rdbD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                respostaSelecionada = rdbD.getText().toString();
                btnResposta.setEnabled(true);
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnResposta) {
            if (respostaSelecionada != null && respostaSelecionada.equals(PerguntasRespostas.respostaCorreta[questaoAtualIndex])) {
                score++;
            }

            questaoAtualIndex++;
            carregarQuestao();

            btnResposta.setEnabled(false);

            Log.d("Debug", "Valor de score antes do envio: " + score);
        }
    }

    void carregarQuestao(){
        rdgQuestoes.clearCheck();

        if(questaoAtualIndex == 10){
            Intent intent = new Intent(TelaQuestoes.this, TelaFinal.class);
            intent.putExtra("nome", nome);
            intent.putExtra("rm", rm);
            intent.putExtra("score", score);
            intent.putExtra("imagemCapturada", getIntent().getByteArrayExtra("imagemCapturada"));

            startActivity(intent);

            finish();
        }

        imgBandeira.setImageResource(PerguntasRespostas.imagens[questaoAtualIndex]);
        rdbA.setText(PerguntasRespostas.escolha[questaoAtualIndex][0]);
        rdbB.setText(PerguntasRespostas.escolha[questaoAtualIndex][1]);
        rdbC.setText(PerguntasRespostas.escolha[questaoAtualIndex][2]);
        rdbD.setText(PerguntasRespostas.escolha[questaoAtualIndex][3]);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}