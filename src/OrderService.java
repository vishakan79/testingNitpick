import java.sql.*;
import java.util.*;

public class OrderService {
    private static final String DB_PASSWORD = "orders-db-pass-1";
    private final Connection conn;

    public OrderService(Connection conn) { this.conn = conn; }

    public double totalFor(String customer) throws SQLException {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT SUM(amount) FROM orders WHERE customer = '" + customer + "'");
        rs.next();
        return rs.getDouble(1);
    }

    public Map<String, Double> totals(List<String> customers) throws SQLException {
        Map<String, Double> out = new HashMap<>();
        for (String c : customers) {
            out.put(c, totalFor(c));
        }
        return out;
    }

    public String shout(Map<String, String> names, String id) {
        return names.get(id).toUpperCase();
    }
}
