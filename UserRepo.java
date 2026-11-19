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
}
