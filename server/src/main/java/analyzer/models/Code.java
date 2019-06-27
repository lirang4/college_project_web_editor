package analyzer.models;

import analyzer.webDataStractures.WebReport;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import analyzer.webDataStractures.WebReportFromGraphResult;
import java.util.List;

import java.time.Instant;


@Document(collection =  "codes7")
public class Code {

    @Id
    public String id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    public Instant date;

    public final String userName;
    public final String content;

    public WebReport report;

    public Code(@JsonProperty("userName") String userName, @JsonProperty("content") String content) {
        this.userName = userName;
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
    }

    public WebReport getReport() {
        return report;
    }

    public void setReport(WebReport newReport) {
        report = newReport;
    }

    @Override
    public String toString() {
        return String.format(
                "Code[id=%s, userName='%s', content='%s']",
                id, getUserName(), getContent());
    }
}
