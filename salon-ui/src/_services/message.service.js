import { Subject } from 'rxjs';
import { FAILURE, SUCCESS } from './notificationTypes';

const loadingProgressSubject = new Subject();
const loadingIndicatorSubject = new Subject();
const appNotificationSubject = new Subject();

export const messageService = {
  sendPercentageComplete: pc => loadingProgressSubject.next(pc),
  getPercentageComplete: () => loadingProgressSubject.asObservable(),
  showLoadingIndicator: () => loadingIndicatorSubject.next(true),
  hideLoadingIndicator: () => loadingIndicatorSubject.next(false),
  getLoadingIndicator: () => loadingIndicatorSubject.asObservable(),

  sendSuccessNotification: message => appNotificationSubject.next({ type: SUCCESS, message }),
  sendErrorNotification: message => appNotificationSubject.next({ type: FAILURE, message }),
  getNotifications: () => appNotificationSubject.asObservable()
};
