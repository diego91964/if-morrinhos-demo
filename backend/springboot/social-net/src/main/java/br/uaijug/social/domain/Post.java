package br.uaijug.social.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by diego on 15/11/16.
 */
public class Post implements Neo4jBoltInsertable {

    private String textoPost;
    private Date dataPublicacao;
    private String idNode;

    public Post(String idNode, String dataPublicacao, String textoPost) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        this.idNode = idNode;
        try {
            this.dataPublicacao = simpleDateFormat.parse(dataPublicacao);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.textoPost = textoPost;
    }

    public Post(String textoPost, Date dataPublicacao) {
        this.textoPost = textoPost;
        this.dataPublicacao = dataPublicacao;
    }

    @Override
    public String toEntityString() {


        return String.format("Post { textoPost: '%s', dataPublicacao: '%s'}", this.textoPost,new SimpleDateFormat("dd/MM/yyyy").format(this.dataPublicacao));

    }

    public String getTextoPost() {
        return textoPost;
    }


    public void setTextoPost(String textoPost) {
        this.textoPost = textoPost;
    }

    public Date getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }
}
