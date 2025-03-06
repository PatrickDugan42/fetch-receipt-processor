package com.dugan.fetchreceiptprocessor;

import com.dugan.fetchreceiptprocessor.web.dto.ProcessResponse;
import com.dugan.fetchreceiptprocessor.web.rest.PurchaseController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PurchaseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PurchaseController uut;

    private final ObjectMapper objectMapper = new ObjectMapper();
    String successfulJsonBody = """
        {"retailer":"Target",
        "purchaseDate":"2022-01-01",
        "purchaseTime":"13:01",
        "items":[{
            "shortDescription":"Mountain Dew 12PK","price":6.49},
            {"shortDescription":"Emils Cheese Pizza","price":12.25},
            {"shortDescription":"Knorr Creamy Chicken","price":1.26},
            {"shortDescription":"Doritos Nacho Cheese","price":3.35},
            {"shortDescription":"   Klarbrunn 12-PK 12 FL OZ  ","price":12.00}]
        ,"total":"35.55"}
        """;

    String invalidJsonBody = """
        {"retailer":"",
        "purchaseDate":"2022-01-01",
        "purchaseTime":"13:0143",
        "items":[{
            "shortDescription":"Mountain Dew 12PK","price":6.49},
            {"shortDescription":"Emils Cheese Pizza","price":12.25},
            {"shortDescription":"Knorr Creamy Chicken","price":1.26},
            {"shortDescription":"Doritos Nacho Cheese","price":3.35},
            {"shortDescription":"   Klarbrunn 12-PK 12 FL OZ  ","price":12.00}]
        ,"total":"35.55"}
        """;

    @Test
    void processPurchase_OK_status() throws Exception {
        mockMvc.perform(post("/receipts/process")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(successfulJsonBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void processPurchase_invalid_request() throws Exception {
        mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(invalidJsonBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.purchaseTime")
                        .value("The purchaseTime must follow the format of H:mm."))
                .andExpect(jsonPath("$.message").value("The receipt is invalid."));
    }
    @Test
    void getPointsById_successful() throws Exception {

        MvcResult result = mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(successfulJsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andReturn();

        ProcessResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), ProcessResponse.class);
        mockMvc.perform(get("/receipts/{id}/points", response.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").exists());
    }

    @Test
    void getPointById_idDoesNotExist() throws Exception{
        mockMvc.perform(get("/receipts/{id}/points", 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No receipt found for that ID."));
    }
}