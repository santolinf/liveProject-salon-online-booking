import { useEffect, useState } from 'react';
import { messageService, SUCCESS, FAILURE } from '../_services';

export default function AppNotificationComponent() {

  const [notification, setNotification] = useState({}),
    { type, message } = notification;

  useEffect(() => {
    const subscription = messageService.getNotifications().subscribe(notification => setNotification(notification));

    return () => subscription.unsubscribe();
  });

  return (
    <div>
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
    </div>
  );
};
