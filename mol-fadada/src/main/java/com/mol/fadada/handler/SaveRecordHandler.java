package com.mol.fadada.handler;

import com.mol.fadada.config.MapperFactory;
import com.mol.fadada.dao.AuthRecordMapper;
import com.mol.fadada.dao.RegistRecordMapper;
import com.mol.fadada.dao.SignResultRecordMapper;
import com.mol.fadada.pojo.AuthRecord;
import com.mol.fadada.pojo.RegistRecord;
import com.mol.fadada.pojo.SignResultRecord;
import lombok.Synchronized;
public class SaveRecordHandler {

    private static RegistRecordMapper registRecordMapper = MapperFactory.createMapper(RegistRecordMapper.class);
    private static AuthRecordMapper authRecordMapper = MapperFactory.createMapper(AuthRecordMapper.class);
    private static SignResultRecordMapper signResultRecordMapper = MapperFactory.createMapper(SignResultRecordMapper.class);

    @Synchronized
    public static RegistRecordMapper getRegistRecordMapper(){
        if(registRecordMapper == null){
            registRecordMapper = MapperFactory.createMapper(RegistRecordMapper.class);
        }
        return registRecordMapper;
    }

    @Synchronized
    public static AuthRecordMapper getAuthRecordMapper(){
        if(authRecordMapper == null){
            authRecordMapper = MapperFactory.createMapper(AuthRecordMapper.class);
        }
        return authRecordMapper;
    }

    @Synchronized
    public static SignResultRecordMapper getSignResultRecordMapper(){
        if(signResultRecordMapper == null){
            signResultRecordMapper = MapperFactory.createMapper(SignResultRecordMapper.class);
        }
        return signResultRecordMapper;
    }

    public static int saveRegistRecord(RegistRecord registRecord){
        return getRegistRecordMapper().insert(registRecord);
    }

    public static int saveAuthRecord(AuthRecord authRecord){
        return getAuthRecordMapper().insert(authRecord);
    }

    public static int saveSignResultRecord(SignResultRecord signResultRecord){
        return getSignResultRecordMapper().insert(signResultRecord);
    }
}
