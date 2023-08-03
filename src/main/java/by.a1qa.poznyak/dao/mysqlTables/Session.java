package by.a1qa.poznyak.dao.mysqlTables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    private Long id;
    private String sessionKey;
    private Timestamp createdTime;
    private Long buildNumber;

    public Session(String sessionKey, Timestamp createdTime, Long buildNumber) {
        this.sessionKey = sessionKey;
        this.createdTime = createdTime;
        this.buildNumber = buildNumber;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public void setBuildNumber(Long buildNumber) {
        this.buildNumber = buildNumber;
    }
}
