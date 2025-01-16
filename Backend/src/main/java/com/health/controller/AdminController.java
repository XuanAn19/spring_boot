package com.health.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.health.dto.AdminDto;
import com.health.dto.DoctorDto;
import com.health.dto.NormalUserDto;
import com.health.entity.Admin;
import com.health.entity.Doctor;
import com.health.entity.NormalUser;
import com.health.entity.Specialization;
import com.health.service.AdminService;
import com.health.service.DoctorService;
import com.health.service.NormalUserService;


@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {
    
	
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private NormalUserService normalUserService;
    
    //to create admin
    @PostMapping("/")
    public ResponseEntity<?> saveAdmin(@RequestBody AdminDto dto) {
        try {
            Admin admin = adminService.saveAdmin(dto.toEntity());
            return new ResponseEntity<>(admin, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to save admin: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getAdmin/{userId}")
    public ResponseEntity<?> getAdmin(@PathVariable Long userId) {
        try {
            Admin admin = adminService.getAdmin(userId);
            if (admin != null) {
                return new ResponseEntity<>(admin, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Admin not found for id: " + userId, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve admin: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
 // Update Admin
    @PutMapping("/update/{adminId}")
    public ResponseEntity<?> updateAdmin(@PathVariable Long adminId, @RequestBody AdminDto adminDto) {
        try {
            Admin updatedAdmin = adminService.updateAdmin(adminId, adminDto);
            return ResponseEntity.ok(updatedAdmin);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update admin: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
 // Delete Admin
    @DeleteMapping("/delete/{adminId}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Long adminId) {
        try {
            adminService.deleteAdmin(adminId);
            return ResponseEntity.ok("Admin deleted successfully.");
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete admin: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/saveDoctor")
    public ResponseEntity<?> saveDoctor(@RequestBody DoctorDto dto) {
        try {
        	System.out.println(dto.toString());
            Doctor doctor = doctorService.saveDoctor(dto);
            return new ResponseEntity<>(doctor, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to save doctor: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
 // Update Doctor
    @PutMapping("/doctor/update/{doctorId}")
    public ResponseEntity<?> updateDoctor(@PathVariable Long doctorId, @RequestBody DoctorDto doctorDto) {
        try {
            Doctor updatedDoctor = adminService.updateDoctor(doctorId, doctorDto);
            return ResponseEntity.ok(updatedDoctor);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update doctor: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getAllDoctors")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = adminService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }
    
 // Delete Doctor
    @DeleteMapping("/doctor/delete/{doctorId}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long doctorId) {
        try {
            adminService.deleteDoctor(doctorId);
            return ResponseEntity.ok("Doctor deleted successfully.");
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete doctor: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
 // Create Normal User
    @PostMapping("/user/create")
    public ResponseEntity<?> createNormalUser(@RequestBody NormalUserDto normalUserDto) {
        try {
            NormalUser user = adminService.createNormalUser(normalUserDto);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Update Normal User
	
	  @PutMapping("/user/update/{email}") public ResponseEntity<?>
	  updateNormalUser(@PathVariable String email, @RequestBody NormalUserDto
	  normalUserDto) { try { NormalUser updatedUser =
	  normalUserService.updateUser(email, normalUserDto); return
	  ResponseEntity.ok(updatedUser); } catch (Exception e) { return new
	  ResponseEntity<>("Failed to update user: " + e.getMessage(),
	 HttpStatus.INTERNAL_SERVER_ERROR); } }
	 
	  @GetMapping("/users")
	    public ResponseEntity<?> getAllUsers() {
	        try {
	            return ResponseEntity.ok(adminService.getAllUsers());
	        } catch (Exception e) {
	            return new ResponseEntity<>("Failed to retrieve users: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
    // Delete Normal User
    @DeleteMapping("/user/delete/{userId}")
    public ResponseEntity<?> deleteNormalUser(@PathVariable Long userId) {
        try {
        	adminService.deleteNormalUser(userId);
        	
            return ResponseEntity.ok("User deleted successfully.");
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/addSpec")
    public ResponseEntity<?> addSpecialization(@RequestBody Specialization spec) {
        try {
            Specialization newSpec = adminService.addSpecialization(spec);
            return new ResponseEntity<>(newSpec, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add specialization: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getAllSpec")
    public ResponseEntity<?> getAllSpec() {
        try {
            List<Specialization> specs = adminService.getAllSpecializations();
            return new ResponseEntity<>(specs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve specializations: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    
    
    
   
}
