package db;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.io.File;
import java.io.IOException;

public class Provider {
    GraphDatabaseService graphDb;

    private GraphDatabaseService createDatabase(){
        return new GraphDatabaseFactory().newEmbeddedDatabase(createTempDatabaseDir().getAbsolutePath());
    }

    private static File createTempDatabaseDir()
    {
        File directory;
        try
        {
            directory = File.createTempFile( "neo4j-bootcamp", "dir" );
            System.out.println( String.format( "Created a new Neo4j database at [%s]", directory.getAbsolutePath() ) );
        }
        catch ( IOException e )
        {
            throw new RuntimeException( e );
        }
        if ( !directory.delete() )
        {
            throw new RuntimeException( "temp config directory pre-delete failed" );
        }
        if ( !directory.mkdirs() )
        {
            throw new RuntimeException( "temp config directory not created" );
        }
        directory.deleteOnExit();
        return directory;
    }

    public GraphDatabaseService getDatabase() {
        if(graphDb == null){
            graphDb = createDatabase();
        }
        return graphDb;
    }

}
