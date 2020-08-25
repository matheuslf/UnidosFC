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
    private TextInputLayout inputUsuario, inputSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputUsuario    = findViewById(R.id.usuario);
        inputSenha      = findViewById(R.id.senha);

        entrar = findViewById(R.id.entrar);
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validaUsuario() && validaSenha()) {

                    // Captura a informação digitada.
                    final String lsUsuario = inputUsuario.getEditText().getText().toString();
                    final String lsSenha = inputSenha.getEditText().getText().toString();

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("usuarios");
                    Query checkUser = reference.orderByChild("usuario").equalTo(lsUsuario);
                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                final String senhaDB = dataSnapshot.child(inputUsuario.getEditText().getText().toString()).child("senha").getValue(String.class);
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

    private boolean validaUsuario() {
        final String valor = inputUsuario.getEditText().getText().toString();
        final String semEspaco = "\\A\\w{4,20}\\z";
        inputUsuario.setError(null);
        inputUsuario.setErrorEnabled(false);

        if (valor.isEmpty()) {
            inputUsuario.setErrorEnabled(true);
            inputUsuario.setError("Campo usuário não pode ser vazio");
            return false;
        }
        else if (valor.length() >= 15) {
            inputUsuario.setErrorEnabled(true);
            inputUsuario.setError("Campo usuário muito longo");
            return false;
        }
        else if (!valor.matches(semEspaco)) {
            inputUsuario.setErrorEnabled(true);
            inputUsuario.setError("Espaços não é permitido para o campo usuário");
            return false;
        }
        else {
            inputUsuario.setErrorEnabled(false);
            inputUsuario.setError(null);
            return true;
        }
    }

    private boolean validaSenha() {
        final String valor = inputSenha.getEditText().getText().toString();
        inputSenha.setError(null);
        inputSenha.setErrorEnabled(false);

        if (valor.isEmpty()) {
            inputSenha.setErrorEnabled(true);
            inputSenha.setError("Campo inputSenha não pode ser vazio");
            return false;
        }
        else {
            inputSenha.setErrorEnabled(false);
            inputSenha.setError(null);
            return true;
        }
    }
}
