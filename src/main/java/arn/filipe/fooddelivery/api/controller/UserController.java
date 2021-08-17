package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.api.assembler.UserInputDisassembler;
import arn.filipe.fooddelivery.api.assembler.UserModelAssembler;
import arn.filipe.fooddelivery.api.model.UserModel;
import arn.filipe.fooddelivery.api.model.input.PasswordInput;
import arn.filipe.fooddelivery.api.model.input.UserInput;
import arn.filipe.fooddelivery.api.model.input.UserWithPasswordInput;
import arn.filipe.fooddelivery.domain.model.User;
import arn.filipe.fooddelivery.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserInputDisassembler userInputDisassembler;

    @Autowired
    private UserModelAssembler userModelAssembler;

    @GetMapping
    public List<UserModel> listAll(){
        return userModelAssembler.toCollectionModel(userService.listAll());
    }

    @GetMapping("/{id}")
    public UserModel findById(@PathVariable Long id){
        return userModelAssembler.toModel(userService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel save(@RequestBody @Valid UserWithPasswordInput userInput){
        User user = userInputDisassembler.toDomainObject(userInput);

        return userModelAssembler.toModel(userService.save(user));
    }

    @PutMapping("/{id}")
    public UserModel update(@PathVariable Long id, @RequestBody @Valid UserInput userInput){
        User user = userService.verifyIfExistsOrThrow(id);

        userInputDisassembler.copyToDomainObject(userInput, user);

        return userModelAssembler.toModel(userService.save(user));
    }

    @PutMapping("/{id}/change-password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@PathVariable Long id, @RequestBody @Valid PasswordInput passwordInput){
        userService.changePassword(id, passwordInput.getActualPassword(), passwordInput.getNewPassword());
    }

//    public void delete(Long id){
//        userService.delete(id);
//    }









}
