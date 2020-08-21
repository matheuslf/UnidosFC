package unidos.programador.unidosfc.ui.login;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import unidos.programador.unidosfc.MainActivity;
import unidos.programador.unidosfc.R;

public class SplashActivity extends AppCompatActivity {

    Animation topAnim, bottomAnim;
    ImageView logo;
    TextView texto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        logo = findViewById(R.id.logo);
        texto = findViewById(R.id.texto);

        logo.setAnimation(topAnim);
        texto.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(logo, "logo");
                pairs[1] = new Pair<View, String>(texto, "texto");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, pairs);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        }, 2000);
    }
}
