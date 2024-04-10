package org.ispp4.cohabify.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.bson.types.ObjectId;
import org.ispp4.cohabify.configuration.CustomAuthenticationManager;
import org.ispp4.cohabify.dto.LoginRequest;
import org.ispp4.cohabify.dto.UserRegisterRequest;
import org.ispp4.cohabify.storage.StorageService;
import org.ispp4.cohabify.tag.Tag;
import org.ispp4.cohabify.user.CustomUserDetailsService;
import org.ispp4.cohabify.user.Gender;
import org.ispp4.cohabify.user.Plan;
import org.ispp4.cohabify.user.User;
import org.ispp4.cohabify.user.UserService;
import org.ispp4.cohabify.utils.MailHelper;
import org.ispp4.cohabify.utils.RandomStringGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockPart;
import jakarta.servlet.http.Part;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private CustomAuthenticationManager authenticationManager;

    @MockBean
    private CustomUserDetailsService customUserService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private StorageService storageService;

    @MockBean
    private RandomStringGenerator randomStringGenerator;

    @MockBean
    private MailHelper mailHelper;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        
        when(authenticationManager.authenticate(any()))
        .thenAnswer(invocation -> {
            Authentication auth = invocation.getArgument(0);
            UserDetails userDetails = Mockito.mock(UserDetails.class);
            return new UsernamePasswordAuthenticationToken(userDetails, auth.getCredentials(), userDetails.getAuthorities());
        });
        when(jwtService.generateToken(any())).thenReturn("mocked_jwt_token");

        when(userService.findByVerificationCode(any())).thenReturn(new User());

        // Mock the behavior of jwtService.generateToken() to return a mock JWT token
        String mockJwtToken = "mocked_jwt_token";
        when(jwtService.generateToken(any())).thenReturn(mockJwtToken);
        when(jwtService.extractUserName(anyString())).thenReturn("mocked_username");

        // Define a mock User object
        User user = new User();
        user.setId(new ObjectId());
        user.setPlan(Plan.BASIC);
        user.setAuthorities(List.of("USER"));
        user.setUsername("mocked_username");
        user.setEnabled(Boolean.TRUE);

        when(userService.save(any())).thenReturn(user);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
        "mocked_username", "password", new ArrayList<>()
        );

        // Mock the behavior of userService.getUserByUsername() to return the mock User object
        when(userService.getUserByUsername(anyString())).thenReturn(user);
        when(customUserService.loadUserByUsername(anyString())).thenReturn(userDetails);
    }

    @Test
    public void testRegister() throws Exception {
        UserRegisterRequest request = new UserRegisterRequest();
        request.setName("John Doe");
        request.setUsername("johndoe");
        request.setPassword("password123");
        request.setEmail("johndoe@example.com");
        request.setTag(new ArrayList<Tag>());
        request.setPhone("123456789");
        request.setGender(Gender.MASCULINO);
        request.setGoogleOAuthToken("google_oauth_token");

        byte[] requestContent = objectMapper.writeValueAsBytes(request);
        Part requestData = new MockPart("string-data", "", requestContent, MediaType.APPLICATION_JSON);
        Part profilePic = new MockPart("profile-pic", "image1.jpg", "mock image".getBytes(), MediaType.IMAGE_PNG);

        mockMvc.perform(
                multipart("/auth/register")
                        .part(requestData)
                        .part(profilePic)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        )
                .andExpect(status().isCreated());
    }



    @Test
    public void testLogin() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsername("johndoe");
        request.setPassword("password123");

        mockMvc.perform(
                post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    public void testVerifyAccount() throws Exception {
        mockMvc.perform(
                get("/auth/register/verify/{verificationCode}", "mock_verification_code")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    /* 
    @Test
    public void testLoginGoogle() throws Exception {
        String googleAuthToken = "mocked_google_auth_token";

        mockMvc.perform(
                post("/auth/login/google")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"" + googleAuthToken + "\"")
        )
                .andExpect(status().isOk()) 
                .andExpect(jsonPath("$.token").exists());
    }
    */


    @Test
    public void testGetUser() throws Exception {
        mockMvc.perform(
                post("/auth/getUser")
                        .header("Authentication", "Bearer mocked_jwt_token")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").exists());
    }
}
