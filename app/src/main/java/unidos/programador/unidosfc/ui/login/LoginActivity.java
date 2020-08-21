package unidos.programador.unidosfc.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import unidos.programador.unidosfc.R;

public class LoginActivity extends AppCompatActivity {

    private Button novoUsuario, entrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        entrar = findViewById(R.id.entrar);
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("usuario");
                Query checkUser = reference.orderByChild("usuario").equalTo("matheuslf");
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        if (databaseError.getMessage() != null) {

                        }
                    }
                });
            }
        });

        novoUsuario = findViewById(R.id.novoUsuario);
        novoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistroActivity.class));
            }
        });

    }
}
