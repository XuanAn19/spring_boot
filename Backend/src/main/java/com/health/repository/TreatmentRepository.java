package com.health.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.health.entity.Treatment;
import java.util.List;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {

    // Lấy danh sách điều trị theo ID bệnh nhân
    List<Treatment> findByUserUserId(Long userId);

    // Lấy điều trị theo ID cuộc hẹn
    Treatment findByAppointmentAppointmentId(Long appointmentId);
}
