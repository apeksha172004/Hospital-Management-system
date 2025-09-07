package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient(){
        System.out.print("Enter Patient's Name : ");
        String name = scanner.next();
        System.out.print("Enter Patient's Age : ");
        int age = scanner.nextInt();
        System.out.print("Enter Patient's Gender : ");
        String gender = scanner.next();

        try {
            String query = " INSERT INTO patients (name, age, gender) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0){
                System.out.println("Patient Inserted Successfully!!");
            } else {
                System.out.println("Oops!!,Something went wrong!!");
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void viewPatients(){
        String query = "SELECT * FROM patients";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+------------+---------------------------+----------+-------------+");
            System.out.println("| Patient ID | Name                      | Age      | Gender      |");
            System.out.println("+------------+---------------------------+----------+-------------+");
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("|%-12s|%-27s|%-10s|%-13s|");       // Helps in formatting the output...%-12s will leave 12 spaceses between bars.
                System.out.println("+------------+---------------------------+----------+-------------+");

            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean getPatientById(int id){

    }
}

