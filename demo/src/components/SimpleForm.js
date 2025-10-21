import React, { useState } from 'react'

function SimpleForm() {
    const [firstName, setFirstName] = useState("")

    const handleNameChange = (e) => {
        setFirstName(e.target.value)
        console.log(firstName);
    }

  return (
    <div>
      <form>
            <input 
                name="firstName"
                value={firstName}
                onChange={handleNameChange}
             />
      </form>
      <div>
        {firstName}
      </div>
    </div>
  )
}

export default SimpleForm
