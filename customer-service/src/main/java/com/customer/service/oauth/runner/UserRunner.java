package com.customer.service.oauth.runner;

import com.customer.service.oauth.model.User;
import com.customer.service.oauth.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class UserRunner implements ApplicationRunner {

    private final UserRepository userRepository;

    public UserRunner(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        List<User> users = this.userRepository.findAll();
        if (users.isEmpty()) {
            User user = User.builder()
                    .username("customer-username")
                    .password("$2a$04$nJnQN4tMSCze1B90aq1J1u7i.M.wa3NJlZzFFxDV3F3UR2DGvluMG")
                    .build();
            this.userRepository.save(user);
        }
    }
}
