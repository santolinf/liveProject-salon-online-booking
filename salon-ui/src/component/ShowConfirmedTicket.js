import { useEffect, useState } from 'react';
import QRCode from 'qrcode.react';
import { messageService, putWithProgress } from '../_services';
import moment from 'moment';
import './ShowConfirmedTicket.css';

export default function ShowConfirmedTicket ({ paymentSuccessResponse }) {

  const [confirmationResponse, setConfirmationResponse] = useState({}),
    { id: paymentIntentId } = paymentSuccessResponse,
    { salonDetails = undefined, ticket = {} } = confirmationResponse,
    { id: ticketId, payment = {} } = ticket,
    { selectedService = undefined, slot = undefined } = payment
  ;

  useEffect(() => {
    messageService.showLoadingIndicator();
    putWithProgress(process.env.REACT_APP_API_ROOT + "/payments/confirm/" + paymentIntentId)
      .then(data => setConfirmationResponse(data))
      .catch(error => messageService.sendErrorNotification('Failed to confirm payment. ' + error.message))
      .finally(() => messageService.hideLoadingIndicator())
    ;
  }, [paymentIntentId]);

  return (
    <>
    { salonDetails && ticketId && selectedService && slot &&
      <div className="container p-5">
        <div className="row mb-4">
          <h2>Your Ticket Details</h2>
        </div>
        <div className="row">
          <div className="col-6">
            <h6>Service Details</h6>
            <p>{selectedService.name} @ {moment(slot.slotFor).format('ddd MMMM D YYYY')} By {slot.stylistName}</p>
            <hr/>
            <h6>Salon Address Details</h6>
            <ul>
              <li>{salonDetails.name}</li>
              <li>{salonDetails.address}</li>
              <li>{salonDetails.city}</li>
              <li>{salonDetails.state}</li>
              <li>Zip {salonDetails.zipcode}</li>
              <li>Phone {salonDetails.phone}</li>
            </ul>
          </div>
          <div className="col-6">
            <h6>Take a picture of the code below and present it to the salon</h6>
            <QRCode value={ process.env.REACT_APP_API_ROOT + '/tickets/' + ticketId } />
          </div>
        </div>
      </div>
    }
    </>
  );
}
