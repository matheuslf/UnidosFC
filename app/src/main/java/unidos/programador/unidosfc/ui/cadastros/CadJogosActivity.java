package unidos.programador.unidosfc.ui.cadastros;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import unidos.programador.unidosfc.R;
import unidos.programador.unidosfc.database.TabelaJogosModel;

public class CadJogosActivity extends AppCompatActivity {

    private Button cadastrarButton;
    private TextInputLayout dtjogo;
    private TextInputLayout hrjogo;
    private CheckBox emCasa;
    private TextInputLayout time;
    private TextInputLayout timeAdversario;
    private TextInputLayout local;
    Format formatter = new SimpleDateFormat("dd/MM/yyyy");

    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        dtjogo.getEditText().setText(sdf.format(myCalendar.getTime()));
    }

    public void onClick(View v) {
        new DatePickerDialog(CadJogosActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_jogos);

        dtjogo = findViewById(R.id.dtjogo);
        hrjogo = findViewById(R.id.hrjogo);
        emCasa = findViewById(R.id.cbxEmCasa);
        time = findViewById(R.id.Time);
        timeAdversario = findViewById(R.id.TimeAdversario);
        local = findViewById(R.id.LocalPartida);
        cadastrarButton = findViewById(R.id.cadastrarPartida);

        cadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("jogos");
                Query checkUser = reference.orderByChild("dt_jogo").equalTo(dtjogo.getEditText().getText().toString());
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            new SweetAlertDialog(CadJogosActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Erro")
                                    .setContentText("Já existe um jogo cadastrado com esta data!")
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
                            DatabaseReference reference = root.getReference("jogos");
                            TabelaJogosModel jogos = new TabelaJogosModel
                                    (
                                            emCasa.isChecked(),
                                            local.getEditText().getText().toString(),
                                            time.getEditText().getText().toString(),
                                            timeAdversario.getEditText().getText().toString(),
                                            new Date(dtjogo.getEditText().getText().toString()),
                                            Time.valueOf(hrjogo.getEditText().getText().toString())
                                    );
                            reference.child(dtjogo.getEditText().getText().toString()).setValue(jogos);

                            // Mostra a mensagem de sucesso.
                            new SweetAlertDialog(CadJogosActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Informação")
                                    .setContentText("Jogo cadastrado com sucesso !")
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
                            new SweetAlertDialog(CadJogosActivity.this, SweetAlertDialog.ERROR_TYPE)
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
        });
    }
}
