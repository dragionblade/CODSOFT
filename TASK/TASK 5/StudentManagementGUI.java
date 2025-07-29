import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

public class StudentManagementGUI extends JFrame {
    // DB Credentials --- CHANGE AS NEEDED
    static final String DB_URL = "jdbc:mysql://localhost:3306/studentdb";
    static final String USER = "root";
    static final String PASS = "43pr16pi";

    JTextField tfRoll, tfName, tfGrade, tfEmail, tfSearch;
    JButton btnAdd, btnEdit, btnDelete, btnClear, btnSearch;
    JTable table;
    DefaultTableModel model;

    public StudentManagementGUI() {
        setTitle("Student Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 450);
        setLocationRelativeTo(null);

        // Form
        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Student Details"));
        form.add(new JLabel("Roll Number:"));  tfRoll = new JTextField();  form.add(tfRoll);
        form.add(new JLabel("Name:"));        tfName = new JTextField();  form.add(tfName);
        form.add(new JLabel("Grade:"));       tfGrade = new JTextField(); form.add(tfGrade);
        form.add(new JLabel("Email:"));       tfEmail = new JTextField(); form.add(tfEmail);

        btnAdd = new JButton("Add");
        btnEdit = new JButton("Edit");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear Form");
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnAdd); btnPanel.add(btnEdit); btnPanel.add(btnDelete); btnPanel.add(btnClear);
        form.add(btnPanel);

        // Table
        model = new DefaultTableModel(new String[] {"Roll Number", "Name", "Grade", "Email"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Search bar
        JPanel searchPanel = new JPanel();
        tfSearch = new JTextField(15);
        btnSearch = new JButton("Search by Roll");
        searchPanel.add(tfSearch); searchPanel.add(btnSearch);

        // Layout
        JSplitPane hsplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, form, scrollPane);
        hsplit.setDividerLocation(270);
        hsplit.setResizeWeight(0);

        setLayout(new BorderLayout(5, 5));
        add(hsplit, BorderLayout.CENTER);
        add(searchPanel, BorderLayout.NORTH);

        // Events
        btnAdd.addActionListener(e -> addStudent());
        btnEdit.addActionListener(e -> editStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnClear.addActionListener(e -> clearFields());
        btnSearch.addActionListener(e -> searchStudent());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                int row = table.getSelectedRow();
                if(row >= 0){
                    tfRoll.setText((String) model.getValueAt(row, 0));
                    tfName.setText((String) model.getValueAt(row, 1));
                    tfGrade.setText((String) model.getValueAt(row, 2));
                    tfEmail.setText((String) model.getValueAt(row, 3));
                    tfRoll.setEnabled(false); // don't edit roll number; it's key
                }
            }
        });

        loadStudents();
        setVisible(true);
    }

    void addStudent(){
        String roll = tfRoll.getText().trim();
        String name = tfName.getText().trim();
        String grade = tfGrade.getText().trim();
        String email = tfEmail.getText().trim();

        if(!validateInput(name, roll, grade, email)) return;

        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS)){
            String sql = "INSERT INTO students (roll_number, name, grade, email) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, roll); ps.setString(2, name);
            ps.setString(3, grade); ps.setString(4, email);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student added.");
            clearFields();
            loadStudents();
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(this, "Roll number already exists.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void editStudent(){
        String roll = tfRoll.getText().trim();
        String name = tfName.getText().trim();
        String grade = tfGrade.getText().trim();
        String email = tfEmail.getText().trim();

        if(!validateInput(name, roll, grade, email)) return;

        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS)){
            String sql = "UPDATE students SET name=?, grade=?, email=? WHERE roll_number=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name); ps.setString(2, grade); ps.setString(3, email); ps.setString(4, roll);
            int result = ps.executeUpdate();
            if(result > 0){
                JOptionPane.showMessageDialog(this, "Student updated.");
                clearFields();
                loadStudents();
            } else {
                JOptionPane.showMessageDialog(this, "No such student found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void deleteStudent() {
        String roll = tfRoll.getText().trim();
        if(roll.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter roll number to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Delete this student?");
        if(confirm != JOptionPane.YES_OPTION) return;
        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS)){
            String sql = "DELETE FROM students WHERE roll_number=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, roll);
            int result = ps.executeUpdate();
            if(result > 0){
                JOptionPane.showMessageDialog(this, "Student deleted.");
                clearFields();
                loadStudents();
            } else {
                JOptionPane.showMessageDialog(this, "No such student found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e){
            JOptionPane.showMessageDialog(this, "Database error: "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void searchStudent() {
        String roll = tfSearch.getText().trim();
        if(roll.isEmpty()) { loadStudents(); return; }
        model.setRowCount(0);
        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS)){
            String sql = "SELECT * FROM students WHERE roll_number=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, roll);
            ResultSet rs = ps.executeQuery();
            boolean found = false;
            while(rs.next()){
                model.addRow(new Object[] {
                    rs.getString("roll_number"),
                    rs.getString("name"),
                    rs.getString("grade"),
                    rs.getString("email")
                });
                found = true;
            }
            if(!found) JOptionPane.showMessageDialog(this, "No such student.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void loadStudents() {
        model.setRowCount(0);
        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS)){
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");
            while(rs.next()){
                model.addRow(new Object[] {
                    rs.getString("roll_number"),
                    rs.getString("name"),
                    rs.getString("grade"),
                    rs.getString("email")
                });
            }
        } catch (SQLException e){
            JOptionPane.showMessageDialog(this, "Database error: "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void clearFields() {
        tfRoll.setText(""); tfName.setText(""); tfGrade.setText(""); tfEmail.setText("");
        tfRoll.setEnabled(true);
    }

    boolean validateInput(String name, String roll, String grade, String email){
        if(name.isEmpty() || roll.isEmpty() || grade.isEmpty() || email.isEmpty()){
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(!email.contains("@") || email.startsWith("@") || email.endsWith("@")){
            JOptionPane.showMessageDialog(this, "Invalid email format.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch(Exception e){}
        try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch(Exception e){}
        SwingUtilities.invokeLater(() -> new StudentManagementGUI());
    }
}
