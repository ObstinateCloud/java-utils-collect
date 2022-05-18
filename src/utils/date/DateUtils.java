package utils.date;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @title: DateUtils
 * @description: TODO
 * @auther: zhangjianyun
 * @date: 2022/4/19 16:41
 */
public class DateUtils {


    /**
     * 年
     */
    public static final String YEAR = "year";

    /**
     * 月
     */
    public static final String MONTH = "month";

    /**
     * 周
     */
    public static final String WEEK = "week";

    /**
     * 日
     */
    public static final String DAY = "day";

    /**
     * 显示年月日时分秒，例如 2015-08-11 09:51:53.
     */
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 仅显示年月日，例如 2015-08-11.
     */
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 仅显示时分秒，例如 09:51:53.
     */
    public static final String TIME_PATTERN = "HH:mm:ss";

    /**
     * 仅显示时分，例如 09:51
     */
    public static final String TIME_PATTERN2 = "HH:mm";


    /**
     * 获取当前时间最近的一刻钟时间点 如00 15 30 45
     *
     * @return
     */
    public static Date getNear15MinutesDate() {
        //获取当前时间
        Calendar calendar = Calendar.getInstance();
        // 得到分钟
        int minute = calendar.get(Calendar.MINUTE);
        if (minute >= 0 && minute < 15) {
            minute = 0;
        } else if (minute >= 15 && minute < 30) {
            minute = 15;
        } else if (minute >= 30 && minute < 45) {
            minute = 30;
        } else if (minute >= 45 && minute < 60) {
            minute = 45;
        }
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }


    /**
     * 切割日期。按照周期切割成小段日期段。例如： <br>
     *
     * @param startDate 开始日期（yyyy-MM-dd HH:mm:ss）
     * @param endDate   结束日期（yyyy-MM-dd HH:mm:ss）
     * @return 切割之后的日期集合
     * <li>startDate="2019-02-28",endDate="2019-03-05",period="day"</li>
     * <li>结果为：[2019-02-28, 2019-03-01, 2019-03-02, 2019-03-03, 2019-03-04, 2019-03-05]</li><br>
     */
    public static List<Date> listAllDate(Date startDate, Date endDate) {
        List<Date> result = new ArrayList<>();
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(startDate);

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(endDate);
        while (calendarStart.compareTo(calendarEnd) != 1) {
            result.add(calendarStart.getTime());
            calendarStart.add(Calendar.DATE,1);
        }
        return result;
    }

    public static String formatTime(Date time) {
       return new SimpleDateFormat(TIME_PATTERN2).format(time);
    }

    /**
     * 切割日期。按照周期切割成小段日期段。例如： <br>
     *
     * @param startDate 开始日期（yyyy-MM-dd HH:mm:ss）
     * @param endDate   结束日期（yyyy-MM-dd HH:mm:ss）
     * @return 切割之后的日期集合
     * <li>startDate="2019-02-28",endDate="2019-03-05",period="day"</li>
     * <li>结果为：[2019-02-28, 2019-03-01, 2019-03-02, 2019-03-03, 2019-03-04, 2019-03-05]</li><br>
     */
    public static List<String> listAllDateStr(Date startDate, Date endDate) {
        List<String> result = new ArrayList<>();
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(startDate);

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(endDate);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        while (calendarStart.compareTo(calendarEnd) != 1) {
            result.add(simpleDateFormat.format(calendarStart.getTime()));
            calendarStart.add(Calendar.DATE,1);
        }
        return result;
    }

    /**
     * 获取当前时间最近的一刻钟时间点 如00 15 30 45
     *
     * @return
     */
    public static List<String> getAll15MinutesTimeList() {
        List<String> result = new ArrayList<>();
        //获取当前时间
        Calendar calendar = Calendar.getInstance();
        // 得到分钟
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_PATTERN2);
        for (int i = 0; i < 96; i++) {
            result.add(simpleDateFormat.format(calendar.getTime()));
            calendar.add(Calendar.MINUTE,15);
        }
        return result;
    }


    public static void main(String[] args) {
        getAll15MinutesTimeList().forEach(p->{
            System.out.println(p);
        });

    }
}
