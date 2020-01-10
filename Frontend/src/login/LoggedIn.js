import React, { useState, useEffect } from 'react';
import facade from './ApiFacade'
import Jokes from '../Jokes'

export default function LoggedIn() {
    const [user, setUser] = useState({});
    const [categoriesData, setCategoriesData] = useState([]);
    const [categoryCount, setCategoryCount] = useState();

    useEffect(() => {
      facade.fetchUser().then(res => setUser(res)).catch(e => console.log(e));
      facade.fetchAllCategories().then(res => setCategoriesData(res)).catch(e => console.log(e));
    }, [])

    const getCount = (category) => {
      facade.fetchRequestCountByCategory(category).then(res => setCategoryCount(res)).catch(e => console.log(e));
    }

    return (
        <div>
          <Jokes version={2} count={12} />
          <br /> <br /> <br />
          {/* eslint-disable-next-line */}
          {user.roleList == "admin" ? <AdminPage categoriesData={categoriesData} getCount={getCount} categoryCount={categoryCount} /> : ""}
        </div>
      )   
}

function AdminPage({ categoriesData, getCount, categoryCount }) {
  const onChange = (evt) => {
    getCount(evt.target.value)
  }

  return (
    <form className="container" onChange={onChange}>
      <div className="row">
        <p className="col-sm-5">Get Request Count of Hobby:</p>
        <select className="custom-select col-sm-3">
          <option value="">Select...</option>
          {categoriesData.map((category) => (
            <option key={category.id}>{category.name}</option>
          ))}
        </select>
        <p className="col-sm-4">{categoryCount}</p>
      </div>
    </form>
  )
}