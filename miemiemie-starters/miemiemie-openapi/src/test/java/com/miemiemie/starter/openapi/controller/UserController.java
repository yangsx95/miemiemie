package com.miemiemie.starter.openapi.controller;

import com.miemiemie.starter.openapi.request.User;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * UserController
 * <p>
 *
 * @author Feathers
 * @since 2018-05-31 15:52
 */

/*
    Api 标记这个类是Swagger资源
    value说明 可以使用tags替代
    tags 标签，用于分组 冒号之前的内容
    description 描述，冒号之后的内容
    produces 接口响应的数据类型 默认读取@RequestMapping的produces值
    consumes 接口请求的数据类型 默认读取@RequestMapping的consumes值

    ApiOperation用于controller方法上 给这个接口创建Api API名称为 获取用户列表 备注为空
 */
@RestController
@RequestMapping(value = "/v1", produces = "application/json") //设置默认的返回类型为 json
@Api(tags = {"用户操作"})
public class UserController {

    private static final Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());

    static {
        users.put(1L, new User(1L, "张三", 33));
        users.put(2L, new User(2L, "李四", 34));
        users.put(3L, new User(3L, "王五", 35));
        users.put(4L, new User(4L, "赵六", 36));
    }

    /**
     * 查询所有用户
     * GET
     * 无参数
     * 返回json
     */
    @ApiOperation(value = "获取用户列表", notes = "将系统用户表中的所有用户使用json格式输出出来")
    @GetMapping(value = "/users", produces = "application/json") // 设置返回类型，如果不设置，ui界面Response Content Type会显示 */*
    public List<User> getUserList() {
        return new ArrayList<>(users.values());
    }

    /**
     * 根据名称查询用户
     * GET
     * get请求的 query 参数
     * 返回json
     */
    @ApiOperation(value = "根据名称获取用户列表", notes = "使用模糊匹配查找所有符合姓名条件的用户")
    @ApiImplicitParam(name = "name", dataType = "String", required = true, paramType = "query")
    @GetMapping(value = "/usersByName", produces = "application/json")
    public List<User> getUserListByName(@RequestParam(name = "name") String name) {
        ArrayList<User> users = new ArrayList<>(UserController.users.values());
        ArrayList<User> result = new ArrayList<>();
        for (User user : users) {
            if (user.getName().contains(name)) {
                result.add(user);
            }
        }
        return result;
    }

    /**
     * 根据id获取用户的详细信息
     * GET
     * path参数
     * 返回json
     */
    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @GetMapping(value = "/user/{id}", produces = "application/json")
    public User getUser(@PathVariable Long id) {
        return users.get(id);
    }

    /**
     * 创建用户
     * POST请求
     * body参数，json类型
     * 返回普通文本
     */
    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User", paramType = "body")
    @PostMapping(value = "/user", produces = "text/plain")
    public String postUser(@RequestBody User user) {
        users.put(user.getId(), user);
        return "success";
    }


    /**
     * 更新用户
     * PUT请求
     * 既有path参数，又有body参数
     * 返回普通文本
     */
    @ApiOperation(value = "更新用户详细信息", notes = "根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User", paramType = "body")
    })
    @PutMapping(value = "/user/{id}", produces = "text/plain")
    public String putUser(@PathVariable Long id, @RequestBody User user) {
        User u = users.get(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        users.put(id, u);
        return "success";
    }

    /**
     * 使用form表单，更改用户
     * PUT请求
     * form参数
     * 返回普通文本
     */
    @ApiOperation(value = "使用form表单更新用户详细信息", notes = "提供给页面使用")
    @PutMapping(value = "/user/modifyUser", produces = "text/plain")
    public String modifyUserByForm(User user) {
        return "success";
    }

    /**
     * 删除用户
     * DELETE 请求
     */
    @ApiOperation(value = "删除用户", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @DeleteMapping(value = "/user/{id}", produces = "text/plain")
    public String deleteUser(@PathVariable Long id) {
        users.remove(id);
        return "success";
    }


}
