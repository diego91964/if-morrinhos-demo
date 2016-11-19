package br.uaijug.social.service;

import br.uaijug.social.domain.Post;
import br.uaijug.social.domain.Sessao;
import br.uaijug.social.domain.User;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by diego on 15/11/16.
 */
@Component
public class UserService {

    @Autowired
    Neo4jConnection neo4jConnection;

    public void insertUser (User user){
        neo4jConnection.insert(user);
    }

    public void adicionaFotoUsuario (String email, String urlFoto){

        neo4jConnection.run( "MATCH (user:User) WHERE user.email = '"+email+"' SET user.urlFoto = '"+urlFoto+"'" );

    }

    public Sessao login(String email, String senha){

        StatementResult st =  neo4jConnection.run( "MATCH (user:User) WHERE user.email = '"+email+"' AND user.senha = '"+senha+"' RETURN user" );

        User userLogado = null;

        while (st != null && st.hasNext()){
            Record record = st.next();
            if (record != null &&
                record.get("email") != null &&
                record.get("nome")  != null &&
                record.get("senha") != null ){
                userLogado = new User(record.get("email").toString(), record.get("nome").toString(), record.get("senha").toString());
                break;
            }

        }

        if(userLogado != null){

            String token = "" + new Date().getTime();
            Sessao sessao = new Sessao(token, userLogado.getEmail());
            neo4jConnection.insert(sessao);
            return sessao;
        }
        return null;
    }

    public List<User> buscarListaDeUsuariosExceto (String email) {

        StatementResult st =  neo4jConnection.run( "MATCH (user:User) WHERE user.email <> '"+email+"'  RETURN user" );

        List<User> usuarios = new ArrayList<>();

        while (st != null && st.hasNext()){
            Record record = st.next();
            if (record != null &&
                    record.get("email") != null &&
                    record.get("nome")  != null &&
                    record.get("senha") != null ){
                usuarios.add(new User(record.get("email").toString(), record.get("nome").toString()));

            }
        }

        return usuarios;
    }

    public void amigo (String emailUsuario, String emailAmigo){

        String query = "MATCH (user:User {email : '%s' }), (amigo:User {email : '%s' }) MERGE (user)-[r:E_AMIGO_DE]->(amigo)";

        StatementResult st =  neo4jConnection.run( String.format(query,emailUsuario,emailAmigo) );

    }

    public void criaPost (String email, Post post){

        String query = "MATCH (user: User {email : '%s'}) CREATE (user) -[r:USUARIO_POSTOU]-> (post: Post { texto: '%s' , data: '%s'})";

        StatementResult st =  neo4jConnection.run( String.format(query,email,post.getTextoPost(),post.getDataPublicacao()) );
    }

    public List<Post> buscaPostDeAmigos (String emailUsuario){

        String query = "MATCH (user:User {email : '%s'} ) - [r:E_AMIGO_DE] -> (amigo:User) - [r2:USUARIO_POSTOU] -> (post:Post) RETURN post.data AS data, post.texto AS texto , ID(post) AS idNode";

        List<Post> postAmigos = new ArrayList<>();

        StatementResult st =  neo4jConnection.run( String.format(query,emailUsuario) );

        while (st != null && st.hasNext()){
            Record record = st.next();
            if (record != null &&
                    record.get("idNode") != null &&
                    record.get("data")  != null &&
                    record.get("texto") != null ){
                postAmigos.add(new Post(record.get("idNode").toString(),record.get("data").toString(),record.get("texto").toString()));

            }
        }

        return postAmigos;

    }


    public void curtirPost (String email, String idNode) {

        String query = "MATCH (post:Post),(user:User {email: '%s})" +
                        "WHERE ID(post) = %s " +
                        "MERGE (user)- [r:USUARIO_CURTE] -> (post)";

        neo4jConnection.run( String.format(query,email,idNode) );

    }
}
