package jframe;

import org.junit.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;

public class LoginPageTest {
    private LoginPage loginPage;

    @Before
    public void setUp() {
        loginPage = new LoginPage();
    }

    private final String url = "jdbc:mysql://localhost:3306/library_management_system";
    private final String username = "root";
    private final String password = "";

    @Test
    public void testLoginWithValidCredentials() {
        String inputUsername = "admin";
        String inputPassword = "admin";
        String inputUserType = "Admin"; 

        boolean result = authenticateUser(inputUsername, inputPassword, inputUserType);
        assertTrue("Đăng nhập với thông tin hợp lệ phải thành công.", result);
    }

    @Test
    public void testLoginWithInvalidCredentials() {
        String inputUsername = "invalid_user";
        String inputPassword = "wrong_password";
        String inputUserType = "Admin"; 

        boolean result = authenticateUser(inputUsername, inputPassword, inputUserType);
        assertFalse("Đăng nhập với thông tin không hợp lệ phải thất bại.", result);
    }

    @Test
    public void testLoginWithEmptyFields() {
        String inputUsername = "";
        String inputPassword = "";
        String inputUserType = ""; 

        boolean result = authenticateUser(inputUsername, inputPassword, inputUserType);
        assertFalse("Đăng nhập với trường trống phải thất bại.", result);
    }

  
    private boolean authenticateUser(String inputUsername, String inputPassword, String inputUserType) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ? AND usertype = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, inputUsername);
            statement.setString(2, inputPassword);
            statement.setString(3, inputUserType); 

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Nếu có kết quả, đăng nhập thành công
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Trong trường hợp xảy ra lỗi, coi như đăng nhập thất bại
        }
    }
    
    //phan quyen
    @Test
    public void testLoginAsAdmin() {
        loginPage.getTxtUsername().setText("admin");
        loginPage.getTxtPassword().setText("admin");
        loginPage.getTxtUserType().setSelectedItem("Admin");
        loginPage.Connect();
        loginPage.login();

        String role = loginPage.getTxtUserType().getSelectedItem().toString();
        assertEquals("Admin", role);
    }

    @Test
    public void testLoginAsStudent() {

        loginPage.getTxtUsername().setText("huynguyen");
        loginPage.getTxtPassword().setText("huynguyen123");
        loginPage.getTxtUserType().setSelectedItem("Student");

        loginPage.Connect();

        loginPage.login();

        String role = loginPage.getTxtUserType().getSelectedItem().toString();
        assertEquals("Student", role);
    }
}
