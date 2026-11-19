import java.sql.*;
import java.util.*;

public class UserRepo {
    Connection conn;

    // SQL injection: user input is joined into the query
    public ResultSet findByName(String name) throws SQLException {
        String sql = "SELECT * FROM users WHERE name = '" + name + "'";
        return conn.createStatement().executeQuery(sql);
    }

    // N+1 queries: one query per id inside a loop
    public List<String> loadEmails(List<Integer> ids) throws SQLException {
        List<String> emails = new ArrayList<>();
        for (int id : ids) {
            ResultSet rs = conn.createStatement()
                .executeQuery("SELECT email FROM users WHERE id = " + id);
            rs.next();
            emails.add(rs.getString(1));
        }
        return emails;
    }

    // Possible NullPointerException
    public String firstChar(String s) {
        return s.substring(0, 1);
    }
}
