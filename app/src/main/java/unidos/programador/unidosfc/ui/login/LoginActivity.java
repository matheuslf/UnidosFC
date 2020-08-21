package unidos.programador.unidosfc.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import unidos.programador.unidosfc.MainActivity;
import unidos.programador.unidosfc.R;

public class LoginActivity extends AppCompatActivity {

    private Button novoUsuario, entrar;
    private TextInputLayout usuario, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = findViewById(R.id.usuario);
        senha = findViewById(R.id.senha);

        entrar = findViewById(R.id.entrar);
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Limpa o erro do campo.
                usuario.setError(null);
                usuario.setErrorEnabled(false);
                senha.setError(null);
                senha.setErrorEnabled(false);

                // Captura a informação digitada.
                final String lsUsuario = usuario.getEditText().getText().toString();
                final String lsSenha = senha.getEditText().getText().toString();

                // Faz as validações.
                if (lsUsuario.isEmpty()) {
                    usuario.setError("Campo usuário obrigatório");
                    usuario.setErrorEnabled(true);
                }
                else if (lsSenha.isEmpty()) {
                    senha.setError("Campo senha obrigatório");
                    senha.setErrorEnabled(true);
                }
                else {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("usuarios");
                    Query checkUser = reference.orderByChild("usuario").equalTo(lsUsuario);
                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                final String senhaDB = dataSnapshot.child(usuario.getEditText().getText().toString()).child("senha").getValue(String.class);
                                if (lsSenha.equals(senhaDB)) {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            if (databaseError.getMessage() != null) {

                            }
                        }
                    });
                }
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
