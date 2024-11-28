/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package jframe;

import java.sql.Connection;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.swing.table.TableModel;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Acer
 */
public class ManageBooks extends javax.swing.JFrame {

    String bookName,author;
    int bookId,quantity;
    DefaultTableModel model;
    public ManageBooks() {
        initComponents();
        Connect();
        setBookDetailsToTable();
    }
    
    Connection con;
    
    public rojeru_san.complementos.RSTableMetro getTblBookDetails() {
        return tbl_bookDetails;
    }

    public void setTblBookDetails(DefaultTableModel model) {
        this.tbl_bookDetails.setModel(model);
    }

    
    public app.bolivia.swing.JCTextField getTxtBookId() {
        return txt_bookId;
    }

    public void setTxtBookId(String text) {
        this.txt_bookId.setText(text);
    }
    
    public app.bolivia.swing.JCTextField getTxtBookName() {
        return txt_bookName;
    }

    public void setTxtBookName(String text) {
        this.txt_bookName.setText(text);
    }
    
    public app.bolivia.swing.JCTextField getTxtAuthorName() {
        return txt_authorName;
    }

    public void setTxtAuthorName(String text) {
        this.txt_authorName.setText(text);
    }
    
    public app.bolivia.swing.JCTextField getTxtQuantity() {
        return txt_quantity;
    }

    public void setTxtQuantity(String text) {
        this.txt_quantity.setText(text);
    }
    
    public void Connect() {
        try {
            // Đăng ký Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Thiết lập kết nối
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms", "root", "");
            System.out.println("Kết nối cơ sở dữ liệu thành công!");
            
        } catch (Exception e) {
            // Xử lý ngoại lệ nếu có lỗi
            e.printStackTrace();
        }
    }
    
    public void setBookDetailsToTable(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_management_system", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from book_details");
            
