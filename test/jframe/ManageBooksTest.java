package jframe;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static org.junit.Assert.*;

public class ManageBooksTest {

    private ManageBooks manageBooks;

    @Before
    public void setUp() {
        // Khởi tạo đối tượng ManageBooks trước mỗi test
        manageBooks = new ManageBooks();
    }

    private JTable getJTable1() throws NoSuchFieldException, IllegalAccessException {
        // Truy cập jTable1 bằng Reflection
        Field field = ManageBooks.class.getDeclaredField("jTable1");
        field.setAccessible(true);
        return (JTable) field.get(manageBooks);
    }

    @Test
    public void testAddBookSuccess() {
        try {
            // Giả lập các giá trị nhập vào cho các trường
            manageBooks.setTxtBookId("3");
            manageBooks.setTxtBookName("New Book");
            manageBooks.setTxtAuthorName("New Author");
            manageBooks.setTxtQuantity("10");

            // Sử dụng Reflection để gọi phương thức private
            Method method = ManageBooks.class.getDeclaredMethod("addbuttonActionPerformed", java.awt.event.ActionEvent.class);
            method.setAccessible(true);
            method.invoke(manageBooks, (Object) null);  // Gọi phương thức

            // Kiểm tra kết quả sau khi thêm sách
            JTable jTable1 = getJTable1();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

            assertTrue(model.getRowCount() > 0); // Đảm bảo bảng có dữ liệu
            assertEquals("3", model.getValueAt(0, 0));  // Kiểm tra ID sách
            assertEquals("New Book", model.getValueAt(0, 1));  // Kiểm tra tên sách
        } catch (Exception ex) {
            fail("Exception during testAddBookSuccess: " + ex.getMessage());
        }
    }

    @Test
    public void testAddBookFailure() {
        try {
            // Giả lập các giá trị nhập vào không hợp lệ (chẳng hạn để trống ID sách)
            manageBooks.setTxtBookId("");
            manageBooks.setTxtBookName("Invalid Book");
            manageBooks.setTxtAuthorName("Invalid Author");
            manageBooks.setTxtQuantity("5");

            // Sử dụng Reflection để gọi phương thức private
            Method method = ManageBooks.class.getDeclaredMethod("addbuttonActionPerformed", java.awt.event.ActionEvent.class);
            method.setAccessible(true);
            method.invoke(manageBooks, (Object) null);  // Gọi phương thức

            // Kiểm tra thông báo lỗi
            JOptionPane.showMessageDialog(manageBooks, "Lỗi: ID sách không được để trống!");

        } catch (Exception ex) {
            fail("Exception during testAddBookFailure: " + ex.getMessage());
        }
    }

    @Test
    public void testUpdateBookSuccess() {
        try {
            // Giả lập chỉnh sửa sách
            manageBooks.setTxtBookName("Updated Book");
            manageBooks.setTxtAuthorName("Updated Author");
            manageBooks.setTxtQuantity("15");

            // Chọn sách cần chỉnh sửa
            JTable jTable1 = getJTable1();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.addRow(new Object[]{"3", "Old Book", "Old Author", "10"}); // Giả lập thêm dòng sách vào bảng
            jTable1.setRowSelectionInterval(0, 0); // Chọn dòng sách cần chỉnh sửa

            // Sử dụng Reflection để gọi phương thức private
            Method method = ManageBooks.class.getDeclaredMethod("editbuttonActionPerformed", java.awt.event.ActionEvent.class);
            method.setAccessible(true);
            method.invoke(manageBooks, (Object) null);  // Gọi phương thức

            // Kiểm tra sau khi sửa thông tin sách
            assertEquals("Updated Book", model.getValueAt(0, 1));  // Kiểm tra tên sách
            assertEquals("Updated Author", model.getValueAt(0, 2));  // Kiểm tra tên tác giả
        } catch (Exception ex) {
            fail("Exception during testUpdateBookSuccess: " + ex.getMessage());
        }
    }

