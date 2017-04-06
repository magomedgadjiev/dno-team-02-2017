package application.test.controllers;

import application.controller.UserControllerWithDB;
import application.user.UserProfile;
import com.google.gson.Gson;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Created by isopov on 29.09.16.
 */

@SpringBootTest(webEnvironment = RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
public class UserControllerTest {

    private UserProfile userProfile;

    @Autowired
    private MockMvc mockMvc;

    MockHttpSession session = new MockHttpSession();

    @Before
    public void init() {
        userProfile = new UserProfile("login", "password", "email");
    }

    @Test
    public void testMeRequiresLogin() throws Exception {
        final Gson gson = new Gson();
        mockMvc.perform(post("/api/DB/auth/regirstration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(userProfile))).andExpect(status().isCreated());

        session.setAttribute(UserControllerWithDB.EMAIL, userProfile.getEmail());
        session.setAttribute(UserControllerWithDB.LOGIN, true);

        mockMvc.perform(get("/api/DB/user/getInfoUser")
                .session(session)
        ).andExpect(status().isOk());

        mockMvc.perform(get("/api/DB/auth/signOut")
        ).andExpect(status().isOk());


        final UserProfile userProfile1 = new UserProfile("aa", "dd", "email");
        final UserProfile userProfile2 = new UserProfile();
        userProfile2.setLogin("a");
        userProfile2.setPassword("a");
        mockMvc.perform(post("/api/DB/auth/regirstration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(userProfile2))
        ).andExpect(status().isBadRequest());

        mockMvc.perform(post("/api/DB/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(userProfile))
        ).andExpect(status().isOk());

        mockMvc.perform(post("/api/DB/user/setInfoUser")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(userProfile1))
        ).andExpect(status().isOk());
    }
}