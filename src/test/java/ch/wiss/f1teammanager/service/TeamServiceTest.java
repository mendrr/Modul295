package ch.wiss.f1teammanager.service;

import ch.wiss.f1teammanager.dto.TeamDTO;
import ch.wiss.f1teammanager.exception.TeamNotFoundException;
import ch.wiss.f1teammanager.model.Team;
import ch.wiss.f1teammanager.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

/**
 * Service-Test mit reinem Mockito. Das Repository wird gemockt,
 * es läuft also keine echte Datenbank mit.
 */
@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamService teamService;

    @Test
    void getTeamByIdLiefertDto() {
        Team team = new Team();
        team.setId(1L);
        team.setName("Red Bull Racing");
        team.setBase("Milton Keynes");
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));

        TeamDTO dto = teamService.getTeamById(1L);

        assertThat(dto.name()).isEqualTo("Red Bull Racing");
    }

    @Test
    void getTeamByIdWirftFehlerWennNichtGefunden() {
        when(teamRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> teamService.getTeamById(999L))
                .isInstanceOf(TeamNotFoundException.class);
    }
}
