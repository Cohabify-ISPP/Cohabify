package org.ispp4.cohabify.houseAdvertisement;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.ispp4.cohabify.dto.AdvertisementHouseRequest;
import org.ispp4.cohabify.house.Heating;
import org.ispp4.cohabify.house.House;
import org.ispp4.cohabify.house.HouseService;
import org.ispp4.cohabify.storage.StorageService;
import org.ispp4.cohabify.tag.Tag;
import org.ispp4.cohabify.user.Plan;
import org.ispp4.cohabify.user.User;
import org.ispp4.cohabify.user.UserService;
import org.ispp4.cohabify.utils.Global;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Part;

@SpringBootTest
@AutoConfigureMockMvc
public class HouseAdvertisementControllerTest {

    @MockBean
    private HouseAdvertisementService advertisementService;

    @MockBean
    private UserService userService;

    @MockBean
    private HouseService houseService;

    @MockBean
    private StorageService storageService;

    @Autowired
    private MockMvc controller;

    @MockBean
    private Global global;

    private User userBasic;
    private User userBasic2;
    private User userOwner;
    private HouseAdvertisement advertisement2;
    private HouseAdvertisement advertisement3;
    private AdvertisementHouseRequest request;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        // Create a user with BASIC plan
        userBasic = new User();
        userBasic.setId(new ObjectId());
        userBasic.setUsername("test_user_basic");
        userBasic.setPlan(Plan.BASIC);
        userBasic.setAuthorities(List.of("USER"));
        when(userService.getUserByUsername("test_user_basic")).thenReturn(userBasic);

        userBasic2 = new User();
        userBasic2.setId(new ObjectId());
        userBasic2.setUsername("test_user_basic2");
        userBasic2.setPlan(Plan.OWNER);
        userBasic2.setAuthorities(List.of("USER"));
        when(userService.getUserByUsername("test_user_basic2")).thenReturn(userBasic2);

        userOwner = new User();
        userOwner.setId(new ObjectId());
        userOwner.setUsername("test_user_owner");
        userOwner.setPlan(Plan.OWNER);
        userOwner.setAuthorities(List.of("USER"));
        when(userService.getUserByUsername("test_user_owner")).thenReturn(userOwner);

        when(advertisementService.findAll()).thenReturn(Arrays.asList(
                new HouseAdvertisement(),
                new HouseAdvertisement()));

        House house1 = new House();
        house1.setId(new ObjectId());

        House house2 = new House();
        house2.setId(new ObjectId());

        HouseAdvertisement advertisement1 = new HouseAdvertisement();
        advertisement1.setId(new ObjectId());
        advertisement1.setAuthor(userBasic2);
        advertisement1.setHouse(house1);

        advertisement2 = new HouseAdvertisement();
        advertisement2.setId(new ObjectId("60d313f3f682fbd39a1b8b5a"));
        advertisement2.setAuthor(userBasic);
        advertisement2.setHouse(house2);

        when(advertisementService.findAll()).thenReturn(List.of(advertisement1, advertisement2));

        when(advertisementService.findByAuthorId(userBasic.getId())).thenReturn(List.of(advertisement2));
        when(advertisementService.findByAuthorId(userBasic2.getId())).thenReturn(List.of(advertisement1));

        House house3 = new House();
        house3.setId(new ObjectId());
        house3.setRoomsNumber(4);
        house3.setBathroomsNumber(4);
        house3.setArea(400);
        house3.setFloor(4);
        house3.setLocation("Test location");
        house3.setCadastre("12345678901234567890");
        house3.setHeating(Heating.AIR_CONDITIONING);
        house3.setTags(new ArrayList<Tag>());
        GeoJsonPoint point = new GeoJsonPoint(2, 2);
        house3.setLocationPoint(point);

        advertisement3 = new HouseAdvertisement();
        advertisement3.setTitle("Test Advertisement");
        advertisement3.setDescription("This is a test advertisement");
        advertisement3.setPrice(1000.0);
        advertisement3.setTenants(List.of(userBasic));
        advertisement3.setId(new ObjectId());
        advertisement3.setHouse(house3);
        advertisement3.setAuthor(userOwner);

        request = new AdvertisementHouseRequest();
        request.setAuthor(advertisement3.getAuthor());
        request.setTitle(advertisement3.getTitle());
        request.setDescription(advertisement3.getDescription());
        request.setPrice(advertisement3.getPrice());
        request.setTenants(advertisement3.getTenants());
        request.setId(advertisement3.getId());
        request.setHouse(advertisement3.getHouse());
        request.setHouseId(house3.getId());
        request.setImagesB(List.of());

        when(houseService.findById(any(ObjectId.class))).thenReturn(Optional.of(house3));

        when(advertisementService.findById(advertisement2.getId())).thenReturn(Optional.of(advertisement2));
        when(advertisementService.findAdById(advertisement2.getId())).thenReturn(advertisement2);
        when(advertisementService.findAdById(advertisement3.getId())).thenReturn(advertisement3);

        when(houseService.save(any(House.class))).thenReturn(house3);
        when(advertisementService.save(any(HouseAdvertisement.class))).thenReturn(advertisement3);

