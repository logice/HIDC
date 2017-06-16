package hbc315.HIDC.service;

import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hbc315.HIDC.dao.CallDetailMapper;
import hbc315.HIDC.service.DX.DX_BJ_APIGetMessage;
import hbc315.HIDC.service.LT.LT_BJ_APIGetMessage;
import hbc315.HIDC.util.ValidateOperator;


@Service
public class QueryService {

	@Autowired
	private CallDetailMapper callDetailMapper;
	
	public int task(String mobile,String password){
		Calendar now = Calendar.getInstance(); 
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH);
		//查询6个月的通话详单，存入数据库
		for(int i=0;i<6;i++){
			if(month-i > 0){
				queryByMonth(mobile, password, year, month);				
			}else{
				queryByMonth(mobile, password, year-1, 12+month-i);	
			}

		}
		
		return 1;
	}
	
	//查询一个月详单
	public int queryByMonth(String mobile,String password,int year,int month){
		String carrier = ValidateOperator.validateMobile(mobile);
		if(carrier.equals("dx")){
			return dxTask(mobile,password,year,month);
		}else if(carrier.equals("yd")){
			return ydTask();
		}else if(carrier.equals("lt")){
			return ltTask(mobile,password,year,month);
		}else{
			return 0;
		}
	}
	
	//电信
	private int dxTask(String mobile,String password,int year,int month){
		DX_BJ_APIGetMessage dx = new DX_BJ_APIGetMessage();
		dx.getCallDetail(mobile, password, String.valueOf(year), (month>9)?String.valueOf(month):"0"+String.valueOf(month));
		return 1;
	}
	
	//移动
	private int ydTask(){
		
		return 1;
	}
	
	//联通
	private int ltTask(String mobile,String password,int year,int month){
		LT_BJ_APIGetMessage lt =new LT_BJ_APIGetMessage();
		return 1;
	}
	
}
