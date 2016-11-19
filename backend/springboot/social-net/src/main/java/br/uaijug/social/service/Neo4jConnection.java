package br.uaijug.social.service;


import br.uaijug.social.domain.Neo4jBoltInsertable;
import org.neo4j.driver.v1.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * Created by diego on 15/11/16.
 */
@Component
@Scope(scopeName = "singleton")
public class Neo4jConnection {

    private Session session;
    private Driver driver;

    public Neo4jConnection() {



        this.driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic( "neo4j", "admin" ) );


        /*session.run( "CREATE (a:Person {name:'Arthur', title:'King'})" );

        StatementResult result = session.run( "MATCH (a:Person) WHERE a.name = 'Arthur' RETURN a.name AS name, a.title AS title" );
        while ( result.hasNext() )
        {
            Record record = result.next();
            System.out.println( record.get( "title" ).asString() + " " + record.get("name").asString() );
        }
*/
    }

    public void insert(Neo4jBoltInsertable neo4jBoltInsertable){
        run( "CREATE (a: "+neo4jBoltInsertable.toEntityString()+")" );
    }

    public StatementResult run (String query) {

        if (this.session != null && this.session.isOpen() )
            this.session.close();

        session = driver.session();

        StatementResult result =  session.run(query);

        session.close();

        return result;

    }


}
