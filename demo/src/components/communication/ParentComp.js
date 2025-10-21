import React, { useState } from 'react'
import ChildComp from './ChildComp'

function ParentComp() {

    const [msg, setmsg] = useState("");

    const handleChildMessage = (childMessage) => {
        setmsg(childMessage);
    }

  return (
    
    <div>
      <h1> Parent Component</h1>
      <h3>A message from child is here: {msg}</h3>

      <ChildComp sendToParent={handleChildMessage} name="Axess Academy" />
      
    </div>
  )
}

export default ParentComp
