package top.moon.imiao.common.pojo.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * AuthenticationInfo: 自定义认证框架数据封装
 *
 * @author xingzhanqi
 * @date 2023/03/12 16:55
 */
@Data
@ApiModel(value = "自定义认证框架数据封装")
public class AuthenticationInfo {
    /**
     * 用户id 可以是admin用户也可以是普通user用户id
     */
    @ApiModelProperty(value="用户id")
    private Long id;
    @ApiModelProperty(value="用户名")
    private String username;
    @ApiModelProperty(value="用户类型")
    private String userType;
    @ApiModelProperty(value="用户权限列表")
    private List<String> authorities;
}
