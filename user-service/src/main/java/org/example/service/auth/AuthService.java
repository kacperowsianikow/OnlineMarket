package org.example.service.auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.config.PasswordEncoderConfig;
import org.example.dto.SignUpRequest;
import org.example.mapper.IUserMapper;
import org.example.model.ConfirmationToken;
import org.example.model.User;
import org.example.repository.IConfirmationTokenRepository;
import org.example.repository.IUserRepository;
import org.example.service.email.IEmailService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.example.service.email.EmailTemplate.createEmail;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final IEmailService iEmailService;
    private final IConfirmationTokenRepository iConfirmationTokenRepository;
    private final IUserRepository iUserRepository;
    private final IUserMapper iUserMapper;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private static final String CONFIRMATION_LINK =
            "http://localhost:8080/api/auth/sign-up/confirm?token=";
    private static final int EXPIRATION_TIME = 15;

    @Override
    @Transactional
    public String confirmToken(String confirmationToken) {
        ConfirmationToken confirmationTokenDB =
                iConfirmationTokenRepository.findByToken(confirmationToken);
        if (confirmationTokenDB.getConfirmedAt() != null) {
            return "Email has been confirmed already";
        } else if (confirmationTokenDB.getExpiresAt().isBefore(LocalDateTime.now())) {
            return "Confirmation link has expired";
        }

        iConfirmationTokenRepository.updateConfirmationTokenConfirmedAt(
                confirmationToken, LocalDateTime.now()
        );
        iUserRepository.updateUserIsEnabled(confirmationTokenDB.getUser().getEmail());

        return "Email confirmed";
    }

    @Override
    public String signUp(SignUpRequest signupRequest) {
        Optional<User> userFromDB = iUserRepository.findByEmail(signupRequest.email());
        if (userFromDB.isPresent()) {
            if (userFromDB.get().getIsEnabled()) {
                throw new IllegalStateException(
                        "User with email: " + signupRequest.email() + " already exists"
                );
            }

            User user = userFromDB.orElse(null);

            String link = CONFIRMATION_LINK + generateAndSaveToken(user);

            iEmailService.send(signupRequest.email(), createEmail(signupRequest.firstName(), link));

            return "We resend an email with confirmation link to Your email address";
        }

        User newUser = iUserMapper.toUser(signupRequest);

        String encryptedPassword = passwordEncoderConfig
                .bCryptPasswordEncoder()
                .encode(signupRequest.password());

        newUser.setPassword(encryptedPassword);
        newUser.setCreatedAt(LocalDateTime.now());

        User savedUser = iUserRepository.save(newUser);

        String link = CONFIRMATION_LINK + generateAndSaveToken(savedUser);

        iEmailService.send(signupRequest.email(), createEmail(signupRequest.firstName(), link));

        return "Please check Your email for confirmation mail";
    }

    private String generateAndSaveToken(User user) {
        String generatedToken = UUID.randomUUID().toString();

        Optional<ConfirmationToken> confTokenFromDB = iConfirmationTokenRepository.findByUser(user);
        if (confTokenFromDB.isPresent()) {
            iConfirmationTokenRepository.updateConfirmationTokenExpiresAt(user, LocalDateTime.now());
        }

        iConfirmationTokenRepository.save(new ConfirmationToken(
                generatedToken,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(EXPIRATION_TIME),
                user
        ));

        return generatedToken;
    }

}
