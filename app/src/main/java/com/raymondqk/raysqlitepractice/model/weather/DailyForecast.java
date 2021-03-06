package com.raymondqk.raysqlitepractice.model.weather;

import java.io.Serializable;

/**
 * Created by 陈其康 raymondchan on 2016/8/22 0022.
 */
public class DailyForecast implements Serializable{
    private String sr;//日升
    private String ss;//日落
    private String txt_d;//白天气候 多云
    private String txt_n;//晚上气候
    private String date;//日期
    private String hum;//湿度
    private String pcpn;
    private String pop;
    private String pres;//气压
    private String max;//最高温度
    private String min;//最低温度
    private String vis;//能见度
    private String deg;//风速级别
    private String dir;//风向
    private String sc;//风描述
    private String spd;//风速

    @Override
    public String toString() {
        return "DailyForecast\n" + "{" +
                "sr='" + sr + '\'' +
                ", ss='" + ss + '\'' +
                ", txt_d='" + txt_d + '\'' +
                ", txt_n='" + txt_n + '\'' +
                ", date='" + date + '\'' +
                ", hum='" + hum + '\'' +
                ", pcpn='" + pcpn + '\'' +
                ", pop='" + pop + '\'' +
                ", pres='" + pres + '\'' +
                ", max='" + max + '\'' +
                ", min='" + min + '\'' +
                ", vis='" + vis + '\'' +
                ", deg='" + deg + '\'' +
                ", dir='" + dir + '\'' +
                ", sc='" + sc + '\'' +
                ", spd='" + spd + '\'' +
                '}' + "\n";
    }

    public String getSr() {
        return sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getSs() {
        return ss;
    }

    public void setSs(String ss) {
        this.ss = ss;
    }

    public String getTxt_d() {
        return txt_d;
    }

    public void setTxt_d(String txt_d) {
        this.txt_d = txt_d;
    }

    public String getTxt_n() {
        return txt_n;
    }

    public void setTxt_n(String txt_n) {
        this.txt_n = txt_n;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getPcpn() {
        return pcpn;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public String getDeg() {
        return deg;
    }

    public void setDeg(String deg) {
        this.deg = deg;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public String getSpd() {
        return spd;
    }

    public void setSpd(String spd) {
        this.spd = spd;
    }
}
