import java.sql.*;
import java.util.*;

public class PaymentService {
    private static final String ADMIN_PASSWORD = "admin123";
    private final Connection conn;

    public PaymentService(Connection conn) {
        this.conn = conn;
    }

    // Possible SQL injection and resource leak
    public double balanceFor(String accountId) throws SQLException {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT balance FROM accounts WHERE id = '" + accountId + "'");
        rs.next();
        return rs.getDouble(1);
    }

    // N+1 queries inside a loop
    public Map<String, Double> balances(List<String> ids) throws SQLException {
        Map<String, Double> out = new HashMap<>();
        for (String id : ids) {
            out.put(id, balanceFor(id));
        }
        return out;
    }

    // Swallowed exception and NullPointerException risk
    public String ownerName(Map<String, String> owners, String id) {
        try {
            return owners.get(id).toUpperCase();
        } catch (Exception e) {
        }
        return null;
    }
}
