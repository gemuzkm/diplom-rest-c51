package by.tms.diplomrestc51.mapper;

import by.tms.diplomrestc51.dto.UserDTO;
import by.tms.diplomrestc51.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userToUserDto(User user);
    User userDtoToUser(UserDTO userDto);
}
