package com.mrlv.common.utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateTimeUtil {

	final public static String DateTimeFormatString = "yyyy-MM-dd HH:mm:ss";
	final public static SimpleDateFormat DateTimeFormat = new SimpleDateFormat(DateTimeFormatString);

	final public static String DateTimeFormatString2 = "yyyy年MM月dd日hh时mm分ss秒";
	final public static SimpleDateFormat DateTimeFormat2 = new SimpleDateFormat(DateTimeFormatString2);

	final public static String DateFormatString = "yyyy-MM-dd";
	final public static SimpleDateFormat DateFormat = new SimpleDateFormat(DateFormatString);
	
	final public static String DateFormatStringYM = "yyyy-MM";
	final public static SimpleDateFormat DateFormatYM = new SimpleDateFormat(DateFormatStringYM);

	final public static String DateFormatString2 = "yyyy/MM/dd";
	final public static SimpleDateFormat DateFormat2 = new SimpleDateFormat(DateFormatString2);

	final public static String DateFormatString3 = "yyyy年MM月dd日";
	final public static SimpleDateFormat DateFormat3 = new SimpleDateFormat(DateFormatString3);

	final public static String  DateFormatSFMString = "HH:mm:ss";
	final public static SimpleDateFormat DateFormatSFM = new SimpleDateFormat(DateFormatSFMString);
	
	final public static String  DateFormatSFString = "HH:mm";
	final public static SimpleDateFormat DateFormatSF = new SimpleDateFormat(DateFormatSFString);
	
	final public static String DateFotmatString4 ="yyyyMMddHHmmssSSS";
	final public static SimpleDateFormat DateFormat4 = new SimpleDateFormat(DateFotmatString4);
	
	final public static String DateFormatString5 = "yyyyMMdd";
	final public static SimpleDateFormat DateFormat5 = new SimpleDateFormat(DateFormatString5);

	final public static String DateFormatString6 = "yyyyMM";
	final public static SimpleDateFormat DateFormat6 = new SimpleDateFormat(DateFormatString6);
	
	public static Calendar calendar = Calendar.getInstance();



	/**
	 * 
	 * @Description: 时间转字符串
	 * @param  date  
	 * @return String 格式：yyyyMMdd
	 * @throws
	 */
	public static String DateFotmatString5(Date date){
		if(date==null){
			return null;
			
		}
		String reportDate = DateFormat5.format(date);
		return reportDate;
	}
	/**
	 * 
	 * @Description: 字符串转Date类型
	 * @param date 字符串类型  格式:yyyy-MM-dd HH:mm:ss
	 * @return Date
	 * @throws
	 */
	public static Date convertToDateTime(String date) {
		Date res = null;
		try {
			if(date == null || date.length() == 0) {
				
			} else {
				res = DateTimeFormat.parse(date);
			}
		} catch (ParseException e) {
			return null;
		}
		return res;
	}
	
	/**
	 * 
	 * @Description: 将从mysql数据库取出来的日期dateTime类型数据转换成字符串类型
	 * @param date 字符串类型  格式:yyyy-MM-dd HH:mm:ss
	 * @return String
	 * @throws
	 */
	public static String mysqlDateTimeToString(Object date) {
		
		return DateTimeFormat.format(new Date((long) date));
	}

	
	/**
	 * @Description: 字符串转Date类型 
	 * @param date 字符串类型  格式:yyyy年MM月dd日hh时mm分ss秒 
	 * @return Date
	 * @throws
	 */
	public static Date convertToDateTime2(String date) {
		Date res = null;
		try {
			res = (date == null || date.length() == 0) ? null : DateTimeFormat2.parse(date);
		} catch (ParseException e) {
			return null;
		}
		return res;
	}

	/**
	 * @Description 字符串转Date类型
	 * @param date 字符串类型  格式：yyyy-MM-ddT'HH:mm:ss.SSS Z
	 * @return  格式:yyyy-MM-dd HH:mm:ss
	 */
	public static String convertToDateTime3(String date) {
    	String res = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//注意格式化的表达式
			res = DateFormat.format(format.parse(date.replace("Z", " UTC")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return res;
    }
	
	/**
	 * 
	 * @Description: 字符串转Date类型 
	 * @param date 字符串类型  格式:yyyy-MM-dd 
	 * @return Date
	 * @throws
	 */
	public static Date convertToDate(String date) {
		Date res = null;
		try {
			res = (date == null || date.length() == 0) ? null : DateFormat.parse(date);
		} catch (Exception e) {
			return null;
		}
		return res;
	}

	
	/**
	 * 
	 * @Description: 字符串转Date类型 
	 * @param date 字符串类型  格式：yyyy/MM/dd
	 * @return Date 
	 * @throws 
	 */
	public static Date convertToDate2(String date) {
		Date res = null;
		try {
			res = (date == null || date.length() == 0) ? null : DateFormat2.parse(date);
		} catch (Exception e) {
			return null;
		}
		return res;
	}
	
	
	/**
	 * 获取当前时间
	 */
	public static Date getNowTime() {
		Date nowTime = new Date();
		return nowTime;
	}


    
	
	/**
	 * 
	 * @Description: 字符串转Date类型   
	 * @param date 字符串类型  格式yyyy年MM月dd日
	 * @return Date  
	 * @throws
	 */
	public static Date convertToDate3(String date) {
		Date res = null;
		try {
			res = (date == null || date.length() == 0) ? null : DateFormat3.parse(date);
		} catch (Exception e) {
			return null;
		}
		return res;
	}

	
	/**
	 * 
	 * @Description: 时间转字符串
	 * @param date
	 * @return String 格式：yyyy-MM-dd HH:mm:ss
	 * @throws
	 */
	public static String convertToString(Date date) {
		String reportDate = DateTimeFormat.format(date);
		return reportDate;
	}
	
	/**
	 * 时间戳需乘以1000的特殊时间戳
	 * @Description: 时间戳转字符串
	 * @param date
	 * @return String 格式：yyyy-MM-dd HH:mm:ss
	 * @throws
	 */
	public static String convertToString5(Long date) {
		String reportDate = DateTimeFormat.format(new Date(date * 1000l));
		return reportDate;
	}


	/**
	 * 
	 * @Description: 时间转字符串
	 * @param  date  
	 * @return String 格式：yyyy年MM月dd日hh时mm分ss秒
	 * @throws
	 */
	public static String convertToString2(Date date) {
		String reportDate = DateTimeFormat2.format(date);
		return reportDate;
	}


	/**
	 * 
	 * @Description: 时间转字符串
	 * @param  date 
	 * @return String 格式：yyyy-MM-dd
	 * @throws
	 */
	public static String convertToDateYMDString(Date date) {
		if (date == null) {
			return null;
		}
		String reportDate = DateFormat.format(date);
		return reportDate;
	}
	
	/**
	 * 
	 * @Description: 时间转字符串
	 * @param  date 
	 * @return String 格式：yyyy-MM
	 * @throws
	 */
	public static String convertToDateYMString(Date date) {
		if (date == null) {
			return null;
		}
		String reportDate = DateFormatYM.format(date);
		return reportDate;
	}



	/**
	 * 
	 * @Description: 时间转字符串
	 * @param date  
	 * @return String  格式：yyyy/MM/dd
	 * @throws
	 */
	public static String convertToDateYMDString2(Date date) {
		if (date == null) {
			return null;
		}
		String reportDate = DateFormat2.format(date);
		return reportDate;
	}


	/**
	 * 
	 * @Description: 时间转字符串
	 * @param  date  
	 * @return String 格式：yyyy年MM月dd日
	 * @throws
	 */
	public static String convertToDateYMDString3(Date date) {
		if (date == null) {
			return null;
		}
		String reportDate = DateFormat3.format(date);
		return reportDate;
	}
	
	public static String converToDateSFMString(Date date) {
		if (date == null) {
			return null;
		}
		String reportDate = DateFormatSFM.format(date);
		return reportDate;
	}
	
	
	/**
	 * 转换为时分
	 */
	public static String converToDateSFString(Date date) {
		if (date == null) {
			return null;
		}
		String reportDate = DateFormatSF.format(date);
		return reportDate;
	}
	

	/**
	 * 
	 * @Description: 时间转字符串
	 * @param  date  
	 * @return String 格式：yyyyMMddHHmmssSSS
	 * @throws
	 */
	public static String DateFotmatString4(Date date){
		if(date==null){
			return null;
			
		}
		String reportDate = DateFormat4.format(date);
		return reportDate;
	}
	
	/**
	 * 
	 * @Description: 加一天
	 * @throws
	 */
	public static Date addOneDay(Date date) {  
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		
		return calendar.getTime();
	}

	/**
	 * 
	 * @Description: 减一天
	 * @throws
	 */
	public static Date subOneDay(Date date) {  
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);
		
		return calendar.getTime();
	}
	
	
	/**
	 * 
	 * @param date 日期
	 * @param num 天数
	 * @return
	 */
	public static Date subDay(Date date,int num) {  
		calendar.setTime(date);
		calendar.add(Calendar.DATE, num);
		
		return calendar.getTime();
	}
	
	
	/**
	 * 
	 * @Description: 增加小时后的时间
	 * @param date
	 * @param hour  
	 * @return Date 获得data + hour小时后的时间
	 * @throws
	 */
	public static Date addHour(Date date, int hour) {
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, hour);
		return calendar.getTime();
	}
	
	/**
	 * 
	 * @Description: 增加分钟后的时间
	 * @param date
	 * @param minute
	 * @return Date 获得data + minute分钟后的时间
	 * @throws
	 */
	public static Date addMinute(Date date, int minute) {
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minute);
		return calendar.getTime();
	}


	/**
	 * 
	 * @Description: 获得年份
	 * @param  date
	 * @return int 返回当前年份           
	 * @throws
	 */
	public static int getYear(Date date) {  
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);   
	}

	
	/**
	 * 
	 * @Description: 获得月份
	 * @param  date
	 * @return int  返回当前月份  
	 * @throws
	 */
	public static int getMonth(Date date) {
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH + 1);
	}

	
	/**
	 * 
	 * @Description: 计算小时差
	 * @return double  
	 * @throws
	 */
	public static double differHour(Date min,Date max) {
		String dateStart = convertToString(min);
		String dateStop = convertToString(max);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			min = format.parse(dateStart);
			max = format.parse(dateStop);
			
			long diff = max.getTime() - min.getTime();
			double diffHours = (double)diff / (60 * 60 * 1000) % 24;
			
			return Double.parseDouble(new java.text.DecimalFormat("#.00").format(diffHours)); 
		} catch (ParseException e) {
			e.printStackTrace();
			return 0.00;
		}
	}
	
	
	
	/**
	 * 
	 * @Description: 计算分钟差
	 * @return double  
	 * @throws
	 */
	public static long differMinute(Date min,Date max) {
		String dateStart = convertToString(min);
		String dateStop = convertToString(max);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			min = format.parse(dateStart);
			max = format.parse(dateStop);
			
			long diff = max.getTime() - min.getTime();
			long diffMinutes = diff / (60 * 1000) % 60;
			
			return diffMinutes; 
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 计算两段时间分钟数
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public static long caluteMinute(String startTime,String endTime) {
		try {
			SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date begin=dfs.parse(startTime);
			Date end = dfs.parse(endTime);
			long between=(end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒
			long min=between/60;
			return min;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
			
	}

	/**
	 * 计算两段时间小时数
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public static long caluteMinuteHour(String startTime,String endTime) {
		try {
			SimpleDateFormat dfs = new SimpleDateFormat("HH");
			Date begin=dfs.parse(startTime);
			Date end = dfs.parse(endTime);
			long between=(end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒
			long min=between/60;
			return min;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
			
	}
	
	/**
	 * 计算两段时间分钟数
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public static long caluteMinuteMinute(String startTime,String endTime) {
		try {
			SimpleDateFormat dfs = new SimpleDateFormat("mm");
			Date begin=dfs.parse(startTime);
			Date end = dfs.parse(endTime);
			long between=(end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒
			long min=between/60;
			return min;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
			
	}
	/**
	 * 
	 * @Description: 根据日期算出今天是星期几
	 * @param date 日期 
	 * @return int  
	 * @throws
	 */
    public static int getWeek(Date date){  
    	/* 7-星期天, 1-星期一, 2-星期二, 3-星期三, 4-星期四, 5-星期五, 6-星期六  */
        String[] weeks = {"7","1","2","3","4","5","6"};  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;  
        if(week_index<0){  
            week_index = 0;  
        }   
        return Integer.parseInt(weeks[week_index]);  
    } 
	
    public static boolean valid(String str){
        try{
            Date date = (Date)DateFormat2.parse(str);
            return str.equals(DateFormat2.format(date));
        }catch(Exception e){
           return false;
        }
    }
    
    /**
     * 获取当月的最后一天
     * @param date
     * @return
     */
    public static String getlastDate(Date date) {
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last);
        day_last = endStr.toString();
    	return day_last;
    }
    
    public static int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
       int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年            
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            
            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            return day2-day1;
        }
    }
	
    /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }    
          
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;

        if(day==0){
        	return hour + "小时" + min + "分钟";
        } else {
        	return (day + "天" + hour) + "小时" + min + "分钟";
        }
        
    }
    
    public static String getDatePoorNotice(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;

        if(day==0){
        	
        	return new java.text.DecimalFormat("#.00").format(hour+(min/60.0)) + "小时";
        } else {
        	return day + "天";
        }
        
    }
    
    
    public static void main(String[] args) {
    	int i=2;	//频率
    	
    	String str1 = "2017-08-15 08:00:00"; //评价圈创建时间
    	
    	String strNowDate = DateTimeUtil.convertToString(new Date()); //系统当前时间
    	
    	Date startDate = DateTimeUtil.convertToDateTime(str1);
    	Date endDate = null;
    	Date nowDate = DateTimeUtil.convertToDateTime(strNowDate);
    	
    	int days = DateTimeUtil.differentDays(startDate, nowDate);
    	System.out.println("days="+days);
    	
    	Date tempDate = startDate;
    	
    	startDate =  DateTimeUtil.subDay(startDate,days/i);
    	endDate = DateTimeUtil.subDay(startDate, i);
    	
    	if(nowDate.getTime()<endDate.getTime()) {
    		startDate = DateTimeUtil.subDay(tempDate,days/i+i);
    		endDate = DateTimeUtil.subDay(endDate, i);
    	}
    	
    	
    	System.out.println("startDate="+startDate);
    	System.out.println("endDate="+endDate);
    	
    	
    }
    
    /**
     * 
     * @param date 日期
     * @param num 年数
     * @return
     */
    public static Date subYear(Date date,int num) {  
      calendar.setTime(date);
      calendar.add(Calendar.YEAR, num);
      
      return calendar.getTime();
    }

	public static String DateFotmatString6(Date date) {
		if(date==null){
			return null;
		}
		String reportDate = DateFormat6.format(date);
		return reportDate;
	}
    
    
    
}