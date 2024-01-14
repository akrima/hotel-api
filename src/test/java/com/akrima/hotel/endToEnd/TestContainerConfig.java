package com.akrima.hotel.endToEnd;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

/**
 * Here where i can add more containers if i need
 * @Author: Abderrahim KRIMA
 */
public class TestContainerConfig {

    public static final PostgreSQLContainer<?> postgresContainer;

    static {
        postgresContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:13.3"))
                .withDatabaseName("testdb")
                .withUsername("testuser")
                .withPassword("testpass")
                .withInitScript("db/migration/schema.sql")
                .withCommand("postgres", "-c", "listen_addresses=*", "-c", "max_connections=300")
                .waitingFor(Wait.forLogMessage(".*server started.*", 1))
                .withStartupTimeout(Duration.ofSeconds(60));
    }
}
