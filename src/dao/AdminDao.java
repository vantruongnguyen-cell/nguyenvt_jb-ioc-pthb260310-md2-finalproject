package dao;

import bus.Main;
import model.Admin;
import java.sql.*;
import java.util.ArrayList;

public class AdminDao implements DaoInterface<Admin> {

    public Admin login(String user, String pass) {
        Admin result = null;
        String sql = "SELECT * FROM admin WHERE username=? AND password=?";
        try (Connection con = Main.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, user);
            pst.setString(2, pass);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                // Khớp đúng 3 tham số: id, user, pass
                result = new Admin(
                        rs.getInt("admin_id"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int insert(Admin t) {
        int res = 0;
        String sql = "INSERT INTO admin(admin_id, username, password) VALUES(?,?,?)";
        try (Connection con = Main.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, t.getAdmin_id());
            pst.setString(2, t.getUsername());
            pst.setString(3, t.getPassword());

            res = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public ArrayList<Admin> selectAll() {
        ArrayList<Admin> list = new ArrayList<>();
        String sql = "SELECT * FROM admin";
        try (Connection con = Main.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                list.add(new Admin(
                        rs.getInt("admin_id"),
                        rs.getString("username"),
                        rs.getString("password")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int update(Admin t) {
        int res = 0;
        String sql = "UPDATE admin SET username=?, password=? WHERE admin_id=?";
        try (Connection con = Main.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, t.getUsername());
            pst.setString(2, t.getPassword());
            pst.setInt(3, t.getAdmin_id());
            res = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public int delete(int id) {
        int res = 0;
        String sql = "DELETE FROM admin WHERE admin_id=?";
        try (Connection con = Main.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            res = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Admin selectById(int id) {
        Admin result = null;
        String sql = "SELECT * FROM admin WHERE admin_id=?";
        try (Connection con = Main.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                result = new Admin(
                        rs.getInt("admin_id"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}