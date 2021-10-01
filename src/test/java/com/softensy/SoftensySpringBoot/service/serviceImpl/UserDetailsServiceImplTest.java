package com.softensy.SoftensySpringBoot.service.serviceImpl;

import com.softensy.SoftensySpringBoot.TestDataGenerator;
import com.softensy.SoftensySpringBoot.entity.UserSecurity;
import com.softensy.SoftensySpringBoot.mapper.UserSecurityMapper;
import com.softensy.SoftensySpringBoot.repository.UserSecurityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static com.softensy.SoftensySpringBoot.TestDataGenerator.getUserDetails;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

}
