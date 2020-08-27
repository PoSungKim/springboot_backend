package com.portfolio.springboot_backend.dao;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.portfolio.springboot_backend.model.user.UserVO;

@Mapper
public interface UserDao {
    List<UserVO> usersList();
    void registerUser(UserVO user);
    UserVO logInUser(UserVO user);
    UserVO findUser(UserVO user);
    int findUserIdByEmail(String email);
}
