package top.moon.imiao.common.utils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.moon.imiao.common.exception.ServiceException;
import top.moon.imiao.common.pojo.domain.AuthenticationInfo;
import top.moon.imiao.common.restful.ResponseCode;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtTokenUtils: JWT工具类
 *
 * @author xingzhanqi
 * @date 2023/03/12 16:28
 */
@Component
@Slf4j
public class JwtTokenUtils {
    //自定义载荷的key值
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    //导入当前JWT计算的重要数据：secret和expiration超时时间
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;


    private String generateToken(Map<String, Object> claims) {
        String token = Jwts.builder()
                .setClaims(claims)//自定义载荷
                .setExpiration(generateExpirationDate()) // 默认载荷属性过期时间
                .signWith(SignatureAlgorithm.HS512, secret) // 签名算法,和签名密码
                .compact();
        log.info("根据载荷，hash算法，和密码生成有效Token：{}", token);
        return token;
    }

    /**
     * 从JWT Token获取载荷,包括自定义和默认所有内容 active=true
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            log.info("当需要解析Token时，获取载荷");
        } catch (Exception e) {
            log.info("JWT格式验证失败：{}", token);
        }
        return claims;
    }

    /**
     * 生成Token的过期时间 active=true
     */
    private Date generateExpirationDate() {
        Date date = new Date(System.currentTimeMillis() + expiration * 1000);
        log.info("生成token时，生成过期时间值：{}", date);
        return date;
    }

    /**
     * 从Token中获取登录用户名
     */
    @Deprecated
    public String getUserNameFromToken(String token) {
        AuthenticationInfo userInfo = getUserInfo(token);
        return userInfo.getUsername();
    }

    /**
     * 根据JWT Token获取userDetails信息 active=true
     */
    public AuthenticationInfo getUserInfo(String token) {
        Claims claims = null;
        String userInfoJson = null;
        try {
            claims = getClaimsFromToken(token);
            if (claims == null) {
                return null;
            }
            userInfoJson = (String) claims.get(CLAIM_KEY_USERNAME);
            log.info("获取到userDetails中各种属性：{}", userInfoJson);
        } catch (NullPointerException e) {
            log.info("Token解析失败，当前Token完全无效，无法解析数据！", e);
            return null;
        }
        // 拿到载荷之后，解析userDetails对象
        try {
            AuthenticationInfo userInfo =
                    JSON.parseObject(userInfoJson, AuthenticationInfo.class);
            log.debug("解析出userDetails对象：{}", userInfo);
            return userInfo;
        } catch (Exception e) {
            log.info("尝试解析token为userDetails失败", e);
            return null;
        }
    }

    /**
     * 从JSON中解析userDetails数据
     *
     * @param userDetailsJson JSON字符串
     * @return 根据JSON解析得到的UserDetails对象
     * @throws JsonProcessingException
     */
    /*@Deprecated
    private UserDetails generateUserDetailsFromJson(String userDetailsJson) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(userDetailsJson);
        String username = jsonNode.get("username").asText();
        String password = jsonNode.get("password").asText();
        JsonNode authorities = jsonNode.get("authorities");
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        for (JsonNode authority : authorities) {
            authorityList.add(new SimpleGrantedAuthority(authority.get("authority").asText()));
        }
        return new User(username, password, authorityList);
    }*/

    /**
     * 验证Token是否还有效
     *
     * @param token       客户端传入的Token
     * @param userDetails 从数据库中查询出来的用户信息
     */
    /*@Deprecated
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);
        boolean result = username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        log.info("验证Token是否有效：{}", result);
        return result;
    }*/

    /**
     * 判断Token是否已经过期
     */
    @Deprecated
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        boolean result = expiredDate.before(new Date());
        log.info("验证token是否超时过期，超时时间：{}，是否过期：{}", expiredDate, result);
        return result;
    }

    /**
     * 从Token中获取过期时间
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 根据用户信息生成Token active=true
     */
    public String generateToken(AuthenticationInfo userInfo) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_CREATED, new Date());
        String userInfoJson = null;
        try {
            userInfoJson = JSON.toJSONString(userInfo);
        } catch (Exception e) {
            log.info("将用户信息转化成json字符串出现错误,错误信息:{}", e.getMessage());
        }
        if (userInfo == null) {
            throw new ServiceException(ResponseCode.INTERNAL_SERVER_ERROR, "无法转化认证用户信息json");
        }
        claims.put(CLAIM_KEY_USERNAME, userInfoJson);
        return generateToken(claims);
    }

    /**
     * 判断Token是否可以被刷新
     */
    @Deprecated
    public boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 刷新Token
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        /*claims.put(CLAIM_KEY_CREATED, new Date());*/
        return generateToken(claims);
    }
}
