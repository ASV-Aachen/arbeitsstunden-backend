package de.asvaachen.workinghours.backend.members.service;

import de.asvaachen.workinghours.backend.members.converter.UserEntityToUserDtoConverter;
import de.asvaachen.workinghours.backend.members.model.AsvStatus;
import de.asvaachen.workinghours.backend.members.persistence.UserEntity;
import de.asvaachen.workinghours.backend.members.persistence.UserRepository;
import de.asvaachen.workinghours.backend.project.persistence.MemberEntity;
import de.asvaachen.workinghours.backend.project.persistence.MemberRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static de.asvaachen.workinghours.backend.configuration.SecurityConfiguration.PASSWORD_ENCODER;
import static de.asvaachen.workinghours.backend.configuration.SecurityConfiguration.ROLE_TAKEL;

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

    public List<MemberEntity> getAllTakelMembers() {
        return userRepository.findAllByRole("ROLE_TAKEL").stream()
                .map(UserEntity::getMember)
                .collect(Collectors.toList());
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