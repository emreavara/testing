package ch.avara.springboot.repository;

import ch.avara.springboot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("select e from Employee e where e.firstName=:firstName and e.lastName= :lastName ")
    Employee findByJPQL(String firstName, String lastName);
}
