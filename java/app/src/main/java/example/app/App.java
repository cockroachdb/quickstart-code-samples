package example.app;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Optional;

import org.postgresql.ds.PGSimpleDataSource;

public class App {

    public static void executeStmt(Connection conn, String stmt) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(stmt);
            while (rs.next())
            {
                System.out.println(rs.getString(1));
            }
            rs.close();
            st.close();
        }
        catch(Exception e)
        {
            return;
        }
    }

    private static String[] statements = {
        // CREATE the messages table
        "CREATE TABLE IF NOT EXISTS messages (id UUID PRIMARY KEY DEFAULT gen_random_uuid(), message STRING)",
        // INSERT a row into the messages table
        "INSERT INTO messages (message) VALUES ('Hello world!')",
        // SELECT a row from the messages table
        "SELECT message FROM messages"};

    public static void main(String[] args) {
        try {
            PGSimpleDataSource ds = new PGSimpleDataSource();
            ds.setApplicationName("docs_quickstart_java");
            ds.setUrl(Optional.ofNullable(System.getenv("JDBC_DATABASE_URL")).orElseThrow(
  () -> new IllegalArgumentException("JDBC_DATABASE_URL is not set.")));
            Connection connection = ds.getConnection();
            for (int n=0;n<statements.length;n++){
                executeStmt(connection,statements[n]);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
