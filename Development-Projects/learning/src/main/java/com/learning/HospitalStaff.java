package com.learning;

 abstract class HospitalStaff
{
    private String staffName;
    private String staffId;

    public HospitalStaff(String staffName, String staffId) {
        this.staffName = staffName;
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getStaffId() {
        return staffId;
    }

   abstract public String calculatePay();
   abstract protected void setDoctorconsultationFee(double fee);
}