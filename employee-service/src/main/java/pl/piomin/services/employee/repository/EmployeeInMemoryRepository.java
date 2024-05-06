package pl.piomin.services.employee.repository;

import io.micronaut.context.annotation.Requires;
import io.micronaut.runtime.context.scope.Refreshable;
import jakarta.inject.Inject;
import pl.piomin.services.employee.model.Employee;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Refreshable
@Requires(property = "in-memory-store.enabled", value = "true", defaultValue = "false")
public class EmployeeInMemoryRepository implements EmployeeRepository {

    @Inject
    private EmployeesInitialList employeesInitialList;

    private final List<Employee> employees = new ArrayList<>();

    @Override
    public Employee add(Employee employee) {
        employee.setId((long) (employees.size() + 1));
        employees.add(employee);
        return employee;
    }

    @Override
    public Employee findById(Long id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    @Override
    public List<Employee> findAll() {
        return employees;
    }

    @Override
    public List<Employee> findByDepartment(Long departmentId) {
        return employees.stream()
                .filter(employee -> employee.getDepartmentId().equals(departmentId))
                .toList();
    }

    @Override
    public List<Employee> findByOrganization(Long organizationId) {
        return employees.stream()
                .filter(employee -> employee.getOrganizationId().equals(organizationId))
                .toList();
    }

    @PostConstruct
    public void init() {
        employees.addAll(employeesInitialList.getEmployees());
    }

}
