/**
 * NOT USED in Application !
 * This is a reactive client. it is READY but NOT in USE because of two reasons
 *      reason 1: requires system property to set to -Dio.netty.tryReflectionSetAccessible=false
 *      reason 2: in the method due to noise at logging
 * Stays here as an alternative web client in case Reactive feature is needed.
 * I am using HistoricalDataClient, please have a look at it.
 */

package illio.interview.api;

import illio.interview.model.TickData;
import illio.interview.util.Constants;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistoricalDataReactiveClient {

    private String baseUri;
    private String apiToken;
    private String dateFormat;
    private String ticker;

    protected final WebClient client;

    public HistoricalDataReactiveClient() {

        this.baseUri= Constants.BASE_URL_HISTORICAL_DATA;
        this.apiToken=Constants.API_TOKEN;
        this.dateFormat="yyyy-MM-dd";

        this.client = buildWithNoProxy();
    }

    private WebClient buildWithNoProxy(){
        return WebClient.create(this.baseUri);
    }

    private WebClient buildWebClientWithProxy(){
      //todo: If there is a proxy settings used to access the internet, not necessary in most cases but some organisations uses
        return null;
    }
    /**
     * This is the method  being asked to be implemented
     * @param dateBegin : Start date of the data for analysis
     * @param dateEnd   : End date of the data for analysis
     * @param ticker
     * @return
     */
    public void processAverage(LocalDate dateBegin, LocalDate dateEnd, String ticker) {
        final int i;
        this.ticker=ticker;
        Mono<TickData[]> monoList = getTickDataList(dateBegin, dateEnd);
        monoList.doOnSuccess(tickData -> {
            final List<TickData> list=Arrays.asList(tickData);
            printAverageInPence(list);
        });

    }

    private void printAverageInPence(List<TickData> list){
        double average=0.0;
        if (list.size() > 0) {
            average = list.stream().mapToDouble(x -> x.getOpen()).average().getAsDouble()*100;
        }
        String output=String.format("Average of %s  : %d pence", ticker, average);
    }
    public Mono<TickData[]> getTickDataList(LocalDate date1, LocalDate date2){
        String formattedDate1 = date1.format(DateTimeFormatter.ofPattern(this.dateFormat));
        String formattedDate2 = date2.format(DateTimeFormatter.ofPattern(this.dateFormat));

        return  this.client
                .get()
                .uri(ticker+"?api_token="+apiToken+"&period=d.&from="+formattedDate1+"&to="+formattedDate2+"&fmt=json")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(TickData[].class);
    }
}

