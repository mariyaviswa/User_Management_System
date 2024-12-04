package awtCrud;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class userFrame extends Frame implements ActionListener {

    Label lTitle, lName, lId, lAge, lCity, lstatus;
    TextField tName, tId, tAge, tCity;

    Button bsave, bclear, bdelete;

    String qry = "";
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    Statement stmt = null;

    //connection
    public void connect() {
        // connect sql database
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/dbviswa?characterEncoding=utf8";
            String username = "root";
            String password = "3757";
            con = DriverManager.getConnection(url, username, password);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Clear form Details
    public void clear() {
        tId.setText("");
        tName.setText("");
        tAge.setText("");
        tCity.setText("");
        tName.requestFocus();
    }

    public userFrame() {
        connect();
        // set frame default
        this.setVisible(true);
        this.setSize(1000, 600);
        this.setTitle("User Management System");
        this.setLayout(null);
        // frame colour for background
        Color formColor = new Color(53, 59, 72);
        setBackground(formColor);

        // create various font styles
        Font titlefont = new Font("arial", Font.BOLD, 20);
        Font labelfont = new Font("arial", Font.PLAIN, 18);
        Font txtfont = new Font("arial", Font.PLAIN, 15);

        // Set title
        lTitle = new Label("USER MANAGEMENT SYSTEM");
        lTitle.setBounds(370, 40, 300, 50);
        lTitle.setForeground(Color.YELLOW);
        lTitle.setFont(titlefont);
        add(lTitle);

        // ID field
        lId = new Label("ID");
        lId.setBounds(250, 100, 100, 30);
        lId.setFont(labelfont);
        lId.setForeground(Color.WHITE);
        add(lId);

        tId = new TextField();
        tId.setBounds(400, 100, 450, 30);
        tId.setFont(txtfont);
        tId.addActionListener(this);
        add(tId);

        // Name field
        lName = new Label("Name");
        lName.setBounds(250, 170, 100, 30);
        lName.setFont(labelfont);
        lName.setForeground(Color.WHITE);
        add(lName);

        tName = new TextField();
        tName.setBounds(400, 170, 450, 30);
        tName.setFont(txtfont);
        add(tName);

        // Age field
        lAge = new Label("Age");
        lAge.setBounds(250, 240, 100, 30);
        lAge.setFont(labelfont);
        lAge.setForeground(Color.WHITE);
        add(lAge);

        tAge = new TextField();
        tAge.setBounds(400, 240, 450, 30);
        tAge.setFont(txtfont);
        add(tAge);

        // City field
        lCity = new Label("City");
        lCity.setBounds(250, 310, 100, 30);
        lCity.setFont(labelfont);
        lCity.setForeground(Color.WHITE);
        add(lCity);

        tCity = new TextField();
        tCity.setBounds(400, 310, 450, 30);
        tCity.setFont(txtfont);
        add(tCity);

        // Save button
        bsave = new Button("Save");
        bsave.setBounds(430, 400, 100, 30);
        bsave.setForeground(Color.BLACK);
        bsave.setFont(labelfont);
        bsave.setBackground(Color.GREEN);
        bsave.addActionListener(this);
        add(bsave);

        // Clear button
        bclear = new Button("Clear");
        bclear.setBounds(580, 400, 100, 30);
        bclear.setForeground(Color.BLACK);
        bclear.setFont(labelfont);
        bclear.setBackground(Color.GRAY);
        bclear.addActionListener(this);
        add(bclear);

        // Delete button
        bdelete = new Button("Delete");
        bdelete.setBounds(730, 400, 100, 30);
        bdelete.setForeground(Color.BLACK);
        bdelete.setFont(labelfont);
        bdelete.setBackground(Color.RED);
        bdelete.addActionListener(this);
        add(bdelete);

        // This code for show activities
        // example - Data inserted successfully like this
        lstatus = new Label("---------");
        lstatus.setBounds(400, 460, 300, 30);
        lstatus.setFont(labelfont);
        lstatus.setForeground(Color.WHITE);
        add(lstatus);

        // close button code
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            // set all text-fields  in particular variables
            String id=tId.getText();
            String name=tName.getText();
            String age=tAge.getText();
            String city=tCity.getText();
         if(e.getSource().equals(tId))
         {
             // Get user details by id
             qry="SELECT ID,NAME,AGE,CITY from users where ID=" +tId.getText(); // This the query to show the details
             // customer id,name, city
             stmt=con.createStatement();// this will connect with sql database
             rs=stmt.executeQuery(qry); // this execute our query
             if(rs.next()){
                 // Here we get the details from database to our text fields
                 // This code for to show the details with id
                 tId.setText(rs.getString("ID"));
                 tName.setText(rs.getString("NAME"));
                 tAge.setText((rs.getString("AGE")));
                 tCity.setText(rs.getString("CITY"));

             }
             else {
                 clear();
                 // When we enter any id and enter "Enter" button it will show all the id's details
                 // now we can delete the details through clear() function
                 lstatus.setText("Invalid ID");
             }
         }
        if (e.getSource().equals(bclear)) {
            clear();
        }
        else if (e.getSource().equals(bsave)){
                if(id.isEmpty()){
                    // save details or insert the details
                    qry="insert into users (NAME,AGE,CITY) values(?,?,?)";
                    // ? marks are called placeholders
                    st=con.prepareStatement(qry);
                    st.setString(1,name); // Here 1 mention 1st ? mark
                    st.setString(2,age);// Here 2 mention 2nd ? mark
                    st.setString(3,city);// Here 3 mention 3rd ? mark
                    st.executeUpdate();
                    clear();
                    lstatus.setText("Data Inserted Successfully");
                }
                else{
                    // update the details already exist
                    qry="update users set NAME=?,AGE=?,CITY=? where ID=?";
                    st=con.prepareStatement(qry);
                    st.setString(1,name); // Here 1 mention 1st ? mark
                    st.setString(2,age);// Here 2 mention 2nd ? mark
                    st.setString(3,city);// Here 3 mention 3rd ? mark
                    st.executeUpdate();
                    clear();
                    lstatus.setText("Data Update Successfully");
                }

            }
        else if (e.getSource().equals(bdelete)){
              if(!id.isEmpty()){
                  qry="delete from users where ID=?";// Here ? is called placeholders
                  st=con.prepareStatement(qry);
                  st.setString(1,id); // Here if we enter any id in text field named ID
                  // Then it will go to query, and it will execute the command now we can delete the details
                  st.executeUpdate();
                  clear();
                  lstatus.setText("Data Deleted Successfully");
              }
              else{
                  lstatus.setText("Please enter ID");
              }

            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}


public class users {
    public static void main(String[] args) {
        userFrame frm = new userFrame();
    }
}
