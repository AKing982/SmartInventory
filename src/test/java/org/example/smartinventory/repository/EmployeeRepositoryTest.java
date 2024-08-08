package org.example.smartinventory.repository;

import org.example.smartinventory.entities.DepartmentEntity;
import org.example.smartinventory.entities.EmployeeEntity;
import org.example.smartinventory.entities.ManagerEntity;
import org.example.smartinventory.entities.WarehouseEntity;
import org.example.smartinventory.model.EmployeeRole;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryTest {

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
    private EmployeeRepository employeeRepository;

    @Autowired
    private TestEntityManager entityManager;

    private EmployeeEntity testEmployee;

    @BeforeEach
    void setUp() {
        testEmployee = EmployeeEntity.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .jobTitle("Software Engineer")
                .salary("75000")
                .username("johndoe")
                .passwordHash("hashedpassword")
                .role(EmployeeRole.IT_ADMIN)
                .hireDate(LocalDate.now().minusYears(1))
                .isActive(true)
                .build();

        entityManager.persist(testEmployee);
        entityManager.flush();
    }

    @Test
    void testFindByFullName() {
        List<EmployeeEntity> found = employeeRepository.findByFullName("John", "Doe");
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void testFindByEmail() {
        Optional<EmployeeEntity> found = employeeRepository.findByEmail("john.doe@example.com");
        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo("John");
    }

    @Test
    void testFindByUsername() {
        Optional<EmployeeEntity> found = employeeRepository.findByUsername("johndoe");
        assertThat(found).isPresent();
        assertThat(found.get().getLastName()).isEqualTo("Doe");
    }

    @Test
    void testFindByDepartmentId() {
        DepartmentEntity department = new DepartmentEntity();
        department.setDepartmentName("IT");
        entityManager.persist(department);
        testEmployee.setDepartment(department);
        entityManager.persist(testEmployee);
        entityManager.flush();

        List<EmployeeEntity> found = employeeRepository.findByDepartmentId(department.getDepartmentId());
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void testFindByWarehouseId() {
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setWarehouseName("Main Warehouse");
        entityManager.persist(warehouse);
        testEmployee.setWarehouse(warehouse);
        entityManager.persist(testEmployee);
        entityManager.flush();

        List<EmployeeEntity> found = employeeRepository.findByWarehouseId(warehouse.getWarehouseId());
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void testFindByJobTitle() {
        List<EmployeeEntity> found = employeeRepository.findByJobTitle("Software Engineer");
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void testFindByRole() {
        List<EmployeeEntity> found = employeeRepository.findByRole(EmployeeRole.IT_ADMIN);
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void testFindByActiveStatus() {
        List<EmployeeEntity> found = employeeRepository.findByActiveStatus(true);
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void testFindByHireDateBetween() {
        List<EmployeeEntity> found = employeeRepository.findByHireDateBetween(
                LocalDate.now().minusYears(2), LocalDate.now());
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void testFindByManagerId() {
        ManagerEntity manager = new ManagerEntity();
        entityManager.persist(manager);
        testEmployee.setManagerEntity(manager);
        entityManager.persist(testEmployee);
        entityManager.flush();

        List<EmployeeEntity> found = employeeRepository.findByManagerId(manager.getEmployee().getEmployeeId());
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void testFindBySalaryGreaterThan() {
        List<EmployeeEntity> found = employeeRepository.findBySalaryGreaterThan("70000");
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void testFindAllOrderByHireDateDesc() {
        EmployeeEntity newEmployee = EmployeeEntity.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .hireDate(LocalDate.now())
                .build();
        entityManager.persist(newEmployee);
        entityManager.flush();

        List<EmployeeEntity> found = employeeRepository.findAllOrderByHireDateDesc();
        assertThat(found).hasSize(2);
        assertThat(found.get(0).getEmail()).isEqualTo("jane.doe@example.com");
    }

    @Test
    void testSearchEmployees() {
        List<EmployeeEntity> found = employeeRepository.searchEmployees("John");
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void testUpdateEmployeeActiveStatus() {
        int updated = employeeRepository.updateEmployeeActiveStatus(testEmployee.getEmployeeId(), false);
        assertThat(updated).isEqualTo(1);

        EmployeeEntity found = entityManager.find(EmployeeEntity.class, testEmployee.getEmployeeId());
        assertThat(found.isActive()).isFalse();
    }

    @Test
    void testUpdateEmployeeSalary() {
        int updated = employeeRepository.updateEmployeeSalary(testEmployee.getEmployeeId(), "80000");
        assertThat(updated).isEqualTo(1);

        EmployeeEntity found = entityManager.find(EmployeeEntity.class, testEmployee.getEmployeeId());
        assertThat(found.getSalary()).isEqualTo("80000");
    }

    @Test
    void testFindAllUniqueJobTitles() {
        EmployeeEntity newEmployee = EmployeeEntity.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .jobTitle("Project Manager")
                .build();
        entityManager.persist(newEmployee);
        entityManager.flush();

        List<String> jobTitles = employeeRepository.findAllUniqueJobTitles();
        assertThat(jobTitles).hasSize(2);
        assertThat(jobTitles).contains("Software Engineer", "Project Manager");
    }

    @Test
    void testCountEmployeesHiredInYear() {
        long count = employeeRepository.countEmployeesHiredInYear(LocalDate.now().getYear() - 1);
        assertThat(count).isEqualTo(1);
    }

    @Test
    void testDeleteInactiveEmployeesBeforeDate() {
        testEmployee.setActive(false);
        testEmployee.setUpdatedAt(LocalDateTime.now().minusDays(30));
        entityManager.persist(testEmployee);
        entityManager.flush();

        int deleted = employeeRepository.deleteInactiveEmployeesBeforeDate(LocalDateTime.now().minusDays(7));
        assertThat(deleted).isEqualTo(1);

        Optional<EmployeeEntity> found = employeeRepository.findById(testEmployee.getEmployeeId());
        assertThat(found).isEmpty();
    }


    @AfterEach
    void tearDown() {
        entityManager.flush();
    }
}