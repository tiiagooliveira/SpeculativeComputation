package cguide.utils;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cguide.execution.entities.Condition;
import cguide.execution.entities.ConditionSet;
import cguide.execution.entities.TriggerCondition;

import com.google.gson.Gson;

/**
 * Created by IntelliJ IDEA.
 * User: andre
 * Date: 12/1/12
 * Time: 6:27 PM
 */
public class Utils {

    public static String generateRandomKey(int length){
   	  SecureRandom random = new SecureRandom();
   	  String key = new BigInteger(130, random).toString(32);

   	    return key.substring(0, (key.length() > length)?length:key.length()-1);
   	}
    public static String parseId(String iri){
        Pattern pattern = Pattern.compile("#(.*?)#");
        Matcher matcher = pattern.matcher(iri);
        if (matcher.find()) {
            return (matcher.group(1));
        }
        return null;
    }
    public static Boolean alternateTaskTest(ArrayList<Condition> conditions, ArrayList<TriggerCondition> task){

        Boolean flag = false;
            for(TriggerCondition tc: task){
                for(ConditionSet cs:tc.getTriggerConditionSet()){
                    flag = testConditionSet(cs,conditions);
                    if(flag) {System.out.println("Selected according to Condition Set "+cs.getId());return true;}
                }
            }

        return flag;
    }
    public static Boolean processDecision(ArrayList<Condition> conditions, ArrayList<ConditionSet> task){

        Boolean flag = false;
            for(ConditionSet cs:task){
                flag = testConditionSet(cs,conditions);
                if(flag) {System.out.println("Processed according to Condition Set "+cs.toJson());return true;}

        }

        return flag;
    }

    private static Boolean testConditionSet(ConditionSet conditionSet, ArrayList<Condition> conditions){
            for(Condition c : conditionSet.getCondition()){
                  if(!containsCondition(c,conditions)) return Boolean.FALSE;
            }
            return Boolean.TRUE;
    }

    public static Boolean containsCondition(Condition c, ArrayList<Condition> cs){
        Gson gson = new Gson();

        for(Condition tgc: cs){
            if(c.getIsNumeric()&&tgc.getIsNumeric()){
                if(c.conditionCompareNumeric(tgc)){System.out.println("FOUND");return Boolean.TRUE;}

            }
            if(!c.getIsNumeric()&&!tgc.getIsNumeric()){
                if(c.conditionCompare(tgc)){System.out.println("FOUND");return Boolean.TRUE;}
            }
        }
        System.out.println("NOT FOUND");
        return Boolean.FALSE;
    }
}
