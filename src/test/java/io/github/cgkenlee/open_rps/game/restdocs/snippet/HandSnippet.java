package io.github.cgkenlee.open_rps.game.restdocs.snippet;

import io.github.cgkenlee.open_rps.game.Hand;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.snippet.TemplatedSnippet;

import java.util.HashMap;
import java.util.Map;

/**
 * Hand snippet
 *
 * @author cgkenlee
 * @version 0.0.1, 2020-03-01
 */
public class HandSnippet extends TemplatedSnippet {
    public static final String SNIPPET_NAME = "enum/hand";

    public HandSnippet() {
        super(SNIPPET_NAME, null);
    }

    @Override
    protected Map<String, Object> createModel(Operation operation) {
        return new HashMap<String, Object>(){{ put("hands", Hand.values()); }};
    }
}
