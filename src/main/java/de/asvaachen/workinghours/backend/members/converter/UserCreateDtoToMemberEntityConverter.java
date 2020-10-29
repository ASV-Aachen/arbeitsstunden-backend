package de.asvaachen.workinghours.backend.members.converter;

import de.asvaachen.workinghours.backend.members.model.CreateMemberDto;
import de.asvaachen.workinghours.backend.members.persistence.UserEntity;
import de.asvaachen.workinghours.backend.project.persistence.MemberEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static de.asvaachen.workinghours.backend.configuration.SecurityConfiguration.AUTHORITY_USER;

@Component
public class UserCreateDtoToMemberEntityConverter implements Converter<CreateMemberDto, MemberEntity> {

    public static final String ASV_PASSWORD = "$2a$10$ljHyydV.cFZAZJCsWWbmFOuvjiKnj1lOw.3ynYkl6GOTOF8OTxoaG";

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
            userEntity.setRole(AUTHORITY_USER);
            userEntity.setPassword(ASV_PASSWORD);
            return userEntity;
        }
    }
}