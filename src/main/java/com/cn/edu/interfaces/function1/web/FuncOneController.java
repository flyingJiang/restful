package com.cn.edu.interfaces.function1.web;

import cn.hutool.json.JSONUtil;
import com.cn.edu.interfaces.function1.facade.UserServiceFacade;
import com.cn.edu.interfaces.function1.facade.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: restful
 * @description: Java变量的初始化顺序为：静态变量或静态语句块–>实例变量或初始化语句块–>构造函数–>@Autowired 注入Bean。
 * @author: jiangjianfei
 * @create: 2023-02-21 16:57
 **/
@Slf4j
@Controller
@RequestMapping(value = {"/v1/func1"})
public class FuncOneController {
    private static List<UserDTO> userDTOList;

    private UserServiceFacade userServiceFacade;

    /**
     * 构造函数注入参数的方式
     *
     * @param userServiceFacade
     */
    @Autowired
    public FuncOneController(UserServiceFacade userServiceFacade) {
        this.userServiceFacade = userServiceFacade;
        userDTOList = this.userServiceFacade.listUser();
        log.info("userDTOList = {}", JSONUtil.toJsonStr(userDTOList));
    }

    /**
     * 【GET】 /users # 查询用户信息列表
     *
     * @return
     */
    @GetMapping(value = "/users")
    @ResponseBody
    public Object users() {
        return userDTOList;
    }

    /**
     * 【GET】 /users/1001 # 查看某个用户信息
     *
     * @param username
     * @return
     */
    @GetMapping(value = "/users/{username}")
    @ResponseBody
    public Object getUserByUsername(@PathVariable String username) {
        return userDTOList.stream().filter(userDTO -> userDTO.getUsername().equals(username)).collect(Collectors.toList());
    }

    /**
     * 【POST】 /users # 新建用户信息
     *
     * @param userDTO
     * @return
     */
    @PostMapping(value = "/users")
    @ResponseBody
    public Object createUser(@RequestBody UserDTO userDTO) {
        userDTOList.add(userDTO);
        return userDTOList;
    }

    /**
     * 【PUT】 /users/1001 # 更新用户信息(全部字段)
     *
     * @param userDTO
     * @param username
     * @return
     */
    @PutMapping(value = "/users/{username}")
    @ResponseBody
    public Object updateUser(@RequestBody UserDTO userDTO, @PathVariable String username) {
        userDTOList.forEach(user -> {
            if (StringUtils.equals(username, user.getUsername())) {
                user.setUsername(userDTO.getUsername());
                user.setName(userDTO.getName());
            }
        });
        return userDTOList;
    }


    /**
     * 【DELETE】 /users/1001 # 删除用户信息
     *
     * @param username
     * @return
     */
    @DeleteMapping(value = "/users/{username}")
    @ResponseBody
    public Object deleteUser(@PathVariable String username) {
        Iterator<UserDTO> iterator = userDTOList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getUsername().equals(username)) {
                iterator.remove();
            }
        }
        return userDTOList;
    }
}
