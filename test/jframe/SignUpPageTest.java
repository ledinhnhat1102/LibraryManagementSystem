package jframe;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SignUpPageTest {

    private SignUpPage signUpPage;

    @Before
    public void setUp() {
        // Khởi tạo đối tượng SignUpPage trước mỗi bài kiểm tra
        signUpPage = new SignUpPage();
    }

    @Test
    public void testSignUpSuccess() {
        // Thiết lập thông tin hợp lệ
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
        // Thiết lập thông tin thiếu (bỏ trống một số trường)
        signUpPage.getTxtUsername().setText("");
        signUpPage.getTxtPassword().setText("");
        signUpPage.getTxtEmail().setText("nhat1234@gmail.com");
        signUpPage.getTxtMobile().setText("0987654321");
        signUpPage.getTxtutype().setSelectedItem("User");

        boolean isValid = signUpPage.validateUser();
        assertFalse("Đăng ký thất bại do bỏ trống trường thông tin", isValid);
    }
}
