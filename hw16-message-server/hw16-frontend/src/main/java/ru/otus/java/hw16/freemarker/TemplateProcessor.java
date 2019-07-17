package ru.otus.java.hw16.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * @author v.chibrikov
 */
public class TemplateProcessor {
    private static final String HTML_DIR = "/templates/";

    private final Configuration configuration;

    public TemplateProcessor() {
        configuration = new Configuration(Configuration.VERSION_2_3_28);
        //configuration.setDirectoryForTemplateLoading(new File(HTML_DIR));  // for directory
        configuration.setClassForTemplateLoading(this.getClass(), HTML_DIR); // for resource
        configuration.setDefaultEncoding("UTF-8");
    }

    public String getPage(String filename, Map<String, Object> data) throws IOException {
        try (Writer stream = new StringWriter()) {
            Template template = configuration.getTemplate(filename);
            template.process(data, stream);
            return stream.toString();
        } catch (TemplateException e) {
            throw new IOException(e);
        }
    }
}
