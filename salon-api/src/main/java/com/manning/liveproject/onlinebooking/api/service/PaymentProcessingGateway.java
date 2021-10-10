package com.manning.liveproject.onlinebooking.api.service;

import com.google.common.collect.ImmutableList;
import com.manning.liveproject.onlinebooking.api.exceptions.PaymentProcessingException;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
class PaymentIntentDto {

    private String id;
    private String clientSecret;
    private String status;
}

/**
 * status:
 * requires_payment_method      when the PaymentIntent is created, until a payment method is attached
 *
 * requires_confirmation        (optional) after the customer provides their payment information, it is ready to be confirmed
 *
 * requires_action              if the payment requires additional actions, such as authenticating with 3D Secure
 *
 * processing                   once required actions are handled, it moves to processing
 *
 * succeeded                    the payment flow it is driving is complete
 *
 * requires_payment_method      [failure] if the payment attempt fails (for example due to a decline)
 *
 * cancelled                    can be cancelled at any point before it is processing or succeeded;
 *                              this invalidates the PaymentIntent for future payment attempts, and cannot be undone
 */
@Service
public class PaymentProcessingGateway {

    private static final List<String> stripeSuccessfulStatusTypes = ImmutableList.of(
            "succeeded"
    );

    private static final List<String> stripeCancelledStatusTypes = ImmutableList.of(
            "cancelled"
    );

    private static final List<String> stripeNotCompleteStatusTypes = ImmutableList.of(
            "requires_payment_method",
            "requires_confirmation",
            "requires_action",
            "processing"
    );

    private static final String USD = "usd";

    /**
     * Create a new payment intent on the target online platform.
     * @param amount amount for the payment in whole dollars, e.g. $167
     * @return the payment intent ID and the client secret
     */
    public PaymentIntentDto createNewPaymentIntent(Long amount) {
        Long amountInCents = amount * 100; // convert to the smallest currency unit (cents), e.g. a value of 16700 for $167
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setCurrency(USD)
                .setAmount(amountInCents)
                .build();

        try {
            return mapRelevantProperties(PaymentIntent.create(params));
        } catch (StripeException e) {
            throw new PaymentProcessingException("Cannot create payment intent", e);
        }
    }

    public PaymentIntentDto retrievePaymentIntent(String id) {
        try {
            return mapRelevantProperties(PaymentIntent.retrieve(id));
        } catch (StripeException e) {
            throw new PaymentProcessingException("Cannot retrieve payment intent", e);
        }
    }

    public boolean isPaymentFlowSuccessful(String status) {
        return stripeSuccessfulStatusTypes.contains(status);
    }

    public boolean isPaymentFlowCancelled(String status) {
        return stripeCancelledStatusTypes.contains(status);
    }

    private PaymentIntentDto mapRelevantProperties(PaymentIntent paymentIntent) {
        PaymentIntentDto result = new PaymentIntentDto();
        BeanUtils.copyProperties(paymentIntent, result);
        return result;
    }
}
