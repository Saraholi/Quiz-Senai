package br.com.senaijandira.quizsenai;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    LinearLayout layout;
    TextView txtPergunta, txtRelogio;
    Button btnResposta1, btnResposta2;

    Integer qtdAcertos=0, qtdErros=0;

    //player para tocar a música
    MediaPlayer mediaPlayer;

    int indexPergunta;

    String[] perguntas = {
            "Onde se passa a série Breaking Bad?",
            "Qual e o personagem principal da série?",
            "Quantas temporadas tem a série?",
            "Qual era o nome da lanchonete de Gustavo Fring?"
    };

    String[][] respostas = {
             { "Albuquerque", "Los Angeles" },
             { "Saul Goodman", "Walter White" },
             { "5", "3" },
             { "Los Pollos hermanos", "Mc Donalds" },
    };

    int[]  gabarito = { 0, 1, 0, 0 };

    private void iniciarJogo(){

        qtdErros = 0;
        qtdAcertos = 0;
        indexPergunta = 0;
        
        txtPergunta.setText(perguntas[indexPergunta]);

        btnResposta1.setText(respostas[indexPergunta][0]);
        btnResposta1.setTag(0);

        btnResposta2.setText(respostas[indexPergunta][1]);
        btnResposta2.setTag(1);

        btnResposta1.setOnClickListener(clickBtnResposta);
        btnResposta2.setOnClickListener(clickBtnResposta);

        //iniciar a contagem do relógio
        timer.start();

        //iniciar a música
        mediaPlayer = MediaPlayer.create(this, R.raw.countdown);
        mediaPlayer.start();

    }



    //Código do relógio, contar 30 segundos.
    CountDownTimer timer = new CountDownTimer(30000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {

            txtRelogio.setText(""+ millisUntilFinished / 1000);
        }

        @Override
        public void onFinish() {

            alert("Tempo acabou", "Você demorou de mais \uD83D\uDC0C\uD83D\uDC0C ");
        }
    };



    View.OnClickListener clickBtnResposta = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int respostaUsuario = (int)v.getTag();

            if(respostaUsuario == gabarito[indexPergunta]){
                qtdAcertos++;
                alert("Parabéns", "Resposta correta!");
            }else{
                qtdErros++;
                alert("Sorry", "Resposta errada!");
            }

            //parar o relógio
            timer.cancel();

            //parar a música
            mediaPlayer.stop();
        }
    };


    public void alert(String titulo, String msg){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(titulo);
        alert.setMessage(msg);

        //não pode fechar o alert clicando fora da caixa
        alert.setCancelable(false);

        alert.setPositiveButton("Proximo", new Dialog.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                proximaPergunta();
            }

        });

        alert.create().show();
    }

    public void gameOver(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Game Over");
        alert.setMessage("QtdAcertos: "+
                qtdAcertos.toString()
                +" \nQtdErros: "+
                qtdErros.toString());

        alert.setNegativeButton("sair", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        alert.setPositiveButton("reiniciar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                iniciarJogo();
            }
        });

        alert.create().show();
    }

    public void proximaPergunta(){

        if(indexPergunta == perguntas.length-1) {
            //jogo acabou
            gameOver();
            return;
        }

        indexPergunta++;
        txtPergunta.setText(perguntas[indexPergunta]);

        btnResposta1.setText(respostas[indexPergunta][0]);
        btnResposta2.setText(respostas[indexPergunta][1]);

        //Reiniciar o tempo
        timer.start();

        //Inicar a música novamente
        mediaPlayer = MediaPlayer.create(this, R.raw.countdown);
        mediaPlayer.start();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtPergunta = findViewById(R.id.txtPergunta);

        btnResposta1 = findViewById(R.id.btn1);
        btnResposta2 = findViewById(R.id.btn2);

        txtRelogio = findViewById(R.id.txtRelogio);

        iniciarJogo();

    }
}
