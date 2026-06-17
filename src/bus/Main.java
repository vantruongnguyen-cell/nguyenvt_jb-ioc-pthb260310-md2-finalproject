package bus;

import dao.*;
import model.*;
import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static Admin currentAdmin = null;

    private static final String URL = "jdbc:mysql://localhost:3306/FinalProject";
    private static final String USER = "root";
    private static final String PASS = "123456";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void main(String[] args) {
        while (true) {
            if (currentAdmin == null) {
                hienThiGiaoDienBatDau();
            } else {
                hienThiMenuChinh();
            }
        }
    }

    private static void hienThiGiaoDienBatDau() {
        System.out.println("\n========== HỆ THỐNG QUẢN LÝ CỬA HÀNG ==========");
        System.out.println("1. Đăng nhập Admin");
        System.out.println("2. Thoát");
        System.out.print("Chọn: ");
        try {
            int c = Integer.parseInt(sc.nextLine());
            if (c == 1) dangNhap();
            else if (c == 2) System.exit(0);
        } catch (Exception e) { System.out.println("Nhập sai!"); }
    }

    private static void dangNhap() {
        System.out.println("======== ĐĂNG NHẬP QUẢN TRỊ ========");
        System.out.print("Tài khoản: "); String u = sc.nextLine();
        System.out.print("Mật khẩu: "); String p = sc.nextLine();
        System.out.println("====================================");

        AdminDao ad = new AdminDao();
        currentAdmin = ad.login(u, p);

        if (currentAdmin != null) System.out.println("Đăng nhập thành công! Chào mừng " + currentAdmin.getUsername());
        else System.out.println("Sai tài khoản hoặc mật khẩu!");
    }

    private static void hienThiMenuChinh() {
        System.out.println("\n========== MENU CHÍNH ==========");
        System.out.println("1. Quản lý sản phẩm điện thoại");
        System.out.println("2. Quản lý khách hàng");
        System.out.println("3. Quản lý hóa đơn");
        System.out.println("4. Thống kê doanh thu");
        System.out.println("5. Đăng xuất");
        System.out.print("Chọn: ");

        try {
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1: menuProduct(); break;
                case 2: menuCustomer(); break;
                case 3: menuInvoice(); break;
                case 4: menuThongKe(); break;
                case 5: currentAdmin = null; System.out.println("Đã đăng xuất!"); break;
                default: System.out.println("Vui lòng chọn từ 1-5!");
            }
        } catch (Exception e) { System.out.println("Lỗi nhập liệu!"); }
    }

    // ====================
    // 1. QUẢN LÝ SẢN PHẨM
    private static void menuProduct() {
        ProductDao pDao = new ProductDao();
        while (true) {
            System.out.println("\n========= QUẢN LÝ SẢN PHẨM =========");
            System.out.println("1. Hiển thị danh sách sản phẩm");
            System.out.println("2. Thêm sản phẩm mới");
            System.out.println("3. Cập nhật thông tin sản phẩm");
            System.out.println("4. Xóa sản phẩm theo ID");
            System.out.println("5. Tìm kiếm theo Brand");
            System.out.println("6. Tìm kiếm điện theo Khoảng giá");
            System.out.println("7. Tìm kiếm theo tên sp kèm Tồn kho");
            System.out.println("8. Quay lại menu chính");
            System.out.print("Chọn: ");
            try {
                int choice = Integer.parseInt(sc.nextLine());
                if (choice == 8) break; // Thoát về menu chính

                switch (choice) {
                    case 1: // Hiển thị danh sách
                        ArrayList<Product> list = pDao.selectAll();
                        System.out.println("ID | Tên sản phẩm | Brand | Giá tiền | Tồn kho");
                        for (Product p : list) {
                            System.out.println(p.getProduct_id() + " | " + p.getProduct_name() + " | " + p.getBrand() + " | " + p.getPrice() + " | " + p.getStock());
                        }
                        break;

                    case 2: // Thêm mới
                        System.out.print("Nhập ID: "); int id = Integer.parseInt(sc.nextLine());
                        System.out.print("Nhập Tên: "); String name = sc.nextLine();
                        System.out.print("Nhập Brand: "); String brand = sc.nextLine();
                        System.out.print("Nhập Giá: "); double price = Double.parseDouble(sc.nextLine());
                        System.out.print("Nhập Số lượng kho: "); int stock = Integer.parseInt(sc.nextLine());
                        pDao.insert(new Product(id, name, brand, price, stock));
                        System.out.println("Thêm thành công!");
                        break;

                    case 3: // Cập nhật thông tin sản phẩm
                        Product currentProduct = null;
                        int uId = -1;
                        while (true) {
                            System.out.print("Nhập ID cần sửa: ");
                            uId = Integer.parseInt(sc.nextLine());
                            currentProduct = pDao.selectById(uId);
                            if (currentProduct != null) {
                                break;
                            } else {
                                System.out.println("Lỗi: Không tìm thấy sản phẩm có ID này. Vui lòng nhập lại!");
                            }
                        }

                        System.out.println("\n--- Thông tin hiện tại của sản phẩm ---");
                        System.out.println("Tên: " + currentProduct.getProduct_name() + " | Brand: " + currentProduct.getBrand() + " | Giá: " + currentProduct.getPrice() + " | Kho: " + currentProduct.getStock());
                        System.out.println("----------------------------------------");

                        System.out.print("Nhập Tên mới: "); String uName = sc.nextLine();
                        System.out.print("Nhập Brand mới: "); String uBrand = sc.nextLine();
                        System.out.print("Nhập Giá mới: "); double uPrice = Double.parseDouble(sc.nextLine());
                        System.out.print("Nhập Kho mới: "); int uStock = Integer.parseInt(sc.nextLine());

                        pDao.update(new Product(uId, uName, uBrand, uPrice, uStock));
                        System.out.println("Cập nhật thành công!");
                        break;

                    case 4: // Xóa sản phẩm theo ID 
                        System.out.print("Nhập ID sản phẩm cần xóa: ");
                        int dId = Integer.parseInt(sc.nextLine());

                        try {
                            Product prodToDelete = pDao.selectById(dId);
                            if (prodToDelete == null) {
                                System.out.println("Lỗi: Không tìm thấy sản phẩm có ID phù hợp!");
                            } else {
                                System.out.println("--- Thông tin sản phẩm muốn xóa ---");
                                System.out.println("Tên: " + prodToDelete.getProduct_name() + " | Giá: " + prodToDelete.getPrice());
                                System.out.print("Bạn có chắc chắn muốn xóa không? (Y/N): ");
                                String confirm = sc.nextLine().trim();

                                if (confirm.equalsIgnoreCase("Y")) {
                                    pDao.delete(dId);
                                    System.out.println("Xóa thành công sản phẩm khỏi hệ thống!");
                                } else {
                                    System.out.println("Đã hủy thao tác xóa.");
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Lỗi: " + e.getMessage());
                        }
                        break;

                    case 5: // Tìm theo Brand
                        System.out.print("Nhập Brand cần tìm: ");
                        String bName = sc.nextLine();
                        ArrayList<Product> brandRes = pDao.searchByBrand(bName);
                        for (Product p : brandRes) {
                            System.out.println(p.getProduct_id() + " | " + p.getProduct_name() + " | " + p.getPrice() + " [Kho: " + p.getStock() + "]");
                        }
                        break;

                    case 6: // Tìm theo Khoảng giá
                        System.out.print("Giá thấp nhất: "); double min = Double.parseDouble(sc.nextLine());
                        System.out.print("Giá cao nhất: "); double max = Double.parseDouble(sc.nextLine());
                        ArrayList<Product> priceRes = pDao.searchByPriceRange(min, max);
                        System.out.println("Kết quả tìm kiếm từ " + min + "đ đến " + max + "đ:");
                        for (Product p : priceRes) {
                            System.out.println(p.getProduct_id() + " | " + p.getProduct_name() + " | " + p.getBrand() + " | " + p.getPrice());
                        }
                        break;

                    case 7: // Tìm kiếm theo tên sản phẩm kèm Số lượng tồn kho
                        System.out.print("Nhập tên sản phẩm cần tìm: ");
                        String keyword = sc.nextLine().trim();
                        ArrayList<Product> results = pDao.searchByNameWithStock(keyword);

                        if (results.isEmpty()) {
                            System.out.println("Không tìm thấy sản phẩm nào chứa từ khóa: " + keyword);
                        } else {
                            System.out.println("\n================ KẾT QUẢ TÌM KIẾM ================");
                            System.out.printf("%-5s | %-25s | %-12s | %-12s | %-10s\n", "ID", "Tên Sản Phẩm", "Hãng", "Giá Bán", "Số Lượng Tồn");
                            System.out.println("--------------------------------------------------------------------------------");
                            for (Product p : results) {
                                System.out.printf("%-5d | %-25s | %-12s | %-12.2f | %-10d\n", p.getProduct_id(), p.getProduct_name(), p.getBrand(), p.getPrice(), p.getStock());
                            }
                            System.out.println("==================================================");
                        }
                        break;

                    default:
                        System.out.println("Vui lòng chọn từ 1 đến 8!");
                }
            } catch (Exception e) {
                System.out.println("Thao tác thất bại, vui lòng kiểm tra lại dữ liệu nhập vào!");
            }
        }
    }

    // =======================
    // 2. QUẢN LÝ KHÁCH HÀNG
    private static void menuCustomer() {
        CustomerDao cDao = new CustomerDao();
        while (true) {
            System.out.println("\n========= QUẢN LÝ KHÁCH HÀNG =========");
            System.out.println("1. Hiển thị danh sách khách hàng");
            System.out.println("2. Thêm khách hàng mới");
            System.out.println("3. Cập nhật thông tin khách hàng");
            System.out.println("4. Xóa khách hàng theo ID");
            System.out.println("5. Quay lại menu chính");
            System.out.print("Chọn: ");
            try {
                int choice = Integer.parseInt(sc.nextLine());
                if (choice == 5) break;
                switch (choice) {
                    case 1:
                        ArrayList<Customer> list = cDao.selectAll();
                        for (Customer c : list) {
                            System.out.println(c.getCustomer_id() + " | " + c.getCustomer_name() + " | " + c.getPhone() + " | " + c.getEmail() + " | " + c.getAddress());
                        }
                        break;
                    case 2:
                        System.out.print("ID Khách: "); int id = Integer.parseInt(sc.nextLine());
                        System.out.print("Tên Khách: "); String name = sc.nextLine();
                        System.out.print("Số ĐT: "); String phone = sc.nextLine();
                        System.out.print("Email: "); String email = sc.nextLine();
                        System.out.print("Địa chỉ: "); String addr = sc.nextLine();
                        cDao.insert(new Customer(id, name, phone, email, addr));
                        System.out.println("Thêm thành công!");
                        break;

                    case 3: // Cập nhật thông tin khách hàng 
                        Customer currentCustomer = null;
                        int cuId = -1;
                        while (true) {
                            System.out.print("ID Khách cần sửa: ");
                            cuId = Integer.parseInt(sc.nextLine());
                            currentCustomer = cDao.selectById(cuId);
                            if (currentCustomer != null) {
                                break;
                            } else {
                                System.out.println("Lỗi: Không tìm thấy khách hàng có ID này. Vui lòng nhập lại!");
                            }
                        }

                        System.out.println("\n--- Thông tin hiện tại của khách hàng ---");
                        System.out.println("Tên: " + currentCustomer.getCustomer_name() + " | SĐT: " + currentCustomer.getPhone() + " | Email: " + currentCustomer.getEmail());
                        System.out.println("----------------------------------------");

                        System.out.print("Tên mới: "); String uName = sc.nextLine();
                        System.out.print("Số ĐT mới: "); String uPhone = sc.nextLine();
                        System.out.print("Email mới: "); String uEmail = sc.nextLine();
                        System.out.print("Địa chỉ mới: "); String uAddr = sc.nextLine();

                        cDao.update(new Customer(cuId, uName, uPhone, uEmail, uAddr));
                        System.out.println("Cập nhật thành công!");
                        break;

                    case 4: // Xóa khách hàng theo ID )
                        Customer deleteCustomer = null;
                        int dId = -1;
                        while (true) {
                            System.out.print("ID Khách cần xóa: ");
                            dId = Integer.parseInt(sc.nextLine());
                            deleteCustomer = cDao.selectById(dId);
                            if (deleteCustomer != null) {
                                break;
                            } else {
                                System.out.println("Lỗi: Không tìm thấy khách hàng có ID này. Vui lòng nhập lại!");
                            }
                        }

                        System.out.println("\n--- Thông tin khách hàng muốn xóa ---");
                        System.out.println("Tên: " + deleteCustomer.getCustomer_name() + " | SĐT: " + deleteCustomer.getPhone());
                        System.out.print("Bạn có chắc chắn muốn xóa không? (Y/N): ");
                        String confirm = sc.nextLine().trim();

                        if (confirm.equalsIgnoreCase("Y")) {
                            int deleteCount = cDao.delete(dId);
                            if (deleteCount > 0) {
                                System.out.println("Xóa thành công khách hàng!");
                            } else {
                                System.out.println("Xóa thất bại. Khách hàng có thể đang tồn tại trong hóa đơn.");
                            }
                        } else {
                            System.out.println("Đã hủy thao tác xóa khách hàng.");
                        }
                        break;
                }
            } catch (Exception e) { System.out.println("Lỗi nhập liệu!"); }
        }
    }

    // =======================
    // 3. QUẢN LÝ HÓA ĐƠN
    private static void menuInvoice() {
        InvoiceDao iDao = new InvoiceDao();
        while (true) {
            System.out.println("\n========= QUẢN LÝ HÓA ĐƠN =========");
            System.out.println("1. Hiển thị danh sách hóa đơn");
            System.out.println("2. Thêm mới hóa đơn");
            System.out.println("3. Tìm kiếm hóa đơn");
            System.out.println("4. Quay lại menu chính");
            System.out.print("Chọn: ");
            try {
                int choice = Integer.parseInt(sc.nextLine());
                if (choice == 4) break;

                switch (choice) {
                    case 1:
                        ArrayList<Invoice> list = iDao.selectAll();
                        System.out.println("Mã HĐ | Mã Khách | Ngày lập | Tổng tiền");
                        for (Invoice i : list) {
                            System.out.println(i.getInvoice_id() + " | " + i.getCustomer_id() + " | " + i.getCreated_at() + " | " + i.getTotal_amount());
                        }
                        break;

                    case 2:
                        System.out.print("Nhập mã hóa đơn mới: "); int iId = Integer.parseInt(sc.nextLine());
                        System.out.print("Nhập mã khách hàng: "); int cId = Integer.parseInt(sc.nextLine());
                        System.out.print("Nhập tổng trị giá hóa đơn: "); double total = Double.parseDouble(sc.nextLine());

                        Invoice invoice = new Invoice(iId, cId, new Timestamp(System.currentTimeMillis()), total);
                        iDao.insert(invoice);

                        System.out.println("--- NHẬP CHI TIẾT SẢN PHẨM MUA ---");
                        while (true) {
                            System.out.print("Nhập mã sản phẩm mua (hoặc nhập 0 để kết thúc): ");
                            int pId = Integer.parseInt(sc.nextLine());
                            if (pId == 0) break;

                            System.out.print("Nhập số lượng mua: ");
                            int qty = Integer.parseInt(sc.nextLine());
                            System.out.print("Nhập đơn giá tại thời điểm bán: ");
                            double uPrice = Double.parseDouble(sc.nextLine());

                            iDao.insertDetail(iId, pId, qty, uPrice);
                            System.out.println("-> Đã thêm sản phẩm vào chi tiết hóa đơn.");
                        }
                        System.out.println("Lập hóa đơn và lưu chi tiết thành công!");
                        break;

                    case 3:
                        menuSearchInvoice(iDao);
                        break;

                    default:
                        System.out.println("Vui lòng chọn từ 1 đến 4!");
                }
            } catch (Exception e) {
                System.out.println("Thao tác lỗi, vui lòng kiểm tra lại dữ liệu đầu vào!");
            }
        }
    }

    private static void menuSearchInvoice(InvoiceDao iDao) {
        while (true) {
            System.out.println("\n--- MENU TÌM KIẾM HÓA ĐƠN ---");
            System.out.println("1. Tìm theo tên khách hàng");
            System.out.println("2. Tìm theo ngày/tháng/năm");
            System.out.println("3. Quay lại menu hóa đơn");
            System.out.print("Chọn: ");
            try {
                int subChoice = Integer.parseInt(sc.nextLine());
                if (subChoice == 3) break;

                switch (subChoice) {
                    case 1:
                        System.out.print("Nhập tên khách hàng cần tìm: ");
                        String cName = sc.nextLine();
                        ArrayList<Invoice> resByName = iDao.searchByCustomerName(cName);
                        System.out.println("\nKết quả tìm kiếm cho khách hàng '" + cName + "':");
                        if (resByName.isEmpty()) {
                            System.out.println("Không tìm thấy hóa đơn nào!");
                        } else {
                            System.out.println("Mã HĐ | Mã Khách | Ngày lập | Tổng tiền");
                            for (Invoice i : resByName) {
                                System.out.println(i.getInvoice_id() + " | " + i.getCustomer_id() + " | " + i.getCreated_at() + " | " + i.getTotal_amount());
                            }
                        }
                        break;

                    case 2:
                        System.out.print("Nhập ngày cần tìm hóa đơn (Định dạng YYYY-MM-DD): ");
                        String targetDate = sc.nextLine();
                        ArrayList<Invoice> resByDate = iDao.searchByDate(targetDate);
                        System.out.println("\nDanh sách hóa đơn tìm thấy trong ngày " + targetDate + ":");
                        if (resByDate.isEmpty()) {
                            System.out.println("Không có hóa đơn nào được lập trong ngày này!");
                        } else {
                            System.out.println("Mã HĐ | Mã Khách | Ngày lập | Tổng tiền");
                            for (Invoice i : resByDate) {
                                System.out.println(i.getInvoice_id() + " | " + i.getCustomer_id() + " | " + i.getCreated_at() + " | " + i.getTotal_amount());
                            }
                        }
                        break;

                    default:
                        System.out.println("Vui lòng chọn từ 1 đến 3!");
                }
            } catch (Exception e) {
                System.out.println("Thao tác lỗi hoặc sai định dạng dữ liệu đầu vào!");
            }
        }
    }

    // =======================
    // 4. THỐNG KÊ DOANH THU
    private static void menuThongKe() {
        InvoiceDao iDao = new InvoiceDao();
        while (true) {
            System.out.println("\n========= THỐNG KÊ DOANH THU =========");
            System.out.println("1. Doanh thu theo ngày");
            System.out.println("2. Doanh thu theo tháng");
            System.out.println("3. Doanh thu theo năm");
            System.out.println("4. Quay lại menu chính");
            System.out.print("Chọn: ");
            try {
                int choice = Integer.parseInt(sc.nextLine());
                if (choice == 4) break;

                switch (choice) {
                    case 1:
                        System.out.print("Nhập ngày cần thống kê (Định dạng YYYY-MM-DD): ");
                        String dayInput = sc.nextLine();
                        double dayRevenue = iDao.getRevenueByDay(dayInput);
                        System.out.println("---------------------------------------");
                        System.out.println("Tổng doanh thu ngày " + dayInput + ": " + dayRevenue + " VND");
                        System.out.println("---------------------------------------");
                        break;

                    case 2:
                        System.out.print("Nhập tháng (1-12): ");
                        int month = Integer.parseInt(sc.nextLine());
                        System.out.print("Nhập năm (YYYY): ");
                        int yearForMonth = Integer.parseInt(sc.nextLine());
                        double monthRevenue = iDao.getRevenueByMonth(month, yearForMonth);
                        System.out.println("---------------------------------------");
                        System.out.println("Tổng doanh thu tháng " + month + "/" + yearForMonth + ": " + monthRevenue + " VND");
                        System.out.println("---------------------------------------");
                        break;

                    case 3:
                        System.out.print("Nhập năm cần thống kê (YYYY): ");
                        int year = Integer.parseInt(sc.nextLine());
                        double yearRevenue = iDao.getRevenueByYear(year);
                        System.out.println("---------------------------------------");
                        System.out.println("Tổng doanh thu năm " + year + ": " + yearRevenue + " VND");
                        System.out.println("---------------------------------------");
                        break;

                    default:
                        System.out.println("Vui lòng chọn từ 1 đến 4!");
                }
            } catch (Exception e) {
                System.out.println("Lỗi nhập liệu! Vui lòng kiểm tra lại định dạng số hoặc ngày tháng.");
            }
        }
    }
}
