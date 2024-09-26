package com.vijay.tasker.repository;

import com.vijay.tasker.model.AuditLog;
import com.vijay.tasker.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository <AuditLog, Long> {
}
