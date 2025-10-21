import axios from 'axios';
import React, {useState, useEffect} from 'react'

function FetchUser() {

    const uid = 1;
    const [user, setUser] = useState({})

    useEffect(() => {
        axios.get(`https://jsonplaceholder.typicode.com/users/${uid}`)
            .then( (res) => setUser(res.data) )
            .catch( (err) => console.log(err))
    }, [])

  return (
    <div>
      hey hello i am a user component
      <ul>
        <li>{user.name}</li>
      </ul>
    </div>
  )
}

export default FetchUser