        // Simular el servicio de almacenamiento de imágenes
        try {
            when(storageService.saveImage(anyString(), any(MultipartFile.class)))
                    .thenReturn("http://example.com/image.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * // Testea que un usuario anonimo no tiene acceso anticipado
     * 
     * @Test
     * void testGetAllAdvertisements() throws Exception {
     * controller.perform(get("/api/advertisements/houses"))
     * .andExpect(status().isOk())
     * .andExpect(jsonPath("$", hasSize(1)));
     * }
     */

    // Testea con un usuario basico que no es dueño del anuncio recien creado (No lo
    // ve por falta de acceso anticipado)
    @Test
    void testGetAllAdvertisementsWithBasicPlan() throws Exception {
        controller.perform(get("/api/advertisements/houses").with(user("test_user_basic")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("60d313f3f682fbd39a1b8b5a")));
    }

    // Testea con un usuario basico que es dueño del anuncio recien creado
    @Test
    void testGetAllAdvertisementsWithBasic2Plan() throws Exception {
        controller.perform(get("/api/advertisements/houses").with(user("test_user_basic2")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testGetAdvertisement() throws Exception {
        controller.perform(get("/api/advertisements/houses/{id}", advertisement2.getId()))
                .andExpect(status().isOk());

        // Verificar que se llamó al método del servicio con el ID del anuncio correcto
        verify(advertisementService, times(1)).findById(advertisement2.getId());
    }

    @Test
    void testGetAdvertisementsByAuthor() throws Exception {
        controller.perform(get("/api/advertisements/houses/owner/{id}", userBasic.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        // Verificar que se llamó al método del servicio con el ID del autor correcto
        verify(advertisementService, times(1)).findByAuthorId(userBasic.getId());
    }

    @Test
    void testCreateAdvertisement() throws Exception {
        byte[] requestContent = objectMapper.writeValueAsBytes(request);
        Part requestData = new MockPart("string-data", "", requestContent, MediaType.APPLICATION_JSON);
        Part requestImage1 = new MockPart("images", "image1.jpg", "mock image 1".getBytes(), MediaType.IMAGE_PNG);
        Part requestImage2 = new MockPart("images", "image2.jpg", "mock image 2".getBytes(), MediaType.IMAGE_PNG);

        User currentUser = userOwner;
        when(global.getCurrentUser()).thenReturn(currentUser);

        controller.perform(multipart("/api/advertisements/houses")
                .part(requestData)
                .part(requestImage1)
                .part(requestImage2)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());

        // Verificar que se llamó al servicio de casa y al servicio de anuncios con los
        // datos correctos
        verify(houseService, times(1)).save(any(House.class));
        verify(advertisementService, times(3)).save(any(HouseAdvertisement.class));
        verify(storageService, times(2)).saveImage(anyString(), any(MultipartFile.class));
    }

    @Test
    void testFindHeating() throws Exception {
        List<Heating> heatings = Arrays.asList(Heating.values());

        controller.perform(get("/api/advertisements/houses/heating"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(heatings.size())))
                .andExpect(jsonPath("$[0]").value(heatings.get(0).toString()));

    }

    @Test
    void testUpdateAdvertisement() throws Exception {
        byte[] requestContent = objectMapper.writeValueAsBytes(request);
        Part requestData = new MockPart("string-data", "", requestContent, MediaType.APPLICATION_JSON);
        Part requestImage1 = new MockPart("images", "image1.jpg", "mock image 1".getBytes(), MediaType.IMAGE_PNG);
        Part requestImage2 = new MockPart("images", "image2.jpg", "mock image 2".getBytes(), MediaType.IMAGE_PNG);

        User currentUser = userOwner;
        when(global.getCurrentUser()).thenReturn(currentUser);

        controller.perform(multipart("/api/advertisements/houses/{id}", advertisement3.getId())
                .part(requestData)
                .part(requestImage1)
                .part(requestImage2)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                // Necesario para cambiar el comportamiento de multipart que esta hardcoded para
                // enviar un POST unicamente
                .with(request -> {
                    request.setMethod("PUT");
                    return request;
                })
                .with(user("test_user_owner")))
                .andExpect(status().isCreated());

        // Verificar que se llamó al servicio de casa y al servicio de anuncios con los
        // datos correctos
        verify(houseService, times(1)).save(any(House.class));
        verify(advertisementService, times(3)).save(any(HouseAdvertisement.class));
        verify(storageService, times(2)).saveImage(anyString(), any(MultipartFile.class));
    }

    @Test
    void testDeleteAdvertisement() throws Exception {
        ObjectId advertisementId = advertisement2.getId();

        User currentUser = userBasic;
        when(global.getCurrentUser()).thenReturn(currentUser);

        controller.perform(delete("/api/advertisements/houses/{id}", advertisementId))
                .andExpect(status().isNoContent());

        // Verificar que se llamó al método del servicio
        verify(advertisementService, times(1)).deleteById(advertisementId);
    }

    @Test
    void testDeleteAdvertisementForbidden() throws Exception {
        ObjectId advertisementId = advertisement2.getId();

        User currentUser = userBasic2;
        when(global.getCurrentUser()).thenReturn(currentUser);

        controller.perform(delete("/api/advertisements/houses/{id}", advertisementId))
                .andExpect(status().isForbidden());

        // Verificar que no se llamó al método del servicio
        verify(advertisementService, never()).deleteById(any());
    }

}
