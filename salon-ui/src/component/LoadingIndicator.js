import { useState, useEffect } from 'react';
import { messageService } from '../_services';

export default function LoadingIndicator() {
  const [percentageComplete, setPercentageComplete] = useState(0);

  useEffect(() => {
    const subscription = messageService.getPercentageComplete().subscribe(pc => setPercentageComplete(pc));

    return () => subscription.unsubscribe();
  }, []);

  return (
    <div className="LoadingIndicator position-relative top-0 progress">
      <div className={"progress-bar w-" + percentageComplete} role="progressbar"
           aria-valuenow={percentageComplete} aria-valuemin="0" aria-valuemax="100">
        Services {percentageComplete}% loaded
      </div>
    </div>
  );
};
