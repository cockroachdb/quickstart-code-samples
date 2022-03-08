package example.app;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import org.postgresql.ds.PGSimpleDataSource;

public class App {

    public static void main(String[] args) {
        
        try {
            PGSimpleDataSource ds = new PGSimpleDataSource();
            ds.setUrl(System.getenv("JDBC_DATABASE_URL"));
            Connection conn = ds.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT message FROM messages");
            while (rs.next())
            {
                System.out.println(rs.getString(1));
            }
            rs.close();
            st.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
