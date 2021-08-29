package arn.filipe.fooddelivery.api.v1.controller;

import arn.filipe.fooddelivery.api.v1.assembler.UserInputDisassembler;
import arn.filipe.fooddelivery.api.v1.assembler.UserModelAssembler;
import arn.filipe.fooddelivery.api.v1.model.UserModel;
import arn.filipe.fooddelivery.api.v1.model.input.PasswordInput;
import arn.filipe.fooddelivery.api.v1.model.input.UserInput;
import arn.filipe.fooddelivery.api.v1.model.input.UserWithPasswordInput;
import arn.filipe.fooddelivery.api.v1.openapi.controller.UserControllerOpenApi;
import arn.filipe.fooddelivery.domain.model.User;
import arn.filipe.fooddelivery.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController implements UserControllerOpenApi {

    @Autowired
    private UserService userService;

    @Autowired
    private UserInputDisassembler userInputDisassembler;

    @Autowired
    private UserModelAssembler userModelAssembler;

    @GetMapping
    public CollectionModel<UserModel> listAll(){
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









}
