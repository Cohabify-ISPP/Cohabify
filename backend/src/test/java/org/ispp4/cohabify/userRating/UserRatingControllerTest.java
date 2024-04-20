package org.ispp4.cohabify.userRating;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.bson.types.ObjectId;
import org.ispp4.cohabify.dto.UserRatingRequest;
import org.ispp4.cohabify.user.Plan;
import org.ispp4.cohabify.user.User;
import org.ispp4.cohabify.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Part;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRatingControllerTest {
    
    @MockBean
    private UserRatingService userRatingService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc controller;


    private User user;
    private User user2;
    private UserRating userRating;
    private ObjectMapper objectMapper = new ObjectMapper();
    private UserRatingRequest request;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(new ObjectId());
        user.setUsername("test_user");
        user.setPlan(Plan.BASIC);
        user.setAuthorities(List.of("USER"));
        when(userService.getUserByUsername("test_user")).thenReturn(user);

        user2 = new User();
        user2.setId(new ObjectId());
        user2.setUsername("test_user_2");
        user2.setPlan(Plan.OWNER);
        user2.setAuthorities(List.of("USER"));
        when(userService.getUserByUsername("test_user_2")).thenReturn(user2);


        userRating = new UserRating();
        userRating.setId(new ObjectId());
        userRating.setUser(user);
        userRating.setRatedUser(user2);
        userRating.setComment("Ordenado y limpio");
        when(userRatingService.findAll()).thenReturn(List.of(userRating));
        when(userRatingService.findByRatedUserId(user2.getId())).thenReturn(List.of(userRating));
        when(userRatingService.findByUserId(user.getId())).thenReturn(List.of(userRating));
        when(userRatingService.findById(userRating.getId())).thenReturn(userRating);
        when(userRatingService.findByUserIdAndRatedUserId(user2.getId(), user.getId())).thenReturn(List.of(userRating));


        request = new UserRatingRequest();
        request.setUser(user);
        request.setRatedUser(user2);
        request.setComment("Sucio y desordenado");
        when(userRatingService.save(any(UserRating.class))).thenReturn(userRating);

    }

    @Test
    void testGetAllUserRatings() throws Exception{
        controller.perform(get("/api/userRating")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testGetAllUserRatingsByRatedUserId() throws Exception{
        controller.perform(get("/api/userRating/ratedUser/{ratedUserId}", user2.getId())).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
        verify(userRatingService, times(1)).findByRatedUserId(user2.getId());
    }

    @Test
    void testGetAllUserRatingsByUserId() throws Exception{
        controller.perform(get("/api/userRating/user/{userId}", user.getId())).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
        verify(userRatingService, times(1)).findByUserId(user.getId());
    }

    @Test
    void testGetUserRatingById() throws Exception{
        controller.perform(get("/api/userRating/{userRatingId}", userRating.getId())).andExpect(status().isOk());
        verify(userRatingService, times(1)).findById(userRating.getId());
    }

    @Test
    void testDeleteUserRatingsByRatedUserId() throws Exception{
        controller.perform(delete("/api/userRating/ratedUser/{ratedUserId}", user2.getId()))
                .andExpect(status().isOk());

        verify(userRatingService, times(1)).deleteByRatedUserId(user2.getId());
    }

    @Test
    void testDeleteUserRatingsByRatedUserId2() throws Exception{
        controller.perform(delete("/api/userRating/{userId}/{ratedUserId}", user.getId(), user2.getId()))
                .andExpect(status().isOk());

        verify(userRatingService, times(1)).findByUserIdAndRatedUserId(user2.getId(), user.getId());
        verify(userRatingService, times(1)).deleteById(userRating.getId());

    }

    @Test
    void testDeleteUserRatingsByUserId() throws Exception{
        controller.perform(delete("/api/userRating/user/{userId}", user.getId()))
                .andExpect(status().isOk());

        verify(userRatingService, times(1)).deleteByUserId(user.getId());
    }

    @Test
    void testDeleteUserRatingById() throws Exception{
        controller.perform(delete("/api/userRating/{userRatingId}", userRating.getId()))
                .andExpect(status().isOk());

        verify(userRatingService, times(1)).deleteById(userRating.getId());
    }

    @Test
    void testCreateRatingUser() throws Exception{
        byte[] requestContent = objectMapper.writeValueAsBytes(request);
        Part requestData = new MockPart("string-data", "", requestContent, MediaType.APPLICATION_JSON);

        controller.perform(multipart("/api/userRating").part(requestData).contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());

        verify(userRatingService, times(1)).save(any(UserRating.class));
    }

    @Test
    void testUpdateUserRating() throws Exception{
        String requestContent = objectMapper.writeValueAsString(request);

        controller.perform(put("/api/userRating")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent))
                .andExpect(status().isOk());


        verify(userRatingService, times(1)).save(any(UserRating.class));

    }




    
}
