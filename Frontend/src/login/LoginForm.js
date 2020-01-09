import React, { useState} from 'react';
import Facade from './ApiFacade';
import Login from './Login';
import LoggedIn from './LoggedIn';

export default function App() {
    const [loggedIn, setLoggedIn] = useState(false);
    const [error, setError] = useState();

    const logout = () => { 
        Facade.logout();
        setLoggedIn(false);
    }

    const login = (user, pass) => { 
        Facade.login(user,pass)
        .then(res => {setLoggedIn(true); setError("")})
        .catch(e => setError("Wrong username or password"));

    }

    return (
        <div>
            {!loggedIn ? 
                (<div>
                <Login login={login} />
                <p>{error}</p>
                </div>) : (<div>
                    <LoggedIn />
                    <button className="btn btn-primary" onClick={logout}>Logout</button>
                </div>)}
        </div>
    )
}