package de.asvaachen.workinghours.backend.user;

import de.asvaachen.workinghours.backend.user.model.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToUserDtoConverter implements Converter<UserEntity, UserDto> {

    @Nullable
    @Override
    public UserDto convert(UserEntity source) {
        return new UserDto(source.getId(), source.getFirstName(), source.getLastName(), source.getEmail());
    }
}
