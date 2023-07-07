package com.example.openoff.domain.user.domain.service;

import com.example.openoff.common.annotation.DomainService;
import com.example.openoff.common.exception.Error;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.auth.domain.entity.SocialAccount;
import com.example.openoff.domain.user.application.dto.request.UserOnboardingRequestDto;
import com.example.openoff.domain.user.domain.entity.User;
import com.example.openoff.domain.user.domain.exception.UserNotFoundException;
import com.example.openoff.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class UserQueryService {
    private final UserUtils userUtils;
    private final UserRepository userRepository;

    public User initUserSave(SocialAccount socialAccount, String socialType) {
        return userRepository.findBySocialId(socialAccount.getId())
                .orElseGet(() -> {
                    switch (socialType){
                        case "kakao":
                            return userRepository.save(User.builder().kakaoAccount(socialAccount).isActive(true).build());
                        case "google":
                            return userRepository.save(User.builder().googleAccount(socialAccount).isActive(true).build());
                        case "apple":
                            return userRepository.save(User.builder().appleAccount(socialAccount).isActive(true).build());
                        case "normal":
                            return userRepository.save(User.builder().normalAccount(socialAccount).isActive(true).build());
                        default:
                            throw new UserNotFoundException(Error.USER_NOT_FOUND);
                    }
                });
    }

    @Transactional
    public void updateOnboardingData(UserOnboardingRequestDto userOnboardingRequestDto) {
        User user = userUtils.getUser();
        user.updateBasicUserInfo(userOnboardingRequestDto.getNickname(), userOnboardingRequestDto.getUsername(),
                userOnboardingRequestDto.getYear(), userOnboardingRequestDto.getMonth(), userOnboardingRequestDto.getDay(),
                userOnboardingRequestDto.getGender());
    }
}
