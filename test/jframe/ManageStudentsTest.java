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
        assertEquals("thêm", addButton.getText());
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

    

    @Test
    public void testTxtStudentIdField() {
        assertNotNull("txt_studentid should not be null", manageStudents.getTxtStudentId());
        assertEquals("Default text for txt_studentid should be empty", "", manageStudents.getTxtStudentId().getText());
    }

    @Test
    public void testTxtStudentNameField() {
        assertNotNull("txt_studentname should not be null", manageStudents.getTxtStudentName());
        assertEquals("Default text for txt_studentname should be empty", "", manageStudents.getTxtStudentName().getText());
    }

    @Test
    public void testCheckDuplicateStudent() {
        manageStudents.getTxtStudentId().setText("S001");
        boolean isDuplicate = manageStudents.checkDublicateStudent();
        assertFalse("Student ID S001 should not be duplicate (assume empty DB for test)", isDuplicate);
    }

    @Test
    public void testStudentLoad() {
        manageStudents.Student_Load();
        int rowCount = manageStudents.getJTable1().getRowCount();
        assertTrue("Row count should be >= 0 after loading students", rowCount >= 0);
    }
}
