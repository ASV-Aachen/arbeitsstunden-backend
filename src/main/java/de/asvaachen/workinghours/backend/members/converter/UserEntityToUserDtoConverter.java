package de.asvaachen.workinghours.backend.members.converter;

import de.asvaachen.workinghours.backend.members.model.UserDto;
import de.asvaachen.workinghours.backend.members.persistence.UserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToUserDtoConverter implements Converter<UserEntity, UserDto> {

    @Override
    public UserDto convert(UserEntity source) {
        UserDto userDto = new UserDto();
        userDto.setId(source.getId());
        //userDto.setFirstName(source.getFirstName());
        //userDto.setLastName(source.getLastName());
        userDto.setEmail(source.getEmail());
        return userDto;
    }
}
