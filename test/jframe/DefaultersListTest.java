/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package jframe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Acer
 */
public class DefaultersListTest {

    private DefaultersList defaultersList = new DefaultersList();

    // Phương thức trợ giúp để lấy bảng jTable2 thông qua Reflection
    private JTable getJTable2UsingReflection() throws NoSuchFieldException, IllegalAccessException {
        Field field = DefaultersList.class.getDeclaredField("jTable2");
        field.setAccessible(true);
        return (JTable) field.get(defaultersList);
    }

    // Phương thức trợ giúp để gọi các phương thức private thông qua Reflection
    private void invokePrivateMethod(String methodName, Class<?>[] paramTypes, Object[] params) throws Exception {
        Method method = DefaultersList.class.getDeclaredMethod(methodName, paramTypes);
        method.setAccessible(true);
        method.invoke(defaultersList, params);
    }

    @Test
    public void testRecord_Load() {
        try {
            // Kết nối và tải dữ liệu vào bảng
            defaultersList.Connect();
            invokePrivateMethod("Record_Load", null, null); // Gọi phương thức private Record_Load

            // Lấy bảng jTable2 thông qua Reflection
            JTable table = getJTable2UsingReflection();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            
            // Kiểm tra số lượng dòng trong bảng
            assertTrue(model.getRowCount() > 0);
        } catch (Exception e) {
            fail("Failed to load records: " + e.getMessage());
        }
    }

    @Test
    public void testClearTable() {
        try {
            // Thêm một số dữ liệu vào bảng
            DefaultTableModel model = (DefaultTableModel) getJTable2UsingReflection().getModel();
            model.addRow(new Object[] {1, "Test Book", "Test Student", "2025-01-01", "2025-01-10", "pending"});
            
            // Gọi phương thức private clearTable
            invokePrivateMethod("clearTable", null, null);
            
            // Kiểm tra bảng đã được xóa hay chưa
            assertEquals(0, model.getRowCount());
        } catch (Exception e) {
            fail("clearTable failed: " + e.getMessage());
        }
    }

    @Test
    public void testSearch_ValidId() {
        try {
            // Nhập ID hợp lệ vào ô tìm kiếm
            defaultersList.setTxtSearchInput("123");

            // Gọi phương thức private search_buttonActionPerformed
            invokePrivateMethod("search_buttonActionPerformed", new Class<?>[]{java.awt.event.ActionEvent.class}, new Object[]{null});

            // Lấy bảng jTable2 và kiểm tra kết quả tìm kiếm
            JTable table = getJTable2UsingReflection();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            
            // Kiểm tra có dữ liệu tìm thấy không
            assertTrue(model.getRowCount() > 0);
        } catch (Exception e) {
            fail("Search failed: " + e.getMessage());
        }
    }

    @Test
    public void testSearch_InvalidId() {
        try {
            // Nhập ID không hợp lệ vào ô tìm kiếm
            defaultersList.setTxtSearchInput("9999");

            // Gọi phương thức private search_buttonActionPerformed
            invokePrivateMethod("search_buttonActionPerformed", new Class<?>[]{java.awt.event.ActionEvent.class}, new Object[]{null});

            // Lấy bảng jTable2 và kiểm tra kết quả tìm kiếm
            JTable table = getJTable2UsingReflection();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            
            // Kiểm tra bảng không có kết quả tìm kiếm
            assertEquals(0, model.getRowCount());
        } catch (Exception e) {
            fail("Search failed: " + e.getMessage());
        }
    }

    @Test
    public void testClearSearch() {
        try {
            // Nhập ID hợp lệ vào ô tìm kiếm và thực hiện tìm kiếm
            defaultersList.setTxtSearchInput("123");
            invokePrivateMethod("search_buttonActionPerformed", new Class<?>[]{java.awt.event.ActionEvent.class}, new Object[]{null});

            // Kiểm tra bảng có dữ liệu sau khi tìm kiếm
            JTable table = getJTable2UsingReflection();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            assertTrue(model.getRowCount() > 0);

            // Bấm nút "Clear" để xóa tìm kiếm
            invokePrivateMethod("clearSearch", null, null);

            // Kiểm tra bảng sau khi xóa tìm kiếm
            assertEquals(0, model.getRowCount());
        } catch (Exception e) {
            fail("Clear search failed: " + e.getMessage());
        }
    }
}
