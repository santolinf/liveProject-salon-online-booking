import { Fragment, useEffect, useState } from 'react';
import { messageService, SUCCESS, FAILURE, doAfterDelay } from '../_services';

export default function AppNotificationComponent() {

  const [notification, setNotification] = useState({}),
    { type, message } = notification;

  function reset () {
    setNotification({});
  }

  useEffect(() => {
    const subscription = messageService.getNotifications().subscribe(notification => {
      setNotification(notification);
      doAfterDelay(reset);
    });

    return () => subscription.unsubscribe();
  });

  return (
    <Fragment>
      {SUCCESS === type && <div className="alert alert-success d-flex align-items-center" role="alert">
        <div>
          {message}
        </div>
      </div>}
      {FAILURE === type && <div className="alert alert-danger d-flex align-items-center" role="alert">
        <div>
           {message}
        </div>
      </div>}
    </Fragment>
  );
};
