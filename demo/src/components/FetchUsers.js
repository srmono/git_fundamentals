import axios from 'axios'
import React, {useState, useEffect} from 'react'

function FetchUsers() {

    const [users, setUsers] = useState([])

    useEffect(() => {
     axios
        .get(`https://jsonplaceholder.typicode.com/users/`)
        .then( (res) => setUsers(res.data))
        .catch( (err) => console.log(err) )
        .finally( () => console.log("task completed"))
    })
    

  return (
    <div>
      <h1> Fetch Users Component </h1>

    </div>
  )
}

export default FetchUsers
