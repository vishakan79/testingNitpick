import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepo {
    private final Connection connection;

    public UserRepo(Connection connection) {
        this.connection = connection;
    }

    // Intentionally unsafe: user input is concatenated into SQL.
    public ResultSet findByName(String name) throws SQLException {
        String sql = "SELECT * FROM users WHERE name = '" + name + "'";
        return connection.createStatement().executeQuery(sql);
    }

    // Intentionally inefficient: executes one query for every ID (N+1 pattern).
    public List<String> loadEmails(List<Integer> ids) throws SQLException {
        List<String> emails = new ArrayList<>();
        for (int id : ids) {
            ResultSet rs = connection.createStatement()
                .executeQuery("SELECT email FROM users WHERE id = " + id);
            if (rs.next()) {
                emails.add(rs.getString("email"));
            }
        }
        return emails;
    }

    // Intentionally unsafe: throws NullPointerException for null or empty input.
    public String firstCharacter(String value) {
        return value.substring(0, 1);
    }

    // Added only to test Nitpick's SQL-injection detection on newly changed lines.
    public ResultSet findByEmail(String email) throws SQLException {
        String query = "SELECT * FROM users WHERE email = '" + email + "'";
        return connection.createStatement().executeQuery(query);
    }

    // Added only to test N+1 detection on newly changed lines.
    public List<String> loadNames(List<Integer> ids) throws SQLException {
        List<String> names = new ArrayList<>();
        for (int id : ids) {
            ResultSet rs = connection.createStatement()
                .executeQuery("SELECT name FROM users WHERE id = " + id);
            if (rs.next()) {
                names.add(rs.getString("name"));
            }
        }
        return names;
    }
}
