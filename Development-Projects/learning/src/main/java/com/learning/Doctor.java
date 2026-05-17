package com.learning;

public class Doctor extends HospitalStaff 
{
    private int doctorID = 0;
    private String doctorName = "";
    private String specialization = "";
    private double consultationFee = 0.00;
    private static int totalDoctors = 0;
    private static int nextId = 2001;

    public Doctor(String doctorName, String specialization, double consultationFee) {
       
        super(doctorName, "D" + nextId);
        this.doctorID = nextId++;
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.consultationFee = consultationFee;
        totalDoctors++;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public String getDoctorspecialization() {
        return specialization;
    }

    public double getDoctorconsultationFee() {
        return consultationFee;
    }

    public static int getTotalDoctors() {
        return totalDoctors;
    }
    @Override
    protected void setDoctorconsultationFee(double consultationFee) {
        if (consultationFee > 0) {
            this.consultationFee = consultationFee;
        } else {
            System.out.println("Consultation fee must be positive.");
        }
    }

    @Override
    public String toString() 
    {
        return "Doctor{id=" + doctorID +
               ", name='" + doctorName + "'" +
               ", specialization='" + specialization + "'" +
               ", consultationFee=" + consultationFee + "}";
    }
    @Override
    public String calculatePay() {
        return "Doctor " + doctorName + " earns a consultation fee of $" + consultationFee;
    }
}