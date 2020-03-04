  try{

            //create my mysql database connection

            String mysqlUrl = "jdbc:mysql://127.0.0.1:3306/GCUbake";
            String username = "root";
            String password = "";
            // get a connection(Step 1)
            con = DriverManager.getConnection(mysqlUrl,username,password);
            //Create a statement(Step 2)
            String query = "insert into Customer (title, firstname, lastname, contactNo, username, passcode, gcuRole)" + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            pst = con.prepareStatement(query);
            //execute query(step 3)
            
           
            pst.setString(1,cmbGender.getText());
            pst.setString(2,txtFirstname.getText());
            pst.setString(3,txtLastname.getText());
            pst.setString(4,txtPhone.getText());
            pst.setString(5,txtEmail.getText());
            pst.setString(6,txtUsername.getText());
            pst.setString(7,txtPassword.getText());
           
            pst.execute();

            con.close();

        }
        catch (Exception e){

            System.err.println("Got an exception");
            System.err.println(e.getMessage());


        }
