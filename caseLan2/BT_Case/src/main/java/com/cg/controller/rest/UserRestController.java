package com.cg.controller.rest;

import com.cg.exception.DataInputException;
import com.cg.model.LocationRegion;
import com.cg.model.User;
import com.cg.model.dto.LocationRegionDTO;
import com.cg.model.dto.RoleDTO;
import com.cg.model.dto.UserDTO;
//import com.cg.model.dto.UserUpdateDTO;
import com.cg.service.user.UserService;
import com.cg.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserRestController {
    @Autowired
    private AppUtil appUtil;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getList() {

        List<UserDTO> userDTOS = new ArrayList<>();

        List<User> users = userService.findUserByDeletedIsFalse();

        for (User user : users) {
            userDTOS.add(user.toUserDTO1(user.getLocationRegion().toLocationRegionDTO()));
        }

        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<?> findById(@PathVariable Long userId) {
        User user = userService.findById(userId).get();
        UserDTO userDTO = user.toUserDTO1(user.getLocationRegion().toLocationRegionDTO());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }



//    @PostMapping("/update")
//    public ResponseEntity<?> update(@PathVariable Long id,
//                                            @Validated @RequestBody UserDTO userDTO, BindingResult bindingResult){
//
//        if (bindingResult.hasFieldErrors()){
//            List<ObjectError> list = bindingResult.getAllErrors();
//            List<String> errorlists = new ArrayList<>();
//            for (ObjectError objectError : list){
//                errorlists.add(objectError.getDefaultMessage());
//            }
//            return new ResponseEntity<>(errorlists, HttpStatus.BAD_REQUEST);
//        }
//        User user = userService.findById(id).get();
//        LocationRegion locationRegion =userDTO.getLocationRegion().toLocationRegion();
//        Long locationID = user.getLocationRegion().getId();
//        locationRegion.setId(locationID);
//        User newUser =userDTO.toUser().setLocationRegion(locationRegion);
//        user = newUser;
//        user.setId(id);
//        user.setLocationRegion(locationRegion);
//        newUser=userService.save(user);
//        return new ResponseEntity<>(newUser.toUserDTO().getLocationRegion(),HttpStatus.OK);
//    }
//
//    @PostMapping("/update")
//    public ResponseEntity<?> update(@PathVariable @RequestBody Long id, UserDTO userDTO) {
////        Optional<User> userOptional = userService.findById(id);
////        if (!userOptional.isPresent()) {
////            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
////        }
////        User user = userDTO.toUser();
////        user.setPassword(userOptional.get().getPassword());
////        User newUser = userService.save(user);
//        RoleDTO roleDTO = new RoleDTO();
//        userDTO.setRoleDTO(roleDTO);
////        userDTO.getLocationRegion().setId(0L);
//        userDTO.getLocationRegion().setAddress(userDTO.getLocationRegion().getAddress());
//        userDTO.setId(userDTO.getId());
//        userDTO.setUrlImage("user.png");
//        User user = userDTO.toUser();
//        userService.save(user);
//
//        return new ResponseEntity<>( HttpStatus.ACCEPTED);
//    }

//    @PatchMapping("/deleted/{id}")
//    public ResponseEntity<?>deletedCustomer(@PathVariable Long id){
//        User user = userService.findById(id).get();
//        user.setDeleted(true);
//        User newUser =userService.save(user);
//        UserDTO userDTO = newUser.toUserDTO1(newUser.getLocationRegion().toLocationRegionDTO());
//        return new ResponseEntity<>(userDTO,HttpStatus.OK);
//    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> delete(@PathVariable Long userId) {
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) {
            throw new DataInputException("Not User");
        }
        try {
            User user = userOptional.get();
            user.setDeleted(true);
            User newUser = userService.save(user);
            UserDTO userDTO =newUser.toUserDTO1(newUser.getLocationRegion().toLocationRegionDTO());
            return new ResponseEntity<>(userDTO,HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataInputException("Contact Admin");
        }
    }

}
