package de.asvaachen.workinghours.backend.user;

import de.asvaachen.workinghours.backend.user.model.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersService {

    UserRepository userRepository;
    UserEntityToUserDtoConverter converter;

    public UsersService(UserRepository userRepository, UserEntityToUserDtoConverter converter) {
        this.userRepository = userRepository;
        this.converter = converter;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }
}
