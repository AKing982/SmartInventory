package org.example.smartinventory.repository;

import org.example.smartinventory.entities.DepartmentEntity;
import org.example.smartinventory.entities.EmployeeEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class DepartmentRepositoryTest {

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
    private DepartmentRepository departmentRepository;

    @Autowired
    private TestEntityManager entityManager;

    private DepartmentEntity testDepartment;


    @BeforeEach
    void setUp() {
        testDepartment = new DepartmentEntity();
        testDepartment.setDepartmentName("Test Department");
        testDepartment.setDepartmentDescription("Test Description");
        testDepartment.setManagerId(1);
        testDepartment.setActive(true);
        testDepartment.setAddress("Test Address");

        entityManager.persist(testDepartment);
        entityManager.flush();
    }

    @Test
    void testFindByDepartmentName() {
        Optional<DepartmentEntity> found = departmentRepository.findByDepartmentName("Test Department");
        assertThat(found).isPresent();
        assertThat(found.get().getDepartmentName()).isEqualTo("Test Department");
    }

    @Test
    void testFindAllActiveDepartments() {
        List<DepartmentEntity> activeDepartments = departmentRepository.findAllActiveDepartments();
        assertThat(activeDepartments).hasSize(1);
        assertThat(activeDepartments.get(0).isActive()).isTrue();
    }

    @Test
    void testFindByManagerId() {
        List<DepartmentEntity> departments = departmentRepository.findByManagerId(1);
        assertThat(departments).hasSize(1);
        assertThat(departments.get(0).getManagerId()).isEqualTo(1);
    }

    @Test
    void testFindDepartmentsCreatedAfter() {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        List<DepartmentEntity> departments = departmentRepository.findDepartmentsCreatedAfter(yesterday);
        assertThat(departments).hasSize(1);
        assertThat(departments.get(0).getCreatedAt()).isAfter(yesterday);
    }

    @Test
    void testFindDepartmentsUpdatedAfter() {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        testDepartment.setDepartmentName("Updated Department");
        entityManager.persist(testDepartment);
        entityManager.flush();

        List<DepartmentEntity> departments = departmentRepository.findDepartmentsUpdatedAfter(yesterday);
        assertThat(departments).hasSize(1);
        assertThat(departments.get(0).getUpdatedAt()).isAfter(yesterday);
    }

    @Test
    void testSearchDepartments() {
        List<DepartmentEntity> departments = departmentRepository.searchDepartments("Test");
        assertThat(departments).hasSize(1);
        assertThat(departments.get(0).getDepartmentName()).contains("Test");
    }

    @Test
    void testCountEmployeesInDepartment() {
        EmployeeEntity employee = new EmployeeEntity();
        employee.setDepartment(testDepartment);
        entityManager.persist(employee);
        entityManager.flush();

        long count = departmentRepository.countEmployeesInDepartment(testDepartment.getDepartmentId());
        assertThat(count).isEqualTo(1);
    }

    @Test
    void testFindDepartmentsWithMoreThanXEmployees() {
        EmployeeEntity employee = new EmployeeEntity();
        employee.setDepartment(testDepartment);
        entityManager.persist(employee);
        entityManager.flush();

        List<DepartmentEntity> departments = departmentRepository.findDepartmentsWithMoreThanXEmployees(0);
        assertThat(departments).hasSize(1);
    }

    @Test
    void testFindDepartmentsWithNoEmployees() {
        List<DepartmentEntity> departments = departmentRepository.findDepartmentsWithNoEmployees();
        assertThat(departments).hasSize(1);
    }

    @Test
    void testUpdateDepartmentStatus() {
        int updated = departmentRepository.updateDepartmentStatus(testDepartment.getDepartmentId(), false);
        assertThat(updated).isEqualTo(1);

        DepartmentEntity found = entityManager.find(DepartmentEntity.class, testDepartment.getDepartmentId());
        assertThat(found.isActive()).isFalse();
    }

    @Test
    void testUpdateDepartmentManager() {
        int updated = departmentRepository.updateDepartmentManager(testDepartment.getDepartmentId(), 2);
        assertThat(updated).isEqualTo(1);

        DepartmentEntity found = entityManager.find(DepartmentEntity.class, testDepartment.getDepartmentId());
        assertThat(found.getManagerId()).isEqualTo(2);
    }

    @Test
    void testDeleteInactiveDepartments() {
        testDepartment.setActive(false);
        entityManager.persist(testDepartment);
        entityManager.flush();

        int deleted = departmentRepository.deleteInactiveDepartments();
        assertThat(deleted).isEqualTo(1);

        List<DepartmentEntity> remaining = departmentRepository.findAll();
        assertThat(remaining).isEmpty();
    }

    @Test
    void testFindByAddress() {
        List<DepartmentEntity> departments = departmentRepository.findByAddress("Test");
        assertThat(departments).hasSize(1);
        assertThat(departments.get(0).getAddress()).contains("Test");
    }

    @Test
    void testCountActiveAndInactiveDepartments() {
        List<Object[]> counts = departmentRepository.countActiveAndInactiveDepartments();
        assertThat(counts).hasSize(1);
        assertThat(counts.get(0)[0]).isEqualTo(true);
        assertThat(counts.get(0)[1]).isEqualTo(1L);
    }

    @Test
    void testFindDepartmentsSortedByEmployeeCount() {
        List<Object[]> departments = departmentRepository.findDepartmentsSortedByEmployeeCount();
        assertThat(departments).hasSize(1);
        assertThat(departments.get(0)[0]).isEqualTo(testDepartment);
        assertThat(departments.get(0)[1]).isEqualTo(0L);
    }

    @Test
    void testFindDepartmentsCreatedBetween() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(1);

        List<DepartmentEntity> departments = departmentRepository.findDepartmentsCreatedBetween(start, end);
        assertThat(departments).hasSize(1);
        assertThat(departments.get(0).getCreatedAt()).isBetween(start, end);
    }

    @Test
    void testFindUpdatedDepartments() {
        testDepartment.setDepartmentName("Updated Department");
        entityManager.persist(testDepartment);
        entityManager.flush();

        List<DepartmentEntity> departments = departmentRepository.findUpdatedDepartments();
        assertThat(departments).hasSize(1);
        assertThat(departments.get(0).getUpdatedAt()).isNotNull();
    }

    @Test
    void testFindDepartmentsByCustomCriteria() {
        List<DepartmentEntity> departments = departmentRepository.findDepartmentsByCustomCriteria("Test", true, 1);
        assertThat(departments).hasSize(1);
        assertThat(departments.get(0).getDepartmentName()).contains("Test");
        assertThat(departments.get(0).isActive()).isTrue();
        assertThat(departments.get(0).getManagerId()).isEqualTo(1);
    }

    @Test
    void testBulkUpdateDepartmentAddresses() {
        int updated = departmentRepository.bulkUpdateDepartmentAddresses("Test Address", "New Address");
        assertThat(updated).isEqualTo(1);

        DepartmentEntity found = entityManager.find(DepartmentEntity.class, testDepartment.getDepartmentId());
        assertThat(found.getAddress()).isEqualTo("New Address");
    }


    @AfterEach
    void tearDown() {
        entityManager.clear();
    }


}