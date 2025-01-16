package com.health.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.health.dto.AdminDto;
import com.health.dto.DoctorDto;
import com.health.dto.NormalUserDto;
import com.health.dto.Role;
import com.health.entity.Admin;
import com.health.entity.Doctor;
import com.health.entity.NormalUser;
import com.health.entity.Specialization;
import com.health.entity.User;
import com.health.helper.UserNotFoundException;
import com.health.repository.AdminRepository;
import com.health.repository.DoctorRepository;
import com.health.repository.NormalUserRepository;
import com.health.repository.SpecializationRepository;
import com.health.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private UserService userService;

    @Autowired
    private SpecializationRepository specializationRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private NormalUserRepository normalUserRepository;

    @Autowired
    private UserRepository userRepository;
    
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

   
    public Admin saveAdmin(Admin admin) throws Exception {
    	
    	User exist = userService.findUserByEmail(admin.getUser().getEmail());
        if (exist != null) {
            throw new UserNotFoundException("User already present with email: " + admin.getUser().getEmail());
        }
    	
        admin.getUser().setRole(Role.ADMIN);
        
        admin.getUser().setPassword(encoder.encode(admin.getUser().getPassword()));

        return adminRepository.save(admin);
    }
    
    

    public Admin getAdmin(Long userId) {
        return adminRepository.findById(userId).orElse(null); // Trả về null nếu không tìm thấy
    }

    @Transactional
    public Admin updateAdmin(Long adminId, AdminDto dto) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with ID: " + adminId));
        User user = admin.getUser();

        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());

        admin.setGender(dto.getGender());
        admin.setPosition(dto.getPosition());

        userRepository.save(user);
        return adminRepository.save(admin);
    }
    
    public Specialization addSpecialization(Specialization spec) {
        return specializationRepository.save(spec);
    }

    public List<Specialization> getAllSpecializations() {
        return specializationRepository.findAll();
    }
    
    public Admin updateAdmin(Long adminId, Admin updatedAdmin) {
        Admin existingAdmin = adminRepository.findById(adminId)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found with ID: " + adminId));
        existingAdmin.setGender(updatedAdmin.getGender());
        existingAdmin.setPosition(updatedAdmin.getPosition());
        existingAdmin.getUser().setEmail(updatedAdmin.getUser().getEmail());
        existingAdmin.getUser().setPhoneNumber(updatedAdmin.getUser().getPhoneNumber());
        return adminRepository.save(existingAdmin);
    }

    public void deleteAdmin(Long adminId) {
        if (!adminRepository.existsById(adminId)) {
            throw new EntityNotFoundException("Admin not found with ID: " + adminId);
        }
        adminRepository.deleteById(adminId);
    }
    
    @Transactional(readOnly = true)
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    
    @Transactional
    public Doctor updateDoctor(Long doctorId, DoctorDto dto) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + doctorId));
        User user = doctor.getUser();

        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());

        doctor.setLicenseNumber(dto.getLicenseNumber());
        doctor.setClinicAddress(dto.getClinicAddress());
        doctor.setYearsOfExperience(dto.getYearsOfExperience());
        doctor.setBio(dto.getBio());

        userRepository.save(user);
        return doctorRepository.save(doctor);
    }

    @Transactional
    public void deleteDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + doctorId));
        doctorRepository.delete(doctor);
    }

    @Transactional
    public NormalUser createNormalUser(NormalUserDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());

        NormalUser normalUser = new NormalUser();
        normalUser.setAddress(dto.getAddress());
        normalUser.setDateOfBirth(dto.getDateOfBirth());
        normalUser.setGender(dto.getGender());
        normalUser.setUser(user);

        userRepository.save(user);
        return normalUserRepository.save(normalUser);
    }

    @Transactional
    public void deleteNormalUser(Long userId) {
        try {
            // Log before deletion
            System.out.println("Deleting NormalUser with ID: " + userId);
            
            Optional<NormalUser> normalUser = normalUserRepository.findById(userId);
            if (normalUser.isPresent()) {
                normalUserRepository.deleteById(userId);
                System.out.println("NormalUser deleted.");
            } else {
                throw new RuntimeException("NormalUser not found.");
            }

            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                userRepository.deleteById(userId);
                System.out.println("User deleted.");
            } else {
                throw new RuntimeException("User not found.");
            }

        } catch (Exception e) {
            System.err.println("Error deleting user: " + e.getMessage());
            throw e;
        }
    }


    @Transactional(readOnly = true)
    public List<NormalUser> getAllUsers() {
        return normalUserRepository.findAll();
    }

    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

}
