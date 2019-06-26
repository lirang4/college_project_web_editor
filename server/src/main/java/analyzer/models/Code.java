package analyzer.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import analyzer.webDataStractures.WebReportFromGraphResult;
import java.util.List;

import java.time.Instant;


@Document(collection =  "codes")
public class Code {

    @Id
    public String id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    public Instant date;


    private final String userName;

    private final String content;

    private List<WebReportFromGraphResult> report;

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

    public List<WebReportFromGraphResult> getReport() {
        return report;
    }

    public void setReport(List<WebReportFromGraphResult> newReport) {
        report = newReport;
    }

    @Override
    public String toString() {
        return String.format(
                "Code[id=%s, userName='%s', content='%s']",
                id, getUserName(), getContent());
    }
}
