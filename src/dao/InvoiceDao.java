package dao;

import bus.Main;
import model.Invoice;

import java.sql.*;
import java.util.ArrayList;

public class InvoiceDao implements DaoInterface<Invoice> {

    @Override
    public int insert(Invoice t) {
        String sql = "INSERT INTO Invoice (invoice_id, customer_id, created_at, total_amount) VALUES (?, ?, ?, ?)";
        try (Connection con = Main.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, t.getInvoice_id());
            pst.setInt(2, t.getCustomer_id());
            pst.setTimestamp(3, t.getCreated_at());
            pst.setDouble(4, t.getTotal_amount());
            return pst.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }
    public void insertDetail(int invoiceId, int productId, int quantity, double price) {
        String sql = "INSERT INTO Invoice_Details (invoice_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
        try (Connection con = Main.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, invoiceId);
            pst.setInt(2, productId);
            pst.setInt(3, quantity);
            pst.setDouble(4, price);
            pst.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    @Override
    public ArrayList<Invoice> selectAll() {
        ArrayList<Invoice> list = new ArrayList<>();
        String sql = "SELECT * FROM Invoice";
        try (Connection con = Main.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                list.add(new Invoice(
                        rs.getInt("invoice_id"),
                        rs.getInt("customer_id"),
                        rs.getTimestamp("created_at"),
                        rs.getDouble("total_amount")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public Invoice selectById(int id) {
        String sql = "SELECT * FROM Invoice WHERE invoice_id = ?";
        try (Connection con = Main.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Invoice(
                            rs.getInt("invoice_id"),
                            rs.getInt("customer_id"),
                            rs.getTimestamp("created_at"),
                            rs.getDouble("total_amount")
                    );
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public int update(Invoice t) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    //  Tìm kiếm hóa đơn theo Tên khách hàng
    public ArrayList<Invoice> searchByCustomerName(String customerName) {
        ArrayList<Invoice> list = new ArrayList<>();
        String sql = "SELECT i.* FROM Invoice i JOIN Customer c ON i.customer_id = c.customer_id " +
                "WHERE c.customer_name LIKE ?";
        try (Connection con = Main.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, "%" + customerName + "%");
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    list.add(new Invoice(
                            rs.getInt("invoice_id"),
                            rs.getInt("customer_id"),
                            rs.getTimestamp("created_at"),
                            rs.getDouble("total_amount")
                    ));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    //  Tìm kiếm hóa đơn theo ngày/tháng/năm
    public ArrayList<Invoice> searchByDate(String dateStr) {
        ArrayList<Invoice> list = new ArrayList<>();
        String sql = "SELECT * FROM Invoice WHERE DATE(created_at) = ?";
        try (Connection con = Main.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, dateStr);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    list.add(new Invoice(
                            rs.getInt("invoice_id"),
                            rs.getInt("customer_id"),
                            rs.getTimestamp("created_at"),
                            rs.getDouble("total_amount")
                    ));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }


    //  Tính tổng doanh thu của một ngày cụ thể (Định dạng truyền vào: YYYY-MM-DD)
    public double getRevenueByDay(String dateStr) {
        double revenue = 0;
        String sql = "SELECT SUM(total_amount) AS revenue FROM Invoice WHERE DATE(created_at) = ?";
        try (Connection con = Main.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, dateStr);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) revenue = rs.getDouble("revenue");
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return revenue;
    }

    //  Tính tổng doanh thu của một tháng trong năm cụ thể (Tháng: 1-12, Năm: YYYY)
    public double getRevenueByMonth(int month, int year) {
        double revenue = 0;
        String sql = "SELECT SUM(total_amount) AS revenue FROM Invoice WHERE MONTH(created_at) = ? AND YEAR(created_at) = ?";
        try (Connection con = Main.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, month);
            pst.setInt(2, year);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) revenue = rs.getDouble("revenue");
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return revenue;
    }

    //  Tính tổng doanh thu của một năm cụ thể (Định dạng truyền vào: YYYY)
    public double getRevenueByYear(int year) {
        double revenue = 0;
        String sql = "SELECT SUM(total_amount) AS revenue FROM Invoice WHERE YEAR(created_at) = ?";
        try (Connection con = Main.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, year);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) revenue = rs.getDouble("revenue");
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return revenue;
    }
}