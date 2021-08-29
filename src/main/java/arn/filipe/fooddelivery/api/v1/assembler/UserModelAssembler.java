package arn.filipe.fooddelivery.api.v1.assembler;

import arn.filipe.fooddelivery.api.v1.BuildLinks;
import arn.filipe.fooddelivery.api.v1.controller.UserController;
import arn.filipe.fooddelivery.api.v1.model.UserModel;
import arn.filipe.fooddelivery.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport <User, UserModel>{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuildLinks buildLinks;

    public UserModelAssembler() {
        super(UserController.class, UserModel.class);
    }

    public UserModel toModel(User user){
        UserModel userModel = createModelWithId(user.getId(), user);

        modelMapper.map(user, userModel);

        userModel.add(buildLinks.linkToUser("users"));

        userModel.add(buildLinks.linkToTeamUser(user.getId(), "teams-user"));

        return userModel;
    }

    @Override
    public CollectionModel<UserModel> toCollectionModel(Iterable<? extends User> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(UserController.class).withSelfRel());
    }
}
