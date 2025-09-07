package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

import static java.lang.Class.forName;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/hospital";
    private static final String username = "root";
    private static final String password = "#Apek1712";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection);
            while (true){
                System.out.println(" HOSPITAL MANAGEMENT SYSTEM ");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice){
                    case 1:
                        // Add Patients
                        patient.addPatient();
                        System.out.println();
                    case 2:
                        // View Patient
                        patient.viewPatients();
                        System.out.println();
                    case 3:
                        // View Doctors
                        doctor.viewDoctors();
                        System.out.println();
                    case 4:
                        // Book Appointment
                        bookAppointment(patient, doctor, connection, scanner);
                        System.out.println();
                    case 5:
                        return;
                    default:
                        System.out.println("Enter valid choice!!");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patient patient,Doctor doctor, Connection connection, Scanner scanner){
        System.out.println("Enter Patient ID: ");
        int patientID = scanner.nextInt();
        System.out.println("Enter Doctor ID: ");
        int doctorID = scanner.nextInt();
        System.out.println("Enter appointment date(YYYY-MM-DD): ");
        String appointmentDate = scanner.next();
        if (patient.getPatientById(patientID) && doctor.getDoctorById(doctorID)){
            if (checkDoctorAvailability(doctorID, appointmentDate, connection)){
                String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?, ?, ?) ";
                try {
                    PreparedStatement ps = connection.prepareStatement(appointmentQuery);
                    ps.setInt(1, patientID);
                    ps.setInt(2, doctorID);
                    ps.setString(3, appointmentDate);
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected > 0){
                        System.out.println(" Appointment booked successfully!!");
                    } else {
                        System.out.println("Appointment booking failed!!");
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }else {
                System.out.println("Doctor not available in this date!!");
            }
        }else {
            System.out.println("Neither doctor nor patient exists!! ");
        }
    }

    public static boolean checkDoctorAvailability(int doctorID, String appointmentdate, Connection connection) {
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, doctorID);
            ps.setString(2, appointmentdate);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                int count = resultSet.getInt(1);
                if (count == 0){
                    return true;
                }else {
                    return false;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
