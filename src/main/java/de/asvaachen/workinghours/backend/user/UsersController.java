package de.asvaachen.workinghours.backend.user;

import de.asvaachen.workinghours.backend.user.converter.UserDtoToUserEntityConverter;
import de.asvaachen.workinghours.backend.user.model.ErrorMessageDto;
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

    public UsersController(UsersService usersService, UserDtoToUserEntityConverter converter) {
        this.usersService = usersService;
        this.converter = converter;
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        return new ResponseEntity<List<UserDto>>(usersService.getAllUsers(), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/create")
    public ResponseEntity createUser(@Valid @RequestBody UserDto userDto) {
        try {
            UserDto createdUser= usersService.createUser(converter.convert(userDto));
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (EmailExistsException e) {
            return ResponseEntity.badRequest().body(new ErrorMessageDto(HttpStatus.BAD_REQUEST.value(), "Bad Request", "email", "Email ist bereits vorhanden."));
        }
    }

    @CrossOrigin
    @PostMapping("/update")
    public ResponseEntity updateUser(@Valid @RequestBody UserDto userDto) {

        UserDto createdUser = usersService.updateUser(converter.convert(userDto));
        if(createdUser == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(createdUser, HttpStatus.CREATED);
        }


    }

}