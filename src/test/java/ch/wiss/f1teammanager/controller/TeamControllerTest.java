package ch.wiss.f1teammanager.controller;

import ch.wiss.f1teammanager.dto.TeamDTO;
import ch.wiss.f1teammanager.service.TeamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Controller-Test mit @WebMvcTest. Nur die Web-Schicht wird geladen,
 * der Service wird mit @MockitoBean gemockt.
 */
@WebMvcTest(TeamController.class)
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TeamService teamService;

    @Test
    void getTeamsLiefertJson() throws Exception {
        TeamDTO team = new TeamDTO(1L, "Red Bull Racing", "Milton Keynes", List.of());
        when(teamService.getAllTeams()).thenReturn(List.of(team));

        mockMvc.perform(get("/api/teams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Red Bull Racing"));
    }
}
