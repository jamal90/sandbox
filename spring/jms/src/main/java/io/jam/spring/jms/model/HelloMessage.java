package io.jam.spring.jms.model;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HelloMessage implements Serializable {

    static final long serialVersionUID = 5609991540038922875L;

    private UUID uuid;
    private String message;
}
