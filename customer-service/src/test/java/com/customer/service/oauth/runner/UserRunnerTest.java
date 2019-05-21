package com.customer.service.oauth.runner;

import com.customer.service.oauth.model.User;
import com.customer.service.oauth.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.DefaultApplicationArguments;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("User runner service test")
public class UserRunnerTest {

    @Mock
    private UserRepository userRepository;

    private ApplicationRunner applicationRunner;

    @BeforeEach
    public void before() {
        this.applicationRunner = new UserRunner(this.userRepository);
    }

    @Test
    @DisplayName("Should not save user")
    public void shouldNotSaveUser() throws Exception {
        when(this.userRepository.findAll())
                .thenReturn(Lists.newArrayList(getUser()));

        this.applicationRunner.run(new DefaultApplicationArguments(new String[]{"1"}));

        verify(this.userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should save user")
    public void shouldSaveUser() throws Exception {
        when(this.userRepository.findAll())
                .thenReturn(Lists.newArrayList());
        when(this.userRepository.save(any()))
                .thenReturn(getUser());

        this.applicationRunner.run(new DefaultApplicationArguments(new String[]{"1"}));

        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(this.userRepository, atLeastOnce()).save(argument.capture());
        assertNull(argument.getValue().getId());
        assertThat(argument.getValue().getUsername(), is(getUser().getUsername()));
        assertThat(argument.getValue().getPassword(), is(getUser().getPassword()));
    }

    private User getUser(){
        return User.builder()
                .id("5ce43e1f3295e108ee5bcf86")
                .username("customer-username")
                .password("$2a$04$nJnQN4tMSCze1B90aq1J1u7i.M.wa3NJlZzFFxDV3F3UR2DGvluMG")
                .build();
    }
}