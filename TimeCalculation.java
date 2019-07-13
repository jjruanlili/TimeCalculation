package com;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 计算非重复工作区间
 * 先进行所有工作区间的时间排序;
 * 在排序的基础上遍历:
 * 1.只要后一个开始时间在前一个的区间内部，则把两者时间合并;
 * 2.当遇到后一个开始时间超出前一个结束时间时,产生区间断层，保留已纪录的区间，重置新的区间为比较标准按1继续合并
 */
public class TimeCalculation {
    /**
     * 获取时间毫秒数
     *
     * @param time
     * @return
     */
    public static long getTime(String time) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(getDateFromString(time));
            return cal.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("wrong time");
            return 0;
        }
    }

    /**
     * 时间转换 str to date
     *
     * @param time
     * @return
     * @throws ParseException
     */
    public static Date getDateFromString(String time) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
    }

    public static String getStringFromLong(long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
    }

    /**
     * 检查空字符串
     *
     * @param str
     * @return
     */
    public static boolean checkNull(String str) {
        if (null == str || "".equals(str)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        HomeWork[] homeWorkArray = new HomeWork[5];
        homeWorkArray[0] = new HomeWork("2018-11-15 00:00:00", "2018-12-01 03:11:41");
        homeWorkArray[1] = new HomeWork("2018-10-10 21:20:00", "2018-10-31 00:00:00");
        homeWorkArray[2] = new HomeWork("2018-10-18 06:08:40", "2018-11-10 00:00:00");
        homeWorkArray[3] = new HomeWork("2018-11-18 00:00:00", "2019-01-01 01:11:06");
        homeWorkArray[4] = new HomeWork("2018-10-15 04:02:34", "2018-10-30 21:32:40");
        Arrays.sort(homeWorkArray);
        //先排序
        List<HomeWork> arrarHomeWorkList = Arrays.asList(homeWorkArray);
        List<HomeWork> resHomeWorkList = new ArrayList<HomeWork>();
        long minTime = 0;
        long maxTime = 0;
        //后遍历判断
        for(HomeWork hw : arrarHomeWorkList){
            //当后一个的开始时间超出上一个的结束时间时 产生时间断层
            // 则此前的时间段为完整一段区间
            String begin = hw.getBegin();
            if (checkNull(begin)) {
                System.out.println("wrong"); //时间为空 停止运行 或者跳过当前循环continue;
                return;
            }
            long beginTime = getTime(begin);
            if (minTime == 0){
                minTime = beginTime;
            }
            String end = hw.getEnd();
            if (checkNull(end)) {
                System.out.println("wrong");
                return;
            }
            long endTime = getTime(end);
            if (maxTime == 0){
                maxTime = endTime;
            }
            if(beginTime>=minTime && beginTime<=maxTime && endTime>maxTime){
                maxTime=endTime;
            }
            if(beginTime>maxTime){
                //产生断层 存储已有区间 重置min和max
                HomeWork tmpHomeWork = new HomeWork(getStringFromLong(minTime),getStringFromLong(maxTime));
                resHomeWorkList.add(tmpHomeWork);
                minTime = beginTime;
                maxTime = endTime;
            }
        }
        //循环结束产生的最后一个区间
        HomeWork tmpHomeWork = new HomeWork(getStringFromLong(minTime),getStringFromLong(maxTime));
        resHomeWorkList.add(tmpHomeWork);

       for(HomeWork hw : resHomeWorkList){
           System.out.println(hw.getBegin() + " to " + hw.getEnd());
       }

    }

    static class HomeWork implements Comparable<HomeWork>{
        private String begin = null;
        private String end = null;

        public HomeWork() {
        }

        public HomeWork(String begin, String end) {
            this.begin = begin;
            this.end = end;
        }

        public String getBegin() {
            return begin;
        }

        public void setBegin(String begin) {
            this.begin = begin;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        @Override
        /** 比较工作区间起始时间大小用于sort排序
         * 0相等 1大于 -1小于
         */
        public int compareTo(HomeWork o) {
            long res = getTime(this.begin)-getTime(o.getBegin());
            if(res==0L){
                return 0;
            }else if(res>0L){
                return 1;
            }else{
                return -1;
            }
        }
    }
}
