package com.basakdm.excartest.specificationCAR;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchCriteriaCar {
    private String key;
    private String operation;
    private Object value;
}
