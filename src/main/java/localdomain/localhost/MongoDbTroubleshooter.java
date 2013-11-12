/*
 * Copyright (c) 2010-2013 the original author or authors
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */
package localdomain.localhost;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:cleclerc@cloudbees.com">Cyrille Le Clerc</a>
 */
public class MongoDbTroubleshooter {
    @Option(name = "-uri", required = true, usage = "MongoDB URI like 'mongodb://localhost/local'")
    String uri;

    public static void main(String[] args) throws Exception {
        MongoDbTroubleshooter bean = new MongoDbTroubleshooter();
        CmdLineParser parser = new CmdLineParser(bean);
        try {
            parser.parseArgument(args);
            bean.run();
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
        }
    }

    public void run() throws Exception {
        try (PrintWriter writer = new PrintWriter(System.out)) {


            MongoClientURI mongoClientUri = new MongoClientURI(uri);
            writer.println("" +
                    "host=" + mongoClientUri.getHosts() +
                    ",username=" + mongoClientUri.getUsername() +
                    ",database=" + mongoClientUri.getDatabase() +
                    ",collection=" + mongoClientUri.getCollection() +
                    "");

            writer.println();
            writer.println();

            writer.println("# MongoClient");
            writer.println();

            MongoClient mongoClient = new MongoClient(mongoClientUri);
            writer.println("" + mongoClient + "");

            writer.println();
            writer.println();
            writer.println("# Databases");
            writer.println();

            try {
                List<String> databaseNames = mongoClient.getDatabaseNames();
                for (String databaseName : databaseNames) {
                    writer.println("* " + databaseName + (databaseName.equals(mongoClientUri.getDatabase()) ? " - default database" : ""));
                }
            } catch (Exception e) {
                writer.println("Could not list the databases of the MongoDB instance: '" + e.getMessage() + "'");

            }

            writer.println();
            writer.println();
            writer.println("# Database");
            writer.println();

            DB db = mongoClient.getDB(mongoClientUri.getDatabase());
            writer.println("DB: " + db.getName() + "");

            writer.println();
            writer.println();
            writer.println("## Collections");
            writer.println();
            Set<String> myCollections = db.getCollectionNames();
            if (myCollections.isEmpty()) {
                writer.println("NO COLLECTIONS!");

            } else {

                for (String collection : myCollections) {
                    DBCollection dbCollection = db.getCollection(collection);
                    writer.println("* " + dbCollection.getName() + " - " + dbCollection.getCount() + " entries");
                }
            }
        }
    }
}
