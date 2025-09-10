package com.medicalcenter.medicalcentersystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medicalcenter.medicalcentersystem.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
}
