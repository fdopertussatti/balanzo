package br.com.balanzo.application.identidade;

import br.com.balanzo.common.exception.ResourceNotFoundException;
import br.com.balanzo.domain.identidade.entity.User;
import br.com.balanzo.infrastructure.persistence.identidade.UserRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateProfile {

    private final UserRepository userRepository;

    public UpdateProfile(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User run(UUID userId, String name, String defaultCurrency, String timezone) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        if (name != null && !name.isBlank()) {
            user.setName(name);
        }
        if (defaultCurrency != null && defaultCurrency.length() == 3) {
            user.setDefaultCurrency(defaultCurrency);
        }
        if (timezone != null && !timezone.isBlank()) {
            user.setTimezone(timezone);
        }

        return userRepository.save(user);
    }
}
