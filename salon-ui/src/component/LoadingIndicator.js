import {useState, useEffect, Fragment} from 'react';
import { messageService } from '../_services';

export default function LoadingIndicator() {
  const [percentageComplete, setPercentageComplete] = useState(0),
    [showLoading, setShowLoading] = useState(false);

  useEffect(() => {
    const pcSubscription = messageService.getPercentageComplete().subscribe(pc => setPercentageComplete(pc)),
      showSubscription = messageService.getLoadingIndicator().subscribe(value => setShowLoading(value));

    return () => {
      pcSubscription.unsubscribe();
      showSubscription.unsubscribe();
    }
  }, []);

  return (
    <Fragment>
      { showLoading &&
        <div className="LoadingIndicator progress">
          <div className={"progress-bar w-" + percentageComplete} role="progressbar"
               aria-valuenow={percentageComplete} aria-valuemin="0" aria-valuemax="100">
            {percentageComplete}%
          </div>
        </div>
      }
    </Fragment>
  );
};
