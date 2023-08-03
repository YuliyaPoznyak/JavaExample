package by.a1qa.poznyak.dao.mysqlTables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TestTable implements Serializable {
    private Long id;
    private String name;
    private int statusId;
    private String methodName;
    private long projectId;
    private long sessionId;
    private Timestamp startTime;
    private Timestamp endTime;
    private String env;
    private String browser;
    private Long authorId;

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", statusId=" + statusId +
                ", methodName='" + methodName + '\'' +
                ", projectId=" + projectId +
                ", sessionId=" + sessionId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", env='" + env + '\'' +
                ", browser='" + browser + '\'' +
                ", authorId=" + authorId +
                '}';
    }
}
