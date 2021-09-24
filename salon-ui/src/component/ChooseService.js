import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { messageService, fetchWithProgress } from '../_services';

export default function ChooseService () {
  const [services, setServices] = useState([]);

  useEffect(() => {
    messageService.showLoadingIndicator();
    fetchWithProgress(process.env.REACT_APP_API_ROOT + "/services/retrieveAvailableSalonServices")
      .then(data => setServices(data))
      .catch(error => messageService.sendErrorNotification('Failed to retrieve services. Please try later.'))
      .finally(() => messageService.hideLoadingIndicator())
    ;
  }, []);

  return (
    <div className="grid-container row">
      { services.length === 0 && <div>No Services Loaded!</div> }
      { services.map(s => (
        <div key={s.id} className="card p-0 mb-4 shadow-sm">
          <div className="card-header">
            <h4 className="my-0 font-weight-normal">{s.name}</h4>
          </div>
          <div className="card-body">
            <div className="card-title"><h1>${s.price}</h1></div>
            <ul className="card-text list-unstyled mt-3 mb-4">
              <li>{s.description}</li>
              <li>{s.timeInMinutes} Minutes</li>
            </ul>
            <div>
              <Link to={`/chooseslot/${s.id}/${s.name}`} className="btn btn-outline-primary w-100">Book Now</Link>
            </div>
          </div>
        </div>
      ))}
    </div>
  );
}
