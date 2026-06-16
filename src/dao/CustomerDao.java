package dao;

import bus.Main;
import model.Customer;
import java.sql.*;
import java.util.ArrayList;

public class CustomerDao implements DaoInterface<Customer> {
    @Override
    public int insert(Customer t) {
        int ketQua = 0;
        try (Connection con = Main.getConnection()) {
            String sql = "INSERT INTO Customer (customer_id, customer_name, phone, email, address) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, t.getCustomer_id());
            pst.setString(2, t.getCustomer_name());
            pst.setString(3, t.getPhone());
            pst.setString(4, t.getEmail());
            pst.setString(5, t.getAddress());
            ketQua = pst.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
        return ketQua;
    }

    @Override
    public int update(Customer t) {
        int ketQua = 0;
        try (Connection con = Main.getConnection()) {
            String sql = "UPDATE Customer SET customer_name=?, phone=?, email=?, address=? WHERE customer_id=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getCustomer_name());
            pst.setString(2, t.getPhone());
            pst.setString(3, t.getEmail());
            pst.setString(4, t.getAddress());
            pst.setInt(5, t.getCustomer_id());
            ketQua = pst.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
        return ketQua;
    }

    @Override
    public int delete(int id) {
        int ketQua = 0;
        try (Connection con = Main.getConnection()) {
            String sql = "DELETE FROM Customer WHERE customer_id=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ketQua = pst.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
        return ketQua;
    }

    @Override
    public ArrayList<Customer> selectAll() {
        ArrayList<Customer> list = new ArrayList<>();
        try (Connection con = Main.getConnection()) {
            String sql = "SELECT * FROM Customer";
            ResultSet rs = con.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                list.add(new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("customer_name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public Customer selectById(int id) {
        Customer c = null;
        try (Connection con = Main.getConnection()) {
            String sql = "SELECT * FROM Customer WHERE customer_id=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                c = new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("customer_name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                );
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return c;
    }
}