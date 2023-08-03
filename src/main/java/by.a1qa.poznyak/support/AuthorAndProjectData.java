package by.a1qa.poznyak.support;

import aquality.selenium.core.logging.Logger;
import by.a1qa.poznyak.dao.AuthorDao;
import by.a1qa.poznyak.dao.ProjectDao;
import by.a1qa.poznyak.dao.SessionDao;
import by.a1qa.poznyak.dao.mysqlTables.Author;
import by.a1qa.poznyak.dao.mysqlTables.Project;
import by.a1qa.poznyak.dao.mysqlTables.Session;
import by.a1qa.poznyak.readPropertiesFiles.ReadFromConfProperties;
import by.a1qa.poznyak.readPropertiesFiles.ReadTestProperties;

import java.sql.Timestamp;

public class AuthorAndProjectData {
    public static long getAuthorId() {
        Author author = new Author(null, ReadFromConfProperties.getAuthorName(),
                ReadFromConfProperties.getAuthorLogin(), ReadFromConfProperties.getAuthorEmail());
        long authorId = AuthorDao.create(author);
        if (authorId ==0) {
            authorId = AuthorDao.readOne(author.getLogin()).getId();
        }
        Logger.getInstance().info("The author_id is " + authorId);
        return authorId;
    }

    public static long getProjectID() {
        String projectName = ReadFromConfProperties.getProjectName();
        Project project = new Project(null, projectName);
        long projectId = ProjectDao.create(project);
        if (projectId ==0) {
            projectId = ProjectDao.readOne(projectName).getId();
        }
        Logger.getInstance().info("The project_id is " + projectId);
        return projectId;
    }

    public static long getSessionId(){
        String sessionKey = ReadFromConfProperties.getSessionKey();
        long startTime = System.currentTimeMillis();
        Timestamp timeStampStart = new Timestamp(startTime);
        Long buildNumber = ReadTestProperties.getBuildNumber();
        Session session = new Session(sessionKey, timeStampStart, buildNumber);
        long sessionId = SessionDao.create(session);
        Logger.getInstance().info("SessionId is " + sessionId);
        return sessionId;
    }
}