            while(rs.next()){
                int bookId = rs.getInt("book_id");
                String bookName = rs.getString("book_name");
                String author = rs.getString("author");
                int quantity = rs.getInt("quantity");
                
                Object[] obj = {bookId,bookName,author,quantity};
                model = (DefaultTableModel) tbl_bookDetails.getModel();
                model.addRow(obj);
            }
        } catch (Exception e) {       
            e.printStackTrace();
        }
    }
    
    public boolean addBook(){
        boolean isAdded = false;

    try {
        // Kiểm tra các trường đầu vào
        if (txt_bookId.getText().isEmpty() || 
            txt_bookName.getText().isEmpty() || 
            txt_authorName.getText().isEmpty() || 
            txt_quantity.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!");
            return false;
        }

        // Kiểm tra và chuyển đổi giá trị số
        int bookId, quantity;
        try {
            bookId = Integer.parseInt(txt_bookId.getText());
            quantity = Integer.parseInt(txt_quantity.getText());

            if (quantity <= 0) {
                JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0!");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID sách và số lượng phải là số hợp lệ!");
            return false;
        }

        // Gán giá trị chuỗi
        bookName = txt_bookName.getText();
        author = txt_authorName.getText();

        // Kết nối cơ sở dữ liệu và thực hiện thêm sách
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_management_system", "root", "");
        String sql = "INSERT INTO book_details (book_id, book_name, author, quantity) VALUES (?, ?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, bookId);
        pst.setString(2, bookName);
        pst.setString(3, author);
        pst.setInt(4, quantity);

        int rowCount = pst.executeUpdate();
        isAdded = rowCount > 0; // Nếu thêm thành công, rowCount > 0
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Lỗi khi kết nối cơ sở dữ liệu: " + e.getMessage());
        e.printStackTrace();
    }

    return isAdded;
    }
    
    public boolean updateBook() {
        boolean isUpdated = false;
        bookId = Integer.parseInt(txt_bookId.getText());
        bookName = txt_bookName.getText();
        author = txt_authorName.getText();
        quantity = Integer.parseInt(txt_quantity.getText());
        
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/library_management_system", "root", "");
            String sql = "update book_details set book_name = ?,author = ?,quantity = ? where book_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, bookName);
            pst.setString(2, author);
            pst.setInt(3, quantity);
            pst.setInt(4, bookId);
            
            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                isUpdated = true;
            }else{
                isUpdated = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpdated;
    }
    
    public boolean deleteBook() {
        boolean isDeleted = false;
        bookId = Integer.parseInt(txt_bookId.getText());
        
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_management_system", "root", "");
            String sql = "delete from book_details where book_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, bookId);
            
            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                isDeleted = true;
            }else{
                isDeleted = false;
            }
        } catch (Exception e) {
            e.printStackTrace(); //cập nhật
        }
        return isDeleted;
    }
    
    public void searchBook(String searchText) {
    clearTable();
    try {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_management_system", "root", "");
        String sql = "SELECT * FROM book_details WHERE book_name LIKE ? OR author LIKE ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, "%" + searchText + "%");
        pst.setString(2, "%" + searchText + "%");

        ResultSet rs = pst.executeQuery();
        
        while (rs.next()) {
            int bookId = rs.getInt("book_id");
            String bookName = rs.getString("book_name");
            String author = rs.getString("author");
            int quantity = rs.getInt("quantity");

            Object[] obj = {bookId, bookName, author, quantity};
            model = (DefaultTableModel) tbl_bookDetails.getModel();
            model.addRow(obj);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
    
    public void clearTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_bookDetails.getModel();
        model.setRowCount(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        rSMaterialButtonCircle1 = new rojerusan.RSMaterialButtonCircle();
        rSMaterialButtonCircle2 = new rojerusan.RSMaterialButtonCircle();
        rSMaterialButtonCircle3 = new rojerusan.RSMaterialButtonCircle();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_quantity = new app.bolivia.swing.JCTextField();
        txt_bookId = new app.bolivia.swing.JCTextField();
        txt_bookName = new app.bolivia.swing.JCTextField();
        txt_authorName = new app.bolivia.swing.JCTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_bookDetails = new rojeru_san.complementos.RSTableMetro();
        jLabel11 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txt_search = new app.bolivia.swing.JCTextField();
        rSMaterialButtonCircle4 = new rojerusan.RSMaterialButtonCircle();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(51, 102, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nhập ID sách");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(144, 183, 103, -1));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nhập tên sách");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(144, 289, 102, -1));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Author Name");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(144, 394, 94, -1));

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Số lượng");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(144, 500, 123, -1));

        rSMaterialButtonCircle1.setBackground(new java.awt.Color(255, 102, 0));
        rSMaterialButtonCircle1.setText("Chỉnh sửa");
        rSMaterialButtonCircle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle1ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonCircle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(239, 592, 121, 66));

        rSMaterialButtonCircle2.setBackground(new java.awt.Color(255, 102, 0));
        rSMaterialButtonCircle2.setText("Xóa");
        rSMaterialButtonCircle2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle2ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonCircle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(405, 592, 121, 66));

        rSMaterialButtonCircle3.setBackground(new java.awt.Color(255, 102, 0));
        rSMaterialButtonCircle3.setText("Thêm");
        rSMaterialButtonCircle3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle3ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonCircle3, new org.netbeans.lib.awtextra.AbsoluteConstraints(73, 592, 121, 66));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Moleskine_26px.png"))); // NOI18N
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 311, -1, 40));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Contact_26px.png"))); // NOI18N
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 205, -1, 40));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Unit_26px.png"))); // NOI18N
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 522, -1, 40));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Collaborator_Male_26px.png"))); // NOI18N
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 416, -1, 40));

        txt_quantity.setBackground(new java.awt.Color(51, 102, 255));
        txt_quantity.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_quantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_quantityActionPerformed(evt);
            }
        });
        jPanel1.add(txt_quantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 530, 310, -1));

        txt_bookId.setBackground(new java.awt.Color(51, 102, 255));
        txt_bookId.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        jPanel1.add(txt_bookId, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 210, 310, -1));

        txt_bookName.setBackground(new java.awt.Color(51, 102, 255));
        txt_bookName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_bookName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_bookNameActionPerformed(evt);
            }
        });
        jPanel1.add(txt_bookName, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 310, 310, -1));

        txt_authorName.setBackground(new java.awt.Color(51, 102, 255));
        txt_authorName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_authorName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_authorNameActionPerformed(evt);
            }
        });
        jPanel1.add(txt_authorName, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 420, 310, -1));

        jPanel4.setBackground(new java.awt.Color(255, 102, 0));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Rewind_48px.png"))); // NOI18N
        jLabel1.setText("Quay lại");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 7, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 110, 60));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 580, 830));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(51, 102, 255));

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 35)); // NOI18N
        jLabel10.setText("X");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel10)
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 0, 120, 60));

        tbl_bookDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Sách", "Tên sách", "Tác giả", "Số lượng"
            }
        ));
        tbl_bookDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_bookDetailsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_bookDetails);
        if (tbl_bookDetails.getColumnModel().getColumnCount() > 0) {
            tbl_bookDetails.getColumnModel().getColumn(1).setResizable(false);
            tbl_bookDetails.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 270, 610, 310));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 102, 0));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Books_52px_1.png"))); // NOI18N
        jLabel11.setText("Quản lý sách");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 70, 240, -1));

        jPanel5.setBackground(new java.awt.Color(255, 102, 51));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 390, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 130, 390, 5));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel12.setText("Tìm kếm:");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 190, -1, -1));

        txt_search.setText("Nhập tên sách hoặc tên tác giả");
        jPanel3.add(txt_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 190, 290, -1));

        rSMaterialButtonCircle4.setBackground(new java.awt.Color(255, 102, 51));
        rSMaterialButtonCircle4.setText("Tìm kiếm");
        rSMaterialButtonCircle4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle4ActionPerformed(evt);
            }
        });
        jPanel3.add(rSMaterialButtonCircle4, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 180, 100, 50));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 0, 1090, 820));

        setSize(new java.awt.Dimension(1740, 832));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        //HomePage home = new HomePage();
        //home.setVisible(true);
        //dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void rSMaterialButtonCircle3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle3ActionPerformed
        if (addBook() == true) {
            JOptionPane.showMessageDialog(this, "Book Added");
            clearTable();
            setBookDetailsToTable();
        }else{
            JOptionPane.showMessageDialog(this, "Book Addition Failed");
        }
    }//GEN-LAST:event_rSMaterialButtonCircle3ActionPerformed

    private void txt_quantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_quantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_quantityActionPerformed

    private void txt_bookNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bookNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_bookNameActionPerformed

    private void txt_authorNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_authorNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_authorNameActionPerformed

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel10MouseClicked

    private void tbl_bookDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_bookDetailsMouseClicked
        int rowNo = tbl_bookDetails.getSelectedRow();
        TableModel model = tbl_bookDetails.getModel();
        
        txt_bookId.setText(model.getValueAt(rowNo, 0).toString());
        txt_bookName.setText(model.getValueAt(rowNo, 1).toString());
        txt_authorName.setText(model.getValueAt(rowNo, 2).toString());
        txt_quantity.setText(model.getValueAt(rowNo, 3).toString());
    }//GEN-LAST:event_tbl_bookDetailsMouseClicked

    private void rSMaterialButtonCircle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle1ActionPerformed
        if (updateBook() == true) {
            JOptionPane.showMessageDialog(this, "Book Updated");
            clearTable();
            setBookDetailsToTable();
        }else{
            JOptionPane.showMessageDialog(this, "Book Updation Failed");
        }
    }//GEN-LAST:event_rSMaterialButtonCircle1ActionPerformed

    private void rSMaterialButtonCircle2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle2ActionPerformed
        if (deleteBook() == true) {
            JOptionPane.showMessageDialog(this, "Book Deleted");
            clearTable();
            setBookDetailsToTable();
        }else{
            JOptionPane.showMessageDialog(this, "Book Deletion Failed");
        }
    }//GEN-LAST:event_rSMaterialButtonCircle2ActionPerformed

    private void rSMaterialButtonCircle4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle4ActionPerformed
        String searchText = txt_search.getText();
        searchBook(searchText);
    }//GEN-LAST:event_rSMaterialButtonCircle4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ManageBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManageBooks().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle1;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle2;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle3;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle4;
    private rojeru_san.complementos.RSTableMetro tbl_bookDetails;
    private app.bolivia.swing.JCTextField txt_authorName;
    private app.bolivia.swing.JCTextField txt_bookId;
    private app.bolivia.swing.JCTextField txt_bookName;
    private app.bolivia.swing.JCTextField txt_quantity;
    private app.bolivia.swing.JCTextField txt_search;
    // End of variables declaration//GEN-END:variables
}
