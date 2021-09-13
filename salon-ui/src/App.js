import './App.css';
import LoadingIndicator from './component/LoadingIndicator';
import AppNotificationComponent from './component/AppNotificationComponent';
import ChooseService from './component/ChooseService';

function App() {
  return (
    <div className="App container-fluid">
      <header/>
      <nav className="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
        <div className="container-fluid">
          <span className="navbar-brand">AR Salon and Day Spa Services</span>
        </div>
      </nav>
      <main role="main" className="container">
        <div className="padding-container">
          <LoadingIndicator/>
        </div>
        <div className="padding-container">
          <ChooseService/>
        </div>
        <AppNotificationComponent/>
      </main>
      <footer/>
    </div>
  );
}

export default App;
