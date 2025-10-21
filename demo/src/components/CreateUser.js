import axios from 'axios';
import React, {useState, useEffect} from 'react'

function CreateUser() {
    const formdata = {
        name: "Tiwari",
        username: "Bret",
        email: "Sincere@april.biz",
    }

    const [user, setUser] = useState(formdata);
    // const [createdUser, setCreatedUser] = useState({})

    // const postUser  =  () => {
    //     axios.post(
    //         `https://jsonplaceholder.typicode.com/users/`,
    //         user
    //     )
    //     .then( res => {
    //         // setCreatedUser(res.data)
    //         console.log(res.data);
    //     })
    //     .catch(err => console.log(err))
    // }

    const userid = 2;

    const putUser  =  () => {
        axios.put(
            `https://jsonplaceholder.typicode.com/users/${userid}`,
            formdata
        )
        .then( res => {
            // setCreatedUser(res.data)
            console.log(res.data);
        })
        .catch(err => console.log(err))
    }

    useEffect(() => {
        putUser()
    }, [])

  return (
    <div>
      create user
      {/* <div>{createdUser.name}</div> */}
    </div>
  )
}

export default CreateUser
