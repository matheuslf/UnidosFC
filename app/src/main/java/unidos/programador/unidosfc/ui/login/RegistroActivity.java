package unidos.programador.unidosfc.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import unidos.programador.unidosfc.MainActivity;
import unidos.programador.unidosfc.R;
import unidos.programador.unidosfc.database.UsuarioModel;

public class RegistroActivity extends AppCompatActivity {

    private Button cadastrar;
    private TextInputLayout nome;
    private TextInputLayout usuario;
    private TextInputLayout email;
    private TextInputLayout telefone;
    private TextInputLayout senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nome = findViewById(R.id.nome);
        usuario = findViewById(R.id.usuario);
        email = findViewById(R.id.email);
        telefone = findViewById(R.id.telefone);
        senha = findViewById(R.id.senha);

        cadastrar = findViewById(R.id.cadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("usuarios");
                Query checkUser = reference.orderByChild("usuarios").equalTo(usuario.getEditText().toString());
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            new SweetAlertDialog(RegistroActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Erro")
                                    .setContentText("Já existe um usuário cadastrado com este nome!")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismissWithAnimation();
                                        }
                                    })
                                    .show();
                        } else {

                            // Faz o cadastro do usuário.
                            FirebaseDatabase root = FirebaseDatabase.getInstance();
                            DatabaseReference reference = root.getReference("usuarios");
                            UsuarioModel user = new UsuarioModel
                                    (
                                            new Long(telefone.getEditText().getText().toString()),
                                            nome.getEditText().getText().toString(),
                                            usuario.getEditText().getText().toString(),
                                            email.getEditText().getText().toString(),
                                            senha.getEditText().getText().toString()
                                    );
                            reference.child(usuario.getEditText().getText().toString()).setValue(user);

                            // Mostra a mensagem de sucesso.
                            new SweetAlertDialog(RegistroActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Informação")
                                    .setContentText("Usuário cadastrado !")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismissWithAnimation();
                                            finish();
                                        }
                                    })
                                    .show();
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
    }
}
