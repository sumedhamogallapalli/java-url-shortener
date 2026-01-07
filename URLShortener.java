import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class URLShortener {

    static String generateCode() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String code = "";
        Random r = new Random();

        for (int i = 0; i < 6; i++) {
            code += chars.charAt(r.nextInt(chars.length()));
        }
        return code;
    }

    static void shortenURL(String longUrl) {
        String shortCode = generateCode();

        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO urls (shortcode, longurl) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, shortCode);
            ps.setString(2, longUrl);
            ps.executeUpdate();

            System.out.println("Short URL: http://short.ly/" + shortCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void expandURL(String code) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT longurl FROM urls WHERE shortcode=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Original URL: " + rs.getString("longurl"));
            } else {
                System.out.println("Invalid short code");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Shorten URL");
            System.out.println("2. Expand URL");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                System.out.print("Enter long URL: ");
                String longUrl = sc.nextLine();
                shortenURL(longUrl);
            } else if (choice == 2) {
                System.out.print("Enter short code: ");
                String code = sc.nextLine();
                expandURL(code);
            } else {
                System.out.println("Thank you!");
                break;
            }
        }
    }
}
