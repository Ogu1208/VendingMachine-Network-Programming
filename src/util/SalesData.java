package util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class SalesData implements Serializable, Comparable<SalesData> {
    private static final long serialVersionUID = 1L;
    private String clientId;
    private String canName;
    private int quantitySold;
    private int totalSales;
    private String date;

    public SalesData(String clientId, String canName, int quantitySold, int totalSales) {
        this(clientId, canName, quantitySold, totalSales, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

    public SalesData(String clientId, String canName, int quantitySold, int totalSales, String date) {
        this.clientId = clientId;
        this.canName = canName;
        this.quantitySold = quantitySold;
        this.totalSales = totalSales;
        this.date = date;
    }

    @Override
    public String toString() {
        return "SalesData{" +
                "clientId='" + clientId + '\'' +
                "canName='" + canName + '\'' +
                ", quantitySold=" + quantitySold +
                ", totalSales=" + totalSales +
                ", date='" + date + '\'' +
                '}';
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date thisDate = sdf.parse(this.date);
            Date otherDate = sdf.parse(o.date);
            return thisDate.compareTo(otherDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesData salesData = (SalesData) o;
        return quantitySold == salesData.quantitySold && totalSales == salesData.totalSales && Objects.equals(clientId, salesData.clientId) && Objects.equals(canName, salesData.canName) && Objects.equals(date, salesData.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, canName, quantitySold, totalSales, date);
    }
}