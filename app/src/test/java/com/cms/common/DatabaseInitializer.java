package com.cms.common;

import org.apache.commons.io.IOUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class DatabaseInitializer {

    private String location;

    private boolean shouldInitData = false;

    private DataSource dataSource;

    public DatabaseInitializer(DataSource dataSource, String location, boolean shouldInit) {
        super();
        this.location = location;
        shouldInitData = shouldInit;
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void initialize() throws IOException {
        if (!shouldInitData) {
            return;
        }
        JdbcTemplate template = new JdbcTemplate(dataSource);
        String[] sqlLines = sqlContent().split(";");
        for (int i = 0; i < sqlLines.length; i++) {
            String sql = sqlLines[i].trim();
            if (sql.isEmpty() || sql.startsWith("--")) {
                continue;
            }
            try {
                int count = template.update(sql);
            } catch (Exception e) {
                throw new RuntimeException("Error executing sql: " + sql, e);
            }
        }
    }

    public String sqlContent() throws IOException {
        String sql = "";
        try {
            URL stream = getClass().getClassLoader().getResource(location);
            File f = new File(stream.toURI());
            File[] childs = f.listFiles();
            for (int k = 0; k < childs.length; k++) {
                FileInputStream fileInputStream = new FileInputStream(childs[k]);
                sql += IOUtils.toString(fileInputStream, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sql;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isShouldInitData() {
        return shouldInitData;
    }

    public void setShouldInitData(boolean shouldInitData) {
        this.shouldInitData = shouldInitData;
    }
}
