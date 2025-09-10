package com.medicalcenter.medicalcentersystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.medicalcenter.medicalcentersystem.dao.AppointmentRepository;
import com.medicalcenter.medicalcentersystem.dao.DoctorRepository;
import com.medicalcenter.medicalcentersystem.dao.PatientRepository;
import com.medicalcenter.medicalcentersystem.model.Appointment;
import com.medicalcenter.medicalcentersystem.model.Doctor;
import com.medicalcenter.medicalcentersystem.model.Patient;

import com.medicalcenter.medicalcentersystem.dao.BillRepository;
import com.medicalcenter.medicalcentersystem.model.Bill;
import java.util.Optional;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepo;
    @Autowired
    private DoctorRepository doctorRepo;
    @Autowired
    private 
    PatientRepository patientRepo;
    @Autowired
    private BillRepository billRepo;


    @GetMapping({"/", "/appointments-list"})
    public ModelAndView showAppointments() {
        ModelAndView mav = new ModelAndView("appointments-list");
        List<Appointment> appointments = appointmentRepo.findAll();
        mav.addObject("appointments", appointments);
        return mav;
    }

    @GetMapping("/add-appointment-form")
    public ModelAndView addAppointmentForm() {
        ModelAndView mav = new ModelAndView("add-appointment-form");
        List<Patient> patients = patientRepo.findAll();
        List<Doctor> doctors = doctorRepo.findAll();
        mav.addObject("appointment", new Appointment());
        mav.addObject("patients", patients);
        mav.addObject("doctors", doctors);
        return mav;
    }

    // --- THIS IS THE CORRECTED METHOD ---
    @PostMapping("/save-appointment")
    public String saveAppointment(@RequestParam("patientId") int patientId,
                                  @RequestParam("doctorId") int doctorId,
                                  @RequestParam("appointmentDate") String appointmentDate,
                                  @RequestParam("appointmentTime") String appointmentTime) {

        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Patient Id:" + patientId));
        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Doctor Id:" + doctorId));

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(LocalDate.parse(appointmentDate));
        appointment.setAppointmentTime(LocalTime.parse(appointmentTime));

        appointmentRepo.save(appointment);

        return "redirect:/appointments-list";
    }
    // --- END OF CORRECTION ---

@GetMapping("/delete-appointment/{id}")
    public String deleteAppointment(@PathVariable("id") int id) {
        // Step 1: Check for and delete the associated bill first
        Optional<Bill> associatedBill = billRepo.findByAppointmentId(id);
        if (associatedBill.isPresent()) {
            billRepo.delete(associatedBill.get());
        }

        // Step 2: Now it's safe to delete the appointment
        appointmentRepo.deleteById(id);
        
        return "redirect:/appointments-list";
    }
}