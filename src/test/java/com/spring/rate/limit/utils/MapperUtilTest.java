package com.spring.rate.limit.utils;

import com.spring.rate.limit.domain.rate_limit.model.response.RateLimitModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.spring.rate.limit.utils.MapperUtil.convertFromStringToObject;
import static com.spring.rate.limit.utils.MapperUtil.convertObjectToString;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class MapperUtilTest {

    @Test
    void convertObjectToStringReturnsEmpty_WhenObjectIsNull() {
        Optional<String> response = convertObjectToString(null);

        assertThat(response.get()).contains("null");
    }

    @Test
    void successfullyConvertObjectToString() {
        Object object = new RateLimitModel(10, 1);

        Optional<String> response = convertObjectToString(object);


        assertNotNull(response);
        assertThat(response)
                .isNotEmpty()
                .contains("{\"requestCount\":10,\"expiration\":1}");
    }

    @Test
    void convertFromStringToObjectFails_WhenStringIsNull() {
        Optional<RateLimitModel> response = convertFromStringToObject(null, RateLimitModel.class);

        assertThat(response).isEmpty();
    }

    @Test
    void convertFromStringToObjectFails_WhenStringIsEmpty() {
        Optional<RateLimitModel> response = convertFromStringToObject("", RateLimitModel.class);

        assertThat(response).isEmpty();
    }

    @Test
    void convertFromStringToObjectFails_WithInvalidString() {
        String objectData = "{:10,\"expiration\":1000}";

        Optional<RateLimitModel> response = convertFromStringToObject(objectData, RateLimitModel.class);

        assertThat(response).isEmpty();
    }

    @Test
    void successfullyConvertFromStringToObject() {
        String objectData = "{\"requestCount\":10,\"expiration\":1}";

        Object response = convertFromStringToObject(objectData, RateLimitModel.class);

        assertNotNull(response);
    }
}
