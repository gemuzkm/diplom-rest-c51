package by.tms.diplomrestc51.repository;

import by.tms.diplomrestc51.entity.ParameterValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParameterValuesRepository extends JpaRepository<ParameterValues, Long> {
}
