package org.example.filters;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFilter {
    private String idn;
    private String firstName;
    private String lastName;
    private String contacts;
}
