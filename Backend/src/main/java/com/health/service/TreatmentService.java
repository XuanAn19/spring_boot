package com.health.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.health.entity.Treatment;
import com.health.repository.TreatmentRepository;
import java.util.List;
import java.util.Optional;

@Service
public class TreatmentService {

    @Autowired
    private TreatmentRepository treatmentRepository;

   

	/*
	 * public List<Treatment> getTreatmentsByAppointment(Long appointmentId) {
	 * return treatmentRepository.findByAppointmentId(appointmentId); }
	 */
    
    // Thêm điều trị mới
    public Treatment addTreatment(Treatment treatment) {
        return treatmentRepository.save(treatment);
    }

    // Lấy danh sách điều trị theo ID bệnh nhân
    public List<Treatment> getTreatmentsByUserId(Long userId) {
        return treatmentRepository.findByUserUserId(userId);
    }

    
    // Lấy điều trị theo ID cuộc hẹn
    public Treatment getTreatmentByAppointmentId(Long appointmentId) {
        return treatmentRepository.findByAppointmentAppointmentId(appointmentId);
    }

    // Cập nhật thông tin điều trị
    public Treatment updateTreatment(Long treatmentId, Treatment updatedTreatment) {
        Optional<Treatment> existingTreatment = treatmentRepository.findById(treatmentId);
        if (existingTreatment.isPresent()) {
            Treatment treatment = existingTreatment.get();
            treatment.setDiagnosis(updatedTreatment.getDiagnosis());
            treatment.setTreatmentMethod(updatedTreatment.getTreatmentMethod());
            treatment.setNotes(updatedTreatment.getNotes());
            treatment.setResult(updatedTreatment.getResult());
            treatment.setFilePath(updatedTreatment.getFilePath());
            treatment.setTreatmentDate(updatedTreatment.getTreatmentDate());
            return treatmentRepository.save(treatment);
        } else {
            return null;
        }
    }

    // Xóa điều trị
    public boolean deleteTreatment(Long treatmentId) {
        Optional<Treatment> treatment = treatmentRepository.findById(treatmentId);
        if (treatment.isPresent()) {
            treatmentRepository.delete(treatment.get());
            return true;
        } else {
            return false;
        }
    }
}
