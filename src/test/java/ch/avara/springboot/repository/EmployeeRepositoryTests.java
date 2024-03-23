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

}
