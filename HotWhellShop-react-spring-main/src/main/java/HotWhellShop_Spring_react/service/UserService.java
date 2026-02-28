package HotWhellShop_Spring_react.service;

import java.util.List;
import java.util.Optional;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import HotWhellShop_Spring_react.domain.Meta;
import HotWhellShop_Spring_react.domain.ResultPaginationDTO;
import HotWhellShop_Spring_react.domain.User;
import HotWhellShop_Spring_react.domain.DTO.ResGetAllUserDTO;
import HotWhellShop_Spring_react.domain.DTO.ResGetUserByidDTO;
import HotWhellShop_Spring_react.domain.DTO.ResUpdateUserDTO;
import HotWhellShop_Spring_react.domain.DTO.UserDTO;
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

    public ResultPaginationDTO handleGetAllUser(Specification<User> spec, Pageable pageable) {
        Page<User> pageUser = this.userRepository.findAll(spec, pageable);

        Page<ResGetAllUserDTO> pageDto = pageUser.map(user -> {
            ResGetAllUserDTO dto = new ResGetAllUserDTO();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            return dto;
        });

        Meta mt = new Meta();
        mt.setPage(pageDto.getNumber() + 1);
        mt.setPageSize(pageDto.getSize());
        mt.setPages(pageDto.getTotalPages());
        mt.setTotal(pageDto.getTotalElements());

        ResultPaginationDTO res = new ResultPaginationDTO();
        res.setMeta(mt);
        res.setResult(pageDto.getContent());
        return res;
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

    public UserDTO handleConverCreateUserDTO(User user) {

        UserDTO res = new UserDTO();
        res.setEmail(user.getEmail());
        res.setId(user.getId());
        res.setName(user.getName());

        return res;
    }

    public ResGetUserByidDTO handleConverGetUserByIdDTO(User hiu) {
        ResGetUserByidDTO res = new ResGetUserByidDTO();
        res.setEmail(hiu.getEmail());
        res.setId(hiu.getId());
        res.setName(hiu.getName());
        return res;
    }

    public ResUpdateUserDTO handleConverUpdateUserDTO(User hiu) {
        ResUpdateUserDTO res = new ResUpdateUserDTO();
        res.setEmail(hiu.getEmail());
        res.setId(hiu.getId());
        res.setName(hiu.getName());
        return res;
    }

}
