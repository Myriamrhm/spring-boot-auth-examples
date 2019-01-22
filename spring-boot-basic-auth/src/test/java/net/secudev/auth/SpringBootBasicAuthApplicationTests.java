package net.secudev.auth;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
@WebAppConfiguration
public class SpringBootBasicAuthApplicationTests {	
	
	
	@Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

	@Test
	@WithMockUser(username = "alice", password = "password", roles = "vip")
	public void alicePeutSeConnecterEnVIP() throws Exception {
		this.mvc.perform(get("/vip/me")).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "bob", password = "password", roles = "regular")
	public void bobNePeutPasSeconnecterEnVIP() throws Exception {
		this.mvc.perform(get("/vip/me")).andExpect(status().isOk());
	}

	@Test
	public void basicAuth() throws Exception {
		this.mvc.perform(get("/admin/me").header(HttpHeaders.AUTHORIZATION,
				"Basic " + Base64Utils.encodeToString("root:password".getBytes()))).andExpect(status().isOk());
	}
	
	@Test
	public void basicAuthFailed() throws Exception {
		this.mvc.perform(get("/admin/me").header(HttpHeaders.AUTHORIZATION,
				"Basic " + Base64Utils.encodeToString("bob:password".getBytes()))).andExpect(status().isForbidden());
	}

}
