import React, { useState, useEffect } from 'react';
import Facade from './login/ApiFacade';

export default function Data() {
    const [data, setData] = useState([]);

    useEffect(() => {
      Facade.fetchData().then(res => setData(res));
    },[])
    
    return (
      <div className="container">
        <h3>Fetched data</h3>
        <table className="table">
          <thead className="thead-dark">
            <tr>
              <th scope="col">Name</th>
              <th scope="col">Height</th>
              <th scope="col">Gender</th>
              <th scope="col">url</th>
            </tr>
          </thead>
          <tbody>
            {data.map((person, index)=> <tr key={index}><td>{person.name}</td><td>{person.height}</td><td>{person.gender}</td><td>{person.url}</td></tr> )}
          </tbody>
        </table>
      </div>
    )
  }