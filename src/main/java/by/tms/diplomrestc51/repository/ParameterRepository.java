package by.tms.diplomrestc51.repository;

import by.tms.diplomrestc51.entity.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {
}
