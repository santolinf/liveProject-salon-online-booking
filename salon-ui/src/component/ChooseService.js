import { useEffect, useState } from 'react';
import { messageService, fetchWithProgress } from '../_services';

export default function ChooseService () {
  const [services, setServices] = useState([]);

  useEffect(() => {
    fetchWithProgress(process.env.REACT_APP_API_ROOT + "/services/retrieveAvailableSalonServices")
      .then(data => setServices(data))
      .catch(error => messageService.sendErrorNotification('Failed to retrieve services. Please try later.'))
    ;
  }, []);

  return (
    <div className="container-md">
      <div className="row row-cols-1 row-cols-md-3">
        { services.map(s => (
          <div key={s.id} className="col g-4">
            <div className="card h-100" style={{width: '20em'}}>
              <div className="card-header lead"><strong>{s.name}</strong></div>
              <div className="card-body">
                <div className="card-title"><h2>${s.price}</h2></div>
                <div className="card-text">
                  <div>{s.description}</div>
                  <div>{s.timeInMinutes} Minutes</div>
                </div>
                <div>
                  <button className="btn btn-outline-primary w-100">Book Now</button>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
