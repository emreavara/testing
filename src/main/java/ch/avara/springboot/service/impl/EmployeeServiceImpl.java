package ch.avara.springboot.service.impl;

import ch.avara.springboot.exception.ResouceNotFoundException;
import ch.avara.springboot.model.Employee;
import ch.avara.springboot.repository.EmployeeRepository;
import ch.avara.springboot.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Employee save(Employee employee) {
        var existingEmployeeOptional = employeeRepository.findByEmail(employee.getEmail());
        if (existingEmployeeOptional.isPresent()) {
            throw new ResouceNotFoundException("Employee already exists with given email: " + employee.getEmail());
        }
        return employeeRepository.save(employee);
    }

}
