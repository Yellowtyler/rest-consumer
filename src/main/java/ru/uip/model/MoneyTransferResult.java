package ru.uip.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode
@ToString
final public class MoneyTransferResult {
    @NotBlank
    private JsonAccount from;

    @NotBlank
    private JsonAccount to;
}
