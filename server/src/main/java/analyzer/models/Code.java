package analyzer.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

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

    public Code(String userName, String content) {
        this.userName = userName;
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return String.format(
                "Code[id=%s, userName='%s', content='%s']",
                id, getUserName(), getContent());
    }
}
