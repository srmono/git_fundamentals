import React, { useState } from 'react'
import classNames from 'classnames'

function CustomerForm() {
    const [formData, seFormData] = useState({
        username: "",
        email: "",
        password: ""
    })
    const [errors, setErrors] = useState({})

    const handleChange = (e) => {
       seFormData( { ...formData, [e.target.name] : e.target.value} )
       console.log(formData);
    }


    const handleSubmit = (e) => {
        e.preventDefault();
        
        let newErrors = {}

        if(!formData.username.trim()) newErrors.username = "Username required...";
        if(!formData.email.includes("@")) newErrors.email = "Valid email is required";

        setErrors(newErrors)

        if(
            Object.keys(newErrors).length === 0
        ){
            console.log("Form Submitted successfully", JSON.stringify(formData));
            //reset form data
            seFormData({
                username: "",
                email: "",
                password: ""
            })
        }
            

       // console.log(formData); //send to server (convert this object into json )
    }


  return (
    <div>
      <form onSubmit={handleSubmit}>

            <input 
                type='text'
                name="username"
                placeholder='Enter Username'
                value={formData.username}
                onChange={handleChange}
                // className = "input-error"
                className={ 
                    classNames(
                        {"input-error": errors.username}
                    )
                 }
            />
            { errors.username && <p style={{ color:"red"}}> {errors.username} </p>}
            <input 
                type='email'
                name="email"
                placeholder='Enter email'
                value={formData.email}
                onChange={handleChange}
            />
            <input 
                type='password'
                name="password"
                placeholder='Enter password'
                value={formData.password}
                onChange={handleChange}
            />
        <button type='submit'> Submit</button>
      </form>

      {/* {formData} */}
    </div>
  )
}

export default CustomerForm
