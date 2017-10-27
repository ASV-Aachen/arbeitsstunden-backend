package de.asvaachen.workinghours.backend.project;

import de.asvaachen.workinghours.backend.project.model.ProjectDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    ProjectRepository projectRepository;
    ProjectEntityToProjectDtoConverter converter;

    public ProjectService(ProjectRepository projectRepository, ProjectEntityToProjectDtoConverter converter) {
        this.projectRepository = projectRepository;
        this.converter = converter;
    }

    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    public List<ProjectDto> getActiveProjects() {
        return projectRepository.findAllByLastSeasonNull().stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    /*public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    public UserDto updateUser(UserEntity userEntity) {
        if (userEntity.getId() == null) {
            return null;
        }

        Optional<UserEntity> foundUserEntity = userRepository.findById(userEntity.getId());
        if (foundUserEntity.isPresent()) {
            return converter.convert(userRepository.save(userEntity));
        }
        return null;
    }*/

    public ProjectDto createProject(ProjectEntity projectEntity) {
        ProjectEntity savedProjectEntity = projectRepository.save(projectEntity);
        return converter.convert(savedProjectEntity);
    }
}
