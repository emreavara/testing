package ch.avara.springboot.repository;

import ch.avara.springboot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    @Query("select e from Employee e where e.firstName=:firstName and e.lastName= :lastName ")
    Employee findByJPQL(String firstName, String lastName);

    @Query(value = "select * from employees e where e.first_name=:firstName and e.last_name= :lastName ", nativeQuery = true)
    Employee findByNativeSql(String firstName, String lastName);
}
