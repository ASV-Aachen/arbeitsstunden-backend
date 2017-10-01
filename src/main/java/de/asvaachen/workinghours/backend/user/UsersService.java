package de.asvaachen.workinghours.backend.user;

import de.asvaachen.workinghours.backend.user.converter.UserEntityToUserDtoConverter;
import de.asvaachen.workinghours.backend.user.model.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public UserDto updateUser(UserEntity userEntity) {
        if (userEntity.getId() == null) {
            return null;
        }

        Optional<UserEntity> foundUserEntity = userRepository.findById(userEntity.getId());
        if (foundUserEntity.isPresent()) {
            return converter.convert(userRepository.save(userEntity));
        }
        return null;
    }

    public UserDto createUser(UserEntity userEntity) throws EmailExistsException {
        userEntity.setId(null);

        if (emailExists(userEntity.getEmail())) {
            throw new EmailExistsException(userEntity.getEmail());
        }
        return converter.convert(userRepository.save(userEntity));
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
