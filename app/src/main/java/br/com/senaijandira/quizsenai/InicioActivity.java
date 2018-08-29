package br.com.senaijandira.quizsenai;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by sn1041520 on 06/08/2018.
 */

public class InicioActivity extends Activity {

    //Player para tocar a musica
    MediaPlayer mediaPlayer;

    ImageView imgQuiz;

    //animação de "shake"
    Animation shakeAnim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Definindo o conteudo na tela, passando o arquivo xml
        setContentView(R.layout.activity_inicio);

        //inicializando o player com a musica do jogo
        mediaPlayer = MediaPlayer.create(this,
                R.raw.game_music);

        mediaPlayer.setLooping(true);//para a musica ficar em looping
        mediaPlayer.start();//iniciar a musica


        imgQuiz = findViewById(R.id.imgQuiz);

        //Animação FadeIn (Entrada)
        Animation fadein = AnimationUtils
                .loadAnimation(this, R.anim.fadein);

        imgQuiz.startAnimation(fadein);


        //Carregando a animação "Shake"
        shakeAnim = AnimationUtils
                .loadAnimation(this, R.anim.shake);


        imgQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Inicializar animação ao clicar na imagem
                imgQuiz.startAnimation(shakeAnim);
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();

        if(!mediaPlayer.isPlaying()) {
            //executar a musica
            mediaPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        //pausar a musica
        mediaPlayer.pause();

    }

    public void iniciarJogo(View v){

        //Parar a musica
        mediaPlayer.stop();

        //Abrir uma nova tela(Activity)
        Intent intent = new Intent(this,
                MainActivity.class);

        startActivity(intent);
        finish();
        //Toast.makeText(this, "Oies", Toast.LENGTH_SHORT)
        //    .show();
    }


}
