package org.example.smartinventory.repository;

import org.example.smartinventory.entities.WarehouseEntity;
import org.example.smartinventory.model.WarehouseType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class WarehouseRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:11-alpine")
            .withDatabaseName("test")
            .withUsername("alpine")
            .withPassword("alpine");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private TestEntityManager entityManager;

    private WarehouseEntity testWarehouse;

    @BeforeEach
    void setUp() {
        testWarehouse = new WarehouseEntity();
        testWarehouse.setWarehouseName("Test Warehouse");
        testWarehouse.setWarehouseAddress("123 Test St");
        testWarehouse.setEmailAddress("test@warehouse.com");
        testWarehouse.setTotalCapacity(1000.0);
        testWarehouse.setUsedCapacity(500.0);
        testWarehouse.setWarehouseType(WarehouseType.STORAGE);
        testWarehouse.setManagerName("John Doe");
        testWarehouse.setOpeningTime(LocalTime.of(9, 0));
        testWarehouse.setClosingTime(LocalTime.of(17, 0));
        testWarehouse.setEstablishmentDate(LocalDate.now().minusYears(1));

        entityManager.persist(testWarehouse);
        entityManager.flush();
    }

    @Test
    void testFindAllById() {
        WarehouseEntity warehouse2 = new WarehouseEntity();
        warehouse2.setWarehouseName("Test Warehouse 2");
        entityManager.persist(warehouse2);
        entityManager.flush();

        Long warehouseId = (long) testWarehouse.getWarehouseId();
        Long warehouseId2 = (long) testWarehouse.getWarehouseId();
        List<WarehouseEntity> found = warehouseRepository.findAllById(Arrays.asList(warehouseId,warehouseId2));
        assertThat(found).hasSize(2);
    }

    @Test
    void testFindByWarehouseName() {
        Optional<WarehouseEntity> found = warehouseRepository.findByWarehouseName("Test Warehouse");
        assertThat(found).isPresent();
        assertThat(found.get().getWarehouseName()).isEqualTo("Test Warehouse");
    }

    @Test
    void testFindByWarehouseAddress() {
        List<WarehouseEntity> found = warehouseRepository.findByWarehouseAddress("123 Test St");
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getWarehouseAddress()).isEqualTo("123 Test St");
    }

    @Test
    void testSearchWarehouses() {
        List<WarehouseEntity> found = warehouseRepository.searchWarehouses("Test");
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getWarehouseName()).contains("Test");
    }

    @Test
    void testGetWarehousesByType() {
        List<WarehouseEntity> found = warehouseRepository.getWarehousesByType(WarehouseType.STORAGE);
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getWarehouseType()).isEqualTo(WarehouseType.STORAGE);
    }

    @Test
    void testUpdateWarehouse() {
        testWarehouse.setWarehouseName("Updated Warehouse");
        int updated = warehouseRepository.updateWarehouse(testWarehouse);
        assertThat(updated).isEqualTo(1);

        WarehouseEntity found = entityManager.find(WarehouseEntity.class, testWarehouse.getWarehouseId());
        assertThat(found.getWarehouseName()).isEqualTo("Updated Warehouse");
    }

    @Test
    void testDeleteById() {
        warehouseRepository.deleteById(testWarehouse.getWarehouseId());
        WarehouseEntity found = entityManager.find(WarehouseEntity.class, testWarehouse.getWarehouseId());
        assertThat(found).isNull();
    }

    @Test
    void testGetWarehousesByCapacityRange() {
        List<WarehouseEntity> found = warehouseRepository.getWarehousesByCapacityRange(800, 1200);
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getTotalCapacity()).isBetween(800.0, 1200.0);
    }

    @Test
    void testGetWarehousesByUtilizationRate() {
        List<WarehouseEntity> found = warehouseRepository.getWarehousesByUtilizationRate(0.4, 0.6);
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getUsedCapacity() / found.get(0).getTotalCapacity()).isBetween(0.4, 0.6);
    }

    @Test
    void testGetTotalCapacity() {
        double capacity = warehouseRepository.getTotalCapacity(testWarehouse.getWarehouseId());
        assertThat(capacity).isEqualTo(1000.0);
    }

    @Test
    void testGetUsedCapacity() {
        double capacity = warehouseRepository.getUsedCapacity(testWarehouse.getWarehouseId());
        assertThat(capacity).isEqualTo(500.0);
    }

    @Test
    void testGetAvailableCapacity() {
        double capacity = warehouseRepository.getAvailableCapacity(testWarehouse.getWarehouseId());
        assertThat(capacity).isEqualTo(500.0);
    }

    @Test
    void testUpdateCapacity() {
        warehouseRepository.updateCapacity(testWarehouse.getWarehouseId(), 1500.0);
        WarehouseEntity found = entityManager.find(WarehouseEntity.class, testWarehouse.getWarehouseId());
        assertThat(found.getTotalCapacity()).isEqualTo(1500.0);
    }

    @Test
    void testUpdateUsedCapacity() {
        warehouseRepository.updateUsedCapacity(testWarehouse.getWarehouseId(), 750.0);
        WarehouseEntity found = entityManager.find(WarehouseEntity.class, testWarehouse.getWarehouseId());
        assertThat(found.getUsedCapacity()).isEqualTo(750.0);
    }

    @Test
    void testGetWarehouseManager() {
        String manager = warehouseRepository.getWarehouseManager(testWarehouse.getWarehouseId());
        assertThat(manager).isEqualTo("John Doe");
    }

    @Test
    void testGetMostUtilizedWarehouses() {
        List<WarehouseEntity> found = warehouseRepository.getMostUtilizedWarehouses(PageRequest.of(0, 10));
        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getUsedCapacity() / found.get(0).getTotalCapacity()).isEqualTo(0.5);
    }

    @Test
    void testGetLeastUtilizedWarehouses() {
        List<WarehouseEntity> found = warehouseRepository.getLeastUtilizedWarehouses(PageRequest.of(0, 10));
        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getUsedCapacity() / found.get(0).getTotalCapacity()).isEqualTo(0.5);
    }

    @Test
    void testGetWarehousesEstablishedBefore() {
        List<WarehouseEntity> found = warehouseRepository.getWarehousesEstablishedBefore(LocalDate.now());
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getEstablishmentDate()).isBefore(LocalDate.now());
    }

    @Test
    void testGetWarehousesEstablishedAfter() {
        List<WarehouseEntity> found = warehouseRepository.getWarehousesEstablishedAfter(LocalDate.now().minusYears(2));
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getEstablishmentDate()).isAfter(LocalDate.now().minusYears(2));
    }

    @Test
    void testGetWarehouseAge() {
        int age = warehouseRepository.getWarehouseAge(testWarehouse.getWarehouseId());
        assertThat(age).isEqualTo(365); // Approximately 1 year
    }

    @Test
    void testGetWarehouseCount() {
        long count = warehouseRepository.getWarehouseCount();
        assertThat(count).isEqualTo(1);
    }

    @Test
    void testGetWarehouseCountByType() {
        long count = warehouseRepository.getWarehouseCountByType(WarehouseType.STORAGE);
        assertThat(count).isEqualTo(1);
    }

    @Test
    void testGetAverageWarehouseCapacity() {
        double avgCapacity = warehouseRepository.getAverageWarehouseCapacity();
        assertThat(avgCapacity).isEqualTo(1000.0);
    }

    @Test
    void testFindWarehousesWithAvailableCapacity() {
        List<WarehouseEntity> found = warehouseRepository.findWarehousesWithAvailableCapacity(400);
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getTotalCapacity() - found.get(0).getUsedCapacity()).isGreaterThanOrEqualTo(400);
    }

    @Test
    void testIsUniqueWarehouseName() {
        boolean isUnique = warehouseRepository.isUniqueWarehouseName("New Warehouse");
        assertThat(isUnique).isTrue();

        isUnique = warehouseRepository.isUniqueWarehouseName("Test Warehouse");
        assertThat(isUnique).isFalse();
    }

    @Test
    void testUpdateWarehouseManager() {
        int updated = warehouseRepository.updateWarehouseManager(testWarehouse.getWarehouseId(), "Jane Doe");
        assertThat(updated).isEqualTo(1);

        WarehouseEntity found = entityManager.find(WarehouseEntity.class, testWarehouse.getWarehouseId());
        assertThat(found.getManagerName()).isEqualTo("Jane Doe");
    }

    @Test
    void testFindWarehousesByCustomCriteria() {
        Page<WarehouseEntity> found = warehouseRepository.findWarehousesByCustomCriteria(
                "Test", WarehouseType.STORAGE, 800.0, 1200.0, LocalDate.now().minusYears(2), PageRequest.of(0, 10));
        assertThat(found.getContent()).hasSize(1);
        assertThat(found.getContent().get(0).getWarehouseName()).contains("Test");
        assertThat(found.getContent().get(0).getWarehouseType()).isEqualTo(WarehouseType.STORAGE);
        assertThat(found.getContent().get(0).getTotalCapacity()).isBetween(800.0, 1200.0);
        assertThat(found.getContent().get(0).getEstablishmentDate()).isAfter(LocalDate.now().minusYears(2));
    }

    @Test
    void testBulkUpdateWarehouseType() {
        warehouseRepository.bulkUpdateWarehouseType(List.of(testWarehouse.getWarehouseId()), WarehouseType.COLD_STORAGE);
        WarehouseEntity found = entityManager.find(WarehouseEntity.class, testWarehouse.getWarehouseId());
        assertThat(found.getWarehouseType()).isEqualTo(WarehouseType.COLD_STORAGE);
    }


    @AfterEach
    void tearDown() {
        entityManager.flush();
    }
}