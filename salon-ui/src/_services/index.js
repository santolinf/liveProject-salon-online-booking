import { FIVE_SECONDS } from '../constants';

function doAfterDelay (fn, delayInSeconds = FIVE_SECONDS) {
  setTimeout(() => fn(), delayInSeconds);
}

export * from './message.service';
export * from './notificationTypes'
export * from './http.service';

export {
  doAfterDelay
};
