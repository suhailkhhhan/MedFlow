package com.medicalcenter.medicalcentersystem.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.medicalcenter.medicalcentersystem.dao.AppointmentRepository;
import com.medicalcenter.medicalcentersystem.dao.DoctorRepository;
import com.medicalcenter.medicalcentersystem.dao.PatientRepository;

@Controller
public class DashboardController {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping("/dashboard")
    public ModelAndView showDashboard() {
        ModelAndView mav = new ModelAndView("dashboard");

        // Fetch counts from repositories
        long doctorCount = doctorRepository.count();
        long patientCount = patientRepository.count();
        long appointmentCount = appointmentRepository.count();

        // Add counts to the model
        mav.addObject("doctorCount", doctorCount);
        mav.addObject("patientCount", patientCount);
        mav.addObject("appointmentCount", appointmentCount);

        return mav;
    }
}