package jframe;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class IssueBookTest {

    private IssueBook issueBook;

    @Before
    public void setUp() {
        // Khởi tạo lớp IssueBook trước mỗi bài test
        issueBook = new IssueBook();
    }

    @Test
    public void testGetBookDetailsFound() {
        // nhap mã sách
        issueBook.setIssueBookId("1"); 

        // tìm thay
        issueBook.setIssueBookName("Java Programming");
        issueBook.setIssueAuthor("John Doe");
        issueBook.setIssueQuantity("5");
        issueBook.setTxtBookError("");  

        assertEquals("1", issueBook.getIssueBookId());  
        assertEquals("Java Programming", issueBook.getIssueBookName());  
        assertEquals("John Doe", issueBook.getIssueAuthor());  
        assertEquals("5", issueBook.getIssueQuantity());  
        assertTrue(issueBook.getTxtBookError().isEmpty());  
    }

    @Test
    public void testGetBookDetailsNotFound() {
        //noo tim thay
        issueBook.setIssueBookId("999");  
        issueBook.setTxtBookError("Không tìm thấy mã sách!");  

        assertTrue(issueBook.getTxtBookError().contains("Không tìm thấy mã sách!"));
    }
    @Test
    public void testGetBookDetailsInvalidId() {
        issueBook.setIssueBookId(""); 
        issueBook.setTxtBookError("Mã sách không hợp lệ!");

        assertTrue(issueBook.getIssueBookId().isEmpty());
        assertTrue(issueBook.getTxtBookError().contains("Mã sách không hợp lệ!"));

        issueBook.setIssueBookId("123@abc"); 
        issueBook.setTxtBookError("Mã sách không hợp lệ!");

        assertEquals("123@abc", issueBook.getIssueBookId());
        assertTrue(issueBook.getTxtBookError().contains("Mã sách không hợp lệ!"));
    }


}

