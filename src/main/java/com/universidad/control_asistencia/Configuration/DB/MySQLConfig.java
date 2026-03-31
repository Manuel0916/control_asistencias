package com.universidad.control_asistencia.Configuration.DB;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.universidad.control_asistencia.MySQL.Repository",
        transactionManagerRef = "transactionManager"
)
@EntityScan(basePackages = "com.universidad.control_asistencia.MySQL.Model")
public class MySQLConfig {
}