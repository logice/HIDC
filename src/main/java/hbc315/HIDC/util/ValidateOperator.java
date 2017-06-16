package hbc315.HIDC.util;
  
/** 
 * 获取手机运营商 
 * @version 1.0
 */  
public class ValidateOperator {  
    /** 
     * 判断传入的参数号码为哪家运营商 
     * @param mobile 
     * @return 运营商名称 
     */  
    public static String validateMobile(String mobile){  
        String returnString="";  
        if(mobile==null || mobile.trim().length()!=11){  
            return "-1";        //mobile参数为空或者手机号码长度不为11，错误！  
        }  
        String threeNum = mobile.trim().substring(0,3);
        if(threeNum.equals("134") ||  threeNum.equals("135") ||   
                threeNum.equals("136") || threeNum.equals("137")    
                || threeNum.equals("138")  || threeNum.equals("139")
                || threeNum.equals("147")
                ||  threeNum.equals("150") ||threeNum.equals("151")
                || threeNum.equals("152")  || threeNum.equals("157")  
                || threeNum.equals("158")  || threeNum.equals("159") 
                || threeNum.equals("178")
                || threeNum.equals("182")  || threeNum.equals("183")
                || threeNum.equals("184")
                || threeNum.equals("187")  || threeNum.equals("188")  ){  
            returnString="yd";   //中国移动  
        }  
        if(threeNum.equals("130") ||  threeNum.equals("131") ||   
                threeNum.equals("132") || threeNum.equals("156")    
                || threeNum.equals("176")
                || threeNum.equals("185")  || threeNum.equals("186")
                || threeNum.equals("155")){  
            returnString="lt";   //中国联通  
        }  
        if(threeNum.equals("133") ||  threeNum.equals("153") 
        		|| threeNum.equals("177")|| threeNum.equals("180") 
        		|| threeNum.equals("181")|| threeNum.equals("189")
        		|| threeNum.equals("170")){
            returnString="dx";   //中国电信  
        }  
        if(returnString.trim().equals("")){  
            returnString="weizhi";   //未知运营商  
        }  
        return returnString;  
    }  
} 