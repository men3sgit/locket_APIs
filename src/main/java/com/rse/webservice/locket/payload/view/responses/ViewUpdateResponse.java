package com.rse.webservice.locket.payload.view.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class ViewUpdateResponse {
    private Boolean success;
}
