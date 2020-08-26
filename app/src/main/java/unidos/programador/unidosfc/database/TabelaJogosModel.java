package unidos.programador.unidosfc.database;

import java.sql.Time;
import java.util.Date;

public class TabelaJogosModel {
    private boolean emCasa;
    private String local;
    private String time;
    private String timeAdversario;
    private Date dtJogo;
    private Time hrJogo;

    public TabelaJogosModel() {

    }

    public TabelaJogosModel(boolean emCasa, String local, String time, String timeAdversario, Date dtJogo, Time hrJogo) {
        this.emCasa = emCasa;
        this.local = local;
        this.time = time;
        this.timeAdversario = timeAdversario;
        this.dtJogo = dtJogo;
        this.hrJogo = hrJogo;
    }

    public Date getDtJogo() {
        return dtJogo;
    }

    public Time getHrJogo() {
        return hrJogo;
    }

    public void setHrJogo(Time hrJogo) {
        this.hrJogo = hrJogo;
    }

    public boolean isEmCasa() {
        return emCasa;
    }

    public void setEmCasa(boolean emCasa) {
        this.emCasa = emCasa;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimeAdversario() {
        return timeAdversario;
    }

    public void setTimeAdversario(String timeAdversario) {
        this.timeAdversario = timeAdversario;
    }

}
