import React, { useState, useEffect } from "react";
import Login from "./components/Login";
import CustomersGrid from "./components/CustomersGrid";
import { getAuth } from "./service/authService";

 function App() {

  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {

    setIsAuthenticated(!!getAuth());

  }, []);

  const onLoginSuccess = () => {
    setIsAuthenticated(true);
  };

  return (
    <div>
  
      {/* // simple conditional rendering */}
      {isAuthenticated ? ( 
        <CustomersGrid />
      ) : (
        <Login onLoginSuccess={onLoginSuccess} />
      )}
    </div>
  );
}


export default App;





// import './App.css';
// import ArrayList from './components/ArrayList';
// import ParentComp from './components/communication/ParentComp';
// import Counter from './components/Counter';
// import CustomerForm from './components/CustomerForm';
// import CustomerDetails from './components/customers/CustomerDetails';
// import CustomersList from './components/customers/CustomersList';
// import Employee  from './components/Employee';
// import FetchUsers from './components/FetchUsers';
// import LifeCycle from './components/LifeCycle';
// import Home from './components/pages/Home';
// import Nav from './components/pages/Nav';
// import PageNotFound from './components/pages/PageNotFound';
// import SimpleForm from './components/SimpleForm';
// import {Routes, Route, Link} from 'react-router-dom';
// import Welcome from './components/Welcome';
// import FetchUser from './components/FetchUser';
// import CreateUser from './components/CreateUser';

// function App() {
  
//   return (
//     <div className="container">

//       <CreateUser />

//       {/* <FetchUser /> */}

//       <FetchUsers />
//       <Welcome fname="venkat"  id="1" />

//       {/* <Counter /> */}
//       {/* <Nav />

//       <Routes>
//         <Route path='/' element={ <CustomersList /> } />
        
//         <Route path='/home' element= {<Home />} />
//         <Route path='/customers/:cid' element= {<CustomerDetails />} />
//         <Route path='*' element= {<PageNotFound />} />
        
//       </Routes> */}

      
//     </div>
//   );
// }

// export default App;
