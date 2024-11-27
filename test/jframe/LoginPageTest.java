package jframe;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import jframe.LoginPage;

public class LoginPageTest {
    
    private Connection connection;
    private LoginPage loginPage;
    
    @Before
    public void setUp() {
        // Tạo kết nối MySQL trước mỗi bài kiểm tra
        try {
            // Thay đổi thông tin kết nối cho phù hợp với cơ sở dữ liệu của bạn
            String url = "jdbc:mysql://localhost:3306/library_management_system";
            String username = "root";
            String password = "";
            
            connection = DriverManager.getConnection(url, username, password);
            
            // Khởi tạo LoginPage hoặc đối tượng cần kiểm tra
            loginPage = new LoginPage();
        } catch (SQLException e) {
            fail("Kết nối cơ sở dữ liệu thất bại: " + e.getMessage());
        }
    }

    @Test
    public void testDatabaseConnection() {
        // Kiểm tra xem kết nối có thành công hay không
        assertNotNull("Kết nối cơ sở dữ liệu không thành công", connection);
    }

    @Test
    public void testLoginWithValidCredentials() {
        // Kiểm tra đăng nhập với thông tin hợp lệ
        loginPage.getTxtUsername().setText("admin");
        loginPage.getTxtPassword().setText("admin123");

        boolean result = loginPage.validateLogin();
        assertTrue("Đăng nhập với thông tin hợp lệ thất bại", result);
    }

    @Test
    public void testLoginWithInvalidCredentials() {
        // Kiểm tra đăng nhập với thông tin không hợp lệ
        loginPage.getTxtUsername().setText("invalidUser");
        loginPage.getTxtPassword().setText("wrongPassword");

        boolean result = loginPage.validateLogin();
        assertFalse("Đăng nhập với thông tin không hợp lệ không thất bại", result);
    }

    @Test
    public void testDatabaseQuery() {
        // Kiểm tra tên người dùng trong cơ sở dữ liệu
        String testUsername = "admin";
        boolean exists = false;
        
        try {
            String query = "SELECT * FROM users WHERE username = '" + testUsername + "'";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            fail("Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage());
        }
        
        assertTrue("Tên người dùng không tồn tại trong cơ sở dữ liệu", exists);
    }

    @After
    public void tearDown() {
        // Đảm bảo đóng kết nối sau mỗi bài kiểm tra
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            fail("Không thể đóng kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }
}
