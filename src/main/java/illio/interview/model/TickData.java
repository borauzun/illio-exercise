package illio.interview.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"date", "open", "high", "low", "close", "adjusted_close", "volume"})
public class TickData {


private String date;
private Double open;
private Double high;
private Double low;
private Double close;
private Double adjustedClose;
private Integer volume;

// Normally I would use Lombok instead of getters and setters, but lombok requires plugin on the IDE. So Getters & Setters are below

@JsonProperty("date")
public String getDate() {
return date;
}
@JsonProperty("date")
public void setDate(String date) {
this.date = date;
}

@JsonProperty("open")
public Double getOpen() {
return open;
}
@JsonProperty("open")
public void setOpen(Double open) {
this.open = open;
}

@JsonProperty("high")
public Double getHigh() {
return high;
}
@JsonProperty("high")
public void setHigh(Double high) {
this.high = high;
}

@JsonProperty("low")
public Double getLow() {
return low;
}
@JsonProperty("low")
public void setLow(Double low) {
this.low = low;
}

@JsonProperty("close")
public Double getClose() {
return close;
}
@JsonProperty("close")
public void setClose(Double close) {
this.close = close;
}

@JsonProperty("adjusted_close")
public Double getAdjustedClose() {
return adjustedClose;
}
@JsonProperty("adjusted_close")
public void setAdjustedClose(Double adjustedClose) {
this.adjustedClose = adjustedClose;
}

@JsonProperty("volume")
public Integer getVolume() {
return volume;
}
@JsonProperty("volume")
public void setVolume(Integer volume) {
this.volume = volume;
}

}