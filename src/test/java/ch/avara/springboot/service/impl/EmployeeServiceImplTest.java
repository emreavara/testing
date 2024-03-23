package ch.avara.springboot.service.impl;

import ch.avara.springboot.exception.ResouceNotFoundException;
import ch.avara.springboot.model.Employee;
import ch.avara.springboot.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl testee;

    @Mock
    private EmployeeRepository employeeRepositoryMock;

    @Test
    void shouldSaveEmployee() {
        // Arrange
        var email = "test@email.com";
        var employee = Employee.builder()
                .firstName("TestFirstName")
                .lastName("TestLastName")
                .email(email)
                .build();

        when(employeeRepositoryMock.findByEmail(email)).thenReturn(Optional.empty());
        when(employeeRepositoryMock.save(employee)).thenReturn(employee);

        // Act
        var result = testee.save(employee);

        // Assert
        assertThat(result)
                .returns("TestFirstName", Employee::getFirstName)
                .returns("TestLastName", Employee::getLastName)
                .returns("test@email.com", Employee::getEmail);

    }

    @Test
    void shouldThrowResourceNotFoundException() {
        // Arrange
        var email = "test@email.com";
        var employee = Employee.builder()
                .firstName("TestFirstName")
                .lastName("TestLastName")
                .email(email)
                .build();

        when(employeeRepositoryMock.findByEmail(email)).thenReturn(Optional.of(employee));

        // Act
        var result = catchThrowable(() -> testee.save(employee));

        // Assert
        verify(employeeRepositoryMock, never()).save(any(Employee.class));
        assertThat(result).isInstanceOf(ResouceNotFoundException.class);

    }

}