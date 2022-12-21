import "./App.css";
import Login from "./Components/Login";
import Home from "./Components/Home";
import Navigation from "./Components/Navigation";
import { useEffect, useState } from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import { refresh } from "./Services/auth";
import AuthContext from "./contexts/AuthContext";
import SignUp from "./Components/SignUp";
import ViewEmployees from "./Components/Customer/ViewEmployees";
import ServiceList from "./Components/Admin/ServiceList";
import AddService from "./Components/Admin/AddService";
import UpdateService from "./Components/Admin/UpdateService";
import EmployeeList from "./Components/Admin/EmployeeList";
import CustomerList from "./Components/Admin/CustomerList";
import NotFound from "./Components/NotFound";
import AddEmployee from "./Components/Admin/AddEmployee";
import Error from "./Components/Error";
import ViewSchedule from "./Components/Customer/ViewSchedule";
import ViewProfile from "./Components/ViewProfile"
import AppointmentForm from "./Components/Employee/AppointmentForm";

import UpdateProfile from "./Components/UpdteProfile";

import AppointmentList from "./Components/Employee/AppointmentList";
import BookAppointment from "./Components/Customer/BookAppointment";
import BookedAppointments from "./Components/Customer/BookedAppointments";
import { useHistory, Link, useParams } from "react-router-dom";
import SimpleNav from "./Components/SimpleNav";

function App() {
  const history = useHistory();
  const [user, setUser] = useState();

  useEffect(() => {
    refresh().then(setUser).catch(logout);
  }, []);

  const login = setUser;

  const logout = (history) => {
    setUser();
    localStorage.removeItem("jwt");
    console.log("history");
    console.log(history);


    history?.push("/");

  };

  const auth = {
    user,
    login,
    logout,
  };

  console.log(user);

  return (
    <>
      <AuthContext.Provider value={auth}>
        <Router>
          <Switch>
            <Route path="/login" exact>
              <SimpleNav/>
              <Login />
            </Route>
            <Route path="/signup" exact>
              <SimpleNav/>
              <SignUp />
            </Route>
            <Route path="/" exact> 
              <Navigation/> 
              <Home/>
            </Route>
            <Route path="/" exact>
              <Navigation />
              <Home />
            </Route>
            <Route path="/employee" exact>
              <Navigation />
              <ViewEmployees />
            </Route>
            <Route path="/services/:activeStatus" exact>
              <Navigation/>
              <ServiceList />
            </Route> 
            <Route path="/services/service/add" exact>
              <Navigation/>
              <AddService />
            </Route>
            <Route path="/services/service/edit/:id" exact>
              <Navigation/>
              <UpdateService />
            </Route> 
            <Route path="/employees/:activeStatus" exact>
              <Navigation/>
              <EmployeeList />
            </Route>
            <Route path="/employees/employee/add" exact>
              <Navigation/>
              <AddEmployee />
            </Route>
            <Route path="/customers/:activeStatus" exact>
              <Navigation/>
              <CustomerList />
            </Route>
            <Route path="/error" exact>
              <Navigation/>
              <Error />
            </Route>
            <Route path="/notfound" exact>
              <Navigation/>
              <NotFound />
            </Route>
            <Route path="/schedule/:userId" exact>
              <Navigation />
              <ViewSchedule />
            </Route>
            <Route path="/profile" exact>
              <Navigation/>
              <ViewProfile />
            </Route>
            <Route path="/book/:appointmentId" exact>
              <Navigation />
              <BookAppointment />
            </Route>
            <Route path="/customer/appointments" exact>
              <Navigation />
              <BookedAppointments />
            </Route>
            <Route path="/availability" exact>
              <Navigation />
              <AppointmentForm />
            </Route>
            <Route path="/profile/edit/:id" exact>
              <Navigation/>
              <UpdateProfile />
            </Route>
            <Route path="/appointments" exact>
              <Navigation />
              <AppointmentList />
            </Route>
          </Switch>
        </Router>
      </AuthContext.Provider>
    </>
  );
}

export default App;
