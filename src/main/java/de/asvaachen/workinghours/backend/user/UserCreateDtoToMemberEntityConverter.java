package de.asvaachen.workinghours.backend.user;

import de.asvaachen.workinghours.backend.project.MemberEntity;
import de.asvaachen.workinghours.backend.user.model.CreateMemberDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserCreateDtoToMemberEntityConverter implements Converter<CreateMemberDto, MemberEntity> {
    @Override
    public MemberEntity convert(CreateMemberDto source) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setUser(createUserEntity(memberEntity, source.getEmail()));
        memberEntity.setFirstName(source.getFirstName());
        memberEntity.setLastName(source.getLastName());

        return memberEntity;
    }

    private UserEntity createUserEntity(MemberEntity memberEntity, String email) {
        if (email == null) {
            return null;
        } else {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(UUID.randomUUID());
            userEntity.setMember(memberEntity);
            userEntity.setEmail(email);
            return userEntity;
        }
    }
}