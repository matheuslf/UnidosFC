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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private TextInputLayout confSenha;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nome = findViewById(R.id.nome);
        usuario = findViewById(R.id.usuario);
        email = findViewById(R.id.email);
        telefone = findViewById(R.id.telefone);
        senha = findViewById(R.id.senha);
        confSenha = findViewById(R.id.confirmacaoSenha);

        cadastrar = findViewById(R.id.cadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validaEmail() && validaUsuario() && validaSenha()&&validaConfirmaSenha() &&validaNome() && validaTelefone()) {


                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("usuarios");
                Query checkUser = reference.orderByChild("usuario").equalTo(usuario.getEditText().getText().toString());
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
                            new SweetAlertDialog(RegistroActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Erro")
                                    .setContentText("Falha ao consultar o usuário!")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismissWithAnimation();
                                        }
                                    })
                                    .show();
                        }
                    }
                });
            }
            }
        });
    }

    private boolean validaUsuario() {
        final String valor = usuario.getEditText().getText().toString();
        final String semEspaco = "\\A\\w{4,20}\\z";
        usuario.setError(null);
        usuario.setErrorEnabled(false);

        if (valor.isEmpty()) {
            usuario.setErrorEnabled(true);
            usuario.setError("Campo usuário não pode ser vazio");
            return false;
        }
        else if (valor.length() >= 15) {
            usuario.setErrorEnabled(true);
            usuario.setError("Campo usuário muito longo");
            return false;
        }
        else if (!valor.matches(semEspaco)) {
            usuario.setErrorEnabled(true);
            usuario.setError("Espaços não é permitido para o campo usuário");
            return false;
        }
        else {
            usuario.setErrorEnabled(false);
            usuario.setError(null);
            return true;
        }
    }

    private boolean validaSenha() {
        final String valor = senha.getEditText().getText().toString();
        senha.setError(null);
        senha.setErrorEnabled(false);

        if (valor.isEmpty()) {
            senha.setErrorEnabled(true);
            senha.setError("Campo senha não pode ser vazio");
            return false;
        }
        else if (valor.length() < 6) {
            senha.setErrorEnabled(true);
            senha.setError("Campo senha deve ter pelo menos 6 caracteres");
            return false;
        }

        else {
            senha.setErrorEnabled(false);
            senha.setError(null);
            return true;
        }
    }

    private boolean validaConfirmaSenha() {
        final String valor = confSenha.getEditText().getText().toString();
        final String valorSenha = senha.getEditText().getText().toString();
        confSenha.setError(null);
        confSenha.setErrorEnabled(false);

        if (valor.isEmpty()) {
            confSenha.setErrorEnabled(true);
            confSenha.setError("Campo inputSenha não pode ser vazio");
            return false;
        }
        else if (!valorSenha.equals(valor))
        {
            confSenha.setErrorEnabled(true);
            confSenha.setError("Senhas não coincidem");
            return false;
        }
        else {
            senha.setErrorEnabled(false);
            senha.setError(null);
            return true;
        }
    }

    private boolean validaTelefone() {
        final String valor = telefone.getEditText().getText().toString();
        telefone.setError(null);
        telefone.setErrorEnabled(false);

        if (valor.isEmpty()) {
            telefone.setErrorEnabled(true);
            telefone.setError("Campo telefone não pode ser vazio");
            return false;
        }
        else if (valor.length() != 11) {
            telefone.setErrorEnabled(true);
            telefone.setError("Campo telefone deve ser composto de (00)00000-0000");
            return false;
        }
        else {
            telefone.setErrorEnabled(false);
            telefone.setError(null);
            return true;
        }
    }
    private boolean validaNome() {
        final String valor = nome.getEditText().getText().toString();
        nome.setError(null);
        nome.setErrorEnabled(false);

        if (valor.isEmpty()) {
            nome.setErrorEnabled(true);
            nome.setError("Campo nome não pode ser vazio");
            return false;
        }
        else if (valor.length() < 11) {
            nome.setErrorEnabled(true);
            nome.setError("Por favor, digite seu nome completo");
            return false;
        }
        else {
            nome.setErrorEnabled(false);
            nome.setError(null);
            return true;
        }
    }

    private boolean validaEmail() {
        final String valor = email.getEditText().getText().toString();
        email.setError(null);
        email.setErrorEnabled(false);
        Matcher matcher = pattern.matcher(valor);

        if (valor.isEmpty()) {
            email.setErrorEnabled(true);
            email.setError("Campo nome não pode ser vazio");
            return false;
        }
        else if (!matcher.matches()) {

            email.setErrorEnabled(true);
            email.setError("Digite um e-mail válido");
            return false;
        }
        else {
            email.setErrorEnabled(false);
            email.setError(null);
            return true;
        }
    }
}
