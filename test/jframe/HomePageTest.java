package jframe;

import org.junit.Test;
import static org.junit.Assert.*;

public class HomePageTest {
    
    public HomePageTest() {
    }

    @Test
    public void testConnect() {
        HomePage homePage = new HomePage();
        homePage.Connect();
        assertNotNull("Database connection should not be null", homePage.con);
    }
   

    @Test
    public void testBook_Load() {
        HomePage homePage = new HomePage();
        homePage.Connect();
        homePage.getJTable2();
        assertNotNull("jTable2 should not be null", homePage.getJTable2());
    }

    @Test
    public void testStudent_Load() {
        HomePage homePage = new HomePage();
        homePage.Connect();
        homePage.getJTable1();
        assertNotNull("jTable1 should not be null", homePage.getJTable1());
    }

    @Test
    public void testCountBookDetails() {
        HomePage homePage = new HomePage();
        homePage.Connect();
        homePage.countBookDetails();
        
        assertNotNull("lbl_noOfBooks should not be null", homePage.getLbl_noOfBooks());
        String expected = "8"; // Thay bằng giá trị mong đợi từ cơ sở dữ liệu
        assertEquals("Book count should match", expected, homePage.getLbl_noOfBooks().getText());
    }

    @Test
    public void testCountStudentDetails() {
        HomePage homePage = new HomePage();
        homePage.Connect();
        homePage.countStudentDetails();
        
        assertNotNull("lbl_noOfStudents should not be null", homePage.getLbl_noOfStudents());
        String expected = "7"; // Thay bằng giá trị mong đợi từ cơ sở dữ liệu
        assertEquals("Student count should match", expected, homePage.getLbl_noOfStudents().getText());
    }

    @Test
    public void testCountIssueBookDetails() {
        HomePage homePage = new HomePage();
        homePage.Connect();
        homePage.countIssueBookDetails();
        
        assertNotNull("lbl_noOfIssuedBooks should not be null", homePage.getLbl_noOfIssuedBooks());
        String expected = "13"; // Thay bằng giá trị mong đợi từ cơ sở dữ liệu
        assertEquals("Issued book count should match", expected, homePage.getLbl_noOfIssuedBooks().getText());
    }

    @Test
    public void testCountDefaultersDetails() {
        HomePage homePage = new HomePage();
        homePage.Connect();
        homePage.countDefaultersDetails();
        
        assertNotNull("lbl_noOfDefaulters should not be null", homePage.getLbl_noOfDefaulters());
        String expected = "16"; // Thay bằng giá trị mong đợi từ cơ sở dữ liệu
        assertEquals("Defaulter count should match", expected, homePage.getLbl_noOfDefaulters().getText());
    }


    @Test
    public void testMain() {
        try {
            String[] args = null;
            HomePage.main(args); // Kiểm tra hàm main
            assertTrue("Main method executed successfully", true);
        } catch (Exception e) {
            fail("Main method failed: " + e.getMessage());
        }
    }
}
