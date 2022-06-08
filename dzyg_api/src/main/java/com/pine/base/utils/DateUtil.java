package com.pine.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author 日期工具
 */
public class DateUtil {


    /**
     * 将时间转成"yyyyMMdd"格式的字符串
     *
     * @param date 时间
     */
    public static String formatToYYYYMMDDStr(Date date) {
        if (date != null) {
            return DateFormat.getInstance("yyyyMMdd").format(date);
        } else {
            return "null";
        }
    }

    /**
     * 将时间转成"yyyy-MM-dd"格式的字符串
     *
     * @param date时间
     * @return
     */
    public static String formatToYYYYMMDD(Date date) {
        if (date != null) {
            return DateFormat.getInstance("yyyy-MM-dd").format(date);
        } else {
            return "null";
        }
    }

    public static Date formatToDayByYYYYMMDDA(String str) throws ParseException {
        DateFormat format = DateFormat.getInstance("yyyyMMdd");
        return format.parse(str);
    }

    public static Date formatToDayByYYYYMMDDMMHH(String str) throws ParseException {
        DateFormat format = DateFormat.getInstance("yyyy-MM-dd HH:mm");
        return format.parse(str);
    }

    public static Date formatToDayByYYYYMMDDMMHHSS(String str)  {
        try {
			DateFormat format = DateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
			return format.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }
    
    public static Date formatToDayByYYYYMMDDMMHHSST(String str)  {
        try {
			DateFormat format = DateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			return format.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }
    

    public static Date formatToDayByYYYYMMDDMMhhSS(Date date) {
        try {
        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
			return format.parse(format.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }
    
    public static Date formatToDayByYYYYMMDD(String str) {
        try {
			DateFormat format = DateFormat.getInstance("yyyy-MM-dd");
			return format.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }
    
 

    public static String formatToYYYYMMDDMMHHSS(Date date) {
        DateFormat format = DateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }


    public static String formatToyyyyMMddHHmmss(Date date) {
        DateFormat format = DateFormat.getInstance("yyyyMMddHHmmss");
        return format.format(date);
    }

    public static Date formatToyyyyMMddHHmmss(String str) throws ParseException {
        DateFormat format = DateFormat.getInstance("yyyyMMddHHmmss");
        return format.parse(str);
    }

    /**
     * 获取两个时间之间的IntervalToNow值，规则:
     * 1. 不满3600秒，返回 "xx分钟前"
     * 2. 不满24*3600秒， 返回"xx小时前"
     * 3. 其余显示"xx天前"
     *
     * @param oldDate 老时间
     * @param newDate 新时间
     * @return IntervalToNow值
     */
    public static String getIntervalToNow(Date oldDate, Date newDate) throws ParseException {
        Long seconds = DateUtil.secondsBetween(oldDate, newDate);
        if (seconds < 60) {
            return String.valueOf(seconds).concat("秒前");
        } else if (seconds < 60 * 60) {
            return String.valueOf(seconds / 60).concat("分钟前");
        } else if (seconds < 60 * 60 * 24) {
            return String.valueOf(seconds / (60 * 60)).concat("小时前");
        } else {
            return String.valueOf(seconds / (60 * 60 * 24)).concat("天前");
        }
    }

    /**
     * 将给定时间间隔（单位:秒），转化为hh:mm:ss的格式. 时间间隔不为负, 否则返回0:00:00
     * @param interval 时间间隔（单位:秒）
     * @return hh:mm:ss格式的字符串
     */
    public static String getFormattedInterval(int interval) {
        if (interval <= 0) {
            return "0:00:00";
        }
        int hours = interval / (60 * 60);
        int minutes = interval / (60) - hours * 60;
        int seconds = interval - hours * 60 * 60 - minutes * 60;
        return String.format("%d:%2d:%2d", hours, minutes, seconds);
    }

    /**
     * 两日期相差秒
     *
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public static Long secondsBetween(Date sdate, Date edate) {
        try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdate = sdf.parse(sdf.format(sdate));
			edate = sdf.parse(sdf.format(edate));
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(edate);
			long time2 = cal.getTimeInMillis();
			return (time2 - time1) / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return 0L;
    }
    
    /**
     * 两日期相差分钟
     *
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public static Integer minsBetween(Date smdate, Date bdate) {
        try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
			Calendar cal = Calendar.getInstance();
			cal.setTime(smdate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(bdate);
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 60);
			return Integer.parseInt(String.valueOf(between_days));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        return 0;
    }

    /**
     * 两日期相差天数
     *
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 两日期相差月数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getMonth(Date startDate, Date endDate) {
        long monthday;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            startDate = sdf.parse(sdf.format(startDate));
            endDate = sdf.parse(sdf.format(endDate));

            Calendar starCal = Calendar.getInstance();
            starCal.setTime(startDate);

            int sYear = starCal.get(Calendar.YEAR);
            int sMonth = starCal.get(Calendar.MONTH);
            int sDay = starCal.get(Calendar.DATE);

            Calendar endCal = Calendar.getInstance();
            endCal.setTime(endDate);
            int eYear = endCal.get(Calendar.YEAR);
            int eMonth = endCal.get(Calendar.MONTH);
            int eDay = endCal.get(Calendar.DATE);

            monthday = ((eYear - sYear) * 12 + (eMonth - sMonth));

            if (sDay < eDay) {
                monthday = monthday + 1;
            }
            return Integer.parseInt(String.valueOf(monthday));
        } catch (ParseException e) {
            monthday = 0;
        }
        return Integer.parseInt(String.valueOf(monthday));
    }

    /***
     *
     * @param startDate 开始日期
     * @param phaseNumber 期数
     * @param  type  2月，5天  12表示分钟 10代表小时
     * @return
     * @throws ParseException
     */
    public static Date getMonthlyRepayDate(Date startDate, Integer phaseNumber, Integer type) {
   /* 	SimpleDateFormat sdf  =new SimpleDateFormat("yyyy-MM-dd");
    	startDate=sdf.parse(sdf.format(startDate));  */
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(type, phaseNumber);
        return calendar.getTime();
    }

    /**
     * 获取当天的起始日期
     *
     * @return
     * @author 董小满
     * @since 2014-05-19
     */
    public static Long getStartTime() {

        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime().getTime();
    }

    /**
     * 获取当天的结束日期
     *
     * @return
     * @author 董小满
     * @since 2014-05-19
     */
    public static Long getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime().getTime();
    }


    /**
     * 获取某月前X月的第一天
     *
     * @param dateStr  初始时间
     * @param monthNum 与dateStr相隔的月数
     * @return
     */
    public static Date getFirstDayOfMonth(String dateStr, int monthNum) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(dateStr);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int month = calendar.get(Calendar.MONTH);
            calendar.set(Calendar.MONTH, month + monthNum);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getMinimum(Calendar.DAY_OF_MONTH));
            Date strDateTo = calendar.getTime();
            return strDateTo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取某月前X月的最后一天
     *
     * @param dateStr  初始时间
     * @param monthNum 与dateStr相隔的月数
     * @return
     */
    public static Date getLastDayOfMonth(String dateStr, int monthNum) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(dateStr);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int month = calendar.get(Calendar.MONTH);
            calendar.set(Calendar.MONTH, month + monthNum);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date strDateTo = calendar.getTime();
            return strDateTo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    //    /**
    //     * 用于天数比较（常用）
    //     *
    //     * @param smdate
    //     * @param bdate
    //     * @return
    //     * @throws ParseException
    //     */
    //    public static int daysBetweenInt(Date smdate, Date bdate) throws ParseException {
    //    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    //        smdate = sdf.parse(sdf.format(smdate));
    //        bdate = sdf.parse(sdf.format(bdate));
    //        Calendar cal = Calendar.getInstance();
    //        cal.setTime(smdate);
    //        long time1 = cal.getTimeInMillis();
    //        cal.setTime(bdate);
    //        long time2 = cal.getTimeInMillis();
    //        long between_days = (time2 - time1) / (1000 * 3600 * 24);
    //        return Integer.parseInt(String.valueOf(between_days));
    //    }

    /**
     *
     *计算smdate time天之后的日期
     * @param smdate
     * @param time
     * @return
     * @throws ParseException
     */
    /**
     * 计算smdate之后的日期
     *
     * @param smdate
     * @param year   年
     * @param month  月
     * @param day    日
     * @return
     * @throws ParseException
     */
    public static Date getSmdateDaysLater(Date smdate, Integer year, Integer month, Integer day) throws ParseException {
        Calendar cal = new GregorianCalendar();
        cal.setTime(smdate);
        int oldYear = cal.get(Calendar.YEAR);
        int oldMonth = cal.get(Calendar.MONTH);
        int oldDay = cal.get(Calendar.DAY_OF_MONTH);
        int newDay = oldDay + day;
        int newMonth = oldMonth + month;
        int newYear = oldYear + year;
        cal.set(Calendar.YEAR, newYear);
        cal.set(Calendar.MONTH, newMonth);
        cal.set(Calendar.DAY_OF_MONTH, newDay);
        Date effectiveDate = new Date(cal.getTimeInMillis());
        return effectiveDate;
    }
    
     // 获得本周一0点时间
    public static Date getTimesWeekmorning() {
    Calendar cal = Calendar.getInstance();
    cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
    cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    return cal.getTime();
    }

    public static Date getEffectiveDate(Date currentDate, int timeout) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, timeout);
        return calendar.getTime();
    }
    
    //获取当年
    public static String getSysYear() {
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        return year;
    }
    
    //获取当年月份
    public static String getSysMonth() {
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.MONTH)+1);
        return year;
    }
    
    /**
     * 获取指定年月的第一天
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int year, int month) {     
        Calendar cal = Calendar.getInstance();   
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份 
        cal.set(Calendar.MONTH, month-1); 
        //获取某月最小天数
        int firstDay = cal.getMinimum(Calendar.DATE);
        //设置日历中月份的最小天数 
        cal.set(Calendar.DAY_OF_MONTH,firstDay);  
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());  
    }
    
    /**
     * 获取指定年月的最后一天
     * @param year
     * @param month
     * @return
     */
     public static String getLastDayOfMonth(int year, int month) {     
         Calendar cal = Calendar.getInstance();     
         //设置年份  
         cal.set(Calendar.YEAR, year);  
         //设置月份  
         cal.set(Calendar.MONTH, month-1); 
         //获取某月最大天数
         int lastDay = cal.getActualMaximum(Calendar.DATE);
         //设置日历中月份的最大天数  
         cal.set(Calendar.DAY_OF_MONTH, lastDay);  
         //格式化日期
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
         return sdf.format(cal.getTime());
     }
     

   public static List<String> getDateList (String startTime,String endTime) {
		 try {
			List<String> list=new ArrayList<>();
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			 Calendar cal = Calendar.getInstance();
			 cal.setTime(sdf.parse(startTime));
			 for (long d = cal.getTimeInMillis(); d <= sdf.parse(endTime).getTime(); d = get_D_Plaus_1(cal)) {
				 list.add(sdf.format(d));
			  }
			  return list;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	 return null;
   }
   
   public static long get_D_Plaus_1(Calendar c) {
	   c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
	   return c.getTimeInMillis();
	   }
   
   /**
    * 时间戳按指定格式转化为日期（String）
    * @param timestamp
    * @return
    */
   public static String convertTimestamp2Date(Long timestamp) {

       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

       return simpleDateFormat.format(new Date(timestamp));
   }
	
  /*  public static void main(String[] args) {
   	 try {
   		java.text.DateFormat format2 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//展示日期不能大于上线日期
		 Date notifyTime_=format2.parse("2016-03-24 13:00:00");
		 System.out.println(format2.format(DateUtil.getMonthlyRepayDate(notifyTime_, 1, 12)));
	} catch (ParseException e) {
		e.printStackTrace();
	}
	}*/
}
