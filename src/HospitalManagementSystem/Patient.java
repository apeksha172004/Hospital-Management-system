package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private final Connection connection;
    private final Scanner scanner;

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
                System.out.printf("| %-10s | %-25s | %-8s | %-11s |\n",id, name, age, gender);       // Helps in formatting the output...%-12s will leave 12 spaces between bars.
                System.out.println("+------------+---------------------------+----------+-------------+");

            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean getPatientById(int id){
        String query = "SELECT * FROM patients WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                return true;
            } else {
                return false;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}

