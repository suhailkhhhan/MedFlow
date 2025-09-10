package com.medicalcenter.medicalcentersystem.dao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medicalcenter.medicalcentersystem.model.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    
    List<Appointment> findByPatientUserId(Long userId);
}