package illio.interview.api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import illio.interview.model.TickData;
import illio.interview.util.Constants;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HistoricalDataClient {

    private String baseUri;
    private String apiToken;
    private String dateFormat;
    private String ticker;


    /**
     * This is the method  being asked to be implemented
     * Only Public method in this class
     * @param dateBegin : Start date of the data for analysis
     * @param dateEnd   : End date of the data for analysis
     * @param ticker    : Defines the ticker
     * @return
     */
    public int getAverageRange(LocalDate dateBegin, LocalDate dateEnd, String ticker) {
        this.ticker=ticker;
        List<TickData> list = getTickDataList(dateBegin, dateEnd);
        return getAvarageInPence(list);
    }

    /**
     *
     * @param list : The list of TickData
     * @return : returns the average in Pence
     */
    private int getAvarageInPence(List<TickData> list){
        double average=0.0;
        if (list.size() > 0) {
            average = list.stream().mapToDouble(x -> x.getOpen()).average().getAsDouble()*100;
        }
        return (int) average;
    }

    /**
     * This method makes a HTTP GET to Rest API and gets all the price actions between two dates
     * @param date1 : Beginning date of the query
     * @param date2 : End date of the query
     * @return
     */
    private List<TickData> getTickDataList(LocalDate date1, LocalDate date2){
        String formattedDate1 = date1.format(DateTimeFormatter.ofPattern(this.dateFormat));
        String formattedDate2 = date2.format(DateTimeFormatter.ofPattern(this.dateFormat));

        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        String uri=baseUri+ticker+"?api_token="+apiToken+"&period=d.&from="+formattedDate1+"&to="+formattedDate2+"&fmt=json";
        ResponseEntity<TickData[]> result = restTemplate.exchange(uri, HttpMethod.GET, entity, TickData[].class);

        return Arrays.asList(result.getBody());

    }

   // Constructor
    HistoricalDataClient(String ticker){
        this.baseUri= Constants.BASE_URL_HISTORICAL_DATA;
        this.apiToken=Constants.API_TOKEN;
        this.dateFormat="yyyy-MM-dd";
    }
}

