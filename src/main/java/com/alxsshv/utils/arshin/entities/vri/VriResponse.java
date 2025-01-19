package com.alxsshv.utils.arshin.entities.vri;

import com.alxsshv.utils.arshin.entities.Response;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VriResponse extends Response {
    private VriResult result;
}
