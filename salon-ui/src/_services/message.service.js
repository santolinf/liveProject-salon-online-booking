import { Subject } from 'rxjs';
import { FAILURE, SUCCESS } from './notificationTypes';

const loadingIndicatorSubject = new Subject();
const appNotificationSubject = new Subject();

export const messageService = {
  sendPercentageComplete: pc => loadingIndicatorSubject.next(pc),
  getPercentageComplete: () => loadingIndicatorSubject.asObservable(),

  sendSuccessNotification: message => appNotificationSubject.next({ type: SUCCESS, message }),
  sendErrorNotification: message => appNotificationSubject.next({ type: FAILURE, message }),
  getNotifications: () => appNotificationSubject.asObservable()
};
