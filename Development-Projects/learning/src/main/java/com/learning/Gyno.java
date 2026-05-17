package com.learning;

public class Gyno extends Doctor{

    public Gyno(String doctorName, double consultationFee)
    {
        super(doctorName , "Gynocologist", consultationFee);
    }

     @Override
    public String toString() 
    {
        return "Gynocologist{id=" + getDoctorID() +
               ", name='" + getDoctorName() + "'" +
               ", specialization='" + getDoctorspecialization() + "'" +
               ", consultationFee=" + getDoctorconsultationFee() + "}";
    }
    @Override
    public void setDoctorconsultationFee(double consultationFee) {
        if (consultationFee > 200) {
            super.setDoctorconsultationFee(consultationFee);
        } else 
            {
            System.out.println("Consultation fee must be greater than 200 for Gynocologist.");
        }
    }
    
}
