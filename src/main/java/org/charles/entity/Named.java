package org.charles.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Named {

    private  String text;

    public Named(String text, String ner) {
        this.text = text;
        this.ner = ner;
    }

    private String ner;
}
