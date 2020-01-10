import React, { useState, useEffect } from 'react';
import Facade from './login/ApiFacade';

export default function Jokes({ version, count }) {
  const [jokes, setJokes] = useState([]);
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    Facade.fetchAllCategories().then(res => setCategories(res));
  },[])

  const fetchJokes = (categories) => {
    switch(version) {
      case 2:
          Facade.fetchJokeByCategoryV2(categories).then(res => setJokes(res.jokes));
      break;
      default:
          Facade.fetchJokeByCategory(categories).then(res => setJokes(res.jokes));
      break;
    }
  }
  
  return (
    <div className="container">
      <h3>Fetched data</h3>
      <table className="table">
        <thead className="thead-dark">
          <tr>
            <th scope="col">Category</th>
            <th scope="col">Joke</th>
          </tr>
        </thead>
        <tbody>
          {jokes.map((joke, index)=> (
            <tr key={index}>
              <td>{joke.category}</td>
              <td>{joke.joke}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <Filters count={count} categoriesData={categories} fetchJokes={fetchJokes} />
    </div>
  )
}

function Filters({ count, categoriesData, fetchJokes }) {
  const [categories, setCategories] = useState({});

  const onSubmit = (evt) => {
    evt.preventDefault();
    fetchJokes(Object.keys(categories).map(function(k){return categories[k] !== "" ? categories[k] : null}).join(","));
  }

  const onChange = (evt) => {
    setCategories({...categories, [evt.target.id]: evt.target.value});
  }

  return (
    <form onSubmit={onSubmit} onChange={onChange}>
      <div className="container">
        <div className="row">
          <SelectFilters count={count} categoriesData={categoriesData} />
          <input type="submit" className="btn btn-dark col-sm-12" />
        </div>
      </div>
    </form>
  )
}

function SelectFilters({ count, categoriesData }) {
  let result = [];

  for(let i = 1; i <= count; i++) {
    result.push(
      <select className={"custom-select col-sm-" + 12/count} id={"category" + i}>
        <option value="">Select...</option>
        {categoriesData.map((category) => (
          <option key={category.id}>{category.name}</option>
        ))}
      </select>
    );
  }

  return result
}