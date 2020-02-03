package pl.piomin.services.employee.repository;

import io.micronaut.context.annotation.ConfigurationProperties;
import pl.piomin.services.employee.model.Employee;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties("test")
public class EmployeesInitialList {

    private List<Employee> employees = new ArrayList<>();

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

}
