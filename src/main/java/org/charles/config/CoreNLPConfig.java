package org.charles.config;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class CoreNLPConfig {

    @Bean
    public StanfordCoreNLP getPipeline() {
        String props = "chinese.properties";  // 配置文件，放在 main/resources 目录下
//        pipeline = new StanfordCoreNLP(props);
//        Properties props = new Properties();
//        props.put("annotators", "tokenize, ssplit, pos, lemma");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        return pipeline;
    }

}
