import { useState } from 'react';
import './PaymentContainer.css';
import GetBillingDetails from './GetBillingDetails';
import PayWithStripe from './PayWithStripe';
import ShowConfirmedTicket from './ShowConfirmedTicket';

export default function PaymentContainer () {
  const [initiatePaymentResponse, setInitiatePaymentResponse] = useState(undefined),
    [paymentSuccessResponse, setPaymentSuccessResponse] = useState(undefined);

  return (
    <div className="PaymentContainer">
      { initiatePaymentResponse === undefined &&
        <GetBillingDetails onInitiatePaymentResponse={response => setInitiatePaymentResponse(response)} /> }
      { initiatePaymentResponse && paymentSuccessResponse === undefined &&
        <PayWithStripe initiatePaymentResponse={initiatePaymentResponse}
                       onPaymentSuccessResponse={response => setPaymentSuccessResponse(response)} /> }
      { initiatePaymentResponse && paymentSuccessResponse &&
        <ShowConfirmedTicket paymentSuccessResponse={paymentSuccessResponse} /> }
    </div>
  );
}
