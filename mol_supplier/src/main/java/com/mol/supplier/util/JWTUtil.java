package com.mol.supplier.util;

import com.alibaba.fastjson.JSON;
import com.mol.supplier.entity.dingding.login.DDUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

public class JWTUtil {

    private static Logger logger = LoggerFactory.getLogger(JWTUtil.class);

    private static String JWT_SECRET = "fengyuan201906190852";

    public static String createJwt(Map claims, String subject, long ttlMillis){
        //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        //将long型的时间毫秒转为日期时间
        Date now = new Date(nowMillis);

        //生成签名的时候使用的秘钥secret
        SecretKey key = generalKey();

        //下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder() //这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(claims) //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setIssuedAt(now) //iat: jwt的签发时间
                .setSubject(subject)//一个json格式的字符串作为用户的唯一标志。
                .signWith(signatureAlgorithm, key);//设置签名使用的签名算法和签名使用的秘钥

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp); //设置过期时间戳
        }
        return builder.compact();

    }

    public static Claims parseJwt(String jwt){
        //得到原来的签名秘钥，用其才能解析JWT
        SecretKey key = generalKey();
        //得到 DefaultJwtParser
        Claims claims = Jwts.parser()
                .setSigningKey(key) //设置签名的秘钥
                .parseClaimsJws(jwt).getBody();//设置需要解析的jwt
        logger.info(claims.toString());
        return claims;
    }

    public static DDUser getUserByRequest(HttpServletRequest request){
        String eticket = request.getHeader("eticket");
        Claims claims = parseJwt(eticket);
        return JSON.parseObject(claims.get("user",String.class),DDUser.class);
    }


    private static  SecretKey generalKey(){
        //stringKey
        String stringKey = JWT_SECRET;
        // 使用base64解码
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        // 根据给定的字节数组使用AES加密算法构造一个密钥
        SecretKey secretKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return secretKey;
    }

}
