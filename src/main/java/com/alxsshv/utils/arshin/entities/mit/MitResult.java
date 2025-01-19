package com.alxsshv.utils.arshin.entities.mit;

import com.alxsshv.utils.arshin.entities.Result;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
@JsonDeserialize
public class MitResult extends Result {
    private int count;
    private int start;
    private int rows;
    private List<MitItem> items = new ArrayList<>();
}
