import React, { useState, useEffect } from 'react';
import facade from './ApiFacade'

export default function LoggedIn() {
    const [username, setUsername] = useState();
    const [role, setRole] = useState();

    useEffect(() => {
        facade.fetchUser().then(res => {setUsername(res.userName); setRole(res.roleList)}).catch(e => console.log(e));
      }, [])

    return (
        <div>
          <h2>Data Received from server</h2>
          <h3>Username: {username}</h3>
          <h3>Role: {role}</h3>
        </div>
      )   
}