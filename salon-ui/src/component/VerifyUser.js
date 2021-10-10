import { Fragment, useState } from 'react';
import QrReader from 'react-qr-reader';
import moment from 'moment';
import { fetchWithProgress, messageService } from '../_services';
import './VerifyUser.css';

const ticketUriRe = /[http|https]:\/\/[\w\d:\-_/]*\/(.+)/;

export default function VerifyUser () {

  const [scanQrCode, setScanQrCode] = useState(true),
    [ticket, setTicket] = useState(undefined);

  const onScan = qrResult => {
      if (qrResult === null) {
        return;
      }

      const matchResults = qrResult.match(ticketUriRe);
      if (matchResults && matchResults[1]) {
        setScanQrCode(false);
        const ticketId = matchResults[1];

        messageService.showLoadingIndicator();
        fetchWithProgress(process.env.REACT_APP_API_ROOT + "/tickets/" + ticketId)
          .then(data => setTicket(data))
          .catch(error => messageService.sendErrorNotification("Failed to retrieve ticket. " + error.message))
          .finally(() => messageService.hideLoadingIndicator())
        ;
      } else {
        messageService.sendErrorNotification('Cannot identify ticket from QR code. Please try again.');
      }
    },
    onError = error => messageService.sendErrorNotification('Error reading QR code. ' + error.message),
    scanAnother = () => {
      setTicket(undefined);
      setScanQrCode(true);
    };

  return (
    <Fragment>
      { scanQrCode && <QrReader delay={300}
                onError={onError}
                onScan={onScan}
                style={{ width: '100%' }} /> }
      { ticket &&
      <div className="VerifyUser container p-5">
        <div className="row mb-4">
          <h2>Details</h2>
        </div>
        <div className="row">
          <div className="col-6">
            <h6>Service Details</h6>
            <p>{ticket.payment.selectedService.name} @ {moment(ticket.payment.slot.slotFor).format('ddd MMMM D YYYY')} By {ticket.payment.slot.stylistName}</p>
            <hr/>
            <h6>User Information</h6>
            <ul>
              <li>{ticket.payment.firstName + ' ' + ticket.payment.lastName}</li>
              <li>{ticket.payment.email}</li>
              <li>{ticket.payment.phoneNumber}</li>
            </ul>
          </div>
          <div className="col-6">
            <button className="btn btn-primary" onClick={scanAnother}>Scan another</button>
          </div>
        </div>
      </div>
      }
    </Fragment>
  );
}
