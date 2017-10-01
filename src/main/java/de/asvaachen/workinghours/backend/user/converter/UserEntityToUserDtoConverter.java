package de.asvaachen.workinghours.backend.user.converter;

import de.asvaachen.workinghours.backend.user.UserEntity;
import de.asvaachen.workinghours.backend.user.model.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToUserDtoConverter implements Converter<UserEntity, UserDto> {

    @Override
    public UserDto convert(UserEntity source) {
        UserDto userDto = new UserDto();
        userDto.setId(source.getId());
        userDto.setFirstName(source.getFirstName());
        userDto.setLastName(source.getLastName());
        userDto.setEmail(source.getEmail());
        return userDto;
    }
}
