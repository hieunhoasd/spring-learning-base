package HotWhellShop_Spring_react.service;

import java.util.List;
import java.util.Optional;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import HotWhellShop_Spring_react.domain.User;
import HotWhellShop_Spring_react.repository.UserRepository;
import HotWhellShop_Spring_react.util.error.EmailAlreadyExistsException;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleCreateUser(User reqUser) {
        if (userRepository.existsByEmail(reqUser.getEmail())) {
            throw new EmailAlreadyExistsException("email da ton tai");
        }
        return this.userRepository.save(reqUser);
    }

    public User handleGetUserById(long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional != null) {
            return userOptional.get();
        }
        return null;

    }

    public List<User> handleGetAllUser() {
        return this.userRepository.findAll();
    }

    public User handleUpdateUser(User user) {
        User currentUser = this.handleGetUserById(user.getId());
        if (currentUser != null) {
            currentUser.setName(user.getName());
            currentUser.setEmail(user.getEmail());

            currentUser = this.userRepository.save(currentUser);

        }
        return currentUser;
    }

    public void handleDeleteUserByid(long id) {
        this.userRepository.deleteById(id);
    }
}
