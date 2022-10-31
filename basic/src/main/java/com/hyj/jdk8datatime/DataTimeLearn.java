package com.hyj.jdk8datatime;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * @date 2021.01.13
 */
public class DataTimeLearn {


    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {


        System.out.println(Timestamp.valueOf(LocalDate.now().atTime(0,0,0)).getTime());

        //获取今天的日期
        LocalDate now = LocalDate.now();
        System.out.println(String.format("Today`s local date %s", now.toString()));
        System.out.println("=============equals==============");
        LocalDate customDate = LocalDate.of(2021,01,13);
        System.out.println(now.equals(customDate));

        List<String> ex = new ArrayList<>();
        ex.add("main");

        Method[] declaredMethods = DataTimeLearn.class.getDeclaredMethods();

        for (Method method : declaredMethods) {
            if(ex.contains(method.getName())){
                continue;
            }
            System.out.println(String.format("================%s===================", method.getName()));
            method.invoke(DataTimeLearn.class.newInstance());
        }


    }


    /**
     * 获取年、月、日信息
     */
    public void getDetailDate(){
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        System.out.println(String.format("year : %d  month : %d day : %d", year,month,day));
    }

    /**
     * 处理特定日期
     */
    public  void handleSpecilDate(){
        LocalDate customDate = LocalDate.of(2021,01,26);
        System.out.println(String.format("Today`s local date %s", customDate.toString()));
    }


    /**
     * Java 中另一个日期时间的处理就是检查类似生日、纪念日、法定假日（国庆以及春节）、或者每个月固定时间发送邮件给客户 这些周期性事件。
     *
     * Java中如何检查这些节日或其它周期性事件呢？答案就是MonthDay类。这个类组合了月份和日，去掉了年，这意味着你可以用它判断每年都会发
     * 生事件。和这个类相似的还有一个YearMonth类。这些类也都是不可变并且线程安全的值类型。下面我们通过MonthDay来检查周期性事件：
     */
    public  void cycleDate(){
        MonthDay birth = MonthDay.of(1,13);

        MonthDay now = MonthDay.now();

        if(now.equals(birth)){
            System.out.println("今天是你的生日");
        }
    }


    public void getCurrentTime(){
        System.out.println(LocalTime.now());
    }


    /**
     * 计算两小时以后的时间
     */
    public void plusHours(){
        LocalTime now = LocalTime.now();
        System.out.println("current time is " + now);
        System.out.println("after 2 hours time is " + now.plusHours(2L));

    }

    /**
     * 计算一周后的日期
     */
    public void plusWeek(){
        LocalDate now = LocalDate.now();
        LocalDate afterDate = now.plus(1L,ChronoUnit.WEEKS);
        System.out.println(String.format("current date:%s after 1 week %s", now.toString(),afterDate.toString()));
    }

    public void minusDate(){
        LocalDate now = LocalDate.now();
        LocalDate previousYear = now.minus(1L,ChronoUnit.YEARS);
        System.out.println("Date before 1 year : " + previousYear);

        LocalDate nextYear = now.plus(1L,ChronoUnit.YEARS);
        System.out.println("Date after 1 year : " + nextYear);
    }

    public void clock(){

        // 根据系统时间返回当前时间并设置为UTC。
        Clock clock = Clock.systemUTC();
        System.out.println("Clock : " + clock);

        // 根据系统时钟区域返回时间
        Clock defaultClock = Clock.systemDefaultZone();
        System.out.println("Clock : " + clock);
    }

    //如何用Java判断日期是早于还是晚于另一个日期
    public void isBeforeOrIsAfter(){
        LocalDate today = LocalDate.now();

        LocalDate tomorrow = today.plusDays(1L);
        if(tomorrow.isAfter(today)){
            System.out.println("Tomorrow comes after today");
        }

        //减去一天
        LocalDate yesterday = today.minus(1, ChronoUnit.DAYS);

        if(yesterday.isBefore(today)){
            System.out.println("Yesterday is day before today");
        }
    }

    //获取特定时区下面的时间
    public void getZoneTime(){
        //设置时区
        ZoneId america = ZoneId.of("America/New_York");

        LocalDateTime localtDateAndTime = LocalDateTime.now();

        ZonedDateTime dateAndTimeInNewYork  = ZonedDateTime.of(localtDateAndTime, america );
        System.out.println("现在的日期和时间在特定的时区 : " + dateAndTimeInNewYork);
    }

    /**
     * 格式化到指定时区的时间
     */
    public void getCustomZoneTime(){
        String zone = "Asia/Shanghai";
        ZoneId zoneId = ZoneId.of(zone);//获取时区Id
        ZoneId america = ZoneId.of("America/New_York");
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(america);//以1970-01-01的形式格式化时间
        String formatStr = formatter.format(ZonedDateTime.now());//格式化当前的时间为字符串
        System.out.println(formatStr);
    }

    //使用 YearMonth类处理特定的日期
    public void checkCardExpiry(){
        YearMonth currentYearMonth = YearMonth.now();
        System.out.printf("Days in month year %s: %d%n", currentYearMonth, currentYearMonth.lengthOfMonth());

        YearMonth creditCardExpiry = YearMonth.of(2028, Month.FEBRUARY);
        System.out.printf("Your credit card expires on %s %n", creditCardExpiry);
    }

    //检查闰年
    public void isLeapYear(){
        LocalDate today = LocalDate.now();
        if(today.isLeapYear()){
            System.out.println("This year is Leap year");
        }else {
            System.out.println(String.format("%s is not a Leap year", today.getYear()));
        }
    }


    //计算两个日期之间的天数和月数
    public void calcDateDays(){
        LocalDate today = LocalDate.now();

        LocalDate java8Release = LocalDate.of(2021, Month.MAY, 14);

        Period periodToNextJavaRelease = Period.between(today, java8Release);

        System.out.println("Months left between today and Java 8 release : "
                + periodToNextJavaRelease.getMonths() );
        //只计算当月差的天数
        System.out.println("days left between today and Java 8 release : "
                + periodToNextJavaRelease.getDays() );
    }

    // 包含时差信息的日期和时间
    public void ZoneOffset(){
        LocalDateTime datetime = LocalDateTime.of(2018, Month.FEBRUARY, 14, 19, 30);
        ZoneOffset offset = ZoneOffset.of("+05:30");
        OffsetDateTime date = OffsetDateTime.of(datetime, offset);
        System.out.println("Date and Time with timezone offset in Java : " + date);
    }

    // 获取时间戳
    public void getTimestamp(){
        Instant timestamp = Instant.now();
        System.out.println("What is value of this instant " + timestamp);
    }

    // 使用预定义的格式化工具去解析或格式化日期
    public void formateDate(){
        String dayAfterTommorrow = "20180210";
        LocalDate formatted = LocalDate.parse(dayAfterTommorrow, DateTimeFormatter.BASIC_ISO_DATE);
        System.out.printf("Date generated from String %s is %s %n", dayAfterTommorrow, formatted);
    }




}
