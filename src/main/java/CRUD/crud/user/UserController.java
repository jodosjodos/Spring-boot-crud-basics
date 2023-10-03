package CRUD.crud.user;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }




    @PostMapping("/addUSer")
    ResponseEntity<String> addNewUser(@RequestBody User requestBody) {
        if (requestBody == null) {
            return ResponseEntity.badRequest().body(" please provide credentials");
        }
        try {
            userService.addNewUser(requestBody);
            return ResponseEntity.ok(" you have created user successfully");
        } catch (UserAlreadyExistsException e) {
            throw new UserAlreadyExistsException(" user already exits");
        }

    }

    @GetMapping(path = "/getUser/{userId}")
    ResponseEntity<Optional<User>> getUser(@PathVariable("userId") Long userId) {
        if (userId == null) throw new UserAlreadyExistsException(" please provide id");
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @DeleteMapping(path = "/deleteUser/{userId}")
    ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId) {
        if (userId == null) {
            return ResponseEntity.badRequest().body(" please provide id");
        }

        userService.deleteUser(userId);
        return ResponseEntity.ok(" you have been delete successfully");

    }


//     update user
    @PutMapping(path = "/updateUser/{userId}")
    ResponseEntity<?> updateUser(@PathVariable("userId") Long id , @RequestBody User requestBody){
        try {
            if (id == null) throw new UserAlreadyExistsException(" please provide id");
            User user = userService.updateUser(id,requestBody);
            System.out.println(user);
            return  ResponseEntity.ok(user);
        }
        catch (Exception e){
            return  ResponseEntity.internalServerError().body("sth went wrong");
        }

    }
}
