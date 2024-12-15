package jframe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import javax.swing.JButton;

public class ManageStudentsTest {

    private ManageStudents manageStudents;

    @Before
    public void setUp() {
        manageStudents = new ManageStudents(); // Khởi tạo đối tượng trước mỗi bài test
    }

    @Test
    public void testAddButton() {
        JButton addButton = manageStudents.getAddbutton();
        assertNotNull("Add button should not be null", addButton);
        assertEquals("thêm", addButton.getText()); // Kiểm tra text trên nút
    }

    @Test
    public void testEditButton() {
        JButton editButton = manageStudents.getEditbutton();
        assertNotNull("Edit button should not be null", editButton);
        assertEquals("sửa", editButton.getText());
    }

    @Test
    public void testDeleteButton() {
        JButton deleteButton = manageStudents.getDeletebutton();
        assertNotNull("Delete button should not be null", deleteButton);
        assertEquals("xóa", deleteButton.getText());
    }
}
