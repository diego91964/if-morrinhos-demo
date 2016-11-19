package br.uaijug.social.domain;

import scala.Predef;

/**
 * Created by diego on 15/11/16.
 */
public class User implements Neo4jBoltInsertable {

    private String email;
    private String nome;
    private String urlFoto;
    private String senha;

    public User(String email, String nome, String senha) {
        this.email = email;
        this.nome = nome;
        this.senha = senha;
    }

    public User(String nome, String email , String senha, String urlFoto) {
        this.nome = nome;
        this.email = email;
        this.urlFoto = urlFoto;
        this.senha = senha;
    }

    public User(String email, String nome) {
        this.email = email;
        this.nome = nome;
    }

    @Override
    public String toEntityString() {

        if (urlFoto == null)
            return String.format("User { email: '%s', nome: '%s' , senha: '%s' }", this.email, this.nome, this.senha);
        else
            return String.format("User { email: '%s', nome: '%s', urlFoto: '%s' , senha: '%s' }", this.email, this.nome, this.urlFoto, this.senha);

    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


}
