package com.library.dao;

import java.sql.*;
import com.library.db.DBConnection;

public class MemberDAO {

    public void addMember(String name, String email, String phone) {

        String sql =
            "INSERT INTO members(name,email,phone) VALUES(?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                 con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int memberId = rs.getInt(1);
                System.out.println("✔ Member Added Successfully");
                System.out.println("🆔 Your Member ID is: " + memberId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void viewMembers() {

        String sql = "SELECT member_id, name, email, phone FROM members";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n👥 Registered Members");
            System.out.println("ID | Name | Email | Phone");

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println(
                    rs.getInt("member_id") + " | " +
                    rs.getString("name") + " | " +
                    rs.getString("email") + " | " +
                    rs.getString("phone")
                );
            }

            if (!found) {
                System.out.println("❌ No members found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
