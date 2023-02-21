package com.cn.edu.interfaces.function1.facade.impl;

import com.cn.edu.interfaces.function1.facade.UserServiceFacade;
import com.cn.edu.interfaces.function1.facade.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: restful
 * @description:
 * @author: jiangjianfei
 * @create: 2023-02-21 18:32
 **/
@Service
public class UserServiceFacadeImpl implements UserServiceFacade {
    @Override
    public List<UserDTO> listUser() {
        // 本次Mock
        return doListUser();
    }

    private List<UserDTO> doListUser() {
        List<UserDTO> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserDTO userDTO = new UserDTO();
            userDTO.setName("张" + (i + 1));
            userDTO.setUsername("zhang" + (i + 1));
            list.add(userDTO);
        }
        return list;
    }
}
