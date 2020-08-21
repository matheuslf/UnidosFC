package unidos.programador.unidosfc.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import unidos.programador.unidosfc.R;
import unidos.programador.unidosfc.database.UsuarioModel;

public class RegistroActivity extends AppCompatActivity {

    private Button cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        cadastrar = findViewById(R.id.cadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase root = FirebaseDatabase.getInstance();
                DatabaseReference reference = root.getReference("tb_usuarios");

                UsuarioModel usuario = new UsuarioModel
                                        (
                                            48991645854L,
                                            "Matheus Leandro Ferreira",
                                            "matheuslf",
                                            "matheusleandroferreira@gmail.com",
                                            "bombom"
                                        );

                reference.setValue(usuario);
            }
        });
    }
}
