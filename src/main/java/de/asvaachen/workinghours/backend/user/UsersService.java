package de.asvaachen.workinghours.backend.user;

import de.asvaachen.workinghours.backend.project.MemberEntity;
import de.asvaachen.workinghours.backend.project.MemberRepository;
import de.asvaachen.workinghours.backend.user.converter.UserEntityToUserDtoConverter;
import de.asvaachen.workinghours.backend.user.model.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersService {

    UserRepository userRepository;
    MemberRepository memberRepository;
    UserEntityToUserDtoConverter converter;

    public UsersService(UserRepository userRepository, MemberRepository memberRepository, UserEntityToUserDtoConverter converter) {
        this.userRepository = userRepository;
        this.memberRepository = memberRepository;
        this.converter = converter;
    }

    public void updateUser(MemberEntity memberEntity) {
        memberRepository.save(memberEntity);
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }
}
