package com.cesco.api.cesnetapi.funtions;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String 처리 관련 공통 기능
 * @since 2021-08-11
 * @author 조선호
 */
public class StringFunctions {
    
    /**
     * 리스트 String 을 텍스트로 변환
     * @since 2021-08-11
     * @author 조선호
     * @param list 리스트
     * @return 텍스트
     */
    public static String listToString(List<String> list) {

        // 에러처리
        if (list == null || list.size() == 0) {
            return "";
        }

        StringBuilder strs = new StringBuilder();
        
        // 리스트 to Message
        for(String str : list){
            
            if (strs.toString().length() > 0) {
                strs.append(", ");
            }
            strs.append(str);
        }

        return strs.toString();
    }

    /**
     * 이메일 형식 조회
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex); 
        Matcher m = p.matcher(email); 
        if(m.matches()) { err = true; } 
        return err; 
    }
}
