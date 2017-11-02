package de.asvaachen.workinghours.backend.project;

import de.asvaachen.workinghours.backend.project.model.ProjectItemDto;
import de.asvaachen.workinghours.backend.project.model.ProjectItemHourDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProjectItemDtoToProjectItemEntityConverter implements Converter<ProjectItemDto, ProjectItemEntity> {

    ProjectRepository projectRepository;
    MemberRepository memberRepository;

    public ProjectItemDtoToProjectItemEntityConverter(ProjectRepository projectRepository, MemberRepository memberRepository) {
        this.projectRepository = projectRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public ProjectItemEntity convert(ProjectItemDto source) {

        UUID id = UUID.fromString(source.getId());

        ProjectItemEntity entity = new ProjectItemEntity();
        entity.setId(id);
        entity.setSeason(source.getSeason());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        entity.setDate(LocalDate.parse(source.getDate(), formatter));
        entity.setTitle(source.getTitle());
        entity.setDescription(source.getDescription());
        entity.setProject(projectRepository.findOne(UUID.fromString(source.getProjectId())));

        entity.setHours(source.getHours().stream()
                .map(projectItemHourDto -> convertProjectItemHour(projectItemHourDto, entity))
                .collect(Collectors.toList()));
        return entity;
    }

    private ProjectItemHourEntity convertProjectItemHour(ProjectItemHourDto projectItemHourDto, ProjectItemEntity projectItemEntity) {
        ProjectItemHourEntity hourEntity = new ProjectItemHourEntity();
        hourEntity.setId(UUID.fromString(projectItemHourDto.getId()));
        hourEntity.setDuration(projectItemHourDto.getDuration());
        hourEntity.setMember(memberRepository.findOne(UUID.fromString(projectItemHourDto.getMemberId())));
        hourEntity.setProjectItem(projectItemEntity);
        return hourEntity;
    }
}