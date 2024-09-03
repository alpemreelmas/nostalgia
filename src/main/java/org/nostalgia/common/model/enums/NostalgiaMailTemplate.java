package org.nostalgia.common.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NostalgiaMailTemplate {

    CREATE_PASSWORD("create-password.html");

    private final String file;

}
