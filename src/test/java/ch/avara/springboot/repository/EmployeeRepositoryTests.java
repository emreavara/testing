package ch.avara.springboot.repository;

import ch.avara.springboot.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository testee;

    @Test
    void shouldSaveEmployee() {
        // Arrange
        var employee = Employee.builder()
                .firstName("TestFirstName")
                .lastName("TestLastName")
                .email("test@email.com")
                .build();

        // Act
        var result = testee.save(employee);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result)
                .returns("TestFirstName", Employee::getFirstName)
                .returns("TestLastName", Employee::getLastName)
                .returns("test@email.com", Employee::getEmail);
    }

    @Test
    void shouldSaveAllEmployees() {
        // Arrange
        var employee1 = Employee.builder()
                .firstName("TestFirstName1")
                .lastName("TestLastName1")
                .email("test@email.com1")
                .build();
        var employee2 = Employee.builder()
                .firstName("TestFirstName2")
                .lastName("TestLastName2")
                .email("test@email.com2")
                .build();
        testee.save(employee1);
        testee.save(employee2);

        // Act
        var result = testee.findAll();

        // Assert
        assertThat(result).hasSize(2);
    }

    @Test
    void shouldUpdateEmployee() {
        // Arrange
        var employee = Employee.builder()
                .firstName("TestFirstName")
                .lastName("TestLastName")
                .email("test@email.com")
                .build();
        testee.save(employee);

        var updatedEmployee = employee.toBuilder().email("new@email.com").build();
        testee.save(updatedEmployee);

        // Act
        var result = testee.findById(employee.getId()).get();

        // Assert
        assertThat(result).returns("new@email.com", Employee::getEmail);
    }

    @Test
    void shouldDeleteEmployee() {
        // Arrange
        var employee1 = Employee.builder()
                .firstName("TestFirstName1")
                .lastName("TestLastName1")
                .email("test@email.com1")
                .build();
        var employee2 = Employee.builder()
                .firstName("TestFirstName2")
                .lastName("TestLastName2")
                .email("test@email.com2")
                .build();
        testee.save(employee1);
        testee.save(employee2);

        // Act
        testee.delete(employee2);

        // Assert
        var result = testee.findAll();
        assertThat(result).hasSize(1);
    }

    @Test
    void shouldFindByJpqlEmployee() {
        // Arrange
        var firstName = "TestFirstName";
        var lastName = "TestLastName";
        var employee = Employee.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email("test@email.com")
                .build();

        testee.save(employee);

        // Act
        var result = testee.findByJPQL(firstName, lastName);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result)
                .returns("TestFirstName", Employee::getFirstName)
                .returns("TestLastName", Employee::getLastName)
                .returns("test@email.com", Employee::getEmail);
    }

}
