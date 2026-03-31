package com.universidad.control_asistencia.Configuration.DB;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = "com.universidad.control_asistencia.MongoDB.Repository"
)
public class MongoDBConfig {
}