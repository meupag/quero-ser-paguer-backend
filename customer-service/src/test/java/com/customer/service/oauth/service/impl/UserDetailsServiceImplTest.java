package com.customer.service.oauth.service.impl;

import com.customer.service.oauth.model.User;
import com.customer.service.oauth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("User details service test")
public class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private UserDetailsService userDetailsService;

    @BeforeEach
    public void before() {
        this.userDetailsService = new UserDetailsServiceImpl(this.userRepository);
    }

    @Test
    @DisplayName("Should throw UsernameNotFoundException")
    public void shouldThrowUsernameNotFoundException(){
        when(this.userRepository.findByUsername(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> this.userDetailsService.loadUserByUsername("john"));
    }

    @Test
    public void shouldLoadUser(){
        when(this.userRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(getUser()));

        UserDetails userDetails = this.userDetailsService.loadUserByUsername("john");

        assertThat(userDetails.getUsername(), is(getUser().getUsername()));
        assertThat(userDetails.getPassword(), is(getUser().getPassword()));
    }

    private User getUser(){
        return User.builder()
                .id("5ce43e1f3295e108ee5bcf86")
                .username("john")
                .password("password")
                .build();
    }
}