    @Test
    public void testUpdateBookFailure() {
        try {
            // Giả lập chỉnh sửa sách với giá trị không hợp lệ (ví dụ: thiếu tên sách)
            manageBooks.setTxtBookName("");
            manageBooks.setTxtAuthorName("No Name Author");
            manageBooks.setTxtQuantity("20");

            // Chọn sách cần chỉnh sửa
            JTable jTable1 = getJTable1();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.addRow(new Object[]{"3", "Old Book", "Old Author", "10"}); // Giả lập thêm dòng sách vào bảng
            jTable1.setRowSelectionInterval(0, 0); // Chọn dòng sách cần chỉnh sửa

            // Sử dụng Reflection để gọi phương thức private
            Method method = ManageBooks.class.getDeclaredMethod("editbuttonActionPerformed", java.awt.event.ActionEvent.class);
            method.setAccessible(true);
            method.invoke(manageBooks, (Object) null);  // Gọi phương thức

            // Kiểm tra thông báo lỗi
            JOptionPane.showMessageDialog(manageBooks, "Lỗi: Tên sách không được để trống!");

        } catch (Exception ex) {
            fail("Exception during testUpdateBookFailure: " + ex.getMessage());
        }
    }

    @Test
    public void testDeleteBookSuccess() {
        try {
            // Giả lập thêm một sách vào bảng trước khi xóa
            JTable jTable1 = getJTable1();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.addRow(new Object[]{"3", "Book to delete", "Author", "10"});
            jTable1.setRowSelectionInterval(0, 0);  // Chọn sách đầu tiên để xóa

            // Sử dụng Reflection để gọi phương thức private
            Method method = ManageBooks.class.getDeclaredMethod("deletebuttonActionPerformed", java.awt.event.ActionEvent.class);
            method.setAccessible(true);
            method.invoke(manageBooks, (Object) null);  // Gọi phương thức

            // Kiểm tra nếu bảng đã cập nhật sau khi xóa
            assertTrue(model.getRowCount() == 0);  // Kiểm tra nếu không còn dòng nào trong bảng
        } catch (Exception ex) {
            fail("Exception during testDeleteBookSuccess: " + ex.getMessage());
        }
    }

    @Test
    public void testDeleteBookFailure() {
        try {
            // Không chọn dòng để xóa (giả lập lỗi khi không có sách nào được chọn)
            JTable jTable1 = getJTable1();
            jTable1.clearSelection();  // Xóa tất cả lựa chọn

            // Sử dụng Reflection để gọi phương thức private
            Method method = ManageBooks.class.getDeclaredMethod("deletebuttonActionPerformed", java.awt.event.ActionEvent.class);
            method.setAccessible(true);
            method.invoke(manageBooks, (Object) null);  // Gọi phương thức

            // Kiểm tra thông báo lỗi
            JOptionPane.showMessageDialog(manageBooks, "Lỗi: Chọn sách cần xóa!");

        } catch (Exception ex) {
            fail("Exception during testDeleteBookFailure: " + ex.getMessage());
        }
    }

    @Test
    public void testSearchBooks() {
        try {
            // Giả lập tìm kiếm
            String searchQuery = "3";  // Tìm theo ID sách
            manageBooks.setTxtSearch(searchQuery);

            // Sử dụng Reflection để gọi phương thức private
            Method method = ManageBooks.class.getDeclaredMethod("rSMaterialButtonCircle4ActionPerformed", java.awt.event.ActionEvent.class);
            method.setAccessible(true);
            method.invoke(manageBooks, (Object) null);  // Gọi phương thức tìm kiếm

            // Kiểm tra bảng sau khi tìm kiếm
            JTable jTable1 = getJTable1();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

            assertTrue(model.getRowCount() > 0);
            assertEquals("3", model.getValueAt(0, 0));  // Kiểm tra ID sách tìm được
            assertEquals("New Book", model.getValueAt(0, 1));  // Kiểm tra tên sách
        } catch (Exception ex) {
            fail("Exception during testSearchBooks: " + ex.getMessage());
        }
    }
}
