package com.alxsshv.utils.arshin.entities.mit;

import com.alxsshv.utils.arshin.entities.Response;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MitResponse extends Response {
    private MitResult result;
}
