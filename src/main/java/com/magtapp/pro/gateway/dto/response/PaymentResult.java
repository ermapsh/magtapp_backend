package com.magtapp.pro.gateway.dto.response;

public sealed interface PaymentResult permits PaymentResult.Pending ,PaymentResult.Failure, PaymentResult.Success {
    record Pending(String registrationRef)implements PaymentResult{}
//    record Success(String registrationRef)implements PaymentResult{}
    record Failure(String errorCode, String errorDescription) implements PaymentResult{}

    record Success(String bankReference)implements PaymentResult{}
}
