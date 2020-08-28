package com.xinggou.common.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xinggou.common.constant.Const;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtOperator {
    /**
     * 秘钥
     */
    @Value("${jwt.staff.secret:aaabbbcccdddeeefffggghhhiiijjjkkklllmmmnnnooopppqqqrrrsssttt}")
    private String staffSecret;
    /**
     * 有效期，单位秒
     * - 默认1周
     */
    @Value("${jwt.staff.token-expire-time-in-second:604800}")
    private int staffTokenExpireTimeInSecond;
    /**
     * 有效期，单位秒
     * - 默认2周
     */
    @Value("${jwt.staff.token-expire-time-in-second:1209600}")
    private int staffRefreshTokenExpireTimeInSecond;

    /**
     * 为指定用户生成token
     *
     * @param staffId 用户信息
     * @return token
     */
    public String generateStaffToken(Long staffId) {
        //生成jwt
        Date now = new Date();
        Algorithm algo = Algorithm.HMAC256(staffSecret);
        return JWT.create()
                .withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + this.staffTokenExpireTimeInSecond * 1000))
                .withClaim(Const.AUTH_STAFF_ID, staffId)//保存身份标识
                .sign(algo);
    }

    /**
     * 验证并获取用户名
     */
    public long getStaffId(String token){
        Algorithm algorithm = Algorithm.HMAC256(staffSecret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        try{
            DecodedJWT verify = verifier.verify(token);
            return verify.getClaim(Const.AUTH_STAFF_ID).asLong();
        }catch (JWTVerificationException ve){
            log.warn("invalid token");
        }
        return 0;

    }

    public int getStaffTokenExpireTimeInSecond() {
        return staffTokenExpireTimeInSecond;
    }

    public int getStaffRefreshTokenExpireTimeInSecond() {
        return staffRefreshTokenExpireTimeInSecond;
    }
}