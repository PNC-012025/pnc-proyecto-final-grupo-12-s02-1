package org.carshare.carsharesv_webservice.util;

import org.carshare.carsharesv_webservice.domain.entity.User;

import java.util.List;

public record CurrentUserInfo(
        List<String> roles,
        User currentUser,
        User requestedUser
) {
}

