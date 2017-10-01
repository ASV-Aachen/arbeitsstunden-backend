package de.asvaachen.workinghours.backend.user.converter;

import de.asvaachen.workinghours.backend.user.UserEntity;
import de.asvaachen.workinghours.backend.user.model.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToUserEntityConverter implements Converter<UserDto, UserEntity> {

    @Override
    public UserEntity convert(UserDto source) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(source.getId());
        userEntity.setFirstName(source.getFirstName());
        userEntity.setLastName(source.getLastName());
        userEntity.setEmail(source.getEmail());
        return userEntity;
    }
}
