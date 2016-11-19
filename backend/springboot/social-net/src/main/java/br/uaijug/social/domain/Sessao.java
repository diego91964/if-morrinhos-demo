package br.uaijug.social.domain;

/**
 * Created by diego on 18/11/16.
 */
public class Sessao implements  Neo4jBoltInsertable {

    private String token;
    private String emailUsuario;

    public Sessao(String token, String emailUsuario) {
        this.token = token;
        this.emailUsuario = emailUsuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    @Override
    public String toEntityString() {
        return null;
    }
}
