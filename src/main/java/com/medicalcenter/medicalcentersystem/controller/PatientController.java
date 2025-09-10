package com.medicalcenter.medicalcentersystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.medicalcenter.medicalcentersystem.dao.PatientRepository;
import com.medicalcenter.medicalcentersystem.model.Patient;

import java.util.List;

@Controller
public class PatientController {

    @Autowired
    private PatientRepository patientRepo;

    @GetMapping("/patients-list")
    public ModelAndView showPatientsList() {
        ModelAndView mav = new ModelAndView("patients-list");
        List<Patient> patients = patientRepo.findAll();
        mav.addObject("patients", patients);
        return mav;
    }

    @GetMapping("/add-patient-form")
    public ModelAndView addPatientForm() {
        ModelAndView mav = new ModelAndView("add-patient-form");
        mav.addObject("patient", new Patient());
        return mav;
    }

    @PostMapping("/save-patient")
    public String savePatient(@ModelAttribute Patient patient) {
        patientRepo.save(patient);
        return "redirect:/patients-list";
    }


    @GetMapping("/edit-patient/{id}")
    public ModelAndView showEditPatientForm(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView("edit-patient-form");
        Patient patient = patientRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + id));
        mav.addObject("patient", patient);
        return mav;
    }

    @PostMapping("/update-patient/{id}")
    public String updatePatient(@PathVariable("id") int id, @ModelAttribute Patient patient) {
        patient.setId(id);
        patientRepo.save(patient);
        return "redirect:/patients-list";
    }

    @GetMapping("/delete-patient/{id}")
    public String deletePatient(@PathVariable("id") int id) {
        patientRepo.deleteById(id);
        return "redirect:/patients-list";
    }
}