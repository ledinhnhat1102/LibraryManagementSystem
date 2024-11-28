/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package jframe;

import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.Connection;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import org.junit.Before;
/**
 *
 * @author Acer
 */
public class ManageBooksTest {
    
    private final String url = "jdbc:mysql://localhost:3306/library_management_system";
    private final String username = "root";
    private final String password = "";
    private ManageBooks manageBooks;

    @Before
    public void setUp() {
        // Khởi tạo đối tượng ManageBooks trước mỗi test
        manageBooks = new ManageBooks();
    }
    
    @Test
    public void testConnect() {
        // Gọi phương thức Connect()
        manageBooks.Connect();
        Connection connection = manageBooks.con;

        // Kiểm tra kết nối không null
        assertNotNull( "Kết nối cơ sở dữ liệu thất bại!", connection);
    }

    @Test
    public void testSetBookDetailsToTable() {
         // Gọi phương thức setBookDetailsToTable
        manageBooks.setBookDetailsToTable();

        // Xác nhận bảng không rỗng sau khi thêm dữ liệu
        int rowCount = manageBooks.getTblBookDetails().getRowCount();
        assertTrue("Bảng không có dữ liệu sau khi gọi setBookDetailsToTable!", rowCount > 0);
    }

    @Test
    public void testAddBook() {
        manageBooks.setTxtBookId("1001");
        manageBooks.setTxtBookName("Test Book");
        manageBooks.setTxtAuthorName("Test Author");
        manageBooks.setTxtQuantity("5");

        // Gọi phương thức addBook
        boolean result = manageBooks.addBook();

        // Xác nhận kết quả
        assertTrue("Không thể thêm sách vào cơ sở dữ liệu!", result);
    }

    @Test
    public void testUpdateBook() {
        manageBooks.setTxtBookId("1001");
        manageBooks.setTxtBookName("Updated Book");
        manageBooks.setTxtAuthorName("Updated Author");
        manageBooks.setTxtQuantity("10");

        // Gọi phương thức updateBook
        boolean result = manageBooks.updateBook();

        // Xác nhận kết quả
        assertTrue("Cập nhật sách không thành công!", result);
    }

    @Test
    public void testDeleteBook() {
        // Giả lập dữ liệu cho phương thức deleteBook
        manageBooks.setTxtBookId("1001");

        // Gọi phương thức deleteBook
        boolean result = manageBooks.deleteBook();

        // Xác nhận kết quả
        assertTrue("Xóa sách không thành công!", result);
    }

    @Test
    public void testSearchBook() {
        // Giả lập giá trị tìm kiếm
        String searchText = "Test";

        // Gọi phương thức searchBook
        manageBooks.searchBook(searchText);

        // Xác nhận kết quả: kiểm tra có ít nhất một hàng được trả về
        int rowCount = manageBooks.getTblBookDetails().getRowCount();
        assertTrue("Không tìm thấy sách nào phù hợp với tìm kiếm!", rowCount > 0);
    }

    @Test
    public void testClearTable() {
        // Lấy DefaultTableModel từ tbl_bookDetails
        DefaultTableModel model = (DefaultTableModel) manageBooks.getTblBookDetails().getModel();

    // Thêm một dòng giả lập vào model
        model.addRow(new Object[]{1, "Dummy", "Author", 10});

    // Gọi phương thức clearTable
        manageBooks.clearTable();

    // Xác nhận bảng trống
        int rowCount = manageBooks.getTblBookDetails().getRowCount();
        assertEquals("Bảng không được xóa sạch!", 0, rowCount);
    }   
}
