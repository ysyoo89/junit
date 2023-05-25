package com.test.junit.program;

import com.test.junit.util.Http;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class AddressRetrieverTest {

    @Mock private Http http;
    @InjectMocks private AddressRetriever retriever;

    @Before
    public void createRetriever() {
        retriever = new AddressRetriever();
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void answersAppropriateAddressForValidCoordinates() throws IOException, ParseException {
        Http http = mock(Http.class);
        when(http.get(contains("lat=38.000000&lon=-104.000000"))).thenReturn(
                "{\"address\":{"
                        + "\"house_number\":\"324\","
                        + "\"road\":\"North Tejon Street\","
                        + "\"city\":\"Colorado Springs\","
                        + "\"state\":\"Colorado\","
                        + "\"postcode\":\"80903\","
                        + "\"country_code\":\"us\"}"
                        + "}"
        );

        AddressRetriever retriever = new AddressRetriever();
        Address address = retriever.retrieve(38.0, -104.0);

        assertThat(address.houseNumber).isEqualTo("324");
        assertThat(address.road).isEqualTo("North Tejon Street");
        assertThat(address.city).isEqualTo("Colorado Springs");
        assertThat(address.state).isEqualTo("Colorado");
        assertThat(address.zip).isEqualTo("80903");

    }

    @Test
    public void returnsAppropriateAddressForValidCoordinates() throws IOException, ParseException {
        Http http = new Http() {
            @Override
            public String get(String url) throws IOException {
                return "{\"address\":{"
                        + "\"house_number\":\"324\","
                        + "\"road\":\"North Tejon Street\","
                        // ...
                        + "\"city\":\"Colorado Springs\","
                        + "\"state\":\"Colorado\","
                        + "\"postcode\":\"80903\","
                        + "\"country_code\":\"us\"}"
                        + "}";
            }};

        AddressRetriever retriever = new AddressRetriever();
        Address address = retriever.retrieve(38.0, -104.0);

        assertThat(address.houseNumber).isEqualTo("324");
        assertThat(address.road).isEqualTo("North Tejon Street");
        assertThat(address.city).isEqualTo("Colorado Springs");
        assertThat(address.state).isEqualTo("Colorado");
        assertThat(address.zip).isEqualTo("80903");
    }
}