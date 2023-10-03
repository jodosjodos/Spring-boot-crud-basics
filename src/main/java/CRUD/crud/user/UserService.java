package CRUD.crud.user;

import CRUD.crud.config.SecurityConfig;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addNewUser(User user) throws UserAlreadyExistsException {
        Optional<User> checkUser = userRepository.findByEmail(user.getEmail());


        if (checkUser.isPresent()) throw new UserAlreadyExistsException("  user with this email already exits");
         String hashedPassword = SecurityConfig.hashPassword(user.getPassword(),SecurityConfig.generateSalt());
         user.setPassword(hashedPassword);

        userRepository.save(user);

    }

//    delete user
    public  void  deleteUser(Long id) throws UserAlreadyExistsException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) throw new UserAlreadyExistsException(" user with that  id doesn't exits");
         userRepository.deleteById(id);

    }

//    get single user
    public Optional<User> getUser(Long id ){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) throw new UserAlreadyExistsException(" no user with  that id");
    return  user;
    }

//     update user
    public  User updateUser(Long id , User user){
        Optional<User> userr = userRepository.findById(id);
        if ( userr.isEmpty()) throw   new UserAlreadyExistsException("  no user with that id");

        userr.get().setFirstName(user.getFirstName());
        userr.get().setLastName(user.getLastName());
        userr.get().setEmail(user.getEmail());
        String hashedPassword = SecurityConfig.hashPassword(user.getPassword(),SecurityConfig.generateSalt());

        userr.get().setPassword( hashedPassword);

        return userRepository.save(userr.get());
    }
}
