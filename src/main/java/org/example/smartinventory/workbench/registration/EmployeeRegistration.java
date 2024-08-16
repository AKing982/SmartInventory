package org.example.smartinventory.workbench.registration;

import org.example.smartinventory.entities.EmployeeEntity;
import org.example.smartinventory.entities.RoleEntity;
import org.example.smartinventory.entities.UserEntity;
import org.example.smartinventory.model.Employee;
import org.example.smartinventory.model.Permission;
import org.example.smartinventory.model.Registration;
import org.example.smartinventory.repository.RoleRepository;
import org.example.smartinventory.repository.UserRepository;
import org.example.smartinventory.service.EmployeeService;
import org.example.smartinventory.service.PermissionsService;
import org.example.smartinventory.service.RoleService;
import org.example.smartinventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.example.smartinventory.workbench.registration.utilities.EmployeeRegistrationUtil.createEmployee;
import static org.example.smartinventory.workbench.registration.utilities.EmployeeRegistrationUtil.createEmployeeEntity;

@Component
public class EmployeeRegistration extends AbstractRegistrationBase<EmployeeEntity> implements RegistrationStrategy<EmployeeEntity>
{
    private EmployeeService employeeService;

    @Autowired
    public EmployeeRegistration(EmployeeService employeeService,
                                UserService userService,
                                RoleService roleService){
        super(roleService, userService);
        this.employeeService = employeeService;
    }

    @Override
    public String getRole() {
        return "ROLE_EMPLOYEE";
    }

    @Override
    public Optional<EmployeeEntity> register(Registration registration, PermissionsService permissionsService) {
        validateRegistrationAndPermissionsService(registration, permissionsService);
        validateRegistrationParams(registration);
        
        UserEntity user = createDefaultUser(registration);
        Employee employee = createEmployee(registration);
        EmployeeEntity employeeEntity = createEmployeeEntity(employee, user);
        Set<Permission> permissions = getPermissions(employeeEntity, permissionsService);
        addRoleToPermissions(permissions, getRole(), permissionsService);
        setRoleConfigurationForEntity(permissions, employeeEntity);
        saveEmployee(employeeEntity);
        return Optional.of(employeeEntity);
    }

    private void saveEmployee(EmployeeEntity employeeEntity) {
        employeeService.save(employeeEntity);
    }

    public void validateRegistrationAndPermissionsService(Registration registration, PermissionsService permissionsService) {
        if(registration == null || permissionsService == null){
            throw new IllegalArgumentException("Registration is null");
        }
    }

    public void validateRegistrationParams(Registration registration) {
        if(registration == null){
            throw new IllegalArgumentException("Registration is null");
        }
        validateField(registration.getEmail());
        validateField(registration.getPassword());
        validateField(registration.getFirstName());
        validateField(registration.getLastName());
        validateField(registration.getRole());
        validateField(registration.getJobTitle());
        validateField(registration.getUsername());
        validateField(registration.getCompany());
    }

    private void validateField(String value){
        if(value.trim().isEmpty()){
            throw new IllegalArgumentException("Value is null or empty");
        }
    }

    @Override
    protected Set<Permission> getPermissions(EmployeeEntity employeeEntity, PermissionsService permissionsService) {
        if(employeeEntity == null || permissionsService == null){
            throw new IllegalArgumentException("Unable to retrieve permissions for null employee");
        }

        UserEntity userEntity = employeeEntity.getUser();
        if(userEntity == null){
            throw new IllegalArgumentException("Unable to retrieve user for null employee");
        }
        return permissionsService.getPermissionsForUser(userEntity);
    }

    @Override
    protected void setRoleConfigurationForEntity(Set<Permission> permissions, EmployeeEntity entity) {
        RoleEntity employeeRole = roleService.findByName(getRole())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        UserEntity user = entity.getUser();
        roleService.addRoleToUser(user.getId(), employeeRole.getId());

        userService.save(user);
        entity.setUser(user);
    }
}
