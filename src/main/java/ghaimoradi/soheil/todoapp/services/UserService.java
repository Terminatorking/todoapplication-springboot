package ghaimoradi.soheil.todoapp.services;

import ghaimoradi.soheil.todoapp.models.User;
import ghaimoradi.soheil.todoapp.repositories.UserRepository;
import ghaimoradi.soheil.todoapp.utils.encryption.AES;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String username) {
        return userRepository.findByUsername(username);
    }

    public void register(String username, String password) {
        User user = new User();
        user.setPassword(AES.encryptAES(password));
        user.setUsername(username);
        userRepository.save(user);
    }
}
