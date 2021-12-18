package com.tsuiraku.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author helen
 * @since 2019/10/16
 */
public class JwtUtils {
    public static final long EXPIRE = 1000 * 60 * 60 * 24; // 设置token过期时间
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO"; // 密钥

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/11/2 下午11:48
     *  @Description: 生产token字符串
     */
    public static String getJwtToken(String id, String nickname){

        String JwtToken = Jwts.builder()
                // 设置JWT头信息
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")

                // 分类名
                .setSubject("edu-user")

                // 设置过期时间
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))

                // 设置token主体部分
                .claim("id", id)
                .claim("nickname", nickname)

                // 密钥
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return JwtToken;
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/11/2 下午11:51
     *  @Description: 判断token是否存在与有效
     *  @param jwtToken
     *  @return
     */
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/11/2 下午11:52
     *  @Description: 判断token是否存在与有效
     *  @param request
     *  @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)) return false;
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/11/2 下午11:52
     *  @Description: 根据token字符串获取会员id
     *  @param request
     *  @return
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("id");
    }
}
