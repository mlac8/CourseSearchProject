/* Main.java
 *
 * CSCI 112 Summer 2021
 * last edited Jul. 14, 2021 by M. Lacanilao
 *
 * Simple database project that allows the user to search for CSCI/CIS courses
 * offered by the Community College of Philadelphia in the Fall 2014 term
 *
 * Connects to remote MySQL database CWHDemo
 *
 * host IP address: 160.153.75.195
 * database:    CWHDemo
 * username:    CWHDemo
 * password:    Cwhdemo%123 (NOTE: PASSWORD IS CASE SENSITIVE)
 *
 * Table metadata, which can be retrieved with the query "DESCRIBE fall2014;"
 * crn        char(20)
 * subject    varchar(5)
 * course     varchar(5)
 * section    varchar(5)
 * credits    integer
 * time       varchar(20)
 * days       varchar(8)
 * term       varchar(5)
 * campus     varchar(5)
 * room       varchar(8)
 * enrollment integer
 */


package CourseSearchProject;
import java.sql.*;
import java.io.*;

public class Main {

    public static void main(String[] args)
            throws Exception {
            System.out.println("Connecting to the database...\n ");

        // Connect to a database by establishing a Connection object
        Connection conn = DriverManager.getConnection("jdbc:mysql://160.153.75.195/CWHDemo", "CWHDemo", "Cwhdemo%123");


        System.out.println("Database connection established.\n");

            // Create a statement Object for this database connection
            Statement st = conn.createStatement();

            // call a method that performs a query using Statement st
            selectAll(st);

            // call a method that performs our custom query
            selectSome(st);

            // Close the connection
            conn.close();
        } // end main()

    /* following method will retrieve and write every item stored in the
     CSCI column from the fall2014 table as a string.
     */
    private static void selectAll(Statement s) throws Exception {
        // create File class object
        java.io.File myCourses = new java.io.File("myCourses.csv");

        // create PrintWriter text output stream and link it to the File myCourses
        java.io.PrintWriter outfile = new java.io.PrintWriter (myCourses);

        String queryString; // a String to hold an SQL query
        ResultSet rs; // the result set from an SQL query as a table

        // Create SQL query as a string
        queryString = "SELECT * FROM fall2014 WHERE subject = 'csci' ORDER BY course;";

        // send statement executing query and save the result set
        rs = s.executeQuery (queryString);

        // print statements for query
        outfile.println("CRN"+","+"Subject"+","+"Course"+","+
                "Section"+","+"Time"+","+"Days");

        /* iterate the result set and print the crn, subject, course, section,
        time, and days attributes */
        while (rs.next ()) {
            outfile.println (rs.getString (1) + "," + rs.getString (2)
                    + "," + rs.getString (3) + "," + rs.getString (4) + ","
                    + rs.getString (6)+","+rs.getString (7));
        }
        outfile.close ();

    } // end selectAll()

    /* this method will retrieve and write items that matches the student's
    course criteria.

    We will be reusing the format I used in the SelectAll method, except I will not
    create a new .csv file. The results of our custom query will be posted inside the
    Java program itself.
     */
    private static void selectSome(Statement s)
            throws SQLException, ClassNotFoundException {

        String queryString;
        ResultSet rs;

        // our query will be "Which CIS205 sections are available on Tuesdays and Thursdays?"
        queryString = "SELECT * FROM fall2014 WHERE subject = 'cis' AND course = '205' AND days = 'tr';";

        rs = s.executeQuery (queryString);

        System.out.println (queryString);
        System.out.printf ("%-20s%-20s%-20s%-20s%-20s%-20s%n", "CRN", "Subject", "Course",
                "Section", "Time", "Days");
        System.out.println ("************************************************");

        while (rs.next ()) {
            System.out.printf ("%-20s%-20s%-20s%-20s%-20s%-20s%n", rs.getString (1),
                    rs.getString (2), rs.getString (3),
                    rs.getString (4), rs.getString (6),
                    rs.getString (7));
        }
    } // end selectSome
} // end class Main
