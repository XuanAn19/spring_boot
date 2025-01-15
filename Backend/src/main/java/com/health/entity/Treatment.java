package com.health.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Treatments")
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long treatmentId;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;  // Liên kết với cuộc hẹn

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // Liên kết với bệnh nhân

    private String diagnosis;  // Chẩn đoán bệnh
    private String treatmentMethod;  // Phương pháp điều trị
    private String notes;  // Ghi chú của bác sĩ
    private String result;  // Kết quả khám bệnh (có thể là văn bản hoặc file)
    private String filePath;  // Đường dẫn tới file kết quả khám bệnh (nếu có)
    private Date treatmentDate;  // Ngày điều trị

    // Getter và Setter
    public Long getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(Long treatmentId) {
        this.treatmentId = treatmentId;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatmentMethod() {
        return treatmentMethod;
    }

    public void setTreatmentMethod(String treatmentMethod) {
        this.treatmentMethod = treatmentMethod;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getTreatmentDate() {
        return treatmentDate;
    }

    public void setTreatmentDate(Date treatmentDate) {
        this.treatmentDate = treatmentDate;
    }
}
