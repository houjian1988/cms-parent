package com.cms.common;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class DataPrepareUtil {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private DataSource dataSource;

    private JdbcTemplate template;

    /**
     * 执行测试文件中测试语句
     *
     * @throws IOException
     */
    @Before
    public void setUp() throws IOException {
        template = new JdbcTemplate(dataSource);
        String content = sqlForThisTest();
        if (content.equals("")) {
            return;
        }
        String[] sqlLines = content.split(";");
        for (int i = 0; i < sqlLines.length; i++) {
            String sql = sqlLines[i];
            log.debug("SQL:" + sql);
            if (0 == sql.trim().length()) {
                continue;
            }
            template.update(sql);
            log.debug("SQL UPDATE END");
        }
    }

    /**
     * 根据测试类名称找到测试SQL文件,执行SQL语句
     *
     * @return 测试文件内容
     * @throws IOException
     */
    private String sqlForThisTest() throws IOException {
        String sqlName = getClass().getSimpleName() + ".sql";
        InputStream stream = getClass().getResourceAsStream(sqlName);
        if (stream == null) {
            return "";
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        StringBuilder buffer = new StringBuilder();
        try {
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } finally {
            reader.close();
        }
        return buffer.toString();
    }
}
