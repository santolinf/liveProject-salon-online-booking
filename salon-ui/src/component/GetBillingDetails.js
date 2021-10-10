import { useParams } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import { messageService, postWithProgress } from '../_services';

export default function GetBillingDetails (props) {

  const { slotId, serviceId } = useParams(),
    { onInitiatePaymentResponse } = props,
    { register, handleSubmit, formState: { errors } } = useForm();

  const onSubmit = data => {
      messageService.showLoadingIndicator();
      postWithProgress(process.env.REACT_APP_API_ROOT + '/payments/initiate', data)
        .then(response => onInitiatePaymentResponse(response))
        .catch(error => {
          messageService.sendErrorNotification('Failed to initiate payment. ' + error.message)
        })
        .finally(() => messageService.hideLoadingIndicator())
      ;
    },
    onError = errors => messageService.sendErrorNotification('Please fill in all details.');

  return (
    <div className="container p-5">
      <form onSubmit={handleSubmit(onSubmit, onError)} className="form">
        <div className="row">
          <h2>Enter Billing Details</h2>
        </div>
        <input type="hidden" {...register('slotId', { value: slotId })} />
        <input type="hidden" {...register('salonServiceDetailId', { value: serviceId })} />
        <div className="row mb-3">
          <div className="col-12">
            <label htmlFor="firstName" className="form-label">First Name</label>
            <input type="text" className={`form-control ${errors.firstName ? 'is-invalid' : ''}`}
                   {...register('firstName', { required: true })} />
          </div>
        </div>
        <div className="row mb-3">
          <div className="col">
            <label htmlFor="lastName" className="form-label">Last Name</label>
            <input type="text" className={`form-control ${errors.lastName ? 'is-invalid' : ''}`}
                   {...register('lastName', { required: true })} />
          </div>
        </div>
        <div className="row mb-3">
          <div className="col">
            <label htmlFor="email" className="form-label">Email address</label>
            <input type="email" className={`form-control ${errors.email ? 'is-invalid' : ''}`}
                   {...register('email', { required: true })} />
          </div>
        </div>
        <div className="row mb-3">
          <div className="col">
            <label htmlFor="phoneNumber" className="form-label">Phone Number</label>
            <input type="tel" className={`form-control ${errors.phoneNumber ? 'is-invalid' : ''}`}
                   {...register('phoneNumber', { required: true })} />
          </div>
        </div>
        <div className="row">
          <div className="col">
            <input type="submit" className="btn btn-primary" value="Make Payment" />
          </div>
        </div>
      </form>
    </div>
  );
}
