package com.learning;
public class Main
 {
        public static void main( String[] args )
        {
            
    HospitalStaff Keerthana = new Doctor("Keerthana", "Cardiology", 500.00);
    HospitalStaff Monish = new Doctor("Monish", "Neurology", 600.00);
    HospitalStaff swetha = new  Doctor("swetha", "Neurology", 600.00);
    System.out.println(Keerthana.calculatePay());
    System.out.println(Monish.calculatePay());
    System.out.println(swetha.calculatePay());

   System.out.println(Keerthana.toString());
                System.out.println(Monish.toString());
                System.out.println(swetha.toString());
            System.out.println("Total Doctors: " + Doctor.getTotalDoctors());
Keerthana.setDoctorconsultationFee(-10);
Keerthana.setDoctorconsultationFee(700.00);
 System.out.println(Keerthana.toString());

 Gyno gynocologist = new Gyno("Dr. Smith", 250.00);

 System.out.println(gynocologist.toString());
 gynocologist.setDoctorconsultationFee(200);
    gynocologist.setDoctorconsultationFee(300);
    System.out.println(gynocologist.toString());

    Appointment appointment1 = new Appointment();
    appointment1.bookAppointment("Keerthana", "Alice", "2024-07-01");
    Appointment appointment2 = new Appointment();
    appointment2.bookAppointment("Monish", "Bob", "2024-07-02", "10:00");

    System.out.println(appointment1.toString());
    System.out.println(appointment2.toString());

    
}
 }