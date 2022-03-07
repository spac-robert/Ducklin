package ro.robert.duckling.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.robert.duckling.dto.RegisterRequest;
import ro.robert.duckling.exception.SpringDucklinException;
import ro.robert.duckling.model.NotificationEmail;
import ro.robert.duckling.model.User;
import ro.robert.duckling.model.VerificationToken;
import ro.robert.duckling.repository.UserRepository;
import ro.robert.duckling.repository.VerificationTokenRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;

    //@Transactional
    public void signup(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);
        String token = generateVerificationToken(user);
        NotificationEmail notificationEmail = new NotificationEmail("Please activate your account", user.getEmail(),
                "Thank you for signing up to Ducklin, " +
                        "please click on the below url to activate your account : " +
                        "http://localhost:8080/api/auth/accountVerification/" + token);
        mailService.sendEmail(notificationEmail);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new SpringDucklinException("invalid Token"));
        fetchUserAndEnable(verificationToken.get());
    }


    //@Transactional
    void fetchUserAndEnable(VerificationToken token) {
        String username = token.getUser().getUsername();
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new SpringDucklinException("User " + username + " not found"));
        user.setEnabled(true);
        userRepository.save(user);
    }
}
