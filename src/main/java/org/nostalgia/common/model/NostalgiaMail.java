package org.nostalgia.common.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.nostalgia.common.model.enums.NostalgiaMailTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class NostalgiaMail {

    private List<String> to;
    private NostalgiaMailTemplate template;
    @Builder.Default
    private Map<String, Object> parameters = new HashMap<>();

}
