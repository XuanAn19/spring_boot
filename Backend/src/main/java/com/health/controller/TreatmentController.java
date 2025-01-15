package com.health.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.health.dto.TreatmentDto;
import com.health.entity.Appointment;
import com.health.entity.Treatment;
import com.health.entity.User;
import com.health.service.AppointmentService;
import com.health.service.TreatmentService;
import com.health.service.UserService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/treatment")
@CrossOrigin("*")
public class TreatmentController {

    @Autowired
    private TreatmentService treatmentService;
    @Autowired
    private UserService service;

    @Autowired
    private AppointmentService serviceApp;

    @GetMapping("/treatment/{userId}")
    public ResponseEntity<List<Treatment>> getTreatmentsByAppointment(@PathVariable Long userId) {
        List<Treatment> treatments = treatmentService.getTreatmentsByUserId(userId);
        return ResponseEntity.ok(treatments);
    }

    @GetMapping("/getUserIdByEmail")
    public ResponseEntity<?> getUserIdByEmail(@RequestParam String email) {
        try {
            Long userId = service.getUserIdByEmail(email);
            return ResponseEntity.ok(userId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    // API thêm điều trị
    @PostMapping("/add")
    public ResponseEntity<?> addTreatment(@RequestBody TreatmentDto treatmentDto) {
        try {
        	 // Ánh xạ từ TreatmentDto sang Treatment
            Treatment treatment = new Treatment();
            treatment.setDiagnosis(treatmentDto.getDiagnosis());
            treatment.setTreatmentMethod(treatmentDto.getTreatmentMethod());
            treatment.setNotes(treatmentDto.getNotes());
            treatment.setResult(treatmentDto.getResult());
            treatment.setFilePath(treatmentDto.getFilePath());
            treatment.setTreatmentDate(treatmentDto.getTreatmentDate());
            
            // Lấy User và Appointment từ DB dựa trên ID
            User user = service.findUserById(treatmentDto.getUserId());
            Appointment appointment = serviceApp.findAppointmentById(treatmentDto.getAppointmentId());
            
            treatment.setUser(user);
            treatment.setAppointment(appointment);
            
            Treatment newTreatment = treatmentService.addTreatment(treatment);
            System.out.println(newTreatment);
            return new ResponseEntity<>(newTreatment, HttpStatus.CREATED);
        } catch (Exception e) { 
            return new ResponseEntity<>("Failed to add treatment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // API lấy danh sách điều trị theo ID bệnh nhân
    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<?> getTreatmentsByUserId(@PathVariable Long userId) {
        try {
            List<Treatment> treatments = treatmentService.getTreatmentsByUserId(userId);
            if (treatments.isEmpty()) {
                return new ResponseEntity<>("No treatments found for this patient.", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(treatments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve treatments: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // API lấy thông tin điều trị theo ID cuộc hẹn
    @GetMapping("/getByAppointmentId/{appointmentId}")
    public ResponseEntity<?> getTreatmentByAppointmentId(@PathVariable Long appointmentId) {
        try {
            Treatment treatment = treatmentService.getTreatmentByAppointmentId(appointmentId);
            if (treatment != null) {
                return new ResponseEntity<>(treatment, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No treatment found for this appointment.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve treatment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // API cập nhật điều trị
    @PutMapping("/update/{treatmentId}")
    public ResponseEntity<?> updateTreatment(@PathVariable Long treatmentId, @RequestBody Treatment updatedTreatment) {
        try {
            Treatment updated = treatmentService.updateTreatment(treatmentId, updatedTreatment);
            if (updated != null) {
                return new ResponseEntity<>(updated, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Treatment not found.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update treatment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // API xóa điều trị
    @DeleteMapping("/delete/{treatmentId}")
    public ResponseEntity<?> deleteTreatment(@PathVariable Long treatmentId) {
        try {
            boolean deleted = treatmentService.deleteTreatment(treatmentId);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity<>("Treatment not found.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete treatment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
