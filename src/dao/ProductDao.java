package dao;

import bus.Main;
import model.Product;
import java.sql.*;
import java.util.ArrayList;

public class ProductDao implements DaoInterface<Product> {

    @Override
    public int insert(Product t) {
        String sql = "INSERT INTO Product (product_id, product_name, brand, price, stock) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Main.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, t.getProduct_id());
            pst.setString(2, t.getProduct_name());
            pst.setString(3, t.getBrand());
            pst.setDouble(4, t.getPrice());
            pst.setInt(5, t.getStock());
            return pst.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    @Override
    public ArrayList<Product> selectAll() {
        ArrayList<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product";
        try (Connection con = Main.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("brand"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public Product selectById(int id) {
        String sql = "SELECT * FROM Product WHERE product_id = ?";
        try (Connection con = Main.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("brand"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public int update(Product t) {
        String sql = "UPDATE Product SET product_name=?, brand=?, price=?, stock=? WHERE product_id=?";
        try (Connection con = Main.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, t.getProduct_name());
            pst.setString(2, t.getBrand());
            pst.setDouble(3, t.getPrice());
            pst.setInt(4, t.getStock());
            pst.setInt(5, t.getProduct_id());
            return pst.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    @Override
    public int delete(int id) throws Exception {
        String sql = "DELETE FROM Product WHERE product_id=?";
        try (Connection con = Main.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected == 0) {
                throw new Exception("Không tìm thấy sản phẩm cần xóa");
            }

            return rowsAffected;
        } catch (SQLException e) {
            throw new Exception("Thất bại: Sản phẩm này đã dính vào hóa đơn/chi tiết hóa đơn, không thể xóa!");
        }
    }

    public ArrayList<Product> searchByBrand(String brandName) {
        ArrayList<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE brand LIKE ?";
        try (Connection con = Main.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, "%" + brandName + "%");
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    list.add(new Product(
                            rs.getInt("product_id"), rs.getString("product_name"),
                            rs.getString("brand"), rs.getDouble("price"), rs.getInt("stock")
                    ));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public ArrayList<Product> searchByPriceRange(double minPrice, double maxPrice) {
        ArrayList<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE price BETWEEN ? AND ?";
        try (Connection con = Main.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setDouble(1, minPrice);
            pst.setDouble(2, maxPrice);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    list.add(new Product(
                            rs.getInt("product_id"), rs.getString("product_name"),
                            rs.getString("brand"), rs.getDouble("price"), rs.getInt("stock")
                    ));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    /**
     * Hàm tìm kiếm theo tên sản phẩm kèm thông tin tồn kho
     * Vá lỗi đoạn code thừa/thiếu bị hỏng ở cuối file cũ
     */
    public ArrayList<Product> searchByNameWithStock(String keyword) {
        ArrayList<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE product_name LIKE ?";
        try (Connection con = Main.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, "%" + keyword + "%");
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    list.add(new Product(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getString("brand"),
                            rs.getDouble("price"),
                            rs.getInt("stock")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}