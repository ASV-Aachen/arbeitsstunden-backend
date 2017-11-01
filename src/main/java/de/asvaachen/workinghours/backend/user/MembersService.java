package de.asvaachen.workinghours.backend.user;

import de.asvaachen.workinghours.backend.project.ProjectItemHourRepository;
import de.asvaachen.workinghours.backend.season.model.SeasonDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MembersService {

    ProjectItemHourRepository projectItemHourRepository;
    ProjectItemHourEntityToWorkinghourItemDtoConverter converter;

    public MembersService(ProjectItemHourRepository projectItemHourRepository, ProjectItemHourEntityToWorkinghourItemDtoConverter converter) {
        this.projectItemHourRepository = projectItemHourRepository;
        this.converter = converter;
    }

    public List<WorkinghourItemDto> getActiveMemberWorkinghours(Integer year) {
        UUID uuidRalf = UUID.fromString("781f054a-9d9d-4a48-a2e6-b2b76ef58f85");

        return projectItemHourRepository.findAllByMemberIdAndProjectItemSeason(uuidRalf, year).stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    public ActiveMemberWorkinghoursDto getActiveMemberWorkinghours() {
        Integer activeYear = 2017;
        ActiveMemberWorkinghoursDto activeMemberWorkinghoursDto = new ActiveMemberWorkinghoursDto();
        activeMemberWorkinghoursDto.setActiveYear(activeYear);
        activeMemberWorkinghoursDto.setSeasons(createWorkingHoursSeasonDto());

        UUID uuidRalf = UUID.fromString("781f054a-9d9d-4a48-a2e6-b2b76ef58f85");

        activeMemberWorkinghoursDto.setWorkinghours(projectItemHourRepository.findAllByMemberIdAndProjectItemSeason(uuidRalf, activeYear).stream()
                .map(converter::convert)
                .collect(Collectors.toList()));
        return activeMemberWorkinghoursDto;
    }

    private List<SeasonDto> createWorkingHoursSeasonDto() {
        List<SeasonDto> availableSeasons = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            int year = 2007 + i;
            int nextYear = year + 1;
            availableSeasons.add(new SeasonDto(year, String.format("%d/%d", year, nextYear)));
        }
        return availableSeasons;
    }
}