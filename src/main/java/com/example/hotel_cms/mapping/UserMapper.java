package com.example.hotel_cms.mapping;

import com.example.hotel_cms.model.Room;
import com.example.hotel_cms.model.User;
import com.example.hotel_cms.web.request.RoomRequest;
import com.example.hotel_cms.web.request.UserRequest;
import com.example.hotel_cms.web.response.RoomResponse;
import com.example.hotel_cms.web.response.RoomResponseList;
import com.example.hotel_cms.web.response.UserResponse;
import com.example.hotel_cms.web.response.UserResponseList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(source = "role.authority", target = "role")
    UserResponse userToResponse(User user);

    User requestToUser(UserRequest request);

    default UserResponseList userListToUserResponseList(List<User> users) {
        List<UserResponse> userResponses =
                users.stream().map(this::userToResponse)
                        .collect(Collectors.toList());

        UserResponseList response = new UserResponseList(userResponses);
        return response;
    }

}
