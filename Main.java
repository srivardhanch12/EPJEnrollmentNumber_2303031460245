import java.sql.*;
import java.util.Scanner;

public class Main {
    // ✅ New DB connection details
    static final String DB_URL = "jdbc:mysql://bytexldb.com:5051/db_43rn6jvud?useSSL=false&allowPublicKeyRetrieval=true";
    static final String USER = "user_43rn6jvud";
    static final String PASS = "p43rn6jvud";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter User name: ");
        String username = scanner.nextLine();

        System.out.print("Enter User password: ");
        String password = scanner.nextLine();

        if (checkLogin(username, password)) {
            System.out.println("Login Successful, Welcome -> " + capitalize(username));
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }

        scanner.close();
    }

    public static boolean checkLogin(String username, String password) {
        boolean isValid = false;

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to database
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Secure query to validate login
            String query = "SELECT * FROM users WHERE Username = ? AND Password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                isValid = true;
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
            e.printStackTrace();
        }

        return isValid;
    }

    public static String capitalize(String name) {
        if (name == null || name.isEmpty()) return name;
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}