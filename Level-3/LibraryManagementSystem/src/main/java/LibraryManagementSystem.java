
import java.sql.*;
import java.util.Scanner;

public class LibraryManagementSystem {

    private static final String URL =
            "jdbc:mysql://localhost:3307/codveda_library";

    private static final String USER = "root";

    // Set your MySQL password in the DB_PASSWORD environment variable.
    private static final String PASSWORD =
            System.getenv("DB_PASSWORD") == null ? "" :
            System.getenv("DB_PASSWORD");

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private static void addBook(Scanner sc) {
        try (Connection con = getConnection()) {
            System.out.print("Enter book title: ");
            String title = sc.nextLine();

            System.out.print("Enter author: ");
            String author = sc.nextLine();

            System.out.print("Enter total copies: ");
            int copies = Integer.parseInt(sc.nextLine());

            if (copies <= 0) {
                System.out.println("Copies must be greater than zero.");
                return;
            }

            String sql = "INSERT INTO books (title, author, total_copies, available_copies) VALUES (?, ?, ?, ?)";

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, title);
                ps.setString(2, author);
                ps.setInt(3, copies);
                ps.setInt(4, copies);
                ps.executeUpdate();
                System.out.println("Book added successfully!");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void registerUser(Scanner sc) {
        try (Connection con = getConnection()) {
            System.out.print("Enter user name: ");
            String name = sc.nextLine();

            System.out.print("Enter email: ");
            String email = sc.nextLine();

            String sql = "INSERT INTO library_users (name, email) VALUES (?, ?)";

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, email);
                ps.executeUpdate();
                System.out.println("User registered successfully!");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewBooks() {
        String sql = "SELECT * FROM books";

        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n--- BOOK LIST ---");

            while (rs.next()) {
                System.out.println(
                    "ID: " + rs.getInt("book_id") +
                    " | Title: " + rs.getString("title") +
                    " | Author: " + rs.getString("author") +
                    " | Available: " + rs.getInt("available_copies")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void borrowBook(Scanner sc) {
        try (Connection con = getConnection()) {
            System.out.print("Enter user ID: ");
            int userId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter book ID: ");
            int bookId = Integer.parseInt(sc.nextLine());

            con.setAutoCommit(false);

            try {
                String checkUser = "SELECT user_id FROM library_users WHERE user_id = ?";

                try (PreparedStatement ps = con.prepareStatement(checkUser)) {
                    ps.setInt(1, userId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("User not found.");
                        }
                    }
                }

                String checkBook = "SELECT available_copies FROM books WHERE book_id = ? FOR UPDATE";

                try (PreparedStatement ps = con.prepareStatement(checkBook)) {
                    ps.setInt(1, bookId);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Book not found.");
                        }

                        if (rs.getInt("available_copies") <= 0) {
                            throw new SQLException("Book is not available.");
                        }
                    }
                }

                String update = "UPDATE books SET available_copies = available_copies - 1 WHERE book_id = ?";

                try (PreparedStatement ps = con.prepareStatement(update)) {
                    ps.setInt(1, bookId);
                    ps.executeUpdate();
                }

                String insert = "INSERT INTO borrow_transactions (user_id, book_id, borrow_date, status) VALUES (?, ?, CURDATE(), 'BORROWED')";

                try (PreparedStatement ps = con.prepareStatement(insert)) {
                    ps.setInt(1, userId);
                    ps.setInt(2, bookId);
                    ps.executeUpdate();
                }

                con.commit();
                System.out.println("Book borrowed successfully!");

            } catch (SQLException e) {
                con.rollback();
                System.out.println("Borrow failed: " + e.getMessage());
            } finally {
                con.setAutoCommit(true);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void returnBook(Scanner sc) {
        try (Connection con = getConnection()) {
            System.out.print("Enter transaction ID: ");
            int transactionId = Integer.parseInt(sc.nextLine());

            con.setAutoCommit(false);

            try {
                int bookId;

                String find = "SELECT book_id FROM borrow_transactions WHERE transaction_id = ? AND status = 'BORROWED' FOR UPDATE";

                try (PreparedStatement ps = con.prepareStatement(find)) {
                    ps.setInt(1, transactionId);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Active transaction not found.");
                        }
                        bookId = rs.getInt("book_id");
                    }
                }

                String updateTransaction = "UPDATE borrow_transactions SET return_date = CURDATE(), status = 'RETURNED' WHERE transaction_id = ?";

                try (PreparedStatement ps = con.prepareStatement(updateTransaction)) {
                    ps.setInt(1, transactionId);
                    ps.executeUpdate();
                }

                String updateBook = "UPDATE books SET available_copies = available_copies + 1 WHERE book_id = ?";

                try (PreparedStatement ps = con.prepareStatement(updateBook)) {
                    ps.setInt(1, bookId);
                    ps.executeUpdate();
                }

                con.commit();
                System.out.println("Book returned successfully!");

            } catch (SQLException e) {
                con.rollback();
                System.out.println("Return failed: " + e.getMessage());
            } finally {
                con.setAutoCommit(true);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewTransactions() {
        String sql = "SELECT * FROM borrow_transactions";

        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n--- TRANSACTIONS ---");

            while (rs.next()) {
                System.out.println(
                    "Transaction ID: " + rs.getInt("transaction_id") +
                    " | User ID: " + rs.getInt("user_id") +
                    " | Book ID: " + rs.getInt("book_id") +
                    " | Borrow Date: " + rs.getDate("borrow_date") +
                    " | Return Date: " + rs.getDate("return_date") +
                    " | Status: " + rs.getString("status")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Book");
            System.out.println("2. Register User");
            System.out.println("3. View Books");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. View Transactions");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1": addBook(sc); break;
                case "2": registerUser(sc); break;
                case "3": viewBooks(); break;
                case "4": borrowBook(sc); break;
                case "5": returnBook(sc); break;
                case "6": viewTransactions(); break;
                case "7":
                    System.out.println("Thank you!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
