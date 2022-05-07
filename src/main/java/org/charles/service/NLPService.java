package org.charles.service;


import com.alibaba.fastjson.JSON;
import org.charles.entity.Named;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Slf4j
@Service
public class NLPService {

    @Autowired
    private StanfordCoreNLP pipeline;


    public List<Named> NamedList(String text) {
        Annotation annotation = new Annotation(text);
        pipeline.annotate(annotation);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        StringBuilder stringBuffer = new StringBuilder();
        List<Named> namedList = new ArrayList<>();
        for (CoreMap sentence : sentences) {
            // 获取句子的 token（可以是作为分词后的词语）
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                //String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                //String ne = token.get(CoreAnnotations.NormalizedNamedEntityTagAnnotation.class);
                String ner = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
//                if (ner.equals("PERSON")) {
                    namedList.add(new Named(word, ner));
//                }
            }
        }
        return namedList;
    }


    /**
     * 对句子进行分词、词形还原
     */
    public void precess(String paragraph) {

        //创建props，依次对段落进行处理
//        Properties props = new Properties();
//        props.put("annotators", "tokenize, ssplit, pos, lemma");
//        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        //对段落进行分词和词形还原
        //paragraph = "The Origins of Theater";
        Annotation document = new Annotation(paragraph);
        pipeline.annotate(document);

        //处理之后的结果
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        Map<String, String> map = new HashMap<>();

        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(TokensAnnotation.class)) {

                String word = token.get(TextAnnotation.class);            // 获取分词
                //String pos = token.get(PartOfSpeechAnnotation.class);     // 获取词性标注
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);    // 获取命名实体识别结果
                String lemma = token.get(LemmaAnnotation.class);          // 获取词形还原结果

                map.put(word, lemma);
            }
        }
        log.info(" MAP = {}", JSON.toJSONString(map));
    }

}