import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        try {
            // REGISTER DRIVER (IMPORTANT)
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/urlshortener?useSSL=false&serverTimezone=UTC",
                "root",
                "tiger"
            );

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
