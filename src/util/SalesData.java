package util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalesData implements Serializable, Comparable<SalesData> {
    private static final long serialVersionUID = 1L;
    private String canName;
    private int quantitySold;
    private int totalSales;
    private String date;

    public SalesData(String canName, int quantitySold, int totalSales) {
        this(canName, quantitySold, totalSales, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

    public SalesData(String canName, int quantitySold, int totalSales, String date) {
        this.canName = canName;
        this.quantitySold = quantitySold;
        this.totalSales = totalSales;
        this.date = date;
    }

    // Getters and setters

    @Override
    public String toString() {
        return "SalesData{" +
                "canName='" + canName + '\'' +
                ", quantitySold=" + quantitySold +
                ", totalSales=" + totalSales +
                ", date='" + date + '\'' +
                '}';
    }

    public String getCanName() {
        return canName;
    }

    public void setCanName(String canName) {
        this.canName = canName;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int compareTo(SalesData o) {
        return o.date.compareTo(this.date);
    }
}