package com.library.dao;

import java.sql.*;
import com.library.db.DBConnection;

public class BookDAO {

    public void addBook(String title, String author,
                        String category, int copies) {

        String sql =
        "INSERT INTO books(title,author,category,total_copies,available_copies) VALUES(?,?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, category);
            ps.setInt(4, copies);
            ps.setInt(5, copies);

            ps.executeUpdate();
            System.out.println("✔ Book Added");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewBooks() {
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM books")) {

            while (rs.next()) {
                System.out.println(
                    rs.getInt("book_id") + " | " +
                    rs.getString("title") + " | " +
                    rs.getInt("available_copies")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void searchByCategory(String category) {

        String sql = "SELECT * FROM books WHERE category = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n📚 Books in Category: " + category);
            System.out.println("ID | Title | Author | Available");

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println(
                    rs.getInt("book_id") + " | " +
                    rs.getString("title") + " | " +
                    rs.getString("author") + " | " +
                    rs.getInt("available_copies")
                );
            }

            if (!found) {
                System.out.println("❌ No books found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchByAuthor(String author) {

        String sql = "SELECT * FROM books WHERE author LIKE ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + author + "%");
            ResultSet rs = ps.executeQuery();

            System.out.println("\n📚 Books by Author: " + author);
            System.out.println("ID | Title | Category | Available");

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println(
                    rs.getInt("book_id") + " | " +
                    rs.getString("title") + " | " +
                    rs.getString("category") + " | " +
                    rs.getInt("available_copies")
                );
            }

            if (!found) {
                System.out.println("❌ No books found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void lowStockAlert(int threshold) {

        String sql =
            "SELECT book_id, title, available_copies FROM books WHERE available_copies <= ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, threshold);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n⚠ Low Stock Books (<= " + threshold + ")");
            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println(
                    rs.getInt("book_id") + " | " +
                    rs.getString("title") + " | " +
                    rs.getInt("available_copies")
                );
            }

            if (!found) {
                System.out.println("✔ All books sufficiently stocked");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
    
}
