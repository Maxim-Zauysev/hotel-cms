package com.example.hotel_cms;

import com.example.hotel_cms.model.Hotel;
import com.example.hotel_cms.model.Role;
import com.example.hotel_cms.model.RoleType;
import com.example.hotel_cms.model.User;
import com.example.hotel_cms.repository.jpa.UserRepository;
import com.example.hotel_cms.service.UserService;
import com.example.hotel_cms.web.response.HotelResponse;
import com.example.hotel_cms.web.response.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public abstract class AbstractTestController {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    protected static PostgreSQLContainer postgreSQLContainer;
    @Autowired
    protected UserService userService;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected WebApplicationContext context;

    static {
        DockerImageName dockerImageName = DockerImageName.parse("postgres:16.3");
        postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer<>(dockerImageName)
                .withReuse(true);
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    public static void registerProperties(DynamicPropertyRegistry registry) {
        String jdbcUrl = postgreSQLContainer.getJdbcUrl();
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.url", () -> jdbcUrl);
    }

    @BeforeEach
    public void setup() {
        userService.create(User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .email("admin@example.com")
                .build(),Role.from(RoleType.ROLE_ADMIN));

        userService.create(User.builder()
                .username("user")
                .password(passwordEncoder.encode("user"))
                .email("user@example.com")
                .build(),Role.from(RoleType.ROLE_USER));
    }

    @AfterEach
    public void afterEach() {
        userRepository.deleteAll();
    }

    protected User createUser(Long id, RoleType roleType){
         return new User(
                id,
                "user\s" + id,
                passwordEncoder.encode("user"),
                "email\s" + id,
                Role.from(roleType),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }
    protected Hotel createHotel(Long id) {
        return new Hotel(id, "hotel\s" + id, "hotel", "city", "address", 0.0, 0.0,
                0, new ArrayList<>(), new ArrayList<>());
    }

    protected HotelResponse createHotelResponse(Long id) {
        return new HotelResponse(id, "hotel\s" + id, "hotel", "city", "address", 0.0, 0.0, 0);
    }

    protected UserResponse createUserResponse(Long id,RoleType roleType){
        return new UserResponse( id,
                "user\s" + id,
                passwordEncoder.encode("user"),
                "email\s" + id,
                Role.from(roleType).getAuthority().toString());
    }

}
