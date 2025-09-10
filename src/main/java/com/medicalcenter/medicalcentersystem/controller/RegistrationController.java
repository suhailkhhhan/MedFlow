package com.medicalcenter.medicalcentersystem.controller;

import com.medicalcenter.medicalcentersystem.dao.PatientRepository;
import com.medicalcenter.medicalcentersystem.model.Patient;
import com.medicalcenter.medicalcentersystem.model.Role;
import com.medicalcenter.medicalcentersystem.model.User;
import com.medicalcenter.medicalcentersystem.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.Set;

@Controller
public class RegistrationController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @GetMapping("/register")
    public ModelAndView showRegistrationForm() {
        ModelAndView mav = new ModelAndView("patient-registration");
        mav.addObject("patient", new Patient());
        return mav;
    }

    @PostMapping("/register-patient")
    public String registerPatient(@ModelAttribute Patient patient, @RequestParam("username") String username, @RequestParam("password") String password) {
        // Create and set up the User object
        User user = new User();
        user.setUsername(username);

        // --- THIS IS THE FIX ---
        // Restore the line that encrypts the password
        user.setPassword(passwordEncoder.encode(password));
        
        user.setRoles(Set.of(Role.ROLE_PATIENT));
        
        patient.setUser(user);

        patientRepository.save(patient);
        try {
            String subject = "Welcome to the Medical Center!";
            String body = "Dear " + patient.getName() + ",\n\nYour account has been successfully created. Welcome to our medical center!";
            mailService.sendEmail(username, subject, body);
        } catch (Exception e) {
            System.err.println("Error sending registration email: " + e.getMessage());
        }
        
        // This should now redirect to your new custom login page
        return "redirect:/login?register_success";
    }
}