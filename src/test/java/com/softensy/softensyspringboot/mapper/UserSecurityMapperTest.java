package com.softensy.softensyspringboot.mapper;

import com.softensy.softensyspringboot.entity.UserSecurity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import static com.softensy.softensyspringboot.TestDataGenerator.getPatientSecurity;
import static com.softensy.softensyspringboot.TestDataGenerator.getUserDetails;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserSecurityMapperTest {
    @Autowired
    private UserSecurityMapper userSecurityMapper;

    @Test
    @DisplayName("check mapping userSecurity to userDetails")
    void testMappingUserSecurityToUserDetails() {
        // given
        UserSecurity userSecurity = getPatientSecurity();
        UserDetails expectedUserDetails = getUserDetails();
        // when
        UserDetails actualUserDetails = userSecurityMapper.userSecurityToUserDetails(userSecurity);
        //then
        assertEquals(expectedUserDetails.getUsername(), actualUserDetails.getUsername());
        assertEquals(expectedUserDetails.getPassword(), actualUserDetails.getPassword());
        assertEquals(expectedUserDetails.getAuthorities(), actualUserDetails.getAuthorities());
        assertEquals(expectedUserDetails, actualUserDetails);
        assertNotNull(expectedUserDetails);
        assertNotNull(actualUserDetails);
    }

}
