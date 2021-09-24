import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import { todayDateAs_yyyy_MM_dd } from '../utils';
import { fetchWithProgress, messageService } from '../_services';
import moment from 'moment';

// https://www.digitalocean.com/community/tutorials/how-to-handle-routing-in-react-apps-with-react-router
export default function ChooseSlot () {
// https://blog.logrocket.com/the-complete-guide-to-react-hook-form/
  const { register, setValue, handleSubmit } = useForm(),
    todaysDate = todayDateAs_yyyy_MM_dd(),
    { serviceId, serviceName } = useParams(),
    [slotsData, setSlotsData] = useState({});

  useEffect(() => {
    setValue('serviceId', serviceId);
    setValue('selectedSlotDate', todayDateAs_yyyy_MM_dd());
  }, [serviceId, setValue]);

  const onSubmit = values => {
    messageService.showLoadingIndicator();
    fetchWithProgress(process.env.REACT_APP_API_ROOT + '/services/retrieveAvailableSlots/' + serviceId + '/' + values.selectedSlotDate)
      .then(slots => setSlotsData({ selectedSlotDate: values.selectedSlotDate, slots }))
      .catch(error => {
        setSlotsData({});
        messageService.sendErrorNotification('Failed to retrieve slots. Please try later.')
      })
      .finally(() => messageService.hideLoadingIndicator())
    ;
  }

  const onError = errors => console.dir(errors);

  return (
    <div className="container" style={{backgroundColor: 'none'}}>
      <form onSubmit={handleSubmit(onSubmit, onError)} className="form">
        <div className="row">
          <div className="col col-4 text-start" style={{backgroundColor: 'none'}}>
            <strong>Choose a Date for {serviceName}</strong>
            <input type="hidden" {...register('serviceId')} />
          </div>
          <div className="col">
            <input type="date" min={todaysDate} {...register('selectedSlotDate')} />
          </div>
          <div className="col">
            <input type="submit" className="btn btn-primary" value="Show Slots" />
          </div>
        </div>
      </form>
      {
        slotsData && slotsData.slots &&
        <>
          <div className="row mt-4">
            <div className="col col-4 text-start" style={{backgroundColor: 'none'}}>
              <h4>Available Slots on {slotsData.selectedSlotDate}</h4>
            </div>
          </div>
          <div className="grid-container row">
            {slotsData.slots.map(s => (
              <div key={s.id} className="card p-0 mb-4 shadow-sm">
                <div className="card-header">
                  <h4 className="my-0 font-weight-normal">{serviceName}</h4>
                </div>
                <div className="card-body">
                  <div className="card-title"><h1>{s.stylistName}</h1></div>
                  <ul className="card-text list-unstyled mt-3 mb-4">
                    <li>Slot Time {moment(s.slotFor).format('hh:mm A')}</li>
                  </ul>
                  <div>
                    <Link to="" className="btn btn-outline-primary w-100">Book this Slot</Link>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </>
      }
    </div>
  );
}
