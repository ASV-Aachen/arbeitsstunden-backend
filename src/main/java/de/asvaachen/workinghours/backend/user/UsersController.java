package de.asvaachen.workinghours.backend.user;

import de.asvaachen.workinghours.backend.user.model.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @CrossOrigin
    @GetMapping()
    public ResponseEntity<List<UserDto>> getUsers() {
        return new ResponseEntity<List<UserDto>>(usersService.getAllUsers(), HttpStatus.OK);
    }
}