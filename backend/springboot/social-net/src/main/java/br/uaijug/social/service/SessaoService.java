package br.uaijug.social.service;

import br.uaijug.social.domain.Sessao;
import br.uaijug.social.domain.User;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by diego on 18/11/16.
 */
@Component
public class SessaoService {

    @Autowired
    Neo4jConnection neo4jConnection;

    public Sessao buscarSessaoPorToken (String token){

        StatementResult st =  neo4jConnection.run( "MATCH (sessao:Sessao) WHERE sessao.token = '"+token+"'  RETURN sessao" );

        User userLogado = null;

        while (st != null && st.hasNext()){
            Record record = st.next();
            if (record != null &&
                    record.get("emailUsuario") != null &&
                    record.get("token")  != null ){
                return new Sessao(record.get("emailUsuario").toString(), record.get("token").toString());
            }

        }

        return null;
    }

    public void logout (String token){

        StatementResult st =  neo4jConnection.run( "MATCH (sessao:Sessao) WHERE sessao.token = '"+token+"'  DELETE sessao" );

    }
}
