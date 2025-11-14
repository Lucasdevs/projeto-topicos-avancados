package com.unifacisa.rede.controller;

import com.unifacisa.rede.dto.UserDTO;
import com.unifacisa.rede.entity.UserEntity;

import com.unifacisa.rede.mapper.UserMapper;
import com.unifacisa.rede.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return toDtoList(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User não encontrado"));
        return toDto(user);
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        UserEntity user = toEntity(userDTO);
        UserEntity u = userRepository.save(user);
        return toDto(u);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {

        UserEntity user = userMapper.toEntity(userDTO);

        UserEntity userUpdate = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User não encontrado"));

        user.setId(userUpdate.getId());

        return toDto(userRepository.save(user));
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "User deletado com sucesso!";
    }

    @GetMapping("/search")
    public List<UserDTO> search(@RequestParam String q) {
        List<UserEntity> users = userRepository
                .findByNameContainingIgnoreCaseOrUsernameContainingIgnoreCase(q, q);
        return UserMapper.INSTANCE.toDTOList(users);
    }

    @PostMapping("/{id}/friends")
    public void addFriend(@PathVariable Long id, Authentication auth) {
        UserEntity me = userRepository.findByUsername(auth.getName()).orElseThrow();
        UserEntity friend = userRepository.findById(id).orElseThrow();

        me.getFriends().add(friend);
        friend.getFriends().add(me);
        userRepository.save(me);
        userRepository.save(friend);
    }

    @GetMapping("/me/friends")
    @Cacheable("friends")
    public List<UserDTO> myFriends(Authentication auth) {
        UserEntity me = userRepository.findByUsername(auth.getName()).orElseThrow();
        return UserMapper.INSTANCE.toDTOList(new ArrayList<>(me.getFriends()));
    }



    private UserDTO toDto(UserEntity userEntity) {
        return userMapper.toDTO(userEntity);
    }

    private UserEntity toEntity(UserDTO userDTO) {
        return userMapper.toEntity(userDTO);
    }

    private List<UserDTO> toDtoList(List<UserEntity> users) {
        List<UserDTO> userDTOList = new ArrayList<>();
        for (UserEntity userEntity : users) {
            userDTOList.add(toDto(userEntity));
        }
        return userDTOList;
    }
}
