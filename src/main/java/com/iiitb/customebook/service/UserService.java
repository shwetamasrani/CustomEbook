package com.iiitb.customebook.service;

import com.iiitb.customebook.bean.User;
import com.iiitb.customebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.iiitb.customebook.exception.ResourceNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        List<User> userList = new ArrayList<>();
        userRepository.findAll().forEach(userList::add);
        return userList;    //convert iterable class to List collection and return
    }

    public User createUser(User user)
    {
        System.out.println(user.toString());
        return userRepository.save(user);
    }

    public User getUserById(Integer id){

        User user= userRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Employee not exists with id:"+id));

        return user;  //entity is returned along with the status

    }

    public User getUserByEmail(String email){

        User user= userRepository.findByEmail(email);

        return user;  //entity is returned along with the status
    }

    public ResponseEntity<User> updateUser(Integer id, User userDetails)
    {
        User user= userRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Employee not exists with id:"+id));

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setContactNumber(userDetails.getContactNumber());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setPublisherFlag(userDetails.isPublisherFlag());
        user.setCompanyName(userDetails.getCompanyName());

        User updatedUser=userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

}