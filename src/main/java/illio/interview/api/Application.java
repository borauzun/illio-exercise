package illio.interview.api;

import java.time.LocalDate;

public class Application {

    public static void main(String[] args) {

        LocalDate dateStart= LocalDate.of(2019,12,01);
        LocalDate dateEnd= LocalDate.of(2019,12,31);
        String ticker="AAPL.US";

        HistoricalDataClient historicalDataClient=new HistoricalDataClient(ticker);
        int avg=historicalDataClient.getAverageRange(dateStart,dateEnd,ticker);
        String output=String.format("Average of %s between %s and %s : %d pence", ticker,dateStart.toString(),dateEnd.toString(), avg);
        System.out.println("******************************************************************");
        System.out.println(output);
        System.out.println("******************************************************************");
    }
    

}
