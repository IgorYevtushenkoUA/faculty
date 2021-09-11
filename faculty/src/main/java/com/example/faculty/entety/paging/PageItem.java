package com.example.faculty.entety.paging;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageItem {
    PageItemType pageItemType;
    int index;
    boolean active;
}
