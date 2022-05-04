package by.tms.diplomrestc51.repository;

import by.tms.diplomrestc51.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<List<Device>> findAllByUsername(String username);
}
