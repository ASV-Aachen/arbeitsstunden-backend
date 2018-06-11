package de.asvaachen.workinghours.backend.project.converter;

import de.asvaachen.workinghours.backend.members.persistence.UserEntity;
import de.asvaachen.workinghours.backend.project.model.MemberDto;
import de.asvaachen.workinghours.backend.project.persistence.MemberEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MemberEntityToMemberDtoConverter implements Converter<MemberEntity, MemberDto> {
    public MemberDto convert(MemberEntity member) {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(member.getId());
        memberDto.setFirstName(member.getFirstName());
        memberDto.setLastName(member.getLastName());

        UserEntity user = member.getUser();
        if (user != null) {
            memberDto.setEmail(user.getEmail());
        }

        return memberDto;
    }
}