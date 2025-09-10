package com.medicalcenter.medicalcentersystem.dao;

import com.medicalcenter.medicalcentersystem.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional; 

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
    
    Optional<Bill> findByAppointmentId(int appointmentId);

     List<Bill> findByPatientUserId(Long userId);
}