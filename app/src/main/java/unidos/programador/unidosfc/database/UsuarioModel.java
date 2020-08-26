package unidos.programador.unidosfc.database;

public class UsuarioModel {

    private long id;
    private String nome;
    private String usuario;
    private String email;
    private String senha;
    private boolean adm;

    public UsuarioModel() {

    }

    public UsuarioModel(final long id, final String nome, final String usuario, final String email, final String senha, final boolean adm) {
        this.id = id;
        this.nome = nome;
        this.usuario = usuario;
        this.email = email;
        this.senha = senha;
        this.adm = adm;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAdm() {
        return adm;
    }

    public void setAdm(boolean adm) {
        this.adm = adm;
    }
}
