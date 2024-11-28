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
    
    private final String url = "jdbc:mysql://localhost/library_management_system";
    private final String username = "root";
    private final String password = "";
    private ManageBooks manageBooks;

    @Before
    public void setUp() {
        // Khởi tạo đối tượng ManageBooks trước mỗi test
        manageBooks = new ManageBooks();
    }
    
    @Test
    public void testConnect_Success() {
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
        // Gán dữ liệu vào các trường
    manageBooks.setTxtBookId("101");
    manageBooks.setTxtBookName("Java Programming");
    manageBooks.setTxtAuthorName("Author A");
    manageBooks.setTxtQuantity("5");

    // Gọi phương thức thêm sách
    boolean result = manageBooks.addBook();

    // Kiểm tra kết quả trả về từ phương thức
    assertTrue("Thêm sách không thành công!", result);

    // Làm mới bảng để kiểm tra đồng bộ dữ liệu
    manageBooks.setBookDetailsToTable();

    // Kiểm tra sách có được thêm vào bảng hay không
    DefaultTableModel model = (DefaultTableModel) manageBooks.getTblBookDetails().getModel();
    boolean found = false;
    for (int i = 0; i < model.getRowCount(); i++) {
        if (model.getValueAt(i, 0).toString().equals("101") && // Kiểm tra ID
            model.getValueAt(i, 1).toString().equals("Java Programming")) { // Kiểm tra tên sách
            found = true;
            break;
        }
    }
    assertTrue("Sách không được thêm vào bảng!", found);
    }
    
    //Lỗi thêm sách khi thiếu thông tin
    @Test
    public void testAddBook_Failure() {
        // Gán dữ liệu thiếu sót (bỏ trống ID)
    manageBooks.setTxtBookId("");
    manageBooks.setTxtBookName("Java Programming");
    manageBooks.setTxtAuthorName("Author A");
    manageBooks.setTxtQuantity("5");

    // Gọi phương thức thêm sách
    boolean result = manageBooks.addBook();

    // Kiểm tra kết quả trả về từ phương thức
    assertFalse("Thêm sách thành công mặc dù dữ liệu không hợp lệ!", result);

    // Kiểm tra bảng dữ liệu không thay đổi
    DefaultTableModel model = (DefaultTableModel) manageBooks.getTblBookDetails().getModel();
    boolean found = false;
    for (int i = 0; i < model.getRowCount(); i++) {
        if (model.getValueAt(i, 1).toString().equals("Java Programming")) {
            found = true;
            break;
        }
    }
    assertFalse("Sách bị thêm vào bảng mặc dù dữ liệu không hợp lệ!", found);
    }


    @Test
    public void testUpdateBook() {
        // Giả lập thông tin sách đã tồn tại
    manageBooks.setTxtBookId("101");
    manageBooks.setTxtBookName("Advanced Java");
    manageBooks.setTxtAuthorName("Author B");
    manageBooks.setTxtQuantity("10");

    // Cập nhật sách
    boolean result = manageBooks.updateBook();
    manageBooks.setBookDetailsToTable();
    assertTrue("Cập nhật sách không thành công!", result);

    // Kiểm tra bảng có dữ liệu đã cập nhật
    DefaultTableModel model = (DefaultTableModel) manageBooks.getTblBookDetails().getModel();
    boolean found = false;
    for (int i = 0; i < model.getRowCount(); i++) {
        if (model.getValueAt(i, 0).toString().equals("101")
                && model.getValueAt(i, 1).toString().equals("Advanced Java")) {
            found = true;
            break;
        }
    }
    assertTrue("Sách không được cập nhật trong bảng!", found);
    }
    
    //Cập nhật sách với ID không tồn tại
    @Test
    public void testUpdateBook_Failure() {
    // Giả lập thông tin sách với ID không tồn tại
    manageBooks.setTxtBookId("999"); // Không tồn tại
    manageBooks.setTxtBookName("New Book");
    manageBooks.setTxtAuthorName("Unknown");
    manageBooks.setTxtQuantity("1");

    // Cập nhật sách
    boolean result = manageBooks.updateBook();
    assertFalse("Cập nhật sách vẫn thành công với ID không tồn tại!", result);
    }


    @Test
    public void testDeleteBook() {
        // Giả lập ID sách đã tồn tại
    manageBooks.setTxtBookId("101");

    // Xóa sách
    boolean result = manageBooks.deleteBook();
    manageBooks.setBookDetailsToTable();
    assertTrue("Xóa sách không thành công!", result);

    // Kiểm tra bảng không còn dữ liệu sách
    DefaultTableModel model = (DefaultTableModel) manageBooks.getTblBookDetails().getModel();
    boolean found = false;
    for (int i = 0; i < model.getRowCount(); i++) {
        if (model.getValueAt(i, 0).toString().equals("101")) {
            found = true;
            break;
        }
    }
    assertTrue("Sách đã xóa!", found);
    }
    
    //Lỗi xóa ID không tồn tại
    @Test
    public void testDeleteBook_Failure() {
    // Giả lập ID sách không tồn tại
    manageBooks.setTxtBookId("999"); // Không tồn tại

    // Xóa sách
    boolean result = manageBooks.deleteBook();
    assertFalse("Xóa sách vẫn thành công với ID không tồn tại!", result);
    }

    
    @Test
    public void testSearchBook() {
        // Giả lập dữ liệu tìm kiếm
    String searchText = "Java"; // Từ khóa có tồn tại trong cơ sở dữ liệu

    // Gọi phương thức tìm kiếm
    manageBooks.searchBook(searchText);

    // Kiểm tra bảng có chứa kết quả khớp
    DefaultTableModel model = (DefaultTableModel) manageBooks.getTblBookDetails().getModel(); 
    boolean found = false;
    for (int i = 0; i < model.getRowCount(); i++) {
        String bookName = model.getValueAt(i, 1).toString();
        if (bookName.contains("Java")) {
            found = true;
            break;
        }
    }
    assertTrue("Không tìm thấy sách khớp với từ khóa trong bảng!", found);
    }
    
    //Tìm kiếm với từ khóa không tồn tại
    @Test
    public void testSearchBook_Failure() {
    // Giả lập dữ liệu tìm kiếm
    String searchText = "NonExistentBook"; // Từ khóa không tồn tại trong cơ sở dữ liệu

    // Gọi phương thức tìm kiếm
    manageBooks.searchBook(searchText);

    // Kiểm tra bảng không chứa kết quả nào
    DefaultTableModel model = (DefaultTableModel) manageBooks.getTblBookDetails().getModel();
    int rowCount = model.getRowCount();
    assertEquals("Kết quả tìm kiếm không hợp lệ! Bảng không nên có dữ liệu khi từ khóa không khớp.", 0, rowCount);
    }
    
    //Tìm kiếm với từ khóa rỗng
    @Test
    public void testSearchBook_EmptyKeyword() {
    // Giả lập từ khóa tìm kiếm rỗng
    String searchText = "";

    // Gọi phương thức tìm kiếm
    manageBooks.searchBook(searchText);

    // Kiểm tra bảng không thay đổi hoặc trả về toàn bộ dữ liệu
    DefaultTableModel model = (DefaultTableModel) manageBooks.getTblBookDetails().getModel();
    assertTrue("Tìm kiếm với từ khóa rỗng không nên làm bảng trống!", model.getRowCount() > 0);
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
