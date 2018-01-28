package de.asvaachen.workinghours.backend.member.service;

import de.asvaachen.workinghours.backend.project.persistence.MemberEntity;
import de.asvaachen.workinghours.backend.project.persistence.MemberRepository;
import de.asvaachen.workinghours.backend.member.converter.UserEntityToUserDtoConverter;
import de.asvaachen.workinghours.backend.member.model.AsvStatus;
import de.asvaachen.workinghours.backend.member.persistence.UserEntity;
import de.asvaachen.workinghours.backend.member.persistence.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static de.asvaachen.workinghours.backend.configuration.SecurityConfiguration.PASSWORD_ENCODER;

@Service
public class UserService {

    private UserRepository userRepository;
    private MemberRepository memberRepository;
    private UserEntityToUserDtoConverter converter;
    private ReductionStatusService reductionStatusService;

    public UserService(UserRepository userRepository, MemberRepository memberRepository, UserEntityToUserDtoConverter converter, ReductionStatusService reductionStatusService) {
        this.userRepository = userRepository;
        this.memberRepository = memberRepository;
        this.converter = converter;
        this.reductionStatusService = reductionStatusService;
    }

    public void updateUser(MemberEntity memberEntity) {
        memberRepository.save(memberEntity);
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    @Transactional
    public boolean createUser(MemberEntity member, Integer firstSeason, AsvStatus asvStatus) {
        if (!userRepository.findByEmail(member.getUser().getEmail()).isPresent()) {
            memberRepository.save(member);

            reductionStatusService.createInitial(member, firstSeason, asvStatus);

            return true;
        } else {
            return false;
        }
    }

    public void updatePassword(String email, String newPassword) {
        userRepository.findByEmail(email).ifPresent(userEntity -> {
                    userEntity.setPassword(PASSWORD_ENCODER.encode(newPassword));
                    userRepository.save(userEntity);
                }
        );
    }
}