package dao;

import bus.Main;
import model.InvoiceDetails;
import java.sql.*;
import java.util.ArrayList;

public class InvoiceDetailsDao implements DaoInterface<InvoiceDetails> {

    @Override
    public int insert(InvoiceDetails t) {
        String sql = "INSERT INTO InvoiceDetails (id, invoice_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Main.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, t.getId());
            pst.setInt(2, t.getInvoice_id());
            pst.setInt(3, t.getProduct_id());
            pst.setInt(4, t.getQuantity());
            pst.setDouble(5, t.getUnit_price());

            int result = pst.executeUpdate();

            return result;
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    @Override
    public ArrayList<InvoiceDetails> selectAll() {
        ArrayList<InvoiceDetails> list = new ArrayList<>();
        String sql = "SELECT * FROM InvoiceDetails";
        try (Connection con = Main.getConnection();
             ResultSet rs = con.prepareStatement(sql).executeQuery()) {
            while (rs.next()) {
                list.add(new InvoiceDetails(
                        rs.getInt("id"),
                        rs.getInt("invoice_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("unit_price")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override public int update(InvoiceDetails t) { return 0; }
    @Override public int delete(int id) { return 0; }
    @Override public InvoiceDetails selectById(int id) { return null; }
}