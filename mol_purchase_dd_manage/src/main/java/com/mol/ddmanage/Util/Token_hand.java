package com.mol.ddmanage.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class Token_hand
{
    public static String CreateMyToken(String user)//创建token
    {
        try
        {
            Algorithm algorithm= Algorithm.HMAC256("demo");

            String Token= JWT.create().withIssuer("crm")
                    .withSubject(user)
                    .withExpiresAt(getTimeout())
                    .sign(algorithm);
            return Token;
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return "errer";
        }
    }
    public  static String Review_My_Token(String token)//验证用户的token
    {
        try {
            Algorithm algorithm=Algorithm.HMAC256("demo");
            JWTVerifier verifier=JWT.require(algorithm)
                    .withIssuer("crm")
                    .build();
            DecodedJWT jwt=verifier.verify(token);
            return jwt.getSubject();//验证token
        }
        catch
        (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return "errer";
        }
        catch (JWTDecodeException ex)
        {
            return "errer";
        }
        catch (JWTVerificationException ex)
        {
            return "errer";
        }

    }
    public static Date getTimeout()//token有效时间
    {
        long timeStamp=System.currentTimeMillis()+3600*1000;//持续一小时
        Date timeout=new Date(Long.parseLong(String.valueOf(timeStamp)));
        return timeout;
    }
}
