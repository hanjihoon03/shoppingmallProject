package shoppingmall.project.repository.custom;

import shoppingmall.project.domain.apidto.UserDto;
import shoppingmall.project.domain.apidto.UserLoginIdPwDto;
import shoppingmall.project.domain.subdomain.Tier;

import java.util.List;

public interface UserRepositoryCustom {
    List<UserDto> findUserTierAndAge(Tier tier, int age);
    UserLoginIdPwDto findByNameAndEmail(String name, String email);
}
