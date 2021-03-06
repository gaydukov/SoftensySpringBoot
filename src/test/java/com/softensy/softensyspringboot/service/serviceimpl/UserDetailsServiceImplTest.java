package com.softensy.softensyspringboot.service.serviceimpl;

import com.softensy.softensyspringboot.TestDataGenerator;
import com.softensy.softensyspringboot.entity.UserSecurity;
import com.softensy.softensyspringboot.mapper.UserSecurityMapper;
import com.softensy.softensyspringboot.repository.UserSecurityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static com.softensy.softensyspringboot.TestDataGenerator.getUserDetails;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserDetailsServiceImplTest {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private UserSecurityMapper userSecurityMapper;
    @MockBean
    private UserSecurityRepository userSecurityRepository;

    @Test
    @DisplayName("checking loud userDetails by userName")
    void testLoadUserDetailsByUserName() {
        // given
        UserDetails expectedUserDetails = getUserDetails();
        UserSecurity patientSecurity = TestDataGenerator.getPatientSecurity();
        // when
        when(userSecurityRepository.findByLogin(anyString())).thenReturn(Optional.of(patientSecurity));
        when(userSecurityMapper.userSecurityToUserDetails(any(UserSecurity.class))).thenReturn(expectedUserDetails);
        UserDetails actualDoctor = userDetailsService.loadUserByUsername(patientSecurity.getLogin());
        //then
        assertEquals(expectedUserDetails, actualDoctor);
        assertNotNull(expectedUserDetails);
        verify(userSecurityRepository, times(1)).findByLogin(anyString());
        verify(userSecurityMapper, times(1)).userSecurityToUserDetails(any(UserSecurity.class));
    }

    @Test
    @DisplayName("checking loud userDetails by invalid userName")
    void testLoadUserDetailsByInvalidUserName() {
        //given
        UserDetails userDetails = getUserDetails();
        String login = userDetails.getUsername();
        // when
        when(userSecurityRepository.findByLogin(anyString())).thenThrow(UsernameNotFoundException.class);
        //then
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(login));
        verify(userSecurityRepository, times(1)).findByLogin(anyString());

    }

}
