package com.medicalcenter.medicalcentersystem.controller;

import com.medicalcenter.medicalcentersystem.dao.AppointmentRepository;
import com.medicalcenter.medicalcentersystem.dao.BillRepository;
import com.medicalcenter.medicalcentersystem.dao.UserRepository;
import com.medicalcenter.medicalcentersystem.model.Appointment;
import com.medicalcenter.medicalcentersystem.model.Bill;
import com.medicalcenter.medicalcentersystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
public class PatientDashboardController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private BillRepository billRepository;

    @GetMapping("/patient/dashboard")
    public ModelAndView showPatientDashboard() {
        ModelAndView mav = new ModelAndView("patient-dashboard");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User user = userRepository.findByUsername(currentUserName).orElseThrow(() -> new IllegalStateException("User not found"));

        List<Appointment> appointments = appointmentRepository.findByPatientUserId(user.getId());
        List<Bill> bills = billRepository.findByPatientUserId(user.getId());

        mav.addObject("patientName", user.getPatient().getName());
        mav.addObject("appointments", appointments);
        mav.addObject("bills", bills);
        return mav;
    }
}