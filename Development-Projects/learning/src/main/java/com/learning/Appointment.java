package com.learning;

public class Appointment 
{
    private String doctor;
    private String patientName;
    private String appointmentDate;
    private String appointmentTime;

   public void bookAppointment(String doctor, String patientName, String appointmentDate) {
        this.doctor = doctor;
        this.patientName = patientName;
        this.appointmentDate = appointmentDate;
    }
   public void bookAppointment(String doctor, String patientName, String appointmentDate, String appointmentTime) {
        this.doctor = doctor;
        this.patientName = patientName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
    }
   public String toString() {
        return "Appointment{" +
                "doctor='" + doctor + '\'' +
                ", patientName='" + patientName + '\'' +
                ", appointmentDate='" + appointmentDate + '\'' +
                (appointmentTime != null ? ", appointmentTime='" + appointmentTime + '\'' : "") +
                '}';
    }
}
