package jframe;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SignUpPageTest {

    private SignUpPage signUpPage;

    @Before
    public void setUp() {
        signUpPage = new SignUpPage();
    }

    @Test
    public void testSignUpSuccess() {
        signUpPage.getTxtUsername().setText("nhat1234");
        signUpPage.getTxtPassword().setText("nhat1234");
        signUpPage.getTxtEmail().setText("nhat1234@gmail.com.com");
        signUpPage.getTxtMobile().setText("0987654321");
        signUpPage.getTxtutype().setSelectedItem("User");

        boolean isValid = signUpPage.validateUser();
        assertTrue("Đăng ký thành công với thông tin hợp lệ", isValid);
    }

    @Test
    public void testSignUpFailWithEmptyFields() {
        signUpPage.getTxtUsername().setText("");
        signUpPage.getTxtPassword().setText("");
        signUpPage.getTxtEmail().setText("nhat1234@gmail.com");
        signUpPage.getTxtMobile().setText("0987654321");
        signUpPage.getTxtutype().setSelectedItem("User");

        boolean isValid = signUpPage.validateUser();
        assertFalse("Đăng ký thất bại do bỏ trống trường thông tin", isValid);
    }
    
    //phan quyen
     @Test
    public void testAssignAdminRole() {
        signUpPage.getTxtutype().setSelectedItem("Admin");
        boolean isRoleValid = signUpPage.assignRole();
        assertTrue("Vai trò Admin được chấp nhận.", isRoleValid);
    }

    @Test
    public void testAssignStudentRole() {
        signUpPage.getTxtutype().setSelectedItem("Student");
        boolean isRoleValid = signUpPage.assignRole();
        assertTrue("Vai trò Student được chấp nhận.", isRoleValid);
    }

    @Test
    public void testAssignInvalidRole() {
        signUpPage.getTxtutype().setSelectedItem("InvalidRole");
        boolean isRoleValid = signUpPage.assignRole();
        assertFalse("Vai trò không hợp lệ bị từ chối.", isRoleValid);
    }
    
}