package com.rse.webservice.locket.payload.file.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class FileSelfRequest {
    private Long id;
}
