package de.asvaachen.workinghours.backend.project;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MemberEntityToMemberDtoConverter implements Converter<MemberEntity, MemberDto> {
    public MemberDto convert(MemberEntity member) {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(member.getId());
        memberDto.setFirstName(member.getFirstName());
        memberDto.setLastName(member.getLastName());
        return memberDto;
    }
}