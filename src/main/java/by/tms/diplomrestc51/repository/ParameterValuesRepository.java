package by.tms.diplomrestc51.repository;

import by.tms.diplomrestc51.entity.ParameterValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface ParameterValuesRepository extends JpaRepository<ParameterValues, Long> {
}
