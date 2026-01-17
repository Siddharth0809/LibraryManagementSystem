package com.library.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.library.db.DBConnection;

public class IssueDAO {

	public void issueBook(int bookId, int memberId) {

        try (Connection con = DBConnection.getConnection()) {

            // Check member
            PreparedStatement ps1 =
                con.prepareStatement(
                    "SELECT 1 FROM members WHERE member_id = ?");
            ps1.setInt(1, memberId);
            ResultSet rs1 = ps1.executeQuery();

            if (!rs1.next()) {
                System.out.println("❌ Invalid Member ID");
                return;
            }

            // Check book availability
            PreparedStatement ps2 =
                con.prepareStatement(
                    "SELECT available_copies FROM books WHERE book_id = ?");
            ps2.setInt(1, bookId);
            ResultSet rs2 = ps2.executeQuery();

            if (!rs2.next() || rs2.getInt(1) <= 0) {
                System.out.println("❌ Book not available");
                return;
            }

            // Issue book
            String issueSql =
                "INSERT INTO issue_records " +
                "(book_id, member_id, issue_date, issue_time) " +
                "VALUES (?, ?, CURDATE(), CURTIME())";

            PreparedStatement ps3 =
                con.prepareStatement(
                    issueSql, Statement.RETURN_GENERATED_KEYS);

            ps3.setInt(1, bookId);
            ps3.setInt(2, memberId);
            ps3.executeUpdate();

            ResultSet rsKey = ps3.getGeneratedKeys();
            if (rsKey.next()) {
                int issueId = rsKey.getInt(1);
                System.out.println("✔ Book Issued Successfully");
                System.out.println("📄 Issue ID: " + issueId);
            }

            // Update stock
            PreparedStatement ps4 =
                con.prepareStatement(
                    "UPDATE books SET available_copies = available_copies - 1 WHERE book_id = ?");
            ps4.setInt(1, bookId);
            ps4.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void returnBook(int issueId) {

        try (Connection con = DBConnection.getConnection()) {

            String fetch =
                "SELECT issue_date, book_id FROM issue_records " +
                "WHERE issue_id = ? AND return_date IS NULL";

            PreparedStatement ps1 = con.prepareStatement(fetch);
            ps1.setInt(1, issueId);
            ResultSet rs = ps1.executeQuery();

            if (!rs.next()) {
                System.out.println("❌ Invalid or Already Returned Issue ID");
                return;
            }

            LocalDate issueDate =
                rs.getDate("issue_date").toLocalDate();
            int bookId = rs.getInt("book_id");

            long days =
                java.time.temporal.ChronoUnit.DAYS
                    .between(issueDate, LocalDate.now());
            double fine = days > 14 ? (days - 14) * 5 : 0;

            String updateIssue =
                "UPDATE issue_records SET " +
                "return_date = CURDATE(), " +
                "return_time = CURTIME(), " +
                "fine = ? WHERE issue_id = ?";

            PreparedStatement ps2 =
                con.prepareStatement(updateIssue);
            ps2.setDouble(1, fine);
            ps2.setInt(2, issueId);
            ps2.executeUpdate();

            PreparedStatement ps3 =
                con.prepareStatement(
                    "UPDATE books SET available_copies = available_copies + 1 WHERE book_id = ?");
            ps3.setInt(1, bookId);
            ps3.executeUpdate();

            System.out.println("✔ Book Returned Successfully");
            System.out.println("📄 Issue ID: " + issueId);
            System.out.println("💰 Fine: ₹" + fine);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void topIssuedBooks() {

        String sql =
            "SELECT b.title, COUNT(i.issue_id) AS issued_count " +
            "FROM issue_records i " +
            "JOIN books b ON i.book_id = b.book_id " +
            "GROUP BY b.title " +
            "ORDER BY issued_count DESC " +
            "LIMIT 5";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n📊 Top Issued Books");
            System.out.println("Title | Times Issued");

            while (rs.next()) {
                System.out.println(
                    rs.getString("title") + " | " +
                    rs.getInt("issued_count")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}







