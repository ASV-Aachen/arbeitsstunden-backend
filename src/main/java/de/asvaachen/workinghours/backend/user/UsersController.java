package de.asvaachen.workinghours.backend.user;

import de.asvaachen.workinghours.backend.user.converter.UserDtoToUserEntityConverter;
import de.asvaachen.workinghours.backend.user.model.CreateUserDto;
import de.asvaachen.workinghours.backend.user.model.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    UsersService usersService;

    UserDtoToUserEntityConverter converter;
    UserCreateDtoToMemberEntityConverter userCreateDtoconverter;

    public UsersController(UsersService usersService, UserDtoToUserEntityConverter converter, UserCreateDtoToMemberEntityConverter userCreateDtoconverter) {
        this.usersService = usersService;
        this.converter = converter;
        this.userCreateDtoconverter = userCreateDtoconverter;
    }

    /*@CrossOrigin
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        return new ResponseEntity<List<UserDto>>(usersService.getAllUsers(), HttpStatus.OK);
    }*/

    @CrossOrigin
    @PostMapping
    public ResponseEntity updateUser(@Valid @RequestBody CreateUserDto createUserDto) {
        usersService.updateUser(userCreateDtoconverter.convert(createUserDto));
        return new ResponseEntity(HttpStatus.CREATED);
    }
}