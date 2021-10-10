import { useForm } from 'react-hook-form';
import { loadStripe } from '@stripe/stripe-js';
import { CardElement, Elements, useElements, useStripe } from '@stripe/react-stripe-js';
import './PayWithStripe.css';
import { messageService } from '../_services';

// Make sure to call `loadStripe` outside of a component’s render to avoid
// recreating the `Stripe` object on every render.
const stripePromise = loadStripe(process.env.REACT_APP_STRIPE_PUBLISHABLE_KEY);

const CARD_ELEMENT_OPTIONS = {
  style: {
    base: {
      color: "#32325d",
      fontFamily: '"Helvetica Neue", Helvetica, sans-serif',
      fontSmoothing: "antialiased",
      fontSize: "16px",
      "::placeholder": {
        color: "#aab7c4",
      },
    },
    invalid: {
      color: "#fa755a",
      iconColor: "#fa755a",
    },
  },
};

function GetCardDetails ({ initiatePaymentResponse, onPaymentSuccessResponse }) {

  const stripe = useStripe(),
    elements = useElements(),
    { handleSubmit } = useForm();

  const onSubmit = async data => {
      messageService.showLoadingIndicator();

      console.dir(data);

      const { clientSecret, firstName, lastName, email, phoneNumber } = initiatePaymentResponse;

      const result = await stripe.confirmCardPayment(clientSecret, {
        payment_method: {
          card: elements.getElement(CardElement),
          billing_details: {
            name: firstName + ' ' + lastName,
            email: email,
            phone: phoneNumber
          },
        }
      });

      if (result.error) {
        // Show error to your customer (e.g., insufficient funds)
        console.log(result.error.message);
        messageService.sendErrorNotification(result.error.message);
      } else {
        // The payment has been processed!
        if (result.paymentIntent.status === 'succeeded') {
          console.dir(result.paymentIntent);
          onPaymentSuccessResponse(result.paymentIntent);
        }
      }

      messageService.hideLoadingIndicator();
    },
    onError = errors => messageService.sendErrorNotification(errors.message);

  return (
    <div className="container p-5">
      <form onSubmit={handleSubmit(onSubmit, onError)} className="form">
        <div className="row mb-4">
          <h2>Enter Card Details</h2>
          <CardElement options={CARD_ELEMENT_OPTIONS} />
        </div>
        <div className="row">
          <div className="col">
            <input type="submit" className="btn btn-primary" value="Pay" disabled={!stripe} />
          </div>
        </div>
      </form>
    </div>
  );
}

export default function PayWithStripe ({ initiatePaymentResponse, onPaymentSuccessResponse }) {

  return (
    <Elements stripe={stripePromise}>
      <GetCardDetails initiatePaymentResponse={initiatePaymentResponse} onPaymentSuccessResponse={onPaymentSuccessResponse} />
    </Elements>
  );
}
