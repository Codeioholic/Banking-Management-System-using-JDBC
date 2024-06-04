package BankingManagement;

import com.mysql.cj.protocol.Resultset;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class myjdbcproj {
    public static void main(String[] args) throws ClassNotFoundException {
        final String url = "jdbc:mysql://localhost:3306/banking_system";
        final String username = "root";
        final String password = "Ujjwal@2002";


        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

            int choice = 0;
            do {
                System.out.println("\n\n********* Banking Management System *********\n");

                //===============================
                // Display the menu
                //===============================
                System.out.println("1. Show Customer Records");
                System.out.println("2. Add Customer Record");
                System.out.println("3. Delete Customer Record");
                System.out.println("4. Update Customer Record for any attribute except Customer Number");
                System.out.println("5. Show Account Details of a Customer");
                System.out.println("6. Show Loan Details of a Customer");
                System.out.println("7. Deposit Money to an Account");
                System.out.println("8. Withdraw Money from an Account");
                System.out.println("9. Exit the Program");
                System.out.println("Enter your choice(1-9) :");

                //================================
                //Accept user's choice
                //================================
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

                choice = Integer.parseInt(br.readLine());

                switch (choice) {
                    case 1:
                        //===================
                        // Show Customer Record
                        //===================
                        String query1 = "SELECT * FROM CUSTOMER;";
                        ResultSet resultSet = statement.executeQuery(query1);

                        int a = 0;
                        System.out.print("CUST_NO\t\tNAME\t\t\tPHONE_NO\t\tCITY");
                        System.out.print("\n-----------------------------------------------------------------------");
                        while (resultSet.next()) {
                            System.out.print("\n"+resultSet.getString("CUST_NO")+"\t");
                            System.out.print("\t"+resultSet.getString("NAME"));
                            System.out.print("\t"+resultSet.getString("PHONE_NO"));
                            System.out.print("\t\t"+resultSet.getString("CITY"));
                            a++;
                        }
                        System.out.println();
                        System.out.println(a+" rows selected");
                        break;
                    case 2:
                        //====================
                        // Add Customer Record
                        // Accept input from the user
                        //====================

                        System.out.println("Enter Customer Number (MUST BE 5 CHARACTER LONG & START WITH LETTER 'C') :");
                        String cust_no = br.readLine();
                        System.out.println("Enter Customer Name :");
                        String name = br.readLine();
                        System.out.println("Enter Customer Phone number (10 digit) :");
                        long phone_no = Long.parseLong(br.readLine());
                        System.out.println("Enter Customer City location :");
                        String city = br.readLine();

                        String query2 = "INSERT INTO CUSTOMER VALUES ('"+cust_no+"','"+name+"','"+phone_no+"','"+city+"');";
                        int n = statement.executeUpdate(query2);

                        System.out.println("1 Row Inserted");
                        break;
                    case 3:
                        //=================
                        // Delete customer record
                        // Accept customer number from user
                        //=================
                        System.out.println("Enter the Customer number for deletion :");
                        String custDel = br.readLine();
                        String query3 = "DELETE FROM CUSTOMER WHERE CUST_NO='"+custDel+"'";
                        n = statement.executeUpdate(query3);
                        if (n>0) {
                            System.out.println("1 Row Deleted");
                        }
                        else {
                            System.out.println("0 Row Deleted");
                        }
                        break;
                    case 4:
                        //================
                        // Update Customer Record
                        // Accept customer number from user
                        //================
                        System.out.println("Enter choice for Customer Record update :");
                        System.out.println("1. Name\n2. Phone number\n3. City");

                        int UpdateChoice = Integer.parseInt(br.readLine());
                        switch (UpdateChoice) {
                            case 1 :
                                //====================
                                // Update name of Customer
                                //====================
                                System.out.println("Enter Customer number :");
                                cust_no = br.readLine();
                                System.out.println("Enter New Customer Name :");
                                String updateName = br.readLine();
                                String query4 = "UPDATE CUSTOMER SET NAME='" + updateName + "' WHERE CUST_NO='" + cust_no + "'";
                                n = statement.executeUpdate(query4);
                                if (n > 0) {
                                    System.out.println("1 Updated Sucessfully!!!");
                                } else {
                                    System.out.println("Update Failed!!!");
                                }
                                break;

                            case 2 :
                                //====================
                                // Update Phone number of Customer
                                //====================
                                System.out.println("Enter Customer number :");
                                cust_no = br.readLine();
                                System.out.println("Enter New Phone No :");
                                long updatePhone = Long.parseLong(br.readLine());
                                String query5 = "UPDATE CUSTOMER SET PHONE_NO='" + updatePhone + "' WHERE CUST_NO='" + cust_no + "'";
                                n = statement.executeUpdate(query5);
                                if (n > 0) {
                                    System.out.println("1 Updated Sucessfully!!!");
                                } else {
                                    System.out.println("Update Failed!!!");
                                }
                                break;

                            case 3 :
                                //=================
                                // Update City of Customer
                                //=================
                                System.out.println("Enter Customer number :");
                                cust_no = br.readLine();
                                System.out.println("Enter New Customer City :");
                                String updateCity = br.readLine();
                                String query6 = "UPDATE CUSTOMER SET CITY='" + updateCity + "' WHERE CUST_NO='" + cust_no + "'";
                                n = statement.executeUpdate(query6);
                                if (n > 0) {
                                    System.out.println("1 Updated Sucessfully!!!");
                                } else {
                                    System.out.println("Update Failed!!!");
                                }
                                break;

                            default :
                                System.out.println("Invalid Choice :/ ");
                        }
                    case 5:
                        //===============================
                        // Display account details
                        // Accept customer number from user
                        //===============================
                        System.out.println("Enter Customer Number :");
                        cust_no = br.readLine();
                        String query7 = "SELECT A.ACCOUNT_NO, A.TYPE, A.BALANCE, B.BRANCH_CODE, B.BRANCH_NAME, B.BRANCH_CITY FROM ACCOUNT A, BRANCH B, CUSTOMER C, DEPOSITOR D WHERE D.ACCOUNT_NO=A.ACCOUNT_NO AND A.BRANCH_CODE=B.BRANCH_CODE AND C.CUST_NO=D.CUST_NO AND C.CUST_NO='"+cust_no+"'";
                        resultSet = statement.executeQuery(query7);

                        if (!resultSet.next()) {
                            System.out.println("0 rows selected.");
                        } else {
                            System.out.format("Acc. No \tType \tBalance \tBranch Code \tBranch Name \t\tBranch City \n");
                            do {
                                String account_no = resultSet.getString("ACCOUNT_NO");
                                String type = resultSet.getString("TYPE");
                                Long balance = resultSet.getLong("BALANCE");
                                String branch_code = resultSet.getString("BRANCH_CODE");
                                String branch_name = resultSet.getString("BRANCH_NAME");
                                String branch_city = resultSet.getString("BRANCH_CITY");

                                System.out.format("%s \t\t%s \t %d\t\t %s\t\t %s\t %s\t\n",account_no,type,balance,branch_code,branch_name,branch_city);
                            } while (resultSet.next());
                        }
                        break;
                    case 6:
                        //======================
                        // Display loan details
                        // Accept customer number from user
                        //======================
                        System.out.println("Enter Customer number :");
                        cust_no = br.readLine();
                        String query8 = "SELECT C.*, L.LOAN_NO, L.AMOUNT, B.* FROM LOAN L, CUSTOMER C, BRANCH B WHERE L.BRANCH_CODE=B.BRANCH_CODE AND C.CUST_NO=L.CUST_NO AND C.CUST_NO='"+cust_no+"'";
                        resultSet = statement.executeQuery(query8);
                        if (!resultSet.next()) {
                            System.out.println("0 row selected!");
                        }
                        else {
                            System.out.println("CUST_NO\t\t\tNAME\t\tPHONE_NO\t\tCITY\t\tLOAN_NO\tAMOUNT\t\tBRANCH_CODE\t\tBRANCH_NAME\t\tBRANCH_CITY");
                            do {
                                String ccust = resultSet.getString("CUST_NO");
                                String cname = resultSet.getString("NAME");
                                Long cphone = resultSet.getLong("PHONE_NO");
                                String ccity = resultSet.getString("CITY");

                                String loan_no = resultSet.getString("LOAN_NO");
                                Long amount = resultSet.getLong("AMOUNT");
                                String branch_code = resultSet.getString("BRANCH_CODE");

                                String branch_name = resultSet.getString("BRANCH_NAME");

                                String branch_city = resultSet.getString("BRANCH_CITY");

                                System.out.format("%s\t %s\t\t %d\t\t %s\t\t %s\t %d\t %s\t\t %s\t\t %s\n",ccust, cname, cphone, ccity, loan_no, amount, branch_code, branch_name,branch_city);

                            } while (resultSet.next());
                        }
                        break;
                    case 7:
                        //============================
                        //Deposit money
                        // Accept the account number to be deposited in
                        // Message for transaction completion
                        // ===========================
                        System.out.println("Enter Account No :");
                        String acc_no = br.readLine();
                        System.out.println("Enter the amount to deposit : (Rs.)");
                        long amt = Long.parseLong(br.readLine());
                        String query9 = "UPDATE ACCOUNT SET BALANCE=BALANCE+"+amt+" WHERE ACCOUNT_NO='"+acc_no+"'";
                        n = statement.executeUpdate(query9);
                        if (n > 0) {
                            System.out.println("1 Updated Successfully!!!");
                        } else {
                            System.out.println("Update Failed!!!");
                        }
                        break;
                    case 8:
                        //==========================
                        //Withdraw money
                        // Accept the account number to be withdrawn from
                        // Handle appropriate withdrawal check conditions
                        // Message for transaction completion
                        //==========================
                        System.out.println("Enter Account NO :");
                        acc_no = br.readLine();
                        System.out.print("Enter the amount to withdrawal : (Rs.)");
                        amt = Long.parseLong(br.readLine());
                        String query10 = "SELECT BALANCE FROM ACCOUNT WHERE ACCOUNT_NO='"+acc_no+"'";
                        resultSet = statement.executeQuery(query10);

                        if (!resultSet.next()) {
                            System.out.println("Invalid Account Number!");
                        } else {
                            long bal = resultSet.getLong(1);
                            if (amt <= bal) {
                                String query11 = "UPDATE ACCOUNT SET BALANCE = BALANCE-"+amt+" WHERE ACCOUNT_NO='"+acc_no+"'";
                                n = statement.executeUpdate(query11);
                                if (n > 0) {
                                    System.out.println("Updated Successfully!!!");
                                } else {
                                    System.out.println("Update Failed!!!");
                                }
                            } else {
                                System.out.println("Insufficient Balance!!!");
                            }
                        }
                        break;
                    case 9:
                        //=====================
                        // Exit the Menu
                        //=====================
                        System.out.println("Thank You for using Our Banking Service");
                        System.out.println("Please Visit Us Again :D");
                        statement.close();
                        connection.close();
                        break;
                    default:
                        System.out.println("Invalid Choice :( ");
                }
            } while (choice != 9);

        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
