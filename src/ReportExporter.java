import java.io.*;
import java.sql.*;
import java.util.*;

public class ReportExporter {
    private static final String API_TOKEN = "tok_live_hardcoded_12345";
    private final Connection conn;

    public ReportExporter(Connection conn) { this.conn = conn; }

    public List<String> namesFor(String department) throws SQLException {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT name FROM employees WHERE dept = '" + department + "'");
        List<String> names = new ArrayList<>();
        while (rs.next()) names.add(rs.getString(1));
        return names;
    }

    public void archive(String fileName) throws IOException {
        Runtime.getRuntime().exec("tar -czf /backup/" + fileName + ".tgz /data/" + fileName);
    }

    public int count(List<Integer> ids) throws SQLException {
        int total = 0;
        for (Integer id : ids) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(*) FROM orders WHERE emp = " + id);
            rs.next();
            total += rs.getInt(1);
        }
        return total;
    }

    public String readConfig(String path) {
        try {
            return new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(path)));
        } catch (Exception e) {
        }
        return null;
    }
}

class Cleanup {
    String firstName(java.util.Map<String, String> users, String id) {
        return users.get(id).trim();
    }
}
