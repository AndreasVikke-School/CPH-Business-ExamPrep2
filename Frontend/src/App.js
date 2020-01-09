import React from 'react';
import { BrowserRouter as Router, Switch, Route, NavLink } from "react-router-dom";
import './style/App.css';
import LoginForm from './login/LoginForm';
import URLSettings from './settings'
import Data from './Data';

function App() {


  return (
    <div className="App">
      <Router>
        <Header />
        <Switch>
          <Route exact path={URLSettings.getURL("Home")}> <Welcome /> </Route>
          <Route path={URLSettings.getURL("Login")}> <LoginForm /> </Route>
          <Route path={URLSettings.getURL("Data")}> <Data /> </Route>
          <Route path={URLSettings.getURL("About")}> <About /> </Route>
          <Route path={URLSettings.getURL("NoMatch")}> <NoMatch /> </Route>
        </Switch>
        <Footer />
      </Router>
    </div>
  )
}

const Header = () => {
  return (
    <ul className="header">
      <li><NavLink activeClassName="active" exact to={URLSettings.getURL("Home")}>Home</NavLink></li>
      <li><NavLink activeClassName="active" to={URLSettings.getURL("Login")}>Login</NavLink></li>
      <li><NavLink activeClassName="active" to={URLSettings.getURL("Data")}>Data</NavLink></li>
      <li><NavLink activeClassName="active" to={URLSettings.getURL("About")}>About</NavLink></li>
      <li><NavLink activeClassName="active" to={URLSettings.getURL("FAQ")}>FAQ</NavLink></li>
    </ul>
  )
}

const Footer = () => {
  return (
    <footer>
      <div className="d-flex justify-content-center align-items-center">
        <span> © Copyright 2019 - Martin Frederiksen, Andreas Vikke, Emil Svensmark, Asger Sørensen, & William Huusfeldt. </span>
      </div>
    </footer>
  )
}

const About = () => <div>About</div>

const NoMatch = () => <div>No match!</div>

//If Welcome function reaches about 10 lines of code place the function in separate file.
function Welcome() {
  return (
    <div className="d-flex justify-content-center align-items-center link">
      <a href="https://github.com/asgerhs/CA-3/blob/master/README.md">Press me for quick start guide!!</a>
    </div>
  )
}

export default App;