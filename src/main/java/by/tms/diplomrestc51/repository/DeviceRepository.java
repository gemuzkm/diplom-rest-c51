package by.tms.diplomrestc51.repository;

import by.tms.diplomrestc51.entity.Device;
import by.tms.diplomrestc51.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<List<Device>> findAllByUser(User user);

    Optional<String> findBySerialNumber(String serialNumber);

    Optional<String> findByMacAddress(String macAddress);


}
