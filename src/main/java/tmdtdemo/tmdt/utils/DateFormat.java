package tmdtdemo.tmdt.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateFormat {
    public static String dateFormatWithLocate(String expiration){
        java.text.DateFormat inputDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        // dịnh dạng của chuỗi ngày giờ đầu ra
        java.text.DateFormat outputDateFormat = new SimpleDateFormat("HH:mm MM/dd/yyyy");
        //  them mui giờ việt nam
        outputDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        String outputDateString = null;
        try {
            // chuyển đổi chuỗi ngày giờ vào đối tượng Date
            Date date = inputDateFormat.parse(expiration);
            // chuyển đổi đối tượng Date thành chuỗi ngày giờ mới
            outputDateString = outputDateFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDateString;
    }
    public static int compareDateTimeStrings(String str1, String str2) {
        // Định dạng cho chuỗi thời gian
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm MM/dd/yyyy");

        // Kiểm tra định dạng
        if (!isValidDateFormat(str1) || !isValidDateFormat(str2)) {
            return 0; // Trả về 0 nếu chuỗi không đúng định dạng
        }

        try {
            // Chuyển đổi chuỗi thời gian thành đối tượng Date
            Date dateTime1 = dateFormat.parse(str1);
            Date dateTime2 = dateFormat.parse(str2);

            // So sánh thời gian
            return dateTime1.compareTo(dateTime2);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0; // Trả về 0 nếu có lỗi xảy ra trong quá trình chuyển đổi
        }
    }

    private static boolean isValidDateFormat(String str) {
        try {
            // Chuyển đổi chuỗi thời gian thành đối tượng Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            dateFormat.parse(str);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }


    public static String convertDateToString(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        // chuyen doi sang string
        String dateString = simpleDateFormat.format(date);
        return dateString;
    }

    public static Date convertStringToDate(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        Date res = new Date();
        try{
            res = dateFormat.parse(date);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return res;
    }
}
